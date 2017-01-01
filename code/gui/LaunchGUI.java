package gui;

import java.awt.AWTException;

import exceptions.AlreadyUsedUsernameException;

/**
 * @author John de Wasseige
 * @author Patrick von Platen
 *
 */
public class LaunchGUI {
	
	/**
	 * @throws AWTException		when an Abstract Window Toolkit exception has occurred
	 * @throws AlreadyUsedUsernameException		if username is already used
	 *******************************************************/
	/* Launch */
	public static void main(String[] args) throws AWTException, AlreadyUsedUsernameException {

		GUIStartFrame gui = GUIStartFrame.getInstance();
		gui.open(0, 0, 600, 400);

		// Register Tests - can be run all together
		// GUIStartFrameTest.checkIfClickGoToButtonsWork();
		// GUIStartFrameTest.checkIfRestaurantCanBeRegistered();
		// GUIStartFrameTest.checkIfCourierCanBeRegistered();
		// GUIStartFrameTest.checkIfCustomerCanBeRegistered();

		// Log-in Tests - please run only one test at a time - if not they will
		// fail
		// GUIStartFrameTest.checkIfCourierLogInWorks();
		// GUIStartFrameTest.checkIfCourierLogInFailsWithWrongLogIn();
//		 GUIStartFrameTest.checkIfRestaurantLogInWorks();
//		 GUIStartFrameTest.checkIfManagerLogInWorks();
//		 GUIStartFrameTest.checkIfCustomerLogInWorks();
	}

}
