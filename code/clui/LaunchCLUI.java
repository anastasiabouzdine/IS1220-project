/**
 * 
 */
package clui;

import java.util.ArrayList;

import parsers.ParseCommands;

/**
 * @author john
 *
 */
public class LaunchCLUI {
	
	public static void main(String[] args) {
		CommandLine CL = CommandLine.getInstance();
		
		ArrayList<Command> command_list = ParseCommands.parseCommands("src/txtFILES/mf_commands.txt");

		CL.setCommand_list(command_list);
		
		CL.launchFromInput();
	}

}
