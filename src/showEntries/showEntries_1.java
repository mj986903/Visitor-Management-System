package showEntries;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;


import database.*; // self made package
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/showEntries_1")
public class showEntries_1 extends HttpServlet 
{	
	private String getPreviousMonthDate()
	{
		Date date = new Date();
		SimpleDateFormat ft =  new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		String currDate = ft.format(date);
		
		String month = currDate.substring(5, 7);
		month = String.valueOf(Integer.parseInt(month) - 1); // previous month
		if(month.equals("0") == true) // if curr month is 1
		{
			return currDate.substring(0, 5) + "12" + (Integer.parseInt(currDate.substring(7)) - 1);
		}
		
		if(month.length() == 1) // month might be like 8, 9 after converted into int
		{
			month = "0" + month;
		}
		return currDate.substring(0, 5) + month + currDate.substring(7);
	}
	
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
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{	
		ifNotAuthenticated(req, resp);
		
		HttpSession session = req.getSession();
		String date = getPreviousMonthDate();
		String query = "select * from entries where entry_time >= " + "'" + date + "'" + "order by entry_time desc;";
		
		ResultSet resultset = ConnectionDB.executeQueryResultSet(query);
		session.setAttribute("entries", resultset);
		System.out.print(resultset);
		resp.sendRedirect(req.getContextPath() + "/JSP_Files/showData.jsp");
		
		
	}
}
















