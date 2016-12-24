package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.PrimitiveIterator.OfDouble;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import core.Core;
import restaurantSetUp.FullMeal;
import restaurantSetUp.HalfMeal;
import restaurantSetUp.Meal;
import users.Restaurant;
import users.User;

public class GUIRestaurantFrame extends GUIUserFrame {
	
	private GUIRestaurantFrame instance;
	private Restaurant restaurant;
	
	private JPanel delButtPanel = new JPanel();
	private JPanel scrollPanel = new JPanel();
	
	private JMenu addRemoveMenu = new JMenu("Add / remove");
	private JMenu addDish = new JMenu("Add Dish");
	private JMenu removeDish = new JMenu("Remove Dish");
	
	private JScrollPane jScroll = new JScrollPane();
	private GUIDisplayMealDish mealDishDisplay = new GUIDisplayMealDish();
	
	private Button selectButton = new Button("SELECT");
	private Button deleteButton;
	
	public GUIRestaurantFrame() {
		super();
		instance = this;
	}
	
	/*************************************************/
	//Init functions
	
	@Override
	public GUIUserFrame getInstance(User user) {

		if (user instanceof Restaurant) {
			
			Restaurant rest = (Restaurant) user;

			GUIStartFrame.getFrame().setVisible(false);
			this.restaurant = rest;
			initRest(restaurant);
			fillAndSetMenuBarRest(rest);
			initGUI(restaurant, Color.orange, Color.yellow, "Restaurant Area", "Just Dwaggit...");
			instance.open(0, 0, 600, 400);
			
			
			return instance;
		}

		return null;
	}
	
	private void initRest(Restaurant rest) {
		
		mealDishDisplay.setTextFields(rest);
		mealDishDisplay.getGoBack_button().addActionListener(new UserActionInfoBasicRest("meals", "show all full meals", rest));
		mealDishDisplay.getjListMeal().addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					Meal meal = rest.getListOfMeal().get(index);
					JPanel dishPanel1 = mealDishDisplay.display(meal,rest);
					getFrame().add(dishPanel1);
					setCurrentPanel(dishPanel1);
				}
			}
		});

	}
	

	private void fillAndSetMenuBarRest(Restaurant rest) {
		fillInfoMenu(rest);
		fillSettingMenu(rest);
		fillAddRemoveMenu(rest);
		getMenuBar().add(addRemoveMenu);
		
	}
	
	private void fillAddRemoveMenu(Restaurant rest) {
		removeDish.add(new RestActionAddRemove("removeStarter", "remove a starter dish from the menu", rest));
		removeDish.add(new RestActionAddRemove("removeMainDish", "remove a main dish dish from the menu", rest));
		removeDish.add(new RestActionAddRemove("removeDessert", "remove a dessert dish from the menu", rest));
		addRemoveMenu.add(removeDish);
		addRemoveMenu.add(addDish);
	}

	private void fillInfoMenu(Restaurant rest){
		getInfoMenu().add(new UserActionInfoBasicRest("address", "show current address", rest));
		getInfoMenu().add(new UserActionInfoBasicRest("meals", "show all Full meals", rest));
		JMenu dishMenu = mealDishDisplay.getDishMenu();
		dishMenu.add(new UserActionInfoBasicRest("starter", "show all starters", rest));
		dishMenu.add(new UserActionInfoBasicRest("mainDish", "show all main dishes", rest));
		dishMenu.add(new UserActionInfoBasicRest("dessert", "show all desserts", rest));
		getInfoMenu().add(dishMenu);
	}
	
	private void fillSettingMenu(Restaurant rest){
		getSettingMenu().add(new UserActionSettingBasicRest("address", "change current address", rest));
	}
	
	private void fillInfoPanelScroll(JList jlist){
		getInfoPanel().removeAll();
		getInfoPanel().setLayout(new BorderLayout());
		jScroll = new JScrollPane(jlist);
		scrollPanel.removeAll();
		scrollPanel.add(jScroll);
		getInfoPanel().add(scrollPanel,BorderLayout.SOUTH);
	}
	
	private void fillDeleteButtonPanel(){
		deleteButton = new Button("DELETE SELECTED ITEMS");
		delButtPanel.removeAll();
		getInfoPanel().add(delButtPanel, BorderLayout.NORTH);
		delButtPanel.add(deleteButton);
	}
	
	

	/*************************************************/
	//Help functions
	
	

	private class UserActionInfoBasicRest extends AbstractAction {

		String choice;
		Restaurant rest;

		public UserActionInfoBasicRest(String choice, String desc, Restaurant rest) {
			super(choice);
			this.choice = choice;
			this.rest = rest;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			switch (choice) {

			case "address":
				
				fillInfoPanel("Your address: ", rest.getAddress().toString());
				break;
				
			case "meals":
				
				mealDishDisplay.fillPanelMeal(rest);
				fillInfoPanelScroll(mealDishDisplay.getjListMeal());
				break;
			case "starter":
				
				mealDishDisplay.fillPanelStarter(rest);
				fillInfoPanelScroll(mealDishDisplay.getjListStarter());
				break;
			case "mainDish":
				
				mealDishDisplay.fillPanelMainDish(rest);
				fillInfoPanelScroll(mealDishDisplay.getjListMainDish());
				break;
			case "dessert":
				
				mealDishDisplay.fillPanelDessert(rest);
				fillInfoPanelScroll(mealDishDisplay.getjListDessert());
				break;
			}
			setCurrentPanel(getInfoPanel());
		}
	}
	
	
	
	private class UserActionSettingBasicRest extends AbstractAction {

		String choice;
		User rest;

		public UserActionSettingBasicRest(String choice, String desc, Restaurant rest) {
			super(choice);
			this.choice = choice;
			this.rest = rest;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e){
			
			String descr = null;
			String value = null;
			
			switch (choice) {
            
			//TODO 
//            case "address":
//            	descr = "Set your new address: ";
//            	value = rest.getAddress():
//            	setCurrentSettingShow(7);
//                break;
        }
			fillSetPanel(descr,value);
			setCurrentPanel(getSettingPanel());
		}
	}
	
	private class RestActionAddRemove extends AbstractAction {

		String choice;
		Restaurant rest;

		public RestActionAddRemove(String choice, String desc, Restaurant rest) {
			super(choice);
			this.choice = choice;
			this.rest = rest;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e){
			
			String descr = null;
			String value = null;
			
			switch (choice) {
            
//			TODO 
            case "addMeal":
            	descr = "Set your new email address: ";
          
                break;
            case "removeMeal":
            	descr = "Set your new email address: ";
            	
                break;
            case "addStarter":
            	descr = "Add a new Dish to the : ";
            	
                break;
			case "addMainDish":
            	descr = "Set your new email address: ";
            	
                break;
			case "addDessert":
            	descr = "Set your new email address: ";
    
                break;
            case "removeStarter":
            	
            	//TODO add Textfield that explains
            	
            	descr = "Remove a starter by a double click: ";
				
				mealDishDisplay.fillPanelStarter(rest);
				fillInfoPanelScroll(mealDishDisplay.getjListStarter());
				
				fillDeleteButtonPanel();
				deleteButton.addActionListener((ActionEvent e3) -> {
					int[] toDelete = mealDishDisplay.getjListStarter().getSelectedIndices();
					for (int i = 0; i < toDelete.length; i++) {
						rest.getMenu().getListOfStarter().remove(toDelete[i]-i);
					}
					setCurrentPanel(welcome_panel);
				});
				break;
			case "removeMainDish":
				
				//TODO add Textfield that explains
				
				descr = "Remove a main Dish by a double click: ";
				
				mealDishDisplay.fillPanelMainDish(rest);
				fillInfoPanelScroll(mealDishDisplay.getjListMainDish());
				
				fillDeleteButtonPanel();
				deleteButton.addActionListener((ActionEvent e3) -> {
					int[] toDelete = mealDishDisplay.getjListMainDish().getSelectedIndices();
					for (int i = 0; i < toDelete.length; i++) {
						rest.getMenu().getListOfMainDish().remove(toDelete[i]-i);
					}
					setCurrentPanel(welcome_panel);
				});
				break;
			case "removeDessert":

				descr = "Remove a dessert by a double click: ";

				// TODO add Textfield that explains
				mealDishDisplay.fillPanelDessert(rest);
				fillInfoPanelScroll(mealDishDisplay.getjListDessert());
			
				fillDeleteButtonPanel();
				deleteButton.addActionListener((ActionEvent e3) -> {
					int[] toDelete = mealDishDisplay.getjListDessert().getSelectedIndices();
					for (int i = 0; i < toDelete.length; i++) {
						rest.getMenu().getListOfDessert().remove(toDelete[i]-i);
					}
					setCurrentPanel(welcome_panel);
				});
				break;
			}
			setCurrentPanel(getInfoPanel());
		}
	}
}

	
