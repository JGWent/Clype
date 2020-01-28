/**
*
* Test Class for the Class that will be the client for the Clype Application
*
* @author John Graham
*
*
*/
package test;

import java.io.FileNotFoundException;
import java.io.IOException;

import main.ClypeClient;
import main.ClypeServer;

public class TestClypeClient {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		ClypeClient client  = new ClypeClient("John","Hello",2300);
		
		ClypeClient clientDPort = new ClypeClient("Jim","Cake");
		
		ClypeClient clientDHP = new ClypeClient("Bob");
		
		ClypeClient clientDefualt = new ClypeClient();
		
		System.out.println(client.getPort());
		System.out.println(client.getHostName());
		System.out.println(client.getUserName());
		System.out.println(client.hashCode());
		System.out.println(client.toString());
		System.out.println(client.equals(clientDefualt));
		
		System.out.println(clientDPort.toString());
		
		System.out.println(clientDHP.toString());
		
		System.out.println(clientDefualt.toString());
		
		System.out.println("----------------------------------");
		
//		Throws excpetion now
//		ClypeClient clientNEG = new ClypeClient(null,"Hello",-7000);
//
//		System.out.println(clientNEG.getPort());
//		System.out.println(clientNEG.toString());
//		System.out.println(clientNEG.equals(clientDefualt));
		
		System.out.println("---------------------------------- \n");
		
//		ClypeClient clientNull = new ClypeClient(null,"Hello",null);
//
//		System.out.println(clientNull.getPort());
//		System.out.println(clientNull.toString());
//		System.out.println(clientNull.equals(clientDefualt));
		
		client.start();
		client.readClientData();
		System.out.println(client.toString());

	}

}
