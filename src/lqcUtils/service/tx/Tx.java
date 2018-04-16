package lqcUtils.service.tx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD })
public @interface Tx {
	
	/**
	 * 事务的传播特性。只有开启事务和不开启事务,默认开启事务。
	 * @return
	 */
	Propagation propagation() default Propagation.REQUIRED ;
	
	/**
	 * 事务的隔离级别,默认按照数据库的默认事务隔离级别
	 * @return
	 */
	Isolation isolation() default Isolation.DEFAULT;
	
}
