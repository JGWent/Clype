/**
*
* The Class that will be the server for the Clype Application
* Has 2 Constructors
* Has 4 private properties
* Has 1 default vars
* Has 7 methods
* 
* @author John Graham
*
*
*/
package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import data.ClypeData;

public class ClypeServer {

	private int port;
	private boolean closeConnection;
	private ServerSocket sskt;
	private Socket clientSkt;
	private ArrayList<ServerSideClientIO> serverSideClientIOList;
	
	
	private static final int defaultPort = 7000;
	
	public static void main(String[] args) {
		if(args.length == 0) {
			ClypeServer clypeServer = new ClypeServer();
			clypeServer.start();
		}else if(args.length == 1) {
			ClypeServer clypeServer = new ClypeServer(Integer.parseInt(args[0]));
			clypeServer.start();
		}else {
			System.out.println("To many input arguments");
		}

	}
	
	
	/**
	 * Constructor for ClypeServer
	 * Accepts port as a input
	 * Sets data to send and receive from client both to null
	*/
	public ClypeServer(int port){
		if(port < 1024)
			throw new IllegalArgumentException("Incorrect Port Entered");
		this.port = port;
		this.serverSideClientIOList = new ArrayList<ServerSideClientIO>();
	}
	
	/**
	 * Constructor for ClypeServer
	 * Calls the original constructor and uses a default port number.
	*/
	public ClypeServer(){
		this(defaultPort);
	}
	
	/**
	 * start()
	 * Initializes the Clype Server
	*/
	public void start() {
		try {
			sskt = new ServerSocket(this.port);
			this.closeConnection  = false;
			while(this.closeConnection == false) {
			clientSkt = sskt.accept();
			ServerSideClientIO SSCIO = new ServerSideClientIO(this,clientSkt);
			serverSideClientIOList.add(SSCIO);
			new Thread(SSCIO).start();

			}
			sskt.close();
			clientSkt.close();
			this.closeConnection = true;
			
		}catch(IOException ioe) {
			this.closeConnection = true;
			System.err.println(ioe.getMessage());
			
		}
		
	}
	
	public synchronized void broadcast(ClypeData dataToBroadcastToClients ){
		
		for(int i = 0; i<serverSideClientIOList.size(); i++) {
			serverSideClientIOList.get(i).setDataToSendToClient(dataToBroadcastToClients);
			serverSideClientIOList.get(i).sendData();
		}
	}
	
	/** 
	 * removes clients from the list of clients connected to the server.
	 * 
	 */
	public synchronized void remove(ServerSideClientIO serverSideClientToRemove ) {
		serverSideClientIOList.remove(serverSideClientToRemove);
	}
	
	/** 
	 * returns the list of clients connected to the server.
	 * 
	 */
	public ArrayList<ServerSideClientIO> getServerSideClientIOList(){
		return this.serverSideClientIOList;
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
		result = 37*result + Float.floatToIntBits(this.port);
		result = 37*result + Boolean.hashCode(this.closeConnection);

		return result;
	}
	
	/**
	 * toString()
	 * Prints all items of a object to the command window
	*/
	@Override
	public String toString() {
		return "The current port is " + this.port +
				", and the connection is " + this.closeConnection;
	}
	
	/**
	 * equals()
	 * Compares two objects to see if they are the same.
	*/
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ClypeServer)) {
			return false;
		}
		
		ClypeServer otherClient = (ClypeServer)other;
		return this.port == otherClient.port && 
				this.closeConnection == otherClient.closeConnection;
	}
	
}

