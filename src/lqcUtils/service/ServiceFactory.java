package lqcUtils.service;

import java.lang.reflect.Field;
import java.sql.Connection;

import lqcUtils.db.JDBCUtils;
import lqcUtils.proxy.BeforeAdvice;
import lqcUtils.proxy.ExceptionAdvice;
import lqcUtils.proxy.FinallyAdvice;
import lqcUtils.proxy.ProxyFactory;
import lqcUtils.service.tx.TxBeforeAdvice;
import lqcUtils.service.tx.TxExceptionAdvice;
import lqcUtils.service.tx.TxFinallyAdvice;

public class ServiceFactory {
	
	/**
	 * ����֧������ķ������
	 * @param clazzz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object getTxService(Class clazzz){
//		return getTxService(clazzz,null);
		return getTxService(clazzz,JDBCUtils.getConnection());
	}
	
	/**
	 * .
	 * @param clazzz
	 * @param conn
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object getTxService(Class clazzz, Connection conn){
		try{
			ProxyFactory proxyFactory = new ProxyFactory();
			if(BaseService.class.isAssignableFrom(clazzz)){ // clazz��BaseService������
				BaseService serviceImpl = (BaseService) clazzz.newInstance();
				proxyFactory.setTargetObject(serviceImpl);
				
				// ���ô��������ݿ�����
				injectConnection(serviceImpl, conn);
						
				addTxBeforeAdvice(serviceImpl, proxyFactory);
				addTxExceptionAdvice(proxyFactory.getBeforeAdvice(), serviceImpl, proxyFactory);
				addTxFinallyAdvice(proxyFactory.getBeforeAdvice(), serviceImpl, proxyFactory);
				
				if(clazzz.getInterfaces() != null){
					return proxyFactory.createProxy();
				} else {
					throw new RuntimeException("�÷�����"+clazzz.getName()+"û��ʵ�ֽӿ�");
				}
			} else {
				throw new ServiceException("������������Ϊ"+clazzz.getClass().getName()+",û�м̳�BaseService");
			}
		} catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}
	
	private static void injectConnection(BaseService bs, Connection conn) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		if(conn!=null){
			Field field = bs.getClass().getSuperclass().getDeclaredField("conn");
			field.setAccessible(true);
			field.set(bs, conn);
		}
	}
	
	private static void addTxBeforeAdvice(BaseService serviceImpl, ProxyFactory proxyFactory){
		BeforeAdvice beforeAdvice = new TxBeforeAdvice();
		beforeAdvice.setResource(serviceImpl.getConnection()); // ������÷��Ѿ�������Ӧ�û�ȡ���÷�������
		proxyFactory.setBeforeAdvice(beforeAdvice);
	}
	
	private static void addTxExceptionAdvice(BeforeAdvice beforeAdvice, BaseService serviceImpl, ProxyFactory proxyFactory){
		ExceptionAdvice exceptionAdvice = new TxExceptionAdvice();
		exceptionAdvice.setResource(beforeAdvice.getResource());
		proxyFactory.setExceptionAdvice(exceptionAdvice);
	}
	
	private static void addTxFinallyAdvice(BeforeAdvice beforeAdvice, BaseService serviceImpl, ProxyFactory proxyFactory){
		FinallyAdvice finallyAdvice = new TxFinallyAdvice();
		finallyAdvice.setResource(beforeAdvice.getResource());
		proxyFactory.setFinallyAdvice(finallyAdvice);
	}
	
}
