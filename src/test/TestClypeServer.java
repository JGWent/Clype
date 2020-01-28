/**
*
* Test Class for the Class that will be the server for the Clype Application
* 
* @author John Graham
*
*
*/
package test;

import main.ClypeServer;

public class TestClypeServer {
	public static void main( String[] args ) {
		ClypeServer server = new ClypeServer(2300);
		
		ClypeServer serverDefualt = new ClypeServer();
		
		System.out.println(server.getPort());
		System.out.println(server.toString());
		System.out.println(server.equals(serverDefualt));
		
		System.out.println("----------------------------------");
		
		ClypeServer serverNEG = new ClypeServer(-7000);

		System.out.println(serverNEG.getPort());
		System.out.println(serverNEG.toString());
		System.out.println(serverNEG.equals(serverDefualt));
		
		System.out.println("----------------------------------");
		
		//ClypeServer serverNull = new ClypeServer(null); Wont run
		
		//System.out.println(serverNull.getPort());
		//System.out.println(serverNull.toString());
		//System.out.println(serverNull.equals(serverDefualt));
		
	}

}
