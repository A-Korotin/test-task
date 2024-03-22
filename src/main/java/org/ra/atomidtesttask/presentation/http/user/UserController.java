package org.ra.atomidtesttask.presentation.http.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ra.atomidtesttask.application.user.service.UserService;
import org.ra.atomidtesttask.domain.user.User;
import org.ra.atomidtesttask.presentation.http.dto.ErrorDto;
import org.ra.atomidtesttask.presentation.http.dto.ValidationErrorDto;
import org.ra.atomidtesttask.presentation.http.user.dto.RegisterRequest;
import org.ra.atomidtesttask.presentation.http.user.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Tag(name = "Users", description = "Operations with system users")
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register new user", responses = {@ApiResponse(responseCode = "201", description = "Success", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "Invalid data provided", content = {@Content(schema = @Schema(implementation = ValidationErrorDto.class))}),
            @ApiResponse(responseCode = "409", description = "Duplicate login", content = {@Content(schema = @Schema(implementation = ErrorDto.class))})})
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        User user = mapper.fromDto(request);
        user = service.register(user);
        return mapper.toDto(user);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN') or authentication.name.equals(#username)")
    @Operation(summary = "Find user by id", security = @SecurityRequirement(name = "basicAuth"),
            responses = {@ApiResponse(responseCode = "200", description = "Ok", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "401", description = "Not authorized", content = {@Content}),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {@Content})
            })
    public UserResponse findById(@PathVariable String username) {
        User user = service.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return mapper.toDto(user);
    }
}
