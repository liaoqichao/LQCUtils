package test.proxyfactory;

import lqcUtils.service.tx.Isolation;
import lqcUtils.service.tx.Propagation;
import lqcUtils.service.tx.Tx;

public interface BService {

	@Tx(propagation = Propagation.REQUIRED ,isolation = Isolation.READ_COMMITED)
	public void b();
}
