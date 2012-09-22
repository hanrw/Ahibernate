
package com.hrw.framework.ahibernate.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationReader {

    private static HashMap<Class, String> annotationToXml;
    private AnnotatedElement element;
    private String className;
    private String propertyName;
    private transient Map<Class, Annotation> annotationsMap;
    private transient Annotation[] annotations;

    static {
        annotationToXml = new HashMap<Class, String>();
        annotationToXml.put(Table.class, "table");
    }

    public AnnotationReader(AnnotatedElement el) {
        this.element = el;
        if (el instanceof Class) {
            Class clazz = (Class) el;
            className = clazz.getName();
        }
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        initAnnotations();
        return (T) annotationsMap.get(annotationType);
    }

    public Annotation[] getJavaAnnotations() {
        return element.getAnnotations();
    }

    private <T extends Annotation> T getJavaAnnotation(Class<T> annotationType) {
        return element.getAnnotation(annotationType);
    }

    /**
     * Adds {@code annotation} to the list (only if it's not null) and then
     * returns it.
     * 
     * @param annotationList The list of annotations.
     * @param annotation The annotation to add to the list.
     * @return The annotation which was added to the list or {@code null}.
     */
    private Annotation addIfNotNull(List<Annotation> annotationList, Annotation annotation) {
        if (annotation != null) {
            annotationList.add(annotation);
        }
        return annotation;
    }

    private void initAnnotations() {
        if (annotations == null) {
            if (className != null) {
                Annotation[] annotations = getJavaAnnotations();
                List<Annotation> annotationList = new ArrayList<Annotation>(annotations.length + 5);
                annotationsMap = new HashMap<Class, Annotation>(annotations.length + 5);
                for (Annotation annotation : annotations) {
                    if (!annotationToXml.containsKey(annotation.annotationType())) {
                        // unknown annotations are left over
                        annotationList.add(annotation);
                    }
                }

            }
        }
    }

}
