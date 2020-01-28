/**
*
* The Class that will take care of file transfers for the Clype Application
* Has 2 Constructors
* Has 2 private properties
* Has 0 default vars
* Has 8 methods
* @author John Graham
*
*
*/
package data;

import java.io.*;

public class FileClypeData extends ClypeData {

		private String fileName;
		private String fileContents;
		
		/**
		 * Constructor for FileClypeData
		 * Accepts a user name, file name, and type
		 * calls ClypeData Constructor using super for user name and type.
		 * Sets fileContents to null and file name to entered file name
		 */
		public FileClypeData(String userName, String fileName,	int type){
			super(userName,type);
			this.fileContents = null;
			this.fileName = fileName;
		}
		/**
		 * Constructor for FileClypeData
		 * calls ClypeData Constructor using super type
		 * and sets file Contents to null
		 */
		public FileClypeData(){
			super(2);
			this.fileContents =null;
		}

		/**
		 * getFileName
		 * Returns the file name
		*/
		public String getFileName() {
			return this.fileName;
		}

		/**
		 * setFileName
		 * Accepts a file Name 
		 * Sets the file Name to file name parameter of object
		*/
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		/**
		 * readFileContents()
		 * Reads file contents of a file on the computer
		*/
		public void readFileContents() throws IOException, FileNotFoundException {
			String nextLine = null;
			try {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(this.fileName));
				
				while((nextLine = bufferedReader.readLine()) != null) {
					this.fileContents += nextLine;
					this.fileContents += "\n";
				}
				bufferedReader.close();
			}catch(FileNotFoundException fnfe) {
				System.err.println("File could not be found");
			}
			catch(IOException ioe) {
				System.err.println("IO Error");
			}
		}
		
		/**
		 * readFileContents()
		 * Accepts a key
		 * Reads file contents of a file on the computer and encrypts it
		*/
		public void readFileContents(String key) throws IOException, FileNotFoundException {
			String nextLine = null;
			try {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(this.fileName));
				
				while((nextLine = bufferedReader.readLine()) != null) {
					this.fileContents += nextLine;
				}
				bufferedReader.close();
				this.fileContents = this.encrypt(this.fileContents, key);
			}catch(FileNotFoundException fnfe) {
				System.err.println("File could not be found");
			}
			catch(IOException ioe) {
				System.err.println("IO Error");
			}

		}

		/**
		 * writeFileContents()
		 * Writes file contents to a file on computer
		*/
		public void writeFileContents() throws IOException {
			try {
				File file = new File(this.fileName);
				if (!file.exists()) {
					file.createNewFile();
				}
				
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
				bufferedWriter.write(this.fileContents);
				
				bufferedWriter.close();
				
			}catch(IOException ioe) {
				System.err.println("IOExcetion error");
			}
		}
		
		/**
		 * writeFileContents()
		 * Accepts a key
		 * Writes file contents to a file on computer in a encrypted format
		*/
		public void writeFileContents(String key) throws IOException {
			try {
				File file = new File(this.fileName);
				if (!file.exists()) {
					file.createNewFile();
				}
				
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
				bufferedWriter.write(this.decrypt(this.fileContents, key));
				
				bufferedWriter.close();
				
			}catch(IOException ioe) {
				System.err.println("IOExcetion error");
			}
		}
		
		/**
		 * getData()
		 * Returns file contents
		*/
		public String getData() {
			return this.fileContents;
		}
		
		/**
		 * getData()
		 * Accepts Key
		 * Returns decrypted file contents
		*/
		public String getData(String key) {
			return this.decrypt(this.fileContents, key);
		}
		
		/**
		 * hashCode()
		 * Returns the hash code
		*/
		@Override
		public int hashCode() {
			int result = 17;
			result = 37*result + this.fileContents.hashCode();
			result = 37*result + this.fileName.hashCode();
			
			return result;
		}
		
		/**
		 * toString()
		 * Prints all items of a object to the command window
		*/
		@Override
		public String toString() {
			return "The File Name is: " + this.fileName +
					",\n and the File Contains: " + this.fileContents + 
					",\n and the user is: " + this.getUserName() +
					",\n and the current type is: " + this.getType() +
					",\n and the date is: " + this.getDate();
		}
		
		/**
		 * equals()
		 * Compares two objects to see if they are the same.
		*/
		@Override
		public boolean equals(Object other) {
			if (!(other instanceof FileClypeData)) {
				return false;
			}
			
			FileClypeData otherFile = (FileClypeData)other;
			return this.fileName == otherFile.fileName &&
					this.fileContents == otherFile.fileContents;
		}
}
