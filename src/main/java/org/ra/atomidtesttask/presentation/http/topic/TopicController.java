package org.ra.atomidtesttask.presentation.http.topic;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ra.atomidtesttask.application.topic.dto.TopicUpdateDto;
import org.ra.atomidtesttask.application.topic.service.TopicService;
import org.ra.atomidtesttask.application.user.service.UserService;
import org.ra.atomidtesttask.domain.topic.Message;
import org.ra.atomidtesttask.domain.topic.Topic;
import org.ra.atomidtesttask.domain.user.User;
import org.ra.atomidtesttask.presentation.http.dto.ErrorDto;
import org.ra.atomidtesttask.presentation.http.dto.ValidationErrorDto;
import org.ra.atomidtesttask.presentation.http.message.TopicMessageMapper;
import org.ra.atomidtesttask.presentation.http.topic.dto.CreateTopicRequest;
import org.ra.atomidtesttask.presentation.http.topic.dto.TopicResponse;
import org.ra.atomidtesttask.presentation.http.topic.dto.UpdateTopicRequest;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@Tag(name = "Topics", description = "Operations with topics")
@RequestMapping("/topics")
@SecurityRequirement(name = "basicAuth")
@RequiredArgsConstructor
@Validated
public class TopicController {
    private final TopicService service;
    private final UserService userService;

    private final TopicMapper mapper;
    private final TopicMessageMapper topicMessageMapper;

    @GetMapping
    @PageableAsQueryParam
    @Operation(summary = "Find all topics", description = "Pagination friendly topic query",
            responses = {@ApiResponse(responseCode = "200", description = "Ok", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = ErrorDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Not authorized", content = {@Content})})
    public Page<TopicResponse> findAll(Pageable pageable) {
        return service.findAll(pageable).map(mapper::toDto);
    }

    @GetMapping("/{topicId}")
    @Operation(summary = "Find topic by id", responses = {@ApiResponse(responseCode = "200", description = "Ok", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "BadRequest", content = {@Content(schema = @Schema(implementation = ErrorDto.class))}),
            @ApiResponse(responseCode = "401", description = "Not authorized", content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = ErrorDto.class))})})
    public TopicResponse findTopicById(@PathVariable UUID topicId) {
        return mapper.toDto(service.findById(topicId));
    }

    @PostMapping
    @Operation(summary = "Create topic", description = "Create topic with initial message",
            responses = {@ApiResponse(responseCode = "201", description = "Created", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = ValidationErrorDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Not authorized", content = {@Content})})
    @ResponseStatus(HttpStatus.CREATED)
    public TopicResponse createTopic(@Valid @RequestBody CreateTopicRequest request,
                                     Principal principal) {
        Topic topic = mapper.fromDto(request);
        User author = userService.findByUsername(principal.getName()).orElseThrow();
        Message initial = topicMessageMapper.fromDto(request.getInitialMessage());
        initial.setUser(author);
        topic = service.saveWithInitialMessage(topic, initial);
        return mapper.toDto(topic);
    }

    @PutMapping("/{topicId}")
    @Operation(summary = "Edit topic", description = "Admin only",
            responses = {@ApiResponse(responseCode = "200", description = "Ok", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = ValidationErrorDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Not authorized", content = {@Content}),
                    @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = ErrorDto.class))})})
    @PreAuthorize("hasRole('ADMIN')")
    public TopicResponse editTopic(@PathVariable UUID topicId,
                                   @Valid @RequestBody UpdateTopicRequest request) {
        TopicUpdateDto dto = mapper.toUpdateDto(request);
        Topic updated = service.updateById(topicId, dto);
        return mapper.toDto(updated);
    }

    @DeleteMapping("/{topicId}")
    @Operation(summary = "Delete topic", description = "Admin only.  All the related messages will be also deleted",
            responses = {@ApiResponse(responseCode = "204", description = "Deleted", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = {@Content(schema = @Schema(implementation = ErrorDto.class))}),
                    @ApiResponse(responseCode = "401", description = "Not authorized", content = {@Content}),
                    @ApiResponse(responseCode = "404", description = "Not found", content = {@Content(schema = @Schema(implementation = ErrorDto.class))})})
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(@PathVariable UUID topicId) {
        service.deleteById(topicId);
    }
}
