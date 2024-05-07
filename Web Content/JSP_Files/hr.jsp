<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!-- This page is similar to security.jsp.....some of the things are taken from security.jsp like CSS files and HTML -->

<%@ page import="login.LocalConstants" %>



<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    if(session.getAttribute("loggedIn") == null || LocalConstants.isHR == false)
    {
        response.sendRedirect(request.getContextPath() + "/HTML/loginPage.html");
    }
%>


<%
	session.removeAttribute("sucessModal");
	session.removeAttribute("errorMessage"); 
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>HR</title>
<link rel="stylesheet" href="../CSS/navBar.css">
<link rel="stylesheet" href="../CSS/security.css">

</head>
<body>
    <header>
        <nav id="navBar">
            <div id="navDiv">
                <img src="../Images/logo.png" alt="" height="65%">
                <span id="logo">In Time Tec</span>
                <ul>
                	<li><a href="/Development/JSP_Files/newJoineeForm.jsp">Joinee</a></li>
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
            <form action="/Development/HrFunctionality" method="post">
                <select name="chooseOption" id="chooseOption">
                    <option value="newJoinee">Invite New Joinee</option>
                    <option value="invite">Invite Visitor</option>
                </select>
                <button type="submit" class="btn">Select</button>
            </form>
    </div>
    

</body>
</html>
</html>