package exceptions;

/**
 * The AlreadyUsedUsernameException class extends 
 * the <code>Exception</code> class and is used
 * to be thrown when a <code>User</code> tries to
 * register with a <code>username</code> that is already used.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 * 
 * @see AlreadyUsedUsernameException
 */
@SuppressWarnings("serial")
public class AlreadyUsedUsernameException extends Exception {
	
	public AlreadyUsedUsernameException(){
		super("This username is already used by another user.");
	}

}
