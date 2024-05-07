<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="Verification.*" %>
    <%@ page import="security.*" %>
    <%@ page import="login.LocalConstants" %> 
	   
    <%response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");%> <%-- Tells browser to not store this page on cache --%>
	<%response.setHeader("Pragma", "no-cache");%> <%-- Tells browser to not store this page on cache, for older versions of http --%>

	
	<%if(session.getAttribute("loggedIn") == null || LocalConstants.isSecurityPersonnel == false)
	{
		response.sendRedirect(request.getContextPath() + "/HTML/loginPage.html");
	}
	%>
	
	<%	
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String aadharNum = request.getParameter("aadharNum");
		String purpose = request.getParameter("purpose");
    	String mobNum = request.getParameter("mobNum");
		String mssg = "Your OTP for Namaste Visitor - In Time Tec : ";
    %>
    
    
    <%	
    	// security checks of input values
    	if(session.getAttribute("visitorData") == null) // if hiiting page first time
    	{	
    		if(mobNum == null || aadharNum == null)
    		{
    			session.setAttribute("errorMessage", true);
    			response.sendRedirect(request.getContextPath() + "/JSP_Files/entryForm.jsp");
    			return; // apiStatusCode line throws exception when code jumps into this if block. So, I returned servlet method here
    		}
    		if(mobNum.length() != 10)
    		{	
    			session.setAttribute("errorMessage", "Mobile number must be of 10 digits.");
    			response.sendRedirect(request.getContextPath() + "/JSP_Files/entryForm.jsp");
    			return; // apiStatusCode line throws exception when code jumps into this if block. So, I returned servlet method here
    		}
    		if(mobNum.matches("[0-9]+") == false)
    		{	
    			session.setAttribute("errorMessage", "Mobile number must only contain numbers.");
    			response.sendRedirect(request.getContextPath() + "/JSP_Files/entryForm.jsp");
    			return; // apiStatusCode line throws exception when code jumps into this if block. So, I returned servlet method here
    		}
    		if(aadharNum.length() != 11)
    		{	
    			session.setAttribute("errorMessage", "Aadhar card number must be of 10 digits.");
    			response.sendRedirect(request.getContextPath() + "/JSP_Files/entryForm.jsp");
    			return; // apiStatusCode line throws exception when code jumps into this if block. So, I returned servlet method here
    		}
    		if(aadharNum.matches("[0-9]+") == false)
    		{	
    			session.setAttribute("errorMessage", "Aadhar card number must only contain numbers.");
    			response.sendRedirect(request.getContextPath() + "/JSP_Files/entryForm.jsp");
    			return; // apiStatusCode line throws exception when code jumps into this if block. So, I returned servlet method here
    		}
    	}
    %>
   
    
	<%	
		boolean hittingFirstTime = true;
		if(session.getAttribute("visitorData") == null) // first time hitting page
		{	
			try
			{
				int apiStatusCode = SendOTPOnMobile.sendOTP(mobNum, mssg);
				if(apiStatusCode != 200)
				{	
					session.setAttribute("otpError", true);
					response.sendRedirect(request.getContextPath() + "/JSP_Files/entryForm.jsp");
					return;
				}
			}
			catch(Exception e)
			{
				session.setAttribute("otpError", true);
				response.sendRedirect(request.getContextPath() + "/JSP_Files/entryForm.jsp");
				return;
			} 
		}	
	%>

	<%	
		if(session.getAttribute("visitorData") == null) // first time hitting page
		{
			VisitorData data = new VisitorData(firstName, lastName, mobNum, aadharNum, purpose);
			session.setAttribute("visitorData", data);	
		}
		else // redirected because otp is incorrect
		{
			hittingFirstTime = false;
		}
    %>
    
    
    

    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>OTP</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<!-- <link rel="stylesheet" href="../CSS/loginPage.css"> -->
<link rel="stylesheet" href="../CSS/navBar.css">
<link rel="stylesheet" href="../CSS/otpForm.css">

</head>
<body>
	<jsp:include page="../HTML/logoutNavSecurity.html"/>
	<div class="containerr">
		<div id="titleDiv">
			<h2>OTP</h2>
		</div>
		<form action="/Development/verifyOTP" method="post">
			<%
				if(hittingFirstTime == false)
				{
					out.println("<div class='alert alert-danger alert-dismissible fade show' role='alert' style='width: 100%; border-radius: 0;'><strong>Incorrect OTP! </strong> Please Enter Correct OTP.<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div>");
				}
				else
				{
					out.println("<div class='alert alert-success alert-dismissible fade show' role='alert' style='width: 100%; border-radius: 0;'><strong>OTP Sent Successfully! </strong><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div>");
				}
			%>
			
			<div id="inpDiv">
				<input type="text" placeholder="Enter OTP" class="inp" name="OTP">
				<button type="submit" class="btn">Enter</button>
			</div>
		</form>
	</div>
</body>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

<%
		if(session.getAttribute("otpError") != null)
        {	
        	// for showing modal of otp error
        	out.print("<script src='../JavaScript/securityOTPNotSent.js'></script>");
        	session.removeAttribute("otpError"); // mobOTPForm.jsp
        }
%>     
</html>