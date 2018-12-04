package TetrisClient;

import java.io.IOException;
import java.net.Socket;

import MySocket.ExchangeThread;
import ocsf.client.AbstractClient;

public class Client extends AbstractClient
{
	private ClientGUI gui;
  private static ExchangeThread clientExchangeThread;
  private static Socket socket;
  private String username;
  
  public Client(ClientGUI gui)
  {
    super("localhost", 8300);
  	this.gui = gui;
  }
  
  public Client(String host, int port, ClientGUI gui)
  {
	  super(host, port); 
	  this.gui = gui;
  }

  @Override
  public void handleMessageFromServer(Object arg0)
  {
  	if(arg0.toString().equals("correct login"))
  	{
  		try
			{
				gui.correctLogin();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  	}
  	else if(arg0.toString().equals("username okay"))
  	{
  		gui.correctAccount();
  	}
  	else if(arg0.toString().equals("username in use"))
  	{
  		gui.usernameInUse();
  	}
  	else if(arg0.toString().equals("incorrect login"))
  	{
  		gui.incorrectLogin();
  	}
    System.out.println("Server Message sent to Client " + (String)arg0);

  }
  
  public void connectionException (Throwable exception) 
  {
    //Add your code here
  	System.out.println("Connection error: " + exception.toString());
  }
  public void connectionEstablished()
  {
    //Add your code here
  	System.out.println("Connected to the server");
  }

  public static void Init(int PORT, String host){
      try {
          socket = new Socket(host,PORT);
          System.out.println("IP:"+socket.getLocalAddress()+"port"+socket.getPort());

          clientExchangeThread=new ExchangeThread(socket);
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
  
  public Socket getSocket()
  {
  	return this.socket;
  }
  
  public static ExchangeThread getExchangeThread(){
      return clientExchangeThread;
  }
  
  public void addWin()
  {
  	try
		{
			this.sendToServer("I won, " + this.username);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
  public void setUsername(String username)
  {
  	this.username = username;
  }
}