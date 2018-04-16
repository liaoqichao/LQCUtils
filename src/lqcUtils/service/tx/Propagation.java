package lqcUtils.service.tx;

public enum Propagation {
	/**
	 * 如果当前已开启事务，则用当前事务；如果当前没有事务则开启事务
	 */
	REQUIRED,
	/**
	 * 不开启事务
	 */
	NOT_REQUIRED,
	/**
	 * 如果当前已开启事务，则用当前事务；如果当前没有开启事务，则不开启事务
	 */
	SUPPORTS,
	/**
	 * 如果当前已开启事务，则用当前事务；如果当前没有开始事务，则抛出异常
	 */
	MANDATORY
}
