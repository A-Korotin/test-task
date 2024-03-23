package org.ra.atomidtesttask.presentation.http.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "Update message request")
public class UpdateMessageRequest {
    @NotNull
    @NotBlank
    @Schema(example = "New message text")
    private String text;
}
