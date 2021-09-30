package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Horse{

	//-----ATTRIBUTES-----//

	@Id @GeneratedValue
	private int key;
	private String name;
	private String cavalryOrigin;
	private int age;
	private String sex;
	private int points;


	//-----CONSTRUCTOR-----//

	public Horse(String name, String cavalryOrigin, int age, String sex, int points) {
		this.name = name;
		this.cavalryOrigin = cavalryOrigin;
		this.age = age;
		this.sex = sex;
		this.points = points;
	}


	//-----GET/SET-----//

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCavalryOrigin() {
		return cavalryOrigin;
	}

	public void setCavalryOrigin(String cavalryOrigin) {
		this.cavalryOrigin = cavalryOrigin;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}


	//-----MORE METHODS-----//

	/**
	 * Returns a string with objects attributes information prepared to print
	 */
	@Override
	public String toString() {
		return name + " | " + this.cavalryOrigin + " | " + age + " | " + sex + " | " + points;
	}

	/**
	 * This method compares this RaceHorse with the given object and returns true if they are same
	 */
	@Override
	public boolean equals(Object o) {
		if((o==null) || (this.getClass()!=o.getClass()))return false;
		Horse h = (Horse) o;
		return this.name==h.getName();
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
