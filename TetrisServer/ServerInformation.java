package TetrisServer;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerInformation
{

	private Server server;
	
	public ServerInformation(Server server)
	{
		this.server = server;
	}
	
	public String getServerIP() throws UnknownHostException
	{
		InetAddress address;
		address = InetAddress.getLocalHost();
		
		return address.getHostAddress();
	}
	
	public int getPortNumber()
	{
		return server.getPort();
	}
	
	public String getServerName() throws UnknownHostException
	{
		InetAddress name;
		name = InetAddress.getLocalHost();
		return name.getHostName();
	}
	
}
