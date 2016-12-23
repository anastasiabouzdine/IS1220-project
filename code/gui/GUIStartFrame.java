package gui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


import clui.Command;
import clui.CommandProcessor;
import core.Core;
import exceptions.AlreadyUsedUsernameException;
import parsers.ParseCouriers;
import parsers.ParseCustomers;
import parsers.ParseManagers;
import parsers.ParseRestaurants;
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
	
	private static CommandProcessor cmd_processor = CommandProcessor.getInstance();


	JPanel address_panel = new JPanel();
	JPanel login_panel = new JPanel();
	
	// radio buttons for user to register
	JRadioButton radio_customer = new JRadioButton("Customer", true);
	JRadioButton radio_courier = new JRadioButton("Courier");
	JRadioButton radio_restaurant = new JRadioButton("Restaurant");
	ButtonGroup user_type_group = new ButtonGroup();
	
	// buttons 
	JButton logIn_button = new JButton("LOG IN");
	JButton	home_button = new JButton("GO TO HOME");
	JButton goToRegister_button = new JButton("GO TO REGISTER");
	JButton backToRegister_button = new JButton("GO BACK");
	JButton createAccount_button = new JButton("CREATE ACCOUNT");
	JButton goToLogIn_button = new JButton("GO TO LOG IN");
	JButton register_button = new JButton("REGISTER");
	
	
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
	String address;
	String phoneNum;
	String emailAddress;
	String username;
	String passwort;
	String passwortConf;
	String name;
	
	
	
 
	// panels when user register and add info
	JPanel welcome_panel = new JPanel();
	JPanel welcome_button_panel = new JPanel();
	JPanel welcome_message_panel = new JPanel();
	JPanel register_panel_info = new JPanel();
	JPanel user_global_info = new JPanel();
	JPanel user_specific_info = new JPanel();
	JPanel customer_specific_info = new JPanel();
	JPanel courier_specific_info = new JPanel();
	JPanel restaurant_specific_info = new JPanel();
	
	/*********************************************************/
	// HelpFunctions
	
	public static GUIStartFrame getInstance() {
		if(instance == null){
			instance = new GUIStartFrame();
			// For testing
			
		}
		return instance;
	}
	
	
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
	public static CommandProcessor getCmd_processor() {
		return cmd_processor;
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

	/*********************************************************/
	/* Fill panels */
	public void fillUserGlobalInfoPanel() {
		user_global_info.setBorder(BorderFactory.createEmptyBorder(30, 150, 10, 150));
		user_global_info.setBackground(Color.red);

		JTextField ask_info = new JTextField(23);
	    Font font = new Font("SansSerif", Font.BOLD, 12);
	    ask_info.setFont(font);
	    ask_info.setBackground(Color.red);
	    ask_info.setText("PLEASE FILL OUT THE FOLLOWING INFOS");
	    ask_info.setEditable(false);
		
		
		name_JTF.setPreferredSize(new Dimension(150, 30));
		name_JTF.setForeground(Color.BLUE);
		name_JTF.setText("Insert your name");
		
		user_specific_info.setBackground(Color.green);
		
	    user_global_info.add(ask_info, BorderLayout.NORTH);
		user_global_info.add(name_JTF, BorderLayout.NORTH);
	    user_global_info.add(user_specific_info, BorderLayout.CENTER);
	    frame.add(user_global_info);
	}
	
	private void fillAddressInfosPanel() {	
		address_panel.add(xCoordinate_JTF);
		address_panel.add(yCoordinate_JTF);
	}
	
	public void fillCourierSpecificInfosPanel() {
		courier_specific_info.setLayout(new BorderLayout());
		courier_specific_info.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
		courier_specific_info.setBackground(Color.green);
	}
	
	public void fillCustomerSpecificInfosPanel() {
		customer_specific_info.setLayout(new BorderLayout());
		customer_specific_info.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
		customer_specific_info.setBackground(Color.green);
	}
	
	public void fillRestaurantSpecificInfosPanel() {
		restaurant_specific_info.setLayout(new BorderLayout());
		restaurant_specific_info.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
		restaurant_specific_info.setBackground(Color.green);
	}
	
	public void fillLoginPanel() {
		login_panel.setBackground(Color.green);
		login_panel.setBorder(BorderFactory.createEmptyBorder(30, 150, 10, 150));
		frame.add(login_panel);
	}
	
	public void fillRegisterPanel() {
		register_panel_info.setBackground(Color.red);
		register_panel_info.setBorder(BorderFactory.createEmptyBorder(30, 150, 10, 150));
		
		user_type_group.add(radio_restaurant);
		user_type_group.add(radio_courier);
		user_type_group.add(radio_customer);
		
		JPanel user_type = new JPanel();
		user_type.setBackground(Color.red);
		user_type.add(radio_customer);
		user_type.add(radio_courier);
		user_type.add(radio_restaurant);
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
	    welcome_text.setText("If you already have a username, please LOGIN !\n"
	    		+ "If not, then REGISTER !");
	    welcome_text.setBackground(Color.yellow);

		welcome_panel.add(welcome_message_panel, BorderLayout.NORTH);
		welcome_panel.add(welcome_scrollPane, BorderLayout.CENTER);
		welcome_panel.add(welcome_button_panel, BorderLayout.SOUTH); 
		
		//Buttons
		welcome_button_panel.add(goToLogIn_button, BorderLayout.CENTER);
		welcome_button_panel.add(goToRegister_button, BorderLayout.CENTER);
		frame.add(welcome_panel);
   	}
	
	/*********************************************************/
	
	
	
	
	
	
	/*********************************************************/
	/* Functions */
	
	public void setCurrentPanel(JPanel panel) {
    	frame.setContentPane(panel);  	
    	frame.setVisible(true);
	}
	
	public void initSettings(){
        home_button.addActionListener(new HomeButton());
        
        
        fillWelcomePanel();
        fillRegisterPanel();
        fillLoginPanel();
        fillUserGlobalInfoPanel();
        fillAddressInfosPanel();
        
        fillCourierSpecificInfosPanel();
        fillCustomerSpecificInfosPanel();
        fillRestaurantSpecificInfosPanel();
        
        
        setCurrentPanel(welcome_panel);
        
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	
    	goToLogIn_button.addActionListener(new GoToLoginButton());
    	home_button.addActionListener(new HomeButton());
    	goToRegister_button.addActionListener(new GoToRegisterButton());
    	backToRegister_button.addActionListener(new GoBackToRegisterButton());
    	createAccount_button.addActionListener(new WhatAccountType());
    	register_button.addActionListener(new RegisterButton());
    	logIn_button.addActionListener(new LoginButton());	
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
		if(passwort.equals(passwortConf)){
			return true;
		}
		else {
			return false;
		}
	}
	
	public void goToHomePage() {
		setCurrentPanel(welcome_panel);
	}
	
	private void goToLogInPanel(){
		
		login_panel.add(home_button, BorderLayout.SOUTH);
		login_panel.add(logIn_button, BorderLayout.SOUTH);
		
		username_JTF.setPreferredSize(new Dimension(150, 30));
		username_JTF.setForeground(Color.BLUE);
		username_JTF.setText("Insert your username");
		//TODO find a function that deletes the text when clicked on 

		password_JTF.setPreferredSize(new Dimension(150, 30));
		password_JTF.setForeground(Color.BLUE);
		password_JTF.setText("Insert your password");
		
		login_panel.add(username_JTF, BorderLayout.NORTH);
		login_panel.add(password_JTF, BorderLayout.CENTER);
		
		setCurrentPanel(login_panel);
	}
	
	private void goToRegisterPanel(){
		register_panel_info.add(home_button, BorderLayout.SOUTH);
		register_panel_info.add(createAccount_button, BorderLayout.SOUTH);
		
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
	
	private class WhatAccountType implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			username = username_JTF.getText();
			passwort = password_JTF.getText();
			passwortConf = passwordConf_JTF.getText();
			
			if(checkIfPassWordIsEqual(passwort,passwortConf)){
					
				user_specific_info.removeAll();
				
				surname_JTF.setText("Insert your surname");
				phoneNum_JTF.setText("Insert your phone number");
				emailAddress_JTF.setText("Insert your Email address");
				xCoordinate_JTF.setText("Insert xCoordinate");
				yCoordinate_JTF.setText("Insert yCoordinate");
				
				if (radio_customer.isSelected()){
					
					customer_specific_info.add(surname_JTF,BorderLayout.NORTH);
					customer_specific_info.add(phoneNum_JTF,BorderLayout.WEST);
					customer_specific_info.add(emailAddress_JTF,BorderLayout.EAST);
					customer_specific_info.add(address_panel, BorderLayout.SOUTH);
					
					user_specific_info.add(customer_specific_info);
					
				} else if (radio_courier.isSelected()){
	
					courier_specific_info.add(surname_JTF,BorderLayout.EAST);
					courier_specific_info.add(address_panel, BorderLayout.CENTER);
					courier_specific_info.add(phoneNum_JTF,BorderLayout.WEST);
					courier_specific_info.add(address_panel, BorderLayout.SOUTH);
					
					user_specific_info.add(courier_specific_info);
		
				} else if (radio_restaurant.isSelected()){
					
					user_specific_info.add(restaurant_specific_info);
					
					restaurant_specific_info.add(address_panel, BorderLayout.SOUTH);
					
				}
				user_global_info.add(home_button, BorderLayout.SOUTH);
				user_global_info.add(backToRegister_button, BorderLayout.SOUTH);
				user_global_info.add(register_button, BorderLayout.SOUTH);
				
				setCurrentPanel(user_global_info);
				}
			else {
				//TODO pop up window
				System.out.println("Wrong password!!!");
			}
			}
	}
	
	private class GoToLoginButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			goToLogInPanel();
		}
	}
	
	private class GoToRegisterButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			goToRegisterPanel();
		}
	}
	
	private class GoBackToRegisterButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			register_panel_info.add(home_button, BorderLayout.SOUTH);
			register_panel_info.add(createAccount_button, BorderLayout.SOUTH);
			setCurrentPanel(register_panel_info);
		}
	}
	
	private class HomeButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			goToHomePage();
		}
	}
	
	
	private class RegisterButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			address = new StringBuilder().append(xCoordinate_JTF.getText()).append(",").append(yCoordinate_JTF.getText()).toString();
			name = name_JTF.getText();
			Command command =null;
			
			if(customer_specific_info.isShowing()){
				surname = surname_JTF.getText();
				emailAddress = emailAddress_JTF.getText();
				phoneNum = phoneNum_JTF.getText();
				
				String[] commandArgsCust = {surname,name,username,address,passwort};
				command = new Command("registerCustomer", commandArgsCust);
			}
			else if(courier_specific_info.isShowing()){
				surname = surname_JTF.getText();
				phoneNum = phoneNum_JTF.getText();
				
				String[] commandArgsCour = {surname,name,username,address,passwort};
				command = new Command("registerCourier", commandArgsCour);
			}
			else if(restaurant_specific_info.isShowing()){
				
				String[] commandArgsRest = {name,username,address,passwort};
				command = new Command("registerRestaurant", commandArgsRest);
			}
			
			try {
				cmd_processor.processCmd(command);
			} catch (Exception ex) {
				ex.getMessage();
			}
			goToLogInPanel();
		}
	
	}
	
	private class LoginButton implements ActionListener {
//		@SuppressWarnings("unused")
		public void actionPerformed(ActionEvent e){
			Core core = cmd_processor.getCore();
			username = username_JTF.getText();
			String code = password_JTF.getText();
			
			String[] commandArgsLogIn = {username,code};
			
			try {
				cmd_processor.processCmd(new Command("login", commandArgsLogIn));
			} catch (Exception ex) {
				ex.getMessage();
			}

			User current_user = core.getCurrent_user();
			if (current_user == null) {
				System.out.println("No log in!");
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
	

	
	/**
	 * @throws AWTException 
	 * @throws AlreadyUsedUsernameException *******************************************************/
	/* Launch */
	public static void main(String[] args) throws AWTException, AlreadyUsedUsernameException {
		
		GUIStartFrame gui = GUIStartFrame.getInstance();
		gui.open(0, 0, 600, 400);
		
		//Register Tests - can be run all together
//		GUIStartFrameTest.checkIfClickGoToButtonsWork();
//		GUIStartFrameTest.checkIfRestaurantCanBeRegistered();
//		GUIStartFrameTest.checkIfCourierCanBeRegistered();
//		GUIStartFrameTest.checkIfCustomerCanBeRegistered();
		
		//Log-in Tests - please run only one test at a time - if not they will fail
//		GUIStartFrameTest.checkIfCourierLogInWorks();
//		GUIStartFrameTest.checkIfCourierLogInFailsWithWrongLogIn();
//		GUIStartFrameTest.checkIfRestaurantLogInWorks();
//		GUIStartFrameTest.checkIfManagerLogInWorks();
//		GUIStartFrameTest.checkIfCustomerLogInWorks();
	}

	

	

}
