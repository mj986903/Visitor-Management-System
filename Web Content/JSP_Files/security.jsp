<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="login.LocalConstants" %>    

<%response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");%> <%-- Tells browser to not store this page on cache --%>
<%response.setHeader("Pragma", "no-cache");%> <%-- Tells browser to not store this page on cache, for older versions of http --%>

<%if(session.getAttribute("loggedIn") == null || LocalConstants.isSecurityPersonnel == false)
	{
		response.sendRedirect(request.getContextPath() + "/HTML/loginPage.html");
	}
%>



<%	
	session.removeAttribute("entryMadeModal"); // when entry created successfully ---> attribute verify.java
	session.removeAttribute("otpError"); // mobOTPForm.jsp
	if(session.getAttribute("visitorData") != null)
	{
		session.removeAttribute("visitorData"); // if user redirects itself from otp page to options page
	}
%> 
 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Security</title>
<link rel="stylesheet" href="../CSS/navBar.css">
<link rel="stylesheet" href="../CSS/security.css">
</head>
<body>
    <header>
        <nav id="navBar">
            <div id="navDiv">
                <img src="../Images/logo.png" alt="" height="65%">
                <span id="logo">Visitor Management</span>
                <ul>
                   	<li><a href="/Development/JSP_Files/entryForm.jsp">Create</a></li>
            		<li><a href="/Development/showEntries_1">View</a></li>
            		<li><a href="/Development/JSP_Files/inviteVisitorForm.jsp">Invite</a></li>
                    <li><a href="/Development/Logout">Logout</a></li>
                </ul>
            </div>
        </nav>
    </header>
    
    <div class="container">
        <div id="user-name">
            <%out.println(session.getAttribute("emp_id"));%>
        </div>
            <form action="choices" method="post">
                <select name="chooseOption" id="chooseOption">
                    <option value="create">Create Entry</option>
                    <option value="view">View Entries</option>
                    <option value="invite">Invite Visitor</option>
                </select>
                <button type="submit" class="btn">Select</button>
            </form>
    </div>
    

</body>
</html>