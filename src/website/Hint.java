package website;

/**
 * This class handles a Hint object, which is used for lesson hints.
 * @author Alec Helyar
 * @version 2017.7.2
 */
public class Hint {
	private String hintTitle;
	private String data;
	
	/**
	 * Default constructor for Hint
	 * @param hint The title of the given hint.
	 * @param dataP The data entered for this hint.
	 */
	public Hint(String hint, String dataP) {
		this.setHintTitle(hint);
		this.setData(dataP);
	}
	
	/**
	 * @return Returns a string of the form: "{'hintTitle':'___','data':'___'}"
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("{\"hintTitle\":\"");
		s.append(hintTitle);
		s.append(",\"data\":\"");
		s.append(data);
		s.append("\"}");
		return s.toString();
	}

	/**
	 * @return returns the hintTitle
	 */
	public String getData() {
		return data;
	}

	/**
	 * Set the data value for this object
	 * @param data The dataa for this object.
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @return the hintTitle
	 */
	public String getHintTitle() {
		return hintTitle;
	}

	/**
	 * @param hintTitle the hintTitle to set
	 */
	public void setHintTitle(String hintTitle) {
		this.hintTitle = hintTitle;
	}
	
	
	
}
