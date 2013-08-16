package business.applicationservice.factory;


class ApplicationServiceSelector {

    private static final String PACKAGE_PATH_NAME = "business.applicationservice.";

    private static ApplicationServiceMap asMap = new ApplicationServiceHashMap();

    static {
        asMap.selectApplicationServiceBy("ElencaPianificazioni");
        asMap.setApplicationServiceValues("ApplicationServicePianificazione", "getFileList");

        asMap.selectApplicationServiceBy("ElencaInterventi");
        asMap.setApplicationServiceValues("ApplicationServicePianificazione", "getPianificazione");

        asMap.selectApplicationServiceBy("VerificaPianificazione");
        asMap.setApplicationServiceValues("ApplicationServicePianificazione", "checkValid");

        asMap.selectApplicationServiceBy("AvviaServiziPrincipali");
        asMap.setApplicationServiceValues("ApplicationServiceGeneral", "start");

        asMap.selectApplicationServiceBy("EseguiIntervento");
        asMap.setApplicationServiceValues("ApplicationServiceRilevazione", "startReceiving");

        asMap.selectApplicationServiceBy("InterrompiEsecuzione");
        asMap.setApplicationServiceValues("ApplicationServiceRilevazione", "stopReceiving");

        asMap.selectApplicationServiceBy("RegistraValori");
        asMap.setApplicationServiceValues("ApplicationServiceRilevazione", "registerValues");

        asMap.selectApplicationServiceBy("LeggiFileJournaling");
        asMap.setApplicationServiceValues("ApplicationServiceJournaling", "readJournalingFile");

        asMap.selectApplicationServiceBy("FinisciIntervento");
        asMap.setApplicationServiceValues("ApplicationServiceRilevazione", "completeIntervento");
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
