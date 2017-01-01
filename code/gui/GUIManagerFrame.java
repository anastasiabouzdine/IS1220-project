package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import core.Core;
import policies.DeliveryCostProfit;
import policies.DishSort;
import policies.FastestDelivery;
import policies.MarkupProfit;
import policies.ServiceFeeProfit;
import policies.SortPolicy;
import users.Manager;
import users.Courier;
import users.Customer;
import users.Restaurant;
import users.User;

public class GUIManagerFrame extends GUIUserFrame {

	private GUIManagerFrame instance;
	private Manager manager;

	private JPanel userManagePanel = new JPanel();
	private JPanel profitPanel = new JPanel();
	private JPanel profitRelatedPanel = new JPanel();

	// JMenu
	private JMenu userManageMenu = new JMenu("Manage Users");
	private JMenu profitMenu = new JMenu("Check Profits");
	private Core core = GUIStartFrame.getCore();

	// Button
	private Button goBackfromAddUserButton = new Button("GO BACK");
	private Button removeDeActivateButton;
	private Button simulateButton = new Button("SIMULATE");

	private JList<Manager> jListManagerShow = new JList<>();
	private JList<Customer> jListCustomerShow = new JList<>();
	private JList<Courier> jListCourierShow = new JList<>();
	private JList<Restaurant> jListRestaurantShow = new JList<>();
	private JList<SortPolicy> jListFoodShow = new JList<>();

	private JScrollPane jScrollManager;
	private JScrollPane jScrollCustomer;
	private JScrollPane jScrollCourier;
	private JScrollPane jScrollRestaurant;
	private JScrollPane jScrollFood;

	private JPanel panManager = new JPanel();
	private JPanel panCustomer = new JPanel();
	private JPanel panCourier = new JPanel();
	private JPanel panRestaurant = new JPanel();
	private JPanel panFood = new JPanel();
	private JPanel userPanel = new JPanel();

	// TextField
	private JTextField descrT = new JTextField();
	private JTextField valueT = new JTextField();

	private JTextField descrInput2T = new JTextField();
	private JTextField valueInput2T = new JTextField("");
	private JPanel input2Panel = new JPanel();

	private JTextField descrInput3T = new JTextField();
	private JTextField valueInput3T = new JTextField("");
	private JPanel input3Panel = new JPanel();

	private JPanel inputPanel = new JPanel();

	// Radio Buttons
	ButtonGroup sort_policy_group = new ButtonGroup();
	JRadioButton radio_sortMeal = new JRadioButton("Sort by meal");
	JRadioButton radio_sortDish = new JRadioButton("Sort by dish");

	ButtonGroup delivery_policy_group = new ButtonGroup();
	JRadioButton radio_fastDeliv = new JRadioButton("Set fast delivery policy");
	JRadioButton radio_fairDeliv = new JRadioButton("Set fair delivery policy");

	ButtonGroup simulate_policy_group = new ButtonGroup();
	JRadioButton radio_delCostProfit = new JRadioButton("Simulate delivery cost");
	JRadioButton radio_serFeeProfit = new JRadioButton("Simulate service fee");
	JRadioButton radio_markupProfit = new JRadioButton("Simulate markup percentage");

	/*************************************************/
	// Constructors
	public GUIManagerFrame() {
		super();
		instance = this;
	}

	@Override
	public GUIUserFrame getInstance(User user) {

		if (user instanceof Manager) {
			this.manager = (Manager) user;

			initManager(manager);
			GUIStartFrame.getFrame().setVisible(false);

			initGUI(manager, Color.orange, Color.yellow, "Manager Area", "Just Dwaggit...");
			instance.open(0, 0, 600, 400);
			popUpOkWindow(User.messageBoxGUI);
			return instance;
		}

		return null;
	}

	/*************************************************/
	// fill functions

	public void fillUserManagePanelRemove(String string) {
		if (string.equals("remove")) {
			fillPanelRemoveUsers();
			removeDeActivateButton = new Button("REMOVE");

			removeDeActivateButton.addActionListener((ActionEvent e) -> {
				for (Manager user : jListManagerShow.getSelectedValuesList()) {
					core.removeUser(user);
				}
				for (Customer user : jListCustomerShow.getSelectedValuesList()) {
					core.removeUser(user);
				}
				for (Courier user : jListCourierShow.getSelectedValuesList()) {
					core.removeUser(user);
				}
				for (Restaurant user : jListRestaurantShow.getSelectedValuesList()) {
					core.removeUser(user);
				}
				setCurrentPanel(welcome_panel);
			});

		} else if (string.equals("deactivate")) {
			fillPanelDeactivateUsers();
			removeDeActivateButton = new Button("DEACTIVATE");

			removeDeActivateButton.addActionListener((ActionEvent e) -> {
				for (User user : jListManagerShow.getSelectedValuesList()) {
					core.deactivateUser(user);
				}
				for (User user : jListCustomerShow.getSelectedValuesList()) {
					core.deactivateUser(user);
				}
				for (User user : jListCourierShow.getSelectedValuesList()) {
					core.deactivateUser(user);
				}
				for (User user : jListRestaurantShow.getSelectedValuesList()) {
					core.deactivateUser(user);
				}
				setCurrentPanel(welcome_panel);
			});

		} else if (string.equals("activate")) {
			fillPanelActivateUsers();
			removeDeActivateButton = new Button("ACTIVATE");

			removeDeActivateButton.addActionListener((ActionEvent e) -> {
				for (User user : jListManagerShow.getSelectedValuesList()) {
					core.activateUser(user);
				}
				for (User user : jListCustomerShow.getSelectedValuesList()) {
					core.activateUser(user);
				}
				for (User user : jListCourierShow.getSelectedValuesList()) {
					core.activateUser(user);
				}
				for (User user : jListRestaurantShow.getSelectedValuesList()) {
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
		userPanel.add(panManager, BorderLayout.WEST);
		userPanel.add(panCustomer, BorderLayout.CENTER);
		userPanel.add(panCourier, BorderLayout.CENTER);
		userPanel.add(panRestaurant, BorderLayout.EAST);

		userManagePanel.removeAll();
		userManagePanel.add(userPanel, BorderLayout.CENTER);
		userManagePanel.add(removeDeActivateButton, BorderLayout.NORTH);

		userManagePanel.add(home_button, BorderLayout.SOUTH);
		setCurrentPanel(userManagePanel);
	}

	public void fillPanelRemoveUsers() {
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
		for (Restaurant user : core.getRestaurantList()) {
			modelR.addElement(user);
		}
		for (Courier user : core.getCourierList()) {
			modelCo.addElement(user);
		}

		jListManagerShow.setModel(modelM);
		jListCustomerShow.setModel(modelCu);
		jListCourierShow.setModel(modelCo);
		jListRestaurantShow.setModel(modelR);
	}

	private void fillPanelMostLeast(String string, boolean most) {
		if (string.equals("restaurant")) {
			DefaultListModel<Restaurant> modelR = new DefaultListModel<Restaurant>();

			for (Restaurant user : core.getMostOrLeastSellingRestaurants(most)) {
				modelR.addElement(user);
			}
			jListRestaurantShow.setModel(modelR);
		} else if (string.equals("courier")) {
			DefaultListModel<Courier> modelCo = new DefaultListModel<Courier>();

			for (Courier user : core.getMostOrLeastActiveCouriers(most)) {
				modelCo.addElement(user);
			}
			jListCourierShow.setModel(modelCo);
		} else if (string.equals("food")) {

			DefaultListModel<SortPolicy> modelFood = new DefaultListModel<SortPolicy>();

			for (SortPolicy sortPolicy : core.getSortedList(most)) {
				modelFood.addElement(sortPolicy);
			}
			jListFoodShow.setModel(modelFood);
		}
	}

	private void fillScrollBarMostLeast(String string, boolean most) {
		fillPanelMostLeast(string, most);
		getInfoPanel().removeAll();

		if (string.equals("restaurant")) {

			jScrollRestaurant = new JScrollPane(jListRestaurantShow);
			jScrollRestaurant.setPreferredSize(new Dimension(500, 240));

			panRestaurant.removeAll();
			panRestaurant.add(jScrollRestaurant);

			getInfoPanel().add(panRestaurant, BorderLayout.CENTER);
		} else if (string.equals("courier")) {

			jScrollCourier = new JScrollPane(jListCourierShow);
			jScrollCourier.setPreferredSize(new Dimension(500, 240));

			panCourier.removeAll();
			panCourier.add(jScrollCourier);

			getInfoPanel().add(panCourier, BorderLayout.CENTER);
		} else if (string.equals("food")) {

			jScrollFood = new JScrollPane(jListFoodShow);
			jScrollFood.setPreferredSize(new Dimension(500, 240));

			panFood.removeAll();
			panFood.add(jScrollFood);

			getInfoPanel().add(jScrollFood);
		}
	}

	public void fillPanelActivateUsers() {
		DefaultListModel<Manager> modelM = new DefaultListModel<Manager>();
		DefaultListModel<Customer> modelCu = new DefaultListModel<Customer>();
		DefaultListModel<Courier> modelCo = new DefaultListModel<Courier>();
		DefaultListModel<Restaurant> modelR = new DefaultListModel<Restaurant>();

		for (Manager user : core.getManagerList()) {
			if (!core.getUsers().containsValue(user))
				modelM.addElement(user);
		}
		for (Customer user : core.getCustomerList()) {
			if (!core.getUsers().containsValue(user))
				modelCu.addElement(user);
		}
		for (Courier user : core.getCourierList()) {
			if (!core.getUsers().containsValue(user))
				modelCo.addElement(user);
		}
		for (Restaurant user : core.getRestaurantList()) {
			if (!core.getUsers().containsValue(user))
				modelR.addElement(user);
		}

		jListManagerShow.setModel(modelM);
		jListCustomerShow.setModel(modelCu);
		jListCourierShow.setModel(modelCo);
		jListRestaurantShow.setModel(modelR);
	}

	public void fillPanelDeactivateUsers() {
		DefaultListModel<Manager> modelM = new DefaultListModel<Manager>();
		DefaultListModel<Customer> modelCu = new DefaultListModel<Customer>();
		DefaultListModel<Courier> modelCo = new DefaultListModel<Courier>();
		DefaultListModel<Restaurant> modelR = new DefaultListModel<Restaurant>();

		for (Manager user : core.getManagerList()) {
			if (core.getUsers().containsValue(user))
				modelM.addElement(user);
		}
		for (Customer user : core.getCustomerList()) {
			if (core.getUsers().containsValue(user))
				modelCu.addElement(user);
		}
		for (Courier user : core.getCourierList()) {
			if (core.getUsers().containsValue(user))
				modelCo.addElement(user);
		}
		for (Restaurant user : core.getRestaurantList()) {
			if (core.getUsers().containsValue(user))
				modelR.addElement(user);
		}

		jListManagerShow.setModel(modelM);
		jListCustomerShow.setModel(modelCu);
		jListCourierShow.setModel(modelCo);
		jListRestaurantShow.setModel(modelR);
	}

	public void fillSetPanelRadioPolicy(String string) {
		JPanel policy_type = new JPanel();
		if (string.equals("target")) {

			if (core.getTpPolicy() instanceof MarkupProfit)
				radio_markupProfit.setSelected(true);
			else if (core.getTpPolicy() instanceof ServiceFeeProfit)
				radio_serFeeProfit.setSelected(true);
			else
				radio_delCostProfit.setSelected(true);

			policy_type.add(radio_markupProfit);
			policy_type.add(radio_serFeeProfit);
			policy_type.add(radio_delCostProfit);

		} else if (string.equals("sort")) {

			if (core.getSoPolicy() instanceof DishSort)
				radio_sortDish.setSelected(true);
			else
				radio_sortMeal.setSelected(true);

			policy_type.add(radio_sortDish);
			policy_type.add(radio_sortMeal);

		} else if (string.equals("delivery")) {

			if (core.getdPolicy() instanceof FastestDelivery)
				radio_fastDeliv.setSelected(true);
			else
				radio_fairDeliv.setSelected(true);

			policy_type.add(radio_fastDeliv);
			policy_type.add(radio_fairDeliv);

		}
		getSettingPanel().removeAll();
		getSettingPanel().add(policy_type, BorderLayout.NORTH);
		getSetButtonPanel().removeAll();
		getSetButtonPanel().add(save_button, BorderLayout.SOUTH);
		getSetButtonPanel().add(home_button, BorderLayout.SOUTH);
		getSettingPanel().add(getSetButtonPanel());
		setCurrentPanel(getSettingPanel());
	}

	private void fillManagerActionProfit(String descr, String value) {
		profitRelatedPanel.removeAll();
		descrT.setText(descr);
		descrT.setEditable(false);
		valueT.setText(value);
		profitRelatedPanel.add(descrT, BorderLayout.WEST);
		profitRelatedPanel.add(valueT, BorderLayout.CENTER);
		profitPanel.add(profitRelatedPanel, BorderLayout.NORTH);
		profitPanel.add(home_button, BorderLayout.SOUTH);
		setCurrentPanel(profitPanel);
	}

	/*************************************************/
	// Init functions

	private void initManager(Manager manager) {
		fillAndSetMenuBarManagerInit(manager);
		fillUserManagePanelInit();
		fillProfitRelatedPanelInit();
		initRadioButtons();
		initProfitPanel();

		goBackfromAddUserButton.addActionListener((ActionEvent e) -> {
			setCurrentPanel(welcome_panel);
			GUIStartFrame.register_panel_info.remove(goBackfromAddUserButton);
		});

		simulateButton.addActionListener((ActionEvent e) -> {
			String message;
			try {
				double profit = core.simulateProfit(Double.parseDouble(valueT.getText()),
						Double.parseDouble(valueInput2T.getText()), Double.parseDouble(valueInput3T.getText()));
				System.out.println(profit);
			} catch (NumberFormatException e2) {
				message = "Please fill out all input fields with doubles.";
				popUpOkWindow(message);
			}

		});
	}

	private void initProfitPanel() {
		valueT.setColumns(10);
		valueInput2T.setColumns(5);
		valueInput3T.setColumns(5);
		input2Panel.add(descrInput2T);
		input2Panel.add(valueInput2T);
		input2Panel.setBackground(Color.ORANGE);

		input3Panel.add(descrInput3T);
		input3Panel.add(valueInput3T);
		input3Panel.setBackground(Color.ORANGE);

		inputPanel.setLayout(new BorderLayout());
		inputPanel.add(input2Panel, BorderLayout.EAST);
		inputPanel.add(input3Panel, BorderLayout.WEST);
		inputPanel.setBackground(Color.ORANGE);

		descrInput2T.setEditable(false);
		descrInput2T.setEditable(false);

	}

	private void initRadioButtons() {
		sort_policy_group.add(radio_sortDish);
		sort_policy_group.add(radio_sortMeal);

		delivery_policy_group.add(radio_fastDeliv);
		delivery_policy_group.add(radio_fairDeliv);

		simulate_policy_group.add(radio_delCostProfit);
		simulate_policy_group.add(radio_markupProfit);
		simulate_policy_group.add(radio_serFeeProfit);
	}

	private void fillInfoMenuWithFunctionManagerInit(Manager manager) {
		getInfoMenu().add(new ManagerActionInfoBasicManager("surname", "show current surname", manager));
		getInfoMenu().add(new ManagerActionInfoBasicManager("most selling restaurant",
				"get the most selling restaurants in descending order", manager));
		getInfoMenu().add(new ManagerActionInfoBasicManager("least selling restaurant",
				"get the least selling restaurants in descending order", manager));
		getInfoMenu().add(new ManagerActionInfoBasicManager("most active courier",
				"get the most active courier in descending order", manager));
		getInfoMenu().add(new ManagerActionInfoBasicManager("least active courier",
				"get the least active courier in descending order", manager));
		getInfoMenu().add(new ManagerActionInfoBasicManager("most selling food",
				"get the most selling food in descending order", manager));
		getInfoMenu().add(new ManagerActionInfoBasicManager("least selling food",
				"get the least selling food in descending order", manager));
	}

	private void fillSetMenuWithFunctionManagerInit(Manager manager) {
		getSettingMenu().add(new ManagerActionSettingBasicManager("surname", "change current surname", manager));
		getSettingMenu()
				.add(new ManagerActionSettingBasicManager("markup", "change current markup percentage", manager));
		getSettingMenu()
				.add(new ManagerActionSettingBasicManager("service fee", "change current service fee", manager));
		getSettingMenu()
				.add(new ManagerActionSettingBasicManager("delivery cost", "change current delivery cost", manager));
		getSettingMenu().add(
				new ManagerActionSettingBasicManager("simulation policy", "change current simulation policy", manager));
		getSettingMenu()
				.add(new ManagerActionSettingBasicManager("sort policy", "change current sorting policy", manager));
		getSettingMenu().add(
				new ManagerActionSettingBasicManager("delivery policy", "change current delivery policy", manager));
	}

	private void fillUserManagMenuWithFunctionManagerInit(Manager manager) {
		userManageMenu.add(new ManagerActionUserManagement("add", "adding a new user"));
		userManageMenu.add(new ManagerActionUserManagement("remove", "removing a user"));
		userManageMenu.add(new ManagerActionUserManagement("activate", "activating an existing user"));
		userManageMenu.add(new ManagerActionUserManagement("deactivate", "deactivating an existing user"));
	}

	private void fillUserProfitMenuWithFunctionInit() {
		profitMenu.add(new ManagerActionProfit("simulate", "simulate the profit"));
		profitMenu.add(new ManagerActionProfit("average income per customer", "see average income per customer"));
		profitMenu.add(new ManagerActionProfit("total income", "see total income"));
		profitMenu.add(new ManagerActionProfit("total profit", "see total profit"));
	}

	private void fillAndSetMenuBarManagerInit(Manager manager) {
		fillInfoMenuWithFunctionManagerInit(manager);
		fillSetMenuWithFunctionManagerInit(manager);
		fillUserManagMenuWithFunctionManagerInit(manager);
		fillUserManagePanelInit();
		fillProfitRelatedPanelInit();
		fillUserProfitMenuWithFunctionInit();
		getMenuBar().add(userManageMenu);
		getMenuBar().add(profitMenu);
	}

	private void fillUserManagePanelInit() {
		userManagePanel.setBorder(BorderFactory.createTitledBorder("User management"));
		userManagePanel.setLayout(new BorderLayout());
		userManagePanel.setBackground(Color.LIGHT_GRAY);
	}

	private void fillProfitRelatedPanelInit() {
		profitPanel.setBorder(BorderFactory.createTitledBorder("Revenue and profit"));
		profitPanel.setLayout(new BorderLayout());
		profitPanel.setBackground(Color.orange);
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
				fillInfoPanel(descr, value);
				break;
			case "most selling restaurant":
				fillScrollBarMostLeast("restaurant", true);
				break;
			case "least selling restaurant":
				fillScrollBarMostLeast("restaurant", false);
				break;
			case "most active courier":
				fillScrollBarMostLeast("courier", true);
				break;
			case "least active courier":
				fillScrollBarMostLeast("courier", false);
				break;
			case "most selling food":
				fillScrollBarMostLeast("food", true);
				break;
			case "least selling food":
				fillScrollBarMostLeast("food", false);
			}

			getInfoPanel().add(home_button, BorderLayout.SOUTH);
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

			case "markup":

				descr = "Set the makeup percentage: ";
				value = Double.toString(core.getMarkupPercentage());

				save_button = new JButton("SAVE");
				save_button.addActionListener((ActionEvent e3) -> {

					try {
						Double makeupPercentage = Double.parseDouble(getSetTextFieldValue().getText());
						core.setMarkupPercentage(makeupPercentage);
					} catch (NumberFormatException e4) {
						String message = "Please insert a double for the markup percentage";
						popUpOkWindow(message);
					}

				});

				fillSetPanel(descr, value);
				setCurrentPanel(getSettingPanel());
				break;

			case "service fee":

				descr = "Set the service fee: ";
				value = Double.toString(core.getServiceFee());

				save_button = new JButton("SAVE");
				save_button.addActionListener((ActionEvent e3) -> {

					try {
						Double serviceFee = Double.parseDouble(getSetTextFieldValue().getText());
						core.setServiceFee(serviceFee);
					} catch (NumberFormatException e4) {
						String message = "Please insert a double for the service fee";
						popUpOkWindow(message);
					}

				});

				fillSetPanel(descr, value);
				setCurrentPanel(getSettingPanel());
				break;

			case "delivery cost":

				descr = "Set the delivery cost: ";
				value = Double.toString(core.getDeliveryCost());

				save_button = new JButton("SAVE");
				save_button.addActionListener((ActionEvent e3) -> {

					try {
						Double deliveryCost = Double.parseDouble(getSetTextFieldValue().getText());
						core.setDeliveryCost(deliveryCost);
					} catch (NumberFormatException e4) {
						String message = "Please insert a double for the delivery cost";
						popUpOkWindow(message);
					}

				});

				fillSetPanel(descr, value);
				setCurrentPanel(getSettingPanel());
				break;

			case "simulation policy":

				save_button = new JButton("SAVE");
				save_button.addActionListener((ActionEvent e3) -> {

					if (radio_markupProfit.isSelected())
						core.setTargetProfitPolicyToMarkup();
					else if (radio_serFeeProfit.isSelected())
						core.setTargetProfitPolicyToSerFeeProf();
					else
						core.setTargetProfitPolicyToDelivCostProf();
				});
				fillSetPanelRadioPolicy("target");

				break;
			case "sort policy":

				save_button = new JButton("SAVE");
				save_button.addActionListener((ActionEvent e3) -> {

					if (radio_sortMeal.isSelected())
						core.setSortPolicyToMealSort();
					else
						core.setSortPolicyToDishSort();
				});
				fillSetPanelRadioPolicy("sort");

				break;
			case "delivery policy":

				save_button = new JButton("SAVE");
				save_button.addActionListener((ActionEvent e3) -> {

					if (radio_fastDeliv.isSelected())
						core.setDeliveryPolicyToFastDeliv();
					else
						core.setDeliveryPolicyToFairOcc();
				});
				fillSetPanelRadioPolicy("delivery");

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

		public ManagerActionUserManagement(String choice, String desc) {
			super(choice);
			this.choice = choice;
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

	private class ManagerActionProfit extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String choice;

		public ManagerActionProfit(String choice, String desc) {
			super(choice);
			this.choice = choice;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			String descr = null;
			String value = null;

			switch (choice) {

			case "simulate":
				profitPanel.removeAll();

				String string;
				String decr2 = "Insert ";
				String decr3 = "Insert ";

				if (core.getTpPolicy() instanceof MarkupProfit) {
					string = "markup percentage";
					decr2 += "delivery cost";
					decr3 += "service fee";

				} else if (core.getTpPolicy() instanceof DeliveryCostProfit) {
					string = "delivery cost";
					decr2 += "markup percentage";
					decr3 += "service fee";

				} else {
					string = "service fee";
					decr2 += "markup percentage";
					decr3 += "delivery cost";

				}
				descr = "Insert the profit to simulate the " + string;
				value = "";
				valueT.setEditable(true);
				descrInput2T.setText(decr2);
				descrInput3T.setText(decr3);

				inputPanel.add(simulateButton, BorderLayout.SOUTH);
				profitPanel.add(inputPanel, BorderLayout.CENTER);

				break;
			case "average income per customer":
				profitPanel.removeAll();

				descr = "The average income per customer is: ";
				System.out.println(core.getCustomerList());
				value = Double.toString(core.calcAverageIncome());
				valueT.setEditable(false);

				break;
			case "total income":
				profitPanel.removeAll();

				descr = "The total income is: ";
				value = Double.toString(core.calcTotalIncome());
				valueT.setEditable(false);

				break;
			case "total profit":
				profitPanel.removeAll();

				descr = "The total profit is: ";
				value = Double.toString(core.calcTotalProfit());
				valueT.setEditable(false);

				break;
			}
			fillManagerActionProfit(descr, value);
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
