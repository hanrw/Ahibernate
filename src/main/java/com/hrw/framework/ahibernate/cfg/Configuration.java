
package com.hrw.framework.ahibernate.cfg;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.xml.sax.InputSource;

import android.util.Log;

import com.hrw.framework.ahibernate.annotation.AnnotationReader;
import com.hrw.framework.ahibernate.annotation.Table;
import com.hrw.framework.ahibernate.exceptions.AhibernateException;
import com.hrw.framework.ahibernate.exceptions.MappingException;
import com.hrw.framework.ahibernate.mapping.Column;
import com.hrw.framework.ahibernate.util.ConfigHelper;
import com.hrw.framework.ahibernate.util.ReflectHelper;
import com.hrw.framework.ahibernate.util.XMLHelper;

public class Configuration {
    private static boolean DEBUG = true;

    private static Configuration configuration;

    protected transient XMLHelper xmlHelper;

    private transient Map<String, Class> entityPersisters;

    private ColumnsBuilder columnsBuilder;

    private static Logger LOG = Logger.getLogger(Configuration.class);

    public Map<String, Class> getEntityPersisters() {
        return entityPersisters;
    }

    public Class getEntityPersister(String key) {
        return entityPersisters.get(key);
    }

    /**
     * Use the mappings and properties specified in an application resource
     * named <tt>hibernate.cfg.xml</tt>.
     * 
     * @return this for method chaining
     * @throws HibernateException Generally indicates we cannot find
     *             <tt>hibernate.cfg.xml</tt>
     * @see #configure(String)
     */
    public Configuration configure() throws AhibernateException {
        reset();
        configure("/ahibernate.cfg.xml");
        return this;
    }

    private void reset() {
        xmlHelper = new XMLHelper();
        entityPersisters = new HashMap<String, Class>();
        columnsBuilder = new ColumnsBuilder();
    }

    public Configuration configure(String resource) throws AhibernateException {
        if (DEBUG) {
            LOG.info("configure resource:" + resource);
        }
        InputStream stream = getConfigurationInputStream(resource);
        return doConfigure(stream, resource);
    }

    protected InputStream getConfigurationInputStream(String resource) throws AhibernateException {
        if (DEBUG) {
            LOG.info("getConfigurationInputStream resource:" + resource);
        }
        return ConfigHelper.getResourceAsStream(resource);
    }

    protected Configuration doConfigure(InputStream stream, String resourceName)
            throws AhibernateException {
        try {
            List errors = new ArrayList();
            Document document = xmlHelper.createSAXReader(resourceName, errors).read(
                    new InputSource(stream));
            if (errors.size() != 0) {
                throw new MappingException("invalid configuration", (Throwable) errors.get(0));
            }
            doConfigure(document);
        } catch (DocumentException e) {
            throw new AhibernateException("Could not parse configuration: " + resourceName, e);
        } finally {
            try {
                stream.close();
            } catch (IOException ioe) {
                Log.e(resourceName, ioe.toString());
            }
        }
        return this;
    }

    protected Configuration doConfigure(Document doc) throws AhibernateException {
        Element sfNode = doc.getRootElement().element("mapping");
        Attribute classAttribute = sfNode.attribute("class");
        if (classAttribute != null) {
            final String className = classAttribute.getValue();
            try {
                Class clazz = ReflectHelper.classForName(className);
                if (clazz.isAnnotationPresent(Table.class)) {
                    addEntityPersister(ReflectHelper.classForName(className));
                    buildMappings(clazz);
                }
            } catch (Exception e) {
                throw new MappingException("Unable to load class [ " + className
                        + "] declared in Hibernate configuration <mapping/> entry", e);
            }

        }
        return this;
    }

    private void buildMappings(Class clazz) {
        buildColumns(clazz);
    }

    private void buildColumns(Class clazz) {
        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(com.hrw.framework.ahibernate.annotation.Column.class)
                    || f.isAnnotationPresent(com.hrw.framework.ahibernate.annotation.Id.class)) {
                AnnotationReader ar = new AnnotationReader(f);
                Column column = new Column();
                column.setName(ar.getAnnotationName());
                columnsBuilder.addColumn(column);
            }
        }

    }

    private void addEntityPersister(Class classForName) {
        entityPersisters.put(classForName.getName(), classForName);
    }

    public static Configuration getInstance() {
        if (configuration == null) {
            configuration = new Configuration();
        }
        return configuration;
    }

    public ColumnsBuilder getColumnBuilder() {
        return columnsBuilder;
    }
}
