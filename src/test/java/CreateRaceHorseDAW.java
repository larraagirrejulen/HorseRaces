

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import data_access.DataAccess;
import domain.Horse;
import domain.Race;
import domain.RaceHorse;
import domain.StartTime;
import exceptions.HorseDoesntExist;
import exceptions.RaceDoesntExist;
import exceptions.RaceFullException;
import exceptions.RaceHorseAlreadyExist;
import exceptions.WrongParameterException;
import test.TestDataAccess;

public class CreateRaceHorseDAW {

	static DataAccess sut = new DataAccess(true);
	static TestDataAccess testDA = new TestDataAccess();
	
	private Race race;
	
	@Test
	public void test1(){
		try {
			
			Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
			StartTime st = new StartTime("10:30");
			int numberOfStreets = 4;
			double winGain = 0.5;
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate=null;
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			
			testDA.open();
			race = testDA.addRaceWithRaceHorse(oneDate, numberOfStreets, st, winGain, horse);
			testDA.close();
			
			sut.createRaceHorse(winGain, null, horse);
			
			fail();
			
		}catch(WrongParameterException e) {
			assertTrue(true);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}finally {
			testDA.open();
		}
	}

	@Test
	public void test2(){
		try {
			
			Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
			StartTime st = new StartTime("10:30");
			int numberOfStreets = 4;
			double winGain = 1.5;
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate=null;
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			
			testDA.open();
			race = testDA.addRaceWithRaceHorse(oneDate, numberOfStreets, st, winGain, horse);
			testDA.close();
			
			sut.createRaceHorse(1.5, race, null);
			
			fail();
			
		}catch(WrongParameterException e) {
			assertTrue(true);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}finally {
			testDA.open();
		}
	}
	
	@Test
	public void test3(){
		try {
			
			Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
			StartTime st = new StartTime("10:30");
			int numberOfStreets = 4;
			double winGain = 0.5;
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate=null;
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			
			testDA.open();
			race = testDA.addRaceWithRaceHorse(oneDate, numberOfStreets, st, winGain, horse);
			testDA.close();
			
			sut.createRaceHorse(winGain, race, horse);
			
			fail();
			
		}catch(WrongParameterException e) {
			assertTrue(true);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}finally {
			testDA.open();
		}
	}
	
	@Test
	public void test4(){
		try {
			
			Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
			StartTime st = new StartTime("10:30");
			int numberOfStreets = 4;
			double winGain = 1.5;
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate=null;
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Race race = new Race(oneDate, numberOfStreets, st);
			
			sut.open(false);
			sut.createRaceHorse(winGain, race, horse);
			
			fail();
			
		}catch(RaceDoesntExist e) {
			assertTrue(true);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}finally {
			sut.close();
		}
	}
	
	@Test
	public void test5(){
		try {
			
			Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
			StartTime st = new StartTime("10:30");
			int numberOfStreets = 4;
			double winGain = 1.5;
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate=null;
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			
			testDA.open();
			race = testDA.addRaceWithRaceHorse(oneDate, numberOfStreets, st, winGain, horse);
			testDA.close();
			
			sut.open(false);
			sut.createRaceHorse(winGain, race, new Horse("a", "a", 1, "male", 1));
			
			fail();
			
		}catch(HorseDoesntExist e) {
			assertTrue(true);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}finally {
			sut.close();
		}
	}
	
	@Test
	public void test6(){
		try {
			
			Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
			StartTime st = new StartTime("10:30");
			int numberOfStreets = 4;
			double winGain = 1.5;
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate=null;
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			
			testDA.open();
			race = testDA.addRaceWithRaceHorse(oneDate, numberOfStreets, st, winGain, horse);
			testDA.close();
			
			sut.open(false);
			sut.createRaceHorse(winGain, race, horse);
			
			fail();
			
		}catch(RaceHorseAlreadyExist  e) {
			assertTrue(true);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}finally {
			sut.close();
		}
	}
	
	@Test
	public void test7(){
		try {
			
			Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
			StartTime st = new StartTime("10:30");
			int numberOfStreets = 1;
			double winGain = 1.5;
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate=null;
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			
			testDA.open();
			race = testDA.addRaceWithRaceHorse(oneDate, numberOfStreets, st, winGain, horse);
			Horse horse1 = testDA.addHorse("a", "a", 1, "male", 1);
			testDA.close();
			
			sut.open(false);
			sut.createRaceHorse(winGain, race, horse1);
			
			fail();
			
		}catch(RaceFullException e) {
			assertTrue(true);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}finally {
			sut.close();
		}
	}
	
	@Test
	public void test8(){
		try {
			
			Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
			StartTime st = new StartTime("10:30");
			int numberOfStreets = 4;
			double winGain = 1.5;
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate=null;
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			
			testDA.open();
			race = testDA.addRaceWithRaceHorse(oneDate, numberOfStreets, st, winGain, horse);
			Horse horse1 = testDA.addHorse("a", "a", 1, "male", 1);
			testDA.close();
			
			sut.open(false);
			RaceHorse rh = sut.createRaceHorse(winGain, race, horse1);
			
			assertEquals(rh.getHorse(), horse1);
			assertEquals(rh.getRace(), race);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}finally {
			sut.close();
		}
	}
}
