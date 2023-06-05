package com.cvv.reggie.exception;

import com.cvv.reggie.common.R;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:
 */

public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
