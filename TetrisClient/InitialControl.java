package TetrisClient;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class InitialControl implements ActionListener
{
  // Private data field for storing the container.
  private JPanel container;
  private InitialPanel initialPanel;
  private Client client;
  private ClientGUI clientGUI;
 
  // Constructor for the initial controller.
  public InitialControl(JPanel container)
  {
    this.container = container;
   
  }
  
  // Handle button clicks.
  public void actionPerformed(ActionEvent ae)
  {
    // Get the name of the button clicked.
    String command = ae.getActionCommand();
    
    // The Login button takes the user to the login panel.
    if (command.equals("Login"))
    {
    	if (initialPanel.checkInfo())
    	{
    	    client = new Client(initialPanel.getAddress(), initialPanel.getPort());
    	    try
    			{
    				client.openConnection();
    			} catch (IOException e)
    			{
    				// TODO Auto-generated catch block
    				System.out.println(e.toString());
    			}
    	    clientGUI.setClient(client);
    	    clientGUI.addtoContainer();
    	   LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
    	   loginPanel.setError("");
    	   CardLayout cardLayout = (CardLayout)container.getLayout();
    	   cardLayout.show(container, "2");
    	}
    	else
    	{
    		initialPanel.setError("Server Address/Port is empty");
    	}
     
    }
    
    // The Create button takes the user to the create account panel.
    else if (command.equals("Create"))
    {
      CreateAccountPanel cPanel = (CreateAccountPanel)container.getComponent(2);
      cPanel.setError("");
      CardLayout cardLayout = (CardLayout)container.getLayout();
      cardLayout.show(container, "3");
    }
  }
  
  public void setInitalPanel(InitialPanel initialP)
  {
	  this.initialPanel = initialP;
  }
  public void setClientFrame(ClientGUI clientgui)
  {
	  this.clientGUI = clientgui;
  }
}