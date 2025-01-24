package com.rede.social.exception.global;

import com.rede.social.exception.AppException;

public class InvalidInputError extends AppException {

    public InvalidInputError(String message) {
        super(message);
    }
}
