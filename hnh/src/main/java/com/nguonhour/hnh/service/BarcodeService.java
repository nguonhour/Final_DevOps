package com.nguonhour.hnh.service;

import com.google.zxing.oned.Code128Writer;
import org.springframework.stereotype.Service;

@Service
public class BarcodeService {

    public void generate(String registrationNumber) {
        Code128Writer writer = new Code128Writer();

        writer.encode(
                registrationNumber,
                com.google.zxing.BarcodeFormat.CODE_128,
                300,
                100);
    }
}