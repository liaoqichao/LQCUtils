package lqcUtils.service.tx;

import java.lang.reflect.Method;
import java.sql.Connection;

import lqcUtils.proxy.ExceptionAdvice;

public class TxExceptionAdvice extends ExceptionAdvice{

	@Override
	public void advice(Object proxy, Method method, Object[] args, Exception e) throws Throwable {
		// TODO Auto-generated method stub
		
		e.printStackTrace(); // ��ӡ������Ϣ
		Connection conn = (Connection) this.getResource();
		if(conn!=null && !conn.isClosed() && conn.getAutoCommit() == false){ // ���Ӳ�Ϊ��������δ�ر��ҿ���������
			conn.rollback();
		}
	}

}
