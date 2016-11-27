package user_interface;

import java.util.Scanner;

public class CLUI {
	
	private static Scanner in = null;
	 
	public static void launch(){
		in = new Scanner(System.in);
		String hello = "MyFoodora CLUI -> type \"help\" for a list of commands or \"stop\" to quit\n--> ";
		System.out.println("START OF PROGRAM");
		String s = null; 
		String[] command_list = getListOfCommands();
		do {
			System.out.print(hello);
			s = in.next();
			if (s.equals(command_list[0])) {
				log_in_command();
			} else if (s.equals(command_list[1])) {
				create_account_command();
			} else if (s.equals(command_list[3])) {
				help_command(command_list);
			} else if (!s.equals(command_list[2])) {
				System.out.println("! NOT A COMMAND !\n");
			} 
		} while (!s.equals("stop")) ;
		System.out.println("END OF PROGRAM");
		in.close();
	}
	
	public static void log_in_command() {
		System.out.println("YOU R LOGGED IN MALAKA\n");
	}
	
	public static void create_account_command() {
		System.out.println("plz give 10 euros to create an account\n");
	}
	
	public static void help_command(String[] command_list) {
		String[] descriptions = getDescriptionOfCommands();
		int count = 0;
		System.out.println("--- the following commands are available ---\n");
		for (String d : descriptions) {
			System.out.println(command_list[count++] + ": " + d);
		}
		System.out.println();
	}
	
	public static String[] getListOfCommands() {
		String[] list = new String[4];
		list[0] = "log_in";
		list[1] = "create_account";
		list[2] = "stop";
		list[3] = "help";
		return list;
	}
	
	public static String[] getDescriptionOfCommands() {
		String[] list = new String[3];
		list[0] = "TO LOG IN THE SYSTEM";
		list[1] = "TO CREATE AN ACCOUNT";
		list[2] = "TO STOP THE PROGRAM";
		return list;
	}
	
	public static void main(String[] args) {
		launch();
	}

}
