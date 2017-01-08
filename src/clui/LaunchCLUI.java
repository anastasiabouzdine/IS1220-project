/**
 * 
 */
package clui;

import java.util.ArrayList;

import parsers.ParseCommands;

/**
 * Launches the command line user interface.
 * 
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public class LaunchCLUI {
	
	public static void main(String[] args) {
		CommandLine CL = CommandLine.getInstance();
		
		ArrayList<Command> command_list = ParseCommands.parseCommands("src/txtFILES/mf_commands.txt");

		CL.setCommand_list(command_list);
		
		CL.launchFromInput();
	}

}
