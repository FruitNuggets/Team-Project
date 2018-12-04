package TetrisJTests;

import static org.junit.Assert.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Test;
import TetrisServer.Database;

public class DatabaseTest
{
	private String[] users = {"jsmith","msmith","tjones","jjones"};
  private String[] passwords = {"hello123","pass123","123456","hello1234"};
  private Database db;
  private Connection conn;
	private int rando;
	
	@Test
	public void testSetConnection() throws Exception 
	{
		db = new Database();
		conn = db.getConnection();
		System.out.println(conn.toString());
		assertNotNull("Check connection", conn);
		conn.close();
	}
	@Test
	public void testQuery() throws SQLException, IOException 
	{
		db = new Database();
		conn = db.getConnection();
		assertNotNull("Check connection", conn);
	  //Use Random # to extract username/ and expected password
	  rando = ((int)Math.random()*users.length); 
	  String username = users[rando]; 
	  String expected = passwords[rando];
			
	  //get actual result (invoke query with username
	  ArrayList<String> result = db.query("Select username, aes_decrypt(password, 'key') from userdata where userdata.username = '" + username + "'");
	 
	  if(result != null)
	  {
		 	for(String res : result)
			{
				String[] tokens = res.split(",");
				String actual = tokens[1].trim();
				System.out.println("actual: " + actual + " expected: " + expected);
				assertEquals(actual, expected);
			}
	  }
	  conn.close();
	}
	
	@Test
	public void testExecuteDML() throws SQLException, IOException
	{
		db = new Database();
		conn = db.getConnection();
		assertNotNull("Check connection", conn);
		
		String username = "rclingan";
		String password = "abc123";
		db.executeDML("Insert into userdata "
				+ "values('" + username + "', aes_encrypt('" + password + "', 'key'))");
		conn.close();
	}
}
