package entity;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * label with background
 */
public class LabelWithBG  extends JLabel{
	
	// To make the picture stretch
	private Image background;
	
	public LabelWithBG(int width,int height,String address){
		// Set button background illustration
        ImageIcon icon=new ImageIcon(address);
        background=icon.getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT);
	}
	public void draw(Graphics g){
		g.drawImage(background, 0, 0, null);
	}
}
