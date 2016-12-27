package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.junit.experimental.theories.Theories;

import core.Core;
import users.Manager;
import users.Address;
import users.Courier;
import users.Customer;
import users.Restaurant;
import users.User;

public class GUIManagerFrame extends GUIUserFrame {
	
	private GUIManagerFrame instance;
	private Manager manager;
	
	private JPanel userManagePanel = new JPanel();
	private JPanel profitRelatedPanel = new JPanel();
	
	//JMenu
	private JMenu userManageMenu = new JMenu("Manage Users");
	private JMenu profitMenu = new JMenu("Check Profits");
	private Core core = GUIStartFrame.getCore();
	
	//Button
	private Button goBackfromAddUserButton = new Button("GO BACK");
	
	public GUIManagerFrame() {
		super();
		instance = this;
	}
	
	/*************************************************/
	//Init functions
	
	@Override
	public GUIUserFrame getInstance(User user) {
		
		if(user instanceof Manager){
			this.manager = (Manager) user;
			
			initManager(manager);
			GUIStartFrame.getFrame().setVisible(false);
			
			initGUI(manager, Color.orange, Color.yellow, "Manager Area", "Just Dwaggit...");
			instance.open(0, 0, 600, 400);
			return instance;
		}
			
		return null;
	}
	
	private void initManager(Manager manager){
		fillAndSetMenuBarManagerInit(manager);
		fillUserManagePanelInit();
		fillProfitRelatedPanelInit();
		
		goBackfromAddUserButton.addActionListener((ActionEvent e) -> {
			setCurrentPanel(welcome_panel);
			GUIStartFrame.register_panel_info.remove(goBackfromAddUserButton);
		});
	}
	
	private void fillInfoMenuWithFunctionManagerInit(Manager manager) {
		getInfoMenu().add(new ManagerActionInfoBasicManager("surname", "show current surname", manager));
	}

	private void fillSetMenuWithFunctionManagerInit(Manager manager) {
		getSettingMenu().add(new ManagerActionSettingBasicManager("surname", "change current surname", manager));
	}
	
	private void fillUserManagMenuWithFunctionManagerInit(Manager manager){
		userManageMenu.add(new ManagerActionUserManagement("add", "adding a new user", manager));
	}

	private void fillAndSetMenuBarManagerInit(Manager manager) {
		fillInfoMenuWithFunctionManagerInit(manager);
		fillSetMenuWithFunctionManagerInit(manager);
		fillUserManagMenuWithFunctionManagerInit(manager);
		fillUserManagePanelInit();
		fillProfitRelatedPanelInit();
		getMenuBar().add(userManageMenu);
		getMenuBar().add(profitMenu);
	}
	
	private void fillUserManagePanelInit() {
		userManagePanel.setBorder(BorderFactory.createTitledBorder("User management"));
		userManagePanel.setLayout(new BorderLayout());
		userManagePanel.setBackground(Color.LIGHT_GRAY);
	}
	
	private void fillProfitRelatedPanelInit() {
		profitRelatedPanel.setBorder(BorderFactory.createTitledBorder("Revenue and profit"));
		profitRelatedPanel.setLayout(new BorderLayout());
		profitRelatedPanel.setBackground(Color.orange);
	}

	private class ManagerActionInfoBasicManager extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String choice;
		Manager manager;

		public ManagerActionInfoBasicManager(String choice, String desc, Manager manager) {
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

	private class ManagerActionSettingBasicManager extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String choice;
		Manager manager;

		public ManagerActionSettingBasicManager(String choice, String desc, Manager manager) {
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

				save_button = new JButton("SAVE");
				save_button.addActionListener((ActionEvent e3) -> {

					String surname = getSetTextFieldValue().getText();
					manager.setSurname(surname);
				});

				fillSetPanel(descr, value);
				setCurrentPanel(getSettingPanel());
				break;
			}
		}
	}
	
	private class ManagerActionUserManagement extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String choice;
		Manager manager;

		public ManagerActionUserManagement(String choice, String desc, Manager manager) {
			super(choice);
			this.choice = choice;
			this.manager = manager;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e) {


			switch (choice) {

			case "add":
				GUIStartFrame.manager = instance;
				GUIStartFrame.radio_manager.setVisible(true);
				GUIStartFrame.home_button.setVisible(false);	
				GUIStartFrame.user_global_info.add(GUIStartFrame.addUserButton);
				GUIStartFrame.register_panel_info.add(goBackfromAddUserButton);
				GUIStartFrame.getInstance().goToRegisterPanel();
				
				
				
				break;
				
			case "remove":
				
				
				
				break;
			case "activate":
				
				
				
				break;
				
			case "deactivate":
				
				
				
				break;
			}
		}
	}

	/**
	 * @return the instance
	 */
	public GUIManagerFrame getInstance() {
		return this.instance;
	}

	/**
	 * @return the goBackfromAddUserButton
	 */
	public Button getGoBackfromAddUserButton() {
		return goBackfromAddUserButton;
	}
	
}
