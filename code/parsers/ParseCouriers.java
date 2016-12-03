package parsers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import restaurantSetUp.Address;
import users.Courier;



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
	
	public static void main(String[] args) {
		ArrayList<Courier> courier_list = parseCouriers("src/txtFILES/courierList.txt");
		for(Courier c : courier_list){
			System.out.println(c.getID() + " : " + c.toString());
		}
	}

}
