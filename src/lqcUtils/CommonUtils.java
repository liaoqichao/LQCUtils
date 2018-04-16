package lqcUtils;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;

public class CommonUtils {
	/**
	 * 获取UUID
	 * @return UUID的字符串
	 */
	public static String uuid(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	@SuppressWarnings("rawtypes")
	/**
	 * 把Map转换成指定类型的JavaBean对象
	 * eaxmple1:
	 * 把表单数据放到JavaBean类里
	 * User user = CommonUtils.toBean(request.getParameterMap(),User.class);
	 */
	public static <T> T toBean(Map map,Class<T> clazz){
		try {
			/*
			 * 1.创建指定类型的JavaBean对象
			 * 2.把数据封装到JavaBean中
			 * 3.返回JavaBean对象
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
	 * String转换到对应基本类型的包装类型
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
			throw new RuntimeException("请转换到基本类型!");
		}
	}
}

