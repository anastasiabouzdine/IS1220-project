package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import restaurantSetUp.AbstractFactory;
import restaurantSetUp.Dessert;
import restaurantSetUp.FactoryProducer;
import restaurantSetUp.FullMeal;
import restaurantSetUp.HalfMeal;
import restaurantSetUp.MainDish;
import restaurantSetUp.Meal;
import restaurantSetUp.Menu;
import restaurantSetUp.Starter;
import users.Restaurant;
import users.User;

public class RestaurantFrame extends UserFrame {

	private RestaurantFrame instance;
	private Restaurant restaurant;

	// JPanel
	private JPanel buttPanel = new JPanel();
	private JPanel scrollPanel = new JPanel();
	private JPanel dishPanel = new JPanel();
	private JPanel panAddMeal1 = new JPanel();
	private JPanel panAddMeal2 = new JPanel();
	private JPanel panAddMeal3 = new JPanel();
	private JPanel createMeal = new JPanel();
	private JPanel addRemovePanel = new JPanel();
	private JPanel dishLabelPanel = new JPanel(new GridLayout(0, 1));
	private JPanel dishValuePanel = new JPanel(new GridLayout(0, 1));

	// JMenu
	private JMenu addRemoveMenu = new JMenu("Add / Remove");
	private JMenu addDishMenu = new JMenu("add dish");
	private JMenu removeDish = new JMenu("remove dish");

	// JScrollPane
	private JScrollPane jScrollMeal;
	private JScrollPane jScrollMeal1;
	private JScrollPane jScrollMeal2;
	private JScrollPane jScrollMeal3;
	private JScrollPane jScrollPaneSpecMeal;

	// JList
	private JList<Meal> jListMealSetSpec = new JList<>();

	// JTextField
	private JTextField mealName = new JTextField("insert new meal name");

	// JFormatedTextField
	private JFormattedTextField nameDish = new JFormattedTextField("Name: ");
	private JFormattedTextField priceDish = new JFormattedTextField("Price: ");
	private JFormattedTextField typeDish = new JFormattedTextField("Type: ");
	private JFormattedTextField nameDishT = new JFormattedTextField("insert name");
	private JFormattedTextField priceDishT = new JFormattedTextField("insert price");
	private JFormattedTextField typeDishT = new JFormattedTextField("insert type");

	// Helper to easily display meals
	private DisplayMealDish mealDishDisplay = new DisplayMealDish();

	// Combo box
	private String[] food_types = { "standard", "vegetarian", "glutenfree" };
	private JComboBox<String> comboBox_foodType = new JComboBox<String>(food_types);

	// Buttons
	private Button deleteButton;
	private Button createMealButton = new Button("CREATE MEAL");
	private Button createDishButton;

	// Factory
	private AbstractFactory dishMealFactory;

	/*************************************************/
	// Constructor

	public RestaurantFrame() {
		super();
		if (instance == null)
			instance = this;
	}

	/*************************************************/
	// Initialize functions

	@Override
	public UserFrame getInstance(User user) {

		if (user instanceof Restaurant) {

			Restaurant rest = (Restaurant) user;

			StartFrame.getFrame().setVisible(false);
			this.restaurant = rest;
			fillAndSetMenuBarRestInit(rest);
			initGUI(restaurant, Color.red, Color.white, "Restaurant Area", User.messageBoxGUI);
			initRest(restaurant);
			instance.open(0, 0, 600, 400);
			return instance;
		}

		return null;
	}

	private void initRest(Restaurant rest) {

		getReset_button().setVisible(false);
		fillTextFieldsWithFocusInit();
		fillAddMealPanelInit(rest);
		fillAddRemovePanelInit();
		fillAddDishPanelInit();

		mealDishDisplay.setTextFields(rest);
		mealDishDisplay.setGoBack_button(new Button("GO BACK"));
		mealDishDisplay.getGoBack_button()
				.addActionListener(new UserActionInfoBasicRest("meals", "show all full meals", rest));

		mealDishDisplay.getjListMealShow().addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				JList<?> list = (JList<?>) evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					Meal meal = rest.getListOfMeal().get(index);
					JPanel dishPanel1 = mealDishDisplay.display(meal, rest);
					getFrame().add(dishPanel1);
					setCurrentPanel(dishPanel1);
				}
			}
		});

		jListMealSetSpec.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				JList<?> list = (JList<?>) evt.getSource();
				if (evt.getClickCount() == 2) {
					int index = list.locationToIndex(evt.getPoint());
					Meal meal = rest.getListOfMeal().get(index);
					rest.setSpecialMeal(meal);
					setCurrentPanel(getWelcome_panel());
				}
			}
		});
	}

	private void fillTextFieldsWithFocusInit() {

		nameDishT.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				nameDishT.setText("");

			}
		});
		priceDishT.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				priceDishT.setText("");

			}
		});
		mealName.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				mealName.setText("");

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

	private void fillAddMealPanelInit(Restaurant rest) {

		panAddMeal1.setPreferredSize(new Dimension(180, 250));
		panAddMeal2.setPreferredSize(new Dimension(180, 250));
		panAddMeal3.setPreferredSize(new Dimension(180, 250));

		createMeal.setLayout(new BorderLayout());

		createMealButton.addActionListener((ActionEvent e) -> {

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

			if (isStart && isMain && isDessert) {
				int starterIndex = starterList.getSelectedIndices()[0];
				int mainDishIndex = mainDishList.getSelectedIndices()[0];
				int dessertIndex = dessertList.getSelectedIndices()[0];

				AbstractFactory mealFactory = FactoryProducer.getFactory("Meal");
				FullMeal fullMeal = (FullMeal) mealFactory.getMeal("FullMeal", name);
				fullMeal.setFullMeal(starters.get(starterIndex), mainDishs.get(mainDishIndex),
						desserts.get(dessertIndex));
				rest.addMeal(fullMeal);
				setCurrentPanel(getWelcome_panel());
			} else if (isStart && isMain) {
				int mainDishIndex = mainDishList.getSelectedIndices()[0];
				int starterIndex = starterList.getSelectedIndices()[0];

				AbstractFactory mealFactory = FactoryProducer.getFactory("Meal");
				HalfMeal halfMeal = (HalfMeal) mealFactory.getMeal("HalfMeal", name);
				halfMeal.setHalfMeal(mainDishs.get(mainDishIndex), starters.get(starterIndex));
				rest.addMeal(halfMeal);
				setCurrentPanel(getWelcome_panel());
			} else if (isDessert && isMain) {
				int mainDishIndex = mainDishList.getSelectedIndices()[0];
				int dessertIndex = dessertList.getSelectedIndices()[0];

				AbstractFactory mealFactory = FactoryProducer.getFactory("Meal");
				HalfMeal halfMeal = (HalfMeal) mealFactory.getMeal("HalfMeal", name);
				halfMeal.setHalfMeal(mainDishs.get(mainDishIndex), desserts.get(dessertIndex));
				rest.addMeal(halfMeal);
				setCurrentPanel(getWelcome_panel());
			} else {
				String message = "Please select one of the following options: 1) starter, main dish and dessert (full meal), "
						+ "2) starter and main dish (half meal) or 3) main dish and dessert (half meal).";

				popUpOkWindow(message);
			}

		});
	}

	private void fillAndSetMenuBarRestInit(Restaurant rest) {
		fillInfoMenuInit(rest);
		fillSettingMenuInit(rest);
		fillAddRemoveMenuInit(rest);
		getMenuBar().add(addRemoveMenu);
	}

	private void fillAddMealPanelValues(Restaurant rest) {

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
		createMeal.add(panAddMeal1, BorderLayout.WEST);
		createMeal.add(panAddMeal2, BorderLayout.CENTER);
		createMeal.add(panAddMeal3, BorderLayout.EAST);

		filladdButtonPanel();

		addRemovePanel.removeAll();
		addRemovePanel.add(createMeal, BorderLayout.CENTER);
		addRemovePanel.add(buttPanel, BorderLayout.NORTH);

	}

	private void fillAddRemovePanelInit() {
		addRemovePanel.setBorder(BorderFactory.createTitledBorder("Add / Remove"));
		addRemovePanel.setLayout(new BorderLayout());
		addRemovePanel.setBackground(Color.magenta);
	}

	private void fillAddRemoveMenuInit(Restaurant rest) {
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

	private void fillInfoMenuInit(Restaurant rest) {
		getInfoMenu().add(new UserActionInfoBasicRest("address", "show current address", rest));
		getInfoMenu().add(new UserActionInfoBasicRest("special meal", "show current special meal", rest));
		getInfoMenu().add(new UserActionInfoBasicRest("meals", "show all full meals", rest));
		JMenu dishMenu = mealDishDisplay.getDishMenu();
		dishMenu.add(new UserActionInfoBasicRest("starter", "show all starters", rest));
		dishMenu.add(new UserActionInfoBasicRest("main dish", "show all main dishes", rest));
		dishMenu.add(new UserActionInfoBasicRest("dessert", "show all desserts", rest));
		getInfoMenu().add(dishMenu);
	}

	private void fillSettingMenuInit(Restaurant rest) {
		getSettingMenu().add(new UserActionSettingBasicRest("address", "change current address", rest));
		getSettingMenu().add(new UserActionSettingBasicRest("special meal", "change current special meal", rest));
	}

	/*************************************************/
	// fill functions

	private void fillAddDishPanel() {
		nameDishT.setText("insert name");
		priceDishT.setText("insert price");
		typeDishT.setText("insert type");

		addRemovePanel.removeAll();
		addRemovePanel.add(createDishButton, BorderLayout.NORTH);
		addRemovePanel.add(dishPanel, BorderLayout.CENTER);
	}

	private void fillInfoPanelScroll(JList<?> jlist) {
		getInfoPanel().removeAll();
		getInfoPanel().setLayout(new BorderLayout());
		jScrollMeal = new JScrollPane(jlist);
		scrollPanel.removeAll();
		scrollPanel.add(jScrollMeal);
		getInfoPanel().add(scrollPanel, BorderLayout.CENTER);
	}

	private void fillAddRemovePanelScroll(JList<?> jlist) {
		addRemovePanel.removeAll();
		addRemovePanel.setLayout(new BorderLayout());
		jScrollMeal = new JScrollPane(jlist);
		scrollPanel.removeAll();
		scrollPanel.add(jScrollMeal);
		addRemovePanel.add(scrollPanel, BorderLayout.CENTER);
	}

	private void fillDeleteButtonPanel() {
		deleteButton = new Button("DELETE SELECTED ITEMS");
		buttPanel.removeAll();
		addRemovePanel.add(buttPanel, BorderLayout.NORTH);
		buttPanel.add(deleteButton);
	}

	private void filladdButtonPanel() {
		buttPanel.removeAll();
		addRemovePanel.add(buttPanel, BorderLayout.NORTH);
		buttPanel.add(createMealButton);
		buttPanel.add(mealName);
	}

	public void fillPanelMealSetSpecial(Restaurant rest) {
		DefaultListModel<Meal> model = new DefaultListModel<Meal>();
		for (Meal meal : rest.getListOfMeal()) {
			model.addElement(meal);
		}
		jListMealSetSpec.setModel(model);
	}

	/*************************************************/
	// action classes

	private class UserActionInfoBasicRest extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
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

				fillInfoPanel("Your current special meal: ",
						rest.getSpecialMeal() == null ? "no special meal selected" : rest.getSpecialMeal().toString());
				break;

			case "meals":

				mealDishDisplay.fillPanelMealShow(rest);
				mealDishDisplay.getjListMealShow().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				fillInfoPanelScroll(mealDishDisplay.getjListMealShow());
				getInfoPanel().add(getHome_button(), BorderLayout.SOUTH);
				break;
			case "starter":

				mealDishDisplay.filljListStarter(rest);
				fillInfoPanelScroll(mealDishDisplay.getjListStarter());
				getInfoPanel().add(getHome_button(), BorderLayout.SOUTH);
				break;
			case "main dish":

				mealDishDisplay.filljListMainDish(rest);
				fillInfoPanelScroll(mealDishDisplay.getjListMainDish());
				getInfoPanel().add(getHome_button(), BorderLayout.SOUTH);
				break;
			case "dessert":

				mealDishDisplay.filljListDessert(rest);
				fillInfoPanelScroll(mealDishDisplay.getjListDessert());
				getInfoPanel().add(getHome_button(), BorderLayout.SOUTH);
				break;
			}
			setCurrentPanel(getInfoPanel());
		}
	}

	private class UserActionSettingBasicRest extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String choice;
		Restaurant rest;

		public UserActionSettingBasicRest(String choice, String desc, Restaurant rest) {
			super(choice);
			this.choice = choice;
			this.rest = rest;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		public void actionPerformed(ActionEvent e) {

			String descr = null;

			switch (choice) {

			case "address":
				descr = "Set your new address: ";

				setSave_button(new JButton("SAVE"));
				getSave_button().addActionListener((ActionEvent e4) -> {

					try {
						int xCoord = Integer.parseInt(getSetTextFieldXInt().getText());
						int yCoord = Integer.parseInt(getSetTextFieldYInt().getText());
						rest.getAddress().setxCoordinate(xCoord);
						rest.getAddress().setyCoordinate(yCoord);

					} catch (NumberFormatException fex) {
						String message = "Wrong Format! - Please write the address values as integers";
						popUpOkWindow(message);
					}
				});
				fillSetPanelAddress(descr, Integer.toString(rest.getAddress().getxCoordinate()),
						Integer.toString(rest.getAddress().getyCoordinate()));
				break;
			case "special meal":
				String message = "click on one of the meals to select them as the special meal";
				popUpOkWindow(message);
				fillPanelMealSetSpecial(rest);
				jScrollPaneSpecMeal = new JScrollPane(jListMealSetSpec);
				scrollPanel.removeAll();
				scrollPanel.add(jScrollPaneSpecMeal);
				getSettingPanel().removeAll();
				getSettingPanel().add(scrollPanel, BorderLayout.CENTER);
				getSettingPanel().add(getHome_button(), BorderLayout.SOUTH);

				break;
			}
			setCurrentPanel(getSettingPanel());
		}
	}

	private class RestActionAddRemove extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String choice;
		Restaurant rest;

		public RestActionAddRemove(String choice, String desc, Restaurant rest) {
			super(choice);
			this.choice = choice;
			this.rest = rest;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		public void actionPerformed(ActionEvent e) {

			switch (choice) {

			case "add meal":

				String message = "Please select one of the following options: 1) starter, main dish and dessert (full meal), "
						+ "2) starter and main dish (half meal) or 3) main dish and dessert (half meal).";
				popUpOkWindow(message);

				mealDishDisplay.filljListStarter(rest);
				mealDishDisplay.filljListMainDish(rest);
				mealDishDisplay.filljListDessert(rest);

				mealDishDisplay.getjListStarter().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				mealDishDisplay.getjListMainDish().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				mealDishDisplay.getjListDessert().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				fillAddMealPanelValues(rest);

				break;
			case "remove meal":

				mealDishDisplay.fillPanelMealRemove(rest);
				fillAddRemovePanelScroll(mealDishDisplay.getjListMealRemove());

				fillDeleteButtonPanel();
				deleteButton.addActionListener((ActionEvent e3) -> {
					int[] toDelete = mealDishDisplay.getjListMealRemove().getSelectedIndices();
					for (int i = 0; i < toDelete.length; i++) {
						rest.getListOfMeal().remove(toDelete[i] - i);
					}
					setCurrentPanel(getWelcome_panel());
				});
				break;

			case "add starter":

				createDishButton = new Button("CREATE STARTER");
				createDishButton.addActionListener((ActionEvent e1) -> {

					dishMealFactory = FactoryProducer.getFactory("Dish");

					try {
						double price = 0;
						price = Double.parseDouble(priceDishT.getText());

						Starter starter = (Starter) dishMealFactory.getDish("Starter", nameDishT.getText(), price,
								(String) comboBox_foodType.getSelectedItem());

						rest.addStarter(starter);
						setCurrentPanel(getWelcome_panel());
					} catch (NumberFormatException e2) {
						String message2 = "Please insert a number with two digits for the price.";
						popUpOkWindow(message2);
					}

				});
				fillAddDishPanel();

				break;
			case "add main dish":

				createDishButton = new Button("CREATE MAINDISH");
				createDishButton.addActionListener((ActionEvent e1) -> {

					dishMealFactory = FactoryProducer.getFactory("Dish");

					try {
						double price = 0;
						price = Double.parseDouble(priceDishT.getText());

						MainDish mainDish = (MainDish) dishMealFactory.getDish("Starter", nameDishT.getText(), price,
								(String) comboBox_foodType.getSelectedItem());

						rest.addMainDish(mainDish);
						setCurrentPanel(getWelcome_panel());
					} catch (NumberFormatException e2) {
						String message3 = "Please insert a number with two digits for the price.";
						popUpOkWindow(message3);
					}

				});

				fillAddDishPanel();

				break;
			case "add dessert":

				createDishButton = new Button("CREATE DESSERT");
				createDishButton.addActionListener((ActionEvent e1) -> {

					dishMealFactory = FactoryProducer.getFactory("Dish");

					try {
						double price = 0;
						price = Double.parseDouble(priceDishT.getText());
						Dessert dessert = (Dessert) dishMealFactory.getDish("Starter", nameDishT.getText(), price,
								(String) comboBox_foodType.getSelectedItem());

						rest.addDessert(dessert);
						setCurrentPanel(getWelcome_panel());
					} catch (NumberFormatException e2) {
						String message4 = "Please insert a number with two digits for the price.";
						popUpOkWindow(message4);
					}
				});

				fillAddDishPanel();

				break;
			case "remove starter":

				mealDishDisplay.filljListStarter(rest);
				fillAddRemovePanelScroll(mealDishDisplay.getjListStarter());

				fillDeleteButtonPanel();
				deleteButton.addActionListener((ActionEvent e3) -> {
					int[] toDelete = mealDishDisplay.getjListStarter().getSelectedIndices();
					for (int i = 0; i < toDelete.length; i++) {
						rest.getMenu().getListOfStarter().remove(toDelete[i] - i);
					}
					setCurrentPanel(getWelcome_panel());
				});
				break;
			case "remove main dish":

				mealDishDisplay.filljListMainDish(rest);
				fillAddRemovePanelScroll(mealDishDisplay.getjListMainDish());

				fillDeleteButtonPanel();
				deleteButton.addActionListener((ActionEvent e3) -> {
					int[] toDelete = mealDishDisplay.getjListMainDish().getSelectedIndices();
					for (int i = 0; i < toDelete.length; i++) {
						rest.getMenu().getListOfMainDish().remove(toDelete[i] - i);
					}
					setCurrentPanel(getWelcome_panel());
				});
				break;
			case "remove dessert":

				mealDishDisplay.filljListDessert(rest);
				fillAddRemovePanelScroll(mealDishDisplay.getjListDessert());

				fillDeleteButtonPanel();
				deleteButton.addActionListener((ActionEvent e3) -> {
					int[] toDelete = mealDishDisplay.getjListDessert().getSelectedIndices();
					for (int i = 0; i < toDelete.length; i++) {
						rest.getMenu().getListOfDessert().remove(toDelete[i] - i);
					}
					setCurrentPanel(getWelcome_panel());
				});
				break;
			}
			addRemovePanel.add(getHome_button(), BorderLayout.SOUTH);
			setCurrentPanel(addRemovePanel);
		}
	}
}
