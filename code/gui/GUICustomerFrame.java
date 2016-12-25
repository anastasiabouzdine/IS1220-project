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
			fillAndSetMenuBarCustomer(customer);
			initGUI(customer, Color.orange, Color.yellow, "Customer Area", "Just Dwaggit...");
			instance.open(0, 0, 600, 400);
			return instance;
		}
			
		return null;
	}
	
	private void fillInfoMenuWithFunctionCustomer(Customer customer) {
		getInfoMenu().add(new customerActionInfoBasicCust("address", "show current address", customer));
		getInfoMenu().add(new customerActionInfoBasicCust("surname", "show current surname", customer));
		getInfoMenu().add(new customerActionInfoBasicCust("phoneNumb", "show current phone number", customer));
		getInfoMenu().add(new customerActionInfoBasicCust("emailAddress", "show current email address", customer));
	}
	
	private void fillSetMenuWithFunctionCustomer(Customer customer) {
		getSettingMenu().add(new customerActionSettingBasicCust("address", "change current address", customer));
		getSettingMenu().add(new customerActionSettingBasicCust("surname", "change current surname", customer));
		getSettingMenu().add(new customerActionSettingBasicCust("phoneNumb", "change current phone number", customer));
		getSettingMenu().add(new customerActionSettingBasicCust("emailAddress", "change current email address", customer));
	}
	
	public void fillAndSetMenuBarCustomer(Customer customer){
		fillInfoMenuWithFunctionCustomer(customer);
		fillSetMenuWithFunctionCustomer(customer);
	}
	
	private class customerActionInfoBasicCust extends AbstractAction {

		String choice;
		Customer customer;

		public customerActionInfoBasicCust(String choice, String desc, Customer customer) {
			super(choice);
			this.choice = choice;
			this.customer = customer;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e){
			
			
			
			String descr = null;
			String value = null;
			
			switch (choice) {
            case "surname":
            	descr = "Your surname: ";
            	value = customer.getSurname();
                break;
            case "address":
            	descr = "Your address: ";
            	value = customer.getAddress().toString();
                break;
            case "phoneNumb":
            	descr = "Your phone number: ";
            	value = customer.getPhoneNumber();
                break;
            case "emailAddress":
            	descr = "Your email address: ";
            	value = customer.getEmail();
                break;
                
        }
			fillInfoPanel(descr,value);
			setCurrentPanel(getInfoPanel());
		}
	}
	
	
	
	private class customerActionSettingBasicCust extends AbstractAction {

		String choice;
		Customer customer;

		public customerActionSettingBasicCust(String choice, String desc, Customer customer) {
			super(choice);
			this.choice = choice;
			this.customer = customer;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e){
			
			String descr = null;
			String value = null;
			
			switch (choice) {
            case "surname":
            	descr = "Set your new surname: ";
            	value = customer.getSurname();
            	setCurrentSettingShow(4);
                break;
            case "address":
            	descr = "Set your new address in the format \"xCoord,yCoord\": ";
            	value = customer.getAddress().toString();
            	setCurrentSettingShow(5);
                break;
            case "phoneNumb":
            	descr = "Set your new phone number: ";
            	value = customer.getPhoneNumber();
            	setCurrentSettingShow(6);
                break;
            case "emailAddress":
            	descr = "Set your new email address: ";
            	value = customer.getEmail();
            	setCurrentSettingShow(7);
                break;
        }
			fillSetPanel(descr,value);
			setCurrentPanel(getSettingPanel());
		}
	}
	
}