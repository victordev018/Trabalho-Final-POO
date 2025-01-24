package com.rede.social.exception.global;

import com.rede.social.exception.AppException;

public class NotFoundError extends AppException {

    public NotFoundError(String message) {
        super(message);
    }
}
