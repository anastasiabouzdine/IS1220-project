package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

import user_interface.GUI;

public class GUIStartFrame {
	
	private static GUIStartFrame instance;

	private JFrame frame = new JFrame("My Foodora");
	
	JPanel welcome_panel = new JPanel();
	JPanel welcome_button_panel = new JPanel();
	JPanel welcome_message_panel = new JPanel();
	
	JPanel address_panel = new JPanel();
	JPanel login_panel = new JPanel();
	
	// radio buttons for user to register
	JRadioButton radio_customer = new JRadioButton("Customer", true);
	JRadioButton radio_courier = new JRadioButton("Courier");
	JRadioButton radio_restaurant = new JRadioButton("Restaurant");
	JRadioButton radio_manager = new JRadioButton("Manager");
	ButtonGroup user_type_group = new ButtonGroup();
	
	// buttons 
	
	JButton logIn_button = new JButton("LOG IN");
	JButton	home_button = new JButton("GO TO HOME");
	JButton goToRegister_button = new JButton("GO TO REGISTER");
	JButton backToRegister_button = new JButton("GO BACK");
	JButton createAccount_button = new JButton("CREATE ACCOUNT");
	JButton goToLogIn_button = new JButton("GO TO LOG IN");
	
	


	
	// panels when user register and add info
	JPanel register_panel_info = new JPanel();
	
	JPanel user_global_info = new JPanel();
	JPanel user_specific_info = new JPanel();
	
	JPanel customer_specific_info = new JPanel();
	JPanel courier_specific_info = new JPanel();
	JPanel restaurant_specific_info = new JPanel();
	JPanel manager_specific_info = new JPanel();
	
	/*********************************************************/
	// HelpFunctions
	
	private static GUIStartFrame getInstance() {
		if(instance == null){
			instance = new GUIStartFrame();
		}
		return instance;
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
		
		JTextField name = new JTextField(15);
		name.setPreferredSize(new Dimension(150, 30));
		name.setForeground(Color.BLUE);
		name.setText("Insert your name");
		
		user_specific_info.setBackground(Color.green);
		
	    user_global_info.add(ask_info, BorderLayout.NORTH);
		user_global_info.add(name, BorderLayout.NORTH);
	    user_global_info.add(user_specific_info, BorderLayout.CENTER);
	}
	
	private void fillAddressInfosPanel() {
		
		JTextField xCoordinate = new JTextField(15);
		xCoordinate.setText("Insert xCoordinate");
		JTextField yCoordinate = new JTextField(15);
		yCoordinate.setText("Insert yCoordinate");
		
		address_panel.add(xCoordinate);
		address_panel.add(yCoordinate);
	}
	
	public void fillCourierSpecificInfosPanel() {
		courier_specific_info.setLayout(new BorderLayout());
		courier_specific_info.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
		courier_specific_info.setBackground(Color.green);
		
		JTextField surname = new JTextField(15);
		surname.setText("Insert your surname");
		JTextField phoneNum = new JTextField(15);
		phoneNum.setText("Insert your phone number");
		
		courier_specific_info.add(surname,BorderLayout.NORTH);
		courier_specific_info.add(phoneNum,BorderLayout.NORTH);
		courier_specific_info.add(address_panel, BorderLayout.SOUTH);
	}
	
	public void fillManagerSpecificInfosPanel() {
		manager_specific_info.setLayout(new BorderLayout());
		manager_specific_info.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
		manager_specific_info.setBackground(Color.green);
		
		JTextField surname = new JTextField(15);
		surname.setText("Insert your surname");
		JTextField phoneNum = new JTextField(15);
		phoneNum.setText("Insert your phone number");
		
		manager_specific_info.add(surname,BorderLayout.NORTH);
		manager_specific_info.add(phoneNum,BorderLayout.NORTH);
		manager_specific_info.add(address_panel, BorderLayout.SOUTH);
	}
	
	public void fillCustomerSpecificInfosPanel() {
		customer_specific_info.setLayout(new BorderLayout());
		customer_specific_info.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
		customer_specific_info.setBackground(Color.green);
		
		JTextField surname = new JTextField(15);
		surname.setText("Insert your surname");
		JTextField phoneNum = new JTextField(15);
		phoneNum.setText("Insert your phone number");
		JTextField emailAddress = new JTextField(15);
		phoneNum.setText("Insert your Email address");
		
		customer_specific_info.add(surname,BorderLayout.NORTH);
		customer_specific_info.add(phoneNum,BorderLayout.NORTH);
		customer_specific_info.add(emailAddress,BorderLayout.NORTH);
		customer_specific_info.add(address_panel, BorderLayout.SOUTH);
	}
	
	public void fillRestaurantSpecificInfosPanel() {
		restaurant_specific_info.setLayout(new BorderLayout());
		restaurant_specific_info.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
		restaurant_specific_info.setBackground(Color.green);
		
		restaurant_specific_info.add(address_panel, BorderLayout.SOUTH);
	}
	
	public void fillLoginPanel() {
		login_panel.setBackground(Color.green);
		login_panel.setBorder(BorderFactory.createEmptyBorder(30, 150, 10, 150));
		
		JTextField username = new JTextField(20);
		username.setPreferredSize(new Dimension(150, 30));
		username.setForeground(Color.BLUE);
		username.setText("Insert your username");
		//TODO find a function that deletes the text when clicked on 

		JTextField password = new JTextField(20);
		password.setPreferredSize(new Dimension(150, 30));
		password.setForeground(Color.BLUE);
		password.setText("Insert your password");
		
		
		login_panel.add(username, BorderLayout.NORTH);
		login_panel.add(password, BorderLayout.CENTER);
		
		
	}
	
	public void fillRegisterPanel() {
		register_panel_info.setBackground(Color.red);
		register_panel_info.setBorder(BorderFactory.createEmptyBorder(30, 150, 10, 150));
		
		user_type_group.add(radio_restaurant);
		user_type_group.add(radio_courier);
		user_type_group.add(radio_customer);
		user_type_group.add(radio_manager);
		
		JPanel user_type = new JPanel();
		user_type.setBackground(Color.red);
		user_type.add(radio_customer);
		user_type.add(radio_courier);
		user_type.add(radio_restaurant);
		user_type.add(radio_manager);
		
		JTextField username = new JTextField(15);
		username.setForeground(Color.BLUE);
		username.setText("Insert a username");

		JTextField password = new JTextField(15);
		password.setForeground(Color.BLUE);
		password.setText("Insert a password");
		
		register_panel_info.add(user_type, BorderLayout.NORTH);
		register_panel_info.add(username, BorderLayout.NORTH);
		register_panel_info.add(password, BorderLayout.CENTER);
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
        fillCourierSpecificInfosPanel();
        fillAddressInfosPanel();
        
        setCurrentPanel(welcome_panel);
        
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	
    	goToLogIn_button.addActionListener(new GoToLoginButton());
    	home_button.addActionListener(new HomeButton());
    	goToRegister_button.addActionListener(new GoToRegisterButton());
    	backToRegister_button.addActionListener(new GoBackToRegisterButton());
    	createAccount_button.addActionListener(new WhatAccountType());
    	
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
	
	/*********************************************************/
	/* ActionListeners */
	
	private class WhatAccountType implements ActionListener{
		public void actionPerformed(ActionEvent e){
			user_specific_info.removeAll();
			if (radio_customer.isSelected()){
				user_specific_info.add(customer_specific_info);
			} else if (radio_courier.isSelected()){
				user_specific_info.add(courier_specific_info);
			} else if (radio_restaurant.isSelected()){
				user_specific_info.add(restaurant_specific_info);
			}
			user_global_info.add(home_button, BorderLayout.SOUTH);
			user_global_info.add(backToRegister_button, BorderLayout.SOUTH);
			setCurrentPanel(user_global_info);
		}
	}
	
	private class GoToLoginButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			login_panel.add(home_button, BorderLayout.SOUTH);
			login_panel.add(logIn_button, BorderLayout.SOUTH);
			setCurrentPanel(login_panel);
		}
	}
	
	private class GoToRegisterButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			register_panel_info.add(home_button, BorderLayout.SOUTH);
			register_panel_info.add(createAccount_button, BorderLayout.SOUTH);
			setCurrentPanel(register_panel_info);
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
			setCurrentPanel(welcome_panel);
		}
	}

	
	/*********************************************************/
	/* Launch */
	public static void main(String[] args) {
		GUIStartFrame gui = GUIStartFrame.getInstance();
		gui.open(0, 0, 600, 400);
	}

	

}
