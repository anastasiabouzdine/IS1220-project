package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
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
			initGUIRest(courier, Color.orange, Color.yellow, "Courier Area", "Just Dwaggit...");
			instance.open(0, 0, 600, 400);
			return instance;
		}
			
		return null;
	}
	
	private void fillInfoMenuWithFunctionCourier(User user) {
		getInfoMenu().add(new UserActionInfoBasic("address", "show current address", user));
		getInfoMenu().add(new UserActionInfoBasic("surname", "show current surname", user));
		getInfoMenu().add(new UserActionInfoBasic("phoneNumb", "show current phone number", user));
	}
	
	private void fillSetMenuWithFunctionCourier(User user) {
		getSettingMenu().add(new UserActionSettingBasic("address", "change current address", user));
		getSettingMenu().add(new UserActionSettingBasic("surname", "change current surname", user));
		getSettingMenu().add(new UserActionSettingBasic("phoneNumb", "change current phone number", user));
	}
	
	public void fillAndSetMenuBarCourier(User user){
		fillInfoMenuWithFunctionCourier(user);
		fillSetMenuWithFunctionCourier(user);
	}
	
}