package website;

import java.util.Iterator;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Class DataHandler
 */
public class DataHandler {
	private DatastoreService datastore;
	private static final String LessonStr = "Lesson";
	private static final String PageStr = "Page";
	private static final String EntityStr = "Hint";
	private static final String UserStr = "User";
	private static final String Password = "Password";
       
    /**
     * Initialize a DataHandler object
     */
    public DataHandler() {
        datastore = DatastoreServiceFactory.getDatastoreService();
    }
    
    /**
     * This method creates a lesson entity
     * @param lesson The lesson being created
     * @return Returns a key for this lesson.
     */
    public Key createLessonEntity(String lesson) {
    	Entity newLessonEntity = new Entity(LessonStr, lesson);
    	Key lessonKey = datastore.put(newLessonEntity);
    	return lessonKey;
    }
    
    /**
     * This method creates a page for a lesson entity
     * @param page The page being added.
     * @param order The order of this page.
     * @param URL The final url of this page
     * @param lessonKey The parent lesson of the page
     * @return returns the key of the new page.
     */
    public Key createPageEntity(String page, int order, String URL, String lessonKey) {
    	Entity newPageEntity = new Entity(PageStr, page, KeyFactory.createKey(LessonStr, lessonKey));
    	newPageEntity.setProperty("order", order);
    	newPageEntity.setProperty("URL", URL);
    	Key pageKey = datastore.put(newPageEntity);
    	return pageKey;
    }
    
    /**
     * This method creates a hint for a page entity
     * @param title The title of this hint
     * @param data The data of this hint
     * @param order The order index of this hint.
     * @param page The page key ID for this hint.
     * @return Returns the key for this new hint.
     */
    public Key createHintEntity(String title, String data, int order, String page) {
    	Entity newHintEntity = new Entity(EntityStr, title, KeyFactory.createKey(PageStr, page));
    	newHintEntity.setProperty("data", data);
    	newHintEntity.setProperty("order", order);
    	Key hintKey = datastore.put(newHintEntity);
    	return hintKey;
    }
    
    /**
     * This method returns a List of pages for a given project.
     * @param Project The project of this query.
     * @return Returns a String based upon a list of querried pages.
     */
	@SuppressWarnings("unchecked")
	public String getPages(String project) {
    	Query q = new Query("Page").addSort("order");
    	PreparedQuery pq = datastore.prepare(q);
    	List<Entity> list = pq.asList(FetchOptions.Builder.withChunkSize(10));
    	Iterator<Entity> iter = list.iterator();
    	JSONArray jsonA = new JSONArray();
    	while (iter.hasNext()) {
    		Entity e = iter.next();
    		JSONObject jobj = new JSONObject();
    		jobj.put("Name", e.getKey().getName());
    		jobj.put("URL", e.getProperty("URL"));
    		jsonA.add(jobj);
    	}
    	return jsonA.toJSONString();
    }
	
	/**
	 * This method returns a DataReader of all User datastore entries.
	 * @param project The project of this query.
	 * @return Returns a DataReader for user logs.
	 */
	public DataReader getUserLogs(String project) {
		Query q = new Query("User").addSort("Date");
    	PreparedQuery pq = datastore.prepare(q);
		return new DataReader(pq.asList(FetchOptions.Builder.withDefaults()));
	}
	
	/**
	 * This method checks whether or not a given password matches a password
	 * stored on the database.
	 * @param pass The password being checked
	 * @return Returns true if the given password matches.
	 */
	public boolean checkPass(String pass) {
		Query q = new Query(Password);
		PreparedQuery pq = datastore.prepare(q);
		Entity entity = pq.asSingleEntity();
		return entity.getProperty("Edit").equals(pass);
	}
    
    /**
     * This method logs a user datastore value for each user.
     * @param fName First name of the user
     * @param lName Last name of the user
     * @param ipAddress IPAddress of the user
     * @param date Date of access
     * @param page The page being accessed
     */
    public Key logUser(String fName, String lName, String ipAddress, String date, String page) {
    	Entity newUserEntity = new Entity(UserStr);
    	newUserEntity.setProperty("FirstName", fName);
    	newUserEntity.setProperty("LastName", lName);
    	newUserEntity.setProperty("IPAddress", ipAddress);
    	newUserEntity.setProperty("Date", date);
    	newUserEntity.setProperty("Page", page);
    	Key userKey = datastore.put(newUserEntity);
    	return userKey;
    }
}
