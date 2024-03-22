package org.ra.atomidtesttask.presentation.http.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Schema(name = "Register request")
public class RegisterRequest {
    @NotNull
    @NotBlank
    @Length(min = 5, max = 50)
    @Schema(example = "username")
    private String username;

    @Length(min = 5, max = 50)
    @NotNull
    @NotBlank
    @Schema(example = "password")
    private String password;
}
