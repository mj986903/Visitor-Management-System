package register;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import database.ConnectionDB;

/**
 * Servlet implementation class register_add
 */
@WebServlet("/HTML/register_add")
public class register_add extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public register_add() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String userRole = request.getParameter("user-role");
		
		String query = "Insert into login values(\"" + username + "\",\"" + password + "\",\"" + userRole + "\");";
		ConnectionDB.executeQuery(query);
		
		response.sendRedirect(request.getContextPath() + "/HTML/loginPage.html");
	}

}
