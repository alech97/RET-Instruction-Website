package website;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.google.appengine.api.datastore.Entity;

/**
 * This class tests the methods of DataReader
 * @author Alec Helyar
 * @version 2017.8.17
 */
public class TestDataReader {

	/**
	 * This method is used to test DataReader.  It creates a fake
	 * list of entities and asserts that it maps correctly.
	 */
	@Test
	public void test() {
		List<Entity> list = new ArrayList<Entity>();
		createAndLogUser("ALEC", "HELYAR", 
				"Jul 24 18:51:34 UTC 2017", "Page 1", list);
		createAndLogUser("ALEC", "HELYAR", 
				"Jul 24 18:52:34 UTC 2017", "Page 2", list);
		createAndLogUser("ALEC", "HELYAR", 
				"Jul 24 18:54:44 UTC 2017", "Page 5", list);
		createAndLogUser("ALEC", "HELYAR", 
				"Jul 24 19:51:34 UTC 2017", "Page 6", list);
		createAndLogUser("ALEC", "HELYAR", 
				"Jul 24 18:54:34 UTC 2017", "Page 4", list);
		createAndLogUser("ALEC", "HELYAR", 
				"Jul 24 18:53:34 UTC 2017", "Page 3", list);
		createAndLogUser("JESSICA", "RANDOM", 
				"Jul 24 14:00:00 UTC 2017", "Page 3", list);
		createAndLogUser("JESSICA", "RANDOM", 
				"Jul 24 16:00:00 UTC 2017", "Page 4", list);
		createAndLogUser("JESSICA", "RANDOM", 
				"Jul 24 15:00:00 UTC 2017", "Page 3", list);
		createAndLogUser("JESSICA", "RANDOM", 
				"Jul 24 12:00:00 UTC 2017", "Page 2", list);
		DataReader dR = new DataReader(list);
		assertEquals(dR.getUserMap().keySet().size(), 2);
		assertEquals(dR.getUserMap().get("ALEC HELYAR").getLogs().size(), 6);
		assertEquals(dR.getUserMap().get("JESSICA RANDOM").getLogs().size(), 4);
		Iterator<Link> iter = dR.getUserMap().get("ALEC HELYAR").getLogs().iterator();
		int i = 0;
		while (iter.hasNext()) {
			Link l = iter.next();
			int check = Integer.parseInt(l.getPage().substring(5));
			assertTrue(check > i);
			i = check;
		}
	}

	/**
	 * This private method is used to test DataReader by adding
	 * a value to a made up list of entities.
	 * @param fname first name
	 * @param lname last name
	 * @param date date
	 * @param page fake page name
	 * @param list the list being added to
	 */
	private void createAndLogUser(String fname, String lname, 
			String date, String page, List<Entity> list) {
		Entity e = new Entity("User");
		e.setProperty("FirstName", fname);
		e.setProperty("LastName", lname);
		e.setProperty("Date", date);
		e.setProperty("Page", page);
		list.add(e);
	}
}
