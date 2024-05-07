package showEntries;

import java.io.IOException;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import database.*; // self made package

@WebServlet("/SearchFunc")
public class SearchFunc extends HttpServlet 
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
	
	
	
	private ResultSet getEntries(String searchType, String inpWord)
	{	
		String query = null;
		inpWord = inpWord.toLowerCase();
		
		if(searchType.equals("visitingID") == true)
		{
			query = "select * from entries where visiting_ID = " + "'" + inpWord + "' order by entry_time desc;";
		}
		else if(searchType.equals("name") == true)
		{	
			if(inpWord == "") // when name field is empty at form then it gives all entries because ''% means all the names in pattern matching
			{
				inpWord = "   ";
			}
			query = "select * from " + "(select *,  " + "CONCAT(first_name , ' ' , last_name)" + " as full_name from entries) tb where full_name like " + "'" + inpWord + "%' order by entry_time desc;";
		}
		else if(searchType.equals("mobNum") == true)
		{
			query = "select * from entries where mob_num = " + "'" + inpWord + "' order by entry_time desc;";
		}
		else if(searchType.equals("aadharNum") == true)
		{
			query = "select * from entries where aadhar_num = " + "'" + inpWord + "' order by entry_time desc;";
		}
		
		return ConnectionDB.executeQueryResultSet(query);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{	
		ifNotAuthenticated(req, resp);
		
		String searchType = req.getParameter("searchBy");
		String inpWord = req.getParameter("inpWord");
		
		ResultSet resultset = getEntries(searchType, inpWord);
	
		HttpSession session = req.getSession();
		session.setAttribute("entries", resultset);
		
		resp.sendRedirect(req.getContextPath() + "/JSP_Files/showData.jsp");
	}

}
