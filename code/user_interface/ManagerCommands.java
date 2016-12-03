package user_interface;

import java.util.Scanner;

import users.Manager;

public class ManagerCommands {
	private static Scanner in = null;
	
	public static Manager createAccount(){
		System.out.println("Creating manager account...");
		in = new Scanner(System.in);
		System.out.print("Enter name : ");
		String name = in.next();
		System.out.print("Enter surname : ");
		String surname = in.next();		
		System.out.print("Enter username : ");
		String username = in.next();
		System.out.println("Done !\n");
		Manager m = new Manager(name, surname, username);
		in.close();
		return m;
	}

}
