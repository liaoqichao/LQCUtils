package lqcUtils.beanFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import lqcUtils.beanFactory.cfg.BeanCfg;
import lqcUtils.beanFactory.cfg.PropertyCfg;
import lqcUtils.beanFactory.factoryBean.FactoryBean;
import lqcUtils.service.BaseService;
import lqcUtils.service.ServiceFactory;

public class BeanFactory {

	private Map<String, BeanCfg> beanCfgMap = new HashMap<String, BeanCfg>(); // bean
	
	private Map<String, Object> beanCache = new HashMap<String, Object>(); // 单例缓存,键id,值单例对象

	public BeanFactory(String xmlName) { 
		BeanFactoryUtils.load(this, xmlName); // 加载配置文件
	}

	private Object createBean(String id){
		return createBean(id,null);
	}
	private Object createBean(String id, Connection conn){
		try {
			// 创建bean
			BeanCfg beanCfg = this.beanCfgMap.get(id);
			Class<?> clazz = Class.forName(beanCfg.getClassName());
			
			// 判断是否是serviceBean,如果是，则通过ServiceFactory创建
			Object bean = createBeanByOtherFactory(clazz, conn);
			
//			Object bean = clazz.newInstance();
			// 给bean设置属性
			Map<String, PropertyCfg> pm = beanCfg.getProperiesMap();
			for (String propName : pm.keySet()) {
				PropertyCfg pc = pm.get(propName); // 子标签的name属性值
				if(pc.getRef()!=null){  // 非简单类型依赖注入成功
					String ref = pc.getRef();
					Object refBean = getBean(ref);
					Field field = clazz.getDeclaredField(propName);
					field.setAccessible(true);
					field.set(bean, refBean);
				} else if(pc.getValue()==null && pc.getRef()==null){ // 集合类型的依赖注入
					Field field = clazz.getDeclaredField(propName);
					field.setAccessible(true); // 强制访问
				} else{ // 需要将String类型转化为其他类型才能注入成功
					Field field = clazz.getDeclaredField(propName);
					field.setAccessible(true);
					// 对pc.getValue()的类型转换
					Class<?> type = field.getType();
					stringToBasicType(bean, field, type, pc.getValue());
				}
			}
			/*
			 * 如果是工厂Bean，那么返回的应该是工厂Bean生产的对象，而不是返回工厂Bean本身
			 */
			if(bean instanceof FactoryBean){
				FactoryBean factoryBean = (FactoryBean) bean;
				return factoryBean.getObject();
			}
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Object getBean(String id){
		return getBean(id, null);
	}
	public Object getBean(String id, Connection conn){
		BeanCfg beanCfg = this.beanCfgMap.get(id);
		if(beanCfg == null){
			throw new RuntimeException("配置文件下没有"+id+"对应的bean标签");
		}
		if(beanCfg.getScope()==null || beanCfg.getScope().equalsIgnoreCase("singleton")){
			// 如何创建单例？ 缓存没有则创建，然后放入缓存，缓存中有则直接拿缓存。
			if(this.beanCache.containsKey(id)){
				return this.beanCache.get(id);
			} else{
				Object bean = createBean(id,conn);
				beanCache.put(id, bean);
				return bean;
			}
		} else if(beanCfg.getScope().equalsIgnoreCase("prototype")){
			return createBean(id);
		}
		throw new RuntimeException("scope只能是prototype或者singleton");
	}
	
	/**
	 * 如果clazz是BaseService的子类，则通过ServiceFactory创建，否则返回直接创建。
	 * @param clazz
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private Object createBeanByOtherFactory(Class clazz, Connection conn) throws Exception{
		Object bean = null;
		// 判断是否是serviceBean,如果是，则通过ServiceFactory创建
		if(BaseService.class.isAssignableFrom(clazz)){ // BaseService是不是clazz的父类
//			bean = new ServiceFactory().getTxService(clazz, conn); //
			bean = ServiceFactory.getTxService(clazz, conn); //
		} else{ 
			bean = clazz.newInstance();
		}
		return bean;
	}
	
	@SuppressWarnings("rawtypes")
	private void stringToBasicType(Object bean,Field field,Class type, String str){
		try {
			if(type == String.class){
				field.set(bean, str);
			} else if(type == int.class || type == Integer.class){
				field.set(bean, Integer.valueOf(str));
			} else if(type == float.class ||type == Float.class) {
				field.set(bean, Float.valueOf(str));
			} else if(type == boolean.class || type == Boolean.class){
				field.set(bean, Boolean.valueOf(str));
			} else if(type == short.class || type == Short.class){
				field.set(bean, Short.valueOf(str));
			} else if(type == double.class || type == Double.class) {
				field.set(bean, Double.valueOf(str));
			} else if(type == long.class || type == Long.class) {
				field.set(bean, Long.valueOf(str));
			} else if(type == char.class || type == Character.class){
				field.set(bean, str.charAt(0));
			} else if(type == byte.class || type == Byte.class) {
				field.set(bean, Byte.valueOf(str));
			} else {
				System.out.println(type);
				throw new RuntimeException("type参数为基本类型的包装类的类");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
		
	}
	
	public Map<String, BeanCfg> getBeanCfgMap() {
		return beanCfgMap;
	}

	public void setBeanCfgMap(Map<String, BeanCfg> beanCfgMap) {
		this.beanCfgMap = beanCfgMap;
	}
	public BeanCfg getBeanCfg(String id){
		return this.beanCfgMap.get(id);
	}
	public void addBeanCfg(BeanCfg beanCfg){
		this.beanCfgMap.put(beanCfg.getId(), beanCfg);
	}

}
