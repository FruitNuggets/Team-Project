package TetrisServer;

import java.awt.Color;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import ocsf.server.*;
import TetrisLogin.*;

public class Server extends AbstractServer
{

  private JTextArea log;
  private JLabel status;
  private Database database;
  private int clients;
  
	public Server(int port)
	{
		super(port);
		clients = 0;
		// TODO Auto-generated constructor stub
	}
	
  public Server()
  {
    super(12345);
    clients = 0;
  }

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
	{
		if (arg0 instanceof LoginData)
    {
       LoginData loginData = (LoginData)arg0;
       try
			{
				ArrayList<String> result = database.query("Select username, password from UserData");
				if(result != null)
				{
					boolean correct = false;
					for(String res : result)
					{
						String[] tokens = res.split(",");
						if(loginData.getUsername().equals(tokens[0].trim()) && loginData.getPassword().equals(tokens[1].trim()))
						{
							correct = true;
							try
							{
								arg1.sendToClient("correct login");
					      log.append("Username/Password: " + loginData.getUsername() + " " + loginData.getPassword());
							} catch (IOException e)
							{
								// TODO Auto-generated catch block
								log.append(e.toString());
							}
						}
					}
					if(correct == false)
					{
						try
						{
							arg1.sendToClient("incorrect login");
				      log.append("incorrect login");
						} catch (IOException e)
						{
							// TODO Auto-generated catch block
							log.append(e.toString());
						}
					}
				}
				else
				{
					log.append("NULL Query error.");
				}
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
			  log.append(e.toString());
			}
       
    }
    else if(arg0 instanceof CreateAccountData)
    {
      CreateAccountData loginData = (CreateAccountData)arg0;
      try
			{
				ArrayList<String> result = database.query("Select username, password from UserData");
				if(result != null)
				{
					boolean correct = false;
					for(String res : result)
					{
						String[] tokens = res.split(",");
						if(loginData.getUsername().equals(tokens[0].trim()))
						{
							correct = true;
							try
							{
								arg1.sendToClient("username in use");
							} catch (IOException e)
							{
								// TODO Auto-generated catch block
								log.append(e.toString());
							}
						}
					}
					if(correct == false)
					{
						try
						{
							arg1.sendToClient("username okay");
						} catch (IOException e)
						{
							// TODO Auto-generated catch block
							log.append(e.toString());
						}
						database.executeDML("Insert into UserData "
								+ "values('" + loginData.getUsername() + "', '" + loginData.getPassword() + "')");
					}
				}
				else
				{
					log.append("NULL Query error.");
				}
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
			  log.append(e.toString());
			}
    }

    // TODO Auto-generated method stub
    log.append("Message from Client" + arg0.toString() + arg1.toString() + "\n");
		
	}
	
  public void setDatabases(Database database)
  {
  	this.database = database;
  }
  
  public void setLog(JTextArea log)
  {
    this.log = log;
  }
  
  public JTextArea getLog()
  {
    return log;
  }
  
  public void setStatus(JLabel status)
  {
    this.status = status;
  }
  
  public JLabel getStatus()
  {
    return status;
  }

  
  protected void listeningException(Throwable exception) 
  {
    //Display info about the exception
    log.append("\nListening Exception:" + exception);
    exception.printStackTrace();
    log.append(exception.getMessage());
    
    if (this.isListening())
    {
      log.append("\nServer not Listening\n");
      status.setText("Not Connected");
      status.setForeground(Color.RED);
    }
    
  }
  
  protected void serverStarted() 
  {
    log.append("\nServer Started");
    try
		{
			this.listen();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			listeningException(e);
		}
    status.setText("Listening");
    status.setForeground(Color.green);
  }
  
  protected void serverStopped() 
  {
    log.append("\nServer Stopped Accepting New Clients - Press Listen to Start Accepting New Clients\n");
  }
  
  protected void serverClosed() 
  {
    log.append("\nServer and all current clients are closed - Press Listen to Restart\n");
  }

  protected void serverFull()
  {
  	log.append("\nMax number of clients are connected.\n");
  }
  
  protected void clientConnected(ConnectionToClient client) 
  {
    log.append("\nClient Connected\n");
    clients++;
    if(clients > 1)
    {
    	serverFull();
    }
  }
  
}
