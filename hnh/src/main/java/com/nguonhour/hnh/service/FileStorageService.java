package com.nguonhour.hnh.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final Path root = Paths.get("uploads");

    public String save(MultipartFile file) throws IOException {

        String contentType = file.getContentType();

        if (!contentType.equals("image/jpeg")
                && !contentType.equals("image/png")) {
            throw new RuntimeException("Only JPG and PNG allowed");
        }

        if (file.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("File too large (max 5MB)");
        }

        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), root.resolve(filename));

        return filename;
    }
}