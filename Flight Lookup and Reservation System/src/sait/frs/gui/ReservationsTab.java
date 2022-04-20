package sait.frs.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sait.frs.exception.InvalidCitizenshipException;
import sait.frs.exception.InvalidNameException;
import sait.frs.manager.Manager;
import sait.frs.problemdomain.Flight;
import sait.frs.problemdomain.Reservation;

/**
 * Holds the components for the reservations tab.
 * 
 */
public class ReservationsTab extends TabBase {
	/**
	 * Instance of travel manager.
	 */
	private Manager manager;

	/**
	 * Creates the components for reservations tab.
	 */
	JLabel fromLabel;
	JLabel toLabel;
	JLabel nameLabel;
	JLabel flightLabel;
	JLabel airlineLabel;
	JLabel dayLabel;
	JLabel timeLabel;
	JLabel costLabel;
	JLabel citizenshipLabel;
	JLabel nameLabel2;
	JLabel airlineLabel2;
	JLabel codeLabel2;
	JLabel statusLabel;
	JLabel code2;

	JTextArea flightArea;
	JTextArea airlineArea;
	JTextArea codeArea;
	JTextArea timeArea;
	JTextArea costArea;
	JTextArea citizenshipArea;
	JTextArea nameArea;

	JComboBox statusArea;

	JTextField code;
	JTextField airline;
	JTextField name;

	JButton update;
	JButton findReservations;

	private JList<Reservation> reservationList;

	private DefaultListModel<Reservation> reservationModel;

	public ReservationsTab(Manager manager) {
		this.manager = manager;
		panel.setLayout(new BorderLayout());

		JPanel northPanel = createNorthPanel();
		panel.add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = createCenterPanel();
		panel.add(centerPanel, BorderLayout.CENTER);
	}

	/**
	 * Creates the north panel.
	 * 
	 * @return JPanel that goes in north.
	 */
	private JPanel createNorthPanel() {
		JPanel panel = new JPanel();

		JLabel title = new JLabel("Reservations", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title);

		return panel;
	}

	private JPanel createCenterPanel() {

		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());

		reservationModel = new DefaultListModel<>();
		reservationList = new JList<>(reservationModel);

		// User can only select one item at a time.
		reservationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Wrap JList in JScrollPane so it is scrollable.
		JScrollPane scrollPane = new JScrollPane(this.reservationList);

		reservationList.addListSelectionListener(new MyListSelectionListener());

		panel.add(scrollPane);

		// panel left
		JPanel panelSize = new JPanel();
		panelSize.setPreferredSize(new Dimension(100, 100));
		panel.add(panelSize, BorderLayout.WEST);

		// nested panel in the south
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		panel2.setBorder(new TitledBorder(""));

		panel.add(panel2, BorderLayout.SOUTH);

		JLabel title2 = new JLabel("Search", SwingConstants.CENTER);
		title2.setFont(new Font("serif", Font.PLAIN, 29));
		panel2.add(title2, BorderLayout.NORTH);

		// Need a new panel to replace paneldrop

		JPanel text = new JPanel();
		text.setLayout(new GridLayout(8, 1));
		text.setBorder(new TitledBorder(""));

		panel2.add(text);

		code = new JTextField();
		text.add(code);

		airline = new JTextField();
		text.add(airline);

		name = new JTextField();
		text.add(name);

		ActionListener findActionListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				reservationModel.clear();
				String resCode = code.getText();
				String airlineInput = airline.getText();
				String nameInput = name.getText();
				for (int i = 0; i < manager.findReservations(resCode, airlineInput, nameInput).size(); i++) {
					reservationModel.addElement(manager.findReservations(resCode, airlineInput, nameInput).get(i));
				}

			}

		};
		findReservations = new JButton("Find Reservation");
		findReservations.setPreferredSize(new Dimension(40, 40));
		text.add(findReservations);

		findReservations.addActionListener(findActionListener);

		// Panel 3 West

		JPanel textLabel = new JPanel();
		textLabel.setLayout(new GridLayout(8, 1));
		textLabel.setBorder(new TitledBorder(""));
		panel2.add(textLabel, BorderLayout.WEST);

		codeLabel2 = new JLabel("Code:");
		textLabel.add(codeLabel2);

		airlineLabel2 = new JLabel("Airline:");
		textLabel.add(airlineLabel2);

		nameLabel2 = new JLabel("Name:");
		textLabel.add(nameLabel2);

		// Nested Border Layout Panel in the East of Center Panel
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BorderLayout());
		panel3.setBorder(new TitledBorder(""));
		panel.add(panel3, BorderLayout.EAST);

		//
		JPanel textLabel2 = new JPanel();
		textLabel2.setLayout(new GridLayout(10, 1));
		textLabel2.setBorder(new TitledBorder(""));
		panel3.add(textLabel2, BorderLayout.WEST);

		code2 = new JLabel("Code:");
		textLabel2.add(code2);

		flightLabel = new JLabel("Flight:");
		textLabel2.add(flightLabel);

		airlineLabel = new JLabel("Airline:");
		textLabel2.add(airlineLabel);

		costLabel = new JLabel("Cost:");
		textLabel2.add(costLabel);

		nameLabel = new JLabel("Name:");
		textLabel2.add(nameLabel);

		citizenshipLabel = new JLabel("Citizenship:");
		textLabel2.add(citizenshipLabel);

		statusLabel = new JLabel("Status:");
		textLabel2.add(statusLabel);

		// text area
		JPanel textArea = new JPanel();
		textArea.setLayout(new GridLayout(10, 1));
		textArea.setBorder(new TitledBorder(""));
		panel3.add(textArea, BorderLayout.CENTER);

		codeArea = new JTextArea(1, 20);
		codeArea.setEditable(false);
		textArea.add(codeArea);
		Border border3 = BorderFactory.createLineBorder(Color.BLACK);
		codeArea.setBorder(
				BorderFactory.createCompoundBorder(border3, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		codeArea.setOpaque(false);

		flightArea = new JTextArea(1, 20);
		flightArea.setEditable(false);
		textArea.add(flightArea);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		flightArea
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		flightArea.setOpaque(false);

		airlineArea = new JTextArea(1, 20);
		airlineArea.setEditable(false);
		textArea.add(airlineArea);
		Border border2 = BorderFactory.createLineBorder(Color.BLACK);
		airlineArea.setBorder(
				BorderFactory.createCompoundBorder(border2, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		airlineArea.setOpaque(false);

		costArea = new JTextArea(1, 20);
		costArea.setEditable(false);
		textArea.add(costArea);
		Border border4 = BorderFactory.createLineBorder(Color.BLACK);
		costArea.setBorder(
				BorderFactory.createCompoundBorder(border4, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		costArea.setOpaque(false);

		nameArea = new JTextArea(1, 20);
		textArea.add(nameArea);
		Border border5 = BorderFactory.createLineBorder(Color.BLACK);
		nameArea.setBorder(
				BorderFactory.createCompoundBorder(border5, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		citizenshipArea = new JTextArea(1, 20);
		textArea.add(citizenshipArea);
		Border border6 = BorderFactory.createLineBorder(Color.BLACK);
		citizenshipArea.setBorder(
				BorderFactory.createCompoundBorder(border6, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		String statusArray[] = { "Active", "Non Active" };
		statusArea = new JComboBox(statusArray);
		textArea.add(statusArea);

		ActionListener updateActionListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Reservation selectedRes = (Reservation) reservationList.getSelectedValue();
				try {
					selectedRes.setName(nameArea.getText());
					selectedRes.setCitizenship(citizenshipArea.getText());
				} catch (InvalidNameException | InvalidCitizenshipException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Your changes have been saved");
				manager.persist();
			}
		};

		update = new JButton("Update");
		update.setPreferredSize(new Dimension(50, 50));
		panel3.add(update, BorderLayout.SOUTH);

		update.addActionListener(updateActionListener);

		return panel;
	}

	private class MyListSelectionListener implements ListSelectionListener {
		/**
		 * Called when user selects an item in the JList.
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {

			Reservation selectedRes = (Reservation) reservationList.getSelectedValue();
			Flight flights = selectedRes.getFlight();

			codeArea.setText(selectedRes.getCode());
			flightArea.setText(flights.getCode());
			airlineArea.setText(flights.getAirline());
			costArea.setText(flights.getCostPerSeat() + "");
			nameArea.setText(selectedRes.getName());
			citizenshipArea.setText(selectedRes.getCitizenship());

		}

	}

}
