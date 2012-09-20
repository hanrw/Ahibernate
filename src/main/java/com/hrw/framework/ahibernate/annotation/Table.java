
package com.hrw.framework.ahibernate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies the primary table for the annotated entity.
 * <p/>
 * If no <code>Table</code> annotation is specified for an entity class, the
 * class name apply.
 * 
 * <pre>
 *    Example:
 * 
 *    &#064;Entity
 *    &#064;Table(name="CUST")
 *    public class Customer { ... }
 * </pre>
 * 
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface Table {
    /**
     * (Optional) The name of the table.
     * <p/>
     * Defaults to the entity name.
     */
    String name() default "";
}
