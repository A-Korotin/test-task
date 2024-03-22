package org.ra.atomidtesttask.application.user.service;

import org.ra.atomidtesttask.domain.user.User;

import java.util.Optional;

public interface UserService {
    User register(User user);
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
