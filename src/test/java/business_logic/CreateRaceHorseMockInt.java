package business_logic;

import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

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
public class CreateRaceHorseMockInt {
	
	//Race mockedRace=Mockito.mock(Race.class);
	
	@Mock
	DataAccess horseRacesDAO;
	
	@InjectMocks
	BLFacade sut;
	
	@Test
	public void test1(){
		try {
			Race race = new Race(new Date(), 4, new StartTime("10:30"));
			Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
			double winGain = 1.5;
			
			Mockito.doReturn(new RaceHorse(winGain, race, horse)).when(horseRacesDAO).createRaceHorse(winGain, race, horse);

			RaceHorse rh = sut.createRaceHorse(winGain, race, horse);
			
			assertEquals(rh.getWinGain(), winGain);
			assertEquals(rh.getRace(), race);
			assertEquals(rh.getHorse(), horse);
			
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}finally {
			sut.close();
		}
	}

}
