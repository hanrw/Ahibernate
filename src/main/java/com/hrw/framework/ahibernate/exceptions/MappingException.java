
package com.hrw.framework.ahibernate.exceptions;

public class MappingException extends AhibernateException {

    public MappingException(String msg, Throwable root) {
        super(msg, root);
    }

    public MappingException(Throwable root) {
        super(root);
    }

    public MappingException(String s) {
        super(s);
    }

}
