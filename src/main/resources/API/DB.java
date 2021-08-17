package askapp.model;

import java.sql.*;
import java.util.Calendar;

/**
 * This the class that handles all operations from and to the DB
 * 
 * @author sehamalharbi
 */

public class DB {

	Connection connection;
	int count;
	String username;
	String role;

	/**
	 * A DB connection will be created once an object is constructed
	 */
	public DB() {

		connection = null;
		count = 0;
	}

	/**
	 * Connects to the DB
	 */
	public void connect() {

		String url = "jdbc:mysql://localhost:3306/AskDB";
		String username = "root";
		String password = "svds2019";

		// Get a connection to the DB
		try {
			connection = DriverManager.getConnection(url, username, password);

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Disconnects to the DB
	 */
	public void disconnect() {

		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	}

	/**
	 * This method inserts a question in the DB
	 */
	public void insertQuestion(String question, String username, String sourcePage) {

		Calendar calendar = Calendar.getInstance();
		Timestamp dateObj = new Timestamp(calendar.getTime().getTime());
		int departmentId = getDepartmentId(sourcePage);

		try {

			// Insert the question into the DB
			String sql = "insert into Question"
					+ " (question_date, no_answers, question_content, asked_by, Department_department_id) "
					+ " values (?,?,?,?,?)";
			PreparedStatement myStmt = connection.prepareStatement(sql);
			myStmt.setTimestamp(1, dateObj);
			myStmt.setInt(2, 0);
			myStmt.setString(3, question);
			myStmt.setString(4, username);
			myStmt.setInt(5, departmentId);
			myStmt.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This method inserts an answer in the DB
	 */
	public void insertAnswer(String answer, String username, int questionId) {

		Calendar calendar = Calendar.getInstance();
		Timestamp dateObj = new Timestamp(calendar.getTime().getTime());

		try {

			// Insert the answer in the DB
			String sql_1 = "insert into Answer" + " (answer_date, answer_content, answered_by, Question_question_id) "
					+ " values (?,?,?,?)";
			PreparedStatement myStmt = connection.prepareStatement(sql_1);

			myStmt.setTimestamp(1, dateObj);
			myStmt.setString(2, answer);
			myStmt.setString(3, username);
			myStmt.setInt(4, questionId);
			myStmt.executeUpdate();

			// Update the number of posts/answers for the question itself
			int numberOfAnswers = 0;
			Statement statement = myConn.createStatement();
			ResultSet myRs = statement.executeQuery("select * from Question WHERE question_id= '" + questionId + "' ");

			while (myRs.next()) {
				numberOfAnswers = myRs.getInt("no_answers");
			}
			// Increment it by one since this is a new answer for this question
			numberOfAnswers++;
			String sql_2 = "UPDATE Question SET no_answers='" + numberOfAnswers + "' WHERE question_id= '" + questionId
					+ "' ";
			statement.executeUpdate(sql_2);

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * This method gets the department ID
	 * 
	 * @return the department ID and send it back to be inserted in the DB
	 */
	public int getDepartmentId(String sourcePage) {

		int departmentID = 0;

		switch (sourcePage) {
		case "Arts and Humanities":
			departmentID = 1;
			break;
		case "Engineering":
			departmentID = 2;
			break;
		case "International Faculty":
			departmentID = 3;
			break;
		case "Medicine Dentistry and Health":
			departmentID = 4;
			break;
		case "Science":
			departmentID = 5;
			break;
		case "Social Science":
			departmentID = 6;
			break;
		}

		return departmentID;

	}

	/**
	 * Searches for the user and return 1 if the user is found
	 */
	public int search(int userID, int sessionID) {

		try {

			ResultSet rs = ss.executeQuery(
					"select * from loggedIn where userID='" + userID + "'and sessionID='" + sessionID + "'");

			while (rs.next()) {

				username = rs.getString("username");
				role = rs.getString("role");
				count++;
			}

		} catch (SQLException e) {
			System.out.println("An SQL exception has occured in creating the resultset!");
		}

		return count;
	}

}