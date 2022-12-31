package com.universityadmissions.dao;

public class DaoException extends Exception {
    public DaoException() {
        super();
    }

    public DaoException(String massage) {
        super(massage);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(String massage, Throwable cause) {
        super(massage, cause);
    }
}
