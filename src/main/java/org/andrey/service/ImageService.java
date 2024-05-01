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
    public void upload(String imageName, InputStream content) {
        Path fullImagePath = getFullImagePath(imageName);
        try (content) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, content.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    public Optional<byte[]> get(String imageName) {
        Path fullImagePath = getFullImagePath(imageName);
        return Files.exists(fullImagePath) ?
                Optional.of(Files.readAllBytes(fullImagePath)) :
                Optional.empty();
    }

    @SneakyThrows
    public boolean delete(String imageName) {
            Path fullImagePath = getFullImagePath(imageName);
            return Files.deleteIfExists(fullImagePath);
    }

    private Path getFullImagePath(String imageName) {
        return Path.of(bucket, imageName);
    }

}
