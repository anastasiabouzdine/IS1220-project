package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.sun.xml.internal.bind.v2.TODO;

import clui.Command;
import users.User;

public abstract class GUIUserFrame {
	
	//Frames
	private JFrame frame;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu settingMenu = new JMenu("Settings");
	private JMenu infoMenu = new JMenu("Information");
	
	private JPanel infoPanel = new JPanel();
	private JPanel infoSubPanel = new JPanel();
	private JTextField infoTextFieldDesc = new JTextField();
	private JTextField infoTextFieldValue = new JTextField();
	
	private JPanel settingPanel = new JPanel();
	private JPanel setSubPanel = new JPanel();
	private JPanel setButtonPanel = new JPanel();
	private JTextField setTextFieldDesc = new JTextField();
	private JTextField setTextFieldValue = new JTextField();
	
	//Panels
	JPanel welcome_panel = new JPanel();
	JPanel welcome_button_panel = new JPanel();
	JPanel welcome_message_panel = new JPanel();
	
	//Buttons
	JButton logOut_button = new JButton("LOG OUT");
	JButton home_button = new JButton("GO HOME");
	JButton save_button = new JButton("SAVE");
	private int currentSettingShow= 0;
	
		
	
	public abstract GUIUserFrame getInstance(User user);
	
	public void setGUIStartFrameVisible(){
		GUIStartFrame.getFrame().setVisible(true);
	}
	
	public void open(final int xLocation, final int yLocation, final int width, final int height) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	frame.setBounds(xLocation, yLocation, width, height);
                frame.setVisible(true);
            }
        });
    }
	
	/**************************************************/
	//fill panels
	public void fillInfoPanel(){
		infoPanel.setBorder(BorderFactory.createTitledBorder("Information"));
		infoPanel.setLayout(new BorderLayout());
		infoPanel.setBackground(Color.CYAN);
		infoTextFieldDesc.setEditable(false);
		infoTextFieldValue.setEditable(false);
		getInfoSubPanel().setBackground(Color.WHITE);
		infoPanel.add(getInfoSubPanel());
		frame.add(infoPanel);
	}
	
	public void fillInfoPanel(String descr, String value){
		infoPanel.removeAll();
		infoTextFieldDesc.setText(descr);
		infoTextFieldValue.setText(value);
		getInfoSubPanel().add(infoTextFieldDesc, BorderLayout.CENTER);
		getInfoSubPanel().add(infoTextFieldValue, BorderLayout.CENTER);
		infoPanel.add(getInfoSubPanel());
		infoPanel.add(home_button, BorderLayout.SOUTH);
		
	}
	
	private void fillInfoMenuWithFunction(User user) {
		infoMenu.add(new UserActionInfoBasic("name", "show current name", user));
		infoMenu.add(new UserActionInfoBasic("username", "show current username", user));
		infoMenu.add(new UserActionInfoBasic("ID", "show current ID", user));
		infoMenu.add(new UserActionInfoBasic("password", "show current password", user));
	}

	public void fillSetPanel() {
		settingPanel.setBorder(BorderFactory.createTitledBorder("Settings"));
		settingPanel.setLayout(new BorderLayout());
		settingPanel.setBackground(Color.GREEN);
		setTextFieldDesc.setEditable(false);
		setSubPanel.setBackground(Color.WHITE);
		setTextFieldValue.setColumns(12);
		setSubPanel.add(setTextFieldDesc, BorderLayout.CENTER);
		setSubPanel.add(setTextFieldValue, BorderLayout.CENTER);
		settingPanel.add(setSubPanel);
		settingPanel.add(setButtonPanel,BorderLayout.SOUTH);
		frame.add(settingPanel);
	}

	public void fillSetPanel(String descr, String value) {
		setTextFieldDesc.setText(descr);
		setTextFieldValue.setText(value);
		setButtonPanel.add(home_button);
		setButtonPanel.add(save_button);
	}

	private void fillSetMenuWithFunction(User user) {
		getSettingMenu().add(new UserActionSettingBasic("name", "change current name", user));
		getSettingMenu().add(new UserActionSettingBasic("username", "change current username", user));
		getSettingMenu().add(new UserActionSettingBasic("password", "change current password", user));
	}
	
	public void fillAndSetMenuBar(User user){
		fillInfoMenuWithFunction(user);
		fillSetMenuWithFunction(user);
		menuBar.add(infoMenu);
		menuBar.add(getSettingMenu());
		frame.add(menuBar);
		frame.setJMenuBar(menuBar);
	}
	
	public void fillWelcomePanel(User user, Color color1, Color color2, String welcomeText, String programText) {
		welcome_panel.setBackground(color1);
		welcome_panel.setBorder(BorderFactory.createTitledBorder(welcomeText));
		welcome_panel.setLayout(new BorderLayout());

		welcome_button_panel.setBackground(color1);
		
		
		JTextField program_name = new JTextField(31);
	    Font font = new Font("SansSerif", Font.BOLD, 12);
	    program_name.setFont(font);
	    program_name.setBackground(color1);
	    program_name.setText(programText);
	    program_name.setEditable(false);
	    welcome_message_panel.setBackground(color1);
		welcome_message_panel.add(program_name);
		
	    JTextArea welcome_text = new JTextArea();
	    JScrollPane welcome_scrollPane = new JScrollPane();
	    welcome_scrollPane.setViewportView(welcome_text);
	    welcome_text.setText("Welcome " + user.getName());
	    welcome_text.setBackground(color2);

		welcome_panel.add(welcome_message_panel, BorderLayout.NORTH);
		welcome_panel.add(welcome_scrollPane, BorderLayout.CENTER);
		welcome_panel.add(welcome_button_panel, BorderLayout.SOUTH);
		
		//Buttons
		welcome_button_panel.add(logOut_button, BorderLayout.CENTER);
		home_button.addActionListener((ActionEvent e)->{
			setCurrentPanel(welcome_panel);
		});
		
		frame.add(welcome_panel);
   	}
	
	/**************************************************/
	//help functions
	public void setCurrentPanel(JPanel panel) {
	    getFrame().setContentPane(panel);  	
	    getFrame().setVisible(true);
	}	
	
	/**************************************************/
	//Initialize functions
	public void initGUI(User user, Color color1, Color color2, String welcomeText, String programText) {
		setFrame(new JFrame("Welcome " + user.getName()));	
		fillWelcomePanel(user, color1, color2, welcomeText, programText);
		fillInfoPanel();
		fillSetPanel();
		fillAndSetMenuBar(user);
		setCurrentPanel(welcome_panel);
		logOut_button.addActionListener((ActionEvent e) -> {
			frame.setVisible(false);
			GUIStartFrame.setCurrentLogInUser(null);
			GUIStartFrame.getInstance().goToHomePage();
			GUIStartFrame.getFrame().setVisible(true);
		});
		
		save_button.addActionListener((ActionEvent e) -> {
			String value = setTextFieldValue.getText();
			String message = "Changes could not be saved!";
			if(currentSettingShow == 1) {
				user.setName(value);
				message = "New name succesfully saved!";
			}
			else if(currentSettingShow == 2){
				GUIStartFrame.getCore().setUsername(user, value);
				message = "New username succesfully saved!";
			}
			else if(currentSettingShow == 3){
				user.setPassword(value);
				message = "New password succesfully saved!";
			}
			//TODO 
//			else if(currentSettingShow == 4){
//				user.setSurname(value);
//				message = "New surname succesfully saved!";
//			}
//			else if(currentSettingShow == 5){
//				String[] valueAddress = {value};
//				try{
////					GUIStartFrame.getCmd_processor().processCmd(new Command("setAddress", valueAddress));
////					message = "New address succesfully saved!";
//				}catch(NumberFormatException fex){
//            		message = "Wrong Format! - Please write the address in the format \"xCoord,yCoord\"";
//            	}
//			}
//			else if(currentSettingShow == 6){
//				user.setPhoneNumb(value);
//				message = "New phoneNumb succesfully saved!";
//			}
//			else if(currentSettingShow == 7){
//				user.setEmailAddress(value);
//				message = "New emailAddress succesfully saved!";
//			}
			
			//TODO Pop-Up String saved
			System.out.println(message);
		});
	}
	
	

	@SuppressWarnings("serial")
	private class UserActionInfoBasic extends AbstractAction {

		String choice;
		User user;

		public UserActionInfoBasic(String choice, String desc, User user) {
			super(choice);
			this.choice = choice;
			this.user = user;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			String descr = null;
			String value = null;

			switch (choice) {
			case "name":
				descr = "Your name: ";
				value = user.getName();
				break;
			case "username":
				descr = "Your username: ";
				value = user.getUsername();
				break;
			case "ID":
				descr = "Your ID: ";
				value = Integer.toString(user.getID());
				break;
			case "password":
				descr = "Your password: ";
				value = user.getPassword();
				break;
			}
			fillInfoPanel(descr, value);
			setCurrentPanel(infoPanel);
		}
	}
	
	@SuppressWarnings("serial")
	private class UserActionSettingBasic extends AbstractAction {

		String choice;
		User user;

		public UserActionSettingBasic(String choice, String desc, User user) {
			super(choice);
			this.choice = choice;
			this.user = user;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e){
			
			String descr = null;
			String value = null;
			
			switch (choice) {
            case "name" :
            	descr = "Set your new name: ";
            	value = user.getName();
            	setCurrentSettingShow(1);
                break;
            case "username":
            	descr = "Set your new username: ";
            	value = user.getUsername();
            	setCurrentSettingShow(2);
                break;
            case "password":
            	descr = "Set your new password: ";
            	value = user.getPassword();
            	setCurrentSettingShow(3);
                break;
        }
			fillSetPanel(descr,value);
			setCurrentPanel(settingPanel);
		}
	}
	
	
	@SuppressWarnings("serial")
	abstract class UserActionSetting extends AbstractAction {

		String name;

		public UserActionSetting(String name, String desc) {
			super(name);
			this.name = name;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public abstract void actionPerformed(ActionEvent e);
		
		
	}
	
	@SuppressWarnings("serial")
	abstract class UserActionInfo extends AbstractAction {

		String name;

		public UserActionInfo(String name, String desc) {
			super(name);
			this.name = name;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public abstract void actionPerformed(ActionEvent e);
		
		
	}

	/*****************************************************/
	//getter & setter
	
	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public JMenu getSettingsMenu() {
		return getSettingMenu();
	}

	public void setSettingsMenu(JMenu settingsMenu) {
		this.setSettingMenu(settingsMenu);
	}

	public JMenu getInfoMenu() {
		return infoMenu;
	}

	public void setInfoMenu(JMenu infoMenu) {
		this.infoMenu = infoMenu;
	}

	public JPanel getInfoPanel() {
		return infoPanel;
	}

	public void setInfoPanel(JPanel infoPanel) {
		this.infoPanel = infoPanel;
	}

	public JPanel getSettingPanel() {
		return settingPanel;
	}

	public void setSettingPanel(JPanel settingPanel) {
		this.settingPanel = settingPanel;
	}

	public JPanel getWelcome_panel() {
		return welcome_panel;
	}

	public void setWelcome_panel(JPanel welcome_panel) {
		this.welcome_panel = welcome_panel;
	}

	public JPanel getWelcome_button_panel() {
		return welcome_button_panel;
	}

	public void setWelcome_button_panel(JPanel welcome_button_panel) {
		this.welcome_button_panel = welcome_button_panel;
	}

	public JPanel getWelcome_message_panel() {
		return welcome_message_panel;
	}

	public void setWelcome_message_panel(JPanel welcome_message_panel) {
		this.welcome_message_panel = welcome_message_panel;
	}

	public JButton getLogOut_button() {
		return logOut_button;
	}

	public void setLogOut_button(JButton logOut_button) {
		this.logOut_button = logOut_button;
	}

	public int getCurrentSettingShow() {
		return currentSettingShow;
	}

	public void setCurrentSettingShow(int currentSettingShow) {
		this.currentSettingShow = currentSettingShow;
	}

	public JMenu getSettingMenu() {
		return settingMenu;
	}

	public void setSettingMenu(JMenu settingMenu) {
		this.settingMenu = settingMenu;
	}

	public JPanel getInfoSubPanel() {
		return infoSubPanel;
	}

	public void setInfoSubPanel(JPanel infoSubPanel) {
		this.infoSubPanel = infoSubPanel;
	}

	
}
