package data_access;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Admin;
import domain.Client;
import domain.Horse;
import domain.Race;
import domain.RaceHorse;
import domain.StartTime;
import exceptions.HorseDoesntExist;
import exceptions.RaceDoesntExist;
import exceptions.RaceFinished;
import exceptions.RaceFullException;
import exceptions.RaceHorseAlreadyExist;
import exceptions.WrongParameterException;

public class DataAccessCreateRaceHorse {
	private static final String DB_HEADER = "DB>>> ";
	private static final String MALE = "male";
	private static final String FEMALE = "female";
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
			throws RaceHorseAlreadyExist,WrongParameterException, RaceFullException, RaceDoesntExist, HorseDoesntExist, RaceFinished{
		
		if(race==null || horse==null || winGain<1) throw new WrongParameterException();
		
		Race newRace = getRace(race);
		Horse newHorse = getHorse(horse);
		
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

	private Horse getHorse(Horse horse) throws HorseDoesntExist {
		Horse newHorse = db.find(horse.getClass(), horse.getKey());
		if(newHorse==null) throw new HorseDoesntExist();
		return newHorse;
	}

	private Race getRace(Race race) throws RaceDoesntExist {
		Race newRace = db.find(race.getClass(), race.getKey());
		if(newRace==null) throw new RaceDoesntExist();
		return newRace;
	}
}
