package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class RaceHorse{

	//-----ATRIBUTES-----//

	@Id @GeneratedValue
	private int key;
	@OneToOne
	private Race race;
	@OneToOne
	private Horse horse;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Bet> bets;
	private int street;
	private double winGain;


	//-----CONSTRUCTOR-----//

	public RaceHorse(double winGain, Race race, Horse horse) {
		this.street = race.getSize()+1;
		this.winGain = winGain;
		this.horse = horse;
		this.race = race;
		this.bets = new ArrayList<>();
	}


	//-----GET/SET-----//

	public List<Bet> getBets() {
		return bets;
	}

	public void setBets(List<Bet> bets) {
		this.bets = bets;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public Horse getHorse() {
		return horse;
	}

	public void setHorse(Horse horse) {
		this.horse = horse;
	}

	public int getStreet() {
		return street;
	}

	public void setStreet(int street) {
		this.street = street;
	}

	public double getWinGain() {
		return winGain;
	}

	public void setWinGain(double winGain) {
		this.winGain = winGain;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}


	//-----MORE METHODS-----//

	/**
	 * This method adds the given bet to the bets list
	 * @param bet to add to the list
	 */
	public void addBet(Bet bet) {
		this.bets.add(bet);
	}

	/**
	 * Returns a string with objects attributes information prepared to print
	 */
	@Override
	public String toString() {
		return " " +  street + "-" + horse.getName() + " | " + "WinGain: " + winGain + " | " + "Cavalry: " + horse.getCavalryOrigin();
	}

	/**
	 * This method compares this RaceHorse with the given object and returns true if they are same
	 */
	@Override
	public boolean equals(Object o) {
		if((o==null) || (this.getClass()!=o.getClass()))return false;
		RaceHorse rh = (RaceHorse) o;
		return this.horse.equals(rh.getHorse());
	}

	@Override
	public int hashCode() {
		return this.horse.hashCode();
	}

}
