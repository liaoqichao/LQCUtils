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
//		// ��ȡ���������
//		
//		// ����ҵ��㷽�����Զ���������
//		this.getAService().service(); // ���ﲻҪֱ���ó�Ա�������һ���õĻ����ǿգ����涼���ǿգ����÷���һֱ��֪��
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

