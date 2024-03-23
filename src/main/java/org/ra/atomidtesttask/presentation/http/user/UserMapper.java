package org.ra.atomidtesttask.presentation.http.user;

import org.ra.atomidtesttask.domain.user.User;
import org.ra.atomidtesttask.domain.user.UserRole;
import org.ra.atomidtesttask.presentation.http.EntityMapper;
import org.ra.atomidtesttask.presentation.http.user.dto.RegisterRequest;
import org.ra.atomidtesttask.presentation.http.user.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements EntityMapper<User, RegisterRequest, UserResponse> {
    @Override
    public User fromDto(RegisterRequest dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(UserRole.USER); // default role

        return user;
    }

    @Override
    public UserResponse toDto(User entity) {
        return UserResponse.builder()
                .username(entity.getUsername())
                .role(entity.getRole())
                .build();
    }
}

