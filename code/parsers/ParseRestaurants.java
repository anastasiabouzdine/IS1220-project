package parsers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import restaurantSetUp.Address;
import users.Restaurant;


public class ParseRestaurants {
	
	public static ArrayList<Restaurant> parseRestaurants(String fileName) {
		ArrayList<Restaurant> restaurant_list = new ArrayList<Restaurant>();
		String name;
		Address address; int x; int y;
		String username;
		Restaurant restaurant = null;
		
		File file = new File(fileName);
		Scanner scan = null;
		try{
			scan = new Scanner(file);
			while (scan.hasNextLine() && scan.nextLine().equals("-------")){
				name = scan.nextLine();
				x = Integer.parseInt(scan.nextLine()); 			
				y = Integer.parseInt(scan.nextLine());
				address = new Address(x, y);
				username = scan.nextLine();
				restaurant = new Restaurant(name, address, username);
				restaurant_list.add(restaurant);
			}
			scan.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return restaurant_list;
	}
	
	public static void main(String[] args) {
		ArrayList<Restaurant> restaurant_list = parseRestaurants("src/txtFILES/restaurantList.txt");
		for(Restaurant c : restaurant_list){
			System.out.println(c.getName() + " ; " + c.getAddress());
		}
	}

}
