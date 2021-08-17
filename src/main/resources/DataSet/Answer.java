package askapp.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import askapp.model.DB;

/**
 * Servlet implementation class AnswerQuestion which handles any post request of an answer.
 * @author sehamalharbi
 */

@WebServlet("/Answer")
public class Answer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * This is a default constructor
     */
    public Answer() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * This is the implemented method that handles the answer posting request
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get the user name from the current session
		HttpSession session = request.getSession();
		String username=(String)session.getAttribute("username");
		
		// Get the answer and the question id from the request form's parameters
		String answer = request.getParameter("answerField");
		int questionId= Integer.parseInt(request.getParameter("questionID"));
		
		// Create an object from the DB class to insert the answer in the DB
		DB db = new DB();
		db.connect();
		db.insertAnswer(answer,username,questionId);
		db.disconnect();
		
		// Redirect the user to the same JSP page after posting the answer
		String referer = request.getHeader("Referer");
		response.sendRedirect(referer);
		
	}

}

