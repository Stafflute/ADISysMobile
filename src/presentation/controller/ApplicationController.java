package presentation.controller;

import business.applicationservice.transfer.Parameter;

public interface ApplicationController {
    public Object handleRequest(String serviceName, Parameter parameter);

}
