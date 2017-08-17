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
		private CustomDate date;
		private String page;
		
		/**
		 * Default constructor for a Link object.
		 * @param da The date of this entry.
		 * @param pa The page name of this entry.
		 */
		public Link(String da, String pa) {
			date = new CustomDate(da);
			page = pa;
		}

		/**
		 * Compares the dates of a link.  Should sort by first date first.
		 * @param otherLink the link being compared to.
		 * @return Returns negative if less than, 0 if equal to, or positive
		 * 		if greater than.
		 */
		@Override
		public int compareTo(Link otherLink) {
			return date.compareTo(otherLink.getDate());
		}
		
		/**
		 * This method returns the date of this object.
		 * @return Returns the date of this object
		 */
		public CustomDate getDate() {
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
