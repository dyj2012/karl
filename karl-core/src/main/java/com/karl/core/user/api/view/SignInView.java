package com.karl.core.user.api.view;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Think
 */
@Data
@Accessors(chain = true)
public class SignInView {
    private String accessToken;
    private String refreshToken;
}