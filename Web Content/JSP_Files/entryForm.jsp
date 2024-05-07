<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%@ page import="login.LocalConstants" %> 

<%response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");%> <%-- Tells browser to not store this page on cache --%>
<%response.setHeader("Pragma", "no-cache");%> <%-- Tells browser to not store this page on cache, for older versions of http --%>

<%if(session.getAttribute("loggedIn") == null || LocalConstants.isSecurityPersonnel == false)
	{
		response.sendRedirect(request.getContextPath() + "/HTML/loginPage.html");
	}
%> 


<%if(session.getAttribute("visitorData") != null) // if user press backword button in browser when OTP form is opened then it should be a reset
	{	
		session.removeAttribute("visitorData"); // if user redirects itself from otp page to options page
	}
%> 

    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="ISO-8859-1">
        <title>Entry Form</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="../CSS/navBar.css">
        <link rel="stylesheet" href="../CSS/entryForm.css">
    </head>

    <body>
    	
    	<div class="modal fade" id="entryCreatedModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	   <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-body" id="modalContentDiv">
	        Entry Created Successfully!
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn-small" id="entryCreatedModalBtn">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
    
        <jsp:include page="../HTML/logoutNavSecurity.html"/>
        <div class="containerr">
            <div class="titleDiv">
                <h2>Entry Form</h2>
            </div>
            
            <div id="center-div">
            	
            <%  if(session.getAttribute("errorMessage") != null) // this attributed is created in otpForm.jsp
				{
					out.print("<div class='alert alert-danger alert-dismissible fade show' role='alert' style='width: 100%;'><strong>Invalid Input Values! </strong>" + session.getAttribute("errorMessage") + "You should check in on some of those fields below.<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button></div>");
					
					// user entered wrong entries and submitted then to show mssg
					session.removeAttribute("errorMessage"); 
				}
			%> 
            	
                <form action="/Development/JSP_Files/mobOTPForm.jsp" method="post">
                    <div id="form-inside">
                        <div id="left-div">
                            <input type="text" placeholder="First Name" name="firstName" class="inp" required>
                            <input type="text" placeholder="Mobile Number" name="mobNum" class="inp" id="mobNum" minlength="10" maxlength="10" required>
                        </div>

                        <div id="right-div">
                            <input type="text" placeholder="Last Name" name="lastName" class="inp" required>
                            <input type="text" placeholder="Aadhar Card Number" name="aadharNum" class="inp" id="aadharNum" minlength="11" maxlength="11" required>
                        </div>
                    </div>
                    <div id="bottom-div">
                        <input type="text" placeholder="Purpose" name="purpose" class="inp center-inp" required>
                    </div>
                    <button type="submit" class="btn">Create Entry</button>
                </form>
            </div>
        </div>
    </body>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

<%
		if(session.getAttribute("entryMadeModal") != null)
        {	
        	// for showing modal of entry created successfully
        	out.print("<script src='../JavaScript/securityEntryCreatedModal.js'></script>");
        	session.removeAttribute("entryMadeModal"); // attribute is verify.java
        }
%>  

<%
		if(session.getAttribute("otpError") != null)
        {	
        	// for showing modal of otp error
        	out.print("<script src='../JavaScript/securityOTPNotSent.js'></script>");
        	session.removeAttribute("otpError"); // mobOTPForm.jsp
        }
%>      
</html>
    