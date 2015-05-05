package Login;

/** Description of InvalidUsernameException:
* This class is called InvalidUsernameException and it throws an excpetion that says "Username is invalid"
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public class InvalidUsernameException extends RuntimeException {
	public InvalidUsernameException(){
		super("Username is invalid");
	}
}
