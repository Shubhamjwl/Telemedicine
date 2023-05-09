/**
 * 
 */
package com.nsdl.telemedicine.review.loggers;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Pegasus_girishk
 *
 */
@Aspect
@Component
public class PatientReviewLoggingAspect {
	private static final Logger LOGGER = LogManager.getLogger(PatientReviewLoggingAspect.class);
	 
    //AOP expression for which methods shall be intercepted
    @Around("execution(* com.nsdl.telemedicine.review..*(..)))")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
         
        //Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
         
        final StopWatch stopWatch = new StopWatch();
         
        //Measure method execution time
        stopWatch.start();
        LOGGER.info("Start of " + className + "." + methodName );
        Object result = proceedingJoinPoint.proceed();
        LOGGER.info("END of " + className + "." + methodName );
        stopWatch.stop();
 
        //Log method execution time
        LOGGER.info("Execution time of " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis() + " ms");
 
        return result;
    }
    
    
    
    
    @Around("@within(PatientReviewLoggingClientInfo) || @annotation(PatientReviewLoggingClientInfo)")
    public Object logPerformance(ProceedingJoinPoint pjp) throws Throwable {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

    		String remoteAddr = "";
    		
        	if (request != null) {
    			remoteAddr = request.getHeader("X-FORWARDED-FOR");
    			if (remoteAddr == null || "".equals(remoteAddr)) {
    				remoteAddr = request.getRemoteAddr();
    			}
    		}         

            MDC.put("clientIp", remoteAddr);

        } catch (Exception e) {
            MDC.clear();
        }
        return pjp.proceed();
    }
}
