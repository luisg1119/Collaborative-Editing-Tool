package Login;

public class InvalidUsernameException extends RuntimeException {
	public InvalidUsernameException(){
		super("Username is invalid");
	}
}
