package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
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
import users.Courier;
import users.Customer;
import users.Restaurant;
import users.User;

public class GUICourierFrame extends GUIUserFrame {
	
	private GUICourierFrame instance;
	private Courier courier;
	
	public GUICourierFrame() {
		super();
		instance = this;
	}
	
	/*************************************************/
	//Init functions
	
	@Override
	public GUIUserFrame getInstance(User user) {
		
		if(user instanceof Courier){
			
			GUIStartFrame.getFrame().setVisible(false);
			this.courier = (Courier) user;
			fillAndSetMenuBarCourier(user);
			initGUI(courier, Color.orange, Color.yellow, "Courier Area", "Just Dwaggit...");
			instance.open(0, 0, 600, 400);
			return instance;
		}
			
		return null;
	}
	
	private void fillInfoMenuWithFunctionCourier(User user) {
		getInfoMenu().add(new UserActionInfoBasicCour("address", "show current address", user));
		getInfoMenu().add(new UserActionInfoBasicCour("surname", "show current surname", user));
		getInfoMenu().add(new UserActionInfoBasicCour("phoneNumb", "show current phone number", user));
	}
	
	private void fillSetMenuWithFunctionCourier(User user) {
		getSettingMenu().add(new UserActionSettingBasicCour("address", "change current address", user));
		getSettingMenu().add(new UserActionSettingBasicCour("surname", "change current surname", user));
		getSettingMenu().add(new UserActionSettingBasicCour("phoneNumb", "change current phone number", user));
	}
	
	public void fillAndSetMenuBarCourier(User user){
		fillInfoMenuWithFunctionCourier(user);
		fillSetMenuWithFunctionCourier(user);
	}
	
	private class UserActionInfoBasicCour extends AbstractAction {

		String choice;
		User user;

		public UserActionInfoBasicCour(String choice, String desc, User user) {
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
        }
			fillInfoPanel(descr,value);
			setCurrentPanel(getInfoPanel());
		}
	}
	
	
	private class UserActionSettingBasicCour extends AbstractAction {

		String choice;
		User user;

		public UserActionSettingBasicCour(String choice, String desc, User user) {
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
          
        }
			fillSetPanel(descr,value);
			setCurrentPanel(getSettingPanel());
		}
	}
	
}