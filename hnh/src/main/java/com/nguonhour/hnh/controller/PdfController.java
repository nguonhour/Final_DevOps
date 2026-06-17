package com.nguonhour.hnh.controller;

import com.nguonhour.hnh.model.Profile;
import com.nguonhour.hnh.service.ProfileService;
import com.nguonhour.hnh.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PdfController {

    private final ProfileService profileService;
    private final PdfService pdfService;

    public PdfController(ProfileService profileService, PdfService pdfService) {
        this.profileService = profileService;
        this.pdfService = pdfService;
    }

    @GetMapping("/profile/{id}/pdf")
    public ResponseEntity<byte[]> downloadIdCardPdf(@PathVariable Long id) {
        try {
            Profile profile = profileService.getById(id);
            byte[] pdfContents = pdfService.generateIdCardPdf(profile);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);

            // "inline" opens it smoothly right inside a browser tab preview window
            headers.setContentDispositionFormData("inline", "ID_Card_" + profile.getRegistrationNumber() + ".pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(pdfContents, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}