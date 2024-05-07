package database;

import java.sql.*;

public class ConnectionDB 
{
	private static String databaseURL = "jdbc:mysql://localhost:3306/visit";
	private static String databaseUserName = "root";
	private static String databasePassword = "Mj@986903";
	

    public static Connection makeConnection() throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(databaseURL, databaseUserName, databasePassword);
        return connection;
    }

	public static ResultSet getResult(String query, String[] queryConditionVars)
	{
		try
        {
            Connection connection = makeConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            
            if(queryConditionVars != null) // if query have variables, otherwise I pass null
            {
            	for(int i = 0; i < queryConditionVars.length; i++)
                {
                	preparedStatement.setString(i + 1, queryConditionVars[i]);
                }
            }
            
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		return null; // if something happened wrong resultset will be not initialized
	}
	
	public static void executeQuery(String query)
	{	
		try
        {
            Connection connection = makeConnection();
            Statement stmt = connection.createStatement();
            stmt.execute(query);
        }
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static ResultSet executeQueryResultSet(String query)
	{	
		try
        {
            Connection connection = makeConnection();
            Statement stmt = connection.createStatement();
            ResultSet resultset = stmt.executeQuery(query);
            return resultset;
        }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}	
}
