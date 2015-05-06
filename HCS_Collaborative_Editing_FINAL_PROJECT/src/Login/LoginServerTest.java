package Login;

import static org.junit.Assert.*;

import org.junit.Test;

public class LoginServerTest {

	@Test
	public void test() {
		LoginServer newLogin = new LoginServer();
		assertEquals("Succesfully Created User, Please relogin!",newLogin.createUser("TestUsername", "password"));
		assertTrue(newLogin.login("TestUsername", "password"));
		}
	@Test(expected=InvalidUsernameException.class)
	public void test2(){
		LoginServer newLogin = new LoginServer();
		newLogin.login("TestUsernam31e", "password");
	}
	@Test(expected=InvalidPasswordException.class)
	public void test3(){
		LoginServer newLogin = new LoginServer();
		newLogin.login("TestUsername", "pass899word2");
	}
	@Test(expected=UsernameAlreadyExistsException.class)
	public void test4(){
		LoginServer newLogin = new LoginServer();
		newLogin.createUser("TestUsername", "password");
	}
}
