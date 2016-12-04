package exceptions;

@SuppressWarnings("serial")
public class AlreadyUsedUsernameException extends Exception {
	
	public AlreadyUsedUsernameException(){
		super("This username is already used by another user.");
	}

}
