package com.example.demo;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AopConfig {
	
	@Pointcut("execution(public * com.example.demo.controller..*(..))")
	private void mypoint() {
		
	}
	
	@Before("mypoint()")
	public void before() {
		System.out.println("타겟메소드가 동작하기 전");
	}
}
