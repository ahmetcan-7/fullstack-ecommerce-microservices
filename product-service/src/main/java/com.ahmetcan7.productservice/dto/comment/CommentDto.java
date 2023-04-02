package com.ahmetcan7.productservice.dto.comment;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private UUID id;
    private String createdBy;
    private LocalDateTime createdDate;
    private String text;
}
