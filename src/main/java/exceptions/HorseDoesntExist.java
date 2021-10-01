package exceptions;

public class HorseDoesntExist extends Exception{

	private static final long serialVersionUID = 1L;
	public HorseDoesntExist() {
		super();
	}
	
	public HorseDoesntExist(String s) {
		super(s);
	}

}
