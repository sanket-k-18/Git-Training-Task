package com.ignitiv.exception;

import com.kibocommerce.sdk.common.ApiException;

public class RateLimitException extends RuntimeException {

    private final ApiException apiException;

    public RateLimitException(ApiException cause) {
        super("API rate limit exceeded (429): " + cause.getMessage(), cause);
        this.apiException = cause;
    }

    public ApiException getApiException() {
        return apiException;
    }
}