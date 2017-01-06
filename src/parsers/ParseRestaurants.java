package parsers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import users.Address;
import users.Restaurant;

/**
* ParseRestaurants allows to generate an ArrayList of <code>Restaurant</code> from a text file
* given as input the <code>parseRestaurants</code> method.
* This class is a tool to parse restaurants from a textfile.
* This is especially useful when making test needing more than
* one restaurant and when the thing to test is something else
* than the actual creation of a <code>Restaurant</code> object.
* 
* For the syntax of the txt file, see "restaurantList.txt".
* 
* @author John de Wasseige
* @author Patrick von Platen
* 
* @see parseRestaurants
*/
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
				restaurant = new Restaurant(name, address, username, "password");
				restaurant_list.add(restaurant);
			}
			scan.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return restaurant_list;
	}

}
