package com.ahmetcan7.productservice.repository;

import com.ahmetcan7.productservice.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment,UUID> {
}
