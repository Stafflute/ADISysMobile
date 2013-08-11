package business.applicationservice.factory;


class ApplicationServiceSelector {

	private static final String PACKAGE_PATH_NAME = "business.business.applicationservice.";

	private static ApplicationServiceMap asMap = new ApplicationServiceHashMap();

	static {
		//TO be implemented
	}

	private ApplicationServiceSelector() {

	}

	public static String getApplicationService(String serviceName) {
        return PACKAGE_PATH_NAME + asMap.getApplicationService(serviceName);
	}

	public static String getServiceMethod(String serviceName) {
		return asMap.getServiceMethod(serviceName);
	}
}
