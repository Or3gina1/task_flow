package com.primary.taskFlow.entities;

import com.primary.taskFlow.dto.TaskDto;
import com.primary.taskFlow.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.text.SimpleDateFormat;
import java.util.Date;
@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; //заголовок

    private String description; //описание

    private Date dueDate; //срок выполнения

    private String priority;// приоритет задачи

    private TaskStatus taskStatus; //статус задачи

    @ManyToOne(fetch = FetchType.LAZY, optional = false) //многие к одному
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) //ложная авторизация
    @JsonIgnore
    private User user;

    public TaskDto getTaskDto() {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(id);
        taskDto.setTitle(title);
        taskDto.setDescription(description);
        taskDto.setEmployeeName(user.getName());
        taskDto.setEmployeeId(user.getId());
        taskDto.setTaskStatus(taskStatus);
        taskDto.setDueDate(dueDate);
        taskDto.setPriority(priority);
        return taskDto;

    }


}
