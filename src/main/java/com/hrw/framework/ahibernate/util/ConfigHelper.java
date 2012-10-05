
package com.hrw.framework.ahibernate.util;

import java.io.InputStream;

import android.os.Environment;

import com.hrw.framework.ahibernate.exceptions.AhibernateException;

public class ConfigHelper {


    public static InputStream getResourceAsStream(String resource) {
        String stripped = resource.startsWith("/") ? resource.substring(1) : resource;

        InputStream stream = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            stream = classLoader.getResourceAsStream(stripped);
        }
        if (stream == null) {
            stream = Environment.class.getResourceAsStream(resource);
        }
        if (stream == null) {
            stream = Environment.class.getClassLoader().getResourceAsStream(stripped);
        }
        if (stream == null) {
            throw new AhibernateException(resource + " not found");
        }
        return stream;
    }

}
