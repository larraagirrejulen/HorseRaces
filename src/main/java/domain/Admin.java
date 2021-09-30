package domain;

import javax.persistence.Entity;

@Entity
public class Admin extends Registered{

	//-----CONSTRUCTOR-----//

	public Admin(String userName, String password) {
		super(userName, password);
	}

}
