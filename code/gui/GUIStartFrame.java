package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

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

public class GUIStartFrame {

	private static GUIStartFrame instance;
	private static JFrame frame = new JFrame("My Foodora");
	private static GUIUserFrame currentLogInUser;

	private static Core core;

	JPanel address_panel = new JPanel();
	JPanel login_panel = new JPanel();

	// radio buttons for user to register
	JRadioButton radio_customer = new JRadioButton("Customer", true);
	JRadioButton radio_courier = new JRadioButton("Courier");
	JRadioButton radio_restaurant = new JRadioButton("Restaurant");
	static JRadioButton radio_manager = new JRadioButton("Manager");
	ButtonGroup user_type_group = new ButtonGroup();

	// buttons
	JButton logIn_button = new JButton("LOG IN");
	static JButton home_button = new JButton("GO TO HOME");
	JButton goToRegister_button = new JButton("GO TO REGISTER");
	JButton backToRegister_button = new JButton("GO BACK");
	JButton createAccount_button = new JButton("CREATE ACCOUNT");
	JButton goToLogIn_button = new JButton("GO TO LOG IN");
	JButton register_button = new JButton("REGISTER");
	static Button addUserButton = new Button("ADD USER");

	// Textfields to register
	JTextField surname_JTF = new JTextField(15);
	JTextField xCoordinate_JTF = new JTextField(15);
	JTextField yCoordinate_JTF = new JTextField(15);
	JTextField phoneNum_JTF = new JTextField(15);
	JTextField emailAddress_JTF = new JTextField(15);
	JTextField username_JTF = new JTextField(15);
	JTextField password_JTF = new JTextField(15);
	JTextField passwordConf_JTF = new JTextField(15);
	JTextField name_JTF = new JTextField(15);

	// String to register
	String surname;
	Address address;
	String phoneNum;
	String emailAddress;
	String username;
	String password;
	String passwortConf;
	String name;

	// panels when user register and add info
	JPanel welcome_panel = new JPanel();
	JPanel welcome_button_panel = new JPanel();
	JPanel welcome_message_panel = new JPanel();
	static JPanel register_panel_info = new JPanel();
	static JPanel user_global_info = new JPanel();
	JPanel user_specific_info = new JPanel();
	JPanel customer_specific_info = new JPanel();
	JPanel courier_specific_info = new JPanel();
	JPanel restaurant_specific_info = new JPanel();
	JPanel manager_specific_info = new JPanel();

	// Manager for add User button
	static GUIUserFrame manager;

	/*********************************************************/
	// HelpFunctions

	public static GUIStartFrame getInstance() {
		if (instance == null) {
			instance = new GUIStartFrame();
			// For testing

			core = new Core();

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

		name_JTF.setPreferredSize(new Dimension(150, 30));
		name_JTF.setForeground(Color.BLUE);
		name_JTF.setText("Insert your name");

		user_specific_info.setBackground(Color.gray);

		user_global_info.add(ask_info, BorderLayout.NORTH);
		user_global_info.add(name_JTF, BorderLayout.NORTH);
		user_global_info.add(user_specific_info, BorderLayout.CENTER);
		frame.add(user_global_info);
	}

	private void fillAddressInfosPanel() {
		address_panel.removeAll();
		address_panel.add(xCoordinate_JTF);
		address_panel.add(yCoordinate_JTF);
	}

	public void fillCourierSpecificInfosPanel() {
		courier_specific_info.setLayout(new BorderLayout());
		courier_specific_info.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
		courier_specific_info.setBackground(Color.gray);
	}

	public void fillCustomerSpecificInfosPanel() {
		customer_specific_info.setLayout(new BorderLayout());
		customer_specific_info.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
		customer_specific_info.setBackground(Color.gray);
	}

	public void fillRestaurantSpecificInfosPanel() {
		restaurant_specific_info.setLayout(new BorderLayout());
		restaurant_specific_info.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
		restaurant_specific_info.setBackground(Color.gray);
	}

	public void fillManagerSpecificInfosPanel() {
		manager_specific_info.setLayout(new BorderLayout());
		manager_specific_info.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
		manager_specific_info.setBackground(Color.green);
	}

	public void fillLoginPanel() {
		login_panel.setBackground(Color.green);
		login_panel.setBorder(BorderFactory.createEmptyBorder(30, 150, 10, 150));
		frame.add(login_panel);
	}

	public void fillRegisterPanel() {
		register_panel_info.setBackground(Color.gray);
		register_panel_info.setBorder(BorderFactory.createEmptyBorder(30, 150, 10, 150));

		user_type_group.add(radio_restaurant);
		user_type_group.add(radio_courier);
		user_type_group.add(radio_customer);
		user_type_group.add(radio_manager);

		JPanel user_type = new JPanel();
		user_type.setBackground(Color.gray);
		user_type.add(radio_customer);
		user_type.add(radio_courier);
		user_type.add(radio_restaurant);
		user_type.add(radio_manager);
		register_panel_info.add(user_type, BorderLayout.NORTH);
		frame.add(register_panel_info);
	}

	public void fillWelcomePanel() {

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
		welcome_text.setText("If you already have a username, please LOGIN !\n" + "If not, then REGISTER !");
		welcome_text.setBackground(Color.yellow);

		welcome_panel.add(welcome_message_panel, BorderLayout.NORTH);
		welcome_panel.add(welcome_scrollPane, BorderLayout.CENTER);
		welcome_panel.add(welcome_button_panel, BorderLayout.SOUTH);

		// Buttons
		welcome_button_panel.add(goToLogIn_button, BorderLayout.CENTER);
		welcome_button_panel.add(goToRegister_button, BorderLayout.CENTER);
		frame.add(welcome_panel);
	}

	/*********************************************************/

	/*********************************************************/
	/* Functions */

	public void popUpOkWindow(String message) {
		Object[] options = { "OK" };
		JOptionPane.showOptionDialog(null, message, "Attention", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE,
				null, options, options[0]);
	}

	public void setCurrentPanel(JPanel panel) {
		frame.setContentPane(panel);
		frame.setVisible(true);
	}

	public void initSettings() {
		home_button.addActionListener(new HomeButton());

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
		home_button.addActionListener(new HomeButton());
		goToRegister_button.addActionListener(new GoToRegisterButton());
		backToRegister_button.addActionListener(new GoBackToRegisterButton());
		createAccount_button.addActionListener(new WhatAccountType());
		register_button.addActionListener(new RegisterButton());
		logIn_button.addActionListener(new LoginButton());

		addUserButton.addActionListener((ActionEvent e) -> {

			name = name_JTF.getText();
			try {
				if (customer_specific_info.isShowing()) {
					surname = surname_JTF.getText();
					emailAddress = emailAddress_JTF.getText();
					phoneNum = phoneNum_JTF.getText();

					address = new Address(Integer.parseInt(xCoordinate_JTF.getText()),
							Integer.parseInt(yCoordinate_JTF.getText()));

					core.addUser(new Customer(name, surname, address, phoneNum, emailAddress, username, password));
				} else if (courier_specific_info.isShowing()) {
					surname = surname_JTF.getText();
					phoneNum = phoneNum_JTF.getText();

					address = new Address(Integer.parseInt(xCoordinate_JTF.getText()),
							Integer.parseInt(yCoordinate_JTF.getText()));

					core.addUser(new Courier(name, surname, address, phoneNum, username, password));
				} else if (restaurant_specific_info.isShowing()) {

					address = new Address(Integer.parseInt(xCoordinate_JTF.getText()),
							Integer.parseInt(yCoordinate_JTF.getText()));

					core.addUser(new Restaurant(name, address, username, password));
				} else if (manager_specific_info.isShowing()) {

					surname = surname_JTF.getText();
					core.addUser(new Manager(name, surname, username, password));
				}
				manager.setCurrentPanel(manager.welcome_panel);
				user_global_info.remove(addUserButton);
				register_panel_info.remove(((GUIManagerFrame) manager).getGoBackfromAddUserButton());
			} catch (Exception ex) {
				popUpOkWindow("Please use only integers for the address coordinates.");
			}

		});
	}

	public void open(final int xLocation, final int yLocation, final int width, final int height) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.setBounds(xLocation, yLocation, width, height);
				initSettings();
				frame.setVisible(true);
			}
		});
	}

	private boolean checkIfPassWordIsEqual(String passwort, String passwortConf) {
		if (passwort.equals(passwortConf)) {
			return true;
		} else {
			return false;
		}
	}

	public void goToHomePage() {
		setCurrentPanel(welcome_panel);
	}

	private void goToLogInPanel() {

		home_button.setVisible(true);
		login_panel.add(home_button, BorderLayout.SOUTH);
		login_panel.add(logIn_button, BorderLayout.SOUTH);

		username_JTF.setPreferredSize(new Dimension(150, 30));
		username_JTF.setForeground(Color.BLUE);
		username_JTF.setText("Insert your username");

		password_JTF.setPreferredSize(new Dimension(150, 30));
		password_JTF.setForeground(Color.BLUE);
		password_JTF.setText("Insert your password");

		login_panel.add(username_JTF, BorderLayout.NORTH);
		login_panel.add(password_JTF, BorderLayout.CENTER);

		setCurrentPanel(login_panel);
	}

	public void goToRegisterPanel() {
		register_panel_info.add(createAccount_button, BorderLayout.SOUTH);
		register_panel_info.add(home_button, BorderLayout.SOUTH);

		register_panel_info.add(username_JTF, BorderLayout.NORTH);
		register_panel_info.add(password_JTF, BorderLayout.CENTER);
		register_panel_info.add(passwordConf_JTF, BorderLayout.CENTER);

		username_JTF.setForeground(Color.BLUE);
		username_JTF.setText("Insert a username");

		password_JTF.setForeground(Color.BLUE);
		password_JTF.setText("Insert a password");

		passwordConf_JTF.setForeground(Color.BLUE);
		passwordConf_JTF.setText("Confirm the password");

		setCurrentPanel(register_panel_info);
	}

	/*********************************************************/
	/* ActionListeners */

	private class WhatAccountType implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			username = username_JTF.getText();
			password = password_JTF.getText();
			passwortConf = passwordConf_JTF.getText();

			if (radio_manager.isVisible())
				register_button.setVisible(false);
			else
				register_button.setVisible(true);

			if (checkIfPassWordIsEqual(password, passwortConf)) {

				user_specific_info.removeAll();

				surname_JTF.setText("Insert your surname");
				phoneNum_JTF.setText("Insert your phone number");
				emailAddress_JTF.setText("Insert your Email address");
				xCoordinate_JTF.setText("Insert xCoordinate");
				yCoordinate_JTF.setText("Insert yCoordinate");

				if (radio_customer.isSelected()) {
					customer_specific_info.removeAll();
					customer_specific_info.add(surname_JTF, BorderLayout.NORTH);
					customer_specific_info.add(phoneNum_JTF, BorderLayout.WEST);
					customer_specific_info.add(emailAddress_JTF, BorderLayout.EAST);
					customer_specific_info.add(address_panel, BorderLayout.SOUTH);

					user_specific_info.add(customer_specific_info);

				} else if (radio_courier.isSelected()) {
					courier_specific_info.removeAll();
					courier_specific_info.add(surname_JTF, BorderLayout.EAST);
					courier_specific_info.add(address_panel, BorderLayout.CENTER);
					courier_specific_info.add(phoneNum_JTF, BorderLayout.WEST);
					courier_specific_info.add(address_panel, BorderLayout.SOUTH);

					user_specific_info.add(courier_specific_info);

				} else if (radio_restaurant.isSelected()) {
					restaurant_specific_info.removeAll();
					restaurant_specific_info.add(address_panel, BorderLayout.SOUTH);

					user_specific_info.add(restaurant_specific_info);

				} else if (radio_manager.isSelected()) {
					manager_specific_info.removeAll();

					manager_specific_info.add(surname_JTF, BorderLayout.SOUTH);
					user_specific_info.add(manager_specific_info);


				}
				fillUserGlobalInfoPanel();

				user_global_info.add(home_button, BorderLayout.SOUTH);
				user_global_info.add(backToRegister_button, BorderLayout.SOUTH);
				if (!radio_manager.isVisible())
					user_global_info.add(register_button, BorderLayout.SOUTH);
				
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
			home_button.setVisible(true);
			radio_manager.setVisible(false);
		}
	}

	private class GoBackToRegisterButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			register_panel_info.add(home_button, BorderLayout.SOUTH);
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

			address = new Address(Integer.parseInt(xCoordinate_JTF.getText()),
					Integer.parseInt(yCoordinate_JTF.getText()));
			name = name_JTF.getText();
			try {
				if (customer_specific_info.isShowing()) {
					surname = surname_JTF.getText();
					emailAddress = emailAddress_JTF.getText();
					phoneNum = phoneNum_JTF.getText();

					core.register(new Customer(name, surname, address, phoneNum, emailAddress, username, password));
				} else if (courier_specific_info.isShowing()) {
					surname = surname_JTF.getText();
					phoneNum = phoneNum_JTF.getText();

					core.register(new Courier(name, surname, address, phoneNum, username, password));
				} else if (restaurant_specific_info.isShowing()) {

					core.register(new Restaurant(name, address, username, password));
				}
			} catch (AlreadyUsedUsernameException e2) {
				popUpOkWindow("Username is already taken. Try a different one.");
			} catch (NumberFormatException ex) {
				popUpOkWindow("Please use only integers for the address coordinates.");
			}
			goToLogInPanel();
		}

	}

	private class LoginButton implements ActionListener {
		// @SuppressWarnings("unused")
		public void actionPerformed(ActionEvent e) {

			username = username_JTF.getText();
			String code = password_JTF.getText();

				core.logIn(username, code);
			
			User current_user = core.getCurrent_user();
			if (current_user == null) {
				popUpOkWindow("Wrong password or username! If you have forgotten your password or username, "
						+ "\n please write a mail to john.de-wasseige@student.ecp.fr.");
			} else {

				if (current_user instanceof Courier) {
					setCurrentLogInUser(new GUICourierFrame());
				} else if (current_user instanceof Customer) {
					setCurrentLogInUser(new GUICustomerFrame());
				} else if (current_user instanceof Manager) {
					setCurrentLogInUser(new GUIManagerFrame());
				} else if (current_user instanceof Restaurant) {
					setCurrentLogInUser(new GUIRestaurantFrame());
				}
				currentLogInUser.getInstance(current_user);
			}
		}
	}
	
	/*******************************************************/
	/* Getters and Setters */

	public static JFrame getFrame() {
		return frame;
	}

	public static void setFrame(JFrame frame) {
		GUIStartFrame.frame = frame;
	}

	public static GUIUserFrame getCurrentLogInUser() {
		return currentLogInUser;
	}

	public static void setCurrentLogInUser(GUIUserFrame currentLogInUser) {
		GUIStartFrame.currentLogInUser = currentLogInUser;
	}

	public JPanel getLogin_panel() {
		return login_panel;
	}

	public JPanel getRegister_panel_info() {
		return register_panel_info;
	}

	public JPanel getWelcome_panel() {
		return welcome_panel;
	}

	public static Core getCore() {
		return core;
	}

}
