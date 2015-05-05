package Login;

/** Description of InvalidPasswordException:
* This class is called InvalidPasswordException and it throws an excpetion that says "Password is Invalid"
*@author HCS Group: Siddharth Sharma, Luis Guerrero, Maverick Tudisco, Chintan Patel
*@version Final Version: May 6th, 2015
*/

public class InvalidPasswordException extends RuntimeException {
	public InvalidPasswordException(){
		super("Password is invalid");
	}
}
