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
import users.Restaurant;
import users.User;

public class GUIRestaurantFrame extends GUIUserFrame {
	
	private GUIRestaurantFrame instance;
	private Restaurant restaurant;
	
	public GUIRestaurantFrame() {
		super();
		instance = this;
	}
	
	/*************************************************/
	//Init functions
	
	@Override
	public GUIUserFrame getInstance(User user) {
		
		if(user instanceof Restaurant){
			
			GUIStartFrame.getFrame().setVisible(false);
			this.restaurant = (Restaurant) user;
			initGUIRest(restaurant, Color.orange, Color.yellow, "Restaurant Area", "Just Dwaggit...");
			instance.open(0, 0, 600, 400);
			return instance;
		}
			
		return null;
	}
	
	private class RestaurantActionInfo extends UserActionInfo{

		public RestaurantActionInfo(String name, String desc) {
			super(name, desc);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			fillInfoPanel();
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
