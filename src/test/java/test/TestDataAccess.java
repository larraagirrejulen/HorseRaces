package test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Horse;
import domain.Race;
import domain.RaceHorse;
import domain.StartTime;

public class TestDataAccess {
	protected EntityManager  db;
	protected EntityManagerFactory emf;

	ConfigXML c=ConfigXML.getInstance();


	public TestDataAccess()  {
		System.out.println("Creating TestDataAccess instance");
		open();
	}

	
	public void open(){
		
		System.out.println("Opening TestDataAccess instance ");

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		
	}
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean removeRace(Race race) {
		System.out.println(">> DataAccessTest: removeRace");
		Race r = db.find(Race.class, race.getKey());
		if (r!=null) {
			db.getTransaction().begin();
			db.remove(r);
			db.getTransaction().commit();
			return true;
		} else 
			return false;
    }
	
	public Horse addHorse(String name, String cavalry, int age, String gender, int totalPoints) {
		db.getTransaction().begin();
		Horse horse = new Horse(name, cavalry, age, gender, totalPoints);
		db.persist(horse);
		db.getTransaction().commit();
		return horse;
	}
		
	public Race addRaceWithRaceHorse(Date date, int numOfStreets, StartTime st, double winGain, Horse horse) {
		System.out.println(">> DataAccessTest: addRace");
		Race rc=null;
			db.getTransaction().begin();
			try {
			    rc=new Race(date, numOfStreets, st);
			    RaceHorse rh = new RaceHorse(winGain, rc, horse);
			    rc.addRaceHorse(rh);
				db.persist(rc);
				db.persist(horse);
				db.getTransaction().commit();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return rc;
	}

}