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
			initGUIRest(manager, Color.orange, Color.yellow, "Manager Area", "Just Dwaggit...");
			instance.open(0, 0, 600, 400);
			return instance;
		}
			
		return null;
	}
	
	private void fillInfoMenuWithFunctionManager(User user) {
		getInfoMenu().add(new UserActionInfoBasic("surname", "show current surname", user));
	}
	
	private void fillSetMenuWithFunctionManager(User user) {
		getSettingMenu().add(new UserActionSettingBasic("surname", "change current surname", user));
	}
	
	public void fillAndSetMenuBarManager(User user){
		fillInfoMenuWithFunctionManager(user);
		fillSetMenuWithFunctionManager(user);
	}
}