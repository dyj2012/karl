package com.karl.core.auth.api.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author karl
 */
@Data
@Accessors(chain = true)
public class SignInData {
    @NotBlank
    private String loginName;
    @NotBlank
    private String password;
}
