package a;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
//		Spring ������AnnocationConfigApplicationContext
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DiConfig.class);
		UseFunctionService useFunctionService = context.getBean(UseFunctionService.class);
		
		System.out.println(useFunctionService.SayHello("�����������ף��Ҹ���������ת����ɽ����ȥ"));
		context.close();
	}
}
