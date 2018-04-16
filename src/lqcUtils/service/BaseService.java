package lqcUtils.service;

import java.sql.Connection;

import lqcUtils.beanFactory.Component;
import lqcUtils.db.JDBCUtils;

abstract public class BaseService extends Component implements Service{

//	private static DataSource dataSource;
//	private Connection conn;
//	
//	static {
//		initDataSource();
//	}
	
//	@Override
//	public Connection getConnection() {
//		try {
//			if(conn == null){
//				conn = dataSource.getConnection();
//			}
//			return conn;
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//	}
	
//	public static void initDataSource() {
//		JDBCUtils.getDataSource();
//		// 修改c3p0的配置文件的读取路径
//		if(dataSource != null) {
//			System.setProperty("com.mchange.v2.c3p0.cfg.xml",BaseService.class.getClassLoader().getResource("c3p0/c3p0-config.xml").getPath()); 
////			System.setProperty("com.mchange.v2.c3p0.cfg.xml","config/c3p0/c3p0-config.xml"); 
//			dataSource = new ComboPooledDataSource();
//			
//		}
//	}
	
	@Override
	public Connection getConnection() {
		return JDBCUtils.getConnection();
	}
	
}
