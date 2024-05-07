package showEntries;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import database.*;

@WebServlet("/DataLimit")
public class DataLimit extends HttpServlet 
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
	
	
	private String getThreeMonthDate()
	{	
		Date date = new Date();
		SimpleDateFormat ft =  new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		String currDate = ft.format(date);
		
		String month = currDate.substring(5, 7);
		
		if(Integer.parseInt(month) - 3 <= 0)
		{	
			if(Integer.parseInt(month) - 3 == 0)
			{
				month = "12";
			}
			else if(Integer.parseInt(month) - 3 == -1)
			{
				month = "11";
			}
			else
			{
				month = "10";
			}
			return currDate.substring(0, 5) + month + (Integer.parseInt(currDate.substring(7)) - 1);
		}
		
		month = String.valueOf(Integer.parseInt(month) - 3);
		return currDate.substring(0, 5) + month + currDate.substring(7);
	}
	
	private String getSixMonthDate()
	{
		Date date = new Date();
		SimpleDateFormat ft =  new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		String currDate = ft.format(date);
		
		String month = currDate.substring(5, 7);
		
		if(Integer.parseInt(month) - 6 <= 0)
		{	
			if(Integer.parseInt(month) - 6 == 0)
			{
				month = "12";
			}
			else if(Integer.parseInt(month) - 6 == -1)
			{
				month = "11";
			}
			else if(Integer.parseInt(month) - 6 == -2)
			{
				month = "10";
			}
			else if(Integer.parseInt(month) - 6 == -3)
			{
				month = "09";
			}
			else if(Integer.parseInt(month) - 6 == -4)
			{
				month = "08";
			}
			else
			{
				month = "07";
			}
			return currDate.substring(0, 5) + month + (Integer.parseInt(currDate.substring(7)) - 1);
		}
		
		month = String.valueOf(Integer.parseInt(month) - 6);
		return currDate.substring(0, 5) + month + currDate.substring(7);
	}
	
	private String getPreviousYearDate()
	{
		Date date = new Date();
		SimpleDateFormat ft =  new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		String currDate = ft.format(date);
		
		String year = String.valueOf(Integer.parseInt(currDate.substring(0, 4)) - 1);
		return year + currDate.substring(4);
	}
	
	private ResultSet getEntries(String dataLimit)
	{	
		ResultSet resultset = null;
		String query = null, date = null;
		
		if(dataLimit.equals("oneMonth") == true)
		{	
			date = getPreviousMonthDate();
		}
		else if(dataLimit.equals("threeMonths") == true)
		{
			date = getThreeMonthDate();	
		}
		else if(dataLimit.equals("sixMonths") == true)
		{
			date = getSixMonthDate();
		}
		else if(dataLimit.equals("oneYear") == true)
		{
			date = getPreviousYearDate();
		}
		else
		{
			query = "select * from entries order by entry_time desc;";
		}
		
		if(query == null)
		{
			query = "select * from entries where entry_time >= " + "'" + date + "'" + " order by entry_time desc;";
		}
		
		resultset = ConnectionDB.executeQueryResultSet(query);
		return resultset;
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{	
		ifNotAuthenticated(req, resp);
		
		String dataLimit = req.getParameter("dataQuantity");
		ResultSet resultset = getEntries(dataLimit);
		
		HttpSession session = req.getSession();
		session.setAttribute("entries", resultset);
		
		resp.sendRedirect(req.getContextPath() + "/JSP_Files/showData.jsp");
	}
}
