
package com.hrw.framework.ahibernate.cfg;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.hrw.framework.ahibernate.exceptions.AhibernateException;
import com.hrw.framework.ahibernate.exceptions.MappingException;
import com.hrw.framework.ahibernate.mapping.Column;
import com.hrw.framework.ahibernate.mapping.Table;
import com.hrw.framework.ahibernate.util.ConfigHelper;
import com.hrw.framework.ahibernate.util.ReflectHelper;
import com.hrw.framework.ahibernate.util.XMLHelper;

public class Configuration {
    private static boolean DEBUG = true;

    private static Configuration configuration;

    protected transient XMLHelper xmlHelper;

    private transient Map<String, Class> entityPersisters;

    private Map<String, Table> tables;

    private Logger mLog;

    public Map<String, Class> getEntityPersisters() {
        return entityPersisters;
    }

    // public Logger getLog() {
    // return mLog;
    // }

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
        // only for android need do this
        // Log4jHelper.configureLog4j();
        mLog = Logger.getLogger(Configuration.class);
        reset();
        configure("/ahibernate.cfg.xml");
        return this;
    }

    private void reset() {
        xmlHelper = new XMLHelper();
        entityPersisters = new HashMap<String, Class>();
        tables = new HashMap<String, Table>();
    }

    public Configuration configure(String resource) throws AhibernateException {
        if (DEBUG) {
            mLog.info("configure resource:" + resource);
        }
        InputStream stream = getConfigurationInputStream(resource);
        return doConfigure(stream, resource);
    }

    protected InputStream getConfigurationInputStream(String resource) throws AhibernateException {
        if (DEBUG) {
            // mLog.info("getConfigurationInputStream resource:" + resource);
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
        Element rootElement = doc.getRootElement();
        Iterator elements = rootElement.elementIterator();
        while (elements.hasNext()) {
            Element subelement = (Element) elements.next();
            String subelementName = subelement.getName();
            if ("mapping".equals(subelementName)) {
                Attribute classAttribute = subelement.attribute("class");
                if (classAttribute != null) {
                    String className = classAttribute.getValue();
                    try {
                        Class clazz = ReflectHelper.classForName(className);
                        if (clazz
                                .isAnnotationPresent(com.hrw.framework.ahibernate.annotation.Table.class)) {
                            addEntityPersister(ReflectHelper.classForName(className));
                            addTable(className, clazz);
                        }
                    } catch (Exception e) {
                        throw new MappingException("Unable to load class [ " + className
                                + "] declared in Hibernate configuration <mapping/> entry", e);
                    }

                }
            }
        }
        return this;
    }

    private void addTable(String className, Class clazz) {
        Table table = TableBuilder.build(clazz);
        table = buildColumnsWithTable(table, clazz);
        tables.put(className, table);
    }

    private Table buildColumnsWithTable(Table table, Class clazz) {
        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(com.hrw.framework.ahibernate.annotation.Column.class)
                    || f.isAnnotationPresent(com.hrw.framework.ahibernate.annotation.Id.class)) {
                Column column = new Column();
                AnnotationReader ar = new AnnotationReader(f);
                column.setFieldName(f.getName());
                column.setName(ar.getAnnotationName());
                table.addColumn(f.getName(), column);
                if (f.isAnnotationPresent(com.hrw.framework.ahibernate.annotation.Id.class)) {
                    table.setIdentifierName(ar.getAnnotationName());
                }
            }
        }
        return table;
    }

    private void addEntityPersister(Class clazz) {
        entityPersisters.put(clazz.getName(), clazz);
    }

    public static Configuration getInstance() {
        if (configuration == null) {
            configuration = new Configuration();
        }
        return configuration;
    }

    public Map<String, Table> getTables() {
        return tables;
    }

    public Table getTable(String className) {
        return tables.get(className);
    }

}
