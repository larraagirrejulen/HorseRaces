package iterator;

import java.net.MalformedURLException;
import java.util.Date;

import business_logic.BLFacade;
import configuration.ConfigXML;
import domain.Race;
import fatory.BLFacadeFactory;

public class Main {
	public static void main(String[] args) throws MalformedURLException {
	    //Facade objektua lortu lehendabiziko ariketa erabiliz 
	    BLFacade appFacadeInterface = new BLFacadeFactory().createBLFacade(ConfigXML.getInstance());
	    
	    ExtendedIterator<Race> i=appFacadeInterface.getRaces(new Date()); 
	    Race race; 
	    i.goLast(); 
	    while (i.hasPrevious()){ 
	    	race=i.previous(); 
	    	System.out.println("Beherantz: " + race.getDate()); 
	    } 
	    //Nahiz eta suposatu hasierara ailegatu garela, eragiketa egiten dugu.  
	    i.goFirst(); 
	    while(i.hasNext()){ 
	        race=i.next(); 
	        System.out.println("Gorantz: " + race.getDate()); 
	    }
	}
}
