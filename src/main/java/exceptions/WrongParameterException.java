package exceptions;

public class WrongParameterException extends Exception{
	private static final long serialVersionUID = 1L;

	public WrongParameterException() {
		super();
	}
	
	public WrongParameterException(String s) {
		super(s);
	}
}
