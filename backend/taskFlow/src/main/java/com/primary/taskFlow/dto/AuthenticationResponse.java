package com.primary.taskFlow.dto;
import com.primary.taskFlow.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private String jwt;

    private Long userId;

    private UserRole userRole;


}



