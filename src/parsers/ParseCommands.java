package parsers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import clui.Command;

/**
 * ParseCommands allows to generate an ArrayList of <code>Command</code> from a text file
 * given as input the <code>parseCommands</code> method.
 * This class is a tool to parse commands from a textfile.
 * This is especially useful when making test needing more than
 * one commands and when the thing to test is something else
 * than the actual creation of a <code>Command</code> object.
 * 
 * For the syntax of the .txt file, see "mf_commands.txt".
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 * 
 * @see #parseCommands(String)
 */

public class ParseCommands {
	
	public static ArrayList<Command> parseCommands(String fileName) {
		ArrayList<Command> command_list = new ArrayList<Command>();
		String name;
		int nb_args;
		String[] args;
		Command cmd = null;
		
		File file = new File(fileName);
		Scanner scan = null;
		try{
			scan = new Scanner(file);
			while (scan.hasNextLine() && scan.nextLine().equals("-------")){
				name = scan.nextLine();
				nb_args = scan.nextInt(); scan.nextLine();
				args = new String[nb_args];
				for(int i = 0; i < nb_args; i++){
					args[i] = scan.nextLine();
				}
				cmd = new Command(name, args);
				command_list.add(cmd);
			}
			scan.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return command_list;
	}


}
