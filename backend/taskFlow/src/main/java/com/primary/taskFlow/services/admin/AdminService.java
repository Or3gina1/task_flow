package com.primary.taskFlow.services.admin;

import com.primary.taskFlow.dto.CommentDto;
import com.primary.taskFlow.dto.TaskDto;
import com.primary.taskFlow.dto.UserDto;

import java.util.List;

public interface AdminService {


    List<UserDto> getUsers();


    TaskDto createTask(TaskDto taskDto);

    List<TaskDto> getAllTasks();

    void deleteTask(Long id);


    TaskDto getTaskById(Long id);

    TaskDto updateTask(Long id, TaskDto taskDto);

    List<TaskDto> searchTaskByTitle(String title);

    CommentDto createComment(Long taskId, String content);

    List<CommentDto> getCommentsByTaskId(Long taskId);

}
