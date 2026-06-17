package com.nguonhour.hnh.service;

import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

@Service
public class QrCodeService {

    public void generate(String uuid) throws Exception {
        QRCodeWriter writer = new QRCodeWriter();

        writer.encode(
                "http://localhost:8090/profiles/" + uuid,
                com.google.zxing.BarcodeFormat.QR_CODE,
                200,
                200);
    }
}