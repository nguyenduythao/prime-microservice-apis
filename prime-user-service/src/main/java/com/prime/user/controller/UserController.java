package com.prime.user.controller;

import com.prime.common.dto.Response;
import com.prime.common.dto.user.UserDTO;
import com.prime.common.dto.user.UserDetailDTO;
import com.prime.common.exception.ValidationException;
import com.prime.common.vo.user.*;
import com.prime.user.component.UserComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserComponent userComponent;

    @GetMapping("/users/{id}")
    public Response<UserDTO> getUserByUserId(@PathVariable("id") Integer userId) throws ValidationException {
        return new Response<>(userComponent.getUserByUserId(userId));
    }

    @PostMapping("/users")
    public Response<UserDTO> createUser(@RequestBody @Valid RegisterUserVO registerUserVO) throws ValidationException {
        return new Response<>(userComponent.createUser(registerUserVO));
    }

    @PutMapping("/users/{userId}")
    public Response<?> updateUser(@PathVariable("userId") Integer userId, @RequestBody @Valid UserVO userVO) throws ValidationException {
        return new Response<>(userComponent.updateUserRole(userId, userVO));
    }

    @PutMapping("/users/detail/{userId}")
    public Response<UserDetailDTO> updateUserDetail(@PathVariable("userId") Integer userId, @RequestBody @Valid UserDetailVO userDetailVO) throws ValidationException {
        return new Response<>(userComponent.updateUserDetail(userId, userDetailVO));
    }

    @DeleteMapping("/users/{userId}")
    public Response<?> deleteUser(@PathVariable("userId") Integer userId) throws ValidationException {
        userComponent.deleteUser(userId);
        return new Response<>();
    }

    @PutMapping("/users/active/{token}")
    public Response<?> activeUser(@PathVariable("token") String token) throws ValidationException {
        userComponent.activeUser(token);
        return new Response<>();
    }

    @PostMapping("/password")
    public Response<?> changePassword(@RequestBody @Valid ChangePasswordVO changePasswordVO) throws ValidationException {
        userComponent.changePassword(changePasswordVO);
        return new Response<>();
    }

    @PostMapping("/password/forgot")
    public Response<?> forgotPassword(@RequestBody @Valid ForgotPasswordVO forgotPasswordVO) {
        userComponent.forgotPassword(forgotPasswordVO);
        return new Response<>();
    }

    @GetMapping("/password/reset/{token}")
    public Response<?> checkResetPasswordToken(@PathVariable("token") String token) throws ValidationException {
        userComponent.checkResetPasswordToken(token);
        return new Response<>();
    }

    @PostMapping("/password/reset/{token}")
    public Response<?> forgotPassword(@PathVariable("token") String token, @RequestBody @Valid ResetPasswordVO resetPasswordVO) throws ValidationException {
        userComponent.resetPassword(token, resetPasswordVO);
        return new Response<>();
    }
}
