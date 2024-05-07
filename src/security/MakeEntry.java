package security;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


import database.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import security.VisitorData;

public class MakeEntry 
{   
    private static String firstName, lastName, mobNum, aadharNum, purpose, timeData;
    private static String query, visitingID;

    public static String randomString()
    {
    	String characters = "0123456789";
		Random random = new Random();
		char visitingID[] = new char[7];
		
		visitingID[0] = '#';
		for(int i = 1; i < 7; i++) 
		{
        	visitingID[i] = characters.charAt(random.nextInt(characters.length()));
      	}
		return new String(visitingID);
    }
    
    public static boolean is_visitingID_unique() throws SQLException
    {   
        String query = "select visiting_ID from entries where visiting_ID=?;";
        String[] queryConditionVars = new String[1];
        queryConditionVars[0] = MakeEntry.visitingID;
        ResultSet resultset = ConnectionDB.getResult(query, queryConditionVars);
        
        if(resultset.next() == false)
        {
            return true;
        }
        return false;
    }
    
    
    public static boolean is_visitingID_unique(String visitingID) throws SQLException
    {   
        String query = "select visiting_ID from entries where visiting_ID=?;";
        String[] queryConditionVars = new String[1];
        queryConditionVars[0] = visitingID;
        ResultSet resultset = ConnectionDB.getResult(query, queryConditionVars);
        
        if(resultset.next() == false)
        {
            return true;
        }
        return false;
    }

    private static void setVisitingID() throws SQLException
    {	
    	MakeEntry.visitingID = randomString();
        while(is_visitingID_unique() == false)
        {
            MakeEntry.visitingID = randomString();
        }
    }
    
    private static void setValues(HttpServletRequest req)
    {
    	HttpSession session = req.getSession();
    	VisitorData data = (VisitorData)session.getAttribute("visitorData");
    	
        MakeEntry.firstName = data.firstName;
        MakeEntry.lastName = data.lastName;
        MakeEntry.mobNum = data.mobNum;
        MakeEntry.aadharNum = data.aadharNum;
        MakeEntry.purpose = data.purpose;
        
        Date date = new Date();
        SimpleDateFormat ft =  new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        MakeEntry.timeData = ft.format(date);
    }

    public static void makeEntry(HttpServletRequest req) throws SQLException
    {
        setValues(req);
        setVisitingID();
        MakeEntry.query = "insert into entries values(" + "'" + MakeEntry.visitingID + "'" + "," + "'" + MakeEntry.firstName + "'" + "," + "'" + MakeEntry.lastName + "'" + "," + MakeEntry.mobNum + "," + MakeEntry.aadharNum + "," + "'" + MakeEntry.purpose + "'" + "," + "'" + MakeEntry.timeData + "'"+ ", null);";
        
        ConnectionDB.executeQuery(MakeEntry.query);
    }
}
