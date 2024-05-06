package org.andrey.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import org.andrey.validation.group.Marker;
import org.springframework.web.multipart.MultipartFile;

public record BookCreateEditDto(
        @NotBlank(groups = Marker.CreateAction.class) String name,
        @Null(groups = Marker.UpdateAction.class) MultipartFile image,
        @NotBlank(groups = Marker.CreateAction.class) String author,
        @NotBlank(groups = Marker.CreateAction.class) String genre,
        @Size(max = 200) String description,
        @NotNull(groups = Marker.CreateAction.class) Integer yearOfPublish,
        @NotNull(groups = Marker.CreateAction.class) Integer pages,
        @NotNull(groups = Marker.CreateAction.class) Integer pricePaper,
        @NotNull(groups = Marker.CreateAction.class) Integer priceDigital,
        @NotNull(groups = Marker.CreateAction.class) Integer inStock) {
}
