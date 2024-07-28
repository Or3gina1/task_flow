package com.primary.taskFlow.services.auth;

import com.primary.taskFlow.dto.SignupRequest;
import com.primary.taskFlow.dto.UserDto;

public interface AuthService {

   UserDto signupUser(SignupRequest signupRequest);

   boolean hasUserWithEmail(String email);
}
