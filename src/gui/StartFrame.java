package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import core.Core;
import core.Order;
import exceptions.AlreadyUsedUsernameException;
import parsers.ParseCouriers;
import parsers.ParseCustomers;
import parsers.ParseDishes;
import parsers.ParseManagers;
import parsers.ParseMeals;
import parsers.ParseOrders;
import restaurantSetUp.Meal;
import users.Address;
import users.Courier;
import users.Customer;
import users.Manager;
import users.Restaurant;
import users.User;

/**
 * The class <code>StartFrame</code> is the class that will create a new core 
 * and be in charge of handling the user experience. It provides the user with 
 * a frame that allows registering and the log in. 
 * 
 * This class has nested classes providing the actions that are added to the menu bar.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public class StartFrame {

	private static StartFrame instance;
	private static JFrame frame = new JFrame("My Foodora");
	private static UserFrame currentLogInUser;

	private static Core core;

	private JPanel address_panel = new JPanel();
	private JPanel login_panel = new JPanel();

	// radio buttons for user to register
	private JRadioButton radio_customer = new JRadioButton("Customer", true);
	private JRadioButton radio_courier = new JRadioButton("Courier");
	private JRadioButton radio_restaurant = new JRadioButton("Restaurant");
	private static JRadioButton radio_manager = new JRadioButton("Manager");
	private ButtonGroup user_type_group = new ButtonGroup();

	// buttons
	private JButton logIn_button = new JButton("LOG IN");
	private static JButton home_button = new JButton("GO TO HOME");
	private JButton goToRegister_button = new JButton("GO TO REGISTER");
	private JButton backToRegister_button = new JButton("GO BACK");
	private JButton createAccount_button = new JButton("CREATE ACCOUNT");
	private JButton goToLogIn_button = new JButton("GO TO LOG IN");
	private JButton register_button = new JButton("REGISTER");
	private static Button addUserButton = new Button("ADD USER");

	// Textfields to register
	private JTextField surname_JTF = new JTextField(15);
	private JTextField xCoordinate_JTF = new JTextField(15);
	private JTextField yCoordinate_JTF = new JTextField(15);
	private JTextField phoneNum_JTF = new JTextField(15);
	private JTextField emailAddress_JTF = new JTextField(15);
	private JTextField username_JTF = new JTextField(15);
	private JPasswordField password_JTF = new JPasswordField(15);
	private JPasswordField passwordConf_JTF = new JPasswordField(15);
	private JTextField name_JTF = new JTextField(15);

	// String to register
	private String surname;
	private Address address;
	private String phoneNum;
	private String emailAddress;
	private String username;
	private char[] password;
	private char[] passwordConf;
	private String name;

	// panels when user register and add info
	private JPanel welcome_panel = new JPanel();
	private JPanel welcome_button_panel = new JPanel();
	private JPanel welcome_message_panel = new JPanel();
	private static JPanel register_panel_info = new JPanel();
	private static JPanel user_global_info = new JPanel();
	private JPanel user_specific_info = new JPanel();
	private JPanel customer_specific_info = new JPanel();
	private JPanel courier_specific_info = new JPanel();
	private JPanel restaurant_specific_info = new JPanel();
	private JPanel manager_specific_info = new JPanel();

	// Manager for add User button
	private static UserFrame manager;

	/*********************************************************/
	// HelpFunctions

	/**
	 * intializing the StartFrame when it was not yet initialized.
	 * If it was, the function will just return the instance of this class.
	 * 
	 * @return instance instance of this class
	 */
	public static StartFrame getInstance() {
		if (instance == null) {
			instance = new StartFrame();
			// For testing

			try {
				FileInputStream fileIn = new FileInputStream("./ser_files/gui_core.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				core = (Core) in.readObject();
				in.close();
				fileIn.close();
			} catch (IOException i) {
				System.out.println("! Error while loading the file !");
				core = new Core();
			} catch (ClassNotFoundException e) {
				System.out.println("! Class not found !");		
			}

			core.logIn("root"); // login with manager to add lists
			core.setCustomerList(ParseCustomers.parseCustomers("src/txtFILES/customersList.txt"));
			core.setCourierList(ParseCouriers.parseCouriers("src/txtFILES/courierList.txt"));
			core.setManagerList(ParseManagers.parseManagers("src/txtFILES/managersList.txt"));
			core.logOut();

			Address address = new Address(3, 4);
			Restaurant restaurant = new Restaurant("aoeu", address, "res8", "code");

			try {
				core.register(restaurant);
			} catch (AlreadyUsedUsernameException e) {
			}

			core.logIn("root");
			for (Meal meal : ParseMeals.parseFullMeals("src/txtFILES/fullMeals.txt"))
				core.getRestaurantList().get(0).addMeal(meal);

			for (Meal meal : ParseMeals.parseHalfMeals("src/txtFILES/halfMeals.txt"))
				core.getRestaurantList().get(0).addMeal(meal);

			core.getRestaurantList().get(0).getMenu()
					.setListOfStarter(ParseDishes.parseStarter("src/txtFILES/starters.txt"));
			core.getRestaurantList().get(0).getMenu()
					.setListOfMainDish(ParseDishes.parseMainDish("src/txtFILES/mainDishes.txt"));
			core.getRestaurantList().get(0).getMenu()
					.setListOfDessert(ParseDishes.parseDessert("src/txtFILES/desserts.txt"));

			ArrayList<Order> orders = ParseOrders.parseOrders(core.getCustomerList(), core);
			core.setSavedOrders(orders);

			core.logOut();
		}
		return instance;
	}

	/*********************************************************/
	/* Fill panels */
	
	/**
	 * initializing the info panels used for registering the user. 
	 */
	public void fillUserGlobalInfoPanel() {
		user_global_info.removeAll();
		user_global_info.setBorder(BorderFactory.createEmptyBorder(30, 150, 10, 150));
		user_global_info.setBackground(Color.gray);

		JTextField ask_info = new JTextField(23);
		Font font = new Font("SansSerif", Font.BOLD, 12);
		ask_info.setFont(font);
		ask_info.setBackground(Color.red);
		ask_info.setText("PLEASE FILL OUT THE FOLLOWING INFOS");
		ask_info.setEditable(false);

		getName_JTF().setPreferredSize(new Dimension(150, 30));
		getName_JTF().setForeground(Color.BLUE);
		getName_JTF().setText("Insert your name");

		user_specific_info.setBackground(Color.gray);

		user_global_info.add(ask_info, BorderLayout.NORTH);
		user_global_info.add(getName_JTF(), BorderLayout.NORTH);
		user_global_info.add(user_specific_info, BorderLayout.CENTER);
		frame.add(user_global_info);
	}

	private void fillAddressInfosPanel() {
		address_panel.removeAll();
		address_panel.add(getxCoordinate_JTF());
		address_panel.add(getyCoordinate_JTF());
	}

	private void fillCourierSpecificInfosPanel() {
		courier_specific_info.setLayout(new BorderLayout());
		courier_specific_info.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
		courier_specific_info.setBackground(Color.gray);
	}

	private void fillCustomerSpecificInfosPanel() {
		customer_specific_info.setLayout(new BorderLayout());
		customer_specific_info.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
		customer_specific_info.setBackground(Color.gray);
	}

	private void fillRestaurantSpecificInfosPanel() {
		restaurant_specific_info.setLayout(new BorderLayout());
		restaurant_specific_info.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
		restaurant_specific_info.setBackground(Color.gray);
	}

	private void fillManagerSpecificInfosPanel() {
		manager_specific_info.setLayout(new BorderLayout());
		manager_specific_info.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
		manager_specific_info.setBackground(Color.green);
	}

	private void fillLoginPanel() {
		login_panel.setBackground(Color.green);
		login_panel.setBorder(BorderFactory.createEmptyBorder(30, 150, 10, 150));
		frame.add(login_panel);
	}

	/**
	 * use this function to display the register panel
	 */
	public void fillRegisterPanel() {
		register_panel_info.setBackground(Color.gray);
		register_panel_info.setBorder(BorderFactory.createEmptyBorder(30, 150, 10, 150));

		user_type_group.add(getRadio_restaurant());
		user_type_group.add(getRadio_courier());
		user_type_group.add(getRadio_customer());
		user_type_group.add(getRadio_manager());

		JPanel user_type = new JPanel();
		user_type.setBackground(Color.gray);
		user_type.add(getRadio_customer());
		user_type.add(getRadio_courier());
		user_type.add(getRadio_restaurant());
		user_type.add(getRadio_manager());
		register_panel_info.add(user_type, BorderLayout.NORTH);
		frame.add(register_panel_info);
	}

	private void fillWelcomePanel() {

		welcome_panel.setBackground(Color.orange);
		welcome_panel.setBorder(BorderFactory.createTitledBorder("Welcome"));
		welcome_panel.setLayout(new BorderLayout());

		welcome_button_panel.setBackground(Color.orange);

		JTextField program_name = new JTextField(31);
		Font font = new Font("SansSerif", Font.BOLD, 12);
		program_name.setFont(font);
		program_name.setBackground(Color.orange);
		program_name.setText("MyFoodora : the food delivery system solution !");
		program_name.setEditable(false);
		welcome_message_panel.setBackground(Color.orange);
		welcome_message_panel.add(program_name);

		JTextArea welcome_text = new JTextArea();
		JScrollPane welcome_scrollPane = new JScrollPane();
		welcome_scrollPane.setViewportView(welcome_text);
		welcome_text
				.setText("If you already have a username, please GO TO LOGIN !\n" + "If not, then GO TO REGISTER !");
		welcome_text.setBackground(Color.yellow);

		welcome_panel.add(welcome_message_panel, BorderLayout.NORTH);
		welcome_panel.add(welcome_scrollPane, BorderLayout.CENTER);
		welcome_panel.add(welcome_button_panel, BorderLayout.SOUTH);

		// Buttons
		welcome_button_panel.add(goToLogIn_button, BorderLayout.CENTER);
		welcome_button_panel.add(goToRegister_button, BorderLayout.CENTER);
		frame.add(welcome_panel);
	}

	private void clearTextWhenClickedonTextfieldInit() {

		getSurname_JTF().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				getSurname_JTF().setText("");

			}
		});
		getxCoordinate_JTF().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				getxCoordinate_JTF().setText("");

			}
		});
		getyCoordinate_JTF().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				getyCoordinate_JTF().setText("");

			}
		});
		getEmailAddress_JTF().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				getEmailAddress_JTF().setText("");

			}
		});
		getPhoneNum_JTF().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				getPhoneNum_JTF().setText("");

			}
		});
		getPasswordConf_JTF().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				getPasswordConf_JTF().setText("");

			}
		});
		getPassword_JTF().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				getPassword_JTF().setText("");

			}
		});
		getUsername_JTF().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				getUsername_JTF().setText("");

			}
		});
		getName_JTF().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				getName_JTF().setText("");

			}
		});
	}

	/*********************************************************/

	/*********************************************************/
	/* Functions */

	/**
	 * to display a message in a pop up window
	 * 
	 * @param message message to be displayed
	 */
	public void popUpOkWindow(String message) {
		Object[] options = { "OK" };
		JOptionPane.showOptionDialog(null, message, "Attention", JOptionPane.PLAIN_MESSAGE,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}

	/**
	 * to change the current panel that is displayed
	 * 
	 * @param panel panel that will be displayed
	 */
	public void setCurrentPanel(JPanel panel) {
		frame.setContentPane(panel);
		frame.setVisible(true);
	}

	private void initSettings() {

		frame.addWindowListener(new WindowEventHandler());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		getHome_button().addActionListener(new HomeButton());

		clearTextWhenClickedonTextfieldInit();
		fillWelcomePanel();
		fillRegisterPanel();
		fillLoginPanel();
		fillUserGlobalInfoPanel();
		fillAddressInfosPanel();

		fillCourierSpecificInfosPanel();
		fillCustomerSpecificInfosPanel();
		fillRestaurantSpecificInfosPanel();
		fillManagerSpecificInfosPanel();

		setCurrentPanel(welcome_panel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		goToLogIn_button.addActionListener(new GoToLoginButton());
		getHome_button().addActionListener(new HomeButton());
		goToRegister_button.addActionListener(new GoToRegisterButton());
		backToRegister_button.addActionListener(new GoBackToRegisterButton());
		createAccount_button.addActionListener(new WhatAccountType());
		register_button.addActionListener(new RegisterButton());
		logIn_button.addActionListener(new LoginButton());

		addUserButton.addActionListener((ActionEvent e) -> {

			name = getName_JTF().getText();
			try {
				if (customer_specific_info.isShowing()) {
					surname = getSurname_JTF().getText();
					emailAddress = getEmailAddress_JTF().getText();
					phoneNum = getPhoneNum_JTF().getText();

					address = new Address(Integer.parseInt(getxCoordinate_JTF().getText()),
							Integer.parseInt(getyCoordinate_JTF().getText()));

					core.addUser(new Customer(name, surname, address, phoneNum, emailAddress, username,
							new String(password)));
				} else if (courier_specific_info.isShowing()) {
					surname = getSurname_JTF().getText();
					phoneNum = getPhoneNum_JTF().getText();

					address = new Address(Integer.parseInt(getxCoordinate_JTF().getText()),
							Integer.parseInt(getyCoordinate_JTF().getText()));

					core.addUser(new Courier(name, surname, address, phoneNum, username, new String(password)));
				} else if (restaurant_specific_info.isShowing()) {

					address = new Address(Integer.parseInt(getxCoordinate_JTF().getText()),
							Integer.parseInt(getyCoordinate_JTF().getText()));

					core.addUser(new Restaurant(name, address, username, new String(password)));
				} else if (manager_specific_info.isShowing()) {

					surname = getSurname_JTF().getText();
					core.addUser(new Manager(name, surname, username, new String(password)));
				}
				manager.getFrame().setVisible(true);
				manager.setCurrentPanel(manager.getWelcome_panel());
				frame.setVisible(false);
				user_global_info.remove(addUserButton);
				register_panel_info.remove(((ManagerFrame) manager).getGoBackfromAddUserButton());
			} catch (Exception ex) {
				popUpOkWindow("Please use only integers for the address coordinates.");
			}

		});
	}

	/**
	 * @param xLocation the horizontal location where the GUI is going to be displayed
	 * @param yLocation the vertical location where the GUI is going to be displayed
	 * @param width the width of the window that will be displayed
	 * @param height the height of the window that will be displayed
	 */
	public void open(final int xLocation, final int yLocation, final int width, final int height) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.setBounds(xLocation, yLocation, width, height);
				initSettings();
				frame.setVisible(true);
			}
		});
	}

	private boolean checkIfPassWordIsEqual(String password, String passwordConf) {
		if (password.equals(passwordConf)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * to display the welcome panel of the start frame
	 */
	public void goToHomePage() {
		setCurrentPanel(welcome_panel);
	}

	private void goToLogInPanel() {

		getHome_button().setVisible(true);
		login_panel.add(getHome_button(), BorderLayout.SOUTH);
		login_panel.add(logIn_button, BorderLayout.SOUTH);

		getUsername_JTF().setPreferredSize(new Dimension(150, 30));
		getUsername_JTF().setForeground(Color.BLUE);
		getUsername_JTF().setText("Insert your username");

		getPassword_JTF().setPreferredSize(new Dimension(150, 30));
		getPassword_JTF().setForeground(Color.BLUE);
		getPassword_JTF().setText("exemple");

		login_panel.add(getUsername_JTF(), BorderLayout.NORTH);
		login_panel.add(getPassword_JTF(), BorderLayout.CENTER);

		setCurrentPanel(login_panel);
	}

	/**
	 * to display the register panel of the start frame
	 */
	public void goToRegisterPanel() {
		register_panel_info.add(createAccount_button, BorderLayout.SOUTH);
		register_panel_info.add(getHome_button(), BorderLayout.SOUTH);

		register_panel_info.add(getUsername_JTF(), BorderLayout.NORTH);
		register_panel_info.add(getPassword_JTF(), BorderLayout.CENTER);
		register_panel_info.add(getPasswordConf_JTF(), BorderLayout.CENTER);

		getUsername_JTF().setForeground(Color.BLUE);
		getUsername_JTF().setText("Insert a username");

		getPassword_JTF().setForeground(Color.BLUE);
		getPassword_JTF().setText("exemple");

		getPasswordConf_JTF().setForeground(Color.BLUE);
		getPasswordConf_JTF().setText("exemple");

		setCurrentPanel(register_panel_info);
	}

	/*********************************************************/
	/* ActionListeners */

	private class WhatAccountType implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			username = getUsername_JTF().getText();
			password = getPassword_JTF().getPassword();
			passwordConf = getPasswordConf_JTF().getPassword();

			if (checkIfPassWordIsEqual(new String(password), new String(passwordConf))) {

				user_specific_info.removeAll();

				getSurname_JTF().setText("Insert your surname");
				getPhoneNum_JTF().setText("Insert your phone number");
				getEmailAddress_JTF().setText("Insert your Email address");
				getxCoordinate_JTF().setText("Insert xCoordinate");
				getyCoordinate_JTF().setText("Insert yCoordinate");

				if (getRadio_customer().isSelected()) {
					customer_specific_info.removeAll();
					customer_specific_info.add(getSurname_JTF(), BorderLayout.NORTH);
					customer_specific_info.add(getPhoneNum_JTF(), BorderLayout.WEST);
					customer_specific_info.add(getEmailAddress_JTF(), BorderLayout.EAST);
					customer_specific_info.add(address_panel, BorderLayout.SOUTH);

					user_specific_info.add(customer_specific_info);

				} else if (getRadio_courier().isSelected()) {
					courier_specific_info.removeAll();
					courier_specific_info.add(getSurname_JTF(), BorderLayout.EAST);
					courier_specific_info.add(address_panel, BorderLayout.CENTER);
					courier_specific_info.add(getPhoneNum_JTF(), BorderLayout.WEST);
					courier_specific_info.add(address_panel, BorderLayout.SOUTH);

					user_specific_info.add(courier_specific_info);

				} else if (getRadio_restaurant().isSelected()) {
					restaurant_specific_info.removeAll();
					restaurant_specific_info.add(address_panel, BorderLayout.SOUTH);

					user_specific_info.add(restaurant_specific_info);

				} else if (getRadio_manager().isSelected()) {
					manager_specific_info.removeAll();

					manager_specific_info.add(getSurname_JTF(), BorderLayout.SOUTH);
					user_specific_info.add(manager_specific_info);

				}
				fillUserGlobalInfoPanel();

				user_global_info.add(getHome_button(), BorderLayout.SOUTH);
				user_global_info.add(backToRegister_button, BorderLayout.SOUTH);
				if (!getRadio_manager().isVisible()) {
					user_global_info.remove(addUserButton);
					user_global_info.add(register_button, BorderLayout.SOUTH);
				} else {
					user_global_info.remove(register_button);
					user_global_info.add(addUserButton, BorderLayout.SOUTH);
				}

				setCurrentPanel(user_global_info);
			} else {
				popUpOkWindow("Different passwords! Please try again.");
			}
		}
	}

	private class GoToLoginButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			goToLogInPanel();
		}
	}

	private class GoToRegisterButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			goToRegisterPanel();
			getHome_button().setVisible(true);
			getRadio_manager().setVisible(false);
		}
	}

	private class GoBackToRegisterButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			register_panel_info.add(getHome_button(), BorderLayout.SOUTH);
			register_panel_info.add(createAccount_button, BorderLayout.SOUTH);
			setCurrentPanel(register_panel_info);
		}
	}

	private class HomeButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			goToHomePage();
		}
	}

	private class RegisterButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try{
				address = new Address(Integer.parseInt(getxCoordinate_JTF().getText()),
						Integer.parseInt(getyCoordinate_JTF().getText()));

				name = getName_JTF().getText();
				if (customer_specific_info.isShowing()) {
					surname = getSurname_JTF().getText();
					emailAddress = getEmailAddress_JTF().getText();
					phoneNum = getPhoneNum_JTF().getText();

					core.register(new Customer(name, surname, address, phoneNum, emailAddress, username,
							new String(password)));
				} else if (courier_specific_info.isShowing()) {
					surname = getSurname_JTF().getText();
					phoneNum = getPhoneNum_JTF().getText();

					core.register(new Courier(name, surname, address, phoneNum, username, new String(password)));
				} else if (restaurant_specific_info.isShowing()) {

					core.register(new Restaurant(name, address, username, new String(password)));
				}
				goToLogInPanel();
			} catch (AlreadyUsedUsernameException e2) {
				popUpOkWindow("Username is already taken. Try a different one.");
			} catch (NumberFormatException ex) {
				popUpOkWindow("Please use only integers for the address coordinates.");
			}
		}

	}

	private class LoginButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			username = getUsername_JTF().getText();
			char[] code = getPassword_JTF().getPassword();

			core.logIn(username, new String(code));

			User current_user = core.getCurrent_user();
			
			if (current_user == null) {
				popUpOkWindow("Wrong password or username! If you have forgotten your password or username, "
						+ "\n please write a mail to john.de-wasseige@student.ecp.fr.");
			} else {

				if (current_user instanceof Courier) {
					setCurrentLogInUser(new CourierFrame());
				} else if (current_user instanceof Customer) {
					setCurrentLogInUser(new CustomerFrame());
				} else if (current_user instanceof Manager) {
					setCurrentLogInUser(new ManagerFrame());
				} else if (current_user instanceof Restaurant) {
					setCurrentLogInUser(new RestaurantFrame());
				}
				currentLogInUser.getInstance(current_user);
			}
		}
	}

	class WindowEventHandler extends WindowAdapter {
		public void windowClosing(WindowEvent evt) {
			core.logOut();;
			try {
				FileOutputStream fileOut = new FileOutputStream("./ser_files/gui_core.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(core);
				out.close();
				fileOut.close();
				System.out.println("Serialized data is saved in ./ser_files/gui_core.ser");
			}catch(IOException i) {
				i.printStackTrace();
			}
			System.exit(0);
		}
	}
	
	/*******************************************************/
	/* Getters and Setters */


	/**
	 * @return the frame
	 */
	public static JFrame getFrame() {
		return frame;
	}

	/**
	 * @param frame the frame to set
	 */
	public static void setFrame(JFrame frame) {
		StartFrame.frame = frame;
	}

	/**
	 * @return the currentLogInUser
	 */
	public static UserFrame getCurrentLogInUser() {
		return currentLogInUser;
	}

	/**
	 * @param currentLogInUser the currentLogInUser to set
	 */
	public static void setCurrentLogInUser(UserFrame currentLogInUser) {
		StartFrame.currentLogInUser = currentLogInUser;
	}

	/**
	 * @return the core
	 */
	public static Core getCore() {
		return core;
	}

	/**
	 * @param core the core to set
	 */
	public static void setCore(Core core) {
		StartFrame.core = core;
	}

	/**
	 * @return the address_panel
	 */
	public JPanel getAddress_panel() {
		return address_panel;
	}

	/**
	 * @param address_panel the address_panel to set
	 */
	public void setAddress_panel(JPanel address_panel) {
		this.address_panel = address_panel;
	}

	/**
	 * @return the login_panel
	 */
	public JPanel getLogin_panel() {
		return login_panel;
	}

	/**
	 * @param login_panel the login_panel to set
	 */
	public void setLogin_panel(JPanel login_panel) {
		this.login_panel = login_panel;
	}

	/**
	 * @return the radio_customer
	 */
	public JRadioButton getRadio_customer() {
		return radio_customer;
	}

	/**
	 * @param radio_customer the radio_customer to set
	 */
	public void setRadio_customer(JRadioButton radio_customer) {
		this.radio_customer = radio_customer;
	}

	/**
	 * @return the radio_courier
	 */
	public JRadioButton getRadio_courier() {
		return radio_courier;
	}

	/**
	 * @param radio_courier the radio_courier to set
	 */
	public void setRadio_courier(JRadioButton radio_courier) {
		this.radio_courier = radio_courier;
	}

	/**
	 * @return the radio_restaurant
	 */
	public JRadioButton getRadio_restaurant() {
		return radio_restaurant;
	}

	/**
	 * @param radio_restaurant the radio_restaurant to set
	 */
	public void setRadio_restaurant(JRadioButton radio_restaurant) {
		this.radio_restaurant = radio_restaurant;
	}

	/**
	 * @return the radio_manager
	 */
	public static JRadioButton getRadio_manager() {
		return radio_manager;
	}

	/**
	 * @param radio_manager the radio_manager to set
	 */
	public static void setRadio_manager(JRadioButton radio_manager) {
		StartFrame.radio_manager = radio_manager;
	}

	/**
	 * @return the user_type_group
	 */
	public ButtonGroup getUser_type_group() {
		return user_type_group;
	}

	/**
	 * @param user_type_group the user_type_group to set
	 */
	public void setUser_type_group(ButtonGroup user_type_group) {
		this.user_type_group = user_type_group;
	}

	/**
	 * @return the logIn_button
	 */
	public JButton getLogIn_button() {
		return logIn_button;
	}

	/**
	 * @param logIn_button the logIn_button to set
	 */
	public void setLogIn_button(JButton logIn_button) {
		this.logIn_button = logIn_button;
	}

	/**
	 * @return the home_button
	 */
	public static JButton getHome_button() {
		return home_button;
	}

	/**
	 * @param home_button the home_button to set
	 */
	public static void setHome_button(JButton home_button) {
		StartFrame.home_button = home_button;
	}

	/**
	 * @return the goToRegister_button
	 */
	public JButton getGoToRegister_button() {
		return goToRegister_button;
	}

	/**
	 * @param goToRegister_button the goToRegister_button to set
	 */
	public void setGoToRegister_button(JButton goToRegister_button) {
		this.goToRegister_button = goToRegister_button;
	}

	/**
	 * @return the backToRegister_button
	 */
	public JButton getBackToRegister_button() {
		return backToRegister_button;
	}

	/**
	 * @param backToRegister_button the backToRegister_button to set
	 */
	public void setBackToRegister_button(JButton backToRegister_button) {
		this.backToRegister_button = backToRegister_button;
	}

	/**
	 * @return the createAccount_button
	 */
	public JButton getCreateAccount_button() {
		return createAccount_button;
	}

	/**
	 * @param createAccount_button the createAccount_button to set
	 */
	public void setCreateAccount_button(JButton createAccount_button) {
		this.createAccount_button = createAccount_button;
	}

	/**
	 * @return the goToLogIn_button
	 */
	public JButton getGoToLogIn_button() {
		return goToLogIn_button;
	}

	/**
	 * @param goToLogIn_button the goToLogIn_button to set
	 */
	public void setGoToLogIn_button(JButton goToLogIn_button) {
		this.goToLogIn_button = goToLogIn_button;
	}

	/**
	 * @return the register_button
	 */
	public JButton getRegister_button() {
		return register_button;
	}

	/**
	 * @param register_button the register_button to set
	 */
	public void setRegister_button(JButton register_button) {
		this.register_button = register_button;
	}

	/**
	 * @return the addUserButton
	 */
	public static Button getAddUserButton() {
		return addUserButton;
	}

	/**
	 * @param addUserButton the addUserButton to set
	 */
	public static void setAddUserButton(Button addUserButton) {
		StartFrame.addUserButton = addUserButton;
	}

	/**
	 * @return the surname_JTF
	 */
	public JTextField getSurname_JTF() {
		return surname_JTF;
	}

	/**
	 * @param surname_JTF the surname_JTF to set
	 */
	public void setSurname_JTF(JTextField surname_JTF) {
		this.surname_JTF = surname_JTF;
	}

	/**
	 * @return the xCoordinate_JTF
	 */
	public JTextField getxCoordinate_JTF() {
		return xCoordinate_JTF;
	}

	/**
	 * @param xCoordinate_JTF the xCoordinate_JTF to set
	 */
	public void setxCoordinate_JTF(JTextField xCoordinate_JTF) {
		this.xCoordinate_JTF = xCoordinate_JTF;
	}

	/**
	 * @return the yCoordinate_JTF
	 */
	public JTextField getyCoordinate_JTF() {
		return yCoordinate_JTF;
	}

	/**
	 * @param yCoordinate_JTF the yCoordinate_JTF to set
	 */
	public void setyCoordinate_JTF(JTextField yCoordinate_JTF) {
		this.yCoordinate_JTF = yCoordinate_JTF;
	}

	/**
	 * @return the phoneNum_JTF
	 */
	public JTextField getPhoneNum_JTF() {
		return phoneNum_JTF;
	}

	/**
	 * @param phoneNum_JTF the phoneNum_JTF to set
	 */
	public void setPhoneNum_JTF(JTextField phoneNum_JTF) {
		this.phoneNum_JTF = phoneNum_JTF;
	}

	/**
	 * @return the emailAddress_JTF
	 */
	public JTextField getEmailAddress_JTF() {
		return emailAddress_JTF;
	}

	/**
	 * @param emailAddress_JTF the emailAddress_JTF to set
	 */
	public void setEmailAddress_JTF(JTextField emailAddress_JTF) {
		this.emailAddress_JTF = emailAddress_JTF;
	}

	/**
	 * @return the username_JTF
	 */
	public JTextField getUsername_JTF() {
		return username_JTF;
	}

	/**
	 * @param username_JTF the username_JTF to set
	 */
	public void setUsername_JTF(JTextField username_JTF) {
		this.username_JTF = username_JTF;
	}

	/**
	 * @return the password_JTF
	 */
	public JPasswordField getPassword_JTF() {
		return password_JTF;
	}

	/**
	 * @param password_JTF the password_JTF to set
	 */
	public void setPassword_JTF(JPasswordField password_JTF) {
		this.password_JTF = password_JTF;
	}

	/**
	 * @return the passwordConf_JTF
	 */
	public JPasswordField getPasswordConf_JTF() {
		return passwordConf_JTF;
	}

	/**
	 * @param passwordConf_JTF the passwordConf_JTF to set
	 */
	public void setPasswordConf_JTF(JPasswordField passwordConf_JTF) {
		this.passwordConf_JTF = passwordConf_JTF;
	}

	/**
	 * @return the name_JTF
	 */
	public JTextField getName_JTF() {
		return name_JTF;
	}

	/**
	 * @param name_JTF the name_JTF to set
	 */
	public void setName_JTF(JTextField name_JTF) {
		this.name_JTF = name_JTF;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the phoneNum
	 */
	public String getPhoneNum() {
		return phoneNum;
	}

	/**
	 * @param phoneNum the phoneNum to set
	 */
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public char[] getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(char[] password) {
		this.password = password;
	}

	/**
	 * @return the passwordConf
	 */
	public char[] getPasswordConf() {
		return passwordConf;
	}

	/**
	 * @param passwordConf the passwordConf to set
	 */
	public void setPasswordConf(char[] passwordConf) {
		this.passwordConf = passwordConf;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the welcome_panel
	 */
	public JPanel getWelcome_panel() {
		return welcome_panel;
	}

	/**
	 * @param welcome_panel the welcome_panel to set
	 */
	public void setWelcome_panel(JPanel welcome_panel) {
		this.welcome_panel = welcome_panel;
	}

	/**
	 * @return the welcome_button_panel
	 */
	public JPanel getWelcome_button_panel() {
		return welcome_button_panel;
	}

	/**
	 * @param welcome_button_panel the welcome_button_panel to set
	 */
	public void setWelcome_button_panel(JPanel welcome_button_panel) {
		this.welcome_button_panel = welcome_button_panel;
	}

	/**
	 * @return the welcome_message_panel
	 */
	public JPanel getWelcome_message_panel() {
		return welcome_message_panel;
	}

	/**
	 * @param welcome_message_panel the welcome_message_panel to set
	 */
	public void setWelcome_message_panel(JPanel welcome_message_panel) {
		this.welcome_message_panel = welcome_message_panel;
	}

	/**
	 * @return the register_panel_info
	 */
	public static JPanel getRegister_panel_info() {
		return register_panel_info;
	}

	/**
	 * @param register_panel_info the register_panel_info to set
	 */
	public static void setRegister_panel_info(JPanel register_panel_info) {
		StartFrame.register_panel_info = register_panel_info;
	}

	/**
	 * @return the user_global_info
	 */
	public static JPanel getUser_global_info() {
		return user_global_info;
	}

	/**
	 * @param user_global_info the user_global_info to set
	 */
	public static void setUser_global_info(JPanel user_global_info) {
		StartFrame.user_global_info = user_global_info;
	}

	/**
	 * @return the user_specific_info
	 */
	public JPanel getUser_specific_info() {
		return user_specific_info;
	}

	/**
	 * @param user_specific_info the user_specific_info to set
	 */
	public void setUser_specific_info(JPanel user_specific_info) {
		this.user_specific_info = user_specific_info;
	}

	/**
	 * @return the customer_specific_info
	 */
	public JPanel getCustomer_specific_info() {
		return customer_specific_info;
	}

	/**
	 * @param customer_specific_info the customer_specific_info to set
	 */
	public void setCustomer_specific_info(JPanel customer_specific_info) {
		this.customer_specific_info = customer_specific_info;
	}

	/**
	 * @return the courier_specific_info
	 */
	public JPanel getCourier_specific_info() {
		return courier_specific_info;
	}

	/**
	 * @param courier_specific_info the courier_specific_info to set
	 */
	public void setCourier_specific_info(JPanel courier_specific_info) {
		this.courier_specific_info = courier_specific_info;
	}

	/**
	 * @return the restaurant_specific_info
	 */
	public JPanel getRestaurant_specific_info() {
		return restaurant_specific_info;
	}

	/**
	 * @param restaurant_specific_info the restaurant_specific_info to set
	 */
	public void setRestaurant_specific_info(JPanel restaurant_specific_info) {
		this.restaurant_specific_info = restaurant_specific_info;
	}

	/**
	 * @return the manager_specific_info
	 */
	public JPanel getManager_specific_info() {
		return manager_specific_info;
	}

	/**
	 * @param manager_specific_info the manager_specific_info to set
	 */
	public void setManager_specific_info(JPanel manager_specific_info) {
		this.manager_specific_info = manager_specific_info;
	}

	/**
	 * @return the manager
	 */
	public static UserFrame getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public static void setManager(UserFrame manager) {
		StartFrame.manager = manager;
	}

	/**
	 * @param instance the instance to set
	 */
	public static void setInstance(StartFrame instance) {
		StartFrame.instance = instance;
	}

	
	

}
