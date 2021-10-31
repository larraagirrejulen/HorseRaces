package business_logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import configuration.ConfigXML;
import data_access.DataAccess;
import domain.Client;
import domain.Horse;
import domain.Race;
import domain.RaceHorse;
import domain.Registered;
import domain.StartTime;
import exceptions.ObjectDoesntExistException;
import exceptions.RaceFinished;
import exceptions.RaceFullException;
import exceptions.RaceHorseAlreadyExist;
import exceptions.WrongParameterException;

public class BLFacadeImplementation implements BLFacade {

	DataAccess dbManager;
	ConfigXML c=ConfigXML.getInstance();
	boolean openMode = c.getDataBaseOpenMode().equals("initialize");

	public BLFacadeImplementation()  {		
		
		dbManager=new DataAccess(openMode);
		if (openMode) {
			dbManager.initializeDB();
			dbManager.close();
		}
	}
	
	/**
	 * Constructor that initializes or not the data base
	 * @param da given dataAccess
	 */
    public BLFacadeImplementation(DataAccess da)  {
    	if (openMode) {
			da.open(false);
			da.initializeDB();
			da.close();
		}
		dbManager=da;
	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
    @Override
	public void initializeBD(){
    	dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}

	/**
	 * This method closes the data base
	 */
    @Override
	public void close() {
		DataAccess dB4oManager=new DataAccess(false);
		dB4oManager.close();
	}

	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 *
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@Override
	public List<Date> getRacesMonth(Date date) {
		dbManager.open(false);
		ArrayList<Date>  dates=(ArrayList<Date>) dbManager.getRacesMonth(date);
		dbManager.close();
		return dates;
	}

	@Override
	public Race getNextRace() {
		dbManager.open(false);
		Race nextRace = dbManager.getNextRace();
		dbManager.close();
		return nextRace;
	}

	/**
	 * This method calls the data base to create a new bet
	 * @param raceHorse of the new Bet
	 * @param client of the new Bet
	 * @param amount bet of the new Bet
	 * @return Client with actualized data
	 */
    @Override
	public Client betForHorse(RaceHorse raceHorse, Client client, double amount) {
		dbManager.open(false);
		Client newClient = dbManager.betForHorse(raceHorse, client, amount);
		dbManager.close();
		return newClient;
	}

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@Override
	public Race getRace(Date date)  {
    	dbManager.open(false);
		Race race = dbManager.getRace(date);
		dbManager.close();
		return race;
	}

	/**
	 * This method calls the data base to create a raceHorse and add it to the given race
	 * @param winGain multiplier of the new RaceHorse
	 * @param race of the new RaceHorse
	 * @param horse of the new RaceHorse
	 * @return RaceHorse
	 */
    @Override
	public RaceHorse createRaceHorse(double winGain, Race race, Horse horse) throws RaceHorseAlreadyExist, WrongParameterException, RaceFullException, ObjectDoesntExistException, RaceFinished{
		dbManager.open(false);
		RaceHorse raceHorse = dbManager.createRaceHorse(winGain, race, horse);
		dbManager.close();
		return raceHorse;
	}

	/**
	 * This method retrieves from the data base to get all raceHorses of a given race
	 * @param race from which to take the raceHorses
	 * @return List<RaceHorse> raceHorses of the given race
	 */
	@Override
	public List<RaceHorse> getRaceHorses(Race race) throws ObjectDoesntExistException{
		dbManager.open(false);
		List<RaceHorse> raceHorses = dbManager.getRaceHorses(race);
		dbManager.close();
		return raceHorses;
	}

	/**
	 * This method retrieves from the data base to get all horses
	 * @return List<Horse> all horses of the DB in a list
	 */
	@Override
	public List<Horse> getHorses(){
		dbManager.open(false);
		List<Horse> horses = dbManager.getHorses();
		dbManager.close();
		return horses;
	}

	/**
	 * This method calls the data base to create a new Race with the given date and numOfStreets
	 * @param date of the race
	 * @param numOfStreets of the race
	 * @return Race
	 */
	@Override
	public Race createRace(Date date, int numberOfStreets, StartTime startTime) {
    	if(new Date().compareTo(date)>0)
    		return null;
    	dbManager.open(false);
    	Race race = dbManager.createRace(date, numberOfStreets, startTime);
    	dbManager.close();
    	return race;
    }

	/**
	 * This method calls the data base to create a new Client by the given username and password
	 * @param userName the name of the new Client
	 * @param password the password of the new Client
	 * @return true if it is correctly registered, false if it is already registered
	 */
	@Override
	public boolean register(String userName, String password) {
    	dbManager.open(false);
		boolean b = dbManager.register(userName, password);
		dbManager.close();
		return b;
	}

	/**
	 * This method retrieves from the data base the Registered user of the given userName and password
	 * @param userName Registered name
	 * @param password Registered password
	 * @return Registered if the user exists or null if it doesn't
	 */
	@Override
	public Registered login(String userName, String password) {
    	dbManager.open(false);
		Registered r = dbManager.login(userName, password);
		dbManager.close();
		return r;
	}

	/**
	 * This method calls the data base to add a given amount of money to the given users current balance
	 * @param client to add money
	 * @param amount of money to add
	 * @return Client with new balance
	 */
	@Override
	public Client addMoney(Client client, double amount) {
    	dbManager.open(false);
		Client c = dbManager.addMoney(client, amount);
		dbManager.close();
		return c;
	}

	/**
	 * This method calls the data base to rest a given amount of money from the given users current balance
	 * @param client to rest money
	 * @param amount of money to rest
	 * @return Client with new balance
	 */
	@Override
	public Client restMoney(Client client, double amount) {
    	dbManager.open(false);
		Client c = dbManager.restMoney(client, amount);
		dbManager.close();
		return c;
	}

	/**
	 * This method calls the data base to add a given amount of points to the given horses total points
	 * @param horse to add points to
	 * @param amount of points to add
	 */
	@Override
	public void addPoints(Horse horse, int points) {
    	dbManager.open(false);
    	dbManager.addPoints(horse, points);
		dbManager.close();
	}

	/**
	 * This method calls the data base to set given race state to finish and set all client bets to null
	 * @param race that has finished
	 * @return new race
	 */
	@Override
	public Race finishRace(Race race) {
		dbManager.open(false);
		Race r = dbManager.finishRace(race);
		dbManager.close();
		return r;
	}

	/**
	 * This method calls the data base to remove the given client acount
	 * @param client client to remove
	 */
	@Override
	public void deleteAcount(Client client) {
		dbManager.open(false);
		dbManager.deleteAcount(client);
		dbManager.close();
	}

}