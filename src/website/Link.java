package website;

/**
 * This class links the date a user entered a page to a page name.
 * @author Alec Helyar
 * @version 2017.2.25
 */
public class Link implements Comparable<Link> {
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
	 * This method returns a JSON-formatted string representing this Link.
	 * Ex: {"page":"Introduction", "date":"Jul 24 18:51:34 UTC 2017"}
	 * @return Returns a JSON string of this object.
	 */
	public String getJSON() {
		return "{\"page\":\"" + page + "\", \"date\":\"" + date.getDateString() + "\"}";
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