package website;

import static org.junit.Assert.*;
import java.util.PriorityQueue;
import org.junit.Before;
import org.junit.Test;


/**
 * This class tests UserData's methods
 * @author Alec Helyar
 * @version 2017.8.17
 */
public class TestUserData {
	private UserData ud;
	
	/**
	 * Set up test cases
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		ud = new UserData("ALEC HELYAR", 
				"Jul 24 18:51:34 UTC 2017", "Introduction");
	}

	/**
	 * Test UserData constructor
	 */
	@Test
	public void testUserDataConst() {
		assertEquals(ud.getName(), "ALEC HELYAR");
		PriorityQueue<Link> logs = ud.getLogs();
		assertEquals(logs.size(), 1);
		assertEquals(logs.peek().getDate().getDateString(), 
				"Jul 24 18:51:34 UTC 2017");
		assertEquals(logs.peek().getPage(), "Introduction");
	}
	
	/**
	 * Test getJSON() method
	 */
	@Test
	public void testGetJSON() {
		String correctJSON = "{\"name\":\"ALEC HELYAR\", \"logs\": "
				+ "[{\"page\":\"Page 1\", \"date\":\"Jul 23 18:51:34 "
				+ "UTC 2017\"}, {\"page\":\"Introduction\", \"date\":\"Jul "
				+ "24 18:51:34 UTC 2017\"}]}";
		ud.addLink("Jul 23 18:51:34 UTC 2017", "Page 1");
		assertEquals(correctJSON, ud.getJSON());
	}

	/**
	 * Test addLink() method
	 */
	@Test
	public void testAddLink() {
		ud.addLink("Jul 25 18:52:34 UTC 2017", "Page 3");
		assertEquals(ud.getLogs().size(), 2);
		ud.addLink("Jul 26 18:52:34 UTC 2017", "Page 4");
		assertEquals(ud.getLogs().size(), 3);
		ud.addLink("Jul 20 18:52:34 UTC 2017", "Page 1");
		assertEquals(ud.getLogs().size(), 4);
		PriorityQueue<Link> logs = ud.getLogs();
		assertEquals(logs.remove().getPage(), "Page 1");
		assertEquals(logs.remove().getPage(), "Introduction");
		assertEquals(logs.remove().getPage(), "Page 3");
		assertEquals(logs.remove().getPage(), "Page 4");
	}
}
