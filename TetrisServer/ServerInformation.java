package TetrisServer;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class ServerInformation
{

	private Server server;
	
	public ServerInformation(Server server)
	{
		this.server = server;
	}
	
	public ArrayList<String> getServerIP() throws UnknownHostException
	{
		ArrayList<String>ip = new ArrayList<String>();
		try {
		    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		    while (interfaces.hasMoreElements()) {
		        NetworkInterface iface = interfaces.nextElement();
		        // filters out 127.0.0.1 and inactive interfaces
		        if (iface.isLoopback() || !iface.isUp())
		            continue;

		        Enumeration<InetAddress> addresses = iface.getInetAddresses();
		        while(addresses.hasMoreElements()) {
		            InetAddress addr = addresses.nextElement();

		            // *EDIT*
		            if (addr instanceof Inet6Address) continue;

		            ip.add(addr.getHostAddress());
		            System.out.println(iface.getDisplayName() + " " + ip);
		        }
		    }
		} catch (SocketException e) {
		    throw new RuntimeException(e);
		}
		return ip;
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
