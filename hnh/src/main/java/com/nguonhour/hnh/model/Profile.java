package com.nguonhour.hnh.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "profiles", uniqueConstraints = @UniqueConstraint(name = "uk_profile_reg_number", columnNames = "registration_number"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false, length = 36)
    private String uuid;

    @Column(name = "registration_number", nullable = false, unique = true, length = 64)
    private String registrationNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile_type", nullable = false, length = 16)
    private ProfileType profileType;

    @Column(nullable = false, length = 120)
    private String fullName;

    @Column(length = 80)
    private String department;

    @Column(length = 120)
    private String title;

    @Column(length = 120)
    private String email;

    @Column(length = 40)
    private String phone;

    @Column(length = 60)
    private String bloodGroup;

    private LocalDate dateOfBirth;
    private LocalDate issueDate;
    private LocalDate expiryDate;

    @Column(length = 255)
    private String photoFileName;

    @Column(length = 60)
    private String photoContentType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "template_id")
    private Template template;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    @Builder.Default
    private BarcodeType barcodeType = BarcodeType.CODE_128;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.issueDate == null) {
            this.issueDate = LocalDate.now();
        }
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Transient
    public boolean hasPhoto() {
        return photoFileName != null && !photoFileName.isBlank();
    }
}