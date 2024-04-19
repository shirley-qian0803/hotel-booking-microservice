package com.jiayi.usermanagement.dto;

import com.jiayi.usermanagement.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponse {
    private Long userId;
    private String userName;
    private String email;
    private Role role;
}
