/**
 * 
 */
package clui;

import java.util.ArrayList;
import java.util.Arrays;

import parsers.ParseCommands;

/**
 * @author john
 *
 */
public class CommandLineTest {
	
	public static void main(String[] args) {
		CommandLine CL = CommandLine.getInstance();
		
		ArrayList<Command> command_list = ParseCommands.parseCommands("src/txtFILES/mf_commands.txt");
		for (Command cmd : command_list) {
//			System.out.println(cmd.getName() + " " + cmd.getName());
		}
		CL.setCommand_list(command_list);
		
		CL.launch();

		
//		System.out.println(c1.getDescription());
//		parseCommands();
	}

}
