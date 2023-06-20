package com.toy.shop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.toy.shop.controller..*.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            //@Before
            log.info("[trace] {} args={}", joinPoint.getSignature(), joinPoint.getArgs());

            Object result = joinPoint.proceed();

            //@AfterReturning
            log.info("[trace] {} result={}", joinPoint.getSignature(), result);

            return result;
        } catch (Exception exception) {
            //@AfterThrowing
            log.error("[trace] {}", joinPoint.getSignature(), exception);

            throw exception;
        } finally {
            //@After
            log.info("[trace] {}", joinPoint.getSignature());
        }
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        log.info("[trace] {} args={}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @AfterReturning(value = "pointcut()", returning = "result")
    public void after(JoinPoint joinPoint, Object result) {
        log.info("[trace] {} result={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "pointcut()", throwing = "exception")
    public void doThrowing(JoinPoint joinPoint, Exception exception) {
        log.error("[trace] {}", joinPoint.getSignature(), exception);
    }

    @After(value = "pointcut()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[trace] {}", joinPoint.getSignature());
    }
}