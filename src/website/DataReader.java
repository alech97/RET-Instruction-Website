package website;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.appengine.api.datastore.Entity;

/**
 * This class is used to read and understand user log data.
 * @author Alec Helyar
 * @version 2017.7.24
 */
public class DataReader {
	public static final String[] editors = {"ALEC HELYAR", "SHAI GERALD", 
			"SHAILICIA GERALD", "BEAU GARDNER", 
			"TAD BERUBE", "MIKE TRAYLOR", "MICHAEL TRAYLOR", "ALEX MILLIKEN", 
			"MATTHEW STIMPSON", "LAURA BOTTOMLEY", "AMBER KENDALL"};
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
	
	public 
	
	/**
	 * This private helper function organizes data by name.
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
