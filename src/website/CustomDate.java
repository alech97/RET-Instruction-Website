package website;

/**
 * This value contains a custom date object used to translate
 * and make changes to the Date objects stored in Datastore.
 * @author Alec Helyar
 * @version 2017.7.25
 */
public class CustomDate implements Comparable<CustomDate> {
	private String date;
	private int month;
	private int year;
	private int day;
	private int hour;
	private int minute;
	private int second;
	
	/**
	 * String constructor for CustomDate object
	 * @param The date string of this object.
	 */
	public CustomDate(String dateString) {
		date = dateString;
		setDateValues();
	}

	/**
	 * Parameter constructor for CustomDate object
	 * @param year The year being set to
	 * @param month The month being set to
	 * @param day The day being set to
	 * @param hour The hour being set to
	 * @param minute The minute being set to
	 * @param second The second being set to
	 */
	public CustomDate(int year, int month, int day, int hour, int minute, int second) {
		setYear(year);
		setMonth(month);
		setDay(day);
		setHour(hour);
		setMinute(minute);
		setSecond(second);
	}
	
	/**
	 * This method loads values from a CustomDate string
	 */
	public void setDateValues() {
		//Set month
		switch (date.substring(0,3)) {
			case "Jan" : setMonth(1);
			case "Feb" : setMonth(2);
			case "Mar" : setMonth(3);
			case "Apr" : setMonth(4);
			case "May" : setMonth(5);
			case "Jun" : setMonth(6);
			case "Jul" : setMonth(7);
			case "Aug" : setMonth(8);
			case "Sep" : setMonth(9);
			case "Oct" : setMonth(10);
			case "Nov" : setMonth(11);
			case "Dec" : setMonth(12);
		}
		
		//Set year
		setYear(Integer.parseInt(date.substring(20,24)));
		
		//Set day
		setDay(Integer.parseInt(date.substring(4,6)));
		
		//Set times
		String[] timeValues = date.substring(7, 15).split(":");
		setHour(Integer.parseInt(timeValues[0]));
		setMinute(Integer.parseInt(timeValues[1]));
		setSecond(Integer.parseInt(timeValues[2]));
	}

	/**
	 * This method compares this CustomDate to another
	 * @param otherCustomDate The date being compared to.
	 * @return Returns -1 if it belongs before it, 0 if equal, 
	 * 		and 1 if it belongs after
	 */
	@Override
	public int compareTo(CustomDate otherCustomDate) {
		if (year == otherCustomDate.getYear()) {
			if (month == otherCustomDate.getMonth()) {
				if (day == otherCustomDate.getDay()) {
					if (hour == otherCustomDate.getHour()) {
						if (minute == otherCustomDate.getMinute()) {
							if (second == otherCustomDate.getSecond()) {
								return 0;
							}
							else 
								return (int) Math.signum(second - otherCustomDate.getSecond());
						}
						else
							return (int) Math.signum(minute - otherCustomDate.getMinute());
					}
					else
						return (int) Math.signum(hour - otherCustomDate.getHour());
				}
				else
					return (int) Math.signum(day - otherCustomDate.getDay());
			}
			else
				return (int) Math.signum(month - otherCustomDate.getMonth());
		}
		else
			return (int) Math.signum(year - otherCustomDate.getYear());
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the minute
	 */
	public int getMinute() {
		return minute;
	}

	/**
	 * @param minute the minute to set
	 */
	public void setMinute(int minute) {
		this.minute = minute;
	}

	/**
	 * @return the second
	 */
	public int getSecond() {
		return second;
	}

	/**
	 * @param second the second to set
	 */
	public void setSecond(int second) {
		this.second = second;
	}

	/**
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * @param hour the hour to set
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}
}
