package lqcUtils.proxy;

abstract public class BaseAdvice implements Advice {

	// ��������ǿʹ�õ���Դ
	private Object resource;

	public Object getResource() {
		return resource;
	}

	public void setResource(Object resource) {
		this.resource = resource;
	}
	
}
