package com.rede.social.exception.global;

import com.rede.social.exception.AppException;

public class AlreadyExistsError extends AppException {

    public AlreadyExistsError(String message) {
        super(message);
    }
}
