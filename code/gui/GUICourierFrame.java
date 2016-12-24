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
			fillAndSetMenuBarCourier(courier);
			initGUI(courier, Color.orange, Color.yellow, "Courier Area", "Just Dwaggit...");
			instance.open(0, 0, 600, 400);
			return instance;
		}
			
		return null;
	}
	
	private void fillInfoMenuWithFunctionCourier(Courier courier) {
		getInfoMenu().add(new courierActionInfoBasicCour("address", "show current address", courier));
		getInfoMenu().add(new courierActionInfoBasicCour("surname", "show current surname", courier));
		getInfoMenu().add(new courierActionInfoBasicCour("phoneNumb", "show current phone number", courier));
	}
	
	private void fillSetMenuWithFunctionCourier(Courier courier) {
		getSettingMenu().add(new courierActionSettingBasicCour("address", "change current address", courier));
		getSettingMenu().add(new courierActionSettingBasicCour("surname", "change current surname", courier));
		getSettingMenu().add(new courierActionSettingBasicCour("phoneNumb", "change current phone number", courier));
	}
	
	public void fillAndSetMenuBarCourier(Courier courier){
		fillInfoMenuWithFunctionCourier(courier);
		fillSetMenuWithFunctionCourier(courier);
	}
	
	private class courierActionInfoBasicCour extends AbstractAction {

		String choice;
		Courier courier;

		public courierActionInfoBasicCour(String choice, String desc, Courier courier) {
			super(choice);
			this.choice = choice;
			this.courier = courier;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e){
			
			String descr = null;
			String value = null;
			
			switch (choice) {
            case "surname":
            	descr = "Your surname: ";
            	value = courier.getSurname();
                break;
            case "address":
            	descr = "Your address: ";
            	value = courier.getAddress().toString();
                break;
            case "phoneNumb":
            	descr = "Your phone number: ";
            	value = courier.getPhoneNumber();
                break;  
        }
			fillInfoPanel(descr,value);
			setCurrentPanel(getInfoPanel());
		}
	}
	
	
	private class courierActionSettingBasicCour extends AbstractAction {

		String choice;
		Courier courier;

		public courierActionSettingBasicCour(String choice, String desc, Courier courier) {
			super(choice);
			this.choice = choice;
			this.courier = courier;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e){
			
			String descr = null;
			String value = null;
			
			switch (choice) {
            
            case "surname":
            	descr = "Set your new surname: ";
            	value = courier.getSurname();
            	setCurrentSettingShow(4);
                break;
            case "address":
            	descr = "Set your new address in the format \"xCoord,yCoord\": ";
            	value = courier.getAddress().toString();
            	setCurrentSettingShow(5);
                break;
            case "phoneNumb":
            	descr = "Set your new phone number: ";
            	value = courier.getPhoneNumber();
            	setCurrentSettingShow(6);
                break;
          
        }
			fillSetPanel(descr,value);
			setCurrentPanel(getSettingPanel());
		}
	}
	
}