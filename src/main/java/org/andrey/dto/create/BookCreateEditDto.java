package org.andrey.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record BookCreateEditDto(
        @NotBlank String name,
        MultipartFile image,
        @NotBlank String author,
        @NotBlank String genre,
        @Size(max = 200) String description,
        @NotNull Integer yearOfPublish,
        @NotNull Integer pages,
        @NotNull Integer pricePaper,
        @NotNull Integer priceDigital,
        @NotNull Integer inStock) {
}
