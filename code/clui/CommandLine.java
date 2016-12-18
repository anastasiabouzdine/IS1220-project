package clui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CommandLine {
	private HashMap<String, Integer> command_hm = new HashMap<String, Integer>();
	private ArrayList<Command> command_list = new ArrayList<Command>();
	private CommandProcessor cmd_processor;
	
	
	/**************************************************/
	/* Singleton pattern */
	
	private CommandLine()
	{
		cmd_processor = CommandProcessor.getInstance();
	}
	
	private static CommandLine INSTANCE = new CommandLine();
 
	public static CommandLine getInstance()
	{	return INSTANCE;
	}
	
	/**************************************************/
	/* Methods */
	
	public String getInputInfo(String s) {
		String[] input = s.split(" ", 2);
		if (!command_hm.containsKey(input[0])) {
			return "Command not found !";
		} else if (input.length != 2) {
			return "Missing arguments !";
		} else if (input[1].charAt(0) != '<' 
				|| input[1].charAt(input[1].length() - 1) != '>') {
			return "Wrong syntax for arguments !";
		} 
		String[] args = input[1].substring(1, input[1].length()-1).split(",");
		if (args.length != command_hm.get(input[0]).intValue()) {
			return "Wrong number of arguments for this command !";
		}
		try {
			cmd_processor.processCmd(new Command(input[0], args));
		} catch (Exception e) {
			
		}
		return "\nCommand successfully executed";
	}
	
	public void launch() {
		Scanner sc; 
		String s = ""; String inputInfo = "";
		try {
			sc = new Scanner(System.in);
			System.out.println("Please enter a command");
			System.out.print(">");
			while(!s.equals("stop") || sc.hasNextLine()) {
				s = sc.nextLine();
				inputInfo = getInputInfo(s);
				System.out.println(inputInfo);
				System.out.print(">");
			}
			System.out.println("End of program");
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void executeCurrentCommand() {
		
	}
	
	/**************************************************/
	/* Getters and setters for command list */

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
