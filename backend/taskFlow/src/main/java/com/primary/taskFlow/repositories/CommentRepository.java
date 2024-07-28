package com.primary.taskFlow.repositories;

import com.primary.taskFlow.dto.CommentDto;
import com.primary.taskFlow.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTaskId(Long taskId);
}
