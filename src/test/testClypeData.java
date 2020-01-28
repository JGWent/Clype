/**
*
* Test Class for the Class that will contain the data for the Clype Application
* 
* @author John Graham
*
*
*/
package test;

import java.io.FileNotFoundException;
import java.io.IOException;

import data.ClypeData;
import data.FileClypeData;
import data.MessageClypeData;

public class testClypeData {
	public static void main( String[] args ) throws FileNotFoundException, IOException {
		
		//ClypeData data = new ClypeData(); Can't be instantiated
		
		
		 MessageClypeData messageData = new MessageClypeData("John","Hello World",1);
		
		 MessageClypeData messageDataDefualt = new MessageClypeData();
		 
		 System.out.println(messageData.getData());
		 System.out.println(messageData.getType());
		 System.out.println(messageData.getUserName());
		 System.out.println(messageData.getDate());
		 System.out.println(messageData.toString());
		 System.out.println(messageData.equals(messageDataDefualt));
		 System.out.println(messageData.equals(null));
		 
		 System.out.println("---------------------------");
		 
		 MessageClypeData messageDataKEY = new MessageClypeData("John","Hello World","DSHJKFDSKJF",1);
			
		 
		 System.out.println(messageDataKEY.getData());
		 System.out.println(messageDataKEY.getType());
		 System.out.println(messageDataKEY.getUserName());
		 System.out.println(messageDataKEY.getDate());
		 System.out.println(messageDataKEY.getData("DSHJKFDSKJF"));
		 System.out.println(messageDataKEY.toString());
		 System.out.println(messageDataKEY.equals(messageDataDefualt));
		 System.out.println(messageDataKEY.equals(null));
		 
		 System.out.println("---------------------------");
		
		 FileClypeData FileData = new FileClypeData("John","document.txt",1);
			
		 FileClypeData FileDataDefualt = new FileClypeData();
		 
		 System.out.println(FileData.getData());
		 System.out.println(FileData.getType());
		 System.out.println(FileData.getUserName());
		 System.out.println(FileData.getDate());
		 FileData.readFileContents();
		 System.out.println(FileData.toString());
		 System.out.println(FileData.equals(FileDataDefualt));
		 System.out.println(FileData.equals(null));
		 
		 System.out.println("---------------------------");
		 
		 FileClypeData FileDataNull = new FileClypeData(null,"document.txt",1);
		 
		 System.out.println(FileDataNull.getData());
		 System.out.println(FileDataNull.getType());
		 System.out.println(FileDataNull.getUserName());
		 System.out.println(FileDataNull.getDate());
		 FileDataNull.readFileContents("SDHKJFSDD");
		 System.out.println(FileDataNull.toString());
		 System.out.println(FileDataNull.equals(FileDataDefualt));
		 System.out.println(FileDataNull.equals(null));
		
	}
}
