

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
import exceptions.RaceFinished;
import exceptions.RaceFullException;
import exceptions.RaceHorseAlreadyExist;
import exceptions.WrongParameterException;
import test.TestDataAccess;

public class CreateRaceHorseDAB {

	static DataAccess sut = new DataAccess(true);
	static TestDataAccess testDA = new TestDataAccess();
	
	private Race race;
	private Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
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
			
			testDA.open();
			race = testDA.addRaceWithRaceHorse(createDate(), numberOfStreets, st, winGain, horse);
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
	
	@Test
	public void test2(){
		try {
			testDA.open();
			race = testDA.addRaceWithRaceHorse(createDate(), numberOfStreets, st, winGain, horse);
			testDA.close();
			
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
	public void test3(){
		try {

			testDA.open();
			race = testDA.addRaceWithRaceHorse(createDate(), numberOfStreets, st, winGain, horse);
			testDA.close();
			
			sut.createRaceHorse(1.5, race, null);
			
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
			
			testDA.open();
			race = testDA.addRaceWithRaceHorse(createDate(), numberOfStreets, st, winGain, horse);
			testDA.close();
			
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
	public void test5(){
		try {
			
			Race race = new Race(createDate(), numberOfStreets, st);
			
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
	public void test6(){
		try {
			testDA.open();
			race = testDA.addRaceWithRaceHorse(createDate(), numberOfStreets, st, winGain, horse);
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
	public void test7(){
		try {
			testDA.open();
			race = testDA.addRaceWithRaceHorse(createDate(), numberOfStreets, st, winGain, horse);
			race.setFinished(true);
			testDA.close();
			
			sut.open(false);
			sut.createRaceHorse(winGain, race, horse);
			
			fail();
			
		}catch(RaceFinished  e) {
			assertTrue(true);
		}catch(Exception e){
			fail();
		}finally {
			sut.close();
		}
	}
	
	@Test
	public void test8(){
		try {

			testDA.open();
			race = testDA.addRaceWithRaceHorse(createDate(), numberOfStreets, st, winGain, horse);
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
	public void test9(){
		try {

			testDA.open();
			race = testDA.addRaceWithRaceHorse(createDate(), 1, st, winGain, horse);
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
	
	
}
