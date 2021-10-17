

import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import business_logic.BLFacade;
import business_logic.BLFacadeImplementation;
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

@RunWith(MockitoJUnitRunner.class)
public class CreateRaceHorseMockIntTest {
	
	DataAccess horseRacesDAO = Mockito.mock(DataAccess.class);
	
	BLFacade sut = new BLFacadeImplementation(horseRacesDAO);
	
	@Test
	public void test1(){
		try {
			Race race = new Race(new Date(), 4, new StartTime("10:30"));	
			Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
			double winGain = 1.5;
			
			Mockito.doReturn(new RaceHorse(winGain, race, horse)).when(horseRacesDAO).createRaceHorse(winGain, race, horse);

			RaceHorse rh = sut.createRaceHorse(winGain, race, horse);
			
			assertEquals(rh.getRace(), race);
			assertEquals(rh.getHorse(), horse);
			
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	@Test
	public void test2(){
		try {	
			Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
			double winGain = 1.5;
			
			Mockito.doThrow(new WrongParameterException()).when(horseRacesDAO).createRaceHorse(winGain, null, horse);
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
			Race race = new Race(new Date(), 4, new StartTime("10:30"));	
			double winGain = 1.5;
			
			Mockito.doThrow(new WrongParameterException()).when(horseRacesDAO).createRaceHorse(winGain, race, null);
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
	public void test4(){
		try {
			Race race = new Race(new Date(), 4, new StartTime("10:30"));	
			Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
			double winGain = 0.5;
			
			Mockito.doThrow(new WrongParameterException()).when(horseRacesDAO).createRaceHorse(winGain, race, horse);
			sut.createRaceHorse(winGain, race, horse);
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
			Race race = new Race(new Date(), 4, new StartTime("10:30"));	
			Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
			double winGain = 1.5;
			
			Mockito.doThrow(new RaceDoesntExist()).when(horseRacesDAO).createRaceHorse(winGain, race, horse);
			sut.createRaceHorse(winGain, race, horse);
			fail();
			
		}catch(RaceDoesntExist e) {
			assertTrue(true);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	@Test
	public void test6(){
		try {
			Race race = new Race(new Date(), 4, new StartTime("10:30"));	
			Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
			double winGain = 1.5;
			
			Mockito.doThrow(new HorseDoesntExist()).when(horseRacesDAO).createRaceHorse(winGain, race, horse);
			sut.createRaceHorse(winGain, race, horse);
			fail();
			
		}catch(HorseDoesntExist e) {
			assertTrue(true);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	@Test
	public void test7(){
		try {
			Race race = new Race(new Date(), 4, new StartTime("10:30"));	
			Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
			double winGain = 1.5;
			
			Mockito.doThrow(new RaceFinished()).when(horseRacesDAO).createRaceHorse(winGain, race, horse);
			sut.createRaceHorse(winGain, race, horse);
			fail();
			
		}catch(RaceFinished e) {
			assertTrue(true);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	@Test
	public void test8(){
		try {
			Race race = new Race(new Date(), 4, new StartTime("10:30"));	
			Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
			double winGain = 1.5;
			
			Mockito.doThrow(new RaceHorseAlreadyExist()).when(horseRacesDAO).createRaceHorse(winGain, race, horse);
			sut.createRaceHorse(winGain, race, horse);
			fail();
			
		}catch(RaceHorseAlreadyExist e) {
			assertTrue(true);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	@Test
	public void test9(){
		try {
			Race race = new Race(new Date(), 4, new StartTime("10:30"));	
			Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
			double winGain = 1.5;
			
			Mockito.doThrow(new RaceFullException()).when(horseRacesDAO).createRaceHorse(winGain, race, horse);
			sut.createRaceHorse(winGain, race, horse);
			fail();
			
		}catch(RaceFullException e) {
			assertTrue(true);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}

}
