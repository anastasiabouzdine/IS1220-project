package parsers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


import restaurantSetUp.Dessert;
import restaurantSetUp.MainDish;
import restaurantSetUp.Starter;

/**
* ParseDishes allows to generate multiples ArrayLists of <code>Dish</code>.
* In detail, an ArrayList of <code>Starter</code>  from a text file
* given as input the <code>parseStarter</code> method,
* an ArrayList of <code>MainDish</code>  from a text file
* given as input the <code>parseMainDish</code> method and
* an ArrayList of <code>Dessert</code>  from a text file
* given as input the <code>parseDessert</code> method.
* 
* This is especially useful when making test needing more than
* one dish and when the thing to test is something else
* than the actual creation of a <code>Dish</code> object.
* 
* For the syntax of the txt file, see "starters.txt",
* "mainDishes.txt" and "desserts.txt".
* 
* @author John de Wasseige
* @author Patrick von Platen
* 
* @see #parseDessert(String)
* @see #parseMainDish(String)
* @see #parseStarter(String)
*/
public class ParseDishes {
	
	public static ArrayList<Starter> parseStarter(String fileName) {
		ArrayList<Starter> starter_list = new ArrayList<Starter>();
		String name;
		double price;
		String type;
		
		File file = new File(fileName);
		Scanner scan = null;
		try{
			scan = new Scanner(file);
			while (scan.hasNextLine() && scan.nextLine().equals("-------")){
				name = scan.nextLine();
				price = Double.parseDouble(scan.nextLine()); 
				type = scan.nextLine();
				
				starter_list.add(new Starter(name, price, type));
			}
			scan.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return starter_list;
	}
	
	public static ArrayList<MainDish> parseMainDish(String fileName) {
		ArrayList<MainDish> mainDish_list = new ArrayList<MainDish>();
		String name;
		double price;
		String type;
		
		File file = new File(fileName);
		Scanner scan = null;
		try{
			scan = new Scanner(file);
			while (scan.hasNextLine() && scan.nextLine().equals("-------")){
				name = scan.nextLine();
				price = Double.parseDouble(scan.nextLine());  
				type = scan.nextLine();
				
				mainDish_list.add(new MainDish(name, price, type));
			}
			scan.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return mainDish_list;
	}
	
	public static ArrayList<Dessert> parseDessert(String fileName) {
		ArrayList<Dessert> dessert_list = new ArrayList<Dessert>();
		String name;
		double price;
		String type;
		
		File file = new File(fileName);
		Scanner scan = null;
		try{
			scan = new Scanner(file);
			while (scan.hasNextLine() && scan.nextLine().equals("-------")){
				name = scan.nextLine();
				price = Double.parseDouble(scan.nextLine()); 
				type = scan.nextLine();
				
				dessert_list.add(new Dessert(name, price, type));
			}
			scan.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return dessert_list;
	}
	
}
