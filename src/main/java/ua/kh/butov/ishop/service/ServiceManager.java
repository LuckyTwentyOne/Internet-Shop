package ua.kh.butov.ishop.service;

import javax.servlet.ServletContext;

public class ServiceManager {
	public static ServiceManager getInstance(ServletContext context) {
		ServiceManager instance = (ServiceManager) context.getAttribute("SERVICE_MANAGER");
		if (instance == null) {
			instance = new ServiceManager(context);
			context.setAttribute("SERVICE_MANAGER", instance);
		}
		return instance;
	}

	public void close() { // close resources
		}
	

	private BusinessService businessService;

	public BusinessService getBusinessService() {
		return businessService;
	}

	private ServiceManager(ServletContext context) {
		// init services
	}
}
