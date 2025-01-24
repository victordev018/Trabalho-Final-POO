package com.rede.social.exception.interactionException;

import com.rede.social.exception.AppException;

public class InteractionDuplicatedError extends AppException {

    public InteractionDuplicatedError(String message) {
        super(message);
    }
}
