package lqcUtils.beanFactory;

import java.sql.Connection;

public abstract class Component implements Getable{

	private static BeanFactory beanFactory;
	
	static {
		beanFactory = new BeanFactory("beanFactory/beans.xml");
	}
	
	public Object getBean(String id){
		return beanFactory.getBean(id);
	}
	public Object getBean(String id, Connection conn){
		return beanFactory.getBean(id,conn);
	}
	
}
