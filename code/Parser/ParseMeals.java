package Parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import restaurantSetUp.Dessert;
import restaurantSetUp.FullMeal;
import restaurantSetUp.HalfMeal;
import restaurantSetUp.MainDish;
import restaurantSetUp.Starter;


public class ParseMeals {
	
	public static ArrayList<FullMeal> parseFullMeals(String fileName){
		
		ArrayList<Starter> starter_list = ParseDishes.parseStarter("src/txtFILES/starters.txt");
		ArrayList<MainDish> mainDish_list = ParseDishes.parseMainDish("src/txtFILES/mainDishes.txt");
		ArrayList<Dessert> dessert_list = ParseDishes.parseDessert("src/txtFILES/dessert.txt");
		
		ArrayList<FullMeal> fullMeals_list = new ArrayList<FullMeal>();
		
		String name;
		int i = 0;
		
		File file = new File(fileName);
		Scanner scan = null;
		try{
			scan = new Scanner(file);
			while (scan.hasNextLine() && scan.nextLine().equals("-------")){
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
		
		
		ArrayList<HalfMeal> halfMeals_list = new ArrayList<HalfMeal>();
		
		String name;
		int i = 0;
		
		File file = new File(fileName);
		Scanner scan = null;
		try{
			scan = new Scanner(file);
			while (scan.hasNextLine() && scan.nextLine().equals("-------")){
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

public static void main(String[] args) {
	ArrayList<FullMeal> fullMeals_list = parseFullMeals("src/txtFILES/fullMeals.txt");
	ArrayList<HalfMeal> halfMeals_list = parseHalfMeals("src/txtFILES/halfMeals.txt");
	
	for(FullMeal c : fullMeals_list)
		System.out.println(c.toString());
		
	for(HalfMeal c : halfMeals_list)
		System.out.println(c.toString());
}

}
