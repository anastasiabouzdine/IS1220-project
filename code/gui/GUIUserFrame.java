package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import users.User;

public abstract class GUIUserFrame {
	
	//Frames
	private JFrame frame;
	
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
	
	//help functions
	public void setCurrentPanel(JPanel panel) {
	    getFrame().setContentPane(panel);  	
	    getFrame().setVisible(true);
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
   	}
	
	public void initGUIRest(User user, Color color1, Color color2, String welcomeText, String programText) {
		setFrame(new JFrame("Welcome " + user.getName()));	
		fillWelcomePanel(user, color1, color2, welcomeText, programText);
		setCurrentPanel(welcome_panel);
		logOut_button.addActionListener((ActionEvent e) -> {
			frame.setVisible(false);
			GUIStartFrame.setCurrentLogInUser(null);
			GUIStartFrame.getInstance().goToHomePage();
			GUIStartFrame.getFrame().setVisible(true);
		});
	}

	/*****************************************************/
	//getter & setter
	
	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

}
