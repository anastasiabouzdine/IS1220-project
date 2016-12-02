package Parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


import restaurantSetUp.Dessert;
import restaurantSetUp.Dish;
import restaurantSetUp.MainDish;
import restaurantSetUp.Starter;


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
	
	public static void main(String[] args) {
		ArrayList<Starter> starter_list = parseStarter("src/txtFILES/starters.txt");
		ArrayList<MainDish> mainDish_list = parseMainDish("src/txtFILES/mainDishes.txt");
		ArrayList<Dessert> dessert_list = parseDessert("src/txtFILES/dessert.txt");
		
		for(Dish c : starter_list)
			System.out.println(c.toString());
			
		for(Dish c : mainDish_list)
			System.out.println(c.toString());
				
		for(Dish c : dessert_list)
			System.out.println(c.toString());
		}
	}


