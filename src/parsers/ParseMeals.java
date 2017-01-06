package parsers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import restaurantSetUp.Dessert;
import restaurantSetUp.FullMeal;
import restaurantSetUp.HalfMeal;
import restaurantSetUp.MainDish;
import restaurantSetUp.Starter;

/**
* ParseMeals allows to generate an ArrayList of <code>Meal</code> from a text file
* given as input the <code>parseFullMeals</code> and <code>parseHalfMeals</code> methods.
* This class is a tool to parse meals from a textfile.
* This is especially useful when making test needing more than
* one meal and when the thing to test is something else
* than the actual creation of <code>FullMeal</code> and <code>HalfMeal</code> objects.
* 
* For the syntax of the txt file, see "halfMeals.txt" and "fullMeals.txt".
* 
* @author John de Wasseige
* @author Patrick von Platen
* 
* @see #parseFullMeals(String)
* @see #parseHalfMeals(String)
*/
public class ParseMeals {
	

	public static ArrayList<FullMeal> parseFullMeals(String fileName){
		
		ArrayList<Starter> starter_list = ParseDishes.parseStarter("src/txtFILES/starters.txt");
		ArrayList<MainDish> mainDish_list = ParseDishes.parseMainDish("src/txtFILES/mainDishes.txt");
		ArrayList<Dessert> dessert_list = ParseDishes.parseDessert("src/txtFILES/desserts.txt");
		int l = Math.min(Math.min(starter_list.size(), mainDish_list.size()), dessert_list.size());
		ArrayList<FullMeal> fullMeals_list = new ArrayList<FullMeal>();
		
		String name;
		int i = 0;
		
		File file = new File(fileName);
		Scanner scan = null;
		try{
			scan = new Scanner(file);
			while (scan.hasNextLine() && scan.nextLine().equals("-------") && i < l){
				name = scan.nextLine();
				fullMeals_list.add(new FullMeal(name, starter_list.get(i), mainDish_list.get(i), dessert_list.get(i)));
				i++;
			}
			scan.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	
		return fullMeals_list;
	}
	
public static ArrayList<HalfMeal> parseHalfMeals(String fileName){
		
		ArrayList<Starter> starter_list = ParseDishes.parseStarter("src/txtFILES/starters.txt");
		ArrayList<MainDish> mainDish_list = ParseDishes.parseMainDish("src/txtFILES/mainDishes.txt");
		
		int l = Math.min(starter_list.size(), mainDish_list.size());

		
		ArrayList<HalfMeal> halfMeals_list = new ArrayList<HalfMeal>();
		
		String name;
		int i = 0;
		
		File file = new File(fileName);
		Scanner scan = null;
		try{
			scan = new Scanner(file);
			while (scan.hasNextLine() && scan.nextLine().equals("-------") && i<l){
				name = scan.nextLine();
				halfMeals_list.add(new HalfMeal(name, mainDish_list.get(i), starter_list.get(i)));
				i++;
			}
			scan.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	
		return halfMeals_list;
	}


}
