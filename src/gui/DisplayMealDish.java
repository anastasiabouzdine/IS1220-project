package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import restaurantSetUp.Dessert;
import restaurantSetUp.Dish;
import restaurantSetUp.FullMeal;
import restaurantSetUp.HalfMeal;
import restaurantSetUp.MainDish;
import restaurantSetUp.Meal;
import restaurantSetUp.Starter;
import users.Restaurant;

/**
 * The class <code>DisplayMealDish</code> provides functionality to show meals and lists 
 * on a scroll panel. 
 * 
 * It is used by both <code>CustomerFrame</code> and <code>RestaurantFrame</code>.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public class DisplayMealDish {

	private JList<Meal> jListMealShow = new JList<>();
	private JList<Meal> jListMealRemove = new JList<>();
	private JList<Starter> jListStarter = new JList<>();
	private JList<MainDish> jListMainDish = new JList<>();
	private JList<Dessert> jListDessert = new JList<>();
	private JList<Dish> jListAllDish = new JList<>();

	private JPanel mealLabelPanel = new JPanel(new GridLayout(0, 1));
	private JPanel mealValuePanel = new JPanel(new GridLayout(0, 1));
	private JPanel dishPanel = new JPanel();

	private Button goBack_button;

	private JFormattedTextField starterDesc = new JFormattedTextField("Starter: ");
	private JFormattedTextField mainDishDesc = new JFormattedTextField("Main Dish: ");
	private JFormattedTextField dessertDesc = new JFormattedTextField("Dessert: ");
	private JFormattedTextField priceDesc = new JFormattedTextField("Total Price: ");
	private JFormattedTextField addDishDesc = new JFormattedTextField("2nd Choice: ");

	private JFormattedTextField starterT = new JFormattedTextField();
	private JFormattedTextField mainDishT = new JFormattedTextField();
	private JFormattedTextField dessertT = new JFormattedTextField();
	private JFormattedTextField priceT = new JFormattedTextField();
	private JFormattedTextField addDishT = new JFormattedTextField();

	private JMenu dishMenu = new JMenu("menu");

	/**
	 * Constructor
	 */
	public DisplayMealDish() {
		super();
	}

	/**
	 * display panel that returns a JPanel including a 
	 * detailed description of a meal of a restaurant.
	 * 
	 * @param meal the meal that is going to by
	 * @param rest the restaurant that offers that meal
	 * @return JPanel panel including description of meal
	 */
	public JPanel display(Meal meal, Restaurant rest) {

		if (meal instanceof FullMeal) {
			FullMeal fullMeal = (FullMeal) meal;
			String starter = fullMeal.getListOfDish().get(0).getName() + " of type "
					+ fullMeal.getListOfDish().get(0).getType();
			String mainDish = fullMeal.getListOfDish().get(1).getName() + " of type "
					+ fullMeal.getListOfDish().get(1).getType();
			String dessert = fullMeal.getListOfDish().get(2).getName() + " of type "
					+ fullMeal.getListOfDish().get(2).getType();
			String addInfo = rest.isMealSpecial(fullMeal) ? "$ = special offer" : "$";
			String price = rest.getPrice(fullMeal) + addInfo;

			starterT.setColumns(30);
			mainDishT.setColumns(30);
			dessertT.setColumns(30);
			priceT.setColumns(30);

			starterT.setText(starter);
			mainDishT.setText(mainDish);
			dessertT.setText(dessert);
			priceT.setText(price);

			mealLabelPanel.removeAll();
			mealLabelPanel.add(starterDesc);
			mealLabelPanel.add(mainDishDesc);
			mealLabelPanel.add(dessertDesc);
			mealLabelPanel.add(priceDesc);

			mealValuePanel.removeAll();
			mealValuePanel.add(starterT);
			mealValuePanel.add(mainDishT);
			mealValuePanel.add(dessertT);
			mealValuePanel.add(priceT);

			jListDessert.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		} else {

			HalfMeal halfMeal = (HalfMeal) meal;

			String mainDish = halfMeal.getListOfDish().get(0).getName() + " of type "
					+ halfMeal.getListOfDish().get(0).getType();
			String addDish = halfMeal.getListOfDish().get(1).getName() + " of type "
					+ halfMeal.getListOfDish().get(1).getType();
			String addInfo = rest.isMealSpecial(halfMeal) ? "$ which is the special offer." : "$.";
			String price = rest.getPrice(halfMeal) + addInfo;

			mainDishT.setText(mainDish);
			addDishT.setText(addDish);
			priceT.setText(price);

			mealLabelPanel.removeAll();
			mealLabelPanel.add(mainDishDesc);
			mealLabelPanel.add(addDishDesc);
			mealLabelPanel.add(priceDesc);

			mealValuePanel.removeAll();
			mealValuePanel.add(mainDishT);
			mealValuePanel.add(addDishT);
			mealValuePanel.add(priceT);

		}
		dishPanel.removeAll();
		dishPanel.add(mealLabelPanel, BorderLayout.CENTER);
		dishPanel.add(mealValuePanel, BorderLayout.LINE_END);
		dishPanel.add(goBack_button, BorderLayout.SOUTH);

		return dishPanel;
	}

	/**
	 * used to display the dishes of the restaurant
	 * 
	 * @param rest restaurant whose dishes are going to be displayed
	 */
	public void setTextFields(Restaurant rest) {

		dishPanel.setBorder(BorderFactory.createEmptyBorder());

		starterDesc.setEditable(false);
		mainDishDesc.setEditable(false);
		dessertDesc.setEditable(false);
		addDishDesc.setEditable(false);
		priceDesc.setEditable(false);

		starterT.setEditable(false);
		mainDishT.setEditable(false);
		dessertT.setEditable(false);
		addDishT.setEditable(false);
		priceT.setEditable(false);
	}

	/**
	 * used to initialize a jList of the restaurant's meals.
	 * 
	 * @param rest restaurant that is chosen
	 */
	public void fillPanelMealRemove(Restaurant rest) {
		DefaultListModel<Meal> model = new DefaultListModel<Meal>();
		for (Meal meal : rest.getListOfMeal()) {
			model.addElement(meal);
		}
		jListMealRemove.setModel(model);
	}

	/**
	 * used to show all the meals of a restaurant in a jlist.
	 * 
	 * @param rest restaurant that is chosen
	 */
	public void fillPanelMealShow(Restaurant rest) {
		DefaultListModel<Meal> model = new DefaultListModel<Meal>();
		for (Meal meal : rest.getListOfMeal()) {
			model.addElement(meal);
		}
		jListMealShow.setModel(model);
	}

	/**
	 * used to make a jlist with all starters of a restaurant.
	 * 
	 * @param rest restaurant that is chosen
	 */
	public void filljListStarter(Restaurant rest) {
		jListStarter.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		DefaultListModel<Starter> model = new DefaultListModel<Starter>();
		for (Starter start : rest.getMenu().getListOfStarter()) {
			model.addElement(start);
		}
		jListStarter.setModel(model);
		jListMealShow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/**
	 * used to make a jlist with all main dishes of a restaurant.
	 * 
	 * @param rest restaurant that is chosen
	 */
	public void filljListMainDish(Restaurant rest) {
		jListMainDish.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		DefaultListModel<MainDish> model = new DefaultListModel<MainDish>();
		for (MainDish mainDish : rest.getMenu().getListOfMainDish()) {
			model.addElement(mainDish);
		}
		jListMainDish.setModel(model);
		jListMealShow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/**
	 * used to make a jlist with all desserts of a restaurant.
	 * 
	 * @param rest restaurant that is chosen
	 */
	public void filljListDessert(Restaurant rest) {
		jListDessert.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		DefaultListModel<Dessert> model = new DefaultListModel<Dessert>();
		for (Dessert dessert : rest.getMenu().getListOfDessert()) {
			model.addElement(dessert);
		}
		jListDessert.setModel(model);
		jListMealShow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/**
	 * used to make a jlist with all dishes of a restaurant.
	 * 
	 * @param rest restaurant that is chosen
	 * @return JlistAllDish list of all dishes of a restaurant
	 */
	public JList<Dish> filljListAllDishes(Restaurant rest) {
		DefaultListModel<Dish> model = new DefaultListModel<Dish>();

		for (Starter start : rest.getMenu().getListOfStarter()) {
			model.addElement(start);
		}
		for (MainDish mainDish : rest.getMenu().getListOfMainDish()) {
			model.addElement(mainDish);
		}
		for (Dessert dessert : rest.getMenu().getListOfDessert()) {
			model.addElement(dessert);
		}
		jListAllDish.setModel(model);
		jListAllDish.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return jListAllDish;
	}

	/**
	 * @return the goBack_button
	 */
	public Button getGoBack_button() {
		return goBack_button;
	}

	/**
	 * @return the dishMenu
	 */
	public JMenu getDishMenu() {
		return dishMenu;
	}

	/**
	 * @return the jListMeal
	 */
	public JList<Meal> getjListMealShow() {
		return jListMealShow;
	}

	/**
	 * @return the jListMainDish
	 */
	public JList<MainDish> getjListMainDish() {
		return jListMainDish;
	}

	/**
	 * @return the jListDessert
	 */
	public JList<Dessert> getjListDessert() {
		return jListDessert;
	}

	/**
	 * @return the jListStarter
	 */
	public JList<Starter> getjListStarter() {
		return jListStarter;
	}

	/**
	 * @return the jListMealRemove
	 */
	public JList<Meal> getjListMealRemove() {
		return jListMealRemove;
	}

	/**
	 * @param goBack_button
	 *            the goBack_button to set
	 */
	public void setGoBack_button(Button goBack_button) {
		this.goBack_button = goBack_button;
	}

	/**
	 * @return the jListAllDish
	 */
	public JList<Dish> getjListAllDish() {
		return jListAllDish;
	}
}
