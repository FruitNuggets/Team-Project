package TetrisJTests;

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import TetrisServer.Database;
import TetrisServer.EventHandler;
import TetrisServer.PlayerInformation;
import TetrisServer.Server;
import TetrisServer.ServerGUI;
import TetrisServer.ServerInformation;

public class ServerIntegrationTest
{

	private String[] users = {"jsmith","msmith","tjones","jjones"};
  private String[] passwords = {"hello123","pass123","123456","hello1234"};
  private Database db;
  private Connection conn;
	private int rando;
	
	@Test
	public void getCurrentClients()
	{
		ServerGUI gui = new ServerGUI();
		Server server = gui.getServer();
		//will be empty because there are no clients connected
		assertNotNull("Check Server Object", server.getClients());
	}
	
	@Test
	public void checkGUILog()
	{
		ServerGUI gui = new ServerGUI();
		assertTrue(gui.checkLog());
	}
	
	@Test
	public void getServerFromGUI()
	{
		ServerGUI gui = new ServerGUI();
		Server server = gui.getServer();
		assertNotNull("Check Server Object", server);
	}
	
	@Test
	public void getPlayerInformation()
	{
		ServerGUI gui = new ServerGUI();
		PlayerInformation player = new PlayerInformation(gui.getServer());
		ArrayList<String> players = new ArrayList<String>();
		try
		{
			players = player.getClientInfo();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//will be empty because no clients are connected currently
		System.out.println(players.toString());
		assertNotNull("Check Client Information", players);
	}
	
	@Test
	public void getClientWins()
	{
		ServerGUI gui = new ServerGUI();
		Server server = gui.getServer();
		String wins = "";
	  rando = ((int)Math.random()*users.length); 
	  String username = users[rando]; 
		try
		{
			wins = server.getClientWins(username);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Username: " + username + " Wins: " +  wins);
		assertNotNull("Check Client Wins", wins);
	}
	
	@Test
	public void getServerInformation()
	{
		ServerGUI gui = new ServerGUI();
		ServerInformation server = new ServerInformation(gui.getServer());
		ArrayList<String> serverinfo = new ArrayList<String>();
		try
		{
			serverinfo = server.getServerIP();
		} catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull("Check Client Information", serverinfo);
	}
}
