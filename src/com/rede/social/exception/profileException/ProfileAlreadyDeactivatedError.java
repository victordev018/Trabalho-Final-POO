package com.rede.social.exception.profileException;

import com.rede.social.exception.AppException;

public class ProfileAlreadyDeactivatedError extends AppException {

    public ProfileAlreadyDeactivatedError(String message) {
        super(message);
    }
}
