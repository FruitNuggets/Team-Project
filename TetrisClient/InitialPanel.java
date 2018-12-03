package TetrisClient;

import java.awt.*;
import javax.swing.*;

public class InitialPanel extends JPanel
{
	public JTextField sPort;
	public JTextField sAddress;
	public JLabel error;
  // Constructor for the initial panel.
  public InitialPanel(InitialControl ic)
  {
    // Create the information label.
    JLabel label = new JLabel("Account Information", JLabel.CENTER);
    
    error = new JLabel();
    error.setVisible(false);
    
    // Create the login button.
    JButton loginButton = new JButton("Login");
    loginButton.addActionListener(ic);
    JPanel loginButtonBuffer = new JPanel();
    loginButtonBuffer.add(loginButton);
    
    // Create the create account button.
    JButton createButton = new JButton("Create");
    createButton.addActionListener(ic);
    JPanel createButtonBuffer = new JPanel();
    createButtonBuffer.add(createButton);
    
    // The server ip address
    JLabel serverAddress = new JLabel("Server Address: ");
    sAddress = new JTextField();
    sAddress.setPreferredSize(new Dimension(160, 24));
    
    JPanel address = new JPanel();
    address.add(serverAddress);
    address.add(sAddress);

    JLabel serverPort = new JLabel("Server Port:       ");
    sPort = new JTextField();
    sPort.setPreferredSize(new Dimension(160, 24));
    
    JPanel port = new JPanel();
    port.add(serverPort);
    port.add(sPort);
    
    JPanel sgrid = new JPanel(new GridLayout(2, 0, 0, 0));
    sgrid.add(address);
    sgrid.add(port);
    
    // Arrange the components in a grid.
    JPanel grid = new JPanel(new GridLayout(5, 1, 5, 5));
    grid.add(label);
    grid.add(error);
    grid.add(loginButtonBuffer);
    grid.add(createButtonBuffer);
    grid.add(sgrid);
    this.add(grid);
    
    ic.setInitalPanel(this);
  }
  
  public boolean checkInfo()
  {
	  if(!sPort.getText().isEmpty() && !sAddress.getText().isEmpty())
	  {
		  return true;
	  }
	  else
	    return false;
  }
  
  public int getPort()
  {
	  return Integer.parseInt(sPort.getText());
  }
  
  public String getAddress()
  {
	  return sAddress.getText();
  }
  
  public void setError(String error)
  {
	  this.error.setText(error);
	  this.error.setForeground(Color.red);
	  this.error.setVisible(true);
  }
}