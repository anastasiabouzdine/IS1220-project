package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
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
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import com.sun.corba.se.spi.protocol.InitialServerRequestDispatcher;
import com.sun.org.apache.bcel.internal.generic.FMUL;
import com.sun.xml.internal.ws.api.policy.ModelUnmarshaller;

import core.Core;
import restaurantSetUp.FullMeal;
import restaurantSetUp.HalfMeal;
import restaurantSetUp.Meal;
import sun.awt.DisplayChangedListener;
import users.Restaurant;
import users.User;

public class GUIRestaurantFrame extends GUIUserFrame {
	
	private GUIRestaurantFrame instance;
	private Restaurant restaurant;
	
	private JList<Meal> jList = new JList<>();
	private JScrollPane jScroll = new JScrollPane();
	private JPanel mealPanel= new JPanel();
	private JPanel dishPanel = new JPanel(); 
		
	
	private JTextField starterT = new JTextField();
	private JTextField mainDishT = new JTextField();
	private JTextField dessertT = new JTextField();
	private JTextField priceT = new JTextField();
		
	
	
	private Button selectButton = new Button("SELECT");
	
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
		
		getFrame().add(dishPanel);
		
		jList.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					Meal meal = rest.getListOfMeal().get(index);
					display(meal);
				} else if (evt.getClickCount() >= 5) {
					System.out.println("Take a chill pill!");
				}
			}

			private void display(Meal meal) {
				if (meal instanceof FullMeal) {
					FullMeal fullMeal = (FullMeal) meal;
					String starter = "Starter :" + fullMeal.getListOfDish().get(0).getName() + " of type "
							+ fullMeal.getListOfDish().get(0).getType();
					String mainDish = "Main Dish :" + fullMeal.getListOfDish().get(1).getName() + " of type "
							+ fullMeal.getListOfDish().get(1).getType();
					String dessert = "Dessert :" + fullMeal.getListOfDish().get(2).getName() + " of type "
							+ fullMeal.getListOfDish().get(2).getType();
					String addInfo = rest.isMealSpecial(fullMeal) ? "$ which is the special offer." : "$.";
					String price = "The total price of the meal is: " + rest.getPrice(fullMeal) + addInfo;

					starterT.setText(starter);
					mainDishT.setText(mainDish);
					dessertT.setText(dessert);
					priceT.setText(price);

					mealPanel.removeAll();
					//TODO layout
					mealPanel.add(starterT);
					mealPanel.add(mainDishT);
					mealPanel.add(dessertT);
					mealPanel.add(priceT);

				} else {

					HalfMeal halfMeal = (HalfMeal) meal;

					String mainDish = "Main Dish :" + halfMeal.getListOfDish().get(0).getName() + " of type "
							+ halfMeal.getListOfDish().get(0).getType();
					String addDish = "Additional Dish :" + halfMeal.getListOfDish().get(1).getName() + " of type "
							+ halfMeal.getListOfDish().get(1).getType();
					String addInfo = rest.isMealSpecial(halfMeal) ? "$ which is the special offer." : "$.";
					String price = "The total price of the meal is: " + rest.getPrice(halfMeal) + addInfo;

					mainDishT.setText(mainDish);
					dessertT.setText(addDish);
					priceT.setText(price);

					mealPanel.removeAll();
					//TODO layout
					mealPanel.add(mainDishT, BorderLayout.NORTH);
					mealPanel.add(dessertT, BorderLayout.NORTH);
					mealPanel.add(priceT, BorderLayout.SOUTH);

				}
				dishPanel.removeAll();
				dishPanel.add(mealPanel);
				setCurrentPanel(dishPanel);
			}
		});

	}
	

	private void fillAndSetMenuBarRest(Restaurant rest) {
		fillInfoMenu(rest);
		fillSettingMenu(rest);
		
	}
	
	private void fillInfoMenu(Restaurant rest){
		getInfoMenu().add(new UserActionInfoBasicRest("address", "show current address", rest));
		getInfoMenu().add(new UserActionInfoBasicRest("meals", "show all full meals", rest));
	}
	
	private void fillSettingMenu(Restaurant rest){
		getSettingMenu().add(new UserActionSettingBasicRest("address", "change current address", rest));
	}
	
	private void fillInfoPanelScroll(){
		getInfoPanel().removeAll();
		jScroll = new JScrollPane(jList);
		getInfoPanel().add(jScroll);
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
				
				DefaultListModel<Meal> model = new DefaultListModel<Meal>();
				for(Meal meal : rest.getListOfMeal()){
						model.addElement(meal);
					
				}
					
				jList.setModel(model);
				jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				fillInfoPanelScroll();
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
            
            case "emailAddress":
            	descr = "Set your new email address: ";
            	value = rest.getEmailAddress();
            	setCurrentSettingShow(7);
                break;
        }
			fillSetPanel(descr,value);
			setCurrentPanel(getSettingPanel());
		}
	}
}

	
