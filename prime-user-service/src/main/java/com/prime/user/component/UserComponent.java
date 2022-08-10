package com.prime.user.component;

import com.prime.common.dto.user.UserDTO;
import com.prime.common.dto.user.UserDetailDTO;
import com.prime.common.exception.ValidationException;
import com.prime.common.vo.user.*;

public interface UserComponent {

    UserDTO getUserByUserId(Integer userId) throws ValidationException;

    UserDTO createUser(RegisterUserVO userVO) throws ValidationException;

    UserDTO updateUserRole(Integer userId, UserVO userVO) throws ValidationException;

    UserDetailDTO updateUserDetail(Integer userId, UserDetailVO userDetailVO) throws ValidationException;

    void deleteUser(Integer userId) throws ValidationException;

    void activeUser(String token) throws ValidationException;

    void changePassword(ChangePasswordVO changePasswordVO);

    void forgotPassword(ForgotPasswordVO forgotPasswordVO);

    void checkResetPasswordToken(String token);

    void resetPassword(String token, ResetPasswordVO resetPasswordVO);
}
