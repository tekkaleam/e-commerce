package com.shop.ecommerse.service;

import com.github.javafaker.Faker;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserResponseConverter userResponseConverter;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @BeforeEach
    public void setUp() {

        faker = new Faker();
        userName = faker.name().username();
        SecurityContextHolder.setContext(new SecurityContextImpl());
        SecurityContextHolder.getContext().setAuthentication(
                new Authentication() {

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }
            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }
            @Override
            public boolean isAuthenticated() {
                return false;
            }
            @Override
            public String getName() {
                return userName;
            }
            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

        });
    }
    private Faker faker;

    private String userName;

    @Test
    void isShouldRegister() {
        // given
        String email = faker.lorem().word();
        String password = faker.lorem().word();
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail(email);
        registerUserRequest.setPassword(password);

        User userExpected = new User();

        given(userRepository.existsByEmail(email)).willReturn(false);
        given(passwordEncoder.encode(password)).willReturn(password);
        given(userRepository.save(any(User.class))).willReturn(userExpected);

        // when
        User user = userService.register(registerUserRequest);

        // then
        verify(userRepository).save(userArgumentCaptor.capture());
        then(user).isEqualTo(userExpected).usingRecursiveComparison();
        then(userArgumentCaptor.getValue().getEmail()).isEqualTo(email);
        then(userArgumentCaptor.getValue().getPassword()).isEqualTo(password);
        then(userArgumentCaptor.getValue().getEmailVerified()).isEqualTo(0);
    }

    @Test
    void isShouldFetchUser() {
        // given
        User user = new User();

        UserResponse userResponseExpected = new UserResponse();

        given(userRepository.findByEmail(userName)).willReturn(Optional.of(user));
        given(userResponseConverter.apply(user)).willReturn(userResponseExpected);

        // when
        UserResponse userResponseResult = userService.fetchUser();

        // then
        verify(userRepository).findByEmail(userName);
        then(userResponseResult).isEqualTo(userResponseExpected);
    }


    @Test
    void isShouldSaveUser() {
        User user = new User();

        // given
        given(userRepository.save(user)).willReturn(user);

        // then
        then(userService.saveUser(user))
                .isNotNull()
                .isEqualTo(user);
    }

    @Test
    void isShouldThrowExceptionSavingNull() {
        assertThatThrownBy(() -> userService.saveUser(null))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessage("Null user");
    }

    @Test
    void isShouldFindByEmail() {
        User user = new User();
        // given
        given(userRepository.findByEmail("Gmail@skdjf.com")).willReturn(Optional.of(user));
        // then
        then(userService.findByEmail("Gmail@skdjf.com")).isNotNull().isEqualTo(user);
    }

    @Test
    void isShouldThrowExceptionWhenUserIsNullFindByEmail() {
        // given
        given(userRepository.findByEmail(userName)).willReturn(Optional.empty());
        //then
        assertThatThrownBy(() -> userService.fetchUser())
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void isShouldUserExists() {
        User user = new User();
        // given
        given(userRepository.existsByEmail("1")).willReturn(true);
        // then
        then(userService.userExists("1")).isTrue();
    }

    @Test
    void isShouldReturnFalseIfUserNotExists() {

        String email = faker.lorem().word();

        given(userRepository.existsByEmail(email)).willReturn(false);

        // when
        boolean doesUserExist = userService.userExists(email);
        // then
        verify(userRepository).existsByEmail(email);
        then(doesUserExist).isEqualTo(false);
    }

    @Test
    void isShouldGetUser() {
        // given
        User user = new User();

        given(userRepository.findByEmail(userName)).willReturn(Optional.of(user));
        // when
        User userResult = userService.getUser();
        // then
        verify(userRepository).findByEmail(userName);
        then(userResult).isEqualTo(user);
    }

    @Test
    void isShouldUpdateUser() {
        // given
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();

        User user = new User();
        UserResponse userResponseExpected = new UserResponse();

        given(userRepository.findByEmail(userName)).willReturn(Optional.of(user));
        given(userRepository.save(user)).willReturn(user);
        given(userResponseConverter.apply(user)).willReturn(userResponseExpected);

        // when
        UserResponse userResponseResult = userService.updateUser(updateUserRequest);

        // then
        verify(userRepository).findByEmail(userName);
        verify(userRepository).save(user);
        verify(userResponseConverter).apply(user);
        then(userResponseResult).isEqualTo(userResponseExpected);
    }

    @Test
    void isShouldUpdateUserAddress() {
        // given
        UpdateUserAddressRequest updateUserAddressRequest = new UpdateUserAddressRequest();

        User user = new User();
        UserResponse userResponseExpected = new UserResponse();

        given(userRepository.findByEmail(userName)).willReturn(Optional.of(user));
        given(userRepository.save(user)).willReturn(user);
        given(userResponseConverter.apply(user)).willReturn(userResponseExpected);

        // when
        UserResponse userResponseResult = userService.updateUserAddress(updateUserAddressRequest);

        // then
        verify(userRepository).findByEmail(userName);
        verify(userRepository).save(user);
        verify(userResponseConverter).apply(user);
        then(userResponseResult).isEqualTo(userResponseExpected);
    }

    @Test
    void isShouldResetPassword() {
// given
        String oldPassword = faker.howIMetYourMother().character();
        String newPassword = faker.random().hex();
        String activePassword = faker.random().hex();

        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setOldPassword(oldPassword);
        passwordResetRequest.setNewPassword(newPassword);

        User user = new User();
        user.setPassword(activePassword);

        given(userRepository.findByEmail(userName)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(oldPassword, activePassword)).willReturn(true);
        given(passwordEncoder.matches(newPassword, activePassword)).willReturn(false);
        given(passwordEncoder.encode(newPassword)).willReturn(newPassword);
        given(userRepository.save(user)).willReturn(user);

        // when
        userService.resetPassword(passwordResetRequest);

        // then
        verify(userRepository).save(userArgumentCaptor.capture());

        then(user).isEqualTo(userArgumentCaptor.getValue());
    }

    @Test
    void isShouldThrowExceptionWhenOldPassAndNewPassNotEqual() {

        given(userRepository.findByEmail(userName)).willReturn(Optional.of(new User()));
        given(passwordEncoder.matches(any(), any())).willReturn(false);

        assertThatThrownBy(() -> userService.resetPassword(new PasswordResetRequest()))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessage("Invalid password");
    }

    @Test
    void isShouldGetVerificationStatusTrueIfIsOne() {

        User user = new User();
        user.setEmailVerified(1);

        given(userRepository.findByEmail(userName)).willReturn(Optional.of(user));

        //when
        Boolean verificationStatus = userService.getVerificationStatus();

        //then
        then(verificationStatus).isEqualTo(true);
    }

    @Test
    void isShouldGetVerificationStatusFalseIfNotOne() {
        User user = new User();
        user.setEmailVerified(faker.number().numberBetween(2, 100));

        given(userRepository.findByEmail(userName)).willReturn(Optional.of(user));

        //when
        Boolean verificationStatus = userService.getVerificationStatus();

        //then
        then(verificationStatus).isEqualTo(false);

    }

    @Test
    void itShouldThrowExceptionWhenUserNameIsNullFetchUser() {

        // given
        SecurityContextHolder.getContext().setAuthentication(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }


        });

        // when, then
        assertThatThrownBy(() -> userService.fetchUser())
                .isInstanceOf(AccessDeniedException.class)
                .hasMessage("Invalid access");

    }
}