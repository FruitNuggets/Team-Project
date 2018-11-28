package TetrisServer;

import java.util.*;
import java.io.*;
import java.sql.*;

public class Database
{
  private Connection conn;
  private Statement stmt;
  private ResultSet rs;
  private ResultSetMetaData rmd;
  //Add any other data fields you like – at least a Connection object is mandatory
  public Database() throws IOException
  {
    //Read properties file
    Properties prop = new Properties();
    FileInputStream fis = new FileInputStream("db.properties");
    prop.load(fis);
    String url = prop.getProperty("url");
    String user = prop.getProperty("user");
    String pass = prop.getProperty("password"); 
    try
    {
      //Perform the Connection
      conn = DriverManager.getConnection(url,user,pass);
      System.out.println("Connection Success");
    } 
    catch (SQLException e)
    {
      // TODO Auto-generated catch block
      e.toString();
    } 
  }
  
  public ArrayList<String> query(String query) throws SQLException
  {
  	ArrayList<String> result = new ArrayList<String>();
    //Create a statement
    stmt=conn.createStatement();  
    //Execute a query
    rs=stmt.executeQuery(query);
    if(rs != null)
    {
      while(rs.next()) 
      {
        result.add(rs.getString(1)+", "+rs.getString(2));
      }
    }
    else
    {
    	result = null;
    }
  	return result;
    //Add your code here
  }
  
  public void executeDML(String dml) throws SQLException
  {
    //Create a statement
    stmt=conn.createStatement();  
    
    //Execute a DML statement
    stmt.execute(dml);
  }
  
}
