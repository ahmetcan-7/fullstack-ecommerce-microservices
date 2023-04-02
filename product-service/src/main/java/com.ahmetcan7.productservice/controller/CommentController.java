package com.ahmetcan7.productservice.controller;

import com.ahmetcan7.productservice.dto.comment.CommentDto;
import com.ahmetcan7.productservice.dto.comment.CreateCommentRequest;
import com.ahmetcan7.productservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CreateCommentRequest createCommentRequest){
        return new ResponseEntity<>(commentService.createComment(createCommentRequest),HttpStatus.CREATED);
    }
}
