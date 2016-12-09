package parsers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import users.Manager;

/**
* ParseManagers allows to generate an ArrayList of <code>Manager</code> from a text file
* given as input the <code>parseManagers</code> method.
* This class is a tool to parse managers from a textfile.
* This is especially useful when making test needing more than
* one manager and when the thing to test is something else
* than the actual creation of a <code>Manager</code> object.
* 
* For the syntax of the txt file, see "managersList.txt".
* 
* @author John de Wasseige
* @author Patrick von Platen
* 
* @see #parseManagers(String)
*/
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
	

}


