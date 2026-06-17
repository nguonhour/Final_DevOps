package com.nguonhour.hnh.model;

public class ProfileBuilder {
    public static Profile defaultStudent(String name) {
        return Profile.builder()
                .fullName(name)
                .profileType(ProfileType.STUDENT)
                .barcodeType(BarcodeType.CODE_128)
                .build();
    }

    public static Profile defaultEmployee(String name) {
        return Profile.builder()
                .fullName(name)
                .profileType(ProfileType.EMPLOYEE)
                .barcodeType(BarcodeType.CODE_128)
                .build();
    }
}