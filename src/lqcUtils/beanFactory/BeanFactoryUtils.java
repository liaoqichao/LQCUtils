package lqcUtils.beanFactory;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import lqcUtils.beanFactory.cfg.BeanCfg;
import lqcUtils.beanFactory.cfg.PropertyCfg;
import lqcUtils.xml.XmlUtils;

public class BeanFactoryUtils {
	@SuppressWarnings("unchecked")
	public static void load(BeanFactory factory, String xmlName) {
		Document doc = XmlUtils.getDocument(xmlName);
		List<Element> beanEleList = doc.selectNodes("//bean");
		for(Element beanEle : beanEleList) {
			BeanCfg bc = XmlUtils.toBean(beanEle, BeanCfg.class);
			List<Element> propEleList = beanEle.elements(); 
			for(Element propEle : propEleList) {
				PropertyCfg pc = XmlUtils.toBean(propEle, PropertyCfg.class);
				bc.addPropertiesCfg(pc);
			}
			factory.addBeanCfg(bc);
		}
	}
}
