package com.xenoage.utils.base.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Annotation for untested code.
 * 
 * While a lot of code in a project may not be covered
 * by tests, code marked with this annotation is especially
 * worth testing.
 *
 * @author Andreas Wenger
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Untested
{
}
