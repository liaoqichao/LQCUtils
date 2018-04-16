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
	 * 创建支持事务的服务对象
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
			if(BaseService.class.isAssignableFrom(clazzz)){ // clazz是BaseService的子类
				BaseService serviceImpl = (BaseService) clazzz.newInstance();
				proxyFactory.setTargetObject(serviceImpl);
				
				// 设置传来的数据库连接
				injectConnection(serviceImpl, conn);
						
				addTxBeforeAdvice(serviceImpl, proxyFactory);
				addTxExceptionAdvice(proxyFactory.getBeforeAdvice(), serviceImpl, proxyFactory);
				addTxFinallyAdvice(proxyFactory.getBeforeAdvice(), serviceImpl, proxyFactory);
				
				if(clazzz.getInterfaces() != null){
					return proxyFactory.createProxy();
				} else {
					throw new RuntimeException("该服务类"+clazzz.getName()+"没有实现接口");
				}
			} else {
				throw new ServiceException("传入对象的类型为"+clazzz.getClass().getName()+",没有继承BaseService");
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
		beforeAdvice.setResource(serviceImpl.getConnection()); // 如果调用方已经有连接应该获取调用方的连接
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
