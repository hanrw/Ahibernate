
package com.hrw.framework.ahibernate.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationReader {

    private static HashMap<Class, String> annotationToXml;

    private AnnotatedElement element;

    private String className;

    private String annotationName;
    private String annotationValue;

    public String getPropertyName() {
        return propertyName;
    }

    public String getAnnotationName() {
        return annotationName;
    }

    public String getAnnotationValue() {
        return annotationValue;
    }

    private String propertyName;

    private transient Map<Class, Annotation> annotationsMap;

    private transient Annotation[] annotations;

    private PropertyType propertyType;

    private AccessibleObject mirroredAttribute;

    static {
        annotationToXml = new HashMap<Class, String>();
        annotationToXml.put(Table.class, "table");
        annotationToXml.put(Id.class, "id");
        annotationToXml.put(Column.class, "column");

    }

    private enum PropertyType {
        PROPERTY, FIELD, METHOD
    }

    public AnnotationReader(AnnotatedElement el) {
        this.element = el;
        if (el instanceof Class) {
            Class clazz = (Class) el;
            className = clazz.getName();
        } else if (el instanceof Field) {
            if (el.isAnnotationPresent(Id.class)) {
                Id id = el.getAnnotation(Id.class);
                annotationName = id.name();
            }
            if (el.isAnnotationPresent(Column.class)) {
                Column column = el.getAnnotation(Column.class);
                annotationName = column.name();
            }
            Field field = (Field) el;
            className = field.getDeclaringClass().getName();
            propertyName = field.getName();
            propertyType = PropertyType.FIELD;
            String expectedGetter = "get" + Character.toUpperCase(propertyName.charAt(0))
                    + propertyName.substring(1);
            try {
                mirroredAttribute = field.getDeclaringClass().getDeclaredMethod(expectedGetter);
            } catch (NoSuchMethodException e) {
                // no method
            }
        }
    }

    // public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
    // initAnnotations();
    // return (T) annotationsMap.get(annotationType);
    // }

    public Annotation[] getAnnotations() {
        return element.getAnnotations();
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        return element.getAnnotation(annotationType);
    }

    public <T extends Annotation> boolean isAnnotationPresent(Class<T> annotationType) {
        return element.isAnnotationPresent(annotationType);
    }

    /**
     * Adds {@code annotation} to the list (only if it's not null) and then
     * returns it.
     * 
     * @param annotationList The list of annotations.
     * @param annotation The annotation to add to the list.
     * @return The annotation which was added to the list or {@code null}.
     */
    public Annotation addIfNotNull(List<Annotation> annotationList, Annotation annotation) {
        if (annotation != null) {
            annotationList.add(annotation);
        }
        return annotation;
    }

    // public void initAnnotations() {
    // if (annotations == null) {
    // // is a class
    // if (className != null && propertyName == null) {
    // Annotation[] annotations = getAnnotations();
    // annotationsMap = new HashMap<Class, Annotation>(annotations.length + 5);
    // for (Annotation annotation : annotations) {
    // if (annotationToXml.containsKey(annotation.annotationType())) {
    // annotationsMap.put(annotation.annotationType(), annotation);
    // }
    // }
    //
    // } else {
    // this.annotations = getAnnotations();
    // annotationsMap = new HashMap<Class, Annotation>(annotations.length + 5);
    // for (Annotation annotation : annotations) {
    // if (annotationToXml.containsKey(annotation.annotationType())) {
    // annotationsMap.put(annotation.annotationType(), annotation);
    // }
    // }
    // }
    // }
    // }

}
