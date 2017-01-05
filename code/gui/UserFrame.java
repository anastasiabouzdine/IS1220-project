package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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

import users.User;

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
	private int currentSettingShow = 0;

	public abstract UserFrame getInstance(User user);

	public void setGUIStartFrameVisible() {
		StartFrame.getFrame().setVisible(true);
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
	// fill panels
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

	public void fillSetPanel() {
		settingPanel.setBorder(BorderFactory.createTitledBorder("Settings"));
		settingPanel.setLayout(new BorderLayout());
		settingPanel.setBackground(Color.GREEN);
		getSetTextFieldDesc().setEditable(false);
		getSetSubPanel().setBackground(Color.WHITE);
		setTextFieldValue.setColumns(12);
		frame.add(settingPanel);
	}

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

	public void fillAndSetMenuBar(User user) {
		fillInfoMenuWithFunction(user);
		fillSetMenuWithFunction(user);
		menuBar.add(infoMenu);
		menuBar.add(getSettingMenu());
		frame.add(menuBar);
		frame.setJMenuBar(menuBar);
	}

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
		getHome_button().addActionListener((ActionEvent e) -> {
			setCurrentPanel(welcome_panel);
		});

		frame.add(welcome_panel);
	}

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
	public void setCurrentPanel(JPanel panel) {
		getFrame().setContentPane(panel);
		getFrame().setVisible(true);
	}

	public void popUpOkWindow(String message) {
		Object[] options = { "OK" };
		JOptionPane.showOptionDialog(null, message, "Attention", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE,
				null, options, options[0]);
	}

	/**************************************************/
	// Initialize functions
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


	/*****************************************************/
	// getter & setter

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

	/**
	 * @return the save_button
	 */
	public JButton getSave_button() {
		return save_button;
	}

	/**
	 * @param save_button
	 *            the save_button to set
	 */
	public void setSave_button(JButton save_button) {
		this.save_button = save_button;
	}

	public JTextField getSetTextFieldDesc() {
		return setTextFieldDesc;
	}

	public void setSetTextFieldDesc(JTextField setTextFieldDesc) {
		this.setTextFieldDesc = setTextFieldDesc;
	}

	public JPanel getSetSubPanel() {
		return setSubPanel;
	}

	public void setSetSubPanel(JPanel setSubPanel) {
		this.setSubPanel = setSubPanel;
	}

	public JPanel getSetButtonPanel() {
		return setButtonPanel;
	}

	public void setSetButtonPanel(JPanel setButtonPanel) {
		this.setButtonPanel = setButtonPanel;
	}

	/**
	 * @return the setTextFieldXInt
	 */
	public JTextField getSetTextFieldXInt() {
		return setTextFieldXInt;
	}

	/**
	 * @return the setTextFieldYInt
	 */
	public JTextField getSetTextFieldYInt() {
		return setTextFieldYInt;
	}

	/**
	 * @return the setTextFieldValue
	 */
	public JTextField getSetTextFieldValue() {
		return setTextFieldValue;
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

}
