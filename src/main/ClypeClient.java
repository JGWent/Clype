/**
*
* The Class that will be the client for the Clype Application
* Has 4 Constructors
* Has 6 private properties
* Has 3 default vars
* Has 11 methods
* @author John Graham
*
*
*/
package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import data.ClypeData;
import data.FileClypeData;
import data.MessageClypeData;


public class ClypeClient {

	private String userName;
	private String hostName;
	private int port;
	private boolean closeConnection;
	private ClypeData dataToSendToServer;
	private ClypeData dataToReceiveFromServer;
	private Scanner inFromStd;
	private ObjectInputStream inFromServer;
	private ObjectOutputStream outToServer;
	private Socket skt;
	
	private static final int defaultPort = 7000;
	private static final String defaultHostName = "localhost";	
	private static final String defaultName = "Anon";
	private static final String key = "ADFHJSFLKSDHFDSFSDFAJ";
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		ArrayList<String> inputs = new ArrayList<String>();
		if(args.length == 0) {
			ClypeClient clypeClient = new ClypeClient();
			clypeClient.start();
		}else if(args.length == 1) {
			Scanner scan = new Scanner(args[0]);
			scan.useDelimiter("[@:]");
			
			while(scan.hasNext())
			{
				inputs.add(scan.next());
				
			}
			
			scan.close();
			if(inputs.size() == 1)
			{
				ClypeClient clypeClient = new ClypeClient(inputs.get(0));
				clypeClient.start();
			}else if(inputs.size() == 2) {
				ClypeClient clypeClient = new ClypeClient(inputs.get(0),inputs.get(1));
				clypeClient.start();
			}else if(inputs.size() == 3) {
				ClypeClient clypeClient = new ClypeClient(inputs.get(0),inputs.get(1),Integer.parseInt(inputs.get(2)));
				clypeClient.start();
			}else {
				System.out.println("To Many Input Arguments.");
			}
				
		}
		
	}
	
	/**
	*Constructor For Clype Client 
	*Accepts a user name, host name, and the port number
	*Has Sets CloseConnection to false and data to send and receive from server both to null
	*Throws Exceptions that will be used in all other ClypeClient constructors
	*/
	public ClypeClient(String userName, String hostName, int port) {
		if(userName == null)
			throw new IllegalArgumentException("userName set to null");
		if(hostName == null)
			throw new IllegalArgumentException("hostName set to null");
		if(port < 1024)
			throw new IllegalArgumentException("Port must be 1024 or greater");
		
		this.userName = userName;
		this.hostName = hostName;
		this.port = port;
		this.closeConnection = false;
		this.dataToSendToServer = null;
		this.dataToReceiveFromServer = null;
		this.inFromServer = null;
		this.outToServer = null;
	}
	
	/**
	*Constructor For Clype Client 
	*Calls the original constructor for Clype Client
	*Uses a default port number instead of a entered one.
	*/
	public ClypeClient(String userName, String hostName){
		this(userName, hostName, defaultPort);		
	}
	
	/**
	*Constructor For Clype Client 
	*Calls the original constructor for Clype Client
	*Uses a default port number instead of a entered one.
	*Uses a default host name instead of a entered one.
	*/
	public ClypeClient(String userName){
		this(userName, defaultHostName, defaultPort);
	}
	
	/**
	*Constructor For Clype Client 
	*Calls the original constructor for Clype Client
	*Uses a default port number instead of a entered one.
	*Uses a default host name instead of a entered one.
	*Usrs a default user name instead of a entered one.
	*/
	public ClypeClient(){
		this(defaultName, defaultHostName, defaultPort);
	}
	
	/**
	 * start()
	 * Starts the Clype Client
	*/
	public void start() throws FileNotFoundException, IOException {
		try {
			skt = new Socket(this.hostName,this.port);
			
			this.closeConnection = false;
			
			outToServer = new ObjectOutputStream(skt.getOutputStream());
			
			inFromServer = new ObjectInputStream(skt.getInputStream());
			inFromStd = new Scanner(System.in);
			
			ClientSideServerListener listener = new ClientSideServerListener(this);
			Thread t = new Thread(listener);
			t.start();
			
			this.dataToSendToServer = new MessageClypeData(this.userName,getUserName(),3);
			sendData();
			
			
			while(this.closeConnection == false) {
				this.readClientData();
			
				 
				sendData();
				

			}
			inFromStd.close();	
			skt.close();
			
		}catch(UnknownHostException uhe) {
			System.err.println("Unknown host.");
		}
		catch(NoRouteToHostException nrhe) {
			System.err.println("Server unreachable");
		}
		catch(ConnectException cd) {
			System.err.println("Connection Refused");
		}
		catch(IOException ioe) {
			this.closeConnection =true;
			System.err.println(ioe.getMessage());
		}
	}
	
	/**
	 * readClientData()
	 * Reads text entered into the command window
	 * Changes what happens depending on the input.
	 * @throws IOException 
	*/
	public void readClientData() throws IOException {
		while(inFromStd.hasNext()) {
			String nextString = inFromStd.next();
			if(nextString.equals("Done")) { 
				this.closeConnection = true;
				break;
			}
			else if(nextString.equals("SENDFILE")) {
				if(inFromStd.hasNext()) {
					String a = inFromStd.next();
					this.dataToSendToServer = new FileClypeData("anon",a,2);
					try {
						((FileClypeData) dataToSendToServer).readFileContents();
						//System.out.println(dataToSendToServer.toString());
						break;
					}catch(FileNotFoundException fnfe) {
						dataToSendToServer = null;
						System.err.println("File could not be found");
					}
				}
				else
					System.out.println("MISSING FILE NAME");
			}
			else if(nextString.equals("LISTUSERS")) {
				this.dataToSendToServer = new MessageClypeData(this.userName,"LISTUSERS",3);
				break;
			
			}
			else { 
				this.dataToSendToServer = new MessageClypeData(this.userName,nextString,3);
				break;
			}
		}
	}
	
	
	/**
	 * SendData()
	 * Will send data for the ClypeClient
	*/
	public void sendData() {
		try {
			//System.out.println("Sent from Client: \n" + dataToSendToServer.getData());
			this.outToServer.writeObject(this.dataToSendToServer);
			this.outToServer.flush();
		}catch(IOException ioe) {
			System.err.println(ioe.getMessage());
			
		}
	
		
	}
	
	/**
	 * receiveData()
	 * Will receive data for the ClypeClient
	*/
	public void receiveData() {
		try {
			dataToReceiveFromServer = (ClypeData) inFromServer.readObject();
		}
		catch(ClassNotFoundException cnfe) {
			System.err.println("No Class found.");
		}
		catch(IOException ioe) {
			System.err.println(ioe.getMessage());
		}

	}
	
	/**
	 * printData()
	 * Prints all items in object received from server.
	*/
	public void printData() {
		System.out.println("From Server.");
		System.out.println(dataToReceiveFromServer.getData());
	}
	
	/**
	 * getUserName()
	 * Returns the user name
	*/
	public String getUserName() {
		return this.userName;
	}
	
	/**
	 * getHostName()
	 * Returns the host name
	*/
	public String getHostName() {
		return this.hostName;
	}
	
	/**
	 * getPort()
	 * Returns the port number
	*/
	public int getPort() {
		return this.port;
	}
	
	/**
	 * hashCode()
	 * Returns the hash code
	*/
	@Override
	public int hashCode() {
		int result = 17;
		result = 37*result + this.userName.hashCode();
		result = 37*result + this.hostName.hashCode();
		result = 37*result + Float.floatToIntBits(this.port);
		result = 37*result + Boolean.hashCode(this.closeConnection);
		result = 37*result + this.dataToSendToServer.hashCode();
		result = 37*result + this.dataToReceiveFromServer.hashCode();
		result = 37*result + this.inFromStd.hashCode();

		return result;
	}
	
	/**
	 * toString()
	 * Prints all items of a object to the command window
	*/
	@Override
	public String toString() {
		return "The user is " + this.userName + 
				",\n and the host is " + this.hostName +
				",\n and the current port is " + this.port +
				",\n and the connection is " + this.closeConnection +
				",\n and the data to send is " + this.dataToSendToServer +
				",\n and the data received is " + this.dataToReceiveFromServer;
	}
	
	/**
	 * equals()
	 * Compares two objects to see if they are the same.
	*/
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ClypeClient)) {
			return false;
		}
		
		ClypeClient otherClient = (ClypeClient)other;
		return this.userName == otherClient.userName && 
				this.hostName == otherClient.hostName &&
				this.closeConnection == otherClient.closeConnection &&
				this.dataToReceiveFromServer == otherClient.dataToReceiveFromServer &&
				this.dataToSendToServer == otherClient.dataToSendToServer;
	}
	
}



