package business_logic;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
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
	
	DataAccess dataAccess=Mockito.mock(DataAccess.class);
	Race mockedRace=Mockito.mock(Race.class);
	
	@InjectMocks
	BLFacade sut = new BLFacadeImplementation(dataAccess);
	
	@Test
	public void test1(){
		try {
			
			Horse horse = new Horse("Julen", "Belauntza", 20, "male", 99);
			double winGain = 1.5;
			
			Mockito.doReturn(new RaceHorse(winGain, mockedRace, horse)).when(dataAccess).createRaceHorse(Mockito.any(Integer.class), Mockito.any(Race.class), Mockito.any(Horse.class));

			RaceHorse rh = sut.createRaceHorse(winGain, mockedRace, horse);
			
			assertEquals(rh.getWinGain(), winGain);
			assertEquals(rh.getRace(), mockedRace);
			assertEquals(rh.getHorse(), horse);
			
//			ArgumentCaptor<Double> winGainCaptor = ArgumentCaptor.forClass(Double.class);
//			ArgumentCaptor<Race> raceCaptor = ArgumentCaptor.forClass(Race.class);
//			ArgumentCaptor<Horse> horseCaptor = ArgumentCaptor.forClass(Horse.class);
//			Mockito.verify(dataAccess,Mockito.times(1)).createRaceHorse(winGainCaptor.capture(), raceCaptor.capture(), horseCaptor.capture());
//			assertEquals(raceCaptor.getValue(), mockedRace);
//			assertEquals(horseCaptor.getValue(), horse);

		}catch(Exception e){
			e.printStackTrace();
			fail();
		}finally {
			sut.close();
		}
	}

}
