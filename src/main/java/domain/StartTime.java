package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class StartTime{

	//-----ATRIBUTES-----//
	
	@Id @GeneratedValue
	private int key;
	private Integer hour;
	private Integer minute;
	
	
	//-----CONSTRUCTOR-----//
	
	public StartTime(String startTime) {
		this.parse(startTime);
	}
	
	
	//-----GET/SET-----//

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}
	
	
	//-----MORE METHODS-----//
	
	/**
	 * This method parses the given parameter
	 * @param startTime String to parse
	 * @return true if the given string is valid, false if is not
	 */
    public Boolean parse(String startTime){
        try{
            this.hour = Integer.parseInt(startTime.split(":")[0]);
            this.minute = Integer.parseInt(startTime.split(":")[1]);
            if((this.hour >= 0) &&
                    (this.hour <= 23) &&
                    (this.minute >= 0) &&
                    (this.minute <= 59)){
                return true;
            }else{
                this.hour = null;
                this.minute = null;
                return false;
            }
        }catch(Exception e){
            this.hour = null;
            this.minute = null;
            return false;
        }
    }
	
    /**
	 * Returns a string with objects attributes information prepared to print
	 */
	public String toString() {
		return this.hour + ":" + this.minute;
	}
}
