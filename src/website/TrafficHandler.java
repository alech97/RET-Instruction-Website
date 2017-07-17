package website;
/**
 * @author Alec Helyar
 * @version 2017.6.28
 * Handles user traffic
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

/**
 * Servlet implementation class TrafficHandler
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
	 * @see HttpServlet#doGet(HttpServletRequest request, 
	 * 		HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException{
		System.out.println("\nString: " + request.getQueryString());
		//Do nothing
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, 
	 * 		HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse 
			response) throws ServletException, IOException {
		System.out.println("****Request received****");
		BufferedReader bR = request.getReader();
		String requestStr = bR.readLine();
		System.out.println(requestStr);
		UserEntry user = new Gson().fromJson(requestStr, UserEntry.class);
		
		System.out.println(user.toString());
		Date date = new Date();
		String dateAndTime = date.toString().substring(4);
		
		dataHandler.logUser(user.getFirstName(), user.getLastName(), 
				request.getRemoteAddr(), dateAndTime.toString(), user.getPage());
		
		response.setContentType("application/json");
		String check = dataHandler.getPages(
				user.getActivity(), (user.getEdit() != null && 
				user.getEdit().equals("T@k3Tw0")) ? 0 : 1);
		System.out.println(check);
		response.getWriter().write(check);
		response.getWriter().close();
	}
	
	private class UserEntry {
		private String firstName;
		private String lastName;
		private String activity;
		private String edit;
		private String page;
		
		@SuppressWarnings("unused")
		public UserEntry(String fName, String lName, String act, String edit, String page) {
			setFirstName(fName);
			setLastName(lName);
			setActivity(act);
			setEdit(edit);
			setPage(page);
		}
		
		@Override
		public String toString() {
			return "First: " + firstName + ", Last: " + 
					lastName + ", Activity: " + activity + ", Edit: " + edit;
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
		 * @return the edit
		 */
		public String getEdit() {
			return edit;
		}

		/**
		 * @param edit the edit to set
		 */
		public void setEdit(String edit) {
			this.edit = edit;
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
