package com.ahmetcan7.productservice.dto.comment;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
public class CreateCommentRequest {
    @NotNull
    private UUID productId;
    @NotNull
    private String text;
}
