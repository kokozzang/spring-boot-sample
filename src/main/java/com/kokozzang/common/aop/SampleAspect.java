package com.kokozzang.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class SampleAspect {

//	@Before("execution(* com.kokozzang.*.service.*.*Aop(..))")
	@Before("@annotation(sample)")
	public void before(JoinPoint joinPoint, Sample sample) {
		logger.info(":: Before Aspect ::");
	}

//	@After("execution(* com.kokozzang.*.*.service.*.*(..))")
	@After("@annotation(sample)")
	public void after(JoinPoint joinPoint, Sample sample) {
		logger.info(":: After Aspect ::");
	}

//	@AfterReturning(pointcut = "execution(* com.kokozzang.*.*.service.*.*(..))", returning = "result")
	@AfterReturning(pointcut = "@annotation(sample)", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result, Sample sample) {
		logger.info(":: AfterReturning Aspect : " + result);
	}

	@Around("@annotation(sample)")
	public Object around(ProceedingJoinPoint pjp, Sample sample) throws Throwable {
		logger.info(":: around before::");
		Object o = pjp.proceed();
		logger.info(":: around after::");

		return o;
	}

}
