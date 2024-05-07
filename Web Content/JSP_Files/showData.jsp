<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%@ page import="java.sql.*" %>
<%@ page import="login.LocalConstants" %>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    if(session.getAttribute("loggedIn") == null || LocalConstants.isSecurityPersonnel == false)
    {
        response.sendRedirect(request.getContextPath() + "/HTML/loginPage.html");
        return;
    }
%>


<%
	if(session.getAttribute("entries") == null) // when page is refreshed then entries object will become null and no entries is printed
	{
		response.sendRedirect(request.getContextPath() + "/showEntries_1");
	}
%>

    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="ISO-8859-1">
        <title>Entries</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css"
            integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="../CSS/navBar.css">
        <link rel="stylesheet" href="../CSS/showData.css">
        <link rel="stylesheet" href="../CSS/showData_2.css">
        <link rel="stylesheet" href="../CSS/showData_3.css">
    </head>
    <body>
    
   <div class="modal fade" id="editEntryModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	   <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-body">
	        Saved Successfully!
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn-small" id="editModalBtn">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="modal fade" id="exitEntryModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	   <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-body">
	        Exited Successfully!
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn-small" id="exitModalBtn">Close</button>
	      </div>
	    </div>
	  </div>
	</div>
    	
    
        <jsp:include page="../HTML/logoutNavSecurity.html" />
        	
        <div id="formBox">
            <div id="editExitFormTitle">
                <h2></h2>
                
            </div>
            <form action="/Development/ExitTime" method="post" id="exitTimeForm">
            	<div style="width: 100%">
            		<button type="button" class="close" aria-label="Close" style="color: black; margin-right: 8px;">
  					<span aria-hidden="true" style="font-size: 2.6rem;">&times;</span>
					</button>
				</div>
				
                <input name="visitingID" style="font-family: 'Montserrat', sans-serif; font-size:2rem; margin-bottom: 6rem; text-indent: 2.5rem; border: none;" class="inpt">
                <div id="form-inside">
                    <div id="left-div">
                    	<label for="firstNameExitForm" class="lable">First Name</label>
                        <input type="text" placeholder="First Name" id="firstNameExitForm" class="inpt">
                        <label for="mobNumExitForm" class="lable">Mobile Number</label>
                        <input type="text" placeholder="Mobile Number" id="mobNumExitForm" class="inpt">
                    </div>

                    <div id="right-div">
                    	<label for="lastNameExitForm" class="lable">Last Name</label>
                        <input type="text" placeholder="Last Name" id="lastNameExitForm" class="inpt">
                        <label for="aadharNumExitForm" class="lable">Aadhar Number</label>
                        <input type="text" placeholder="Aadhar Card Number" id="aadharNumExitForm" class="inpt">
                    </div>
                </div>
                <button type="submit" class="btn">Exit</button>
            </form>

            <form action="/Development/EditEntry" method="post" id="editForm">
            	<div style="width: 100%">
            		<button type="button" class="close" aria-label="Close" style="color: black; margin-right: 8px;">
  					<span aria-hidden="true" style="font-size: 2.6rem;">&times;</span>
					</button>
				</div>
			
            <input name="visitingID" style="font-family: 'Montserrat', sans-serif; font-size:2rem; margin-bottom: 6rem; text-indent: 2.5rem; border: none;" class="inpt">
                <div id="form-inside">
                    <div id="left-div">
                        <label for="editFormFirstName" class="lable">First Name</label>
                        <input type="text" placeholder="First Name" name="firstName" class="inpt" id="editFormFirstName">
                        <label for="editFormAadharNum" class="lable">Aadhar Number</label>
                        <input type="text" placeholder="AadharNum" name="aadharNum" class="inpt" id="editFormAadharNum">
                    </div>

                    <div id="right-div">
                        <label for="editFormLastName" class="lable">Last Name</label>
                        <input type="text" placeholder="Last Name" name="lastName" class="inpt" id="editFormLastName">
                        <label for="editFormPurpose" class="lable">Purpose</label>
                        <input type="text" placeholder="Purpose" name="purpose" class="inpt" id="editFormPurpose">
                    </div>
                </div>
                <button type="submit" class="btn">Save</button>
                
            </form>
        </div>

        <div class="containerr" id="container">
            <div class="upperBox">
                <div class="radio">
                    <input label="Search via other Attributes" type="radio" id="" name="searchOption"
                        value="other-options" checked>
                    <input label="Search via Date" type="radio" id="" name="searchOption" value="date-time">
                </div>

                <form action="/Development/SearchFunc" method="post" id="searchViaOtherAttributes">
                    <div id="searchViaOtherAttributesInp">
                        <select name="searchBy" class="inp">
                            <option value="visitingID">Visiting ID</option>
                            <option value="name">Name</option>
                            <option value="mobNum">Mobile Number</option>
                            <option value="aadharNum">Aadhar Card Number</option>
                        </select>
                        <input type="text" name="inpWord" class="inp" id="inpWord" required>
                    </div>

                    <div id="searchViaOtherAttributesBtn">
                        <button type="submit" class="btn-small">Search</button>
                    </div>
                </form>

                <form action="/Development/SearchFuncViaDate" id="searchViaDate" method="post">
                    <input type="date" name="startDate" id="" class="inp" style="margin-right: 12px" required>
                    <input type="date" name="endData" id="" class="inp" style="margin-right: 12px" required>
                    <button type="submit" class="btn-small">Search</button>
                </form>
            </div>

            <div class="lowerBox">
                <div id="sortingBox">
                    <select name="" id="sortData">
                        <option value="newest">Newest First</option>
                        <option value="oldest">Oldest First</option>
                        <option value="activeEntries">Active Entries</option>
                    </select>
                </div>
                <div class="tableBox" id="tableBoxOfShowEntries">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Visiting ID</th>
                                <th scope="col">First Name</th>
                                <th scope="col">Last Name</th>
                                <th scope="col">Mobile Number</th>
                                <th scope="col">Aadhar Card Number</th>
                                <th scope="col">Purpose</th>
                                <th scope="col">Entry</th>
                                <th scope="col">Exit</th>
                            </tr>
                        </thead>
                        <tbody id="tableBody">
        
                            <%	
                                ResultSet resultset = (ResultSet)session.getAttribute("entries");
                            	if(resultset != null)
                            	{
                            		for(int i = 1; resultset.next() == true; i++)
                                	{	
                                		String visitingID = resultset.getString("visiting_ID");
                                		String fName = resultset.getString("first_name");
                                		String lName = resultset.getString("last_name");
                                		String mobNum = resultset.getString("mob_num");
                                		String aadharNum = resultset.getString("aadhar_num");
                                		String purpose = resultset.getString("purpose");
                                		String entryTime = resultset.getString("entry_time");
                                		String exitTime = resultset.getString("exit_time");
                                		
                                		if(entryTime == null)
                                		{
                                			entryTime = "-----";
                                		}
                                		if(exitTime == null)
                                		{
                                			exitTime = "-----";
                                		}
                                		
                                		out.println("<tr>");
                                		
                                		out.println("<th scope='row'>"+ i + "</th>");
                                		out.println("<td>" + visitingID + "</td>");
                                		out.println("<td>" + fName + "</td>");
                                		out.println("<td>" + lName + "</td>");
                                		out.println("<td>" + mobNum + "</td>");
                                		out.println("<td>" + aadharNum + "</td>");
                                		out.println("<td>" + purpose + "</td>");
                                		out.println("<td>" + entryTime + "</td>");
                                		out.println("<td>" + exitTime + "</td>");
                                		
                                		if(exitTime != "-----")
                                		{	
                                			// not able to edit and change time
                                			out.println("<td><button type='button' class='btn-disabled'>Edit</button></td>"); // removed editBtns class
                                    		out.println("<td><button type='button' class='btn-disabled'>Exit</button></td>"); // removed exitTimeBtns class
                                		}
                                		else
                                		{
                                			out.println("<td><button type='button' class='btn-small editBtns'>Edit</button></td>");
                                    		out.println("<td><button type='button' class='btn-small exitTimeBtns'>Exit</button></td>");
                                		}
                                		
                                		
                                		out.println("</tr>");
                                	}
                            	}	
                            	session.removeAttribute("entries");
                            %>
                        </tbody>
                    </table>
                </div>

                <div id="dataQuantityAndResetDiv">

                    <form action="/Development/showEntries_1" method="get" id="resetForm">
                        <button type="submit" style="margin-right: 1rem" class="btn-small">Reset</button>
                    </form>

                    <form action="/Development/DataLimit" id="dataQuantityForm" method="post">
                        <select name="dataQuantity" id="dataQuantitySelcTag">
                            <option value="oneMonth">1 Month</option>
                            <option value="threeMonths">3 Months</option>
                            <option value="sixMonths">6 Months</option>
                            <option value="oneYear">1 Year</option>
                            <option value="all">All</option>
                        </select>
                        <button type="submit" style="margin-right: 1rem" class="btn-small">Get Entries</button>
                    </form>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"
            integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
            crossorigin="anonymous"></script>
        <script src="../JavaScript/showDataSearchOption.js"></script>
        <script src="../JavaScript/showDataExitForm.js"></script>
        <script src="../JavaScript/showDataSorting.js"></script>
        <script src="../JavaScript/showDataEditForm.js"></script>
        <script src="../JavaScript/showDataEmptyTable.js"></script>
        <script src="../JavaScript/editExitFormCloseBtn.js"></script>
        
        <%if(session.getAttribute("editEntryModal") != null)
        {	
        	// attribute is created at editEntry.java and removed at ifAuth function of securityFunctinality.java
        	// for showing modal of entry edited successfully
        	out.print("<script src='../JavaScript/editModal.js'></script>");
        	session.removeAttribute("editEntryModal");
        }
        %>
        
        <%if(session.getAttribute("exitEntryModal") != null)
        {	
        	// attribute is created at exitTime.java and removed at ifAuth function of securityFunctinality.java
        	// for showing modal of visitor exited successfully
        	out.print("<script src='../JavaScript/exitModal.js'></script>");
        	session.removeAttribute("exitEntryModal");
        }
        %>
        
    </body>

    </html>