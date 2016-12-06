package parsers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import users.Manager;

public class ParseManagers {
	
	public static ArrayList<Manager> parseManagers(String fileName) {
		ArrayList<Manager> manager_list = new ArrayList<Manager>();
		String name;
		String surname;
		String username;
		Manager manager = null;
		
		File file = new File(fileName);
		Scanner scan = null;
		try{
			scan = new Scanner(file);
			while (scan.hasNextLine() && scan.nextLine().equals("-------")){
				name = scan.nextLine();
				surname = scan.nextLine();
				username = scan.nextLine();
				manager = new Manager(name, surname, username);
				manager_list.add(manager);
			}
			scan.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return manager_list;
	}
	
	public static void main(String[] args) {
		ArrayList<Manager> manager_list = parseManagers("src/txtFILES/managersList.txt");
		for(Manager c : manager_list){
			System.out.println(c.getID() + " : " + c.toString());
		}
	}

}


