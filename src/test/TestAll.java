package test;

import lqcUtils.beanFactory.BeanFactory;
import lqcUtils.service.ServiceException;
import lqcUtils.service.ServiceFactory;
import test.proxyfactory.AService;
import test.proxyfactory.AServiceImpl;

public class TestAll {

	private static BeanFactory bf;
	
	
	public static void main(String[] args) throws ServiceException {

		
		bf = new BeanFactory("beanFactory/beans.xml"); 
		AService as = (AService) bf.getBean("aService");
		as.service();
		
//		AService service = (AService) ServiceFactory.getTxService(AServiceImpl.class);
//		service.service();
		
		
	}
}

//@WebServlet("/AServlet")
//class AServlet extends BaseServlet{
//	private AService service;
//	
//	public String listks(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
//		
//		// 获取表单请求参数
//		
//		// 调用业务层方法，自动开启事务
//		this.getAService().service(); // 这里不要直接用成员，如果第一次用的话就是空，后面都不是空，而用方法一直不知空
//		
//		return "f:/ks/listks.jsp";
//		return "/ks/lists.jsp";
//	}
//	
//	private AService getAService(){
//		if(service == null){
//			ServiceFactory<AServiceImpl> factory = new ServiceFactory<AServiceImpl>();
//			this.service =  (AService) factory.getTxService(new AServiceImpl());
//		}
//		return this.service;
//	}
//}
//ServiceFactory<AServiceImpl> factory = new ServiceFactory<AServiceImpl>();
//AService service = (AService) factory.getTxService(new AServiceImpl());
//service.service();

