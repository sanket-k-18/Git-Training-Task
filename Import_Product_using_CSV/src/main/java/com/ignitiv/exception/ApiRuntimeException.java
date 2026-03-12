package com.ignitiv.exception;

import com.kibocommerce.sdk.common.ApiException;

public class ApiRuntimeException extends RuntimeException {

    public ApiRuntimeException(ApiException cause) {
        super(cause.getMessage(), cause);
    }

    @Override
    public ApiException getCause() {
        return (ApiException) super.getCause();
    }
}