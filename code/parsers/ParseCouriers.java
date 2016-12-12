package parsers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import users.Address;
import users.Courier;

/**
 * ParseCouriers allows to generate an ArrayList of <code>Courier</code> from a text file
 * given as input the <code>parseCouriers</code> method.
 * This class is a tool to parse couriers from a textfile.
 * This is especially useful when making test needing more than
 * one courier and when the thing to test is something else
 * than the actual creation of a <code>Courier</code> object.
 * 
 * For the syntax of the .txt file, see "couriersList.txt".
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 * 
 * @see #parseCouriers(String)
 */

public class ParseCouriers {
	
	public static ArrayList<Courier> parseCouriers(String fileName) {
		ArrayList<Courier> courier_list = new ArrayList<Courier>();
		String name;
		String surname;
		Address address; int x; int y;
		String phoneNumber;
		String username;
		Courier courier = null;
		
		File file = new File(fileName);
		Scanner scan = null;
		try{
			scan = new Scanner(file);
			while (scan.hasNextLine() && scan.nextLine().equals("-------")){
				name = scan.nextLine();
				surname = scan.nextLine();
				x = Integer.parseInt(scan.nextLine()); 			
				y = Integer.parseInt(scan.nextLine());
				address = new Address(x, y);
				phoneNumber = scan.nextLine();
				username = scan.nextLine();
				courier = new Courier(name, surname, address, phoneNumber, username);
				courier_list.add(courier);
			}
			scan.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return courier_list;
	}


}
