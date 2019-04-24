package a;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
//		Spring 容器，AnnocationConfigApplicationContext
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DiConfig.class);
		UseFunctionService useFunctionService = context.getBean(UseFunctionService.class);
		
		System.out.println(useFunctionService.SayHello("他明白他明白，我给不起。于是转身向山里走去"));
		context.close();
	}
}
