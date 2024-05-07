package showEntries;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import database.*; // self made package
import security.MakeEntry;

@WebServlet("/ExitTime")
public class ExitTime extends HttpServlet {
	
	private void ifNotAuthenticated(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {	
    	resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // tells browser to not store in cache
    	resp.setHeader("Pragma", "no-cache"); // for older version of http like http 1.0, etc
    	
    	HttpSession session = req.getSession();
    	if(session.getAttribute("loggedIn") == null)
    	{
    		resp.sendRedirect(req.getContextPath() + "/HTML/loginPage.html");
    	}
    }
	
	private void setExitTime(String visitingID)
	{
		Date date = new Date();
		SimpleDateFormat ft =  new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        String exitTime = ft.format(date);
        
        String query = "update entries set exit_time = " + "'" + exitTime + "'" + "where visiting_ID = " + "'" + visitingID + "';";
        ConnectionDB.executeQuery(query);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{	
		ifNotAuthenticated(req, resp);
		
		String visitingID = req.getParameter("visitingID");
		setExitTime(visitingID);
		
		HttpSession session = req.getSession();
		session.setAttribute("exitEntryModal", true); // this is added when no exception is throw and for showing popup of success
		resp.sendRedirect(req.getContextPath() + "/showEntries_1");
	}

}
