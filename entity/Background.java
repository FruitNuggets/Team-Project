package entity;

import javax.swing.*;
import java.awt.*;

public class Background {
	private Image background = new ImageIcon("Graphics/background/background2.png").getImage();
	
	public void draw(Graphics g){
		g.drawImage(background, 0, 0, null);
	}
}
