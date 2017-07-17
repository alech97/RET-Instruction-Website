package website;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles a Lesson object which stores information about a 
 * Lesson's pages.
 * @author Alec
 *
 */
public class Lesson {
	public static final String NAME = "Name";
	private String lessonName;
	private List<Page> pages;
	
	/**
	 * Default constructor
	 * @param title The name of this lesson
	 */
	public Lesson(String title) {
		setLessonName(title);
		setPages(new ArrayList<Page>());
	}

	/**
	 * @return the lessonName
	 */
	public String getLessonName() {
		return lessonName;
	}

	/**
	 * @param lessonName the lessonName to set
	 */
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}

	/**
	 * @return the pages
	 */
	public List<Page> getPages() {
		return pages;
	}

	/**
	 * @param pages the pages to set
	 */
	public void setPages(List<Page> pages) {
		this.pages = pages;
	}
}
