package com.primary.taskFlow.dto;

import com.primary.taskFlow.enums.TaskStatus;
import lombok.Data;

import java.util.Date;

@Data
public class TaskDto {


    private Long id;

    private String title; //заголовок

    private String description; //описание

    private Date dueDate; //срок выполнения

    private String priority;// приоритет задачи

    private TaskStatus taskStatus; //статус задачи

    private Long employeeId; // идентификатор сотрудника

    private String employeeName; // имя сотрудника


}
