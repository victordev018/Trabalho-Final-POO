package com.rede.social.exception.profileException;

import com.rede.social.exception.AppException;

public class ProfileUnauthorizedError extends AppException {

    public ProfileUnauthorizedError(String message) {
        super(message);
    }
}
