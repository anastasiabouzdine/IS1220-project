/**
 * 
 */
package tests;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import clui.Command;
import clui.CommandLine;
import parsers.ParseCommands;

/**
 * @author john
 *
 */
public class CLUIOneCommandsTest {

	@Test
	public void testAllOneCommandOfEvalFolder() {
		CommandLine CL = CommandLine.getInstance();
		ArrayList<Command> command_list = ParseCommands.parseCommands("src/txtFILES/mf_commands.txt");
		CL.setCommand_list(command_list);

		String folder_path = "./eval/oneCommand/";
		File folder = new File(folder_path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			CL.launchFromFile(folder_path + listOfFiles[i].getName());
		}

	}
	
	public static void main(String[] args) {
		
	}

}
