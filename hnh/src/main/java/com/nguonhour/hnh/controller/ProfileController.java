package com.nguonhour.hnh.controller;

import com.nguonhour.hnh.model.Profile;
import com.nguonhour.hnh.repository.ProfileRepository;
import com.nguonhour.hnh.service.PdfService;
import com.nguonhour.hnh.service.ProfileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;
    private final PdfService pdfService;
    private final ProfileRepository profileRepository;

    public ProfileController(ProfileService profileService, PdfService pdfService,
            ProfileRepository profileRepository) {
        this.profileService = profileService;
        this.pdfService = pdfService;
        this.profileRepository = profileRepository;
    }

    @PostMapping("/create")
    public String createCard(@ModelAttribute Profile profile, @RequestParam("file") MultipartFile file, Model model) {
        try {
            Profile entity = profileService.saveProfile(profile, file);
            return "redirect:/profiles/download-view/" + entity.getId();
        } catch (Exception e) {
            model.addAttribute("error", "Failed to compile record schema: " + e.getMessage());
            return "index";
        }
    }

    // @PostMapping("/create")
    // public String createCard(@ModelAttribute Profile profile,
    // @RequestParam("file") MultipartFile file,
    // Model model) {
    // try {
    // Profile savedProfile = profileService.saveProfile(profile, file);

    // // This exposes the saved profile record so Thymeleaf loads
    // // its specific DB attributes and reveals the PDF Download Button
    // model.addAttribute("profile", savedProfile);
    // model.addAttribute("message", "ID Card created successfully!");
    // } catch (Exception e) {
    // e.printStackTrace();
    // model.addAttribute("error", "Failed to generate ID Card profile metadata: " +
    // e.getMessage());
    // }

    // // This targets your index.html template file
    // return "index";
    // }

    @PostMapping("/batch")
    public String handleBatchCsvUpload(@RequestParam("csvFile") MultipartFile file, Model model) {
        try {
            profileService.processBatchCsv(file);
            model.addAttribute("message", "Batch group card registry updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Error mapping data file array: " + e.getMessage());
        }
        return "index";
    }

    @GetMapping("/download-view/{id}")
    public ResponseEntity<byte[]> servePdfCardOutput(@PathVariable Long id) throws Exception {
        Profile target = profileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid data mapping index identity: " + id));
        byte[] payload = pdfService.generateIdCardPdf(target);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=id-card-" + target.getRegistrationNumber() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(payload);
    }
}