package TetrisClient;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;

public class ClientInitialGamePanel extends JPanel
{
  private JLabel errorLabel;
  private ClientInitialGameControl control;
  
	public ClientInitialGamePanel(ClientInitialGameControl cc)
	{
		this.control = cc;
		
    JLabel label = new JLabel("Welcome to Tetris!! Please select a game mode", JLabel.CENTER);
    label.setFont(new Font("Tahoma", Font.BOLD, 16));
    
    errorLabel = new JLabel();
    errorLabel.setVisible(false);
    
    JButton loginButton = new JButton("Single Player");
    loginButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
    loginButton.addActionListener(control);
    JPanel loginButtonBuffer = new JPanel();
    loginButtonBuffer.add(loginButton);
    
    // Create the create account button.
    JButton createButton = new JButton("MultiPlayer");
    createButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
    createButton.addActionListener(control);
    JPanel createButtonBuffer = new JPanel();
    createButtonBuffer.add(createButton);
    
    // Create the create account button.
    JButton infoButton = new JButton("Player Info/Leaderboards");
    infoButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
    infoButton.addActionListener(control);
    JPanel infoButtonBuffer = new JPanel();
    infoButtonBuffer.add(infoButton);
    
 // Arrange the components in a grid.
    JPanel grid = new JPanel(new GridLayout(4, 1, 30, 30));
    grid.add(label);
    grid.add(loginButtonBuffer);
    grid.add(createButtonBuffer);
    grid.add(infoButtonBuffer);
    this.add(grid);
	}
}
