package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import data.ClypeData;
import data.MessageClypeData;

public class ServerSideClientIO implements Runnable {
	
	private boolean closeConnection;
	private ClypeData dataToReceiveFromClient;
	private ClypeData dataToSendToClient;
	private ObjectInputStream inFromClient;
	private ObjectOutputStream outToClient;
	private ClypeServer server;
	private Socket clientSocket;
	private String clientUsername;
	
	ServerSideClientIO(ClypeServer server, Socket clientSocket ){
		this.server	= server;
		this.clientSocket = clientSocket;
		this.closeConnection = false;
		this.dataToReceiveFromClient = null;
		this.dataToSendToClient = null;
		this.inFromClient = null;
		this.outToClient = null;
		
	}
	
	public void run(){
		try {
			inFromClient = new ObjectInputStream(clientSocket.getInputStream());
			
			outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
			
			this.receiveData();
			this.clientUsername = dataToReceiveFromClient.getUserName();
			
			
			while(this.closeConnection == false) {
				
				this.receiveData();
				this.dataToSendToClient = this.dataToReceiveFromClient;
				this.server.broadcast(this.dataToSendToClient);
			}
			
		}catch(IOException ioe) {
			this.server.remove(this);
			this.closeConnection = true;
			System.err.println(ioe.getMessage());
		}
		
		
	}
	
	
	/**
	 * receiveData()
	 * Will receive data for the ClypeServer
	*/
	public void receiveData() {
		try {
			this.dataToReceiveFromClient = (ClypeData) inFromClient.readObject();
			if(this.dataToReceiveFromClient.getData().equals("LISTUSERS")) {
				String listOfUsers = "";
				for(int i=0; i<this.server.getServerSideClientIOList().size();i++) {
					listOfUsers += this.server.getServerSideClientIOList().get(i).clientUsername + "\n";		
				}	
				MessageClypeData listUsersMessage = new MessageClypeData("Server",listOfUsers,3);
				this.dataToSendToClient = listUsersMessage;
				this.sendData();
				
			}
		}
		catch(ClassNotFoundException cnfe) {
			System.err.println("No Class found.");
		}
		catch(IOException ioe) {
			this.server.remove(this);
			System.err.println(ioe.getMessage());
		}
	}
	
	/**
	 * SendData()
	 * Will send data for the ClypeServer
	*/
	public void sendData() {
		try {
		outToClient.writeObject(this.dataToSendToClient);
		outToClient.flush();
		}catch(IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	
	}
	
	public void setDataToSendToClient(ClypeData dataToSendToClient){
		this.dataToSendToClient = dataToSendToClient;
	}
}
