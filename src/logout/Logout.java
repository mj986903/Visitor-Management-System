package logout;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/Logout")
public class Logout extends HttpServlet 
{
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{	
		HttpSession session = req.getSession();
		session.removeAttribute("loggedIn");
		session.invalidate();
		resp.sendRedirect(req.getContextPath() + "/HTML/loginPage.html");
	}
}
