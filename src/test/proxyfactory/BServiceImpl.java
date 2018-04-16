package test.proxyfactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lqcUtils.service.BaseService;

public class BServiceImpl extends BaseService implements BService {

	public void b() {
		System.out.println("B service : " + this.getConnection());
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from ks order by _id limit 1";
			ps = this.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				String id = rs.getString("_id");
				String name = rs.getString("_name");
				System.out.println(id + " : " + name);
			}
		} catch (Exception e) {
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
	}

}
