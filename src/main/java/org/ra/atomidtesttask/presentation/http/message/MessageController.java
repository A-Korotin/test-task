package org.ra.atomidtesttask.presentation.http.message;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ra.atomidtesttask.application.message.dto.MessageUpdateDto;
import org.ra.atomidtesttask.application.message.service.MessageService;
import org.ra.atomidtesttask.domain.topic.Message;
import org.ra.atomidtesttask.presentation.http.dto.ErrorDto;
import org.ra.atomidtesttask.presentation.http.dto.ValidationErrorDto;
import org.ra.atomidtesttask.presentation.http.message.dto.MessageResponse;
import org.ra.atomidtesttask.presentation.http.message.dto.UpdateMessageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/messages")
@Tag(name = "Messages", description = "Operation with messages")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
@Validated
public class MessageController {
    private final MessageService service;
    private final MessageMapper mapper;

    @GetMapping("/{messageId}")
    @Operation(summary = "Find message", description = "Find message by id",
            responses = {@ApiResponse(responseCode = "200", description = "Ok", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = ErrorDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Not authorized", content = {@Content}),
                    @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = ErrorDto.class))})})
    @PreAuthorize("hasRole('ADMIN') or @messageSecurity.userHasAccessToView(#messageId, authentication.name)")
    public MessageResponse findById(@PathVariable UUID messageId) {
        return mapper.toDto(service.findById(messageId));
    }

    @PutMapping("/{messageId}")
    @Operation(summary = "Edit message", description = "Only author or admin can edit messages",
            responses = {@ApiResponse(responseCode = "201", description = "Created", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = ValidationErrorDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Not authorized", content = {@Content}),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {@Content}),
                    @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = ErrorDto.class))})})
    @PreAuthorize("hasRole('ADMIN') or @messageSecurity.userHasAccessToModify(#messageId, authentication.name)")
    public MessageResponse editById(@PathVariable UUID messageId,
                                    @Valid @RequestBody UpdateMessageRequest request) {
        MessageUpdateDto dto = mapper.toUpdateDto(request);
        Message updated = service.updateById(messageId, dto);

        return mapper.toDto(updated);
    }

    @DeleteMapping("/{messageId}")
    @Operation(summary = "Delete message",
            description = "Only author or admin can delete messages. If message is the last in the topic, the topic itself if being deleted",
            responses = {@ApiResponse(responseCode = "204", description = "Deleted", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = ValidationErrorDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Not authorized", content = {@Content}),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {@Content}),
                    @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = ErrorDto.class))})})
    @PreAuthorize("hasRole('ADMIN') or @messageSecurity.userHasAccessToModify(#messageId, authentication.name)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID messageId) {
        service.deleteById(messageId);
    }
}
