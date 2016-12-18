package tests;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import clui.Command;
import clui.CommandLine;
import parsers.ParseCommands;

public class CLUITest {
	private CommandLine CL;

	@Before
	public void test() {
		CL = CommandLine.getInstance();
		ArrayList<Command> command_list = ParseCommands.parseCommands("src/txtFILES/mf_commands.txt");
		CL.setCommand_list(command_list);
	}

	@Test
	public void testInput0() {
		CL.launchFromFile("eval/oneCommand/testWrongCommandInput.txt");
	}

	@Test
	public void testInput1() {
		CL.launchFromFile("eval/oneCommand/testLoginInput.txt");
		;
	}

	@Test
	public void testInput2() {
		CL.launchFromFile("eval/oneCommand/testRegisterRestaurantInput.txt");
		;
	}

}
