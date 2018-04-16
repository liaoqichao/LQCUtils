package lqcUtils.beanFactory;

import java.sql.Connection;

public interface Getable {

	public Object getBean(String id);
	public Object getBean(String id, Connection conn);
}
