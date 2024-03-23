package org.ra.atomidtesttask.application.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.ra.atomidtesttask.application.user.exception.DuplicateUsernameException;
import org.ra.atomidtesttask.application.user.service.UserService;
import org.ra.atomidtesttask.domain.user.User;
import org.ra.atomidtesttask.infrastructure.persistence.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public User register(User user) {
        if (existsByUsername(user.getUsername())){
            throw new DuplicateUsernameException("User with username %s already exists".formatted(user.getUsername()));
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
