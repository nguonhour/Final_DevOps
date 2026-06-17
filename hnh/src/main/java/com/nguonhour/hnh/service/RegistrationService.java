package com.nguonhour.hnh.service;

import com.nguonhour.hnh.model.Profile;
import com.nguonhour.hnh.model.ProfileType;
import com.nguonhour.hnh.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class RegistrationService {

    // FIX: Inject the Repository, not the Profile model entity
    private final ProfileRepository profileRepository;

    public RegistrationService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    /**
     * Logic to generate a custom sequence registration number: YEAR-DEPT-00#
     */
    public String generateRegistrationNumber(ProfileType type, String department) {
        int currentYear = LocalDate.now().getYear();

        // Format department prefix to a clean 3-4 letter code
        String deptPrefix = (department != null && !department.isBlank())
                ? department.substring(0, Math.min(4, department.length())).toUpperCase()
                : "DEPT";

        // Use count to establish sequence tracking numbers safely
        long trackingSequence = profileRepository.count() + 1;

        return String.format("%d-%s-%03d", currentYear, deptPrefix, trackingSequence);
    }
}