package Verification;


import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import Verification.SendOTPOnMobile; // self made class
import database.*; // self made package
import security.MakeEntry; // self made

@WebServlet("/SendEmail")
public class SendEmail extends HttpServlet 
{	
	
	private static boolean validInputsOrNot(String mobNum, String aadharNum, HttpServletRequest req, HttpServletResponse resp)
	{
		if(mobNum.length() != 10)
		{	
			HttpSession session = req.getSession();
			session.setAttribute("errorMessage", "Mobile number must be of 10 digits.");
			
			try 
			{
				resp.sendRedirect(req.getContextPath() + "/JSP_Files/newJoineeForm.jsp");
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
				resp.sendRedirect(req.getContextPath() + "/JSP_Files/newJoineeForm.jsp");
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
				resp.sendRedirect(req.getContextPath() + "/JSP_Files/newJoineeForm.jsp");
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
				resp.sendRedirect(req.getContextPath() + "/JSP_Files/newJoineeForm.jsp");
				return false;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		return true;
	}
	
	protected static void sendEmail(String sub, String mssg, String to, String from) 
	{
        String password = "xidripgdfxbfbuap";     
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
         
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() 
        {
        	protected PasswordAuthentication getPasswordAuthentication() 
            {
        		return new PasswordAuthentication(from, password);
            }
        });
         
          try 
          {
              MimeMessage message = new MimeMessage(session);
              message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
              message.setSubject(sub);
              message.setText(mssg);
              
              //send message
              Transport.send(message);
              System.out.println(mssg);
          } 
          catch (MessagingException e) 
          {
        	  throw new RuntimeException(e);
          }
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
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
		sendEmail(subject, mssg, toEmail, fromEmail);
		
		String query = "insert into entries (visiting_ID, first_name, last_name, mob_num, aadhar_num, purpose, entry_time, exit_time) values (" + "'" + visitingID + "', " + "'" + firstName + "', " + "'" + lastName + "' ," + "'" + mobNum + "', " + "'" + aadharNum + "', " + "'Joinee', " + "null, null);";		
		ConnectionDB.executeQuery(query);
		
		HttpSession session = req.getSession();
		session.setAttribute("sucessModal", true);
		resp.sendRedirect(req.getContextPath() + "/JSP_Files/newJoineeForm.jsp");
	}
}
