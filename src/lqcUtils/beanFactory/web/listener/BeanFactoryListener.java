package lqcUtils.beanFactory.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import lqcUtils.beanFactory.BeanFactory;

/**
 * Application Lifecycle Listener implementation class BeanFactoryListener
 *
 */
@WebListener
public class BeanFactoryListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public BeanFactoryListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	arg0.getServletContext().setAttribute("beanFactory", new BeanFactory("beanFactory/beans.xml"));
    }
	
}
