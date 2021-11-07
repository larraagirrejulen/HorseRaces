package iterator;

import java.util.List;

import domain.Race;

public class ExtendedIteratorRaces implements ExtendedIterator<Race>{

	List<Race> races;
	int position=0;

	public ExtendedIteratorRaces (List<Race> races) {
		this.races = races;
	}
	
	@Override
	public boolean hasNext() {
		return this.position<races.size();
	}

	@Override
	public Race next() {
		Race race = races.get(this.position);
		this.position++;
		return race;
	}
	
	@Override
	public boolean hasPrevious() {
		return this.position >= 0;
	}
	
	@Override
	public Race previous() {
		Race race = races.get(this.position);
		this.position--;
		return race;
	}
	
	@Override
	public void goLast() {
		this.position = this.races.size()-1;
	}

	@Override
	public void goFirst() {
		this.position = 0;
		
	}

}
