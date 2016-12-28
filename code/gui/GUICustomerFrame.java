package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractAction;
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import core.Core;
import core.Order;
import restaurantSetUp.Dish;
import restaurantSetUp.Meal;
import users.Customer;
import users.Restaurant;
import users.User;

public class GUICustomerFrame extends GUIUserFrame {
	
	private GUICustomerFrame instance;
	private Customer customer;
	private GUIDisplayMealDish mealDishDisplay = new GUIDisplayMealDish();
	
	private Meal currentMeal;
	private Order currentOrder;
	private Core core = GUIStartFrame.getCore();
	
	private JList<Restaurant> restaurants = new JList<Restaurant>();
	private JMenu orderMenu = new JMenu("new order");
	
	private JScrollPane jScrollMeal;
	
	private JPanel orderPanel = new JPanel();
	private JPanel scrollPanel = new JPanel();
	
	public GUICustomerFrame() {
		super();
		instance = this;
	}
	
	/*************************************************/
	//Constructor
	
	@Override
	public GUIUserFrame getInstance(User user) {
		
		if(user instanceof Customer){
			
			GUIStartFrame.getFrame().setVisible(false);
			this.customer = (Customer) user;
			fillAndSetMenuBarCustomer(customer);
			initGUI(customer, Color.orange, Color.yellow, "Customer Area", "Just Dwaggit...");
			fillCustomerInit();
			instance.open(0, 0, 600, 400);
			return instance;
		}
			
		return null;
	}
	
	/*************************************************/
	//fill functions
	
	private void fillRest(Restaurant rest) {

		mealDishDisplay.setTextFields(rest);
//		mealDishDisplay.getGoBack_button()
//				.addActionListener(new UserActionInfoBasicRest("meals", "show all full meals", rest));
		mealDishDisplay.getjListMealShow().addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				JList<?> list = (JList<?>) evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					Meal meal = rest.getListOfMeal().get(index);
					JPanel dishPanel1 = mealDishDisplay.display(meal, rest);
					getFrame().add(dishPanel1);
					currentMeal = meal;
					setCurrentPanel(dishPanel1);
				}
			}
		});
	}
	
	private void fillInfoPanelScroll(JList<?> jlist){
		orderPanel.removeAll();
		jScrollMeal = new JScrollPane(jlist);
		scrollPanel.removeAll();
		scrollPanel.add(jScrollMeal);
		orderPanel.add(scrollPanel, BorderLayout.CENTER);
		setCurrentPanel(orderPanel);
	}
	
	private void displayMeals(Restaurant rest) {
		fillRest(rest);
		mealDishDisplay.fillPanelMealShow(rest);
		mealDishDisplay.getjListMealShow().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fillInfoPanelScroll(mealDishDisplay.getjListMealShow());
		orderPanel.add(home_button, BorderLayout.SOUTH);
	}
	
	private void displayRestaurants() {
		fillRestaurants();
		restaurants.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fillInfoPanelScroll(restaurants);
		orderPanel.add(home_button, BorderLayout.SOUTH);
	}
	private void fillRestaurants() {
		DefaultListModel<Restaurant> model = new DefaultListModel<Restaurant>();
		for (Restaurant rest : core.getRestaurantList()) {
			model.addElement(rest);
		}
		restaurants.setModel(model);
	}

	/*************************************************/
	//Initialize functions 
	
	private void fillCustomerInit() {

		fillorderPanelInit();
//		mealDishDisplay.getGoBack_button()
//				.addActionListener(new UserActionInfoBasicRest("meals", "show all full meals", rest));
		restaurants.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				JList<?> list = (JList<?>) evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					Restaurant rest = core.getRestaurantList().get(index);
					displayMeals(rest);
				}
			}
		});
	}
	
	
	private void fillInfoMenuWithFunctionCustomer(Customer customer) {
		getInfoMenu().add(new CustomerActionInfoBasicCust("address", "show current address", customer));
		getInfoMenu().add(new CustomerActionInfoBasicCust("surname", "show current surname", customer));
		getInfoMenu().add(new CustomerActionInfoBasicCust("phoneNumb", "show current phone number", customer));
		getInfoMenu().add(new CustomerActionInfoBasicCust("emailAddress", "show current email address", customer));
	}
	
	
	private void fillSetMenuWithFunctionCustomer(Customer customer) {
		getSettingMenu().add(new CustomerActionSettingBasicCust("address", "change current address", customer));
		getSettingMenu().add(new CustomerActionSettingBasicCust("surname", "change current surname", customer));
		getSettingMenu().add(new CustomerActionSettingBasicCust("phoneNumb", "change current phone number", customer));
		getSettingMenu().add(new CustomerActionSettingBasicCust("emailAddress", "change current email address", customer));
	}
	
	private void fillcustomerActionOrder(Customer customer) {
		getMenuBar().add(orderMenu);
		orderMenu.add(new CustomerActionOrder("order", "place a new order", customer));
	}
	
	public void fillAndSetMenuBarCustomer(Customer customer){
		fillInfoMenuWithFunctionCustomer(customer);
		fillSetMenuWithFunctionCustomer(customer);
		fillcustomerActionOrder(customer);
	}

	private void fillorderPanelInit() {
		orderPanel.setBorder(BorderFactory.createTitledBorder("Ordering"));
		orderPanel.setLayout(new BorderLayout());
		orderPanel.setBackground(Color.ORANGE);
	}
	
	private class CustomerActionInfoBasicCust extends AbstractAction {

		String choice;
		Customer customer;

		public CustomerActionInfoBasicCust(String choice, String desc, Customer customer) {
			super(choice);
			this.choice = choice;
			this.customer = customer;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e){
			
			
			
			String descr = null;
			String value = null;
			
			switch (choice) {
            case "surname":
            	descr = "Your surname: ";
            	value = customer.getSurname();
                break;
            case "address":
            	descr = "Your address: ";
            	value = customer.getAddress().toString();
                break;
            case "phoneNumb":
            	descr = "Your phone number: ";
            	value = customer.getPhoneNumber();
                break;
            case "emailAddress":
            	descr = "Your email address: ";
            	value = customer.getEmail();
                break;
                
        }
			fillInfoPanel(descr,value);
			setCurrentPanel(getInfoPanel());
		}
	}
	
	
	
	private class CustomerActionSettingBasicCust extends AbstractAction {

		String choice;
		Customer customer;

		public CustomerActionSettingBasicCust(String choice, String desc, Customer customer) {
			super(choice);
			this.choice = choice;
			this.customer = customer;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e){
			
			String descr = null;
			String value = null;
			
			switch (choice) {
            case "surname":
            	descr = "Set your new surname: ";
            	value = customer.getSurname();
            	setCurrentSettingShow(4);
                break;
            case "address":
            	descr = "Set your new address in the format \"xCoord,yCoord\": ";
            	value = customer.getAddress().toString();
            	setCurrentSettingShow(5);
                break;
            case "phoneNumb":
            	descr = "Set your new phone number: ";
            	value = customer.getPhoneNumber();
            	setCurrentSettingShow(6);
                break;
            case "emailAddress":
            	descr = "Set your new email address: ";
            	value = customer.getEmail();
            	setCurrentSettingShow(7);
                break;
        }
			fillSetPanel(descr,value);
			setCurrentPanel(getSettingPanel());
		}
	}
	
	private class CustomerActionOrder extends AbstractAction {

		String choice;
		Customer customer;

		public CustomerActionOrder(String choice, String desc, Customer customer) {
			super(choice);
			this.choice = choice;
			this.customer = customer;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e){
			
			switch (choice) {
            case "new order":
            	displayRestaurants();
                break;
        }
			setCurrentPanel(orderPanel);
		}
	}
	
}