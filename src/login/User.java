package login;

import java.io.*;
import java.sql.*;


import database.*; // self made package
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/HTML/login-verification")
public class User extends HttpServlet
{    
    public void setUserType(String user_type)
    {	
    	LocalConstants.isSecurityPersonnel =  false;
        LocalConstants.isEmployee = false;
        LocalConstants.isHR = false;
        
    	if((user_type.toLowerCase()).equals(LocalConstants.HR))
    	{
    		LocalConstants.isHR = true;
    	}
    	else if((user_type.toLowerCase()).equals(LocalConstants.SECURITY_PERSONNEL) == true)
    	{
    		LocalConstants.isSecurityPersonnel = true;
    	}
    	else if((user_type.toLowerCase()).equals(LocalConstants.EMPLOYEE))
    	{
    		LocalConstants.isEmployee = true;
    	}
    }
    
    
    public void redirectAccordingToUserType(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {	
    	if(LocalConstants.isSecurityPersonnel == true)
        {	
            resp.sendRedirect(req.getContextPath() + "/JSP_Files/security.jsp");
        }
		else if(LocalConstants.isHR == true)
		{
			resp.sendRedirect(req.getContextPath() + "/JSP_Files/hr.jsp");
		}
		else if(LocalConstants.isEmployee == true)
		{
			resp.sendRedirect(req.getContextPath() + "/JSP_Files/inviteVisitorForm.jsp");
		}
    }
    
    
    public boolean isAuthenticated(String emp_id, String password) throws Exception
    {   
    	String query = "select * from login where username=? and password=?;";
    	String queryConditionVars[] = new String[2];
    	queryConditionVars[0] = emp_id;
    	queryConditionVars[1] = password;
    	
    	ResultSet resultset = ConnectionDB.getResult(query, queryConditionVars);
    	
    	if(resultset.next() == true)
        {	
    		String user_type = resultset.getString("user_type");
          	setUserType(user_type);
          	return true;
        }
        return false;
    }

    
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {	
    	resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // (HTTP 1.1) tells browser to not store this page in cache
    	resp.setHeader("Pragma", "no-cache"); // for older versions of HTTP
    	
        LocalConstants.emp_id = req.getParameter("emp_id");
        LocalConstants.password = req.getParameter("password");
        
        try 
        {
			if(isAuthenticated(LocalConstants.emp_id, LocalConstants.password) == true)
			{	
				HttpSession session = req.getSession();
				session.setAttribute("loggedIn", true);
				session.setAttribute("emp_id", LocalConstants.emp_id);
				
				redirectAccordingToUserType(req, resp);
			}
			else
			{	
				resp.sendRedirect("/Development/HTML/loginPage.html");
			}
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		}
    }
}