package lqcUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate�Ĺ�����
 *
 */
public class HibernateUtils {

	private static SessionFactory sessionFactory;
	private static Configuration configuration;
//	private static SchemaExport schemaExport;
	static{
//		Configuration cfg = new Configuration();
//		cfg.configure();//Ĭ�϶�ȡ/src/hibernate.cfg.xml
//		cfg.configure("hibernate.cfg.xml");//��ȡָ��λ�õ������ļ�
//		sessionFactory = cfg.buildSessionFactory();
		//3�п�����һ�д���,һ��һ������������Ŀ�ע���Ƿ�ֹ��ʽ�������ʱ���ȥ
//		sessionFactory = new Configuration()//
//				.configure()//
//				.buildSessionFactory();
		configuration = new Configuration().configure();
		sessionFactory = configuration.buildSessionFactory();
//		schemaExport = new SchemaExport(configuration);
	}
	
	/**
	 * ��ȡȫ��Ψһ��sessionFactory��sessionFactory���̰߳�ȫ�ģ������ڶ��߳���ʹ�á�
	 * @return
	 */
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	
	/**
	 * ��ȫ��Ψһ��SessionFactory�п���һ��session
	 */
	public static Session openSession(){
		return sessionFactory.openSession();
	}
	
//	/**
//	 * �����������ɱ�ṹ����������Ĳ�����DDL���Ϳ��Բ��������ݿ��������ȡ�������ļ����������ļ���<mapping>�ҵ���Ӧ��javabean��
//	 * ���ӳ���ϵ��������������������Ĳ�����ת�Ƶ�java����+�����ļ�
//	 */
//	public static void createSchema(){
//		/*
//		 * ��һ���������Ƿ��ڿ���̨��ӡDDL
//		 * �ڶ����������Ƿ񵼳��ű������ݿ� 
//		 */
//		schemaExport.create(true, true);
//		/*
//		 * ��һ������:true��ʾDDL������������̨
//		 * �ڶ���������true��ʾ��ɾ���󴴽����൱�����ļ�����<property name="hbm2ddl"/>create</property>
//		 * ������������false��ʾ��ֻ��ִ��Drop���ʱ��ִ�д���������
//		 * ���ĸ������� true��ʾ��ʽ�����������̨
//		 */
////		schemaExport.execute(true, true, false, true);
//	}
	
//	/**
//	 * ��ȡ�������ļ������ݳ־û��ࣨjavabean����ӳ���ļ���ɾ�����ݿ��еı�ṹ
//	 */
//	public static void dropSchema(){
//		/*
//		 * ��һ���������Ƿ��ڿ���̨��ӡDDL
//		 * �ڶ����������Ƿ񵼳��ű������ݿ� 
//		 */
//		schemaExport.drop(true, true);
//	}
}
