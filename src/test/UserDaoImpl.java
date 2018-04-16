package test;

public class UserDaoImpl implements UserDao {

	/* (non-Javadoc)
	 * @see test.UserDao#save(java.lang.Object)
	 */
	public void save(Object obj){
		System.out.println("userDaoImpl#save");
	}
	
	/* (non-Javadoc)
	 * @see test.UserDao#get(java.lang.Object)
	 */
	public Object get(Object id){
		System.out.println("userDaoImpl#get");
		return new Object();
	}
}
