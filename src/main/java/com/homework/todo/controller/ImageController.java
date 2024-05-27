package com.homework.todo.controller;

import com.homework.todo.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImageController {
    private ImageService imageService;

    public ImageController (ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/uploads")
    public ResponseEntity<?> uploadImage(@ModelAttribute MultipartFile file) {
        try {
            imageService.savaImage(file);
            return ResponseEntity.ok("이미지 업로드 성공");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading image : " + e.getMessage());
        }

    }
}
