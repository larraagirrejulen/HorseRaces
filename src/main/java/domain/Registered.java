package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class Registered{
	
	//-----ATRIBUTES-----//
	
	@Id
	private String userName;
	private String password;
	
	
	//-----CONSTRUCTOR-----//
	
	protected Registered(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
	
	//-----GET/SET-----//
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}