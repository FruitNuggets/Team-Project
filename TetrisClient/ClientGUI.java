package TetrisClient;

import javax.swing.*;

import Controller.GameController;
import Controller.KeyController;
import Controller.RemoteController;
import TetrisServer.Server;
import view.LauncherJPanel;
import view.OfflinePanel;
import view.OnlinePanel;

import java.awt.*;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

public class ClientGUI extends JFrame
{
 

	private String[] title={"Tetris Single Mode","Tetris Online Mode"};
	private int[] size={450,750};
  private Client client;
  private InitialControl initialC;
  private LoginControl loginC;
  private CreateAccountControl accountC;
  private JPanel initialP;
  private JPanel loginP;
  private CreateAccountPanel accountP;
  private JPanel container;
  private CardLayout cardLayout;
  private LauncherJPanel gamePanel;
  private String address;
  private int port;
  // Constructor that creates the client GUI.
  public ClientGUI() throws IOException
  {
    
    // Set the title and default close operation.
    this.setTitle("Tetris Client");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    // Create the card layout container.
    cardLayout = new CardLayout();
    container = new JPanel(cardLayout);
  
    initialC = new InitialControl(container); 
    initialP = new InitialPanel(initialC);
    
    initialC.setClientFrame(this);
    // Add the views to the card layout container.
    container.add(initialP, "1");
   
    // Show the initial view in the card layout.
    cardLayout.show(container, "1");
    
    // Add the card layout container to the JFrame.
    getContentPane().add(container, BorderLayout.CENTER);

    // Show the JFrame.
    this.setSize(552, 570);
    this.setVisible(true);
  }
  
  public void correctLogin() throws IOException
  {
  	this.client.setUsername(loginC.getUsername());
  	cardLayout.show(container, "4");
  }
  
  public void startServer() throws InterruptedException
  {
  	try
		{
			client.sendToServer("Start");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
  
  public void correctAccount()
  {
  	cardLayout.show(container, "2");
  }
  
  public void usernameInUse()
  {
  	accountP.setError("Username has already been selected");
  }
  
  public void incorrectLogin()
  {
  	loginC.displayError("Username/Password is incorrect");
  }

  // Main function that creates the client GUI when the program is started.
  public static void main(String[] args) throws IOException
  {
    new ClientGUI();
  }
  
  public void setClient(Client client)
  {
	  this.client = client;
  }
  
  public void addtoContainer()
  {
	    loginC = new LoginControl(container, client); //Probably will want to pass in ChatClient here
	    accountC = new CreateAccountControl(container, client);
	    gamePanel = new LauncherJPanel(this);
	    
	    // Create the four views. (need the controller to register with the Panels
	    loginP = new LoginPanel(loginC);
	    accountP = new CreateAccountPanel(accountC);
	    
	    container.add(loginP, "2");
	    container.add(accountP, "3");
	    container.add(gamePanel, "4");
  }
  
  public void chooseMode(int mode){
		System.out.println("mode"+mode);
		switch (mode){
		case 0:
			OfflinePanel offlinePanel=new OfflinePanel();
			GameController.localController=new GameController(offlinePanel);
			offlinePanel.setLocalController(GameController.localController);
			this.setContentPane(offlinePanel);
			this.addKeyListener(new KeyController(GameController.localController));
			// renew
			this.setTitle(title[0]);
			this.setSize(size[0],470);
			GameController.localController.gameStart();
			break;
		case 1:
			JOptionPane.showMessageDialog(this, "IP Address: " + getServer().toString());
			String port=JOptionPane.showInputDialog("Please enter the room number:");
			Server.Init(Integer.parseInt(port));
			System.out.println("Connect Success");
			
			OnlinePanel onlinePanel=new OnlinePanel();
			GameController.localController=new GameController(Server.getExchangeThread(),onlinePanel, client);
			RemoteController.remoteController=new RemoteController(onlinePanel);
			onlinePanel.setLocalController(GameController.localController);
			onlinePanel.setRemoteController(RemoteController.remoteController);
			this.setContentPane(onlinePanel);
			this.addKeyListener(new KeyController(GameController.localController));
			// renew
			this.setTitle(title[1]);
			this.setSize(size[1],470);
			GameController.localController.gameStart();
			break;
		case 2:
			String address = JOptionPane.showInputDialog("Please enter the IP Address for the room: ");
			String port2=JOptionPane.showInputDialog("Please enter the room number:");
			Client.Init(Integer.parseInt(port2), address);
			System.out.println("Connect success");

			OnlinePanel onlinePanel2=new OnlinePanel();
			GameController.localController=new GameController(Client.getExchangeThread(),onlinePanel2, client);
			RemoteController.remoteController=new RemoteController(onlinePanel2);
			onlinePanel2.setLocalController(GameController.localController);
			onlinePanel2.setRemoteController(RemoteController.remoteController);
			this.setContentPane(onlinePanel2);
			this.addKeyListener(new KeyController(GameController.localController));
			// renew
			this.setTitle(title[1]);
			this.setSize(size[1],470);
			GameController.localController.gameStart();
			break;
		}
		requestFocus();
	}

	public void setAddress(String address) 
	{
		this.address = address;
	}
	
	public void setPort(int i)
	{
		this.port = i;
	}
	
	public int getPort()
	{
		return this.port;
	}
	
	public String getAddress()
	{
		return this.address;
	}
	
	public ArrayList<String> getServer()
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
}
