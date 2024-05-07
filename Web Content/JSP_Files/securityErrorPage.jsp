<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <%response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");%> <%-- Tells browser to not store this page on cache --%>
	<%response.setHeader("Pragma", "no-cache");%> <%-- Tells browser to not store this page on cache, for older versions of http --%>

	
	<%if(session.getAttribute("loggedIn") == null)
	{
		response.sendRedirect(request.getContextPath() + "/HTML/loginPage.html");
	}
	%>
	
   <%
	  if(session.getAttribute("visitorData") == null)
	  {
		  response.sendRedirect(request.getContextPath() + "/JSP_Files/entryForm.jsp");
	  }
   %>
	
	<%session.removeAttribute("visitorData"); %>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error</title>
<link rel="stylesheet" href="../CSS/navBar.css">
<style>
    @import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@300&display=swap');

    .container {
        display: flex;
        background-color: #FBAB7E;
        background-image: linear-gradient(62deg, #FBAB7E 0%, #fbc531 100%);

        display: flex;
        align-items: center;
        justify-content: center;
        background-color: cadetblue;
        width: 100vw;
        height: 92vh;
    }

    .inner-box
    {
        background-color: white;
        width: 35%;
        height: 45%;
        font-size: 1.7rem;
        font-weight: 500;
        font-family: 'Montserrat', sans-serif;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        border-radius: 8px;
    }

    .btn-small {
    background-color: rgb(234, 167, 42);
    border-radius: 100px;
    color: white;
    cursor: pointer;
    display: inline-block;
    font-family: CerebriSans-Regular, -apple-system, system-ui, Roboto, sans-serif;
    padding: 9px 20px;
    text-align: center;
    text-decoration: none;
    transition: all 250ms;
    border: 0;
    font-size: 13px;
    user-select: none;
    -webkit-user-select: none;
    touch-action: manipulation;
}

.btn-small:hover {
    color: black;
    background-color: rgb(234, 167, 42);
}

.btn-small:focus {
	border: none;
	outline: none;
}

.btn-small:active {
	box-shadow: 0.4px 0.4px 3px 0px;
}

</style>
</head>
<body>
	<jsp:include page="../HTML/logoutNavSecurity.html"/>
	<div class="container">
        <div class="inner-box">
            <div style="margin-bottom: 3rem; font-size: 25px; color: rgb(255, 0, 0); font-weight: bolder;">Something Went Wrong!</div> 
            <a href="/Development/JSP_Files/security.jsp"><button class="btn-small">Ok</button></a>
        </div>
	</div>
</body>
</html>