package gui;

import java.util.Locale;
import javax.swing.UIManager;
import business_logic.BLFacade;
import configuration.ConfigXML;
import fatory.BLFacadeFactory;
import logs.Log;

public class ApplicationLauncher {


	public static void main(String[] args) {

		ConfigXML c = ConfigXML.getInstance();
		
		Log log = new Log("src/main/resources/log/launch/application_launcher.txt", "ApplicationLauncher");

		log.addLine(c.getLocale());

		Locale.setDefault(new Locale(c.getLocale()));

		log.addLine("Locale: "+Locale.getDefault());

		MainGUI gui=new MainGUI();
		gui.setVisible(true);
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			
			BLFacade appFacadeInterface = new BLFacadeFactory().createBLFacade(c);
			
			MainGUI.setBussinessLogic(appFacadeInterface);

		}catch (Exception e) {
			log.addLine("Error in ApplicationLauncher: "+e.toString());
		}
	}

}
