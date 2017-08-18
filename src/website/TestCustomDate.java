package website;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the methods of CustomDate
 * @author Alec Helyar
 * @version 2017.8.17
 */
public class TestCustomDate {
	private CustomDate base, params;
	
	/**
	 * Set up test cases
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		base = new CustomDate("Jul 24 18:51:34 UTC 2017");
		params = new CustomDate(2017, 7, 24, 18, 51, 34);
	}

	/**
	 * This method tests the String constructor for correct variables
	 */
	@Test
	public void testStrConst() {
		assertNotNull(base);
		assertEquals(base.getYear(), 2017);
		assertEquals(base.getMonth(), 7);
		assertEquals(base.getDay(), 24);
		assertEquals(base.getHour(), 18);
		assertEquals(base.getMinute(), 51);
		assertEquals(base.getSecond(), 34);
	}
	
	/**
	 * This method tests the Params constructor for correct variables
	 */
	@Test
	public void testParamConst() {
		assertNotNull(new CustomDate(2017, 7, 24, 18, 51, 34));
		assertEquals(base.getYear(), 2017);
		assertEquals(base.getMonth(), 7);
		assertEquals(base.getDay(), 24);
		assertEquals(base.getHour(), 18);
		assertEquals(base.getMinute(), 51);
		assertEquals(base.getSecond(), 34);
	}

	/**
	 * This method tests the compareTo() method for proper returns
	 */
	@Test
	public void testCompareTo() {
		assertEquals(base.compareTo(params), 0);
		assertEquals(base.compareTo(new CustomDate("Jul 24 18:51:35 UTC 2017")), -1);
		assertEquals(base.compareTo(new CustomDate("Jul 24 18:51:00 UTC 2017")), 1);
		assertEquals(base.compareTo(new CustomDate("Jul 24 18:52:34 UTC 2017")), -1);
		assertEquals(base.compareTo(new CustomDate("Jul 24 18:50:34 UTC 2017")), 1);
		assertEquals(base.compareTo(new CustomDate("Jul 24 19:51:34 UTC 2017")), -1);
		assertEquals(base.compareTo(new CustomDate("Jul 24 17:51:34 UTC 2017")), 1);
		assertEquals(base.compareTo(new CustomDate("Jul 25 18:51:34 UTC 2017")), -1);
		assertEquals(base.compareTo(new CustomDate("Jul 23 18:51:34 UTC 2017")), 1);
		assertEquals(base.compareTo(new CustomDate("Aug 24 18:51:34 UTC 2017")), -1);
		assertEquals(base.compareTo(new CustomDate("Jan 24 18:51:34 UTC 2017")), 1);
		assertEquals(base.compareTo(new CustomDate("Jul 24 18:51:34 UTC 2018")), -1);
		assertEquals(base.compareTo(new CustomDate("Jul 24 18:51:34 UTC 2015")), 1);
	}
}
