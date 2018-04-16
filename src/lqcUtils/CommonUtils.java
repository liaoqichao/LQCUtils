package lqcUtils;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;

public class CommonUtils {
	/**
	 * ��ȡUUID
	 * @return UUID���ַ���
	 */
	public static String uuid(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	@SuppressWarnings("rawtypes")
	/**
	 * ��Mapת����ָ�����͵�JavaBean����
	 * eaxmple1:
	 * �ѱ����ݷŵ�JavaBean����
	 * User user = CommonUtils.toBean(request.getParameterMap(),User.class);
	 */
	public static <T> T toBean(Map map,Class<T> clazz){
		try {
			/*
			 * 1.����ָ�����͵�JavaBean����
			 * 2.�����ݷ�װ��JavaBean��
			 * 3.����JavaBean����
			 */
			T bean = clazz.newInstance();
			BeanUtils.populate(bean,map);
			return bean;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Stringת������Ӧ�������͵İ�װ����
	 * @param type
	 * @param str
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object stringToBasicType(Class type, String str){
		if(type == String.class){
			return str;
		} else if(type == Integer.class){
			return Integer.valueOf(str);
		} else if(type == Character.class){
			return Character.valueOf(str.charAt(0));
		} else if(type == Short.class){
			return Short.valueOf(str);
		} else if(type == Long.class) {
			return Long.valueOf(str);
		} else if(type == Byte.class) {
			return Byte.valueOf(str);
		} else if(type == Double.class) {
			return Double.valueOf(str);
		} else if(type == Boolean.class){
			return Boolean.valueOf(str);
		} else if(type == Float.class) {
			return Float.valueOf(str);
		} else {
			throw new RuntimeException("��ת������������!");
		}
	}
}

