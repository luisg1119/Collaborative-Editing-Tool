/**
 * 
 */
package Login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Description of LoginServer: This class is just the initial login connecting
 * to the server for the collaborative editing tool.
 *
 * @author Group HCS
 * @version Initial Wednesday April 15, 2015
 */
public class LoginServer {
	static HashMap<String, String> loginMap;

	public LoginServer() {
		loginMap = new HashMap<String, String>();
		populateMap();
	}
	public boolean populateMap(){
		try {
			FileReader fr = new FileReader(System.getProperty("user.dir") + "/SavedDocuments" + "/usernamesAndPasswords.txt"); 
		
			BufferedReader br = new BufferedReader(fr);
			String str;
			while((str = br.readLine()) != null){ // str != null
				
				if(str.trim().equals("")){
					continue;
				}else{
					String[] tempSet = str.split(","); // tempSet[0] is username (key), tempSet[1] is password (value)
					if(this.loginMap.containsKey(tempSet[0])){
						continue;
					}else{
						this.loginMap.put(tempSet[0], tempSet[1]);
					}
					
				}
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
		
	}

	public String createUser(String username, String password) {
		//populateMap();
		if (loginMap.containsKey(username))
			return "Username Already Exists!";
		else {
			loginMap.put(username, password);
			return "Succesfully Created User, Please relogin!";
		}
	}

	public boolean login(String username, String password) {
		//populateMap();
		if(loginMap.get(username).equals(password))
			return true;
		return false;
	}
}
