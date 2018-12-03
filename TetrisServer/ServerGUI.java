/**
 * 
 */
package TetrisServer;

/**
 * @author cling
 *
 */
import java.awt.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

import TetrisClient.CreateAccountControl;
import TetrisClient.CreateAccountPanel;
import TetrisClient.LoginPanel;

public class ServerGUI extends JFrame
{

private JLabel status; //Initialized to “Not Connected”
private String[] labels = {"Port #", "Timeout"};
private JTextField[] textFields = new JTextField[labels.length];
private JTextArea log;
private JLabel connection;
private JButton listen;
private JButton close;
private JButton stop;
private JButton quit;
private Server server;
private EventHandler event;
private Database database;
private Process pr;
private Runtime rt;
private JPanel container;
private ServerGamePanel gamePanel;
private CardLayout cardLayout;


public ServerGUI()
{
	this.server = new Server();
  this.setTitle("Tetris Server");
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
  // Create the card layout container.
  cardLayout = new CardLayout();
  container = new JPanel(cardLayout);
  
  // Create runtime variable to start the database
  rt = Runtime.getRuntime();
  
  
  status = new JLabel("Status: ");
  connection = new JLabel("Not Connected");
  connection.setForeground(Color.red);
  
  JPanel north = new JPanel();
  north.setLayout(new GridLayout(3, 0, 0, 0));
  
  JPanel pstatus = new JPanel();
  pstatus.add(status);
  pstatus.add(connection);

  north.add(pstatus);
  
  for(int j = 0; j < labels.length; j++)
  {  
  	JPanel aPanel = new JPanel();
    JLabel thisLabel = new JLabel(labels[j]);
    textFields[j] = new JTextField();
    //textFields[j].setBounds(105, 10, 90, 21);
    if(labels[j].equals("Timeout"))
    {
      textFields[j].setPreferredSize(new Dimension(60, 24));
    }
    else
      textFields[j].setPreferredSize(new Dimension(160, 24));
    aPanel.add(thisLabel);
    aPanel.add(textFields[j]);
    north.add(aPanel);
  }
  
  event = new EventHandler(this);
  JPanel south = new JPanel();
  south.setLayout(new FlowLayout());
  listen = new JButton("Listen");
  listen.setVerticalAlignment(SwingConstants.BOTTOM);
  listen.addActionListener(event);
  close = new JButton("Close");
  close.setVerticalAlignment(SwingConstants.BOTTOM);
  close.addActionListener(event);
  stop = new JButton("Stop");
  stop.setVerticalAlignment(SwingConstants.BOTTOM);
  stop.addActionListener(event);
  quit = new JButton("Quit");
  quit.setVerticalAlignment(SwingConstants.BOTTOM);
  quit.addActionListener(event);
  
  south.add(listen);
  south.add(close);
  south.add(stop);
  south.add(quit);
  
  JPanel center = new JPanel();
  
  JPanel dPanel = new JPanel();
  dPanel.setBounds(0, 220, 582, 220);
  FlowLayout flowLayout = (FlowLayout) dPanel.getLayout();
  flowLayout.setVgap(0);
  flowLayout.setHgap(0);
  JLabel dLabel = new JLabel("Server Log Below ");
  dLabel.setBounds(0, 0, 582, 220);
  dLabel.setVerticalAlignment(SwingConstants.BOTTOM);
  dLabel.setHorizontalAlignment(SwingConstants.CENTER);
  log = new JTextArea();
  log.setPreferredSize(new Dimension(525, 200));
  JScrollPane dScroll = new JScrollPane();
  dScroll.setViewportView(log);
  //dScroll.getViewport().add(log, null);
  dScroll.setPreferredSize(new Dimension(525, 200));
  dPanel.add(dScroll, BorderLayout.SOUTH);
  center.setLayout(null);

  
  center.add(dLabel);
  center.add(dPanel);
  
  getContentPane().add(north, BorderLayout.NORTH);
  getContentPane().add(south, BorderLayout.SOUTH);
  getContentPane().add(center);
  
  server.setLog(log);
  
  JButton btnServerInformation = new JButton("Server Information");
  btnServerInformation.setBounds(379, 95, 177, 25);
  center.add(btnServerInformation);
  btnServerInformation.addActionListener(event);
  
  JButton btnPlayerInformation = new JButton("Player Information");
  btnPlayerInformation.setBounds(28, 95, 177, 25);
  center.add(btnPlayerInformation);
  btnPlayerInformation.addActionListener(event);
  
  server.setStatus(connection);
  this.setBounds(500, 500, 600, 600);
  this.setVisible(true);
  
  //start database
  startDatabase();
  
  try
	{
		this.database = new Database();
		this.server.setDatabases(database);
	} catch (IOException e)
	{
		// TODO Auto-generated catch block
		log.append(e.toString());
	}
}

public void startDatabase()
{
	//Start the database
	try
	{
		pr = rt.exec("C:\\xampp\\mysql_start.bat");
		if(pr != null)
		{
			log.append("\nDatabase connected\n");
		}
	} catch (IOException e)
	{
		// TODO Auto-generated catch block
		log.append("\n" + e.toString());
	}
	
}

public void stopDatabase()
{
	//Start the database
	try
	{
	  pr = rt.exec("C:\\xampp\\mysql_stop.bat");
	} catch (IOException e)
	{
		// TODO Auto-generated catch block
		log.append("\n" + e.toString());
	}
	
}

public boolean didListen()
{
	if(!this.connection.getText().equals("Listening"))
	{
		return false;
	}
	return true;
}

public boolean checkListen()
{
	for(int i = 0; i < textFields.length; i++)
	{
		if(textFields[i].getText().isEmpty())
		{
			return false;
		}
	}
	return true;
}

public void setListen()
{
	for(int i = 0; i < this.textFields.length; i++)
	{
		if(this.labels[i].equals("Port #"))
		{
			int port = Integer.parseInt(this.textFields[i].getText());
			this.server.setPort(port);
		}
		else if(this.labels[i].equals("Timeout"))
		{
			int timeout = Integer.parseInt(this.textFields[i].getText());
			this.server.setTimeout(timeout);
		}
	}
	this.server.serverStarted();
}

public void incorrectListen()
{
	  this.log.append("\nPort Number/timeout not entered before pressing Listen\n");
}

public void inccorectStop()
{
		this.log.append("\nServer not currently started\n");
}

public void stopListening()
{
	this.server.stopListening();
	this.server.serverStopped();
}

public void closeServer()
{
	stopDatabase();
	try
	{
		this.server.close();
	} catch (IOException e)
	{
		// TODO Auto-generated catch block
		log.append(e.toString());
	}
	this.server.serverClosed();
}

public boolean checkLog()
{
	if(this.log.getText().isEmpty())
	{
		return false;
	}
	else
		return true;
}

public void showServerInformation() throws UnknownHostException
{
	//ServerInformationPanel loginPanel = (ServerInformationPanel)container.getComponent(0);
  //cardLayout.show(container, "2");
	ServerInformation serverInfo = new ServerInformation(server);
	log.setText("");
	log.setText("Server IP: " + serverInfo.getServerIP().toString() + "\nServer Name: " + serverInfo.getServerName() +  "\nServer Port: " + serverInfo.getPortNumber() + "\nServer Status: " + connection.getText());
}

public void showPlayerInformation()
{
	PlayerInformation infoPanel = new PlayerInformation(server);
	log.setText("");
	ArrayList<String> client = new ArrayList<String>();
	try
	{
		client = infoPanel.getClientInfo();
	} catch (SQLException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	for(String info : client)
	{
		log.append(info);
		log.append("\n\n");
	}
}

public static void main(String[] args) {
  
	new ServerGUI(); //args[0] represents the title of the GUI

}
}
