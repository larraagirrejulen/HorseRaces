package data_access;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Horse;
import domain.Race;
import domain.RaceHorse;
import exceptions.ObjectDoesntExistException;
import exceptions.RaceFinished;
import exceptions.RaceFullException;
import exceptions.RaceHorseAlreadyExist;
import exceptions.WrongParameterException;

public class DataAccessCreateRaceHorse {
	private static final String DB_HEADER = "DB>>> ";
	protected EntityManager  db;
	protected EntityManagerFactory emf;
	private ConfigXML config = ConfigXML.getInstance();

	/**
	 * Constructor with given initialize mode
	 * @param initializeMode
	 */
    public DataAccessCreateRaceHorse(boolean initializeMode)  {
		open(initializeMode);
		close();
	}	
	
	/**
	 * Opens the data base initializing it depending on the initialize mode parameter
	 * @param initializeMode boolean parameter
	 */
	public void open(boolean initializeMode){
		String fileName = config.getDbFilename();
		if (initializeMode) {
			fileName=fileName+";drop";
			System.out.println(DB_HEADER + "Previous DB deleted");
		}
		System.out.println(DB_HEADER + "DB opened");
		if (config.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
		} else {
			Map<String, String> properties = new HashMap<>();
			properties.put("javax.persistence.jdbc.user", config.getUser());
			properties.put("javax.persistence.jdbc.password", config.getPassword());
			emf = Persistence.createEntityManagerFactory("objectdb://"+  config.getDatabaseNode()+":"+ config.getDatabasePort()+"/"+fileName, properties);
    	}
		db = emf.createEntityManager();
	}

	/**
	 * Closes the data base
	 */
	public void close(){
		db.close();
		System.out.println(DB_HEADER + "DB closed");
	}
	
	/**
	 * This method calls the data base to create a raceHorse and add it to the given race
	 * @param winGain multiplier of the new RaceHorse
	 * @param race of the new RaceHorse
	 * @param horse of the new RaceHorse
	 * @return RaceHorse
	 */
	public RaceHorse createRaceHorse(double winGain, Race race, Horse horse) 
			throws RaceHorseAlreadyExist,WrongParameterException, RaceFullException, ObjectDoesntExistException, RaceFinished{
		
		if(race==null || horse==null || winGain<1) throw new WrongParameterException();
		
		Race newRace = (Race) getObject(race);
		Horse newHorse = (Horse) getObject(horse);
		
		RaceHorse raceHorse = addRaceHorse(winGain, newRace, newHorse);
		System.out.println(DB_HEADER + "New RaceHorse created and added to data base");
		return raceHorse;
	}

	private RaceHorse addRaceHorse(double winGain, Race newRace, Horse newHorse)
			throws RaceHorseAlreadyExist, RaceFullException, RaceFinished {
		db.getTransaction().begin();
		if(newRace.getFinished())throw new RaceFinished();
		
		RaceHorse raceHorse = new RaceHorse(winGain, newRace, newHorse);
		if(newRace.getRaceHorses().contains(raceHorse)) throw new RaceHorseAlreadyExist();
		
		if(!newRace.addRaceHorse(raceHorse)) throw new RaceFullException();
		db.persist(raceHorse);
		db.getTransaction().commit();
		return raceHorse;
	}

	private Object getObject(Object obj) throws ObjectDoesntExistException{
		Object newObj = db.find(obj.getClass(), obj);
		if(newObj == null) throw new ObjectDoesntExistException();
		return newObj;
	}
}
