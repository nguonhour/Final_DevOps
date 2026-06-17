package com.nguonhour.hnh.service;

import com.nguonhour.hnh.model.Profile;
import com.nguonhour.hnh.model.ProfileType;
import com.nguonhour.hnh.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
        File folder = new File(UPLOAD_DIR);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public Profile getById(Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for ID: " + id));
    }

    public Profile saveProfile(Profile profile, MultipartFile file) throws IOException {
        int currentYear = LocalDate.now().getYear();
        long trackingSequence = profileRepository.count() + 1;
        String deptPrefix = (profile.getDepartment() != null && !profile.getDepartment().isBlank())
                ? profile.getDepartment().substring(0, Math.min(4, profile.getDepartment().length())).toUpperCase()
                : "DEPT";

        profile.setUuid(UUID.randomUUID().toString());
        profile.setRegistrationNumber(String.format("%d-%s-%03d", currentYear, deptPrefix, trackingSequence));

        if (file != null && !file.isEmpty()) {
            String originalName = file.getOriginalFilename();
            String extension = originalName != null ? originalName.substring(originalName.lastIndexOf(".")) : ".jpg";
            String savedFilename = profile.getUuid() + extension;
            Path targetPath = Paths.get(UPLOAD_DIR + savedFilename);
            Files.write(targetPath, file.getBytes());
            profile.setPhotoFileName(savedFilename);
            profile.setPhotoContentType(file.getContentType());
        }
        return profileRepository.save(profile);
    }

    public void processBatchCsv(MultipartFile file) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String rowLine;
            boolean insideHeader = true;
            while ((rowLine = br.readLine()) != null) {
                if (insideHeader) {
                    insideHeader = false;
                    continue;
                }
                String[] columns = rowLine.split(",");
                if (columns.length >= 4) {
                    Profile profile = new Profile();
                    profile.setFullName(columns[0].trim());
                    profile.setEmail(columns[1].trim());
                    profile.setPhone(columns[2].trim());
                    profile.setDepartment(columns[3].trim());
                    profile.setProfileType(columns.length >= 5 ? ProfileType.valueOf(columns[4].trim().toUpperCase())
                            : ProfileType.STUDENT);
                    saveProfile(profile, null);
                }
            }
        }
    }
}