package lqcUtils.service.tx;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import lqcUtils.proxy.BeforeAdvice;

public class TxBeforeAdvice extends BeforeAdvice{

	@Override
	public void advice(Object proxy, Method method, Object[] args, Exception e) throws Throwable {
		// TODO Auto-generated method stub
		Connection conn = (Connection) this.getResource();
		
		setConnectionAutoCommit(conn, method); // 设置事务的传播特性，只有开启事务和关闭事务
		
		if(!conn.getAutoCommit()){ // 如果开启了事务,设置事务的隔离级别
			setTxIsolation(conn, method);
		}
		
	}
	
	private void setConnectionAutoCommit(Connection conn, Method method) throws SQLException, TxException{
		if(conn != null){
			if(method.getAnnotation(Tx.class) != null){
				if(method.getAnnotation(Tx.class).propagation().name().equals(Propagation.REQUIRED.toString())){ 
					if(conn.getAutoCommit()){ // 如果存在事务则使用当前事务,如果不存在事务则开启新事务
						conn.setAutoCommit(false); //开启事务
					}
				} else if (method.getAnnotation(Tx.class).propagation().name().equals(Propagation.NOT_REQUIRED.toString())){
					conn.setAutoCommit(true); //关闭事务
				} else if (method.getAnnotation(Tx.class).propagation().name().equals(Propagation.SUPPORTS.toString())) {
//					if(conn.getAutoCommit()){} // 如果没有开启事务则不开启事务
					// 如果存在事务则使用当前事务
				} else if (method.getAnnotation(Tx.class).propagation().name().equals(Propagation.MANDATORY.toString())){
					if(conn.getAutoCommit()){
						throw new TxException("当前事务的传播特性为MANDATORY，当前没有开启事务抛出异常。");
					}
				} else{
					throw new TxException("propagation属性没有"+method.getAnnotation(Tx.class).propagation().name()+"注解值");
				}
			} else{ // 没有使用Tx注解，默认开始事务
				if(conn.getAutoCommit()){
					conn.setAutoCommit(false);
				}
			}
		}
	}
	
	private void setTxIsolation(Connection conn, Method method) throws SQLException, TxException{
		if(conn != null){
			if(method.getAnnotation(Tx.class) != null){ // isolation设置了默认值，不需要判断isolation是否为空
				if(method.getAnnotation(Tx.class).isolation().name().//
						equals(Isolation.READ_UNCOMMITED.toString())){			// 读未提交
					conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
				} else if(method.getAnnotation(Tx.class).isolation().name().//
						equals(Isolation.READ_COMMITED.toString())){	// 读已提交
					conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
				} else if(method.getAnnotation(Tx.class).isolation().name().//
						equals(Isolation.REPEATABLE_READ.toString())){	// 可重复读
					conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
				} else if(method.getAnnotation(Tx.class).isolation().name().//
						equals(Isolation.SERIALIZABLE.toString())){	// 序列化
					conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				} else if(method.getAnnotation(Tx.class).isolation().name().//
						equals(Isolation.DEFAULT.toString())){	// 默认，按照数据库的默认隔离级别
				} else {
					throw new TxException("事务的隔离级别注解错误,没有"+method.getAnnotation(Tx.class).isolation().name()+"枚举");
				}
			}
		}
	}
	
}
