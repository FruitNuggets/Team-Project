package TetrisClient;

import java.awt.*;
import javax.swing.*;
import TetrisLogin.CreateAccountData;
import java.awt.event.*;
import java.io.IOException;

public class CreateAccountControl implements ActionListener
{
  // Private data fields for the container and chat client.
  private JPanel container;
  private Client client;
  
  // Constructor for the createAccount controller.
  public CreateAccountControl(JPanel container, Client client)
  {
    this.container = container;
    this.client = client;
   
  }
  
  // Handle button clicks.
  public void actionPerformed(ActionEvent ae)
  {
    // Get the name of the button clicked.
    String command = ae.getActionCommand();

    // The Cancel button takes the user back to the initial panel.
    if (command == "Cancel")
    {
      CardLayout cardLayout = (CardLayout)container.getLayout();
      cardLayout.show(container, "1");
    }

    // The Submit button submits the login information to the server.
    else if (command == "Submit")
    {
      // Get the username and password the user entered.
      CreateAccountPanel accountPanel = (CreateAccountPanel)container.getComponent(2);
      CreateAccountData data = new CreateAccountData(accountPanel.getUsername(), accountPanel.getPassword());
      
      // Check the validity of the information locally first.
      if (data.getUsername().equals("") || data.getPassword().equals(""))
      {
        displayError("You must enter a username and password.");
        return;
      }
      else if(!accountPanel.getVerifyPassword().equals(data.getPassword()))
      {
      	displayError("Passwords must match.");
      	return;
      }
      else if(data.getPassword().length() < 6)
      {
      	displayError("Passowrd must be at lease 6 characters");
      	return;
      }
      else
      {
      	displayError("Adding Account");
      	try
				{
					client.sendToServer(data);
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					displayError(e.toString());
				}
      }
     
    }
  }


  // Method that displays a message in the error - could be invoked by ChatClient or by this class (see above)
  public void displayError(String error)
  {
    CreateAccountPanel accountPanel = (CreateAccountPanel)container.getComponent(2);
    accountPanel.setError(error);
    
  }
}