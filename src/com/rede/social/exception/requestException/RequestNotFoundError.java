package com.rede.social.exception.requestException;

import com.rede.social.exception.AppException;

public class RequestNotFoundError extends AppException {

    public RequestNotFoundError(String message) {
        super(message);
    }
}
