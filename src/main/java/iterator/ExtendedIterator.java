package iterator;

import java.util.Iterator;

@SuppressWarnings("hiding")
public interface ExtendedIterator<Object> extends Iterator<Object>{
	
	/**
	 * Aurreko elementuan kokatu
	 * @return Uneko elementua
	 */
	public Object previous();
	
	/**
	 * Aurreko elementua existitzen den ikusten du.
	 * @return true existitzen bada, false bestela.
	 */
	public boolean hasPrevious();
	
	/**
	 * Lehendabiziko elementuan kokatzen da.
	 */
	public void goFirst();
	
	/**
	 * Azkeneko elementuan kokatzen da.
	 */
	public void goLast();
	
}
