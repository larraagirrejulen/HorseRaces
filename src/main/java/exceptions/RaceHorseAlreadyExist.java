package exceptions;

public class RaceHorseAlreadyExist extends Exception{
	 private static final long serialVersionUID = 1L;

	 public RaceHorseAlreadyExist()
	  {
	    super();
	  }
	  /**This exception is triggered if the question already exists
	  *@param s String of the exception
	  */
	  public RaceHorseAlreadyExist(String s)
	  {
	    super(s);
	  }
	}
