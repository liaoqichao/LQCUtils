package lqcUtils.xml;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlUtils {
	@SuppressWarnings("rawtypes")
	public static <T> T toBean(Element e, Class<T> clazz) {
		try {
			Map map = toMap(e);
			T bean = clazz.newInstance();
			BeanUtils.populate(bean, map);
			return bean;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static Element toElement(Object bean, String eleName,
			String... childs) {
		List<String> list = Arrays.asList(childs);
		return toElement(bean, eleName, list);
	}

	public static Element toElement(Object bean, String eleName,
			List<String> childs) {
		try {
			Element e = DocumentHelper.createElement(eleName);
			Class<? extends Object> c = bean.getClass();
			Field[] fs = c.getDeclaredFields();
			for (Field f : fs) {
				String name = f.getName();
				Object value = BeanUtils.getProperty(bean, name);
				if(value == null) {
					continue;
				}
				if(childs.contains(name)) {
					e.addElement(name).setText(String.valueOf(value));
				} else {
					e.addAttribute(name, String.valueOf(value));
				}
			}
			return e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> toMap(Element e) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<Attribute> attrs = e.attributes();
		for (Attribute a : attrs) {
			// 把标签的class属性对应BeanCfg的className
			if(a.getName().equalsIgnoreCase("class")){
				map.put("className", a.getValue());
			} else{
				map.put(a.getName(), a.getValue());
			}
		}
		List<Element> eles = e.elements();
		for (Element ele : eles) {
			if (ele.isTextOnly()) {
				map.put(ele.getName(), ele.getText());
			}
		}
		return map;
	}
	
	/**
	 * 根据Map创建元素，map的key为该元素的属性名，map的value为对应属性名的值。
	 * @param map
	 * @param eleName
	 * @param childs
	 * @return
	 */
	public static Element toElement(Map<String, String> map, String eleName,
			List<String> childs) {
		Element e = DocumentHelper.createElement(eleName);
		for (String key : map.keySet()) {
			String value = map.get(key);
			if (childs.contains(key)) {
				e.addElement(key).setText(value);
			} else {
				e.addAttribute(key, value);
			}
		}

		return e;
	}

	public static Element toElement(Map<String, String> map, String eleName,
			String... childs) {
		List<String> list = Arrays.asList(childs);
		return toElement(map, eleName, list);
	}

	public static Document getDocument(String xmlName) {
		return getDocument(xmlName, true);
	}

	public static Document getDocument(String xmlName, boolean relative) {
		try {
			if (relative) {
				xmlName = Thread.currentThread().getContextClassLoader()
						.getResource(xmlName).getPath();
			}
			return new SAXReader().read(xmlName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void saveDocument(Document doc, String xmlName) {
		saveDocument(doc, xmlName, true);
	}

	public static void saveDocument(Document doc, String xmlName,
			boolean relative) {
		try {
			OutputFormat format = new OutputFormat("\t", true);
			format.setTrimText(true);
			if (relative) {
				xmlName = Thread.currentThread().getContextClassLoader()
						.getResource(xmlName).getPath();
			}
			OutputStream out = new FileOutputStream(xmlName);
			Writer wout = new OutputStreamWriter(out, "utf-8");
			XMLWriter writer = new XMLWriter(wout, format);
			writer.write(doc);
			writer.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
