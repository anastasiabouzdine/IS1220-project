package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import core.Core;
import gui.GUIUserFrame.UserActionInfoBasic;
import gui.GUIUserFrame.UserActionSettingBasic;
import users.Customer;
import users.Restaurant;
import users.User;

public class GUICustomerFrame extends GUIUserFrame {
	
	private GUICustomerFrame instance;
	private Customer customer;
	
	public GUICustomerFrame() {
		super();
		instance = this;
	}
	
	/*************************************************/
	//Init functions
	
	@Override
	public GUIUserFrame getInstance(User user) {
		
		if(user instanceof Customer){
			
			GUIStartFrame.getFrame().setVisible(false);
			this.customer = (Customer) user;
			fillAndSetMenuBarCustomer(user);
			initGUIRest(customer, Color.orange, Color.yellow, "Customer Area", "Just Dwaggit...");
			instance.open(0, 0, 600, 400);
			return instance;
		}
			
		return null;
	}
	
	private void fillInfoMenuWithFunctionCustomer(User user) {
		getInfoMenu().add(new UserActionInfoBasic("address", "show current address", user));
		getInfoMenu().add(new UserActionInfoBasic("surname", "show current surname", user));
		getInfoMenu().add(new UserActionInfoBasic("phoneNumb", "show current phone number", user));
		getInfoMenu().add(new UserActionInfoBasic("emailAddress", "show current email address", user));
	}
	
	private void fillSetMenuWithFunctionCustomer(User user) {
		getSettingMenu().add(new UserActionSettingBasic("address", "change current address", user));
		getSettingMenu().add(new UserActionSettingBasic("surname", "change current surname", user));
		getSettingMenu().add(new UserActionSettingBasic("phoneNumb", "change current phone number", user));
		getSettingMenu().add(new UserActionSettingBasic("emailAddress", "change current email address", user));
	}
	
	public void fillAndSetMenuBarCustomer(User user){
		fillInfoMenuWithFunctionCustomer(user);
		fillSetMenuWithFunctionCustomer(user);
	}
	
}