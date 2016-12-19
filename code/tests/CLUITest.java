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
	public void testInputWrongCommand() {
		CL.launchFromFile("eval/oneCommand/testWrongCommandInput.txt");
	}
	@Test
	public void testInputLogin() {
		CL.launchFromFile("eval/oneCommand/testLoginInput.txt");
	}
	@Test
	public void testInputRegisterRestaurant() {
		CL.launchFromFile("eval/oneCommand/testRegisterRestaurantInput.txt");
	}
	@Test
	public void testInputAddDishRestaurantMenu() {
		CL.launchFromFile("eval/oneCommand/testAddDishRestaurantMenu.txt");
	}
	@Test
	public void testInputShowMenuItem() {
		CL.launchFromFile("eval/oneCommand/testShowMenuItem.txt");
	}
	

}
