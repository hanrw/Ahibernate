
package com.hrw.framework.ahibernate.exceptions;

public class AhibernateException extends RuntimeException {
    public AhibernateException(String message) {
        super(message);
    }

    public AhibernateException(Throwable root) {
        super(root);
    }

    public AhibernateException(String message, Throwable root) {
        super(message, root);
    }
}
