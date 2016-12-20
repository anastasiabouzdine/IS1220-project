package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.sun.jndi.url.corbaname.corbanameURLContextFactory;
import com.sun.org.apache.xml.internal.security.Init;

import core.Core;
import jdk.internal.org.objectweb.asm.tree.IntInsnNode;
import users.Manager;
import users.Customer;
import users.Restaurant;
import users.User;

public class GUIManagerFrame extends GUIUserFrame {
	
	private GUIManagerFrame instance;
	private Manager manager;
	
	public GUIManagerFrame() {
		super();
		instance = this;
	}
	
	/*************************************************/
	//Init functions
	
	@Override
	public GUIUserFrame getInstance(User user) {
		
		if(user instanceof Manager){
			
			GUIStartFrame.getFrame().setVisible(false);
			this.manager = (Manager) user;
			initGUIRest(manager, Color.orange, Color.yellow, "Chill die Base", "Just Dwaggit...");
			instance.open(0, 0, 600, 400);
			return instance;
		}
			
		return null;
	}
	
	private class ManagerActionInfo extends UserActionInfo{

		public ManagerActionInfo(String name, String desc) {
			super(name, desc);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (name) {
            case "":
                // do something for menuItem1
                break;
            case "menu2Action":
                // do something for menuItem2
                break;
            case "menu3Action":
                // do something for menuItem3
                break;
        }
			
		}
	}
}