package com.rede.social.exception.profileException;

import com.rede.social.exception.AppException;

public class ProfileAlreadyDeactivatedException extends AppException {

    public ProfileAlreadyDeactivatedException(String message) {
        super(message);
    }
}
