package gui;

import java.awt.AWTException;

import javax.swing.JOptionPane;

import exceptions.AlreadyUsedUsernameException;

/**
 * @author John de Wasseige
 * @author Patrick von Platen
 *
 */
public class LaunchGUI {

	/**
	 * @throws AWTException
	 * @throws AlreadyUsedUsernameException
	 *******************************************************/

	public static void popUpOkWindow(String message) {
			Object[] options = { "OK" };
		JOptionPane.showOptionDialog(null, message, "Attention", JOptionPane.PLAIN_MESSAGE,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}

	/* Launch */
	public static void main(String[] args) throws AWTException, AlreadyUsedUsernameException {

		StartFrame gui = StartFrame.getInstance();
		gui.open(0, 0, 600, 400);
		popUpOkWindow("If you have finished the session, just exist the window and all changes"
				+ "\nwill be saved. At start, saved data will automatically be loaded.");

		// Register Tests - can be run all together
		// StartFrameTest.checkIfClickGoToButtonsWork();
		// StartFrameTest.checkIfRestaurantCanBeRegistered();
		// StartFrameTest.checkIfCourierCanBeRegistered();
		// StartFrameTest.checkIfCustomerCanBeRegistered();

		// Log-in Tests - please run only one test at a time - if not they will
		// fail
		// StartFrameTest.checkIfCourierLogInWorks();
		// StartFrameTest.checkIfCourierLogInFailsWithWrongLogIn();
		// StartFrameTest.checkIfRestaurantLogInWorks();
		// StartFrameTest.checkIfManagerLogInWorks();
		// StartFrameTest.checkIfCustomerLogInWorks();
	}

}
