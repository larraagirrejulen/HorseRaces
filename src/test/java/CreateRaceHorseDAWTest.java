

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
import exceptions.ObjectDoesntExistException;
import exceptions.RaceFullException;
import exceptions.RaceHorseAlreadyExist;
import exceptions.WrongParameterException;
import test.TestDataAccess;

public class CreateRaceHorseDAWTest {

	static DataAccess sut = new DataAccess(true);
	static TestDataAccess testDA = new TestDataAccess();
	
	private Race race;
	private RaceHorse raceHorse;
	private Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
	private Horse horse1;
	private StartTime st = new StartTime("10:30");
	private int numberOfStreets = 4;
	private double winGain = 1.5;
	
	public Date createDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return sdf.parse("05/10/2022");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Test
	public void test1(){
		try {
			
			sut.createRaceHorse(winGain, null, horse);
			fail();
			
		}catch(WrongParameterException e) {
			assertTrue(true);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void test2(){
		try {
			
			sut.createRaceHorse(winGain, race, null);
			fail();
			
		}catch(WrongParameterException e) {
			assertTrue(true);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void test3(){
		try {
			
			sut.createRaceHorse(0.5, race, horse);
			fail();
			
		}catch(WrongParameterException e) {
			assertTrue(true);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void test4(){
		try {
			Race race = new Race(createDate(), numberOfStreets, st);
			
			sut.open(false);
			sut.createRaceHorse(winGain, race, horse);
			
			fail();
			
		}catch(ObjectDoesntExistException e) {
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

			testDA.open();
			race = testDA.addRaceWithRaceHorse(createDate(), numberOfStreets, st, winGain, horse);
			
			sut.open(false);
			sut.createRaceHorse(winGain, race, new Horse("a", "a", 1, "male", 1));
			
			fail();
			
		}catch(ObjectDoesntExistException e) {
			assertTrue(true);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}finally {
			sut.close();
			testDA.removeRace(race);
			testDA.close();
		}
	}
	
	@Test
	public void test6(){
		try {
			
			testDA.open();
			race = testDA.addRaceWithRaceHorse(createDate(), numberOfStreets, st, winGain, horse);
			
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
			testDA.removeRace(race);
			testDA.close();
		}
	}
	
	@Test
	public void test7(){
		try {
			
			testDA.open();
			race = testDA.addRaceWithRaceHorse(createDate(), 1, st, winGain, horse);
			Horse horse1 = testDA.addHorse("a", "a", 1, "male", 1);
			
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
			testDA.removeHorse(horse);
			testDA.removeRace(race);
			testDA.close();
		}
	}
	
	@Test
	public void test8(){
		try {
			
			testDA.open();
			race = testDA.addRaceWithRaceHorse(createDate(), numberOfStreets, st, winGain, horse);
			horse1 = testDA.addHorse("a", "a", 1, "male", 1);
			testDA.close();
			
			
			sut.open(false);
			raceHorse = sut.createRaceHorse(winGain, race, horse1);		
			
			assertEquals(raceHorse.getHorse(), horse1);
			assertEquals(raceHorse.getRace(), race);
			
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}finally {
			sut.close();
			testDA.open();
			testDA.removeRace(race);
			testDA.removeRaceHorse(raceHorse);
			testDA.removeHorse(horse);
			testDA.removeHorse(horse1);
			testDA.close();
		}
			
	}
}
