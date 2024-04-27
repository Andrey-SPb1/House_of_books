package org.andrey.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Service
public class ImageService {

//    @Value("${app.image.bucket:C:\\Users\\Frost\\IdeaProjects\\Book_House\\images}")
    private final String bucket;

    public ImageService(@Value("${app.image.bucket:C:\\Users\\Frost\\IdeaProjects\\Book_House\\images}") String bucket) {
        this.bucket = bucket;
    }

    @SneakyThrows
    public void upload(String imagePath, InputStream content) {
        Path fullImagePath = getFullImagePath(imagePath);
        try (content) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, content.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    public Optional<byte[]> get(String imagePath) {
        Path fullImagePath = getFullImagePath(imagePath);
        return Files.exists(fullImagePath) ?
                Optional.of(Files.readAllBytes(fullImagePath)) :
                Optional.empty();
    }

    @SneakyThrows
    public boolean delete(String imagePath) {
        Path fullImagePath = getFullImagePath(imagePath);
        return Files.deleteIfExists(fullImagePath);
    }

    private Path getFullImagePath(String imagePath) {
        return Path.of(bucket, imagePath);
    }

}
