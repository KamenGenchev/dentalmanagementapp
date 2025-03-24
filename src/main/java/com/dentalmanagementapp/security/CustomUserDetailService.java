package com.dentalmanagementapp.security;

import com.dentalmanagementapp.entities.common.AbstractUser;
import com.dentalmanagementapp.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final List<IUserRepository> userRepositories;

    @Autowired
    public CustomUserDetailService(List<IUserRepository> userRepositories) {
        this.userRepositories = userRepositories;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        for (IUserRepository repository : userRepositories) {
            Optional<? extends AbstractUser> user = repository.findByEmail(email);
            if (user.isPresent()) {
                return new CustomUserDetails(user.get());
            }
        }
        throw new UsernameNotFoundException(
                String.format("User with email: %s could not be found", email)
        );
    }
}
