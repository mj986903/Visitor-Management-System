package showEntries;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import database.*; // self made package


@WebServlet("/EditEntry")
public class EditEntry extends HttpServlet 
{
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
	
	private void editEntryTime(String visitingID) throws SQLException
	{
		Date date = new Date();
		SimpleDateFormat ft =  new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        String entryTime = ft.format(date);
        
        String query = "select * from entries where visiting_ID = " + "'" + visitingID + "';";
        ResultSet resultset = ConnectionDB.executeQueryResultSet(query);
        
        if(resultset.next())
        {
        	 String dataBaseEntryTime = resultset.getString("entry_time");
             if(dataBaseEntryTime == null)
             {
             	query = "update entries set entry_time = " + "'" + entryTime + "'" + " where visiting_ID = " + "'" + visitingID + "';";
             	ConnectionDB.executeQuery(query);
             }
        }  
	}
	
	private void editEntry(String visitingID, String fName, String lName, String aadharNum, String purpose)
	{
		String query = "update entries set first_name = " + "'" + fName + "'" + ", last_name = " + "'" + lName + "'" + ", aadhar_num = " + "'" + aadharNum + "'" + ", purpose = " + "'" + purpose + "'" + "where visiting_ID = " + "'" + visitingID + "';";
		ConnectionDB.executeQuery(query);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		ifNotAuthenticated(req, resp);
		
		String visitingID = req.getParameter("visitingID");
		String fName = req.getParameter("firstName");
		String lName = req.getParameter("lastName");
		String aadharNum = req.getParameter("aadharNum");
		String purpose = req.getParameter("purpose");
		
		editEntry(visitingID, fName, lName, aadharNum, purpose);
		
		try 
		{
			editEntryTime(visitingID);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		HttpSession session = req.getSession();
		session.setAttribute("editEntryModal", true); // this is added when no exception is throw and // for showing popup of success
		resp.sendRedirect(req.getContextPath() + "/showEntries_1");
	}

}
