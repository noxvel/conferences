package com.nextvoyager.conferences.util.recaptcha;

public class RecaptchaConfigException extends RuntimeException{
    public RecaptchaConfigException() {
        super();
    }

    public RecaptchaConfigException(String message) {
        super(message);
    }

    public RecaptchaConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecaptchaConfigException(Throwable cause) {
        super(cause);
    }
}
