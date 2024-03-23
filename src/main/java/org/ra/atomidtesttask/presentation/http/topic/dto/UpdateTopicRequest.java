package org.ra.atomidtesttask.presentation.http.topic.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "Update topic request")
public class UpdateTopicRequest {
    @NotNull
    @NotBlank
    @Schema(example = "New topic name")
    private String name;
}
