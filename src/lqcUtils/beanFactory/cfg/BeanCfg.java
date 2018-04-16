package lqcUtils.beanFactory.cfg;

import java.util.LinkedHashMap;
import java.util.Map;

public class BeanCfg {

	private String id;
	private String className;
	private String scope; // ֻ��prototype�� singleton
	// �����ӱ�ǩ��name���ԣ�ֵ�������ӱ�ǩ
	private Map<String, PropertyCfg> properiesMap = new LinkedHashMap<String, PropertyCfg>();
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		if(!scope.equalsIgnoreCase("prototype") && !scope.equalsIgnoreCase("singleton")){
			throw new RuntimeException("scope��ֵֻ����prototype����singleton��");
		}
		this.scope = scope;
	}
	public Map<String, PropertyCfg> getProperiesMap() {
		return properiesMap;
	}
	public void setProperiesMap(Map<String, PropertyCfg> properiesMap) {
		this.properiesMap = properiesMap;
	}
	public void addPropertiesCfg(PropertyCfg pc){
		this.properiesMap.put(pc.getName(), pc);
	}
	public PropertyCfg getPropertiesCfg(String name){
		return this.properiesMap.get(name);
	}
}
