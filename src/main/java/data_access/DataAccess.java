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
import exceptions.RaceFullException;
import exceptions.RaceHorseAlreadyExist;
import exceptions.WrongParameterException;

public class DataAccess  {

	private static final String DB_HEADER = "DB>>> ";
	private static final String GENDER = "female";
	protected static EntityManager  db;
	protected static EntityManagerFactory emf;
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
		try {
			db.getTransaction().begin();

			Admin admin = new Admin("admin", "admin");
			Client client = new Client("client", "client");
			client.addMoney(20);
			db.persist(admin);
			db.persist(client);
			System.out.println("DB>>> Admin and Client created");

			Horse h1 = new Horse("Seattle Slew", "a", 9, "male", 11);
			Horse h2 = new Horse("Man o' War", "a", 5, GENDER, 5);
			Horse h3 = new Horse("Citation", "b", 8, GENDER, 9);
			Horse h4 = new Horse("Red Rum", "c", 7, "male",2);
			Horse h5 = new Horse("Seabiscuit", "d", 6, GENDER, 19);
			Horse h6 = new Horse("Kelso", "d", 11, "male", 31);
			Horse h7 = new Horse("Native Dancer", "e", 13, "male", 7);
			Horse h8 = new Horse("Affirmed", "f", 9, GENDER, 6);
			Horse h9 = new Horse("Count Fleet", "g", 12, GENDER, 13);
			db.persist(h1);
			db.persist(h2);
			db.persist(h3);
			db.persist(h4);
			db.persist(h5);
			db.persist(h6);
			db.persist(h7);
			db.persist(h8);
			db.persist(h9);
			System.out.println("DB>>> Horses created");

			Calendar today = Calendar.getInstance();
			int month=today.get(Calendar.MONTH);
			int year=today.get(Calendar.YEAR);
			if (month==12) { month=0; year+=1;}
			else month+=1;

			Race race = new Race(UtilDate.newDate(year,month,17), 4, new StartTime("10:30"));
			Race race1 = new Race(UtilDate.newDate(year,month-2,17), 4, new StartTime("10:30"));
			db.persist(race);
			db.persist(race1);
			System.out.println("DB>>> Races created");

			RaceHorse raceHorse0 = new RaceHorse(1.4, race, h4);
			race.addRaceHorse(raceHorse0);
			race1.addRaceHorse(raceHorse0);
			RaceHorse raceHorse1 = new RaceHorse(1.3, race, h3);
			race.addRaceHorse(raceHorse1);
			race1.addRaceHorse(raceHorse1);
			RaceHorse raceHorse2 = new RaceHorse(2, race, h2);
			race.addRaceHorse(raceHorse2);
			race1.addRaceHorse(raceHorse2);
			RaceHorse raceHorse3 = new RaceHorse(1.7, race, h7);
			race.addRaceHorse(raceHorse3);
			race1.addRaceHorse(raceHorse3);
			System.out.println("DB>>> Race horses created");

			db.getTransaction().commit();
			System.out.println("DB initialized");
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Opens the data base initializing it depending on the initialize mode parameter
	 * @param initializeMode boolean parameter
	 */
	public void open(boolean initializeMode){
		String fileName = config.getDbFilename();
		if (initializeMode) {
			fileName=fileName+";drop";
			System.out.println("Previous DB deleted");
		}
		System.out.println("DB opened");
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
		System.out.println("DB closed");
	}

	/**
	 * This method retrieves from the database the dates a month for which there are events
	 *
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public List<Date> getRacesMonth(Date date) {
		ArrayList<Date> res = new ArrayList<>();
		try {
			Date firstDayMonthDate = UtilDate.firstDayMonth(date);
			Date lastDayMonthDate = UtilDate.lastDayMonth(date);
			TypedQuery<Date> query = db.createQuery("SELECT DISTINCT rc.date FROM Race rc WHERE rc.date BETWEEN ?1 and ?2",Date.class);
			query.setParameter(1, firstDayMonthDate);
			query.setParameter(2, lastDayMonthDate);
			List<Date> dates = query.getResultList();
		 	for (Date d:dates){
		 		System.out.println(d.toString());
		 		res.add(d);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	 	return res;
	}

	public Race getNextRace() {
		Race nextRace = null;
		TypedQuery<Race> query = db.createQuery("SELECT rc FROM Race rc WHERE rc.finished==False", Race.class);
		List<Race> races = query.getResultList();
		for(Race rc: races) if(nextRace == null || nextRace.getDate().compareTo(rc.getDate())<0) nextRace = rc;
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
		System.out.println("DB>>> New Bet created and added to data base");
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
			throws RaceHorseAlreadyExist,WrongParameterException, RaceFullException, RaceDoesntExist, HorseDoesntExist{
		
		if(race==null || horse==null || winGain<1) throw new WrongParameterException();
		
		Race newRace = db.find(race.getClass(), race.getKey());
		if(newRace==null) throw new RaceDoesntExist();
		
		Horse newHorse = db.find(horse.getClass(), horse.getKey());
		if(newHorse==null) throw new HorseDoesntExist();
		
		RaceHorse raceHorse = new RaceHorse(winGain, newRace, newHorse);
		if(newRace.getRaceHorses().contains(raceHorse)) throw new RaceHorseAlreadyExist();
		
		db.getTransaction().begin();
		if(!newRace.addRaceHorse(raceHorse)) throw new RaceFullException();
		db.getTransaction().commit();
		System.out.println("DB>>> New RaceHorse created and added to data base");
		return raceHorse;
	}

	/**
	 * This method retrieves from the data base to get all raceHorses of a given race
	 * @param race from which to take the raceHorses
	 * @return List<RaceHorse> raceHorses of the given race
	 */
	public List<RaceHorse> getRaceHorses(Race race){
		Race r = db.find(race.getClass(), race);
		return r.getRaceHorses();
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
		System.out.println("DB>>> New Race created and added to data base");
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
			System.out.println("DB>>> New Client created and added to data base");
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
		System.out.println("DB>>> Race finish state changed to True");
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
		System.out.println("DB>>> Client acount removed: " + cl.getUserName());
	}

}