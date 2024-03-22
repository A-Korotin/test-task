package org.ra.atomidtesttask.presentation.http.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "Create message request")
public class CreateMessageRequest {
    @NotBlank
    @NotNull
    @Schema(example = "Message text")
    private String text;
}
