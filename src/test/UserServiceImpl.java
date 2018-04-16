package test;

import java.util.List;

public class UserServiceImpl implements UserService {

	private int a ;
	private UserDao userDao; // null
//	private List<Integer> myList;
	
	@Override
	public void doSth() {
		System.out.println(a);
//		System.out.println(userDao == null);
		
		userDao.save(new Object());
		userDao.get(new Object());
//		
//		for (Integer integer : myList) {
//			System.out.println(integer);
//		}
	}

}
