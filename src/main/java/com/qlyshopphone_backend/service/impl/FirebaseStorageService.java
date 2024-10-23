package com.qlyshopphone_backend.service.impl;

import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.qlyshopphone_backend.dto.response.ImagesResponse;
import com.qlyshopphone_backend.model.Images;
import com.qlyshopphone_backend.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FirebaseStorageService {
    private final ImageRepository imageRepository;

    public List<ImagesResponse> uploadFiles(MultipartFile[] files) throws IOException {
        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("No files to upload");
        }

        for (MultipartFile file : files) {
            if (file.isEmpty() ||
                    (!Objects.requireNonNull(file.getContentType()).startsWith("image/") &&
                            !file.getContentType().startsWith("video/"))) {
                throw new IllegalArgumentException("Only image or video uploads are allowed.");
            }
        }

        List<ImagesResponse> saveImages = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = uploadFile(file);
            Images image = new Images();
            image.setUrl(fileName);
            Images savedImage = imageRepository.save(image);

            ImagesResponse imagesResponse = new ImagesResponse(savedImage.getId(), savedImage.getUrl());
            saveImages.add(imagesResponse);
        }
        return saveImages;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Bucket bucket = StorageClient.getInstance().bucket();
        bucket.create(fileName, file.getInputStream(), file.getContentType());
        return "https://firebasestorage.googleapis.com/v0/b/" + bucket.getName() + "/o/"
                + URLEncoder.encode(fileName, StandardCharsets.UTF_8) + "?alt=media";

    }
}
