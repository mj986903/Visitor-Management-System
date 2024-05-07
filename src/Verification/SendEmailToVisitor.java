package Verification;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import database.ConnectionDB;
import security.MakeEntry;
import login.LocalConstants;

//this file is almost copy of SendEmail

@WebServlet("/SendEmailToVisitor")
public class SendEmailToVisitor extends HttpServlet 
{	
	protected static boolean validInputsOrNot(String mobNum, String aadharNum, HttpServletRequest req, HttpServletResponse resp)
	{
		if(mobNum.length() != 10)
		{	
			HttpSession session = req.getSession();
			session.setAttribute("errorMessage", "Mobile number must be of 10 digits.");
			
			try 
			{
				resp.sendRedirect(req.getContextPath() + "/JSP_Files/inviteVisitorForm.jsp");
				return false;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		if(mobNum.matches("[0-9]+") == false)
		{	
			HttpSession session = req.getSession();
			session.setAttribute("errorMessage", "Mobile number must only contain numbers.");
			
			try 
			{
				resp.sendRedirect(req.getContextPath() + "/JSP_Files/inviteVisitorForm.jsp");
				return false;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		if(aadharNum.length() != 11)
		{	
			HttpSession session = req.getSession();
			session.setAttribute("errorMessage", "Aadhar card number must be of 10 digits.");
			
			try 
			{
				resp.sendRedirect(req.getContextPath() + "/JSP_Files/inviteVisitorForm.jsp");
				return false;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		if(aadharNum.matches("[0-9]+") == false)
		{	
			HttpSession session = req.getSession();
			session.setAttribute("errorMessage", "Aadhar card number must only contain numbers.");
			
			try 
			{
				resp.sendRedirect(req.getContextPath() + "/JSP_Files/inviteVisitorForm.jsp");
				return false;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		return true;
	}
	
	// this file is almost copy of SendEmail
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{	
		String fromEmail = "sharmamanish16111999@gmail.com";
		
		String toEmail = req.getParameter("email");
		String visitingID = MakeEntry.randomString();
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String mobNum = req.getParameter("mobNum");
		String aadharNum = req.getParameter("aadharNum");
		
		if(validInputsOrNot(mobNum, aadharNum, req, resp) == false) // redirect if not valid inputs
		{	
			// sendEmail throws execption when email attribute is empty string
			return;
		}
		
		try 
		{
			while(MakeEntry.is_visitingID_unique(visitingID) == false)
			{
				visitingID = MakeEntry.randomString();
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
		String subject = "Namaste " + firstName + "!";
		String mssg = "Hey " + firstName + "!\nYour Visiting ID For Namaste Visitor : " + visitingID + "\n";
		SendEmail.sendEmail(subject, mssg, toEmail, fromEmail);
		
		String query = "insert into entries (visiting_ID, first_name, last_name, mob_num, aadhar_num, purpose, entry_time, exit_time) values (" + "'" + visitingID + "', " + "'" + firstName + "', " + "'" + lastName + "' ," + "'" + mobNum + "', " + "'" + aadharNum + "', " + "'Visitor', " + "null, null);";		
		ConnectionDB.executeQuery(query);
		
		HttpSession session = req.getSession();
		session.setAttribute("sucessModal", true);
		resp.sendRedirect(req.getContextPath() + "/JSP_Files/inviteVisitorForm.jsp");
	}

}
