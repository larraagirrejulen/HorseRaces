package gui;

import java.net.URL;
import java.util.Locale;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import business_logic.BLFacade;
import business_logic.BLFacadeImplementation;
import configuration.ConfigXML;
import data_access.DataAccess;
import logs.Log;

public class ApplicationLauncher {


	public static void main(String[] args) {

		ConfigXML c=ConfigXML.getInstance();
		
		Log log = new Log("src/main/resources/log/launch/application_launcher.txt", "ApplicationLauncher");

		log.addLine(c.getLocale());

		Locale.setDefault(new Locale(c.getLocale()));

		log.addLine("Locale: "+Locale.getDefault());

		LoginGUI gui=new LoginGUI();
		gui.setVisible(true);

		try {

			BLFacade appFacadeInterface;
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

			if (c.isBusinessLogicLocal()) {

				DataAccess da= new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
				appFacadeInterface=new BLFacadeImplementation(da);
			}else { //If remote
				String serviceName= "http://"+c.getBusinessLogicNode() +":"+ c.getBusinessLogicPort()+"/ws/"+c.getBusinessLogicName()+"?wsdl";

				URL url = new URL(serviceName);

		        QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");

		        Service service = Service.create(url, qname);

		        appFacadeInterface = service.getPort(BLFacade.class);
			}
			LoginGUI.setBussinessLogic(appFacadeInterface);

		}catch (Exception e) {
			log.addLine("Error in ApplicationLauncher: "+e.toString());
		}
	}

}
