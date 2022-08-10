package com.prime.common.vo.user;

import com.prime.common.constant.RegexPattern;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class OAuthClientDetailVO implements Serializable {

    private static final long serialVersionUID = 8397420174294872197L;

    @NotBlank(message = "{validation.user.common.value.notBlank}")
    private String clientId;

    @NotBlank(message = "{validation.user.common.value.notBlank}")
    @Pattern(regexp = RegexPattern.PASSWORD,
            message = "{validation.user.common.clientSecret.pattern}")
    private String clientSecret;

    private String resourceIds;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    @NotNull(message = "{validation.user.common.value.notNull}")
    private Integer accessTokenValidity;

    @NotNull(message = "{validation.user.common.value.notNull}")
    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;
}
