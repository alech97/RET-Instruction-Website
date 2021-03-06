package website;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.appengine.api.datastore.Entity;

/**
 * This class is used to read and understand user log data.
 * @author Alec Helyar
 * @version 2017.7.24
 */
public class DataReader {
	private Map<String, UserData> userMap;
	private List<Entity> userLogs;
	
	/**
	 * Constructor for DataReader
	 * @param users A list of all user logs from the datastore DB.
	 */
	public DataReader(List<Entity> users) {
		userLogs = users;
		userMap = new HashMap<String, UserData>();
		mapUsers();
	}
	
	/**
	 * This method returns a JSON string representing this DataReader.
	 * @return Returns a JSON-formatted string.
	 */
	public String getJSON() {
		Set<String> set = userMap.keySet();
		String retStr = "[";
		Iterator<String> iter = set.iterator();
		boolean broken = false;
		while (iter.hasNext()) {
			if (!broken) {
				broken = true;
			}
			else {
				retStr += ", ";
			}
			retStr += userMap.get(iter.next()).getJSON();
		}
		retStr += "]";
		return ", \"data\":" + retStr + "}";
	}
	
	/**
	 * This method returns the userMap generated by this DataReader
	 * @returns Returns the user map.
	 */
	public Map<String, UserData> getUserMap() {
		return userMap;
	}
	
	/**
	 * This private helper function organizes data by name.
	 * It maps DataStore lists of Users into UserData objects, which
	 * are organized by their name.
	 */
	private void mapUsers() {
		for (int i = 0; i < userLogs.size(); i++) {
			Entity check = userLogs.get(i);
			String name = check.getProperty("FirstName") + 
					" " + check.getProperty("LastName");
			if (userMap.containsKey(name)) {
				UserData checkUD = userMap.get(name);
				checkUD.addLink((String) check.getProperty("Date"), 
						(String) check.getProperty("Page"));
			}
			else {
				UserData uD = new UserData(name, 
						(String) check.getProperty("Date"), 
						(String) check.getProperty("Page"));
				userMap.put(name, uD);
			}
		}
	}
}
