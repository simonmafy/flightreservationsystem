package sait.frs.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import sait.frs.exception.InvalidCitizenshipException;
import sait.frs.exception.InvalidNameException;
import sait.frs.exception.NoMoreSeatsException;
import sait.frs.exception.NullFlightException;
import sait.frs.manager.Manager;
import sait.frs.problemdomain.Flight;

/**
 * Holds the components for the flights tab.
 * 
 */
public class FlightsTab extends TabBase {

	/**
	 * Instance of travel manager.
	 */
	private Manager manager;

	/**
	 * List of flights.
	 */
	private JList<Flight> flightsList;

	private DefaultListModel<Flight> flightsModel;

	/**
	 * Creates the components for flights tab.
	 */

	JComboBox from;
	JComboBox to;
	JComboBox weekday;
	JButton findFlights;
	JButton reserve;
	JLabel fromLabel;
	JLabel toLabel;
	JLabel nameLabel;
	JLabel flightLabel;
	JLabel airlineLabel;
	JLabel dayLabel;
	JLabel timeLabel;
	JLabel costLabel;
	JLabel citizenshipLabel;

	JTextArea flightArea;
	JTextArea airlineArea;
	JTextArea dayArea;
	JTextArea timeArea;
	JTextArea costArea;
	JTextArea citizenshipArea;
	JTextArea nameArea;

	public FlightsTab(Manager manager) {
		this.manager = manager;
		panel.setLayout(new BorderLayout());

		JPanel northPanel = createNorthPanel();
		panel.add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = createCenterPanel();
		panel.add(centerPanel, BorderLayout.CENTER);

		/*
		 * JPanel southPanel = createSouthPanel(); panel.add(southPanel,
		 * BorderLayout.SOUTH);
		 */

	}

	/**
	 * Creates the north panel.
	 * 
	 * @return JPanel that goes in north.
	 */
	private JPanel createNorthPanel() {
		JPanel panel = new JPanel();

		JLabel title = new JLabel("Flights", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title);

		return panel;
	}

	/**
	 * Creates the center panel.
	 * 
	 * @return JPanel that goes in center.
	 */
	private JPanel createCenterPanel() {
		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());

		flightsModel = new DefaultListModel<>();
		flightsList = new JList<>(flightsModel);

		// User can only select one item at a time.
		flightsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Wrap JList in JScrollPane so it is scrollable.
		JScrollPane scrollPane = new JScrollPane(this.flightsList);

		flightsList.addListSelectionListener(new MyListSelectionListener());

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

		JLabel title2 = new JLabel("Flight Finder", SwingConstants.CENTER);
		title2.setFont(new Font("serif", Font.PLAIN, 29));
		panel2.add(title2, BorderLayout.NORTH);

		// nested paneldrop for panel2 above
		JPanel panelDrop = new JPanel();
		panelDrop.setLayout(new GridLayout(8, 1));
		panelDrop.setBorder(new TitledBorder(""));

		panel2.add(panelDrop);

		// drop down menu
		// String fromDrop[] = {"you", "Iam", "Canadian"};
		ArrayList<String> Temp = new ArrayList<String>(manager.getAirports());
		Object[] objArray = Temp.toArray();
		from = new JComboBox(objArray);
		panelDrop.add(from);

		to = new JComboBox(objArray);
		panelDrop.add(to);

		String nameDrop[] = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "Any" };
		weekday = new JComboBox(nameDrop);
		panelDrop.add(weekday);

		// ActionListener for JComboBox
		ActionListener fromActionListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				flightsModel.clear();
				String chosenFrom = (String) from.getSelectedItem();
				String chosenTo = (String) to.getSelectedItem();
				String chosenDay = (String) weekday.getSelectedItem();
				for (int i = 0; i < manager.findFlights(chosenFrom, chosenTo, chosenDay).size(); i++) {
					flightsModel.addElement(manager.findFlights(chosenFrom, chosenTo, chosenDay).get(i));
				}
			}

		};

		findFlights = new JButton("Find Flights");
		findFlights.setPreferredSize(new Dimension(40, 40));
		panelDrop.add(findFlights);

		findFlights.addActionListener(fromActionListener);

		// Panel 3 West

		JPanel textLabel = new JPanel();
		textLabel.setLayout(new GridLayout(8, 1));
		textLabel.setBorder(new TitledBorder(""));
		panel2.add(textLabel, BorderLayout.WEST);

		// labels
		fromLabel = new JLabel("From: ");
		textLabel.add(fromLabel);

		toLabel = new JLabel("To: ");
		textLabel.add(toLabel);

		nameLabel = new JLabel("Day: ");
		textLabel.add(nameLabel);

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

		flightLabel = new JLabel("Flight:");
		textLabel2.add(flightLabel);

		airlineLabel = new JLabel("Airline:");
		textLabel2.add(airlineLabel);

		dayLabel = new JLabel("Day:");
		textLabel2.add(dayLabel);

		timeLabel = new JLabel("Time:");
		textLabel2.add(timeLabel);

		costLabel = new JLabel("Cost:");
		textLabel2.add(costLabel);

		nameLabel = new JLabel("Name:");
		textLabel2.add(nameLabel);

		citizenshipLabel = new JLabel("Citizenship: ");
		textLabel2.add(citizenshipLabel);

		// text area
		JPanel textArea = new JPanel();
		textArea.setLayout(new GridLayout(10, 1));
		textArea.setBorder(new TitledBorder(""));
		panel3.add(textArea, BorderLayout.CENTER);

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

		dayArea = new JTextArea(1, 20);
		dayArea.setEditable(false);
		textArea.add(dayArea);
		Border border3 = BorderFactory.createLineBorder(Color.BLACK);
		dayArea.setBorder(BorderFactory.createCompoundBorder(border3, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		dayArea.setOpaque(false);

		timeArea = new JTextArea(1, 20);
		timeArea.setEditable(false);
		textArea.add(timeArea);
		Border border4 = BorderFactory.createLineBorder(Color.BLACK);
		timeArea.setBorder(
				BorderFactory.createCompoundBorder(border3, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		timeArea.setOpaque(false);

		costArea = new JTextArea(1, 20);
		costArea.setEditable(false);
		textArea.add(costArea);
		Border border5 = BorderFactory.createLineBorder(Color.BLACK);
		costArea.setBorder(
				BorderFactory.createCompoundBorder(border4, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		costArea.setOpaque(false);

		nameArea = new JTextArea(1, 20);
		textArea.add(nameArea);
		Border border6 = BorderFactory.createLineBorder(Color.BLACK);
		nameArea.setBorder(
				BorderFactory.createCompoundBorder(border5, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		citizenshipArea = new JTextArea(1, 20);
		textArea.add(citizenshipArea);
		Border border7 = BorderFactory.createLineBorder(Color.BLACK);
		citizenshipArea.setBorder(
				BorderFactory.createCompoundBorder(border6, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		ActionListener reserveAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String reserveName = nameArea.getText();
				String reserveCitizen = citizenshipArea.getText();
				Flight flightCode = manager.findFlightByCode(flightArea.getText());
				try {
					JOptionPane.showMessageDialog(null, "Reversation created. Your code is "
							+ manager.makeReservation(flightCode, reserveName, reserveCitizen));

				} catch (NullFlightException | NoMoreSeatsException | InvalidNameException
						| InvalidCitizenshipException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		};

		reserve = new JButton("Reserve");
		reserve.setPreferredSize(new Dimension(50, 50));
		panel3.add(reserve, BorderLayout.SOUTH);
		reserve.addActionListener(reserveAction);

		return panel;
	}

	private class MyListSelectionListener implements ListSelectionListener {
		/**
		 * Called when user selects an item in the JList.
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {
			Flight selectedFlight = (Flight) flightsList.getSelectedValue();
			flightArea.setText(selectedFlight.getCode());
			airlineArea.setText(selectedFlight.getAirline());
			dayArea.setText(selectedFlight.getWeekday());
			timeArea.setText(selectedFlight.getTime());
			costArea.setText(selectedFlight.getCostPerSeat() + "");
		}

	}
}