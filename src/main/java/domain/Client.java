package domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Client extends Registered{

	//-----ATTRIBUTES-----//
	
	private double wallet;
	@OneToOne(cascade=CascadeType.ALL)
	private Bet bet;
	
	
	//-----CONSTRUCTOR-----//
	
	public Client(String userName, String password) {
		super(userName, password);
		this.wallet = 0.0;
	}

	
	//-----GET/SET-----//
	
	public double getWallet() {
		return wallet;
	}

	public void setWallet(double wallet) {
		this.wallet = wallet;
	}

	public Bet getBet() {
		return bet;
	}

	public void setBet(Bet bet) {
		this.bet = bet;
	}
	
	
	//-----MORE METHODS-----//
	
	/**
	 * Adds the given amount of money to the wallet
	 * @param amount of money to add
	 */
	public void addMoney(double amount) {
		this.wallet = this.wallet + amount;
	}
	
	/**
	 * Rests the given amount of money from the wallet
	 * @param amount of money to rest
	 */
	public void restMoney(double amount) {
		this.wallet = this.wallet - amount;
	}

}