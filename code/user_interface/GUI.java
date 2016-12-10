package user_interface;

import java.awt.BorderLayout;
import java.awt.Color;
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


public class GUI {
	private JFrame frame = new JFrame("My Foodora");
	JPanel welcome_panel = new JPanel();
	JPanel welcome_button_panel = new JPanel();
	JPanel welcome_message_panel = new JPanel();
	JPanel login_panel = new JPanel();
	
	JLabel label1 = new JLabel("Hello World !");

	// home button
	JButton home_button = new JButton("GO TO HOME");
	
	// radio buttons for user to register
	JRadioButton radio_customer = new JRadioButton("Customer", true);
	JRadioButton radio_courier = new JRadioButton("Courier");
	JRadioButton radio_restaurant = new JRadioButton("Restaurant");
	ButtonGroup user_type_group = new ButtonGroup();
	
	// panels when user register and add info
	JPanel register_panel = new JPanel();
	JPanel user_global_info = new JPanel();
	JPanel user_specific_info = new JPanel();
	JPanel customer_specific_info = new JPanel();
	JPanel courier_specific_info = new JPanel();
	JPanel restaurant_specific_info = new JPanel();


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
	
	public void fillUsersSpecificInfosPanel() {
		courier_specific_info.setBackground(Color.green);
		
		JTextField surname = new JTextField(15);
		surname.setText("Insert your surname");
		JTextField phoneNum = new JTextField(15);
		phoneNum.setText("Insert your phone number");
		courier_specific_info.add(surname);
		courier_specific_info.add(phoneNum);
		
	}
	public void fillLoginPanel() {
		login_panel.setBackground(Color.green);
		login_panel.setBorder(BorderFactory.createEmptyBorder(30, 150, 10, 150));
		
		JTextField username = new JTextField(20);
		username.setPreferredSize(new Dimension(150, 30));
		username.setForeground(Color.BLUE);
		username.setText("Insert your username");

		JTextField password = new JTextField(20);
		password.setPreferredSize(new Dimension(150, 30));
		password.setForeground(Color.BLUE);
		password.setText("Insert your password");
		
		
		JButton button_login = new JButton("LOG IN");
		
		login_panel.add(username, BorderLayout.NORTH);
		login_panel.add(password, BorderLayout.CENTER);
		login_panel.add(button_login, BorderLayout.CENTER);
		
	}
	
	public void fillRegisterPanel() {
		register_panel.setBackground(Color.red);
		register_panel.setBorder(BorderFactory.createEmptyBorder(30, 150, 10, 150));
		
		user_type_group.add(radio_restaurant);
		user_type_group.add(radio_courier);
		user_type_group.add(radio_customer);
		JPanel user_type = new JPanel();
		user_type.setBackground(Color.red);
		user_type.add(radio_customer);
		user_type.add(radio_courier);
		user_type.add(radio_restaurant);
		
		JTextField username = new JTextField(15);
		username.setForeground(Color.BLUE);
		username.setText("Insert a username");

		JTextField password = new JTextField(15);
		password.setForeground(Color.BLUE);
		password.setText("Insert a password");
		
		JButton button_register = new JButton("CREATE ACCOUNT");
		button_register.addActionListener(new WhatAccountType());
		
		register_panel.add(user_type, BorderLayout.NORTH);
		register_panel.add(username, BorderLayout.NORTH);
		register_panel.add(password, BorderLayout.CENTER);
		register_panel.add(button_register, BorderLayout.CENTER);

	}
	
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
			setCurrentPanel(user_global_info);
		}
	}
	
	public void fillWelcomePanel() {
		JButton button_login = new JButton("LOG IN");
		JButton button_register = new JButton("REGISTER");

		welcome_panel.setBackground(Color.orange);
		welcome_panel.setBorder(BorderFactory.createTitledBorder("Welcome"));
		welcome_panel.setLayout(new BorderLayout());

		welcome_button_panel.setBackground(Color.orange);
		welcome_button_panel.add(button_login, BorderLayout.CENTER);
		welcome_button_panel.add(button_register, BorderLayout.CENTER);
	    	
		button_login.addActionListener(new LoginButton());
		button_register.addActionListener(new RegisterButton());
		
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

//		welcome_panel.add(button_login, BorderLayout.WEST);
//		welcome_panel.add(button_register, BorderLayout.EAST);
    	
   	}
	
	/*********************************************************/
	/* Functions */
	
	public void setCurrentPanel(JPanel panel) {
    	frame.setContentPane(panel);  	
    	frame.setVisible(true);
	}
	
	public void initSettings(){
        home_button.addActionListener(new HomeButton());
        
        /* Menu bar */
    	JMenuBar menubar = new JMenuBar();
    	JMenu menu1 = new JMenu("Menu 42");
    	JMenuItem item1 = new JMenuItem("Item66");
    	menu1.add(item1);
    	menubar.add(menu1);
    	frame.setJMenuBar(menubar);
        
        fillWelcomePanel();
        fillRegisterPanel();
        fillLoginPanel();
        
        fillUserGlobalInfoPanel();
        fillUsersSpecificInfosPanel();
        
        setCurrentPanel(welcome_panel);
        
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	
	private class LoginButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			login_panel.add(home_button, BorderLayout.SOUTH);
			setCurrentPanel(login_panel);
		}
	}
	
	private class RegisterButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			register_panel.add(home_button, BorderLayout.SOUTH);
			setCurrentPanel(register_panel);
		}
	}
	
	private class HomeButton implements ActionListener {
		public void actionPerformed(ActionEvent e){
			setCurrentPanel(welcome_panel);
		}
	}

	public static void main(String[] args) {
		GUI GUI = new GUI();
		GUI.open(0, 0, 600, 400);
	}
	
   

}
