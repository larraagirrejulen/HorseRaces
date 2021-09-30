package exceptions;

public class RaceFinished extends Exception {
 private static final long serialVersionUID = 1L;

 public RaceFinished()
  {
    super();
  }
  /**This exception is triggered if the race has already finished
  *@param s String of the exception
  */
  public RaceFinished(String s)
  {
    super(s);
  }
}