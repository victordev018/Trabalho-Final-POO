package com.rede.social.exception.profileException;

import com.rede.social.exception.AppException;

public class ProfileAlreadyActivatedException extends AppException {

    public ProfileAlreadyActivatedException(String message) {
        super(message);
    }
}
