package org.ra.atomidtesttask.presentation.http.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@Schema(name = "Validation error")
public class ValidationErrorDto {
    @Schema(example = "Error cause")
    private String message;

    @Schema(description = "Error details")
    private Map<String, String> errors;
}
