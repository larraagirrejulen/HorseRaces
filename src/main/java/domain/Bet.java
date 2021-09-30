package domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Bet{
	
	//-----ATTRIBUTES-----//
	
	@Id @GeneratedValue
	private int key;
	private double amount;
	private Date date;
	@OneToOne
	private Client client;
	@OneToOne
	private RaceHorse raceHorse;
	
	
	//-----CONSTRUCTOR-----//
	
	public Bet(double amount, RaceHorse raceHorse, Client client) {
		this.amount = amount;
		this.raceHorse = raceHorse;
		this.client = client;
		this.date = new Date();
	}
	
	
	//-----GET/SET-----//
	
	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public RaceHorse getRaceHorse() {
		return raceHorse;
	}

	public void setRaceHorse(RaceHorse raceHorse) {
		this.raceHorse = raceHorse;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
