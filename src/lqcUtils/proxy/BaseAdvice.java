package lqcUtils.proxy;

abstract public class BaseAdvice implements Advice {

	// 给其他增强使用的资源
	private Object resource;

	public Object getResource() {
		return resource;
	}

	public void setResource(Object resource) {
		this.resource = resource;
	}
	
}
