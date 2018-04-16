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
		
		setConnectionAutoCommit(conn, method); // ��������Ĵ������ԣ�ֻ�п�������͹ر�����
		
		if(!conn.getAutoCommit()){ // �������������,��������ĸ��뼶��
			setTxIsolation(conn, method);
		}
		
	}
	
	private void setConnectionAutoCommit(Connection conn, Method method) throws SQLException, TxException{
		if(conn != null){
			if(method.getAnnotation(Tx.class) != null){
				if(method.getAnnotation(Tx.class).propagation().name().equals(Propagation.REQUIRED.toString())){ 
					if(conn.getAutoCommit()){ // �������������ʹ�õ�ǰ����,�����������������������
						conn.setAutoCommit(false); //��������
					}
				} else if (method.getAnnotation(Tx.class).propagation().name().equals(Propagation.NOT_REQUIRED.toString())){
					conn.setAutoCommit(true); //�ر�����
				} else if (method.getAnnotation(Tx.class).propagation().name().equals(Propagation.SUPPORTS.toString())) {
//					if(conn.getAutoCommit()){} // ���û�п��������򲻿�������
					// �������������ʹ�õ�ǰ����
				} else if (method.getAnnotation(Tx.class).propagation().name().equals(Propagation.MANDATORY.toString())){
					if(conn.getAutoCommit()){
						throw new TxException("��ǰ����Ĵ�������ΪMANDATORY����ǰû�п��������׳��쳣��");
					}
				} else{
					throw new TxException("propagation����û��"+method.getAnnotation(Tx.class).propagation().name()+"ע��ֵ");
				}
			} else{ // û��ʹ��Txע�⣬Ĭ�Ͽ�ʼ����
				if(conn.getAutoCommit()){
					conn.setAutoCommit(false);
				}
			}
		}
	}
	
	private void setTxIsolation(Connection conn, Method method) throws SQLException, TxException{
		if(conn != null){
			if(method.getAnnotation(Tx.class) != null){ // isolation������Ĭ��ֵ������Ҫ�ж�isolation�Ƿ�Ϊ��
				if(method.getAnnotation(Tx.class).isolation().name().//
						equals(Isolation.READ_UNCOMMITED.toString())){			// ��δ�ύ
					conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
				} else if(method.getAnnotation(Tx.class).isolation().name().//
						equals(Isolation.READ_COMMITED.toString())){	// �����ύ
					conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
				} else if(method.getAnnotation(Tx.class).isolation().name().//
						equals(Isolation.REPEATABLE_READ.toString())){	// ���ظ���
					conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
				} else if(method.getAnnotation(Tx.class).isolation().name().//
						equals(Isolation.SERIALIZABLE.toString())){	// ���л�
					conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				} else if(method.getAnnotation(Tx.class).isolation().name().//
						equals(Isolation.DEFAULT.toString())){	// Ĭ�ϣ��������ݿ��Ĭ�ϸ��뼶��
				} else {
					throw new TxException("����ĸ��뼶��ע�����,û��"+method.getAnnotation(Tx.class).isolation().name()+"ö��");
				}
			}
		}
	}
	
}
