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

import users.User;

public abstract class GUIUserFrame {
	
	//Frames
	private JFrame frame;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu settingsMenu = new JMenu("Settings");
	private JMenu infoMenu = new JMenu("Information");
	
	private JPanel infoPanel = new JPanel();
	private JTextField infoTextField = new JTextField("Information");
	private JPanel settingPanel = new JPanel();
	private JTextField settingTextField = new JTextField("Settings");
	
	//Panels
	JPanel welcome_panel = new JPanel();
	JPanel welcome_button_panel = new JPanel();
	JPanel welcome_message_panel = new JPanel();
	
	//Buttons
	JButton logOut_button = new JButton("LOG OUT");
		
	
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
		infoPanel.add(infoTextField);
	}
	
	public void fillSettingPanel(){
		settingPanel.add(settingTextField);
	}
	
	public void fillAndSetMenuBar(){
		menuBar.add(infoMenu);
		menuBar.add(settingsMenu);
		frame.add(menuBar);
		frame.setJMenuBar(menuBar);
	}
	
	public void fillWelcomePanel(User user, Color color1, Color color2, String welcomeText, String programText) {
		welcome_panel.setBackground(color1);
		welcome_panel.setBorder(BorderFactory.createTitledBorder("Welcome " + user.getName()));
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
	    welcome_text.setText(programText);
	    welcome_text.setBackground(color2);

		welcome_panel.add(welcome_message_panel, BorderLayout.NORTH);
		welcome_panel.add(welcome_scrollPane, BorderLayout.CENTER);
		welcome_panel.add(welcome_button_panel, BorderLayout.SOUTH);
		
		//Buttons
		welcome_button_panel.add(logOut_button, BorderLayout.CENTER);
		frame.add(welcome_panel);
   	}
	
	/**************************************************/
	//help functions
	public void setCurrentPanel(JPanel panel) {
	    getFrame().setContentPane(panel);  	
	    getFrame().setVisible(true);
	}
	
	public void addUserActionToList(UserActionInfo action){
		infoMenu.add(action);
	}
		
	
	/**************************************************/
	//Initialize functions
	public void initGUIRest(User user, Color color1, Color color2, String welcomeText, String programText) {
		setFrame(new JFrame("Welcome " + user.getName()));	
		fillWelcomePanel(user, color1, color2, welcomeText, programText);
		fillAndSetMenuBar();
		setCurrentPanel(welcome_panel);
		logOut_button.addActionListener((ActionEvent e) -> {
			frame.setVisible(false);
			GUIStartFrame.setCurrentLogInUser(null);
			GUIStartFrame.getInstance().goToHomePage();
			GUIStartFrame.getFrame().setVisible(true);
		});
	}
	
	
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
		return settingsMenu;
	}

	public void setSettingsMenu(JMenu settingsMenu) {
		this.settingsMenu = settingsMenu;
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

	
}
