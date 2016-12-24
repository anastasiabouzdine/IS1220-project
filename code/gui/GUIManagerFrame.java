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
			 fillAndSetMenuBarManager(manager);
			initGUI(manager, Color.orange, Color.yellow, "Manager Area", "Just Dwaggit...");
			instance.open(0, 0, 600, 400);
			return instance;
		}
			
		return null;
	}
	
	private void fillInfoMenuWithFunctionManager(Manager manager) {
		getInfoMenu().add(new managerActionInfoBasicManager("surname", "show current surname", manager));
	}

	private void fillSetMenuWithFunctionManager(Manager manager) {
		getSettingMenu().add(new managerActionSettingBasicManager("surname", "change current surname", manager));
	}

	public void fillAndSetMenuBarManager(Manager manager) {
		fillInfoMenuWithFunctionManager(manager);
		fillSetMenuWithFunctionManager(manager);
	}

	private class managerActionInfoBasicManager extends AbstractAction {

		String choice;
		Manager manager;

		public managerActionInfoBasicManager(String choice, String desc, Manager manager) {
			super(choice);
			this.choice = choice;
			this.manager = manager;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			String descr = null;
			String value = null;

			switch (choice) {
			case "surname":
				descr = "Your surname: ";
				value = manager.getSurname();
				break;
			}
			fillInfoPanel(descr, value);
			setCurrentPanel(getInfoPanel());
		}
	}

	private class managerActionSettingBasicManager extends AbstractAction {

		String choice;
		Manager manager;

		public managerActionSettingBasicManager(String choice, String desc, Manager manager) {
			super(choice);
			this.choice = choice;
			this.manager = manager;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			String descr = null;
			String value = null;

			switch (choice) {

			case "surname":
				descr = "Set your new surname: ";
				value = manager.getSurname();
				setCurrentSettingShow(4);
				break;

			}
			fillSetPanel(descr, value);
			setCurrentPanel(getSettingPanel());
		}
	}
}