package com.rede.social.exception.requestException;

import com.rede.social.exception.AppException;

public class FriendshipAlreadyExistsError extends AppException {

    public FriendshipAlreadyExistsError(String message) {
        super(message);
    }
}
