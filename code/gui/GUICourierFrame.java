package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import users.Courier;
import users.User;

public class GUICourierFrame extends GUIUserFrame {

	private GUICourierFrame instance;
	private Courier courier;

	private JRadioButton dutyOff = new JRadioButton("Off");
	private JRadioButton dutyOn = new JRadioButton("On");
	private ButtonGroup duty = new ButtonGroup();

	private JPanel dutyPanel = new JPanel();

	public GUICourierFrame() {
		super();
		instance = this;
	}

	/*************************************************/
	// fill functions
	private void fillSetPanelNotification(Courier courier) {
		getSettingPanel().removeAll();
		if (courier.isAvailable()) {
			dutyOn.setSelected(true);
		} else {
			dutyOff.setSelected(true);
		}
		getSettingPanel().add(dutyPanel);
		getSetButtonPanel().removeAll();
		getSetButtonPanel().add(home_button);
		getSetButtonPanel().add(save_button);
		getSettingPanel().add(getSetButtonPanel(), BorderLayout.SOUTH);
	}

	/*************************************************/
	// Init functions

	@Override
	public GUIUserFrame getInstance(User user) {

		if (user instanceof Courier) {

			GUIStartFrame.getFrame().setVisible(false);
			this.courier = (Courier) user;
			initGUI(courier, Color.orange, Color.white, "Courier Area", User.messageBoxGUI);
			initSetPanelNotif();
			fillAndSetMenuBarCourier(courier);
			instance.open(0, 0, 600, 400);
			return instance;
		}

		return null;
	}

	private void fillInfoMenuWithFunctionCourier(Courier courier) {
		getInfoMenu().add(new courierActionInfoBasicCour("address", "show current address", courier));
		getInfoMenu().add(new courierActionInfoBasicCour("surname", "show current surname", courier));
		getInfoMenu().add(new courierActionInfoBasicCour("phoneNumb", "show current phone number", courier));
	}

	private void fillSetMenuWithFunctionCourier(Courier courier) {
		getSettingMenu().add(new courierActionSettingBasicCour("address", "change current address", courier));
		getSettingMenu().add(new courierActionSettingBasicCour("surname", "change current surname", courier));
		getSettingMenu().add(new courierActionSettingBasicCour("phoneNumb", "change current phone number", courier));
		getSettingMenu().add(new courierActionSettingBasicCour("duty", "change current duty settings", courier));
	}

	public void fillAndSetMenuBarCourier(Courier courier) {
		fillInfoMenuWithFunctionCourier(courier);
		fillSetMenuWithFunctionCourier(courier);
	}

	private void initSetPanelNotif() {
		duty.add(dutyOn);
		duty.add(dutyOff);
		dutyPanel.add(dutyOn);
		dutyPanel.add(dutyOff);
	}

	private class courierActionInfoBasicCour extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String choice;
		Courier courier;

		public courierActionInfoBasicCour(String choice, String desc, Courier courier) {
			super(choice);
			this.choice = choice;
			this.courier = courier;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			String descr = null;
			String value = null;

			switch (choice) {
			case "surname":
				descr = "Your surname: ";
				value = courier.getSurname();
				break;
			case "address":
				descr = "Your address: ";
				value = courier.getAddress().toString();
				break;
			case "phoneNumb":
				descr = "Your phone number: ";
				value = courier.getPhoneNumber();
				break;
			}
			fillInfoPanel(descr, value);
			setCurrentPanel(getInfoPanel());
		}
	}

	private class courierActionSettingBasicCour extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String choice;
		Courier courier;

		public courierActionSettingBasicCour(String choice, String desc, Courier courier) {
			super(choice);
			this.choice = choice;
			this.courier = courier;
			putValue(Action.SHORT_DESCRIPTION, desc);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			String descr = null;
			String value = null;

			switch (choice) {

			case "surname":
				descr = "Set your new surname: ";
				value = courier.getSurname();
				save_button = new JButton("SAVE");
				save_button.addActionListener((ActionEvent e2) -> {

					String value2 = getSetTextFieldValue().getText();
					courier.setSurname(value2);
				});
				fillSetPanel(descr, value);
				break;
			case "address":
				descr = "Set your new address: ";

				save_button = new JButton("SAVE");
				save_button.addActionListener((ActionEvent e4) -> {

					try {
						int xCoord = Integer.parseInt(getSetTextFieldXInt().getText());
						int yCoord = Integer.parseInt(getSetTextFieldYInt().getText());
						courier.getAddress().setxCoordinate(xCoord);
						courier.getAddress().setyCoordinate(yCoord);

					} catch (NumberFormatException fex) {
						String message = "Wrong Format! - Please write the address in the format \"xCoord,yCoord\"";
						popUpOkWindow(message);
					}
				});
				fillSetPanelAddress(descr, Integer.toString(courier.getAddress().getxCoordinate()),
						Integer.toString(courier.getAddress().getyCoordinate()));
				break;
			case "phoneNumb":
				descr = "Set your new phone number: ";
				value = courier.getPhoneNumber();
				save_button = new JButton("SAVE");
				save_button.addActionListener((ActionEvent e2) -> {

					String value2 = getSetTextFieldValue().getText();
					courier.setPhoneNumber(value2);
				});
				fillSetPanel(descr, value);
				break;
			case "duty":

				save_button = new JButton("SAVE");
				save_button.addActionListener((ActionEvent e2) -> {
					if (dutyOn.isSelected()) {
						courier.setAvailable(true);
					} else if (dutyOff.isSelected()) {
						courier.setAvailable(false);
					}
				});
				fillSetPanelNotification(courier);
				break;
			}
			setCurrentPanel(getSettingPanel());
		}
	}

}