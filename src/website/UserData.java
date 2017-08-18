package website;

import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * This class manages a data object for a user log.  It is used to map
 * names to each person's list of site logs.  
 * @author Alec Helyar
 * @version 2017.2.25
 */
public class UserData {
	private String name;
	/**
	 * A PriorityQueue organized using CustomDate comparables
	 */
	private PriorityQueue<Link> logs;
	
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
	 * This method is used to retrieve data about this UserData
	 * @return Returns this users' logs.
	 */
	public PriorityQueue<Link> getLogs() {
		return logs;
	}
	
	/**
	 * This method returns a JSON formatted string representing itself.
	 * Example: {"name":"ALEC", "logs":[LinkStr, LinkStr, ...]}
	 * @return Returns a JSON string of itself.
	 */
	public String getJSON() {
		Iterator<Link> iter = logs.iterator();
		String retStr = "{\"name\":\"" + name + "\", \"logs\": [" + 
				iter.next().getJSON();
		while (iter.hasNext()) {
			retStr += ", ";
			retStr += iter.next().getJSON();
		}
		retStr += "]}";
		return retStr;
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
}
