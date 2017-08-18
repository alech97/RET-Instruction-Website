package website;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

/**
 * Servlet implementation class TrafficHandler.
 * This class handles REST requests received.  All requests should be 
 * POST however, since user data is used.
 */
public class TrafficHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataHandler dataHandler;

	/**
	 * Default constructor. 
	 */
	public TrafficHandler() {
		dataHandler = new DataHandler();
	}

	/**
	 * This method handles a GET request received, but there should be no GET
	 * requests.  So it does nothing.
	 * @see HttpServlet#doGet(HttpServletRequest request, 
	 * 		HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException{
		System.out.println("\nString: " + request.getQueryString());
		//Do nothing
	}

	/**
	 * This method handles a Post request being received
	 * @see HttpServlet#doPost(HttpServletRequest request, 
	 * 		HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse 
			response) throws ServletException, IOException {
		String check;
		
		//Create buffered reader
		BufferedReader bR = request.getReader();
		String requestStr = bR.readLine();

		//Create UserEntry from request string
		UserEntry user = new Gson().fromJson(requestStr, UserEntry.class);
		
		//Check if POST request contained a correct password
		if (user.getEdit() != null && user.getEdit().length() > 0 
				&& dataHandler.checkPass(user.getEdit())) {
			//Retrieve user log data for admin page
			DataReader dataReader = dataHandler.getUserLogs(user.getActivity());
			check = "{\"pages\":" + dataHandler.getPages(user.getActivity()) + dataReader.getJSON();
		}
		else {
			//Create new Date and time
			Date date = new Date();
			String dateAndTime = date.toString().substring(4);
			
			//Log user using dataHandler
			dataHandler.logUser(user.getFirstName(), user.getLastName(), 
					request.getRemoteAddr(), dateAndTime.toString(), user.getPage());
			
			//Create response
			check = dataHandler.getPages(user.getActivity());
		}
		response.setContentType("application/json");
		response.getWriter().write(check);
		response.getWriter().close();
	}
	
	/**
	 * This class handles a UserEntry object for data storage
	 * @author Alec helyar
	 */
	private class UserEntry {
		private String firstName;
		private String lastName;
		private String activity;
		private String page;
		private String edit;
		
		/**
		 * Default constructor
		 * @param fName first name
		 * @param lName last name
		 * @param act The current activity
		 * @param edit The possible password used
		 * @param page The current page
		 */
		@SuppressWarnings("unused")
		public UserEntry(String fName, String lName, String act, String edit, String page) {
			setFirstName(fName);
			setLastName(lName);
			setActivity(act);
			setPage(page);
			setEdit(edit);
		}
		
		/**
		 * Sets edit 
		 * @param edit Sets this user's edit to this parameter
		 */
		private void setEdit(String edit) {
			this.edit = edit;
		}
		
		/**
		 * Returns edit
		 * @return This user's edit
		 */
		private String getEdit() {
			return edit;
		}

		/**
		 * Returns a string of the form "First: Alec, Last: Helyar, Activity: Projectile Motion"
		 * @return Returns a string representation of this user.
		 */
		@Override
		public String toString() {
			return "First: " + firstName + ", Last: " + 
					lastName + ", Activity: " + activity;
		}

		/**
		 * @return the firstName
		 */
		public String getFirstName() {
			return firstName;
		}

		/**
		 * @param firstName the firstName to set
		 */
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		/**
		 * @return the lastName
		 */
		public String getLastName() {
			return lastName;
		}

		/**
		 * @param lastName the lastName to set
		 */
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		/**
		 * @return the activity
		 */
		public String getActivity() {
			return activity;
		}

		/**
		 * @param activity the activity to set
		 */
		public void setActivity(String activity) {
			this.activity = activity;
		}

		/**
		 * @return the page
		 */
		public String getPage() {
			return page;
		}

		/**
		 * @param page the page to set
		 */
		public void setPage(String page) {
			this.page = page;
		}
	}
}
