package com.nguonhour.hnh.service;

import com.nguonhour.hnh.model.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfileServiceTest {

    @Autowired
    ProfileService profileService;

    @Test
    void shouldCreateProfile() {
        Profile p = new Profile();
        p.setFullName("John Doe");
        p.setDepartment("Engineering");
        p.setProfileType(Profile.ProfileType.STUDENT);
        Profile saved = profileService.createProfile(p);

        assertNotNull(saved.getId());
    }
}
