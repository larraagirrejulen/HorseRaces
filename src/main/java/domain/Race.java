package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Race{

	//-----ATRIBUTES-----//

	@Id @GeneratedValue
	private int key;
	private Date date;
	@OneToOne(cascade=CascadeType.PERSIST)
	private StartTime startTime;
	private int numOfStreets;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<RaceHorse> raceHorses;
	private boolean finished;


	//-----CONSTRUCTOR-----//

	public Race(Date date, int numOfStreets, StartTime st) {
		this.date = date;
		this.numOfStreets = numOfStreets;
		this.raceHorses = new ArrayList<>();
		this.startTime = st;
		this.finished = false;
	}


	//-----GET/SET-----//

	public boolean getFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public StartTime getStartTime() {
		return startTime;
	}

	public void setStartTime(StartTime startTime) {
		this.startTime = startTime;
	}

	public void setRaceHorses(List<RaceHorse> raceHorses) {
		this.raceHorses = raceHorses;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNumOfStreets() {
		return numOfStreets;
	}

	public void setNumOfStreets(int numOfStreets) {
		this.numOfStreets = numOfStreets;
	}

	public List<RaceHorse> getRaceHorses() {
		return raceHorses;
	}

	public void setRaceHorses(ArrayList<RaceHorse> raceHorses) {
		this.raceHorses = raceHorses;
	}

	public int getSize() {
		return raceHorses.size();
	}

	public boolean doesRaceHorseExist(RaceHorse rh) {
		return getRaceHorses().contains(rh);
	}

	public boolean isFull() {
		return this.raceHorses.size()==this.numOfStreets;
	}


	//-----MORE METHODS-----//

	/**
	 * Method to add the given RaceHorse to the raceHorses list
	 * @param raceHorse to add to the list
	 * @return true if it is added or false if the list is full
	 */
	public boolean addRaceHorse(RaceHorse raceHorse){
		if(raceHorses.size()<this.numOfStreets) {
			raceHorses.add(raceHorse);
			return true;
		}else
			return false;
	}

	/**
	 * Method to remove the given RaceHorse from the raceHorses list
	 * @param raceHorse to remove
	 */
	public void removeRaceHorse(RaceHorse raceHorse) {
		raceHorses.remove(raceHorse);
	}

	/**
	 * This method compares this Race with the given object and returns true if they are same
	 */
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Race other = (Race) obj;
		return key != other.key;
	}

	@Override
	public int hashCode() {
		return this.date.hashCode();
	}
}
