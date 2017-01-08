package clui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import parsers.ParseCommands;

/**
 * The <code>CommandLine</code> class allows to interact with the user and
 * parse input from the console or a file into <code>Command</code> objects.
 * 
 * Those commands will then be processed by the command processor.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public class CommandLine {
	private HashMap<String, Integer> command_hm = new HashMap<String, Integer>();
	private ArrayList<Command> command_list = new ArrayList<Command>();
	private CommandProcessor cmd_processor;


	/**************************************************/
	/* Singleton pattern */

	private CommandLine()
	{
		cmd_processor = CommandProcessor.getInstance();

		command_list = ParseCommands.parseCommands("src/txtFILES/mf_commands.txt");
		for(Command cmd : command_list) {
			command_hm.put(cmd.getName(), cmd.getNb_args());
		}
	}

	private static CommandLine INSTANCE = new CommandLine();

	public static CommandLine getInstance()
	{	return INSTANCE;
	}

	/**************************************************/
	/* Methods */
	
	/**
	 * This function is in charge of parsing the input of the user into
	 * a command object and detecting if input is in the correct form,
	 * if no problems appear, it will pass the command object 
	 * to the command processor.
	 * 
	 * @param s		a string of the input command of the user
	 * @return	a string containing information about the success of the parsing
	 */
	public String getInputInfoAndProcessCmd(String s) {
		if (s.equals("help")) { 
			printListOfCommands();
			return s + " command executed.";
		}
		String[] input = s.trim().split(" ");
		if (input[0].equals("runtest")) {

			launchFromFile("./eval/" + (input[1].replaceAll("\"+","")));
			return "";
		}
		if (input.length == 0) { return ""; }
		String command = input[0];
		if (!command_hm.containsKey(command)) {
			return "Command not found !";
		}
		String[] args = new String[0];
		// special case as the showTotalProfit is used twice with different number of arguments
		if (input[0].equals("showTotalProfit") && input.length == 1) {
			args = new String[]{null, null};
		}
		if (input.length > 1) {
			args = new String[input.length - 1];
			for(int i = 0; i < args.length; i++){
				if (input[i+1].charAt(0) != '"' || input[i+1].charAt(input[i+1].length()-1) != '"'){
					return "Wrong syntax for arguments !";
				}
				args[i] = input[i+1].substring(1, input[i+1].length()-1);
			}
		}

		if (args.length != command_hm.get(command).intValue()){
			return "Wrong number of arguments for this command !";
		}
		try {
			cmd_processor.processCmd(new Command(command, args));
		} catch (Exception e) {
			e.getMessage();
		}
		return input[0] + " command executed.";
	}

	
	/**
	 * This function launches the command line interface
	 * directly in the console and passes input to the 
	 * <code>getInputInfoAndProcessCmd</code> method.
	 */
	public void launchFromInput() {
		Scanner sc; 
		String inputInfo, s = "";
		try {
			sc = new Scanner(System.in);
			System.out.println("MyFoodora application --- IS1220\nPlease enter a command"
					+ ", type \"help\" to get commands info or \"stop\" to end the program !");
			System.out.print(">");
			while(!s.equals("stop") && sc.hasNextLine()) {
				s = sc.nextLine();
				inputInfo = getInputInfoAndProcessCmd(s);
				System.out.println("[CL-info] " + inputInfo);
				System.out.print(">");
			} 
			System.out.println("End of program");
			sc.close();
		} catch (Exception e) {
			System.out.println("! An error occured, please retry following specifications !");;
		}
	}

	/**
	 * Executes commands in the same fashion as <code>launchFromInput</code>
	 * but this time the commands are parsed from a txt file.
	 * 
	 * @param filename	the name of the file containing the list of commands
	 */
	public void launchFromFile(String filename) {
		File file = new File(filename);
		Scanner scan = null;
		String s, inputInfo;
		try {
			try {

				System.out.println("[CL-info] Reading commands from " + filename);
				scan = new Scanner(file);
				while (scan.hasNextLine()){
					s = scan.nextLine();
					if (!s.isEmpty()) {
						inputInfo = getInputInfoAndProcessCmd(s);
						System.out.println("[CL-info] " + inputInfo);
					}
				}
				System.out.println("[CL-info] End of commands from " + filename + "\n");
			} catch (FileNotFoundException e) {
				System.out.println("! Please enter a valid filename !");
			}
		} catch (Exception e) {
			System.out.println("! An error occured, please retry following specifications !");;
		}
	}

	/**************************************************/
	/* Printers, getters and setters for command list */

	/**
	 * Prints the list of commands.
	 */
	public void printListOfCommands() {
		for (Command c : command_list) {
			System.out.println(c.getDescription());
		}
	}
	/**
	 * @return the command_list
	 */
	public ArrayList<Command> getCommand_list() {
		return command_list;
	}

	/**
	 * @param command_list the command_list to set
	 */
	public void setCommand_list(ArrayList<Command> command_list) {
		this.command_list = command_list;
		for(Command cmd : command_list) {
			command_hm.put(cmd.getName(), cmd.getNb_args());
		}
	}


}
