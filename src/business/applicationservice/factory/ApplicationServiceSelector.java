package business.applicationservice.factory;


class ApplicationServiceSelector {

	private static final String PACKAGE_PATH_NAME = "business.applicationservice.";

	private static ApplicationServiceMap asMap = new ApplicationServiceHashMap();

	static {
		//TODO be implemented
        //TODO operation to implement create dirs and load list files
        asMap.selectApplicationServiceBy("ElencaPianificazioni");
        asMap.setApplicationServiceValues("ApplicationServicePianificazione", "getFileList");
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
