package org.ra.atomidtesttask.presentation.http.topic.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.ra.atomidtesttask.presentation.http.message.dto.CreateMessageRequest;

@Data
@Schema(name = "Create topic request")
public class CreateTopicRequest {
    @Schema(example = "Topic name")
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private CreateMessageRequest initialMessage;
}
