package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;

import core.Core;
import core.Order;
import policies.FidCardPlanBasic;
import policies.FidCardPlanPoints;
import restaurantSetUp.Dish;
import restaurantSetUp.Meal;
import users.Customer;
import users.Restaurant;
import users.User;

public class CustomerFrame extends UserFrame {

	private CustomerFrame instance;
	private Customer customer;
	private DisplayMealDish mealDishDisplay = new DisplayMealDish();

	private Meal currentMeal;
	private Order currentOrder;
	private Restaurant currentRestaurant;
	private Core core = StartFrame.getCore();

	private JList<Restaurant> restaurants = new JList<Restaurant>();
	private JList<Dish> dishes;
	private JList<Order> ordersjList = new JList<>();
	private JMenu orderMenu = new JMenu("New Order");

	private JScrollPane jScrollType;
	private JScrollPane jScrollOrders;

	private JPanel orderPanel = new JPanel();
	private JPanel scrollPanel = new JPanel();
	private JPanel menuMealsPanel = new JPanel();
	private JPanel addToOrderPanel = new JPanel();
	private JPanel finishOrderPanel = new JPanel();
	private JPanel fidPlanPanel = new JPanel();
	private JPanel notificationPanel = new JPanel();

	private JRadioButton fidPlanBasic = new JRadioButton("Basic");
	private JRadioButton fidPlanPoints = new JRadioButton("Points");
	private JRadioButton fidPlanLottery = new JRadioButton("Lottery");
	private ButtonGroup fidPlan = new ButtonGroup();

	private JRadioButton notificationOff = new JRadioButton("Off");
	private JRadioButton notificationOn = new JRadioButton("On");
	private ButtonGroup notification = new ButtonGroup();

	private boolean isOrdering = false;

	private JToggleButton mealsToggleButton;
	private JToggleButton menuToggleButton;

	private Button addToOrderButton;
	private Button finishOrderButton = new Button("FINISH ORDER");

	private JTextField quantityTextField = new JTextField("quantity as int");

	public CustomerFrame() {
		super();
		instance = this;
	}

	/*************************************************/
	// Constructor

	@Override
	public UserFrame getInstance(User user) {

		if (user instanceof Customer) {

			StartFrame.getFrame().setVisible(false);
			this.customer = (Customer) user;
			fillAndSetMenuBarCustomer(customer);
			initGUI(customer, Color.yellow, Color.white, "Customer Area", User.messageBoxGUI);
			fillCustomerInit();
			instance.open(0, 0, 600, 400);
			return instance;
		}

		return null;
	}

	/*************************************************/
	// fill functions

	private void fillRest(Restaurant rest) {

		mealDishDisplay.setTextFields(rest);
		mealDishDisplay.setGoBack_button(new Button("GO BACK"));
		mealDishDisplay.getGoBack_button().addActionListener((ActionEvent e) -> {
			displayMeals(currentRestaurant);
		});
		mealDishDisplay.getjListMealShow().addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				JList<?> list = (JList<?>) evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					Meal meal = rest.getListOfMeal().get(index);
					JPanel mealPanel1 = mealDishDisplay.display(meal, rest);
					currentMeal = meal;
					setUpMealOrdering(mealPanel1, rest);
					setCurrentPanel(mealPanel1);
				}
			}
		});
	}

	private void setUpMealOrdering(JPanel mealPanel, Restaurant rest) {
		scrollPanel.removeAll();
		addToOrderPanel.removeAll();
		addToOrderButton = new Button("ADD TO ORDER");

		addToOrderButton.addActionListener((ActionEvent e) -> {
			if (!isOrdering) {
				System.out.println("isOrdering is true");
				isOrdering = true;
				currentOrder = new Order(core.getCurrent_customer(), rest);
			}
			try {
				int quantity = Integer.parseInt(quantityTextField.getText());
				currentOrder.addMeal(currentMeal, quantity);
				displayMeals(currentRestaurant);
			} catch (NumberFormatException e2) {
				String message = "Please insert the amount of meals that you want to order as an Integer";
				popUpOkWindow(message);
			} finally {
				this.quantityTextField.setText("quantity");
			}

		});

		addToOrderPanel.removeAll();
		addToOrderPanel.add(addToOrderButton);
		addToOrderPanel.add(quantityTextField);
		mealPanel.add(addToOrderPanel, BorderLayout.SOUTH);
	}

	private void fillOrderPanelScroll(JList<?> jlist) {
		orderPanel.removeAll();
		jScrollType = new JScrollPane(jlist);
		scrollPanel.removeAll();
		scrollPanel.add(jScrollType);
		orderPanel.add(scrollPanel, BorderLayout.CENTER);
	}

	private void displayMeals(Restaurant rest) {
		fillRest(rest);
		mealDishDisplay.fillPanelMealShow(rest);
		mealDishDisplay.getjListMealShow().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fillOrderPanelScroll(mealDishDisplay.getjListMealShow());
		fillMealMenuToogleButtons(rest);
		orderPanel.add(menuMealsPanel, BorderLayout.NORTH);

		if (isOrdering) {
			mealsToggleButton.setVisible(false);
			menuToggleButton.setVisible(false);
			finishOrderPanel.removeAll();
			finishOrderPanel.removeAll();
			finishOrderPanel.add(getHome_button());
			finishOrderPanel.add(finishOrderButton);
			orderPanel.add(finishOrderPanel, BorderLayout.SOUTH);
		} else {
			orderPanel.add(getHome_button(), BorderLayout.SOUTH);
		}

		setCurrentPanel(orderPanel);
	}

	private void displayRestaurants() {
		fillRestaurants();
		restaurants.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fillOrderPanelScroll(restaurants);
		orderPanel.add(getHome_button(), BorderLayout.SOUTH);
	}

	private void fillRestaurants() {
		DefaultListModel<Restaurant> model = new DefaultListModel<Restaurant>();
		for (Restaurant rest : core.getRestaurantList()) {
			model.addElement(rest);
		}
		restaurants.setModel(model);
	}

	private void fillMealMenuToogleButtons(Restaurant rest) {

		mealsToggleButton = new JToggleButton("Meals");
		menuToggleButton = new JToggleButton("Menu");
		mealsToggleButton.setSelected(true);
		menuMealsPanel.removeAll();
		menuMealsPanel.add(mealsToggleButton);
		menuMealsPanel.add(menuToggleButton);

		mealsToggleButton.addItemListener((ItemEvent e) -> {
			int state = e.getStateChange();
			if (state == ItemEvent.SELECTED) {
				menuToggleButton.setSelected(false);
				System.out.println("Meal is selected");
				displayMeals(rest);
			} else {
				menuToggleButton.setSelected(true);

			}
		});

		menuToggleButton.addItemListener((ItemEvent e) -> {
			int state = e.getStateChange();
			if (state == ItemEvent.SELECTED) {
				mealsToggleButton.setSelected(false);
				System.out.println("Menu is selected");
				displayDishs(rest);
			} else {
				mealsToggleButton.setSelected(true);
			}
		});
	}

	private void displayDishs(Restaurant rest) {
		dishes = mealDishDisplay.filljListAllDishes(rest);
		fillOrderPanelScroll(dishes);
		addToOrderPanel.removeAll();
		addToOrderPanel.add(getHome_button());
		addToOrderButton = new Button("ADD TO ORDER");

		addToOrderButton.addActionListener((ActionEvent e) -> {
			if (!isOrdering) {
				isOrdering = true;
				mealsToggleButton.setVisible(false);
				menuToggleButton.setVisible(false);
				scrollPanel.add(finishOrderButton);
				currentOrder = new Order(core.getCurrent_customer(), rest);
			}
			try {
				int quantity = Integer.parseInt(quantityTextField.getText());
				Dish dish = mealDishDisplay.getjListAllDish().getSelectedValue();
				currentOrder.addDish(dish, quantity);
			} catch (NumberFormatException e2) {
				String message = "Please insert the amount of meals that you want to order as an Integer";
				popUpOkWindow(message);
			} finally {
				this.quantityTextField.setText("quantity");
			}

		});

		addToOrderPanel.add(addToOrderButton);
		addToOrderPanel.add(quantityTextField);
		orderPanel.add(addToOrderPanel, BorderLayout.SOUTH);
		orderPanel.add(menuMealsPanel, BorderLayout.NORTH);
		setCurrentPanel(orderPanel);
	}

	private void setScrollOrdersPanel() {
		getInfoPanel().removeAll();
		ArrayList<Order> orders = core.getHistoryOfOrders();
		DefaultListModel<Order> model = new DefaultListModel<Order>();
		for (Order order : orders) {
			model.addElement(order);
		}
		this.ordersjList.setModel(model);
		jScrollOrders = new JScrollPane(this.ordersjList);
		getInfoPanel().add(jScrollOrders);
	}

	private void fillSetPanelFidPlan(Customer cust) {
		getSettingPanel().removeAll();
		if (cust.getFidCardPlan() instanceof FidCardPlanBasic) {
			fidPlanBasic.setSelected(true);
		} else if (cust.getFidCardPlan() instanceof FidCardPlanPoints) {
			fidPlanPoints.setSelected(true);
		} else {
			fidPlanLottery.setSelected(true);
		}
		getSettingPanel().add(fidPlanPanel);
		getSetButtonPanel().removeAll();
		getSetButtonPanel().add(getHome_button());
		getSetButtonPanel().add(getSave_button());
		getSettingPanel().add(getSetButtonPanel(), BorderLayout.SOUTH);
	}

	private void fillSetPanelNotification(Customer cust) {
		getSettingPanel().removeAll();
		if (cust.isBeNotified()) {
			notificationOn.setSelected(true);
		} else {
			notificationOff.setSelected(true);
		}
		getSettingPanel().add(notificationPanel);
		getSetButtonPanel().removeAll();
		getSetButtonPanel().add(getHome_button());
		getSetButtonPanel().add(getSave_button());
		getSettingPanel().add(getSetButtonPanel(), BorderLayout.SOUTH);
	}

	/*************************************************/
	// Initialize functions

	private void fillCustomerInit() {

		getReset_button().setVisible(false);
		fillorderPanelInit();
		initSetPanelFidPlan();
		initSetPanelNotif();
		finishOrderButton.addActionListener((ActionEvent e) -> {
			String orderedFood = "Do you want to order: \n\n";

			if (currentOrder.getDishes().isEmpty()) {
				ArrayList<Meal> meals = currentOrder.getMeals();
				for (int i = 0; i < meals.size(); i++) {
					String newLine = " - " + currentOrder.getQuantity().get(i) + " * "
							+ currentOrder.getMeals().get(i).toString() + " \n";
					orderedFood += newLine;
				}
			} else {
				ArrayList<Dish> dishes = currentOrder.getDishes();
				for (int i = 0; i < dishes.size(); i++) {
					String newLine = " - " + currentOrder.getQuantity().get(i) + " * "
							+ currentOrder.getDishes().get(i).toString() + " \n";
					orderedFood += newLine;
				}
			}
			orderedFood += "\n";
			double price = currentOrder.getPrice() + core.getServiceFee();

			if (JOptionPane.showConfirmDialog(null, orderedFood + "for " + price + " Euros?", "Order",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				core.placeNewOrder(currentOrder);
				popUpOkWindow(
						core.getCurrentMessage() + " If you don't pay in 2 days, we will come after your family.");
			} else {
				String message = "Order was deleted";
				popUpOkWindow(message);
			}
			setCurrentPanel(getWelcome_panel());

		});
		restaurants.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				JList<?> list = (JList<?>) evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					currentRestaurant = core.getRestaurantList().get(index);
					displayMeals(currentRestaurant);
				}
			}
		});
		quantityTextField.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				quantityTextField.setText("");

			}
		});
	}

	private void fillInfoMenuWithFunctionCustomer(Customer customer) {
		getInfoMenu().add(new CustomerActionInfoBasicCust("address", "show current address", customer));
		getInfoMenu().add(new CustomerActionInfoBasicCust("surname", "show current surname", customer));
		getInfoMenu().add(new CustomerActionInfoBasicCust("phoneNumb", "show current phone number", customer));
		getInfoMenu().add(new CustomerActionInfoBasicCust("emailAddress", "show current email address", customer));
		getInfoMenu().add(new CustomerActionInfoBasicCust("access history", "show old orders", customer));
	}

	private void fillSetMenuWithFunctionCustomer(Customer customer) {
		getSettingMenu().add(new CustomerActionSettingBasicCust("address", "change current address", customer));
		getSettingMenu().add(new CustomerActionSettingBasicCust("surname", "change current surname", customer));
		getSettingMenu().add(new CustomerActionSettingBasicCust("phoneNumb", "change current phone number", customer));
		getSettingMenu()
				.add(new CustomerActionSettingBasicCust("emailAddress", "change current email address", customer));
		getSettingMenu()
				.add(new CustomerActionSettingBasicCust("fidelity plan", "change current fidelity plan", customer));
		getSettingMenu().add(
				new CustomerActionSettingBasicCust("notification", "change current notification settings", customer));
	}

	private void fillcustomerActionOrder(Customer customer) {
		getMenuBar().add(orderMenu);
		orderMenu.add(new CustomerActionOrder("new order", "place a new order"));
	}

	public void fillAndSetMenuBarCustomer(Customer customer) {
		fillInfoMenuWithFunctionCustomer(customer);
		fillSetMenuWithFunctionCustomer(customer);
		fillcustomerActionOrder(customer);
	}

	private void fillorderPanelInit() {
		orderPanel.setBorder(BorderFactory.createTitledBorder("Ordering"));
		orderPanel.setLayout(new BorderLayout());
		orderPanel.setBackground(Color.ORANGE);
	}

	private void initSetPanelFidPlan() {
		fidPlan.add(fidPlanBasic);
		fidPlan.add(fidPlanPoints);
		fidPlan.add(fidPlanLottery);
		fidPlanPanel.add(fidPlanBasic);
		fidPlanPanel.add(fidPlanPoints);
		fidPlanPanel.add(fidPlanLottery);
	}

	private void initSetPanelNotif() {
		notification.add(notificationOn);
		notification.add(notificationOff);
		notificationPanel.add(notificationOn);
		notificationPanel.add(notificationOff);
	}

	/*************************************************/
	// Action classes

	private class CustomerActionInfoBasicCust extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String choice;
		Customer customer;

		public CustomerActionInfoBasicCust(String choice, String desc, Customer customer) {
			super(choice);
			this.choice = choice;
			this.customer = customer;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			String descr = null;
			String value = null;

			switch (choice) {
			case "surname":
				descr = "Your surname: ";
				value = customer.getSurname();
				fillInfoPanel(descr, value);
				break;
			case "address":
				descr = "Your address: ";
				value = customer.getAddress().toString();
				fillInfoPanel(descr, value);
				break;
			case "phoneNumb":
				descr = "Your phone number: ";
				value = customer.getPhoneNumber();
				fillInfoPanel(descr, value);
				break;
			case "emailAddress":
				descr = "Your email address: ";
				value = customer.getEmail();
				fillInfoPanel(descr, value);
				break;
			case "access history":
				setScrollOrdersPanel();
				break;
			}

			setCurrentPanel(getInfoPanel());
		}

	}

	private class CustomerActionSettingBasicCust extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String choice;
		Customer customer;

		public CustomerActionSettingBasicCust(String choice, String desc, Customer customer) {
			super(choice);
			this.choice = choice;
			this.customer = customer;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			String descr = null;
			String value = null;

			switch (choice) {
			case "surname":
				descr = "Set your new surname: ";
				value = customer.getSurname();
				setSave_button(new JButton("SAVE"));
				getSave_button().addActionListener((ActionEvent e2) -> {

					String value2 = getSetTextFieldValue().getText();
					customer.setSurname(value2);
				});
				fillSetPanel(descr, value);
				break;
			case "address":
				descr = "Set your new address: ";

				setSave_button(new JButton("SAVE"));
				getSave_button().addActionListener((ActionEvent e4) -> {

					try {
						int xCoord = Integer.parseInt(getSetTextFieldXInt().getText());
						int yCoord = Integer.parseInt(getSetTextFieldYInt().getText());
						customer.getAddress().setxCoordinate(xCoord);
						customer.getAddress().setyCoordinate(yCoord);

					} catch (NumberFormatException fex) {
						String message = "Wrong Format! - Please write the address in the format \"xCoord,yCoord\"";
						popUpOkWindow(message);
					}
				});
				fillSetPanelAddress(descr, Integer.toString(customer.getAddress().getxCoordinate()),
						Integer.toString(customer.getAddress().getyCoordinate()));
				break;
			case "phoneNumb":
				descr = "Set your new phone number: ";
				value = customer.getPhoneNumber();
				setSave_button(new JButton("SAVE"));
				getSave_button().addActionListener((ActionEvent e2) -> {

					String value2 = getSetTextFieldValue().getText();
					customer.setPhoneNumber(value2);
				});
				fillSetPanel(descr, value);
				break;
			case "emailAddress":
				descr = "Set your new email address: ";
				value = customer.getEmail();
				setSave_button(new JButton("SAVE"));
				getSave_button().addActionListener((ActionEvent e2) -> {

					String value2 = getSetTextFieldValue().getText();
					customer.setEmail(value2);
				});
				fillSetPanel(descr, value);
				break;
			case "fidelity plan":

				setSave_button(new JButton("SAVE"));
				getSave_button().addActionListener((ActionEvent e2) -> {
					if (fidPlanBasic.isSelected()) {
						customer.setFidCardToBasic();
					} else if (fidPlanPoints.isSelected()) {
						customer.setFidCardToPoints();
					} else if (fidPlanLottery.isSelected()) {
						customer.setFidCardToLottery();
					}
				});
				fillSetPanelFidPlan(customer);
				break;
			case "notification":

				setSave_button(new JButton("SAVE"));
				getSave_button().addActionListener((ActionEvent e2) -> {
					if (notificationOn.isSelected()) {
						customer.setBeNotified(true);
					} else if (notificationOff.isSelected()) {
						customer.setBeNotified(false);
					}
				});
				fillSetPanelNotification(customer);
				break;
			}
			setCurrentPanel(getSettingPanel());
		}
	}

	private class CustomerActionOrder extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String choice;

		public CustomerActionOrder(String choice, String desc) {
			super(choice);
			this.choice = choice;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			switch (choice) {
			case "new order":
				currentMeal = null;
				currentOrder = null;
				currentRestaurant = null;
				isOrdering = false;
				quantityTextField.setText("quantity");
				displayRestaurants();
				String message = "How to order: " + "\n - 1) Select the restaurant from where you want to order "
						+ "\n - 2) Select meals or dishes from the menu that you want to order"
						+ "\n IMPORTANT: You can order either meals or dishes"
						+ "\n - 3) Insert the quantity of the items that you want to order and click 'add to order'"
						+ "\n - 4) As soon as you have added everything you need click 'finish order"
						+ "\n - 5) You will see an overview of what you have ordered and can confirm your order"
						+ "\n - 6) If you want to cancel the order, click 'go home'";
				popUpOkWindow(message);
				break;
			}
			setCurrentPanel(orderPanel);
		}
	}

}