package Login;

/** Description of InvalidPasswordException:
* This class is called InvalidPasswordException and it throws an excpetion that says "Username Already Exists"
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public class UsernameAlreadyExistsException extends RuntimeException {
	public UsernameAlreadyExistsException(){
		super("Username Already Exists");
	}
}
