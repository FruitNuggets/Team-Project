package Controller;

import MySocket.ExchangeThread;
import TetrisClient.Client;
import TetrisClient.ClientGUI;
import entity.Rect;
import model.GameDao;
import view.OfflinePanel;
import view.OnlinePanel;
import javax.swing.*;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {

	public static GameController localController;

	// surface
	private JPanel panel;
	// time controller
	private Timer timer;
	private Client client;
	private ClientGUI clientGUI;

	private boolean isRunning =false;

	public GameDao getGamedao() {
		return gamedao;
	}

	public Rect getCurRect() {
		return curRect;
	}

	public Rect getNextRect() {
		return nextRect;
	}

	// game control
	private GameDao gamedao;

	// This Rect and next
	private Rect curRect;
	private Rect nextRect;

	// Threads
	private ExchangeThread exchangeThread;

	private class GameTask extends TimerTask {
		private int speed = 5;
        public void run() {

			if(!isRunning){
				return ;
			}

			// speed control the time
        	if(speed <= 0){
				if(gamedao.isput(curRect)){
					if(gamedao.gameover()) {
						System.out.println("begin to end game");
						// pause
						isRunning = false;
						if(exchangeThread!=null){
							exchangeThread.sendMessage("gameover");
							int myScore = gamedao.score;
							int remoteScore = RemoteController.remoteController.getGameDao().score;

							String str = Integer.toString(myScore) + " : " + Integer.toString(remoteScore) + ",";
							if (myScore > remoteScore) {
								// WIN
								JOptionPane.showMessageDialog(panel, str + "You win");
								client.addWin();
								try
								{
									clientGUI.correctLogin();
								} catch (IOException e)
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else if (myScore < remoteScore) {
								// LOSE
								JOptionPane.showMessageDialog(panel, str + "Otherside win");
								try
								{
									clientGUI.correctLogin();
								} catch (IOException e)
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								// pingju
								JOptionPane.showMessageDialog(panel, str + "Draw");
								try
								{
									clientGUI.correctLogin();
								} catch (IOException e)
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}else{
							int myScore = gamedao.score;
							JOptionPane.showMessageDialog(panel, "Game over."+
							"Your score is:"+Integer.toString(myScore));
						}
						return;
					}
					Random random = new Random();
					// put down
					if(exchangeThread!=null){
						exchangeThread.sendMessage("isput");
					}
					curRect.setColor(0);
					int temp=random.nextInt(7)+1;
					curRect = new Rect(nextRect.color);
					nextRect = new Rect(temp);
					if(exchangeThread!=null){
						// renew the data
						exchangeThread.sendMessage(Integer.toString(temp));
					}
					if(gamedao.ispop()){
						// destroy one line
						if(exchangeThread!=null){
							exchangeThread.sendMessage("ispop");
						}
					}
                }else{
					curRect.down();
					if(exchangeThread!=null)
						exchangeThread.sendMessage("down");
				}
                panel.repaint();
                speed=10-gamedao.level;
        	}
        	else{
				speed--;
			}
        }
    }
	public GameController(JPanel panel){
		this.panel=(OfflinePanel)panel;
	}

	public GameController(ExchangeThread thread,OnlinePanel panel, Client client, ClientGUI clientGUI) {
		this.exchangeThread=thread;
		this.panel=(OnlinePanel)panel;
		this.client = client;
		this.clientGUI = clientGUI;

	}

	/**
	 * game start
	 */
	public void gameStart(){

		gamedao = new GameDao();

		this.curRect = new Rect(1);
		this.nextRect = new Rect(2);

		isRunning =true;

		timer = new Timer();
		timer.schedule(new GameTask(), 100,30);
	}

	public void keyUp() {
		if(!isRunning) return;
		if(!gamedao.ifcanChange(curRect)) return;

		curRect.change();
		if(exchangeThread!=null)
			exchangeThread.sendMessage("up");
		panel.repaint();
		
	}

	public void keyDown() {
		if(!isRunning) return;
		if(gamedao.isput(curRect)) return;
		
		curRect.down();
		if(exchangeThread!=null)
			exchangeThread.sendMessage("down");
    	panel.repaint();

	}

	public void keyLeft() {
		if(!isRunning) return;
		if(gamedao.isleftside(curRect))	return;
		
		curRect.left();
		if(exchangeThread!=null)
			exchangeThread.sendMessage("left");
		panel.repaint();
				
	}

	public void keyRight() {
		if(!isRunning) return;
		if(gamedao.isrightside(curRect))	return;
		
		curRect.right();
		if(exchangeThread!=null)
			exchangeThread.sendMessage("right");
		panel.repaint();
				
	}

	/**
	 * pause
	 */
	public void keyPause()  {
		isRunning =false;
		if(exchangeThread!=null)
			exchangeThread.sendMessage("keyPause");
	}

	/**
	 * recover
	 */
	public void keyResume() {
		isRunning =true;
		if(exchangeThread!=null)
			exchangeThread.sendMessage("keyResume");
	}

	/**
	 * receive
	 */
	public void pause()  {
		isRunning =false;
	}

	/**
	 * receive
	 */
	public void resume() {
		isRunning =true;
	}

	/**
	 *  it should stop when one way is win 
	 */
	public int gameover(){
		System.out.print("gameover");
		isRunning=false;
		return gamedao.score;
	}


	public boolean isRunning() {
		return isRunning;
	}
	
	
}