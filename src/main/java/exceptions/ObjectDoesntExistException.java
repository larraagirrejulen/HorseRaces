package exceptions;

public class ObjectDoesntExistException extends Exception{

	private static final long serialVersionUID = 1L;
	public ObjectDoesntExistException() {
		super();
	}
	
	public ObjectDoesntExistException(String s) {
		super(s);
	}

}