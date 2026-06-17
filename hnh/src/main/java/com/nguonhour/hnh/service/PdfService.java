package com.nguonhour.hnh.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.nguonhour.hnh.model.Profile;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class PdfService {

    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    public byte[] generateIdCardPdf(Profile profile) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // ID Card Outer Container Table (Approx CR80 Dimensions: ~3.37" x 2.12" scaled)
        Table cardTable = new Table(new float[] { 130f, 220f });
        cardTable.setWidth(UnitValue.createPointValue(350f));
        cardTable.setBackgroundColor(new DeviceRgb(26, 82, 118)); // Deep Corporate Blue
        cardTable.setBorder(new com.itextpdf.layout.borders.SolidBorder(new DeviceRgb(20, 52, 75), 2));
        cardTable.setPadding(10f);

        // --- LEFT COLUMN: Photo & QR ---
        Cell leftCell = new Cell().setBorder(null).setTextAlignment(TextAlignment.CENTER);

        // 1. Profile Photo Integration
        String photoPath = UPLOAD_DIR + profile.getPhotoFileName();
        if (profile.getPhotoFileName() != null && new File(photoPath).exists()) {
            Image profileImg = new Image(ImageDataFactory.create(photoPath));
            profileImg.scaleToFit(90f, 90f);
            leftCell.add(profileImg.setMarginBottom(10f));
        } else {
            // Placeholder text if no image exists
            leftCell.add(new Paragraph("[ No Photo ]").setFontColor(DeviceRgb.WHITE).setFontSize(10f));
        }

        // 2. QR Code Integration (Verification URL or Data)
        String qrData = "Verify ID: " + profile.getRegistrationNumber() + " | Name: " + profile.getFullName();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix qrMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, 80, 80);
        ByteArrayOutputStream qrOs = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(qrMatrix, "PNG", qrOs);
        Image qrImg = new Image(ImageDataFactory.create(qrOs.toByteArray()));
        leftCell.add(qrImg);

        cardTable.addCell(leftCell);

        // --- RIGHT COLUMN: Profile Text Info & Barcode ---
        Cell rightCell = new Cell().setBorder(null).setFontColor(DeviceRgb.WHITE).setPaddingLeft(10f);

        // Header Text
        rightCell.add(
                new Paragraph("IDENTITY CARD").setBold().setFontSize(14f).setFontColor(new DeviceRgb(235, 245, 251)));
        rightCell.add(new Paragraph("Institute of Technology").setFontSize(9f).setItalic()
                .setFontColor(new DeviceRgb(174, 214, 241)));
        rightCell.add(new Paragraph("\n"));

        // User Meta Fields
        rightCell.add(new Paragraph("Name: " + profile.getFullName()).setBold().setFontSize(12f));
        rightCell.add(new Paragraph("Reg No: " + profile.getRegistrationNumber()).setFontSize(10f));
        rightCell.add(new Paragraph("Dept: " + (profile.getDepartment() != null ? profile.getDepartment() : "N/A"))
                .setFontSize(10f));
        rightCell.add(new Paragraph("Role: " + profile.getProfileType().toString()).setFontSize(10f));
        rightCell.add(new Paragraph("\n"));

        // 3. Barcode Support (Code-128)
        Code128Writer barcodeWriter = new Code128Writer();
        BitMatrix barcodeMatrix = barcodeWriter.encode(profile.getRegistrationNumber(), BarcodeFormat.CODE_128, 180,
                30);
        ByteArrayOutputStream barcodeOs = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(barcodeMatrix, "PNG", barcodeOs);
        Image barcodeImg = new Image(ImageDataFactory.create(barcodeOs.toByteArray()));
        rightCell.add(barcodeImg);

        cardTable.addCell(rightCell);

        document.add(cardTable);
        document.close();

        return baos.toByteArray();
    }
}