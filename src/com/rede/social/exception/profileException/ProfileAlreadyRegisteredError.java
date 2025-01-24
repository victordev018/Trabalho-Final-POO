package com.rede.social.exception.profileException;

import com.rede.social.exception.AppException;

public class ProfileAlreadyRegisteredError extends AppException  {

    public ProfileAlreadyRegisteredError(String message) {
        super(message);
    }
}
