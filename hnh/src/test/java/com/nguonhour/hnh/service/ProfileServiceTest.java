package com.nguonhour.hnh.service;

import com.nguonhour.hnh.model.Profile;
import com.nguonhour.hnh.model.ProfileType; // FIX 1: Explicitly import the enum type
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @Test
    void shouldCreateProfile() throws Exception {
        Profile p = new Profile();
        p.setFullName("John Doe");
        p.setDepartment("Engineering");

        // FIX 2: Set the correct property using the imported Enum directly
        p.setProfileType(ProfileType.STUDENT);

        // FIX 3: Call saveProfile instead of createProfile, passing null for the
        // MultipartFile parameter
        Profile saved = profileService.saveProfile(p, null);

        assertNotNull(saved.getId());
    }
}