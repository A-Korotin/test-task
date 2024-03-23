package org.ra.atomidtesttask.presentation.http.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(name = "Error")
public class ErrorDto {
    @Getter
    @Schema(example = "Error cause")
    private String detail;
    public static ErrorDto of(String detail) {
        return new ErrorDto(detail);
    }
}
