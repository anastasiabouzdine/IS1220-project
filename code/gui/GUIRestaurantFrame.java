package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PrimitiveIterator.OfDouble;

import javax.net.ssl.ExtendedSSLSession;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import restaurantSetUp.AbstractFactory;
import restaurantSetUp.Dessert;
import restaurantSetUp.FactoryProducer;
import restaurantSetUp.FullMeal;
import restaurantSetUp.HalfMeal;
import restaurantSetUp.MainDish;
import restaurantSetUp.Meal;
import restaurantSetUp.MealFactory;
import restaurantSetUp.Menu;
import restaurantSetUp.Starter;
import users.Restaurant;
import users.User;

public class GUIRestaurantFrame extends GUIUserFrame {

	private GUIRestaurantFrame instance;
	private Restaurant restaurant;

	private JPanel buttPanel = new JPanel();
	private JPanel scrollPanel = new JPanel();
	private JPanel dishPanel = new JPanel();

	private JPanel panAddMeal1 = new JPanel();
	private JPanel panAddMeal2 = new JPanel();
	private JPanel panAddMeal3 = new JPanel();
	private JPanel addDishPanel = new JPanel();
	private JPanel createMeal = new JPanel();
	private JPanel addRemovePanel = new JPanel();
	private JPanel dishLabelPanel = new JPanel(new GridLayout(0, 1));
	private JPanel dishValuePanel = new JPanel(new GridLayout(0, 1));

	private JMenu addRemoveMenu = new JMenu("Add / Remove");
	private JMenu addDishMenu = new JMenu("add dish");
	private JMenu removeDish = new JMenu("remove dish");

	private JScrollPane jScrollMeal;
	private JScrollPane jScrollMeal1;
	private JScrollPane jScrollMeal2;
	private JScrollPane jScrollMeal3;
	private JScrollPane jScrollPaneSpecMeal;

	private JList<Meal> jListMealSetSpec = new JList<>();

	private JTextField mealName = new JTextField("insert new meal name");
	private JTextField setTextFieldXInt = new JTextField();
	private JTextField setTextFieldYInt = new JTextField();

	private JFormattedTextField nameDish = new JFormattedTextField("Name: ");
	private JFormattedTextField priceDish = new JFormattedTextField("Price: ");
	private JFormattedTextField typeDish = new JFormattedTextField("Type: ");

	private JFormattedTextField nameDishT = new JFormattedTextField("insert name");
	private JFormattedTextField priceDishT = new JFormattedTextField("insert price");
	private String[] food_types = {"standard", "vegetarian", "glutenfree"}; 
	private JComboBox<String> comboBox_foodType = new JComboBox<String>(food_types); 

	private GUIDisplayMealDish mealDishDisplay = new GUIDisplayMealDish();

	//	private Button selectButton = new Button("SELECT");
	private Button deleteButton;
	private Button createMealButton =new Button("CREATE MEAL");
	private Button createDishButton;

	private AbstractFactory dishFactory;

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
			fillAndSetMenuBarRest(rest);
			initGUI(restaurant, Color.orange, Color.yellow, "Restaurant Area", "Just Dwaggit...");
			initRest(restaurant);
			instance.open(0, 0, 600, 400);


			return instance;
		}

		return null;
	}

	private void initRest(Restaurant rest) {

		fillAddMealPanelInit(rest);
		fillAddRemovePanel();
		fillAddDishPanelInit();

		mealDishDisplay.setTextFields(rest);
		mealDishDisplay.getGoBack_button().addActionListener(new UserActionInfoBasicRest("meals", "show all full meals", rest));
		mealDishDisplay.getjListMealShow().addMouseListener(new MouseAdapter() {

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

		jListMealSetSpec.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				JList list = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					Meal meal = rest.getListOfMeal().get(index);
					rest.setSpecialMeal(meal);
					setCurrentPanel(welcome_panel);
				}
			}
		});


	}

	private void fillAddDishPanelInit() {

		dishLabelPanel.add(nameDish);
		dishLabelPanel.add(priceDish);
		dishLabelPanel.add(typeDish);

		nameDish.setEditable(false);
		priceDish.setEditable(false);
		typeDish.setEditable(false);

		dishValuePanel.add(nameDishT);
		dishValuePanel.add(priceDishT);
		dishValuePanel.add(comboBox_foodType);

		dishPanel.removeAll();
		dishPanel.add(dishLabelPanel, BorderLayout.CENTER);
		dishPanel.add(dishValuePanel, BorderLayout.LINE_END);
	}

	private void fillAddDishPanel() {
		nameDishT.setText("insert name");
		priceDishT.setText("insert price");

		addRemovePanel.removeAll();
		addRemovePanel.add(createDishButton, BorderLayout.NORTH);
		addRemovePanel.add(dishPanel, BorderLayout.SOUTH);
	}

	private void fillAddMealPanelInit(Restaurant rest){

		panAddMeal1.setPreferredSize(new Dimension(180, 250));
		panAddMeal2.setPreferredSize(new Dimension(180, 250));
		panAddMeal3.setPreferredSize(new Dimension(180, 250));

		createMeal.setLayout(new BorderLayout());

		createMealButton.addActionListener((ActionEvent e)->{

			JList<Starter> starterList = mealDishDisplay.getjListStarter();
			JList<MainDish> mainDishList = mealDishDisplay.getjListMainDish();
			JList<Dessert> dessertList = mealDishDisplay.getjListDessert();

			String name = mealName.getText();
			Menu menu = rest.getMenu();
			ArrayList<Starter> starters = (ArrayList<Starter>) menu.getListOfStarter();
			ArrayList<MainDish> mainDishs = (ArrayList<MainDish>) menu.getListOfMainDish();
			ArrayList<Dessert> desserts = (ArrayList<Dessert>) menu.getListOfDessert();

			boolean isStart = !starterList.isSelectionEmpty();
			boolean isMain = !mainDishList.isSelectionEmpty();
			boolean isDessert = !dessertList.isSelectionEmpty();

			if(isStart&&isMain&&isDessert){
				int starterIndex = starterList.getSelectedIndices()[0];
				int mainDishIndex = mainDishList.getSelectedIndices()[0];
				int dessertIndex = dessertList.getSelectedIndices()[0];

				AbstractFactory mealFactory = FactoryProducer.getFactory("Meal");
				FullMeal fullMeal = (FullMeal) mealFactory.getMeal("FullMeal", name);
				fullMeal.setFullMeal(starters.get(starterIndex), mainDishs.get(mainDishIndex), desserts.get(dessertIndex));
				rest.addMeal(fullMeal);
			}
			else if(isStart&&isMain){
				int mainDishIndex = mainDishList.getSelectedIndices()[0];
				int starterIndex = starterList.getSelectedIndices()[0];

				AbstractFactory mealFactory = FactoryProducer.getFactory("Meal");
				HalfMeal halfMeal = (HalfMeal) mealFactory.getMeal("HalfMeal", name);
				halfMeal.setHalfMeal(mainDishs.get(mainDishIndex), starters.get(starterIndex));
				rest.addMeal(halfMeal);
			}
			else if(isDessert&&isMain){
				int mainDishIndex = mainDishList.getSelectedIndices()[0];
				int dessertIndex = dessertList.getSelectedIndices()[0];

				AbstractFactory mealFactory = FactoryProducer.getFactory("Meal");
				HalfMeal halfMeal = (HalfMeal) mealFactory.getMeal("HalfMeal", name);
				halfMeal.setHalfMeal(mainDishs.get(mainDishIndex), desserts.get(dessertIndex));
				rest.addMeal(halfMeal); 
			}
			else{
				//TODO Fehler message
			}
			setCurrentPanel(welcome_panel);
		});
	}

	private void fillAddMealPanelValues(Restaurant rest){

		getInfoPanel().removeAll();
		getInfoPanel().setLayout(new BorderLayout());

		jScrollMeal1 = new JScrollPane(mealDishDisplay.getjListStarter());
		jScrollMeal2 = new JScrollPane(mealDishDisplay.getjListMainDish());
		jScrollMeal3 = new JScrollPane(mealDishDisplay.getjListDessert());

		jScrollMeal1.setPreferredSize(new Dimension(170, 240));
		jScrollMeal2.setPreferredSize(new Dimension(170, 240));
		jScrollMeal3.setPreferredSize(new Dimension(170, 240));

		panAddMeal1.removeAll();
		panAddMeal2.removeAll();
		panAddMeal3.removeAll();
		panAddMeal1.add(jScrollMeal1);
		panAddMeal2.add(jScrollMeal2);
		panAddMeal3.add(jScrollMeal3);

		createMeal.removeAll();
		createMeal.add(panAddMeal1,BorderLayout.WEST);
		createMeal.add(panAddMeal2,BorderLayout.CENTER);
		createMeal.add(panAddMeal3,BorderLayout.EAST);

		filladdButtonPanel();

		addRemovePanel.removeAll();
		addRemovePanel.add(createMeal,BorderLayout.SOUTH);
		addRemovePanel.add(buttPanel, BorderLayout.NORTH);

	}
	private void fillAddRemovePanel(){
		addRemovePanel.setBorder(BorderFactory.createTitledBorder("Add / Remove"));
		addRemovePanel.setLayout(new BorderLayout());
		addRemovePanel.setBackground(Color.magenta);
	}


	private void fillAndSetMenuBarRest(Restaurant rest) {
		fillInfoMenu(rest);
		fillSettingMenu(rest);
		fillAddRemoveMenu(rest);
		getMenuBar().add(addRemoveMenu);

	}

	private void fillAddRemoveMenu(Restaurant rest) {
		removeDish.add(new RestActionAddRemove("remove starter", "remove a starter dish from the menu", rest));
		removeDish.add(new RestActionAddRemove("remove main dish", "remove a main dish dish from the menu", rest));
		removeDish.add(new RestActionAddRemove("remove dessert", "remove a dessert dish from the menu", rest));
		addDishMenu.add(new RestActionAddRemove("add starter", "add a new starter", rest));
		addDishMenu.add(new RestActionAddRemove("add main dish", "add a new main dish", rest));
		addDishMenu.add(new RestActionAddRemove("add dessert", "add a new dessert", rest));
		addRemoveMenu.add(new RestActionAddRemove("remove meal", "remove a meal", rest));
		addRemoveMenu.add(removeDish);
		addRemoveMenu.add(addDishMenu);
		addRemoveMenu.add(new RestActionAddRemove("add meal", "add a meal by choosing different dishes", rest));
	}

	private void fillInfoMenu(Restaurant rest){
		getInfoMenu().add(new UserActionInfoBasicRest("address", "show current address", rest));
		getInfoMenu().add(new UserActionInfoBasicRest("special meal", "show current special meal", rest));
		getInfoMenu().add(new UserActionInfoBasicRest("meals", "show all full meals", rest));
		JMenu dishMenu = mealDishDisplay.getDishMenu();
		dishMenu.add(new UserActionInfoBasicRest("starter", "show all starters", rest));
		dishMenu.add(new UserActionInfoBasicRest("main dish", "show all main dishes", rest));
		dishMenu.add(new UserActionInfoBasicRest("dessert", "show all desserts", rest));
		getInfoMenu().add(dishMenu);
	}

	private void fillSettingMenu(Restaurant rest){
		getSettingMenu().add(new UserActionSettingBasicRest("address", "change current address", rest));
		getSettingMenu().add(new UserActionSettingBasicRest("special meal", "change current special meal", rest));
	}

	private void fillInfoPanelScroll(JList jlist){
		getInfoPanel().removeAll();
		getInfoPanel().setLayout(new BorderLayout());
		jScrollMeal = new JScrollPane(jlist);
		scrollPanel.removeAll();
		scrollPanel.add(jScrollMeal);
		getInfoPanel().add(scrollPanel,BorderLayout.SOUTH);
	}

	private void fillAddRemovePanelScroll(JList jlist){
		addRemovePanel.removeAll();
		addRemovePanel.setLayout(new BorderLayout());
		jScrollMeal = new JScrollPane(jlist);
		scrollPanel.removeAll();
		scrollPanel.add(jScrollMeal);
		addRemovePanel.add(scrollPanel, BorderLayout.SOUTH);
	}

	private void fillDeleteButtonPanel(){
		deleteButton = new Button("DELETE SELECTED ITEMS");
		buttPanel.removeAll();
		addRemovePanel.add(buttPanel, BorderLayout.NORTH);
		buttPanel.add(deleteButton);
	}

	private void filladdButtonPanel(){
		buttPanel.removeAll();
		addRemovePanel.add(buttPanel, BorderLayout.NORTH);
		buttPanel.add(createMealButton);
		buttPanel.add(mealName);
	}
	public void fillPanelMealSetSpecial(Restaurant rest){
		DefaultListModel<Meal> model = new DefaultListModel<Meal>();
		for (Meal meal : rest.getListOfMeal()) {
			model.addElement(meal);
		}
		jListMealSetSpec.setModel(model);
	}
	public void fillSetPanelAddress(String descr, String valueX, String valueY) {
		getSettingPanel().removeAll();
		getSetTextFieldDesc().setText(descr);
		setTextFieldXInt.setText(valueX);
		setTextFieldYInt.setText(valueY);
		getSetSubPanel().removeAll();
		getSetSubPanel().add(getSetTextFieldDesc(), BorderLayout.CENTER);
		getSetSubPanel().add(setTextFieldXInt, BorderLayout.SOUTH);
		getSetSubPanel().add(setTextFieldYInt, BorderLayout.SOUTH);
		getSettingPanel().add(getSetSubPanel());
		getSetButtonPanel().removeAll();
		getSetButtonPanel().add(home_button);
		getSetButtonPanel().add(save_button);
		getSettingPanel().add(getSetButtonPanel(),BorderLayout.SOUTH);
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

			case "special meal":

				fillInfoPanel("Your current special meal: ", rest.getSpecialMeal()==null ? "no special meal selected" : rest.getSpecialMeal().toString());
				break;

			case "meals":

				mealDishDisplay.fillPanelMealShow(rest);
				mealDishDisplay.getjListMealShow().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				fillInfoPanelScroll(mealDishDisplay.getjListMealShow());
				break;
			case "starter":

				mealDishDisplay.filljListStarter(rest);
				fillInfoPanelScroll(mealDishDisplay.getjListStarter());
				break;
			case "main dish":

				mealDishDisplay.filljListMainDish(rest);
				fillInfoPanelScroll(mealDishDisplay.getjListMainDish());
				break;
			case "dessert":

				mealDishDisplay.filljListDessert(rest);
				fillInfoPanelScroll(mealDishDisplay.getjListDessert());
				break;
			}
			setCurrentPanel(getInfoPanel());
		}
	}



	private class UserActionSettingBasicRest extends AbstractAction {

		String choice;
		Restaurant rest;

		public UserActionSettingBasicRest(String choice, String desc, Restaurant rest) {
			super(choice);
			this.choice = choice;
			this.rest = rest;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e){

			String descr = null;
			String value1 = null;
			String value2 = null;

			switch (choice) {




			case "address":
				descr = "Set your new address: ";

				save_button = new JButton("SAVE");
				save_button.addActionListener((ActionEvent e4) -> {
					try{
						int xCoord = Integer.parseInt(setTextFieldXInt.getText());
						int yCoord = Integer.parseInt(setTextFieldYInt.getText());
						rest.getAddress().setxCoordinate(xCoord);
						rest.getAddress().setyCoordinate(yCoord);
					}catch(NumberFormatException fex){
						String message = "Wrong Format! - Please write the address in the format \"xCoord,yCoord\"";
						//TODO pop up
					}
				});
				fillSetPanelAddress(descr, Integer.toString(rest.getAddress().getxCoordinate()), Integer.toString(rest.getAddress().getyCoordinate()));
				break;
			case "special meal":

				fillPanelMealSetSpecial(rest);
				jScrollPaneSpecMeal = new JScrollPane(jListMealSetSpec);
				scrollPanel.removeAll();
				scrollPanel.add(jScrollPaneSpecMeal);
				getSettingPanel().removeAll();
				getSettingPanel().add(scrollPanel);

				break;
			}
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

			//TODO add a descr if wanted

			switch (choice) {

			//			TODO 
			case "add meal":
				descr = "add a meal: ";

				mealDishDisplay.filljListStarter(rest);
				mealDishDisplay.filljListMainDish(rest);
				mealDishDisplay.filljListDessert(rest);

				mealDishDisplay.getjListStarter().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				mealDishDisplay.getjListMainDish().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				mealDishDisplay.getjListDessert().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				fillAddMealPanelValues(rest);

				break;
			case "remove meal":
				descr = "remove a meal: ";

				mealDishDisplay.fillPanelMealRemove(rest);
				fillAddRemovePanelScroll(mealDishDisplay.getjListMealRemove());

				fillDeleteButtonPanel();
				deleteButton.addActionListener((ActionEvent e3) -> {
					int[] toDelete = mealDishDisplay.getjListMealRemove().getSelectedIndices();
					for (int i = 0; i < toDelete.length; i++) {
						rest.getListOfMeal().remove(toDelete[i]-i);
					}
					setCurrentPanel(welcome_panel);
				});
				break;

			case "add starter":
				descr = "Add a new starter to the menu: ";

				createDishButton = new Button("CREATE STARTER");
				createDishButton.addActionListener((ActionEvent e1)->{

					dishFactory = FactoryProducer.getFactory("Dish");

					try{
						double price=0;
						price = Double.parseDouble(priceDishT.getText());

						Starter starter = (Starter) dishFactory.getDish("Starter", nameDishT.getText(), price ,
								(String)comboBox_foodType.getSelectedItem());

						rest.addStarter(starter);
					} catch (NumberFormatException e2){
						//TODO pop up
						String message = "Please insert a number with two digits for the price.";
						System.out.println(message);
					}
					setCurrentPanel(welcome_panel);
				});
				fillAddDishPanel();

				break;
			case "add main dish":
				descr = "Add a new main dish to the menu: ";

				createDishButton = new Button("CREATE MAINDISH");
				createDishButton.addActionListener((ActionEvent e1)->{

					dishFactory = FactoryProducer.getFactory("Dish");

					try{
						double price=0;
						price = Double.parseDouble(priceDishT.getText());

						MainDish mainDish = (MainDish) dishFactory.getDish("Starter", nameDishT.getText(), price ,
								(String)comboBox_foodType.getSelectedItem());

						rest.addMainDish(mainDish);
					} catch (NumberFormatException e2){
						//TODO pop up
						String message = "Please insert a number with two digits for the price.";
						System.out.println(message);
					}
					setCurrentPanel(welcome_panel);
				});

				fillAddDishPanel();

				break;
			case "add dessert":
				descr = "Add a new dessert to the menu: ";

				createDishButton = new Button("CREATE DESSERT");
				createDishButton.addActionListener((ActionEvent e1)->{

					dishFactory = FactoryProducer.getFactory("Dish");

					try{
						double price=0;
						price = Double.parseDouble(priceDishT.getText());
						Dessert dessert = (Dessert) dishFactory.getDish("Starter", nameDishT.getText(), price ,
								(String)comboBox_foodType.getSelectedItem());

						rest.addDessert(dessert);
					} catch (NumberFormatException e2){
						//TODO pop up
						String message = "Please insert a number with two digits for the price.";
						System.out.println(message);
					}
					setCurrentPanel(welcome_panel);
				});

				fillAddDishPanel();

				break;
			case "remove starter":

				//TODO add Textfield that explains

				descr = "remove a starter by a double click: ";

				mealDishDisplay.filljListStarter(rest);
				fillAddRemovePanelScroll(mealDishDisplay.getjListStarter());

				fillDeleteButtonPanel();
				deleteButton.addActionListener((ActionEvent e3) -> {
					int[] toDelete = mealDishDisplay.getjListStarter().getSelectedIndices();
					for (int i = 0; i < toDelete.length; i++) {
						rest.getMenu().getListOfStarter().remove(toDelete[i]-i);
					}
					setCurrentPanel(welcome_panel);
				});
				break;
			case "remove main dish":

				//TODO add Textfield that explains

				descr = "remove a main dish by a double click: ";

				mealDishDisplay.filljListMainDish(rest);
				fillAddRemovePanelScroll(mealDishDisplay.getjListMainDish());

				fillDeleteButtonPanel();
				deleteButton.addActionListener((ActionEvent e3) -> {
					int[] toDelete = mealDishDisplay.getjListMainDish().getSelectedIndices();
					for (int i = 0; i < toDelete.length; i++) {
						rest.getMenu().getListOfMainDish().remove(toDelete[i]-i);
					}
					setCurrentPanel(welcome_panel);
				});
				break;
			case "remove dessert":

				descr = "remove a dessert by a double click: ";

				// TODO add Textfield that explains
				mealDishDisplay.filljListDessert(rest);
				fillAddRemovePanelScroll(mealDishDisplay.getjListDessert());

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

			setCurrentPanel(addRemovePanel);
		}
	}
}


