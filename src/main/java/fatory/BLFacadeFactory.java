package fatory;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import business_logic.BLFacade;
import business_logic.BLFacadeImplementation;
import configuration.ConfigXML;
import data_access.DataAccess;

public class BLFacadeFactory {
	
	public BLFacade createBLFacade(ConfigXML config) throws MalformedURLException {
		
		BLFacade appFacadeInterface;
		
		if(config.isBusinessLogicLocal()) {
			DataAccess da= new DataAccess(config.getDataBaseOpenMode().equals("initialize"));
			appFacadeInterface = new BLFacadeImplementation(da);
		}else {
			String serviceName= "http://"+config.getBusinessLogicNode() +":"+ config.getBusinessLogicPort()+"/ws/"+config.getBusinessLogicName()+"?wsdl";
			URL url = new URL(serviceName);
	        QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
	        Service service = Service.create(url, qname);
	        appFacadeInterface = service.getPort(BLFacade.class);
		}
		return appFacadeInterface;
		
	}
	
}
