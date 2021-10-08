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

public class ApplicationLauncher {


	public static void main(String[] args) {

		ConfigXML c=ConfigXML.getInstance();

		System.out.println(c.getLocale());

		Locale.setDefault(new Locale(c.getLocale()));

		System.out.println("Locale: "+Locale.getDefault());

		LoginGUI gui=new LoginGUI();
		gui.setVisible(true);

		try {

			BLFacade appFacadeInterface;
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

			if (c.isBusinessLogicLocal()) {

				//In this option, you can parameterize the DataAccess (e.g. a Mock DataAccess object)

				DataAccess da= new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
				appFacadeInterface=new BLFacadeImplementation(da);
			}else { //If remote
				String serviceName= "http://"+c.getBusinessLogicNode() +":"+ c.getBusinessLogicPort()+"/ws/"+c.getBusinessLogicName()+"?wsdl";

				URL url = new URL(serviceName);

		        //1st argument refers to wsdl document above
				//2nd argument is service name, refer to wsdl document above
		        QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");

		        Service service = Service.create(url, qname);

		        appFacadeInterface = service.getPort(BLFacade.class);
			}
			LoginGUI.setBussinessLogic(appFacadeInterface);

		}catch (Exception e) {
			System.out.println("Error in ApplicationLauncher: "+e.toString());
		}
	}

}
