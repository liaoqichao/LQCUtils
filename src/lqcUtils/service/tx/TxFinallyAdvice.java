package lqcUtils.service.tx;

import java.lang.reflect.Method;
import java.sql.Connection;

import lqcUtils.proxy.FinallyAdvice;

public class TxFinallyAdvice extends FinallyAdvice{

	@Override
	public void advice(Object proxy, Method method, Object[] args, Exception e) throws Throwable {
		// TODO Auto-generated method stub
		Connection conn = (Connection) this.getResource();
		if(conn != null && !conn.isClosed() && conn.getAutoCommit() == false){
			conn.commit();
		}
	}

}
