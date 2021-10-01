package exceptions;

public class RaceDoesntExist extends Exception{

	private static final long serialVersionUID = 1L;
	
	public RaceDoesntExist () {
		super();
	}

	public RaceDoesntExist(String s) {
		super(s);
	}
}
