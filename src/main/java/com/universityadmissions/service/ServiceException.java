package com.universityadmissions.service;

public class ServiceException extends Exception {
    public ServiceException() {
        super();
    }

    public ServiceException(String massage) {
        super(massage);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
