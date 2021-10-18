package data_access;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Admin;
import domain.Bet;
import domain.Client;
import domain.Horse;
import domain.Race;
import domain.RaceHorse;
import domain.Registered;
import domain.StartTime;
import exceptions.HorseDoesntExist;
import exceptions.RaceDoesntExist;
import exceptions.RaceFinished;
import exceptions.RaceFullException;
import exceptions.RaceHorseAlreadyExist;
import exceptions.WrongParameterException;

public class DataAccess  {

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
    public DataAccess(boolean initializeMode)  {
		open(initializeMode);
		close();
	}

    /**
     * Initializes the data base with default objects
     */
	public void initializeDB(){
		initializeUsers();
		ArrayList<Horse> horses = initializeHorses();
		ArrayList<Race> races = initializeRaces();
		initializeRaceHorses(horses, races);
		System.out.println(DB_HEADER + "initialized");
	}

	private void initializeRaceHorses(ArrayList<Horse> horses, ArrayList<Race> races) {
		Race race = races.get(0);	
		Race race1 = races.get(1);
		
		RaceHorse raceHorse0 = new RaceHorse(1.4, race, horses.get(4));
		race.addRaceHorse(raceHorse0);
		race1.addRaceHorse(raceHorse0);
		RaceHorse raceHorse1 = new RaceHorse(1.3, race, horses.get(3));
		race.addRaceHorse(raceHorse1);
		race1.addRaceHorse(raceHorse1);
		RaceHorse raceHorse2 = new RaceHorse(2, race, horses.get(2));
		race.addRaceHorse(raceHorse2);
		race1.addRaceHorse(raceHorse2);
		RaceHorse raceHorse3 = new RaceHorse(1.7, race, horses.get(7));
		race.addRaceHorse(raceHorse3);
		race1.addRaceHorse(raceHorse3);
		System.out.println(DB_HEADER + "Race horses created");
	}

	private ArrayList<Race> initializeRaces() {
		Calendar today = Calendar.getInstance();
		int month=today.get(Calendar.MONTH);
		int year=today.get(Calendar.YEAR);
		if (month==12) { month=0; year+=1;}
		else month+=1;

		ArrayList<Race> races = new ArrayList<>();
		races.add(new Race(UtilDate.newDate(year,month,17), 4, new StartTime("10:30")));
		races.add(new Race(UtilDate.newDate(year,month-2,17), 4, new StartTime("10:30")));
		for(Race r: races) {	db.persist(r);	}
		System.out.println(DB_HEADER + "Races created");
		return races;
	}

	private ArrayList<Horse> initializeHorses() {
		ArrayList<Horse> horses = new ArrayList<>();
		horses.add(new Horse("Seattle Slew", "a", 9, MALE, 11));
		horses.add(new Horse("Man o' War", "a", 5, FEMALE, 5));
		horses.add(new Horse("Citation", "b", 8, FEMALE, 9));
		horses.add(new Horse("Red Rum", "c", 7, MALE,2));
		horses.add(new Horse("Seabiscuit", "d", 6, FEMALE, 19));
		horses.add(new Horse("Kelso", "d", 11, MALE, 31));
		horses.add(new Horse("Native Dancer", "e", 13, MALE, 7));
		horses.add(new Horse("Affirmed", "f", 9, FEMALE, 6));
		horses.add(new Horse("Count Fleet", "g", 12, FEMALE, 13));
		for(Horse h: horses) { db.persist(h); }
		System.out.println(DB_HEADER + "Horses created");
		return horses;
	}

	private void initializeUsers() {
		Admin admin = new Admin("admin", "admin");
		Client client = new Client("client", "client");
		client.addMoney(20);
		db.persist(admin);
		db.persist(client);
		System.out.println(DB_HEADER + "Admin and Client created");
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
	 * This method retrieves from the database the dates a month for which there are events
	 *
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public List<Date> getRacesMonth(Date date) {
		ArrayList<Date> res = new ArrayList<>();
		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);
		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT rc.date FROM Race rc WHERE rc.date BETWEEN ?1 and ?2",Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
	 	for (Date d:dates){
	 		System.out.println(DB_HEADER + d.toString());
	 		res.add(d);
		}
	 	return res;
	}

	public Race getNextRace() {
		Race nextRace = null;
		TypedQuery<Race> query = db.createQuery("SELECT rc FROM Race rc WHERE rc.finished==False", Race.class);
		List<Race> races = query.getResultList();
		for(Race rc: races) if(nextRace == null || nextRace.getDate().compareTo(rc.getDate())>0) nextRace = rc;
		return nextRace;
	}

	/**
	 * This method calls the data base to create a new bet
	 * @param raceHorse of the new Bet
	 * @param client of the new Bet
	 * @param amount bet of the new Bet
	 * @return Client with actualized data
	 */
	public Client betForHorse(RaceHorse raceHorse, Client client, double amount){
		db.getTransaction().begin();
		Client newClient = db.find(client.getClass(), client);
		RaceHorse newRaceHorse = db.find(raceHorse.getClass(), raceHorse);
		Bet bet = new Bet(amount, newRaceHorse, newClient);
		newRaceHorse.addBet(bet);
		newClient.setBet(bet);
		newClient.setWallet(client.getWallet()-amount);
		db.getTransaction().commit();
		System.out.println(DB_HEADER + "New Bet created and added to data base");
		return newClient;
	}

	/**
	 * This method retrieves from the database the race of a given date
	 *
	 * @param date in which race is retrieved
	 * @return the race of the given date, null if there is no race
	 */
	public Race getRace(Date date) {
		TypedQuery<Race> query = db.createQuery("SELECT rc FROM Race rc WHERE rc.date=?1",Race.class);
		query.setParameter(1, date);
		List<Race> races = query.getResultList();
		if(!races.isEmpty())
			return races.get(0);
		else
			return null;
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
		if(newRace.getFinished())throw new RaceFinished();
		
		RaceHorse raceHorse = new RaceHorse(winGain, newRace, newHorse);
		if(newRace.getRaceHorses().contains(raceHorse)) throw new RaceHorseAlreadyExist();
		
		db.getTransaction().begin();
		if(!newRace.addRaceHorse(raceHorse)) throw new RaceFullException();
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

	/**
	 * This method retrieves from the data base to get all raceHorses of a given race
	 * @param race from which to take the raceHorses
	 * @return List<RaceHorse> raceHorses of the given race
	 * @throws RaceDoesntExist 
	 */
	public List<RaceHorse> getRaceHorses(Race race) throws RaceDoesntExist{
		Race rc = getRace(race);
		return rc.getRaceHorses();
	}

	/**
	 * This method retrieves from the data base to get all horses
	 * @return List<Horse> all horses of the DB in a list
	 */
	public List<Horse> getHorses(){
		TypedQuery<Horse> query = db.createQuery("SELECT h FROM Horse h",Horse.class);
		return query.getResultList();
	}

	/**
	 * This method calls the data base to create a new Race with the given date and numOfStreets
	 * @param date of the race
	 * @param numOfStreets of the race
	 * @return Race
	 */
	public Race createRace(Date date, int numOfStreets, StartTime startTime) {
		db.getTransaction().begin();
		Race race = new Race(date, numOfStreets, startTime);
		db.persist(race);
		db.getTransaction().commit();
		System.out.println(DB_HEADER + "New Race created and added to data base");
		return race;
	}

	/**
	 * This method calls the data base to create a new Client by the given username and password
	 * @param userName the name of the new Client
	 * @param password the password of the new Client
	 * @return true if it is correctly registered, false if it is already registered
	 */
	public boolean register(String userName, String password) {
		Registered user = db.find(Registered.class, userName);
		if(user == null) {
			Client b = new Client(userName, password);
			db.getTransaction().begin();
			db.persist(b);
			db.getTransaction().commit();
			System.out.println(DB_HEADER + "New Client created and added to data base");
			return true;
		}else
			return false;
	}

	/**
	 * This method retrieves from the data base the Registered user of the given userName and password
	 * @param userName Registered name
	 * @param password Registered password
	 * @return Registered if the user exists or null if it doesn't
	 */
	public Registered login(String userName, String password) {
		Registered user = db.find(Registered.class, userName);
		if(user != null && user.getPassword().equals(password))
			return user;
		else
			return null;
	}

	/**
	 * This method calls the data base to add a given amount of money to the given users current balance
	 * @param client to add money
	 * @param amount of money to add
	 * @return Client with new balance
	 */
	public Client addMoney(Client client, double amount) {
		db.getTransaction().begin();
		Client c = db.find(client.getClass(), client.getUserName());
		c.addMoney(amount);
		db.getTransaction().commit();
		System.out.println(DB_HEADER + amount + "$ added to '" + client.getUserName() + "' client acount");
		return c;
	}


	/**
	 * This method calls the data base to rest a given amount of money from the given users current balance
	 * @param client to rest money
	 * @param amount of money to rest
	 * @return Client with new balance
	 */
	public Client restMoney(Client client, double amount) {
		db.getTransaction().begin();
		Client c = db.find(client.getClass(), client.getUserName());
		c.restMoney(amount);
		db.getTransaction().commit();
		System.out.println(DB_HEADER + amount + "$ rested from '" + client.getUserName() + "' client acount");
		return c;
	}

	/**
	 * This method calls the data base to add a given amount of money to the given users current balance
	 * @param client to add money
	 * @param amount of money to add
	 * @return Client with new balance
	 */
	public void addPoints(Horse horse, int points) {
		db.getTransaction().begin();
		Horse h = db.find(horse.getClass(), horse.getKey());
		h.setPoints(h.getPoints()+points);
		db.getTransaction().commit();
		System.out.println(DB_HEADER + points + " Points added to " + horse.getName() + " horse history points");
	}

	/**
	 * This method calls the data base to set given race state to finish and set all client bets to null
	 * @param race that has finished
	 * @return new race
	 */
	public Race finishRace(Race race) {
		db.getTransaction().begin();
		Race r = db.find(race.getClass(), race.getKey());
		for(RaceHorse rh: r.getRaceHorses()) {
			for(Bet b: rh.getBets()) {
				b.getClient().setBet(null);
			}
		}
		r.setFinished(true);
		db.getTransaction().commit();
		System.out.println(DB_HEADER + "Race finish state changed to True");
		return r;
	}

	/**
	 * This method calls the data base to remove the given client acount
	 * @param client client to remove
	 */
	public void deleteAcount(Client client) {
		db.getTransaction().begin();
		Client cl = db.find(client.getClass(), client);
		db.remove(cl);
		db.getTransaction().commit();
		System.out.println(DB_HEADER + "Client acount removed: " + cl.getUserName());
	}

}