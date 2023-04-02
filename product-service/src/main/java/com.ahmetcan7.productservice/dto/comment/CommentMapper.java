package com.ahmetcan7.productservice.dto.comment;

import com.ahmetcan7.productservice.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentDto commentToCommentDto(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                .createdDate(comment.getCreatedDate())
                .createdBy(comment.getCreatedBy())
                .text(comment.getText())
                .build();
    }
}
