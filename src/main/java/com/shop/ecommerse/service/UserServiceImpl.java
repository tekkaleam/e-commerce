package com.shop.ecommerse.service;

import com.shop.ecommerse.converters.UserResponseConverter;
import com.shop.ecommerse.domain.entity.User;
import com.shop.ecommerse.domain.request.user.PasswordResetRequest;
import com.shop.ecommerse.domain.request.user.RegisterUserRequest;
import com.shop.ecommerse.domain.request.user.UpdateUserAddressRequest;
import com.shop.ecommerse.domain.request.user.UpdateUserRequest;
import com.shop.ecommerse.domain.response.user.UserResponse;
import com.shop.ecommerse.handler.exceptions.InvalidArgumentException;
import com.shop.ecommerse.handler.exceptions.ResourceNotFoundException;
import com.shop.ecommerse.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserResponseConverter userResponseConverter;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserResponseConverter userResponseConverter) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userResponseConverter = userResponseConverter;
    }

    @Override
    public User register(RegisterUserRequest registerUserRequest) {
        if(userExists(registerUserRequest.getEmail()))
            throw new InvalidArgumentException("An account already exists with this email");
        User user = User.builder()
                .email(registerUserRequest.getEmail())
                .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                .emailVerified(0)
                .build();
        return userRepository.save(user);
    }

    @Override
    public UserResponse fetchUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (Objects.isNull(userName)) {
            throw new AccessDeniedException("Invalid access");
        }
        Optional<User> user = userRepository.findByEmail(userName);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }

        return userResponseConverter.apply(user.get());
    }

    @Override
    public User getUser(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (Objects.isNull(userName)) {
            throw new AccessDeniedException("Invalid access");
        }

        Optional<User> user = userRepository.findByEmail(userName);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return user.get();
    }

    @Override
    public User saveUser(User user) {
        if (Objects.isNull(user)) {
            throw new InvalidArgumentException("Null user");
        }
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) throw new ResourceNotFoundException("User not found");

        return user.get();
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest updateUserRequest) {
        User user = getUser();
        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());
        user.setPhoneNumber(updateUserRequest.getPhone());

        user = userRepository.save(user);
        return userResponseConverter.apply(user);
    }

    @Override
    public UserResponse updateUserAddress(UpdateUserAddressRequest updateUserAddressRequest) {
        User user = getUser();

        user.setAddress(updateUserAddressRequest.getAddress());
        user.setCity(updateUserAddressRequest.getCity());
        user.setCountry(updateUserAddressRequest.getCountry());
        user = userRepository.save(user);
        return userResponseConverter.apply(user);
    }

    @Override
    public void resetPassword(PasswordResetRequest passwordResetRequest) {
        User user = getUser();

        if (!passwordEncoder.matches(passwordResetRequest.getOldPassword(), user.getPassword())) {
            throw new InvalidArgumentException("Invalid password");
        }

        if (passwordEncoder.matches(passwordResetRequest.getNewPassword(), user.getPassword())) {
            return;
        }

        user.setPassword(passwordEncoder.encode(passwordResetRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public Boolean getVerificationStatus() {
        User user = getUser();
        return user.getEmailVerified() == 1;
    }
}
