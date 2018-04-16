package lqcUtils.beanFactory.cfg;

public class PropertyCfg {

	private String name;
	private String value;
	private String ref;
	
	public PropertyCfg() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	@Override
	public String toString() {
		return "PropertiesCfg [name=" + name + ", value=" + value + ", ref=" + ref + "]";
	}
	
}
