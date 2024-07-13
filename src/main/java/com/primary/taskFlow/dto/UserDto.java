package com.primary.taskFlow.dto;

import com.primary.taskFlow.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private long id;
    private String name;
    private String email;
    private String password;

    private UserRole userRole;
}
