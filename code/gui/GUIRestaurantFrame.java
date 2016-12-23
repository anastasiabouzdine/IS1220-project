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
import gui.GUIUserFrame.UserActionInfoBasic;
import gui.GUIUserFrame.UserActionSettingBasic;
import users.Restaurant;
import users.User;

public class GUIRestaurantFrame extends GUIUserFrame {
	
	private GUIRestaurantFrame instance;
	private Restaurant restaurant;
	
	public GUIRestaurantFrame() {
		super();
		instance = this;
	}
	
	/*************************************************/
	//Init functions
	
	@Override
	public GUIUserFrame getInstance(User user) {
		
		if(user instanceof Restaurant){
			
			GUIStartFrame.getFrame().setVisible(false);
			this.restaurant = (Restaurant) user;
			fillAndSetMenuBarRest(user);
			initGUIRest(restaurant, Color.orange, Color.yellow, "Restaurant Area", "Just Dwaggit...");
			instance.open(0, 0, 600, 400);
			return instance;
		}
			
		return null;
	}
	
	
	private void fillInfoMenuWithFunctionRest(User user) {
		getInfoMenu().add(new UserActionInfoBasic("address", "show current address", user));
	}
	
	private void fillSetMenuWithFunctionRest(User user) {
		getSettingMenu().add(new UserActionSettingBasic("address", "change current address", user));
	}
	
	public void fillAndSetMenuBarRest(User user){
		fillInfoMenuWithFunctionRest(user);
		fillSetMenuWithFunctionRest(user);
	}
	
	@SuppressWarnings("serial")
	private class UserActionInfoBasicRest extends UserActionInfoBasic {

		public UserActionInfoBasicRest(String choice, String desc, User user) {
			super(choice, desc, user);
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e){
			
			String descr = null;
			String value = null;
			
			switch (choice) {
            case "menus" :
            	String descr1 = "Starters :"; 
            	String descr2 = "Main dishes :"; 
            	String descr3 = "Desserts :"; 
            	
                break;
            
                
        }
			fillInfoPanel(descr,value);
			setCurrentPanel(getInfoPanel());
		}
	}
	
}

	
