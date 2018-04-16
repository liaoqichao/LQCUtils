package lqcUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate的工具类
 *
 */
public class HibernateUtils {

	private static SessionFactory sessionFactory;
	private static Configuration configuration;
//	private static SchemaExport schemaExport;
	static{
//		Configuration cfg = new Configuration();
//		cfg.configure();//默认读取/src/hibernate.cfg.xml
//		cfg.configure("hibernate.cfg.xml");//读取指定位置的配置文件
//		sessionFactory = cfg.buildSessionFactory();
		//3行可以用一行代替,一行一个方法，后面的空注释是防止格式化代码的时候回去
//		sessionFactory = new Configuration()//
//				.configure()//
//				.buildSessionFactory();
		configuration = new Configuration().configure();
		sessionFactory = configuration.buildSessionFactory();
//		schemaExport = new SchemaExport(configuration);
	}
	
	/**
	 * 获取全局唯一的sessionFactory，sessionFactory是线程安全的，可以在多线程下使用。
	 * @return
	 */
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	
	/**
	 * 从全局唯一的SessionFactory中开启一个session
	 */
	public static Session openSession(){
		return sessionFactory.openSession();
	}
	
//	/**
//	 * 根据配置生成表结构。这样建表的操作（DDL）就可以不用在数据库操作。读取主配置文件，主配置文件的<mapping>找到对应的javabean与
//	 * 表的映射关系，来创建表。这样创建表的操作就转移到java程序+配置文件
//	 */
//	public static void createSchema(){
//		/*
//		 * 第一个参数：是否在控制台打印DDL
//		 * 第二个参数：是否导出脚本到数据库 
//		 */
//		schemaExport.create(true, true);
//		/*
//		 * 第一个参数:true表示DDL语句输出到控制台
//		 * 第二个参数：true表示先删除后创建，相当于主文件配置<property name="hbm2ddl"/>create</property>
//		 * 第三个参数：false表示不只是执行Drop语句时候还执行创建操作。
//		 * 第四个参数： true表示格式化输出到控制台
//		 */
////		schemaExport.execute(true, true, false, true);
//	}
	
//	/**
//	 * 读取主配置文件。根据持久化类（javabean）和映射文件，删除数据库中的表结构
//	 */
//	public static void dropSchema(){
//		/*
//		 * 第一个参数：是否在控制台打印DDL
//		 * 第二个参数：是否导出脚本到数据库 
//		 */
//		schemaExport.drop(true, true);
//	}
}
