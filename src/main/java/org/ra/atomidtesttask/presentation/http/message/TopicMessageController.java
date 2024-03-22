package org.ra.atomidtesttask.presentation.http.message;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ra.atomidtesttask.application.message.service.MessageService;
import org.ra.atomidtesttask.application.topic.service.TopicService;
import org.ra.atomidtesttask.application.user.service.UserService;
import org.ra.atomidtesttask.domain.topic.Message;
import org.ra.atomidtesttask.domain.user.User;
import org.ra.atomidtesttask.presentation.http.dto.ErrorDto;
import org.ra.atomidtesttask.presentation.http.dto.ValidationErrorDto;
import org.ra.atomidtesttask.presentation.http.message.dto.CreateMessageRequest;
import org.ra.atomidtesttask.presentation.http.message.dto.TopicMessageResponse;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@Tag(name = "Topics", description = "Operations with topics")
@RequestMapping("/topics/{topicId}/messages")
@SecurityRequirement(name = "basicAuth")
@RequiredArgsConstructor
@Validated
public class TopicMessageController {
    private final TopicService topicService;
    private final UserService userService;
    private final MessageService messageService;
    private final TopicMessageMapper mapper;

    @GetMapping
    @PageableAsQueryParam
    @Operation(summary = "Find all messages", description = "Pagination friendly messages query",
            responses = {@ApiResponse(responseCode = "200", description = "Ok", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = ErrorDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Not authorized", content = {@Content}),
                    @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = ErrorDto.class))})})
    public Page<TopicMessageResponse> findAllMessagesInTopic(@PathVariable UUID topicId, Pageable pageable) {
        return messageService.findAllByTopicId(topicId, pageable).map(mapper::toDto);
    }

    @PostMapping
    @Operation(summary = "Post message", description = "Create message at the existing topic",
            responses = {@ApiResponse(responseCode = "201", description = "Created", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = ValidationErrorDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Not authorized", content = {@Content}),
                    @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = ErrorDto.class))})})
    @ResponseStatus(HttpStatus.CREATED)
    public TopicMessageResponse postMessage(@PathVariable UUID topicId,
                                            @Valid @RequestBody CreateMessageRequest request,
                                            Principal principal) {
        Message message = mapper.fromDto(request);
        User author = userService.findByUsername(principal.getName()).orElseThrow();
        message.setUser(author);
        message = topicService.attachToTopic(topicId, message);
        return mapper.toDto(message);
    }
}
