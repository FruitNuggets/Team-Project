package TetrisClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class ClientInitialGameControl implements ActionListener
{
  private JPanel container;
  private Client client;
  // Constructor for the initial controller.
  public ClientInitialGameControl(JPanel container, Client client1)
  {
    this.container = container;
    this.client = client1;
  }
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
