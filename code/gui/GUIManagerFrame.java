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
import users.Manager;
import users.Customer;
import users.Restaurant;
import users.User;

public class GUIManagerFrame extends GUIUserFrame {
	
	private GUIManagerFrame instance;
	private Manager manager;
	
	public GUIManagerFrame() {
		super();
		instance = this;
	}
	
	/*************************************************/
	//Init functions
	
	@Override
	public GUIUserFrame getInstance(User user) {
		
		if(user instanceof Manager){
			
			GUIStartFrame.getFrame().setVisible(false);
			this.manager = (Manager) user;
			 fillAndSetMenuBarManager(user);
			initGUI(manager, Color.orange, Color.yellow, "Manager Area", "Just Dwaggit...");
			instance.open(0, 0, 600, 400);
			return instance;
		}
			
		return null;
	}
	
	private void fillInfoMenuWithFunctionManager(User user) {
		getInfoMenu().add(new UserActionInfoBasicManager("surname", "show current surname", user));
	}
	
	private void fillSetMenuWithFunctionManager(User user) {
		getSettingMenu().add(new UserActionSettingBasicManager("surname", "change current surname", user));
	}
	
	public void fillAndSetMenuBarManager(User user){
		fillInfoMenuWithFunctionManager(user);
		fillSetMenuWithFunctionManager(user);
	}
	
	private class UserActionInfoBasicManager extends AbstractAction {

		String choice;
		User user;

		public UserActionInfoBasicManager(String choice, String desc, User user) {
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
        }
			fillInfoPanel(descr,value);
			setCurrentPanel(getInfoPanel());
		}
	}
	
	private class UserActionSettingBasicManager extends AbstractAction {

		String choice;
		User user;

		public UserActionSettingBasicManager(String choice, String desc, User user) {
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
           
        }
			fillSetPanel(descr,value);
			setCurrentPanel(getSettingPanel());
		}
	}
}