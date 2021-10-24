package business_logic;

import java.util.Date;
import java.util.List;

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

public interface BLFacade{

	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeBD();

	/**
	 * This method closes the data base
	 */
	public void close();

	/**
	 * This method calls the data base to create a new bet
	 * @param raceHorse of the new Bet
	 * @param client of the new Bet
	 * @param amount bet of the new Bet
	 * @return Client with actualized data
	 */
	public Client betForHorse(RaceHorse raceHorse, Client client, double amount);

	/**
	 * This method retrieves from the database the dates a month for which there are events
	 *
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public List<Date> getRacesMonth(Date date);

	public Race getNextRace();

	/**
	 * This method calls the data base to create a raceHorse and add it to the given race
	 * @param winGain multiplier of the new RaceHorse
	 * @param race of the new RaceHorse
	 * @param horse of the new RaceHorse
	 * @return RaceHorse
	 */
	public RaceHorse createRaceHorse(double winGain, Race race, Horse horse) throws RaceHorseAlreadyExist, WrongParameterException, RaceFullException, ObjectDoesntExistException, RaceFinished;

	/**
	 * This method retrieves from the data base to get all raceHorses of a given race
	 * @param race from which to take the raceHorses
	 * @return List<RaceHorse> raceHorses of the given race
	 */
	public List<RaceHorse> getRaceHorses(Race race) throws ObjectDoesntExistException;

	/**
	 * This method retrieves from the data base to get all horses
	 * @return List<Horse> all horses of the DB in a list
	 */
	public List<Horse> getHorses();

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Race getRace(Date date);

	/**
	 * This method calls the data base to create a new Race with the given date and numOfStreets
	 * @param date of the race
	 * @param numOfStreets of the race
	 * @return Race
	 */
	public Race createRace(Date date, int numberOfStreets, StartTime startTime);

	/**
	 * This method calls the data base to create a new Client by the given username and password
	 * @param userName the name of the new Client
	 * @param password the password of the new Client
	 * @return true if it is correctly registered, false if it is already registered
	 */
	public boolean register(String userName, String password);

	/**
	 * This method retrieves from the data base the Registered user of the given userName and password
	 * @param userName Registered name
	 * @param password Registered password
	 * @return Registered if the user exists or null if it doesn't
	 */
	public Registered login(String userName, String password);

	/**
	 * This method calls the data base to add a given amount of money to the given users current balance
	 * @param client to add money
	 * @param amount of money to add
	 * @return Client with new balance
	 */
	public Client addMoney(Client client, double amount);

	/**
	 * This method calls the data base to rest a given amount of money from the given users current balance
	 * @param client to rest money
	 * @param amount of money to rest
	 * @return Client with new balance
	 */
	public Client restMoney(Client client, double amount);

	/**
	 * This method calls the data base to add a given amount of points to the given horses total points
	 * @param horse to add points to
	 * @param amount of points to add
	 */
	public void addPoints(Horse horse, int points);

	/**
	 * This method calls the data base to set given race state to finish and set all client bets to null
	 * @param race that has finished
	 * @return new race
	 */
	public Race finishRace(Race race);

	/**
	 * This method calls the data base to remove the given client acount
	 * @param client client to remove
	 */
	public void deleteAcount(Client client);

}