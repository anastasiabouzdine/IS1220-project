package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.junit.experimental.theories.Theories;

import core.Core;
import restaurantSetUp.Meal;
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
	private Button removeDeActivateButton;
	
	private JList<Manager> jListManagerShow = new JList<>();
	private JList<Customer> jListCustomerShow = new JList<>();
	private JList<Courier> jListCourierShow = new JList<>();
	private JList<Restaurant> jListRestaurantShow = new JList<>();
	private JScrollPane jScrollManager;
	private JScrollPane jScrollCustomer;
	private JScrollPane jScrollCourier;
	private JScrollPane jScrollRestaurant;
	private JPanel panManager = new JPanel();
	private JPanel panCustomer = new JPanel();
	private JPanel panCourier = new JPanel();
	private JPanel panRestaurant = new JPanel();
	private JPanel userPanel = new JPanel();
	
	public GUIManagerFrame() {
		super();
		instance = this;
	}
	
	/*************************************************/
	//fill functions
	
	public void fillUserManagePanelRemove(String string) {
		if(string.equals("remove")){
			fillPanelRemoveUsers();
			removeDeActivateButton = new Button("REMOVE");
			
			removeDeActivateButton.addActionListener((ActionEvent e) -> {
				for(Manager user : jListManagerShow.getSelectedValuesList()){
					core.removeUser(user);
				}
				for(Customer user : jListCustomerShow.getSelectedValuesList()){
					core.removeUser(user);
				}
				for(Courier user : jListCourierShow.getSelectedValuesList()){
					core.removeUser(user);
				}
				for(Restaurant user : jListRestaurantShow.getSelectedValuesList()){
					core.removeUser(user);
				}	
				setCurrentPanel(welcome_panel);
			});
			
		} else if(string.equals("deactivate")) {
			fillPanelDeactivateUsers();
			removeDeActivateButton = new Button("DEACTIVATE");
			
			removeDeActivateButton.addActionListener((ActionEvent e) -> {
				for(User user : jListManagerShow.getSelectedValuesList()){
					core.deactivateUser(user);
				}
				for(User user : jListCustomerShow.getSelectedValuesList()){
					core.deactivateUser(user);
				}
				for(User user : jListCourierShow.getSelectedValuesList()){
					core.deactivateUser(user);
				}
				for(User user : jListRestaurantShow.getSelectedValuesList()){
					core.deactivateUser(user);
				}	
				setCurrentPanel(welcome_panel);
			});
			
		} else if(string.equals("activate")) {
			fillPanelActivateUsers();
			removeDeActivateButton = new Button("ACTIVATE");
			
			removeDeActivateButton.addActionListener((ActionEvent e) -> {
				for(User user : jListManagerShow.getSelectedValuesList()){
					core.activateUser(user);
				}
				for(User user : jListCustomerShow.getSelectedValuesList()){
					core.activateUser(user);
				}
				for(User user : jListCourierShow.getSelectedValuesList()){
					core.activateUser(user);
				}
				for(User user : jListRestaurantShow.getSelectedValuesList()){
					core.activateUser(user);
				}	
				setCurrentPanel(welcome_panel);
			});
			
		}
		
		jScrollManager = new JScrollPane(jListManagerShow);
		jScrollCustomer = new JScrollPane(jListCustomerShow);
		jScrollCourier = new JScrollPane(jListCourierShow);
		jScrollRestaurant = new JScrollPane(jListRestaurantShow);
		
		jScrollManager.setPreferredSize(new Dimension(130, 240));
		jScrollCustomer.setPreferredSize(new Dimension(130, 240));
		jScrollCourier.setPreferredSize(new Dimension(130, 240));
		jScrollRestaurant.setPreferredSize(new Dimension(130, 240));
		
		panManager.removeAll();
		panCustomer.removeAll();
		panCourier.removeAll();
		panRestaurant.removeAll();
		
		panManager.add(jScrollManager);
		panCustomer.add(jScrollCustomer);
		panCourier.add(jScrollCourier);
		panRestaurant.add(jScrollRestaurant);

		userPanel.removeAll();
		userPanel.add(panManager,BorderLayout.WEST);
		userPanel.add(panCustomer,BorderLayout.CENTER);
		userPanel.add(panCourier,BorderLayout.CENTER);
		userPanel.add(panRestaurant,BorderLayout.EAST);

		userManagePanel.removeAll();
		userManagePanel.add(userPanel,BorderLayout.SOUTH);
		userManagePanel.add(removeDeActivateButton,BorderLayout.NORTH);
		setCurrentPanel(userManagePanel);
	}
	
	public void fillPanelRemoveUsers(){
		DefaultListModel<Manager> modelM = new DefaultListModel<Manager>();
		DefaultListModel<Customer> modelCu = new DefaultListModel<Customer>();
		DefaultListModel<Courier> modelCo = new DefaultListModel<Courier>();
		DefaultListModel<Restaurant> modelR = new DefaultListModel<Restaurant>();
		
		for (Manager user : core.getManagerList()) {
			modelM.addElement(user);
		}
		for (Customer user : core.getCustomerList()) {
			modelCu.addElement(user);
		}
		for (Courier user : core.getCourierList()) {
			modelCo.addElement(user);
		}
		for (Restaurant user : core.getRestaurantList()) {
			modelR.addElement(user);
		}
		
		jListManagerShow.setModel(modelM);
		jListCustomerShow.setModel(modelCu);
		jListCourierShow.setModel(modelCo);
		jListRestaurantShow.setModel(modelR);
	}
	
	public void fillPanelActivateUsers(){
		DefaultListModel<Manager> modelM = new DefaultListModel<Manager>();
		DefaultListModel<Customer> modelCu = new DefaultListModel<Customer>();
		DefaultListModel<Courier> modelCo = new DefaultListModel<Courier>();
		DefaultListModel<Restaurant> modelR = new DefaultListModel<Restaurant>();
		
		for (Manager user : core.getManagerList()) {
			if(!core.getUsers().containsValue(user))
				modelM.addElement(user);
		}
		for (Customer user : core.getCustomerList()) {
			if(!core.getUsers().containsValue(user))
				modelCu.addElement(user);
		}
		for (Courier user : core.getCourierList()) {
			if(!core.getUsers().containsValue(user))
				modelCo.addElement(user);
		}
		for (Restaurant user : core.getRestaurantList()) {
			if(!core.getUsers().containsValue(user))
				modelR.addElement(user);
		}
		
		jListManagerShow.setModel(modelM);
		jListCustomerShow.setModel(modelCu);
		jListCourierShow.setModel(modelCo);
		jListRestaurantShow.setModel(modelR);
	}
	
	public void fillPanelDeactivateUsers(){
		DefaultListModel<Manager> modelM = new DefaultListModel<Manager>();
		DefaultListModel<Customer> modelCu = new DefaultListModel<Customer>();
		DefaultListModel<Courier> modelCo = new DefaultListModel<Courier>();
		DefaultListModel<Restaurant> modelR = new DefaultListModel<Restaurant>();
		
		for (Manager user : core.getManagerList()) {
			if(core.getUsers().containsValue(user))
				modelM.addElement(user);
		}
		for (Customer user : core.getCustomerList()) {
			if(core.getUsers().containsValue(user))
				modelCu.addElement(user);
		}
		for (Courier user : core.getCourierList()) {
			if(core.getUsers().containsValue(user))
				modelCo.addElement(user);
		}
		for (Restaurant user : core.getRestaurantList()) {
			if(core.getUsers().containsValue(user))
				modelR.addElement(user);
		}
		
		jListManagerShow.setModel(modelM);
		jListCustomerShow.setModel(modelCu);
		jListCourierShow.setModel(modelCo);
		jListRestaurantShow.setModel(modelR);
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
		userManageMenu.add(new ManagerActionUserManagement("remove", "removing a user", manager));
		userManageMenu.add(new ManagerActionUserManagement("activate", "activating an existing user", manager));
		userManageMenu.add(new ManagerActionUserManagement("deactivate", "deactivating an existing user", manager));
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
				
				fillUserManagePanelRemove("remove");
				
				
				
				break;
			case "activate":
				fillUserManagePanelRemove("activate");
				
				
				break;
				
			case "deactivate":
				fillUserManagePanelRemove("deactivate");
				
				
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
