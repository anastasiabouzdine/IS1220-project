package core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import restaurants.Address;
import users.Customer;

/**
 * This class is a tool to parse customers from a textfile.
 * This is especially useful when making test needing more than
 * one customer and when the thing to test is something else
 * than the actual creation of a Customer object.
 * 
 * For the syntax of the txt file, see "customersList.txt"
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 *
 */

public class ParseCustomers {
	
	public static ArrayList<Customer> parseCustomers(String fileName) {
		ArrayList<Customer> cust_list = new ArrayList<Customer>();
		String name;
		String surname;
		Address address; int x; int y;
		String phoneNumber;
		String email;
		String username;
		Customer customer = null;
		
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
				email = scan.nextLine();
				phoneNumber = scan.nextLine();
				username = scan.nextLine();
				customer = new Customer(name, surname, address, phoneNumber, email, username);
				cust_list.add(customer);
			}
			scan.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return cust_list;
	}
	
	public static void main(String[] args) {
		ArrayList<Customer> cust_list = parseCustomers("src/txtFILES/customersList.txt");
		for(Customer c : cust_list){
			System.out.println(c.getID() + " : " + c.toString());
		}
	}

}
