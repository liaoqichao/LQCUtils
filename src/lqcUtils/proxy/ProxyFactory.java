package lqcUtils.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理工厂：用来生成代理对象。
 * 它需要所有的参数：
 * 1.目标对象
 * 2.增强：分为前置增强和后置增强
 * 
 * 使用：
 * 1.创建代理工厂
 * 2.给工厂设置参数
 * 	目标对象：setTargetObject
 *  前置增强：setBeforeAdvice(BeforeAdvice接口的实现)
 *  后置增强：setAfterAdvice(该接口的实现)
 *  异常增强、最终增强、返回增强.
 * 3.调用createProxy()得到代理对象
 * 	* 执行代理对象方法时：
 * 		> 执行的BeforeAdvcie#before
 *  	> 目标对象的目标方法
 *  	> 执行AfterAdvice#after
 */
public class ProxyFactory {
	private Object targetObject; // 目标对象
	private BeforeAdvice beforeAdvice; // 前置增强
	private AfterAdvice afterAdvice; // 后置增强
	private ExceptionAdvice exceptionAdvice; // 异常增强
	private FinallyAdvice finallyAdvice; // 最终增强
	private BeforeReturnAdvice beforeReturnAdvice; // 返回增强 
	
	/**
	 * 用来生成代理对象
	 * @return 代理对象
	 */
	public Object createProxy(){
		/*
		 * 1.给出三大参数：ClassLoader、Interface[]、InvocationHandler
		 */
		ClassLoader loader = this.getClass().getClassLoader();
		@SuppressWarnings("rawtypes")
		Class[] interfaces = targetObject.getClass().getInterfaces();//获取目标对象的类型的所有实现接口
		InvocationHandler h = new InvocationHandler(){

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				/*
				 * 调用代理对象的方法时会执行这里的内容。
				 */
				Object obj = null;
				try {
					//执行前置增强
					if(beforeAdvice != null){
						beforeAdvice.advice(proxy, method, args, null);
					}
					obj = method.invoke(targetObject, args);//调用目标对象的目标方法
					//执行后置增强
					if(afterAdvice != null){
						afterAdvice.advice(proxy, method, args, null);
					}
				} catch (Exception e) {
					// TODO: handle exception
					// 异常增强
					if(exceptionAdvice != null){
						exceptionAdvice.advice(proxy, method, args, e);
					} else{
						System.err.println("exceptionAdvice is null.");
						e.printStackTrace();
					}
				} finally{
					// 最终增强
					if(finallyAdvice != null)
						finallyAdvice.advice(proxy, method, args, null);
				}
				// 返回增强
				if(beforeReturnAdvice != null)
					beforeReturnAdvice.advice(proxy, method, args, null);
				//返回目标对象的返回值
				return obj;
				
			}
			
		};
		/*
		 * 2.得到代理对象
		 */
		Class<?> clazz = (Class<?>) targetObject.getClass();
		if(clazz.getInterfaces() != null){
			return Proxy.newProxyInstance(loader, interfaces, h);
		} else {
			System.err.println("目标对象没有实现接口，返回目标对象");
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
