package main;

import java.util.Scanner;

public class ClientSideServerListener implements Runnable {
	
	private ClypeClient client;
	
	public ClientSideServerListener(ClypeClient client){
		this.client = client;
	}
	
	public void run() {
		String connection = "false";
		while(connection.equals("false")) {
			Scanner s = new Scanner(this.toString());
			int connectCnt = 0;
			while(s.hasNext()) {
				String current = s.next();
				if(current.equals("connection")||connectCnt>0) {
					connectCnt++;
				}
				if(connectCnt == 3)
				{
					connection = current;
					break;
				}
			}
			s.close();
			this.client.receiveData();
			this.client.printData();
		}
		
	}

}
