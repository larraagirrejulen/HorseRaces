

import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import business_logic.BLFacade;
import business_logic.BLFacadeImplementation;
import data_access.DataAccess;
import domain.Horse;
import domain.Race;
import domain.RaceHorse;
import domain.StartTime;

@RunWith(MockitoJUnitRunner.class)
public class CreateRaceHorseMockIntTest {
	
	//Race mockedRace=Mockito.mock(Race.class);
	
//	@Mock
	DataAccess horseRacesDAO = Mockito.mock(DataAccess.class);
	
//	@InjectMocks
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

}
