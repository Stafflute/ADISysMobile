package presentation.controller;

import business.applicationservice.transfer.Parameter;

public interface FrontController {

    public Object processRequest(String request, Parameter parameter);
    
}
