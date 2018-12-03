package TetrisClient;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientGUI extends JFrame
{
 
  private Client client;
  private InitialControl initialC;
  private LoginControl loginC;
  private CreateAccountControl accountC;
  private ClientInitialGameControl initialGameC;
  private JPanel initialP;
  private JPanel loginP;
  private CreateAccountPanel accountP;
  private ClientInitialGamePanel initialGameP;
  private JPanel container;
  private CardLayout cardLayout;
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
  
  public void correctLogin()
  {
  	cardLayout.show(container, "4");
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
	    initialGameC = new ClientInitialGameControl(container, client);
	    // Create the four views. (need the controller to register with the Panels
	    loginP = new LoginPanel(loginC);
	    accountP = new CreateAccountPanel(accountC);
	    initialGameP = new ClientInitialGamePanel(initialGameC);
	    
	    container.add(loginP, "2");
	    container.add(accountP, "3");
	    container.add(initialGameP, "4");
  }
}
