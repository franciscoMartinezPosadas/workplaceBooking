package org.booking.aop.logging;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.booking.utils.LogUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * Aspect for logging execution of service and repository Spring components.
 */
@Aspect
public class LoggingAspect {
	
	@Value("${spring.profiles.active}")
	private String serviceName;

	@Value("${aspect.logging.trace.url}")
	private String loggingServiceUrl;
	
	@Value("${aspect.logging.error.msg}")
	private String errorMsg;
	
	private static HttpHeaders headers = new HttpHeaders(); 
	
	private static final Logger log = Logger.getLogger(LoggingAspect.class);
	
	static{
    	headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
	}

    @Pointcut("execution(public * org.booking.controllers.*.*(..))")
    public void loggingPointcut() {}

    @AfterThrowing(pointcut = "loggingPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
    	RestTemplate restTemplate = new RestTemplate();

		String trace = (new StringBuffer())
				.append("Exception in ")
				.append(joinPoint.getSignature().getDeclaringTypeName())
				.append(".")
				.append(joinPoint.getSignature().getName())
				.append("() with cause = ")
				.append(e.getCause()).toString();
		LogUnit logUnit = new LogUnit(trace,serviceName,Priority.ERROR_INT);
        HttpEntity<LogUnit> entity = new HttpEntity<LogUnit>(logUnit, headers);
    	try{
	    	restTemplate.exchange(loggingServiceUrl, HttpMethod.POST, entity, LogUnit.class);
    	}catch(Exception e1){
    		log.error(errorMsg + e);
    	}
    }

    @Around("loggingPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    	RestTemplate restTemplate = new RestTemplate();
		String trace = (new StringBuffer())
				.append("Enter: ")
				.append(joinPoint.getSignature().getDeclaringTypeName())
				.append(".")
				.append(joinPoint.getSignature().getName())
				.append("() with argument[s] = ")
				.append(Arrays.toString(joinPoint.getArgs())).toString();
		LogUnit logUnit = new LogUnit(trace,serviceName,Priority.DEBUG_INT);
		HttpEntity<LogUnit> entity = new HttpEntity<LogUnit>(logUnit, headers);
    	try{
			restTemplate.exchange(loggingServiceUrl, HttpMethod.POST, entity, LogUnit.class);
    	}catch(Exception e){
    		log.error(errorMsg + e);
    	}
        
        try {
            Object result = joinPoint.proceed();
        	
            trace = (new StringBuffer())
            		.append("Exit: ")
            		.append(joinPoint.getSignature().getDeclaringTypeName())
            		.append(".")
            		.append(joinPoint.getSignature().getName())
            		.append("() with result = ")
            		.append(result).toString();
			logUnit.setTrace(trace);
			logUnit.setLogLevel(Priority.DEBUG_INT);
        	entity = new HttpEntity<LogUnit>(logUnit, headers);
        	try{
				restTemplate.exchange(loggingServiceUrl, HttpMethod.POST, entity, LogUnit.class);
        	}catch(Exception e){
        		log.error(errorMsg + e);
        	}

            return result;
        } catch (IllegalArgumentException e) {

            trace = (new StringBuffer())
            		.append("Illegal argument: ")
            		.append(Arrays.toString(joinPoint.getArgs()))
            		.append("in ")
            		.append(joinPoint.getSignature().getDeclaringTypeName())
            		.append(".")
            		.append(joinPoint.getSignature().getName()).toString();
			logUnit.setTrace(trace);
			logUnit.setLogLevel(Priority.DEBUG_INT);
        	entity = new HttpEntity<LogUnit>(logUnit, headers);
        	try{
				restTemplate.exchange(loggingServiceUrl, HttpMethod.POST, entity, LogUnit.class);
        	}catch(Exception e1){
        		log.error(errorMsg + e1);
        	}

            throw e;
        }
    }
    
}
