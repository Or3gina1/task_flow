package com.primary.taskFlow.services.jwt;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

   UserDetailsService userDetailService();
}
