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
	
	private Map<String, Object> beanCache = new HashMap<String, Object>(); // ��������,��id,ֵ��������

	public BeanFactory(String xmlName) { 
		BeanFactoryUtils.load(this, xmlName); // ���������ļ�
	}

	private Object createBean(String id){
		return createBean(id,null);
	}
	private Object createBean(String id, Connection conn){
		try {
			// ����bean
			BeanCfg beanCfg = this.beanCfgMap.get(id);
			Class<?> clazz = Class.forName(beanCfg.getClassName());
			
			// �ж��Ƿ���serviceBean,����ǣ���ͨ��ServiceFactory����
			Object bean = createBeanByOtherFactory(clazz, conn);
			
//			Object bean = clazz.newInstance();
			// ��bean��������
			Map<String, PropertyCfg> pm = beanCfg.getProperiesMap();
			for (String propName : pm.keySet()) {
				PropertyCfg pc = pm.get(propName); // �ӱ�ǩ��name����ֵ
				if(pc.getRef()!=null){  // �Ǽ���������ע��ɹ�
					String ref = pc.getRef();
					Object refBean = getBean(ref);
					Field field = clazz.getDeclaredField(propName);
					field.setAccessible(true);
					field.set(bean, refBean);
				} else if(pc.getValue()==null && pc.getRef()==null){ // �������͵�����ע��
					Field field = clazz.getDeclaredField(propName);
					field.setAccessible(true); // ǿ�Ʒ���
				} else{ // ��Ҫ��String����ת��Ϊ�������Ͳ���ע��ɹ�
					Field field = clazz.getDeclaredField(propName);
					field.setAccessible(true);
					// ��pc.getValue()������ת��
					Class<?> type = field.getType();
					stringToBasicType(bean, field, type, pc.getValue());
				}
			}
			/*
			 * ����ǹ���Bean����ô���ص�Ӧ���ǹ���Bean�����Ķ��󣬶����Ƿ��ع���Bean����
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
			throw new RuntimeException("�����ļ���û��"+id+"��Ӧ��bean��ǩ");
		}
		if(beanCfg.getScope()==null || beanCfg.getScope().equalsIgnoreCase("singleton")){
			// ��δ��������� ����û���򴴽���Ȼ����뻺�棬����������ֱ���û��档
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
		throw new RuntimeException("scopeֻ����prototype����singleton");
	}
	
	/**
	 * ���clazz��BaseService�����࣬��ͨ��ServiceFactory���������򷵻�ֱ�Ӵ�����
	 * @param clazz
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private Object createBeanByOtherFactory(Class clazz, Connection conn) throws Exception{
		Object bean = null;
		// �ж��Ƿ���serviceBean,����ǣ���ͨ��ServiceFactory����
		if(BaseService.class.isAssignableFrom(clazz)){ // BaseService�ǲ���clazz�ĸ���
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
				throw new RuntimeException("type����Ϊ�������͵İ�װ�����");
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
