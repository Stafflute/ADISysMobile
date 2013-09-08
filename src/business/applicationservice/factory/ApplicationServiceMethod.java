package business.applicationservice.factory;

import business.applicationservice.exception.CommonException;
import business.applicationservice.transfer.Parameter;

public interface ApplicationServiceMethod {
	public Object invoke(String serviceName, Parameter parameter) throws CommonException;
}