package com.rede.social.exception.profileException;

import com.rede.social.exception.AppException;

public class ProfileAlreadyActivatedError extends AppException {

    public ProfileAlreadyActivatedError(String message) {
        super(message);
    }
}
