/**
 * 
 */
package Model;

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
	}

	public String createUser(String username, String password) {
		if (loginMap.containsKey(username))
			return "Username Already Exists!";
		else {
			loginMap.put(username, password);
			return "Succesfully Created User, Please relogin!";
		}
	}

	public boolean login(String username, String password) {
		if(loginMap.get(username).equals(password))
			return true;
		return false;
	}
}
