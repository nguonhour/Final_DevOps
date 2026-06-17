package com.nguonhour.hnh.repository;

import com.nguonhour.hnh.model.Profile;
import com.nguonhour.hnh.model.ProfileType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUuid(String uuid);

    Optional<Profile> findByRegistrationNumber(String registrationNumber);

    boolean existsByRegistrationNumber(String registrationNumber);

    List<Profile> findByProfileType(ProfileType profileType);
}