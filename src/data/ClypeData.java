/**
*
* The Class that will be responsible for the data for the Clype Application
* Has 3 Constructors
* Has 3 private properties
* Has 1 default vars
* Has 4 methods
* @author John Graham
*
*
*/
package data;
import java.io.Serializable;
import java.util.*;

public abstract class ClypeData implements Serializable{
	
	private String userName;
	private int type;
	private Date date;
	
	private static final String defaultName = "Anon";
	private static final int listUsers = 0;
	private static final int logOut = 1;
	private static final int sendFile = 2;
	private static final int sendMsg = 3;
	
	/**
	 * Constructor For Clype Data 
	 * Accepts a user and type
	 * Sets the date in the constructor
	*/
	public ClypeData(String userName, int type) {
		this.userName = userName;
		this.type = type;
		this.date = new Date();
	}
	
	/**
	 * Constructor For Clype Data 
	 * Calls the original Constructor and uses a default user name
	*/
	public ClypeData(int type) {
		this(defaultName,type);
	}
	
	/**
	 * Constructor For Clype Data 
	 * Calls the original Constructor and uses a default user name and type
	*/
	public ClypeData() {
		this(defaultName,0);
	}

	/**
	 * getUserName()
	 * Returns the user name
	*/
	public String getUserName() {
		return userName;
	}
	
	/**
	 * encrypt
	 * Accepts a String and a Key and encrypt the String.
	*/
	protected String encrypt(String inputStringToEncrypt,String	key) {
	
		int k = 0;
		String encryptedString = "";
		
		for( int i = 0; i<inputStringToEncrypt.length(); i++) {
			char letter = inputStringToEncrypt.charAt(i);
			if(letter != ' ') {
				if(Character.isLowerCase(letter)) {
					encryptedString += (char)((letter + Character.toLowerCase(key.charAt(k)) -2 * 'a') % 26 + 'a');
					if(k == key.length()-1){
						k=0;
					}
					else
						k++;
				}				
				else if(Character.isUpperCase(letter))
				{				
					encryptedString += (char)(((letter + key.charAt(k)) % 26) + 'A');
					if(k == key.length()-1){
						k=0;
					}
					else
						k++;
				}
				else {
					encryptedString += (char)(((letter + key.charAt(k)) % 33) + '!');
					if(k == key.length()-1){
						k=0;
					}
					else
						k++;
				}
			}
			else
			encryptedString += ' ';
		}
		
		return encryptedString;
	}
	
	/**
	 * decrypt
	 * Accepts a String and a Key and decrypts the encrypted String.
	*/
	protected String decrypt(String inputStringToDecrypt,String	key) {
		int k = 0;
		String decryptedString = "";
		
		for( int i = 0; i<inputStringToDecrypt.length(); i++) {
			char letter = inputStringToDecrypt.charAt(i);
			if(letter != ' ') {
				if(Character.isLowerCase(letter)) {
					decryptedString += (char)(((letter - Character.toLowerCase(key.charAt(k)) + 26) % 26) + 'a');
					if(k == key.length()-1){
						k=0;
					}
					else
						k++;	
				}
				else if(Character.isUpperCase(letter)) { 
					decryptedString += (char)(((letter - key.charAt(k) + 26) % 26) + 'A');
					if(k == key.length()-1){
						k=0;
					}
					else
						k++;	
				}
				else {
					decryptedString += (char)(((letter - key.charAt(k) + 33) % 33) + '!');
					if(k == key.length()-1){
						k=0;
					}
					else
						k++;
				}
			}
			else
				decryptedString += ' ';
				
		}
		
		return decryptedString;
	}

	/**
	 * getType)
	 * Returns the type
	*/
	public int getType() {
		return type;
	}

	/**
	 * getDate()
	 * Returns the date
	*/
	public Date getDate() {
		return date;
	}
	
	public abstract String getData();
	
	
}
