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
 * Servlet implementation class PostQuestion which handles any post request of a question.
 * @author sehamalharbi
 */

@WebServlet("/Question")
public class Question extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * This is a default constructor
	 */
	public Question() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * This is the implemented method that handles the question posting request
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Get the user name from the current session
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");

		// Get the question and the source page by getting the form's parameters
		String question = request.getParameter("questionField");
		String sourcePage = request.getParameter("pageTitle");

		// Create an object from the DB class to insert the question in the DB
		DB db = new DB();
		db.connect();
		db.insertQuestion(question, username, sourcePage);
		db.disconnect();

		// Redirect the user to the same JSP page after posting the question
		String referer = request.getHeader("Referer");
		response.sendRedirect(referer);

	}

}

