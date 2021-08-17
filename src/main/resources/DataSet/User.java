package askapp.user;

import askapp.model.DB;

public class User {

	public boolean isUserFound(int userID, int sessionID) {

		boolean check = false;

		DB db = new DB();
		db.connect();

		if (db.search(userID, sessionID) == 1) {
			check = true;
		}

		// a disconnect to DB should be done here
		
		return check;
	}

}