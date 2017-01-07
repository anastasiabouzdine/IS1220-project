package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import core.Core;
import users.User;


/**
 * The class <code>UserFrame</code> is an abstract class that will be inherited by the users. It determines the
 * basic layout of the user frame and provides with some useful functions.
 * 
 * This class has nested classes providing the actions that are added to the menu bar.
 * 
 * @author John de Wasseige
 * @author Patrick von Platen
 */
public abstract class UserFrame {

	// Frames
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
	private JTextField setTextFieldXInt = new JTextField();
	private JTextField setTextFieldYInt = new JTextField();

	// Panels
	private JPanel welcome_panel = new JPanel();
	private JPanel welcome_button_panel = new JPanel();
	private JPanel welcome_message_panel = new JPanel();

	// Buttons
	private JButton logOut_button = new JButton("LOG OUT");
	private JButton home_button = new JButton("GO HOME");
	private JButton save_button;
	private JButton reset_button = new JButton("RESET ALL");
	private int currentSettingShow = 0;

	/**
	 * @param user 
	 * @return a UserFrame meaning an object of the class itself
	 */
	public abstract UserFrame getInstance(User user);

	/**
	 * sets the startFrame of the GUI visible 
	 * @see <code>StartFrame</code>
	 */
	public void setGUIStartFrameVisible() {
		StartFrame.getFrame().setVisible(true);
	}

	/**
	 * @param xLocation the horizontal location where the GUI is going to be displayed
	 * @param yLocation the vertical location where the GUI is going to be displayed
	 * @param width the width of the window that will be displayed
	 * @param height the height of the window that will be displayed
	 */
	public void open(final int xLocation, final int yLocation, final int width, final int height) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.setBounds(xLocation, yLocation, width, height);
				frame.setVisible(true);
				frame.addWindowListener(new WindowEventHandler());
				frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			}
		});
	}

	/**************************************************/
	// fill panels
	/**
	 * use to fill the panel that will be displayed when an action of the 
	 * information bar is clicked on.
	 */
	public void fillInfoPanel() {
		getInfoPanel().setBorder(BorderFactory.createTitledBorder("Information"));
		infoPanel.setLayout(new BorderLayout());
		infoPanel.setBackground(Color.CYAN);
		infoTextFieldDesc.setEditable(false);
		infoTextFieldValue.setEditable(false);
		getInfoSubPanel().setBackground(Color.WHITE);
		infoPanel.add(getInfoSubPanel());
		frame.add(infoPanel);
	}

	/**
	 * fill info panel with a certain description and a value that 
	 * is displayed.
	 * 
	 * @param descr description of the value to be displayed 
	 * @param value value that was asked for
	 */
	public void fillInfoPanel(String descr, String value) {
		infoPanel.removeAll();
		infoTextFieldDesc.setText(descr);
		infoTextFieldValue.setText(value);
		getInfoSubPanel().add(infoTextFieldDesc, BorderLayout.CENTER);
		getInfoSubPanel().add(infoTextFieldValue, BorderLayout.CENTER);
		infoPanel.add(getInfoSubPanel());
		infoPanel.add(getHome_button(), BorderLayout.SOUTH);

	}

	private void fillInfoMenuWithFunction(User user) {
		infoMenu.add(new UserActionInfoBasic("name", "show current name", user));
		infoMenu.add(new UserActionInfoBasic("username", "show current username", user));
		infoMenu.add(new UserActionInfoBasic("ID", "show current ID", user));
		infoMenu.add(new UserActionInfoBasic("password", "show current password", user));
	}

	/**
	 * use to fill the panel that will be displayed when an action of the 
	 * setting bar is clicked on.
	 */
	public void fillSetPanel() {
		settingPanel.setBorder(BorderFactory.createTitledBorder("Settings"));
		settingPanel.setLayout(new BorderLayout());
		settingPanel.setBackground(Color.GREEN);
		getSetTextFieldDesc().setEditable(false);
		getSetSubPanel().setBackground(Color.WHITE);
		setTextFieldValue.setColumns(12);
		frame.add(settingPanel);
	}

	/**
	 * fill setting panel with a certain description and a value that 
	 * is displayed.
	 * 
	 * @param descr description of the value to be displayed 
	 * @param value value that is going to be changed
	 */
	public void fillSetPanel(String descr, String value) {

		settingPanel.removeAll();
		getSetTextFieldDesc().setText(descr);
		setTextFieldValue.setText(value);
		getSetSubPanel().removeAll();
		getSetSubPanel().add(getSetTextFieldDesc(), BorderLayout.CENTER);
		getSetSubPanel().add(setTextFieldValue, BorderLayout.CENTER);
		settingPanel.add(getSetSubPanel());
		getSetButtonPanel().removeAll();
		getSetButtonPanel().add(getHome_button());
		getSetButtonPanel().add(save_button);
		getSettingPanel().add(getSetButtonPanel(), BorderLayout.SOUTH);
	}

	private void fillSetMenuWithFunction(User user) {
		getSettingMenu().add(new UserActionSettingBasic("name", "change current name", user));
		getSettingMenu().add(new UserActionSettingBasic("username", "change current username", user));
		getSettingMenu().add(new UserActionSettingBasic("password", "change current password", user));
	}

	/**
	 * initialize the menu bar
	 * 
	 * @param user user that is logged in
	 */
	public void fillAndSetMenuBar(User user) {
		fillInfoMenuWithFunction(user);
		fillSetMenuWithFunction(user);
		menuBar.add(infoMenu);
		menuBar.add(getSettingMenu());
		frame.add(menuBar);
		frame.setJMenuBar(menuBar);
	}

	/**
	 * initialize the welcome panel
	 * 
	 * @param color1 the color of the inner panel 
	 * @param color2 the color of the outer panel which is different for every user
	 * @param welcomeText text to welcome the user 
	 * @param messages of the message box
	 */
	public void fillWelcomePanel(User user, Color color1, Color color2, String welcomeText, String new_messages) {
		welcome_panel.setBackground(color1);
		welcome_panel.setBorder(BorderFactory.createTitledBorder(welcomeText));
		welcome_panel.setLayout(new BorderLayout());

		welcome_button_panel.setBackground(color1);

		JTextField program_name = new JTextField(31);
		Font font = new Font("SansSerif", Font.BOLD, 12);
		program_name.setFont(font);
		program_name.setBackground(color1);
		program_name.setText("Welcome " + user.getName());
		program_name.setEditable(false);
		welcome_message_panel.setBackground(color1);
		welcome_message_panel.add(program_name);

		JTextArea messageBox = new JTextArea();
		JScrollPane welcome_scrollPane = new JScrollPane();
		welcome_scrollPane.setViewportView(messageBox);
		messageBox.setText("******************* Message Box ****************** \n\n" + new_messages);
		messageBox.setBackground(color2);

		welcome_panel.add(welcome_message_panel, BorderLayout.NORTH);
		welcome_panel.add(welcome_scrollPane, BorderLayout.CENTER);
		welcome_panel.add(welcome_button_panel, BorderLayout.SOUTH);

		// Buttons
		welcome_button_panel.add(logOut_button, BorderLayout.CENTER);
		welcome_button_panel.add(reset_button, BorderLayout.EAST);
		getHome_button().addActionListener((ActionEvent e) -> {
			setCurrentPanel(welcome_panel);
		});
		reset_button.addActionListener((e) -> {

			popUpOkWindow("All data has been reseted and a new system was put in place. "
					+ "\nsystem will shut down. Plese restart the system.");

			StartFrame.setCore(new Core());
			try {
				FileOutputStream fileOut = new FileOutputStream("./ser_files/gui_core.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(StartFrame.getCore());
				out.close();
				fileOut.close();
				System.out.println("Serialized data is saved in ./ser_files/gui_core.ser");
			}catch(IOException i) {
				i.printStackTrace();
			}
			System.out.println("Database was successfully reset and you have been logged out.");

			System.exit(0);
		});

		frame.add(welcome_panel);
	}

	/**
	 * fill the address panel
	 * 
	 * @param descr the description of the address panel 
	 * @param valueX the value of the xCoordinate
	 * @param valueY the value of the yCoordinate
	 */
	public void fillSetPanelAddress(String descr, String valueX, String valueY) {
		getSettingPanel().removeAll();
		getSetTextFieldDesc().setText(descr);
		setTextFieldXInt.setText(valueX);
		setTextFieldYInt.setText(valueY);
		getSetSubPanel().removeAll();
		getSetSubPanel().add(getSetTextFieldDesc(), BorderLayout.CENTER);
		getSetSubPanel().add(setTextFieldXInt, BorderLayout.SOUTH);
		getSetSubPanel().add(setTextFieldYInt, BorderLayout.SOUTH);
		getSettingPanel().add(getSetSubPanel());
		getSetButtonPanel().removeAll();
		getSetButtonPanel().add(getHome_button());
		getSetButtonPanel().add(save_button);
		getSettingPanel().add(getSetButtonPanel(), BorderLayout.SOUTH);
	}

	/**************************************************/
	// help functions
	/**
	 * change the panel that is shown
	 * 
	 * @param panel panel that will be the panel that is shown
	 */
	public void setCurrentPanel(JPanel panel) {
		getFrame().setContentPane(panel);
		getFrame().setVisible(true);
	}

	/**
	 * create pop up window
	 * 
	 * @param message a message that will be shown in a pop up window
	 */
	public void popUpOkWindow(String message) {
		Object[] options = { "OK" };
		JOptionPane.showOptionDialog(null, message, "Attention", JOptionPane.PLAIN_MESSAGE,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}

	/**************************************************/
	// Initialize functions
	/**
	 * initialize the frame that will be shown
	 * 
	 * @param color1 the color of the inner panel 
	 * @param color2 the color of the outer panel which is different for every user
	 * @param welcomeText text to welcome the user 
	 * @param messages of the message box
	 */
	public void initGUI(User user, Color color1, Color color2, String welcomeText, String messageBox) {
		setFrame(new JFrame("Welcome " + user.getName()));
		fillWelcomePanel(user, color1, color2, welcomeText, messageBox);
		fillInfoPanel();
		fillSetPanel();
		fillAndSetMenuBar(user);
		fillInitTextfieldsWithFocus();
		setCurrentPanel(welcome_panel);
		logOut_button.addActionListener((ActionEvent e) -> {
			frame.setVisible(false);
			StartFrame.getCore().logOut();
			StartFrame.setCurrentLogInUser(null);
			StartFrame.getInstance().goToHomePage();
			StartFrame.getFrame().setVisible(true);
		});
	}

	private void fillInitTextfieldsWithFocus() {
		setTextFieldValue.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				setTextFieldValue.setText("");
			}
		});
		setTextFieldYInt.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				setTextFieldYInt.setText("");
			}
		});
		setTextFieldXInt.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				setTextFieldXInt.setText("");
			}
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
		public void actionPerformed(ActionEvent e) {

			String descr = null;
			String value = null;

			switch (choice) {
			case "name":
				descr = "Set your new name: ";
				value = user.getName();
				save_button = new JButton("SAVE");
				save_button.addActionListener((ActionEvent e2) -> {
					String value2 = setTextFieldValue.getText();
					user.setName(value2);
				});
				break;
			case "username":
				descr = "Set your new username: ";
				value = user.getUsername();
				save_button = new JButton("SAVE");
				save_button.addActionListener((ActionEvent e2) -> {
					String value2 = setTextFieldValue.getText();
					StartFrame.getCore().setUsername(user, value2);
				});
				break;
			case "password":
				descr = "Set your new password: ";
				value = user.getPassword();
				save_button = new JButton("SAVE");
				save_button.addActionListener((ActionEvent e2) -> {
					String value2 = setTextFieldValue.getText();
					user.setPassword(value2);
				});
				break;
			}
			fillSetPanel(descr, value);
			setCurrentPanel(settingPanel);
		}
	}

	class WindowEventHandler extends WindowAdapter {
		public void windowClosing(WindowEvent evt) {
			StartFrame.getCore().logOut();
			try {
				FileOutputStream fileOut = new FileOutputStream("./ser_files/gui_core.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(StartFrame.getCore());
				out.close();
				fileOut.close();
				System.out.println("Serialized data is saved in ./ser_files/gui_core.ser");
			}catch(IOException i) {
				i.printStackTrace();
			}
			System.exit(0);
		}
	}
	
	/*****************************************************/
	// getter & setter

	/**
	 * @return the frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * @param frame the frame to set
	 */
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	/**
	 * @return the menuBar
	 */
	public JMenuBar getMenuBar() {
		return menuBar;
	}

	/**
	 * @param menuBar the menuBar to set
	 */
	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	/**
	 * @return the settingMenu
	 */
	public JMenu getSettingMenu() {
		return settingMenu;
	}

	/**
	 * @param settingMenu the settingMenu to set
	 */
	public void setSettingMenu(JMenu settingMenu) {
		this.settingMenu = settingMenu;
	}

	/**
	 * @return the infoMenu
	 */
	public JMenu getInfoMenu() {
		return infoMenu;
	}

	/**
	 * @param infoMenu the infoMenu to set
	 */
	public void setInfoMenu(JMenu infoMenu) {
		this.infoMenu = infoMenu;
	}

	/**
	 * @return the infoPanel
	 */
	public JPanel getInfoPanel() {
		return infoPanel;
	}

	/**
	 * @param infoPanel the infoPanel to set
	 */
	public void setInfoPanel(JPanel infoPanel) {
		this.infoPanel = infoPanel;
	}

	/**
	 * @return the infoSubPanel
	 */
	public JPanel getInfoSubPanel() {
		return infoSubPanel;
	}

	/**
	 * @param infoSubPanel the infoSubPanel to set
	 */
	public void setInfoSubPanel(JPanel infoSubPanel) {
		this.infoSubPanel = infoSubPanel;
	}

	/**
	 * @return the infoTextFieldDesc
	 */
	public JTextField getInfoTextFieldDesc() {
		return infoTextFieldDesc;
	}

	/**
	 * @param infoTextFieldDesc the infoTextFieldDesc to set
	 */
	public void setInfoTextFieldDesc(JTextField infoTextFieldDesc) {
		this.infoTextFieldDesc = infoTextFieldDesc;
	}

	/**
	 * @return the infoTextFieldValue
	 */
	public JTextField getInfoTextFieldValue() {
		return infoTextFieldValue;
	}

	/**
	 * @param infoTextFieldValue the infoTextFieldValue to set
	 */
	public void setInfoTextFieldValue(JTextField infoTextFieldValue) {
		this.infoTextFieldValue = infoTextFieldValue;
	}

	/**
	 * @return the settingPanel
	 */
	public JPanel getSettingPanel() {
		return settingPanel;
	}

	/**
	 * @param settingPanel the settingPanel to set
	 */
	public void setSettingPanel(JPanel settingPanel) {
		this.settingPanel = settingPanel;
	}

	/**
	 * @return the setSubPanel
	 */
	public JPanel getSetSubPanel() {
		return setSubPanel;
	}

	/**
	 * @param setSubPanel the setSubPanel to set
	 */
	public void setSetSubPanel(JPanel setSubPanel) {
		this.setSubPanel = setSubPanel;
	}

	/**
	 * @return the setButtonPanel
	 */
	public JPanel getSetButtonPanel() {
		return setButtonPanel;
	}

	/**
	 * @param setButtonPanel the setButtonPanel to set
	 */
	public void setSetButtonPanel(JPanel setButtonPanel) {
		this.setButtonPanel = setButtonPanel;
	}

	/**
	 * @return the setTextFieldDesc
	 */
	public JTextField getSetTextFieldDesc() {
		return setTextFieldDesc;
	}

	/**
	 * @param setTextFieldDesc the setTextFieldDesc to set
	 */
	public void setSetTextFieldDesc(JTextField setTextFieldDesc) {
		this.setTextFieldDesc = setTextFieldDesc;
	}

	/**
	 * @return the setTextFieldValue
	 */
	public JTextField getSetTextFieldValue() {
		return setTextFieldValue;
	}

	/**
	 * @param setTextFieldValue the setTextFieldValue to set
	 */
	public void setSetTextFieldValue(JTextField setTextFieldValue) {
		this.setTextFieldValue = setTextFieldValue;
	}

	/**
	 * @return the setTextFieldXInt
	 */
	public JTextField getSetTextFieldXInt() {
		return setTextFieldXInt;
	}

	/**
	 * @param setTextFieldXInt the setTextFieldXInt to set
	 */
	public void setSetTextFieldXInt(JTextField setTextFieldXInt) {
		this.setTextFieldXInt = setTextFieldXInt;
	}

	/**
	 * @return the setTextFieldYInt
	 */
	public JTextField getSetTextFieldYInt() {
		return setTextFieldYInt;
	}

	/**
	 * @param setTextFieldYInt the setTextFieldYInt to set
	 */
	public void setSetTextFieldYInt(JTextField setTextFieldYInt) {
		this.setTextFieldYInt = setTextFieldYInt;
	}

	/**
	 * @return the welcome_panel
	 */
	public JPanel getWelcome_panel() {
		return welcome_panel;
	}

	/**
	 * @param welcome_panel the welcome_panel to set
	 */
	public void setWelcome_panel(JPanel welcome_panel) {
		this.welcome_panel = welcome_panel;
	}

	/**
	 * @return the welcome_button_panel
	 */
	public JPanel getWelcome_button_panel() {
		return welcome_button_panel;
	}

	/**
	 * @param welcome_button_panel the welcome_button_panel to set
	 */
	public void setWelcome_button_panel(JPanel welcome_button_panel) {
		this.welcome_button_panel = welcome_button_panel;
	}

	/**
	 * @return the welcome_message_panel
	 */
	public JPanel getWelcome_message_panel() {
		return welcome_message_panel;
	}

	/**
	 * @param welcome_message_panel the welcome_message_panel to set
	 */
	public void setWelcome_message_panel(JPanel welcome_message_panel) {
		this.welcome_message_panel = welcome_message_panel;
	}

	/**
	 * @return the logOut_button
	 */
	public JButton getLogOut_button() {
		return logOut_button;
	}

	/**
	 * @param logOut_button the logOut_button to set
	 */
	public void setLogOut_button(JButton logOut_button) {
		this.logOut_button = logOut_button;
	}

	/**
	 * @return the home_button
	 */
	public JButton getHome_button() {
		return home_button;
	}

	/**
	 * @param home_button the home_button to set
	 */
	public void setHome_button(JButton home_button) {
		this.home_button = home_button;
	}

	/**
	 * @return the save_button
	 */
	public JButton getSave_button() {
		return save_button;
	}

	/**
	 * @param save_button the save_button to set
	 */
	public void setSave_button(JButton save_button) {
		this.save_button = save_button;
	}

	/**
	 * @return the reset_button
	 */
	public JButton getReset_button() {
		return reset_button;
	}

	/**
	 * @param reset_button the reset_button to set
	 */
	public void setReset_button(JButton reset_button) {
		this.reset_button = reset_button;
	}

	/**
	 * @return the currentSettingShow
	 */
	public int getCurrentSettingShow() {
		return currentSettingShow;
	}

	/**
	 * @param currentSettingShow the currentSettingShow to set
	 */
	public void setCurrentSettingShow(int currentSettingShow) {
		this.currentSettingShow = currentSettingShow;
	}

	

	

}
