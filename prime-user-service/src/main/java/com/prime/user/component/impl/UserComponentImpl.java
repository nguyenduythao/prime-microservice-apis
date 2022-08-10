package com.prime.user.component.impl;

import com.prime.common.constant.BaseConstant;
import com.prime.common.constant.ErrorCategory;
import com.prime.common.dto.user.MailActiveAccountDTO;
import com.prime.common.dto.user.UserDTO;
import com.prime.common.dto.user.UserDetailDTO;
import com.prime.common.enums.TokenType;
import com.prime.common.enums.user.UserStatus;
import com.prime.common.exception.ErrorCode;
import com.prime.common.exception.ValidationException;
import com.prime.common.util.ErrorMessage;
import com.prime.common.vo.user.*;
import com.prime.user.common.constant.UserErrorCode;
import com.prime.user.component.UserComponent;
import com.prime.user.model.entity.RoleEntity;
import com.prime.user.model.entity.TokenEntity;
import com.prime.user.model.entity.UserDetailEntity;
import com.prime.user.model.entity.UserEntity;
import com.prime.user.model.mapper.UserDetailMapper;
import com.prime.user.model.mapper.UserMapper;
import com.prime.user.service.*;
import com.prime.user.task.MailActiveAccountTaskExecutor;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserComponentImpl implements UserComponent {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDetailMapper userDetailMapper;

    @Autowired
    ErrorMessage errorMessage;

    @Autowired
    private TaskExecutor taskExecutor;

    @Value("${service.gateway.active-account-url}")
    private String activeAccountUrl;

    @Override
    public UserDTO getUserByUserId(Integer userId) throws ValidationException {
        Optional<UserEntity> userEntityOptional = userService.findById(userId);
        if (userEntityOptional.isEmpty()) {
            throw new ValidationException(errorMessage.getError(UserErrorCode.ID_NOT_FOUND,
                    UserEntity.Fields.userId));
        }
        return userMapper.convertToDTO(userEntityOptional.get());
    }

    @Override
    public UserDTO createUser(RegisterUserVO userVO) throws ValidationException {
        if (!userVO.getPassword().equals(userVO.getConfirmPassword())) {
            ErrorCode error = errorMessage.getError(UserErrorCode.CONFIRM_PASSWORD_NOT_MATCH,
                    RegisterUserVO.Fields.confirmPassword);
            error.setCategory(ErrorCategory.VALIDATE);
            throw new ValidationException(error);
        }

        Optional<UserEntity> userOptional = userService.findByEmail(userVO.getEmail());
        if (userOptional.isPresent()) {
            throw new ValidationException(errorMessage.getError(UserErrorCode.EMAIL_EXISTED,
                    RegisterUserVO.Fields.email));
        }

        UserEntity userEntity = new UserEntity();
        userMapper.mapToEntity(userVO, userEntity);
        userEntity = userService.create(userEntity);

        Date expireTime = DateUtils.addMinutes(new Date(), BaseConstant.ACTIVE_ACCOUNT_TOKEN_EXPIRE_TIME);
        TokenEntity tokenEntity = TokenEntity.builder()
                .token(UUID.randomUUID().toString())
                .expireTime(expireTime)
                .tokenType(TokenType.ACCOUNT)
                .referenceId(userEntity.getUserId())
                .build();

        tokenEntity = tokenService.create(tokenEntity);

        String url = activeAccountUrl + tokenEntity.getToken();
        MailActiveAccountDTO mailActiveDTO = MailActiveAccountDTO.builder()
                .url(url)
                .subject("Prime System - Active account")
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .build();

        taskExecutor.execute(new MailActiveAccountTaskExecutor(emailService, mailActiveDTO));
        UserDTO userDTO = userMapper.convertToDTO(userEntity);
        return userDTO;
    }

    @Override
    public UserDTO updateUserRole(Integer userId, UserVO userVO) throws ValidationException {
        Optional<UserEntity> userEntityOptional = userService.findById(userId);
        if (userEntityOptional.isEmpty()) {
            throw new ValidationException(errorMessage.getError(UserErrorCode.ID_NOT_FOUND, UserEntity.Fields.userId));
        }

        Optional<RoleEntity> roleEntityOptional = roleService.findById(userVO.getRoleId());
        if (roleEntityOptional.isEmpty()) {
            throw new ValidationException(errorMessage.getError(UserErrorCode.ID_NOT_FOUND, RoleEntity.Fields.roleId));
        }
        final UserEntity userEntity = userEntityOptional.get();
        userEntity.setRoleId(roleEntityOptional.get().getRoleId());
        return userMapper.convertToDTO(userService.update(userEntity));
    }

    @Override
    public UserDetailDTO updateUserDetail(Integer userId, UserDetailVO userDetailVO) throws ValidationException {
        Optional<UserEntity> userEntityOptional = userService.findById(userId);
        if (userEntityOptional.isEmpty()) {
            throw new ValidationException(errorMessage.getError(UserErrorCode.ID_NOT_FOUND, UserEntity.Fields.userId));
        }

        final UserEntity userEntity = userEntityOptional.get();

        Optional<UserDetailEntity> userDetailEntityOptional = userDetailService.findById(userId);
        UserDetailEntity userDetailEntity;
        if (userDetailEntityOptional.isEmpty()) {
            userDetailEntity = new UserDetailEntity();
            userDetailEntity.setUserId(userEntity.getUserId());
            userDetailMapper.mapToEntity(userDetailVO, userDetailEntity);
            userDetailService.create(userDetailEntity);
        } else {
            userDetailEntity = userDetailEntityOptional.get();
            userDetailMapper.mapToEntity(userDetailVO, userDetailEntity);
            userDetailService.create(userDetailEntity);
        }
        return userDetailMapper.convertToDTO(userDetailEntity);
    }

    @Override
    public void deleteUser(Integer userId) throws ValidationException {
        Optional<UserEntity> userEntityOptional = userService.findById(userId);
        if (userEntityOptional.isEmpty()) {
            throw new ValidationException(errorMessage.getError(UserErrorCode.ID_NOT_FOUND, UserEntity.Fields.userId));
        }
        userService.delete(userEntityOptional.get());
    }

    @Override
    public void activeUser(String token) throws ValidationException {
        Optional<TokenEntity> opTokenEntity = tokenService.findByTokenAndTokenType(token, TokenType.ACCOUNT);
        boolean isValidToken = isTokenValid(opTokenEntity);
        if (!isValidToken) {
            if (opTokenEntity.isPresent()) {
                tokenService.delete(opTokenEntity.get());
            }
            throw new ValidationException(errorMessage.getError(UserErrorCode.URL_TOKEN_HAS_EXPIRED_TIME, TokenEntity.Fields.token));
        }

        Optional<UserEntity> opUserEntity = userService.findById(opTokenEntity.get().getReferenceId());
        if (opUserEntity.isEmpty()) {
            throw new ValidationException(errorMessage.getError(UserErrorCode.ID_NOT_FOUND, UserEntity.Fields.userId));
        }
        UserStatus userStatus = UserStatus.ACTIVATED;
        UserEntity userEntity = opUserEntity.get();
        userEntity.setUserStatus(userStatus);
        userService.update(userEntity);
        tokenService.delete(opTokenEntity.get());
    }

    public void changePassword(ChangePasswordVO changePasswordVO) {
        //TODO: Implement here
    }

    public void forgotPassword(ForgotPasswordVO forgotPasswordVO) {
        //TODO: Implement here
    }

    public void checkResetPasswordToken(String token) {
        //TODO: Implement here
    }

    public void resetPassword(String token, ResetPasswordVO resetPasswordVO) {
        //TODO: Implement here
    }

    private boolean isTokenValid(Optional<TokenEntity> tokenEntityOpt) {
        if (!tokenEntityOpt.isPresent()) return false;
        if (tokenEntityOpt.get().getExpireTime().getTime() < System.currentTimeMillis()) return false;
        return true;
    }
}
