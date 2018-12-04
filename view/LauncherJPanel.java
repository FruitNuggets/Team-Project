package view;

import javax.swing.JPanel;

import TetrisClient.ClientGUI;
import entity.Background;
import entity.MyButton;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class LauncherJPanel extends JPanel {

	private ClientGUI mainFrame;
	/**
	 * Create the panel.
	 */
	private Background background=new Background();
	
	
	public LauncherJPanel(ClientGUI mainFrame) {
		this.mainFrame=mainFrame;
		setLayout(null);
		
		MyButton btnOffline = new MyButton("Graphics/window/null.png","Single Mode",123,50);
		btnOffline.setFont(new Font("Time new Roman", Font.BOLD, 16));
		btnOffline.setForeground(Color.WHITE);
		btnOffline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.chooseMode(0);
			}
		});
		btnOffline.setBounds(315, 231, 123, 28);
		add(btnOffline);
		
		MyButton btnMode1 = new MyButton("Graphics/window/null.png","Create Room",123,50);
		btnMode1.setForeground(Color.WHITE);
		btnMode1.setFont(new Font("Time new Roman", Font.BOLD, 16));
		btnMode1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.chooseMode(1);
			}
		});
		btnMode1.setBounds(315, 269, 123, 28);
		add(btnMode1);
		
		MyButton btnMode2 = new MyButton("Graphics/window/null.png","Join Room",123,50);
		btnMode2.setForeground(Color.WHITE);
		btnMode2.setFont(new Font("Time new Roman", Font.BOLD, 16));
		btnMode2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.chooseMode(2);
			}
		});
		btnMode2.setBounds(315, 307, 123, 33);
		add(btnMode2);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		background.draw(g);
	}

}
