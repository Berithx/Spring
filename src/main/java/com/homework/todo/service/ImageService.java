package com.homework.todo.service;

import com.homework.todo.entity.Image;
import com.homework.todo.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    private static final String UPLOAD_DIR = "imageUploads";

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void savaImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Path filepath = Paths.get(UPLOAD_DIR + fileName);

        Files.createDirectories(filepath.getParent());

        Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);

        Image image = new Image();
        image.setFileName(fileName);
        image.setFilePath(filepath.toString());

        imageRepository.save(image);
    }
}
