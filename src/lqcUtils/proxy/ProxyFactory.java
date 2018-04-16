package lqcUtils.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ���������������ɴ������
 * ����Ҫ���еĲ�����
 * 1.Ŀ�����
 * 2.��ǿ����Ϊǰ����ǿ�ͺ�����ǿ
 * 
 * ʹ�ã�
 * 1.����������
 * 2.���������ò���
 * 	Ŀ�����setTargetObject
 *  ǰ����ǿ��setBeforeAdvice(BeforeAdvice�ӿڵ�ʵ��)
 *  ������ǿ��setAfterAdvice(�ýӿڵ�ʵ��)
 *  �쳣��ǿ��������ǿ��������ǿ.
 * 3.����createProxy()�õ��������
 * 	* ִ�д�����󷽷�ʱ��
 * 		> ִ�е�BeforeAdvcie#before
 *  	> Ŀ������Ŀ�귽��
 *  	> ִ��AfterAdvice#after
 */
public class ProxyFactory {
	private Object targetObject; // Ŀ�����
	private BeforeAdvice beforeAdvice; // ǰ����ǿ
	private AfterAdvice afterAdvice; // ������ǿ
	private ExceptionAdvice exceptionAdvice; // �쳣��ǿ
	private FinallyAdvice finallyAdvice; // ������ǿ
	private BeforeReturnAdvice beforeReturnAdvice; // ������ǿ 
	
	/**
	 * �������ɴ������
	 * @return �������
	 */
	public Object createProxy(){
		/*
		 * 1.�������������ClassLoader��Interface[]��InvocationHandler
		 */
		ClassLoader loader = this.getClass().getClassLoader();
		@SuppressWarnings("rawtypes")
		Class[] interfaces = targetObject.getClass().getInterfaces();//��ȡĿ���������͵�����ʵ�ֽӿ�
		InvocationHandler h = new InvocationHandler(){

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				/*
				 * ���ô������ķ���ʱ��ִ����������ݡ�
				 */
				Object obj = null;
				try {
					//ִ��ǰ����ǿ
					if(beforeAdvice != null){
						beforeAdvice.advice(proxy, method, args, null);
					}
					obj = method.invoke(targetObject, args);//����Ŀ������Ŀ�귽��
					//ִ�к�����ǿ
					if(afterAdvice != null){
						afterAdvice.advice(proxy, method, args, null);
					}
				} catch (Exception e) {
					// TODO: handle exception
					// �쳣��ǿ
					if(exceptionAdvice != null){
						exceptionAdvice.advice(proxy, method, args, e);
					} else{
						System.err.println("exceptionAdvice is null.");
						e.printStackTrace();
					}
				} finally{
					// ������ǿ
					if(finallyAdvice != null)
						finallyAdvice.advice(proxy, method, args, null);
				}
				// ������ǿ
				if(beforeReturnAdvice != null)
					beforeReturnAdvice.advice(proxy, method, args, null);
				//����Ŀ�����ķ���ֵ
				return obj;
				
			}
			
		};
		/*
		 * 2.�õ��������
		 */
		Class<?> clazz = (Class<?>) targetObject.getClass();
		if(clazz.getInterfaces() != null){
			return Proxy.newProxyInstance(loader, interfaces, h);
		} else {
			System.err.println("Ŀ�����û��ʵ�ֽӿڣ�����Ŀ�����");
			return targetObject;
		}
	}
	public Object getTargetObject() {
		return targetObject;
	}
	public void setTargetObject(Object targetObject) {
		this.targetObject = targetObject;
	}
	public BeforeAdvice getBeforeAdvice() {
		return beforeAdvice;
	}
	public void setBeforeAdvice(BeforeAdvice beforeAdvice) {
		this.beforeAdvice = beforeAdvice;
	}
	public AfterAdvice getAfterAdvice() {
		return afterAdvice;
	}
	public void setAfterAdvice(AfterAdvice afterAdvice) {
		this.afterAdvice = afterAdvice;
	}
	public ExceptionAdvice getExceptionAdvice() {
		return exceptionAdvice;
	}
	public void setExceptionAdvice(ExceptionAdvice exceptionAdvice) {
		this.exceptionAdvice = exceptionAdvice;
	}
	public FinallyAdvice getFinallyAdvice() {
		return finallyAdvice;
	}
	public void setFinallyAdvice(FinallyAdvice finallyAdvice) {
		this.finallyAdvice = finallyAdvice;
	}
	public BeforeReturnAdvice getBeforeReturnAdvice() {
		return beforeReturnAdvice;
	}
	public void setBeforeReturnAdvice(BeforeReturnAdvice beforeReturnAdvice) {
		this.beforeReturnAdvice = beforeReturnAdvice;
	}
}
