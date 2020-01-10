//package com.kokozzang.sampleapp.common.aop;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class SampleAspect {
//
//	private static final Logger logger = LoggerFactory.getLogger(SampleAspect.class);
//
//	@Before("execution(* com.kokozzang.authorize.*.service.*.*Aop(..))")
//	public void before(JoinPoint joinPoint) {
//		logger.info(":: Before Aspect ::");
//	}
//
//	@After("execution(* com.kokozzang.authorize.*.service.*.*Aop(..))")
//	public void after(JoinPoint joinPoint) {
//		logger.info(":: After Aspect ::");
//	}
//
//	@AfterReturning(pointcut = "execution(* com.kokozzang.authorize.*.service.*.*(..))", returning = "result")
//	public void afterReturning(JoinPoint joinPoint, Object result) {
//		logger.info(":: AfterReturning Aspect : " + result);
//	}
//
//	@Around("@annotation(sample)")
//	public Object around(ProceedingJoinPoint pjp, Sample sample) throws Throwable {
//		logger.info(":: around ::");
//		return pjp.proceed();
//	}
//
//}
