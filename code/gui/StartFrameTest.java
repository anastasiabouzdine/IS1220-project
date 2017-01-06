package gui;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import exceptions.AlreadyUsedUsernameException;

import users.Address;
import users.Courier;
import users.Customer;
import users.Manager;
import users.User;

public class StartFrameTest {

	// checks whether the go to Buttons work
	public static void checkIfClickGoToButtonsWork() throws AWTException {

		boolean goToLoginButton = false;
		boolean goToRegisterButton = false;
		boolean homeButton1 = false;
		boolean homeButton2 = false;

		System.out.println("Check goToRegisterButton...");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		Robot bot = new Robot();
		bot.mouseMove(400, 400);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		StartFrame.getInstance();
		if (StartFrame.getRegister_panel_info().isShowing())
			goToRegisterButton = true;

		System.out.println("Test goRegisterButton was " + goToRegisterButton);

		System.out.println("Check goHomeButton1...");

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		bot.mouseMove(340, 135);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		if (StartFrame.getInstance().getWelcome_panel().isShowing())
			homeButton1 = true;

		System.out.println("Test goHomeButton1 was " + homeButton1);

		System.out.println("Check goToLogInButton...");

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		bot.mouseMove(250, 400);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		if (StartFrame.getInstance().getLogin_panel().isShowing())
			goToLoginButton = true;

		System.out.println("Test goToLogInButton was " + goToLoginButton);

		System.out.println("Check goHomeButton2...");

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		bot.mouseMove(210, 100);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		if (StartFrame.getInstance().getWelcome_panel().isShowing())
			homeButton2 = true;

		System.out.println("Test goHomeButton2 was " + homeButton2);

	}

	public static void checkIfRestaurantCanBeRegistered() throws AWTException {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		Robot bot = new Robot();
		bot.mouseMove(400, 400);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		StartFrame.getInstance().getUsername_JTF().setText("r8");
		StartFrame.getInstance().getPassword_JTF().setText("code");
		StartFrame.getInstance().getPasswordConf_JTF().setText("code");
		StartFrame.getInstance().getRadio_restaurant().doClick();

		bot.mouseMove(300, 130);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		StartFrame.getInstance().getName_JTF().setText("new Restaurant");
		StartFrame.getInstance().getxCoordinate_JTF().setText("5");
		StartFrame.getInstance().getyCoordinate_JTF().setText("8");

		bot.mouseMove(300, 300);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		StartFrame.getCore().logIn("root");
		User user = StartFrame.getCore().getUsers().get("r8");
		System.out.println("Test register Restaurant was " + StartFrame.getCore().getRestaurantList().contains(user));
		StartFrame.getCore().logOut();

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		bot.mouseMove(210, 100);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	public static void checkIfCourierCanBeRegistered() throws AWTException {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		Robot bot = new Robot();
		bot.mouseMove(400, 400);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		StartFrame.getInstance().getUsername_JTF().setText("c8");
		StartFrame.getInstance().getPassword_JTF().setText("code");
		StartFrame.getInstance().getPasswordConf_JTF().setText("code");
		StartFrame.getInstance().getRadio_courier().doClick();

		bot.mouseMove(300, 130);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		StartFrame.getInstance().getName_JTF().setText("new Courier");
		StartFrame.getInstance().getSurname_JTF().setText("peter");
		StartFrame.getInstance().getPhoneNum_JTF().setText("435678");
		StartFrame.getInstance().getxCoordinate_JTF().setText("5");
		StartFrame.getInstance().getyCoordinate_JTF().setText("8");

		bot.mouseMove(300, 370);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		StartFrame.getCore().logIn("root");
		User user = StartFrame.getCore().getUsers().get("c8");
		System.out.println("Test register Courier was " + StartFrame.getCore().getCourierList().contains(user));
		StartFrame.getCore().logOut();

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		bot.mouseMove(210, 100);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	public static void checkIfCustomerCanBeRegistered() throws AWTException {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		Robot bot = new Robot();
		bot.mouseMove(400, 400);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		StartFrame.getInstance().getUsername_JTF().setText("cus8");
		StartFrame.getInstance().getPassword_JTF().setText("code");
		StartFrame.getInstance().getPasswordConf_JTF().setText("code");
		StartFrame.getInstance().getRadio_customer().doClick();

		bot.mouseMove(300, 130);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		StartFrame.getInstance().getName_JTF().setText("new Customer");
		StartFrame.getInstance().getSurname_JTF().setText("peter");
		StartFrame.getInstance().getPhoneNum_JTF().setText("435678");
		StartFrame.getInstance().getxCoordinate_JTF().setText("5");
		StartFrame.getInstance().getyCoordinate_JTF().setText("8");
		StartFrame.getInstance().getEmailAddress_JTF().setText("hallo@aol.com");

		bot.mouseMove(300, 400);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		StartFrame.getCore().logIn("root");
		User user = StartFrame.getCore().getUsers().get("cus8");
		System.out.println("Test register Customer was " + StartFrame.getCore().getCustomerList().contains(user));
		StartFrame.getCore().logOut();

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		bot.mouseMove(210, 100);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	public static void checkIfCourierLogInWorks() throws AWTException, AlreadyUsedUsernameException {

		Address address = new Address(3, 4);
		Courier courier = new Courier("Courier_1", "Mary", address, "56789", "c8", "code");
		StartFrame.getCore().register(courier);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		Robot bot = new Robot();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		bot.mouseMove(250, 400);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		StartFrame.getInstance().getUsername_JTF().setText("c8");
		StartFrame.getInstance().getPassword_JTF().setText("code");

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		bot.mouseMove(350, 100);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		boolean test = (StartFrame.getCurrentLogInUser() instanceof CourierFrame);
		System.out.println("Test checkIfCourierLogInWorks was " + test);
	}

	public static void checkIfCourierLogInFailsWithWrongLogIn() throws AWTException, AlreadyUsedUsernameException {

		Address address = new Address(3, 4);
		Courier courier = new Courier("Courier_1", "Peter", address, "56789", "c8", "code");
		StartFrame.getCore().register(courier);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		Robot bot = new Robot();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		bot.mouseMove(250, 400);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		StartFrame.getInstance().getUsername_JTF().setText("c8");
		StartFrame.getInstance().getPassword_JTF().setText("codoe");

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		bot.mouseMove(350, 100);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		boolean test = (StartFrame.getInstance().getLogin_panel().isShowing());
		System.out.println("Test checkIfCourierLogInFailsWithWrongLogIn was " + test);
	}

	public static void checkIfCustomerLogInWorks() throws AWTException, AlreadyUsedUsernameException {

		Address address = new Address(3, 4);
		Customer customer = new Customer("Cust_1", "Otto", address, "56789", "nth@nhsn", "cus8", "code");
		StartFrame.getCore().register(customer);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		Robot bot = new Robot();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		bot.mouseMove(250, 400);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		StartFrame.getInstance().getUsername_JTF().setText("cus8");
		StartFrame.getInstance().getPassword_JTF().setText("code");

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		bot.mouseMove(350, 100);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		boolean test = (StartFrame.getCurrentLogInUser() instanceof CustomerFrame);
		System.out.println("Test checkIfCustomerLogInWorks was " + test);
	}

	public static void checkIfManagerLogInWorks() throws AWTException, AlreadyUsedUsernameException {

		Manager manager = new Manager("Manag_1", "John", "m8", "code");
		StartFrame.getCore().register(manager);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		Robot bot = new Robot();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		bot.mouseMove(250, 400);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		StartFrame.getInstance().getUsername_JTF().setText("m8");
		StartFrame.getInstance().getPassword_JTF().setText("code");

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		bot.mouseMove(350, 100);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		boolean test = (StartFrame.getCurrentLogInUser() instanceof ManagerFrame);
		System.out.println("Test checkIfManagerLogInWorks was " + test);
	}

	public static void checkIfRestaurantLogInWorks() throws AWTException, AlreadyUsedUsernameException {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		Robot bot = new Robot();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		bot.mouseMove(250, 400);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		StartFrame.getInstance().getUsername_JTF().setText("res8");
		StartFrame.getInstance().getPassword_JTF().setText("code");

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		bot.mouseMove(350, 100);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		boolean test = (StartFrame.getCurrentLogInUser() instanceof RestaurantFrame);
		System.out.println("Test checkIfRestaurantLogInWorks was " + test);
	}

}
