package Login;

public class InvalidPasswordException extends RuntimeException {
	public InvalidPasswordException(){
		super("Password is invalid");
	}
}
