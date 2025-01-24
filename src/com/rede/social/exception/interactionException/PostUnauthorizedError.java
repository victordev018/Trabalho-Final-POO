package com.rede.social.exception.interactionException;

import com.rede.social.exception.AppException;

public class PostUnauthorizedError extends AppException {

    public PostUnauthorizedError(String message) {
        super(message);
    }
}
