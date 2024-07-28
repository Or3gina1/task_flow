package com.primary.taskFlow.controller.admin;

import com.primary.taskFlow.dto.CommentDto;
import com.primary.taskFlow.dto.TaskDto;
import com.primary.taskFlow.services.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin")
@CrossOrigin("*")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(adminService.getUsers());
    }

    @PostMapping("/task")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        TaskDto createTaskDto = adminService.createTask(taskDto);
        if (createTaskDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(createTaskDto);
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks() {
        return ResponseEntity.ok(adminService.getAllTasks());
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        adminService.deleteTask(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getTaskById(id));
    }
    @PutMapping("/task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
       TaskDto updateTask = adminService.updateTask(id, taskDto);
       if (updateTask == null) return ResponseEntity.notFound().build();
       return ResponseEntity.ok(updateTask);
    }
    @GetMapping("/tasks/search/{title}")
    public ResponseEntity<List<TaskDto>> searchTask(@PathVariable String title) {
        return ResponseEntity.ok(adminService.searchTaskByTitle(title));
    }
    @PostMapping("/task/comment/{taskId}")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long taskId, @RequestParam String content) {
        CommentDto createCommentDto = adminService.createComment(taskId, content);
        if (createCommentDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(createCommentDto);
    }
    @GetMapping("/comments/{taskId}")
    public ResponseEntity<List<CommentDto>> getCommentsByTaskId(@PathVariable Long taskId) {
        return ResponseEntity.ok(adminService.getCommentsByTaskId(taskId));
    }

}