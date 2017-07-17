package website;

/**
 * This class handles a Page object.  Lessons consist of pages, and 
 * each page contains a google doc URL and a list of hints.
 * @author Alec
 *
 */
public class Page {
	private String name;
	private String editURL;
	private String URL;
	private int order;
	
	/**
	 * Constructor for a Page object
	 * @param n Name of this page
	 * @param editURL edit URL of this page
	 * @param docURL URL of this page
	 * @param orderIndex The index of this page.
	 */
	public Page(String n, String editURL, String URL, int orderIndex) {
		setName(n);
		setURL(URL);
		setOrder(orderIndex);
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
	 * @return the uRL
	 */
	public String getURL() {
		return URL;
	}

	/**
	 * @param uRL the uRL to set
	 */
	public void setURL(String uRL) {
		URL = uRL;
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * @return the editURL
	 */
	public String getEditURL() {
		return editURL;
	}

	/**
	 * @param editURL the editURL to set
	 */
	public void setEditURL(String editURL) {
		this.editURL = editURL;
	}
}
