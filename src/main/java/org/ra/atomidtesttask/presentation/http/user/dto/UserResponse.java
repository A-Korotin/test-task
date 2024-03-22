package org.ra.atomidtesttask.presentation.http.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.ra.atomidtesttask.domain.user.UserRole;

@Data
@Builder
@Schema(name = "User data")
public class UserResponse {
    @Schema(example = "username")
    private String username;
    @Schema(example = "USER")
    private UserRole role;
}
