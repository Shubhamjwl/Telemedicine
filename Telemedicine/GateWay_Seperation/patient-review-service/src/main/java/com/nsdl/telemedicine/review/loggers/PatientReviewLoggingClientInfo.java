/**
 * 
 */
package com.nsdl.telemedicine.review.loggers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Pegasus_girishk
 *
 */
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface PatientReviewLoggingClientInfo {

}
