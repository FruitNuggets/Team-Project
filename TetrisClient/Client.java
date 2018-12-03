package TetrisClient;

import ocsf.client.AbstractClient;

public class Client extends AbstractClient
{
	private ClientGUI gui;
  
  public Client(ClientGUI gui)
  {
    super("localhost", 8300);
  	this.gui = gui;
  }
  
  public Client(String host, int port)
  {
	super(host, port); 
  }

  @Override
  public void handleMessageFromServer(Object arg0)
  {
  	if(arg0.toString().equals("correct login"))
  	{
  		gui.correctLogin();
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
}