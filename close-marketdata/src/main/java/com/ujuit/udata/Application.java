package com.ujuit.udata;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description: TODO(启动类)
 * @author luyang
 * @date 2017年4月24日
 *
 */
public class Application {
	public static boolean isStart = false;
	public static ApplicationContext context;

	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("classpath*:springContext.xml",
				"classpath*:spring-mybatisContext.xml");
	}
}
