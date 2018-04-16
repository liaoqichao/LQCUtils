package test.proxyfactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lqcUtils.service.BaseService;

public class AServiceImpl extends BaseService implements AService {

//	private ADao dao = new ADao(this.getConnection());
	
//	private BService bService = (BService) new ServiceFactory().getTxService(BServiceImpl.class,this.getConnection());
	private BService bService = (BService) this.getBean("bService",this.getConnection());
	
	public void service() {
		// TODO Auto-generated method stub
		System.out.println("A service : " + this.getConnection());
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from ks";
			ps = this.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				String id = rs.getString(1);
				System.out.println(id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				if(rs!=null){
					rs.close();
				}
				if(ps!=null){
					ps.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.bService.b();
	}

}
