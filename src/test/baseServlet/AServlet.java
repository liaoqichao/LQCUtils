package test.baseServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lqcUtils.servlet.BaseServlet;
import test.proxyfactory.AService;

/**
 * Servlet implementation class AServlet
 */
@WebServlet("/AServlet")
public class AServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private AService aService;
	
	public String listKs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		aService = (AService) ((BeanFactory)request.getServletContext().getAttribute("beanFactory")).getBean("aService");
		aService = (AService) this.getBean(request, "aService");
		aService.service();
		return "f:/test/testBaseServlet.jsp";
	}

}
