package security;

import java.io.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/JSP_Files/choices")
public class SecurityFunctionality extends HttpServlet
{   
    boolean createEntry, viewEntry, invite;
    
    private void ifNotAuthenticated(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {	
    	resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // tells browser to not store in cache
    	resp.setHeader("Pragma", "no-cache"); // for older version of http like http 1.0, etc
    	
    	
    	HttpSession session = req.getSession();
    	session.removeAttribute("exitEntryModal");
    	session.removeAttribute("editEntryModal");
    	session.removeAttribute("sucessModal"); // email sent when invite visitor or joinee
    	
    	if(session.getAttribute("loggedIn") == null)
    	{
    		resp.sendRedirect(req.getContextPath() + "/HTML/loginPage.html");
    	}
    }
    
    private void setFunctionalityVariable(String choosedFunctionality)
    {   
        this.createEntry = false;
        this.viewEntry = false;
        this.invite = false;

        if(choosedFunctionality.equals("create") == true)
        {
            this.createEntry = true;
        }
        else if(choosedFunctionality.equals("view") == true)
        {
            this.viewEntry = true;
        }
        else if(choosedFunctionality.equals("invite") == true)
        {
            this.invite = true;
        }
    }

    private void redirectAccordingToChoice(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        if(this.createEntry == true)
        {
            resp.sendRedirect(req.getContextPath() + "/JSP_Files/entryForm.jsp");
        }
        else if(this.viewEntry == true)
        {
            resp.sendRedirect(req.getContextPath() + "/showEntries_1");
        }
        else if(this.invite == true)
        {
        	resp.sendRedirect(req.getContextPath() + "/JSP_Files/inviteVisitorForm.jsp");
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {	
    	ifNotAuthenticated(req, resp);
        String choosedFunctionality = req.getParameter("chooseOption");
        setFunctionalityVariable(choosedFunctionality);
        redirectAccordingToChoice(req, resp);
    }
}
