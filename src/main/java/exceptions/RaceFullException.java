package exceptions;

public class RaceFullException extends Exception{
	private static final long serialVersionUID = 1L;

	 public RaceFullException()
	  {
	    super();
	  }
	  /**This exception is triggered if the question already exists
	  *@param s String of the exception
	  */
	  public RaceFullException(String s)
	  {
	    super(s);
	  }
}
