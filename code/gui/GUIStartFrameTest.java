package gui;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;



import exceptions.AlreadyUsedUsernameException;

//import com.sun.jndi.cosnaming.IiopUrl.Address;

import users.Address;
import users.Courier;
import users.Customer;
import users.Manager;
import users.Restaurant;
import users.User;

public class GUIStartFrameTest {
	
	
	//checks whether the go to Buttons work
	public static void checkIfClickGoToButtonsWork() throws AWTException{
		
		
		boolean goToLoginButton = false;
		boolean goToRegisterButton = false;
		boolean homeButton1 = false;
		boolean homeButton2 = false;
		
		System.out.println("Check goToRegisterButton...");
		
		try{Thread.sleep(1000);}catch(InterruptedException e){}
		Robot bot = new Robot();
		bot.mouseMove(400,400);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(500);}catch(InterruptedException e){}
		
		if(GUIStartFrame.getInstance().getRegister_panel_info().isShowing())
			goToRegisterButton = true;
		
		System.out.println("Test goRegisterButton was " + goToRegisterButton);
		
		System.out.println("Check goHomeButton1...");
		
		try{Thread.sleep(500);}catch(InterruptedException e){}
		bot.mouseMove(200,135);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(500);}catch(InterruptedException e){}
		
		if(GUIStartFrame.getInstance().getWelcome_panel().isShowing())
			homeButton1 = true;
		
		System.out.println("Test goHomeButton1 was " + homeButton1);
		
		System.out.println("Check goToLogInButton...");
		
		try{Thread.sleep(500);}catch(InterruptedException e){}
		bot.mouseMove(250,400);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(500);}catch(InterruptedException e){}
		
		if(GUIStartFrame.getInstance().getLogin_panel().isShowing())
			goToLoginButton = true;
		
		System.out.println("Test goToLogInButton was " + goToLoginButton);
		
		System.out.println("Check goHomeButton2...");
		
		try{Thread.sleep(500);}catch(InterruptedException e){}
		bot.mouseMove(210,100);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(500);}catch(InterruptedException e){}
		
		if(GUIStartFrame.getInstance().getWelcome_panel().isShowing())
			homeButton2 = true;
		
		System.out.println("Test goHomeButton2 was " + homeButton2);
		
	
	}

	public static void checkIfRestaurantCanBeRegistered() throws AWTException {
		
		try{Thread.sleep(1000);}catch(InterruptedException e){}
		Robot bot = new Robot();
		bot.mouseMove(400,400);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(500);}catch(InterruptedException e){}
		
		GUIStartFrame.getInstance().username_JTF.setText("r8"); 
		GUIStartFrame.getInstance().password_JTF.setText("code");
		GUIStartFrame.getInstance().passwordConf_JTF.setText("code");
		GUIStartFrame.getInstance().radio_restaurant.doClick();
		
		bot.mouseMove(300,130);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(500);}catch(InterruptedException e){}
		
		GUIStartFrame.getInstance().name_JTF.setText("new Restaurant"); 
		GUIStartFrame.getInstance().xCoordinate_JTF.setText("5");
		GUIStartFrame.getInstance().yCoordinate_JTF.setText("8");
		
		bot.mouseMove(300,300);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(500);}catch(InterruptedException e){}
		
		GUIStartFrame.getCmd_processor().getCore().logIn("root");
		User user = GUIStartFrame.getCmd_processor().getCore().getUsers().get("r8");
		System.out.println("Test register Restaurant was " + GUIStartFrame.getCmd_processor().getCore().getRestaurantList().contains(user));
		GUIStartFrame.getCmd_processor().getCore().logOut();
		
		try{Thread.sleep(500);}catch(InterruptedException e){}
		bot.mouseMove(210,100);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(500);}catch(InterruptedException e){}
	}
	
public static void checkIfCourierCanBeRegistered() throws AWTException {
		
		try{Thread.sleep(1000);}catch(InterruptedException e){}
		Robot bot = new Robot();
		bot.mouseMove(400,400);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(500);}catch(InterruptedException e){}
		
		GUIStartFrame.getInstance().username_JTF.setText("c8"); 
		GUIStartFrame.getInstance().password_JTF.setText("code");
		GUIStartFrame.getInstance().passwordConf_JTF.setText("code");
		GUIStartFrame.getInstance().radio_courier.doClick();
		
		bot.mouseMove(300,130);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(500);}catch(InterruptedException e){}
		
		GUIStartFrame.getInstance().name_JTF.setText("new Courier"); 
		GUIStartFrame.getInstance().surname_JTF.setText("peter");
		GUIStartFrame.getInstance().phoneNum_JTF.setText("435678");
		GUIStartFrame.getInstance().xCoordinate_JTF.setText("5");
		GUIStartFrame.getInstance().yCoordinate_JTF.setText("8");
		
		bot.mouseMove(300,370);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(500);}catch(InterruptedException e){}
		
		GUIStartFrame.getCmd_processor().getCore().logIn("root");
		User user = GUIStartFrame.getCmd_processor().getCore().getUsers().get("c8");
		System.out.println("Test register Courier was " + GUIStartFrame.getCmd_processor().getCore().getCourierList().contains(user));
		GUIStartFrame.getCmd_processor().getCore().logOut();
		
		try{Thread.sleep(500);}catch(InterruptedException e){}
		bot.mouseMove(210,100);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(250);}catch(InterruptedException e){}
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try{Thread.sleep(500);}catch(InterruptedException e){}
}

public static void checkIfCustomerCanBeRegistered() throws AWTException {
	
	try{Thread.sleep(1000);}catch(InterruptedException e){}
	Robot bot = new Robot();
	bot.mouseMove(400,400);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(500);}catch(InterruptedException e){}
	
	GUIStartFrame.getInstance().username_JTF.setText("cus8"); 
	GUIStartFrame.getInstance().password_JTF.setText("code");
	GUIStartFrame.getInstance().passwordConf_JTF.setText("code");
	GUIStartFrame.getInstance().radio_customer.doClick();
	
	bot.mouseMove(300,130);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(500);}catch(InterruptedException e){}
	
	GUIStartFrame.getInstance().name_JTF.setText("new Customer"); 
	GUIStartFrame.getInstance().surname_JTF.setText("peter");
	GUIStartFrame.getInstance().phoneNum_JTF.setText("435678");
	GUIStartFrame.getInstance().xCoordinate_JTF.setText("5");
	GUIStartFrame.getInstance().yCoordinate_JTF.setText("8");
	GUIStartFrame.getInstance().emailAddress_JTF.setText("hallo@aol.com");
	
	bot.mouseMove(300,400);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(500);}catch(InterruptedException e){}
	
	GUIStartFrame.getCmd_processor().getCore().logIn("root");
	User user = GUIStartFrame.getCmd_processor().getCore().getUsers().get("cus8");
	System.out.println("Test register Customer was " + GUIStartFrame.getCmd_processor().getCore().getCustomerList().contains(user));
	GUIStartFrame.getCmd_processor().getCore().logOut();
	
	try{Thread.sleep(500);}catch(InterruptedException e){}
	bot.mouseMove(210,100);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(500);}catch(InterruptedException e){}
}
	
public static void checkIfCourierLogInWorks() throws AWTException, AlreadyUsedUsernameException {
	
	Address address = new Address(3,4);
	Courier courier = new Courier("aoeu", "tnhd", address, "56789", "c8", "code");
	GUIStartFrame.getCmd_processor().getCore().register(courier);
	
	try{Thread.sleep(1000);}catch(InterruptedException e){}
	Robot bot = new Robot();
	try{Thread.sleep(500);}catch(InterruptedException e){}
	bot.mouseMove(250,400);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(500);}catch(InterruptedException e){}
	
	GUIStartFrame.getInstance().username_JTF.setText("c8"); 
	GUIStartFrame.getInstance().password_JTF.setText("code");
	
	try{Thread.sleep(500);}catch(InterruptedException e){}
	bot.mouseMove(350,100);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(500);}catch(InterruptedException e){}
	
	boolean test = (GUIStartFrame.getCurrentLogInUser() instanceof GUICourierFrame);
	System.out.println("Test checkIfCourierLogInWorks was " + test);
}

public static void checkIfCourierLogInFailsWithWrongLogIn() throws AWTException, AlreadyUsedUsernameException {
	
	Address address = new Address(3,4);
	Courier courier = new Courier("aoeu", "tnhd", address, "56789", "c8", "code");
	GUIStartFrame.getCmd_processor().getCore().register(courier);
	
	try{Thread.sleep(1000);}catch(InterruptedException e){}
	Robot bot = new Robot();
	try{Thread.sleep(500);}catch(InterruptedException e){}
	bot.mouseMove(250,400);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(500);}catch(InterruptedException e){}
	
	GUIStartFrame.getInstance().username_JTF.setText("c8"); 
	GUIStartFrame.getInstance().password_JTF.setText("codoe");
	
	try{Thread.sleep(500);}catch(InterruptedException e){}
	bot.mouseMove(350,100);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(500);}catch(InterruptedException e){}
	
	boolean test = (GUIStartFrame.getInstance().getLogin_panel().isShowing());
	System.out.println("Test checkIfCourierLogInFailsWithWrongLogIn was " + test);
}

public static void checkIfCustomerLogInWorks() throws AWTException, AlreadyUsedUsernameException {
	
	Address address = new Address(3,4);
	Customer customer = new Customer("aoeu", "tnhd", address, "56789", "nth@nhsn", "cus8", "code");
	GUIStartFrame.getCmd_processor().getCore().register(customer);
	
	try{Thread.sleep(1000);}catch(InterruptedException e){}
	Robot bot = new Robot();
	try{Thread.sleep(500);}catch(InterruptedException e){}
	bot.mouseMove(250,400);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(500);}catch(InterruptedException e){}
	
	GUIStartFrame.getInstance().username_JTF.setText("cus8"); 
	GUIStartFrame.getInstance().password_JTF.setText("code");
	
	try{Thread.sleep(500);}catch(InterruptedException e){}
	bot.mouseMove(350,100);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(500);}catch(InterruptedException e){}
	
	boolean test = (GUIStartFrame.getCurrentLogInUser() instanceof GUICustomerFrame);
	System.out.println("Test checkIfCustomerLogInWorks was " + test);
}

public static void checkIfManagerLogInWorks() throws AWTException, AlreadyUsedUsernameException {
	
	Manager manager = new Manager("aoeu", "tnhd", "m8", "code");
	GUIStartFrame.getCmd_processor().getCore().register(manager);
	
	try{Thread.sleep(1000);}catch(InterruptedException e){}
	Robot bot = new Robot();
	try{Thread.sleep(500);}catch(InterruptedException e){}
	bot.mouseMove(250,400);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(500);}catch(InterruptedException e){}
	
	GUIStartFrame.getInstance().username_JTF.setText("m8"); 
	GUIStartFrame.getInstance().password_JTF.setText("code");
	
	try{Thread.sleep(500);}catch(InterruptedException e){}
	bot.mouseMove(350,100);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(500);}catch(InterruptedException e){}
	
	boolean test = (GUIStartFrame.getCurrentLogInUser() instanceof GUIManagerFrame);
	System.out.println("Test checkIfManagerLogInWorks was " + test);
}

public static void checkIfRestaurantLogInWorks() throws AWTException, AlreadyUsedUsernameException {
	
	Address address = new Address(3,4);
	Restaurant restaurant = new Restaurant("aoeu", address, "res8", "code");
	GUIStartFrame.getCmd_processor().getCore().register(restaurant);
	
	try{Thread.sleep(1000);}catch(InterruptedException e){}
	Robot bot = new Robot();
	try{Thread.sleep(500);}catch(InterruptedException e){}
	bot.mouseMove(250,400);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(500);}catch(InterruptedException e){}
	
	GUIStartFrame.getInstance().username_JTF.setText("res8"); 
	GUIStartFrame.getInstance().password_JTF.setText("code");
	
	try{Thread.sleep(500);}catch(InterruptedException e){}
	bot.mouseMove(350,100);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(250);}catch(InterruptedException e){}
	bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	try{Thread.sleep(500);}catch(InterruptedException e){}
	
	boolean test = (GUIStartFrame.getCurrentLogInUser() instanceof GUIRestaurantFrame);
	System.out.println("Test checkIfRestaurantLogInWorks was " + test);
}
	

}
