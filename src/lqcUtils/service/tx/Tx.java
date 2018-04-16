package lqcUtils.service.tx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD })
public @interface Tx {
	
	/**
	 * ����Ĵ������ԡ�ֻ�п�������Ͳ���������,Ĭ�Ͽ�������
	 * @return
	 */
	Propagation propagation() default Propagation.REQUIRED ;
	
	/**
	 * ����ĸ��뼶��,Ĭ�ϰ������ݿ��Ĭ��������뼶��
	 * @return
	 */
	Isolation isolation() default Isolation.DEFAULT;
	
}
