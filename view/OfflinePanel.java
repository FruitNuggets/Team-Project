package view;

import javax.swing.JPanel;

import Controller.GameController;
import model.MusicPlayer;
import entity.GameWindow;
import entity.LabelWithBG;
import entity.MyButton;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.Color;

/**
 * Single mode
 */
public class OfflinePanel extends JPanel {
private GameController localController;
	
	private LabelWithBG newbackground;
	
	private GameWindow mainWindow;
	private MyButton btnMusic;
	private MyButton btnResume;
	private MyButton btnPause;
	private JLabel lblScroe,lblLevel;
	
	
	public void setLocalController(GameController gameController){
		this.localController=gameController;
	}
	
	/**
	 * Create the panel.
	 */
	public OfflinePanel() {
		setLayout(null);
		
		newbackground = new LabelWithBG(514, 460,"Graphics/background/background.png");
		
		mainWindow = new GameWindow(10, 10, 236, 405);
		mainWindow.setBounds(25, 10, 236, 405);
		add(mainWindow);
		
		JLabel lblLabei= new JLabel("Music");
		lblLabei.setForeground(Color.LIGHT_GRAY);
		lblLabei.setFont(new Font("Time new Roman", Font.BOLD, 16));
		lblLabei.setBounds(271, 26, 54, 15);
		add(lblLabei);
		
		JLabel lblLabei_1 = new JLabel("Level");
		lblLabei_1.setForeground(Color.LIGHT_GRAY);
		lblLabei_1.setFont(new Font("Time new Roman", Font.BOLD, 16));
		lblLabei_1.setBounds(283, 77, 54, 15);
		add(lblLabei_1);
		
		lblLevel = new JLabel("0");
		lblLevel.setFont(new Font("Time new Roman", Font.PLAIN, 20));
		lblLevel.setForeground(Color.WHITE);
		lblLevel.setBounds(293, 102, 54, 15);
		add(lblLevel);
		
		JLabel label_1 = new JLabel("Score");
		label_1.setForeground(Color.LIGHT_GRAY);
		label_1.setFont(new Font("Time new Roman", Font.BOLD, 16));
		label_1.setBounds(285, 151, 54, 15);
		add(label_1);
		
		lblScroe = new JLabel("0");
		lblScroe.setFont(new Font("Time new Roman", Font.PLAIN, 20));
		lblScroe.setForeground(Color.WHITE);
		lblScroe.setBounds(293, 176, 54, 15);
		add(lblScroe);
		
		JLabel label_3 = new JLabel("Next");
		label_3.setForeground(Color.LIGHT_GRAY);
		label_3.setFont(new Font("Time new Roman", Font.BOLD, 16));
		label_3.setBounds(268, 219, 86, 15);
		add(label_3);
		
		// start and pause
		btnResume = new MyButton("Graphics/window/null.png","Start",86,52);
		btnResume.setForeground(Color.LIGHT_GRAY);
		btnResume.setFont(new Font("Time new Roman", Font.BOLD, 20));
		btnResume.setBounds(271, 325, 86, 23);
		btnResume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				localController.keyResume();
				if(!MusicPlayer.isRunning()){
					MusicPlayer.bgmPlay();
				}
			}
		});
		add(btnResume);
		
		btnPause = new MyButton("Graphics/window/null.png","Pause",86,52);
		btnPause.setFont(new Font("Time new Roman", Font.BOLD, 20));
		btnPause.setForeground(Color.LIGHT_GRAY);
		btnPause.setBounds(271, 363, 86, 28);
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				localController.keyPause();
				if(MusicPlayer.isRunning()){
					MusicPlayer.bgmStop();
				}
			}
		});
		add(btnPause);
				
		btnMusic = new MyButton("Graphics/button/musicOn.png","",
				30, 25);
		btnMusic.setBounds(323, 23, 30, 25);
		btnMusic.setBorderPainted(false);
		btnMusic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(MusicPlayer.isturnOn()){
					MusicPlayer.setturnOn(false);
					MusicPlayer.bgmStop();
					// change img
					btnMusic.setNewImage("Graphics/button/musicOff.png");
				}else{
					MusicPlayer.setturnOn(true);
					MusicPlayer.bgmPlay();
					// change img
					btnMusic.setNewImage("Graphics/button/musicOn.png");
				}
			}
		});
		add(btnMusic);
		
		mainWindow.setFocusable(false);
		btnResume.setFocusable(false);
		btnPause.setFocusable(false);
		btnMusic.setFocusable(false);
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		newbackground.draw(g);
		
		mainWindow.creatwindow(g);
		try{
			lblLevel.setText(Integer.toString(localController.getGamedao().level));
			lblScroe.setText(Integer.toString(localController.getGamedao().score));
			
			
			localController.getCurRect().draw(g, 12, 32);
			localController.getNextRect().draw(g, 200, 265);
			localController.getGamedao().drawmap(g,0);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
