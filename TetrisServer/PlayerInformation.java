package TetrisServer;

import java.sql.SQLException;
import java.util.ArrayList;

public class PlayerInformation
{

	private Server server;
	
	public PlayerInformation(Server server)
	{
		this.server = server;
	}
	
	public ArrayList<String> getClientInfo() throws SQLException
	{
		ArrayList<String> client = new ArrayList<String>();
		ArrayList<String> returnInfo = new ArrayList<String>();
		
		client = server.getClients();
		
		for(String info : client)
		{
			String[] tokens = info.split(",");
			String username = tokens[0];
			String id = tokens[1];
			returnInfo.add("Username: " + username + "\nClient ID: " + id + "\nWins: " + server.getClientWins(username));
		}
		
		return returnInfo;
	}
}
