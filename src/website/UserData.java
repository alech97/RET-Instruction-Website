package website;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * This class manages a data object for a user log.  It is used to map
 * user logs to specific names.
 * @author Alec Helyar
 * @version 2017.2.25
 */
public class UserData {
	private String name;
	/**
	 * Maps a Date to a Linked page
	 */
	private Queue<Link> logs;
	
	/**
	 * Default constructor
	 * @param name The name of this user
	 * @param date The initial date for this user
	 * @param page The initial page for this user
	 */
	public UserData(String name, String date, String page) {
		this.setName(name);
		logs = new PriorityQueue<Link>();
		logs.add(new Link(date, page));
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This method adds a link to this UserData object
	 * @param date The date of this entry
	 * @param page The page name of this entry
	 */
	public void addLink(String date, String page) {
		logs.add(new Link(date, page));
	}

	/**
	 * This class links the date a user entered a page to a page name.
	 * @author Alec Helyar
	 * @version 2017.2.25
	 */
	private class Link implements Comparable<Link> {
		private String date;
		private String page;
		
		/**
		 * Default constructor for a Link object.
		 * @param da The date of this entry.
		 * @param pa The page name of this entry.
		 */
		public Link(String da, String pa) {
			date = da;
			page = pa;
		}

		/**
		 * Compares the dates of a link.  Should sort by first date first.
		 * @param otherLink the link being compared to.
		 */
		@Override
		public int compareTo(Link otherLink) {
			//Check months
			int month = getMonth(date);
			int otherMonth = getMonth(otherLink.getDate());
			
			//If months are equal
			if (month == otherMonth) {
				
				//Check days
				int day = Integer.parseInt(date.substring(4, 6));
				int otherDay = Integer.parseInt(otherLink.getDate().substring(4, 6));
				
				//If days are equal
				if (day == otherDay) {
					//Check hours
					String[] times = date.substring(7, 15).split(":");
					String[] otherTimes = otherLink.getDate().substring(7, 15).split(":");
					
					//If hours are equal
					if (times[0].equals(otherTimes[0])) {
						//If minutes are equal
						if (times[1].equals(otherTimes[1])) {
							//Return seconds
							return (int) Math.signum(Integer.parseInt(times[2]) - Integer.parseInt(otherTimes[2]));
						}
						else //Return minute difference
							return (int) Math.signum(Integer.parseInt(times[1]) - Integer.parseInt(otherTimes[1]));
					}
					else //Return hour difference
						return (int) Math.signum(Integer.parseInt(times[0]) - Integer.parseInt(otherTimes[0]));
				}
				else //Return day difference
					return (int) Math.signum(day - otherDay);
			}
			else //Return month difference
				return (int) Math.signum(month - otherMonth);
		}
		
		/**
		 * Computes the month of a date and converts it into an int
		 * @param pdate The date being checked
		 * @return Returns the int value of this date's month.
		 */
		private int getMonth(String pdate) {
			String sub = pdate.substring(0,3);
			switch (sub) {
				case "Jan" : return 1;
				case "Feb" : return 2;
				case "Mar" : return 3;
				case "Apr" : return 4;
				case "May" : return 5;
				case "Jun" : return 6;
				case "Jul" : return 7;
				case "Aug" : return 8;
				case "Sep" : return 9;
				case "Oct" : return 10;
				case "Nov" : return 11;
				case "Dec" : return 12;
			}
			return 0;
		}
		
		/**
		 * This method returns the date of this object.
		 * @return Returns the date of this object
		 */
		public String getDate() {
			return date;
		}
		
		/**
		 * This method returns the Page name of this object.
		 * @return Returns the name of this link.
		 */
		public String getPage() {
			return page;
		}
	}
}
