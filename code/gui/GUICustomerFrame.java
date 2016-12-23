package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
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
			initGUI(customer, Color.orange, Color.yellow, "Customer Area", "Just Dwaggit...");
			instance.open(0, 0, 600, 400);
			return instance;
		}
			
		return null;
	}
	
	private void fillInfoMenuWithFunctionCustomer(User user) {
		getInfoMenu().add(new UserActionInfoBasicCust("address", "show current address", user));
		getInfoMenu().add(new UserActionInfoBasicCust("surname", "show current surname", user));
		getInfoMenu().add(new UserActionInfoBasicCust("phoneNumb", "show current phone number", user));
		getInfoMenu().add(new UserActionInfoBasicCust("emailAddress", "show current email address", user));
	}
	
	private void fillSetMenuWithFunctionCustomer(User user) {
		getSettingMenu().add(new UserActionSettingBasicCust("address", "change current address", user));
		getSettingMenu().add(new UserActionSettingBasicCust("surname", "change current surname", user));
		getSettingMenu().add(new UserActionSettingBasicCust("phoneNumb", "change current phone number", user));
		getSettingMenu().add(new UserActionSettingBasicCust("emailAddress", "change current email address", user));
	}
	
	public void fillAndSetMenuBarCustomer(User user){
		fillInfoMenuWithFunctionCustomer(user);
		fillSetMenuWithFunctionCustomer(user);
	}
	
	private class UserActionInfoBasicCust extends AbstractAction {

		String choice;
		User user;

		public UserActionInfoBasicCust(String choice, String desc, User user) {
			super(choice);
			this.choice = choice;
			this.user = user;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e){
			
			String descr = null;
			String value = null;
			
			switch (choice) {
            case "surname":
            	descr = "Your surname: ";
            	value = user.getSurname();
                break;
            case "address":
            	descr = "Your address: ";
            	value = user.getAddress().toString();
                break;
            case "phoneNumb":
            	descr = "Your phone number: ";
            	value = user.getPhoneNumb();
                break;
            case "emailAddress":
            	descr = "Your email address: ";
            	value = user.getEmailAddress();
                break;
                
        }
			fillInfoPanel(descr,value);
			setCurrentPanel(getInfoPanel());
		}
	}
	
	
	
	private class UserActionSettingBasicCust extends AbstractAction {

		String choice;
		User user;

		public UserActionSettingBasicCust(String choice, String desc, User user) {
			super(choice);
			this.choice = choice;
			this.user = user;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e){
			
			String descr = null;
			String value = null;
			
			switch (choice) {
            case "surname":
            	descr = "Set your new surname: ";
            	value = user.getSurname();
            	setCurrentSettingShow(4);
                break;
            case "address":
            	descr = "Set your new address in the format \"xCoord,yCoord\": ";
            	value = user.getAddress().toString();
            	setCurrentSettingShow(5);
                break;
            case "phoneNumb":
            	descr = "Set your new phone number: ";
            	value = user.getPhoneNumb();
            	setCurrentSettingShow(6);
                break;
            case "emailAddress":
            	descr = "Set your new email address: ";
            	value = user.getEmailAddress();
            	setCurrentSettingShow(7);
                break;
        }
			fillSetPanel(descr,value);
			setCurrentPanel(getSettingPanel());
		}
	}
	
}