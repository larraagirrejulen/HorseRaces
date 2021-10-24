package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import com.toedter.calendar.JCalendar;

import business_logic.BLFacade;
import configuration.UtilDate;
import domain.Horse;
import domain.Race;
import domain.RaceHorse;
import domain.StartTime;

@SuppressWarnings("serial")
public class CreateRaceGUI extends JFrame {

	private BLFacade facade = LoginGUI.getBusinessLogic();
	private String language;
	private CreateRaceGUI frame = this;
	private static final String FONT = "Verdana";
	private static final String WRONG_INP_LBL = "Verdana";

	private JLabel lblNewLabel;
	private JLabel lbl;
	private JLabel lblRace;
	private JLabel lblError;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JButton jButtonCreate;
	private JButton btnAddHorses;
	private JTextField numerOfStreets;
	private JTextField winGainStartTime;
	private JCalendar jCalendar = new JCalendar();

	private JList<RaceHorse> raceHorsesList;
	private DefaultListModel<RaceHorse> raceHorses = new DefaultListModel<>();
	private JComboBox<Horse> horseList;
	private DefaultComboBoxModel<Horse> horses = new DefaultComboBoxModel<>();

	private Calendar calendarAct;
	private Calendar calendarAnt;
	private Race selectedRace;
	private DateFormat dateformat1;
	private ArrayList<Date> datesWithRacesCurrentMonth = new ArrayList<>();

	public CreateRaceGUI(AdminGUI adminFrame, String language) {
		setUndecorated(true);
		setBackground(Color.WHITE);
		getContentPane().setBackground(Color.WHITE);
		this.language = language;

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle(language).getString("CreateNewRace"));

		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 128, 128), 3));
		panel.setBackground(new Color(32, 178, 170));
		panel.setBounds(0, 0, 700, 500);
		getContentPane().add(panel);
		panel.setLayout(null);


		//Button to close

		JButton jButtonClose = new JButton(ResourceBundle.getBundle(language).getString("Back"));
		jButtonClose.setBorder(new LineBorder(new Color(0, 0, 0)));
		jButtonClose.setFont(new Font(FONT, Font.PLAIN, 10));
		jButtonClose.setBackground(new Color(0, 128, 128));
		jButtonClose.setForeground(Color.WHITE);
		jButtonClose.setBounds(590, 11, 100, 20);
		panel.add(jButtonClose);

		lblRace = new JLabel(ResourceBundle.getBundle(language).getString("NoRace"));
		lblRace.setOpaque(true);
		lblRace.setForeground(new Color(255, 255, 255));
		lblRace.setBackground(new Color(0, 128, 128));
		lblRace.setBounds(51, 89, 225, 20);
		panel.add(lblRace);
		lblRace.setHorizontalAlignment(SwingConstants.CENTER);
		lblRace.setFont(new Font(FONT, Font.PLAIN, 10));

		raceHorsesList = new JList<>();
		raceHorsesList.setForeground(new Color(255, 255, 255));
		raceHorsesList.setBorder(new MatteBorder(0, 0, 3, 0,  new Color(0, 0, 51)));
		raceHorsesList.setBounds(321, 120, 292, 150);
		panel.add(raceHorsesList);
		raceHorsesList.setBackground(new Color(0, 128, 128));
		raceHorsesList.setFont(new Font(FONT, Font.PLAIN, 10));
		raceHorsesList.setModel(raceHorses);

		jCalendar.getDayChooser().getDayPanel().setBackground(Color.WHITE);
		jCalendar.getMonthChooser().getComboBox().setFont(new Font(FONT, Font.PLAIN, 10));
		jCalendar.getYearChooser().getSpinner().setFont(new Font(FONT, Font.PLAIN, 10));
		jCalendar.getDayChooser().getDayPanel().setBorder(new LineBorder(new Color(0, 0, 0)));
		jCalendar.getYearChooser().getSpinner().setForeground(new Color(0, 0, 0));
		jCalendar.setBounds(51, 120, 225, 150);
		panel.add(jCalendar);
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					System.out.println("calendarAnt: "+calendarAnt.getTime());
					System.out.println("calendarAct: "+calendarAct.getTime());
					dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar.setCalendar(calendarAct);
						datesWithRacesCurrentMonth=(ArrayList<Date>) facade.getRacesMonth(jCalendar.getDate());
					}

					paintDaysWithRaces(jCalendar,datesWithRacesCurrentMonth);
					Date date = UtilDate.trim(calendarAct.getTime());
					try {
						selectedRace = facade.getRace(date);
						if (selectedRace == null)
							noRace();
						else
							race();
					} catch (Exception e1) {
						System.out.println(e1.getMessage());
					}
				}
			}
		});

		datesWithRacesCurrentMonth=(ArrayList<Date>) facade.getRacesMonth(jCalendar.getDate());
		paintDaysWithRaces(jCalendar,datesWithRacesCurrentMonth);

				numerOfStreets = new JTextField();
				numerOfStreets.setBorder(new MatteBorder(0, 0, 3, 0,  new Color(0, 0, 51)));
				numerOfStreets.setBackground(new Color(0, 128, 128));
				numerOfStreets.setForeground(new Color(255, 255, 255));
				numerOfStreets.setBounds(345, 335, 250, 20);
				panel.add(numerOfStreets);
				numerOfStreets.setFont(new Font(FONT, Font.PLAIN, 11));
				numerOfStreets.setColumns(10);


				//Button to create an empty race

				jButtonCreate = new JButton(ResourceBundle.getBundle(language).getString("CreateRace"));
				jButtonCreate.setBorder(new LineBorder(new Color(0, 0, 0)));
				jButtonCreate.setBounds(204, 378, 250, 35);
				panel.add(jButtonCreate);
				jButtonCreate.setFont(new Font(FONT, Font.PLAIN, 10));
				jButtonCreate.setForeground(Color.WHITE);
				jButtonCreate.setBackground(new Color(0, 0, 51));
				jButtonCreate.setEnabled(true);


				//Button to add horses to a race

				btnAddHorses = new JButton(ResourceBundle.getBundle(language).getString("AddHorses"));
				btnAddHorses.setBorder(new LineBorder(new Color(0, 0, 0)));
				btnAddHorses.setBounds(204, 378, 250, 35);
				panel.add(btnAddHorses);
				btnAddHorses.setFont(new Font(FONT, Font.PLAIN, 10));
				btnAddHorses.setForeground(Color.WHITE);
				btnAddHorses.setBackground(new Color(0, 0, 51));

				lbl = new JLabel("");
				lbl.setBounds(51, 304, 250, 20);
				panel.add(lbl);
				lbl.setFont(new Font(FONT, Font.PLAIN, 10));
				lbl.setForeground(new Color(0, 0, 51));
				lbl.setBackground(Color.WHITE);

				lblError = new JLabel("");
				lblError.setBounds(345, 366, 250, 20);
				panel.add(lblError);
				lblError.setBackground(Color.WHITE);
				lblError.setFont(new Font(FONT, Font.ITALIC, 9));
				lblError.setHorizontalAlignment(SwingConstants.CENTER);

				horseList = new JComboBox<>();
				horseList.setBorder(new LineBorder(new Color(0, 0, 0)));
				horseList.setForeground(new Color(255, 255, 255));
				horseList.setBackground(new Color(0, 128, 128));
				horseList.setBounds(345, 335, 250, 20);
				panel.add(horseList);
				horseList.setFont(new Font(FONT, Font.PLAIN, 10));
				horseList.setModel(horses);


				lblNewLabel = new JLabel(ResourceBundle.getBundle(language).getString("NumOfStreets"));
				lblNewLabel.setForeground(new Color(0, 0, 51));
				lblNewLabel.setBounds(345, 304, 250, 20);
				panel.add(lblNewLabel);
				lblNewLabel.setFont(new Font(FONT, Font.PLAIN, 10));

				winGainStartTime = new JTextField();
				winGainStartTime.setForeground(new Color(255, 255, 255));
				winGainStartTime.setBorder(new MatteBorder(0, 0, 3, 0,  new Color(0, 0, 51)));
				winGainStartTime.setBackground(new Color(0, 128, 128));
				winGainStartTime.setBounds(51, 335, 250, 20);
				panel.add(winGainStartTime);
				winGainStartTime.setFont(new Font(FONT, Font.PLAIN, 11));
				winGainStartTime.setColumns(10);
				winGainStartTime.setVisible(false);
				lblNewLabel.setVisible(false);

				horseList.setVisible(false);
				btnAddHorses.addActionListener(input -> {
					try {
						Horse horse = (Horse)horseList.getSelectedItem();
						RaceHorse rh = facade.createRaceHorse(Double.parseDouble(winGainStartTime.getText()), selectedRace, horse);
						raceHorses.addElement(rh);
						horses.removeElement(rh.getHorse());
						selectedRace = facade.getRace(UtilDate.trim(calendarAct.getTime()));
						if(selectedRace.getSize()==selectedRace.getNumOfStreets()) {
							btnAddHorses.setEnabled(false);
						}else lblError.setText("");
					}catch(Exception ex) {
						lblError.setText(ResourceBundle.getBundle(language).getString(WRONG_INP_LBL));
						lblError.setForeground(Color.RED);
					}
				});
				btnAddHorses.setVisible(false);
				jButtonCreate.addActionListener(input ->{
					try {
						Date date = UtilDate.trim(jCalendar.getDate());
						int num = Integer.parseInt(numerOfStreets.getText());
						StartTime st = new StartTime(winGainStartTime.getText());
						if(num>1 && num<7 && st.getHour()!=null && st.getMinute()!=null) {
							selectedRace = facade.createRace(date, num, st);
							winGainStartTime.setText("");
							race();
							datesWithRacesCurrentMonth=(ArrayList<Date>) facade.getRacesMonth(jCalendar.getDate());
							paintDaysWithRaces(jCalendar,datesWithRacesCurrentMonth);
							lblError.setText("");
						}else {
							lblError.setText(ResourceBundle.getBundle(language).getString(WRONG_INP_LBL));
							lblError.setForeground(Color.RED);
						}

					}catch(Exception ex) {
						lblError.setText(ResourceBundle.getBundle(language).getString(WRONG_INP_LBL));
						lblError.setForeground(Color.RED);
					}
				});
				jButtonCreate.setVisible(false);
				numerOfStreets.setVisible(false);
		raceHorsesList.setVisible(false);
		jButtonClose.addActionListener(input -> {
			adminFrame.setVisible(true);
			frame.dispose();
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				adminFrame.setVisible(true);
			}
		});
	}


	//Function to change interface when there is no race

	private void noRace() {
		jButtonCreate.setVisible(true);
		lblNewLabel.setVisible(true);
		numerOfStreets.setVisible(true);
		btnAddHorses.setVisible(false);
		raceHorsesList.setVisible(false);
		horseList.setVisible(false);
		winGainStartTime.setVisible(true);

		lblRace.setLocation(51, 89);
		lblRace.setSize(225, 20);
		lbl.setText(ResourceBundle.getBundle(language).getString("StartTime"));
		lblRace.setText(ResourceBundle.getBundle(language).getString("NoRace"));
		lblNewLabel.setText(ResourceBundle.getBundle(language).getString("NumOfStreets"));
	}


	//Function to change interface when there is a race

	private void race() {
		selectedRace = facade.getRace(UtilDate.trim(calendarAct.getTime()));
		raceHorses.clear();
		horses.removeAllElements();
		List<Horse> hl = facade.getHorses();
		List<RaceHorse> rhl = selectedRace.getRaceHorses();
		for(Horse h: hl)
			horses.addElement(h);
		for(RaceHorse rh: rhl) {
			horses.removeElement(rh);
			raceHorses.addElement(rh);
		}

		jButtonCreate.setVisible(false);
		numerOfStreets.setVisible(false);
		winGainStartTime.setVisible(true);
		horseList.setVisible(true);
		btnAddHorses.setVisible(true);
		raceHorsesList.setVisible(true);
		btnAddHorses.setEnabled(selectedRace.getSize()<selectedRace.getNumOfStreets());

		lblRace.setLocation(321, 89);
		lblRace.setSize(292, 20);
		lblRace.setText(dateformat1.format(calendarAct.getTime()) + " " + selectedRace.getStartTime().toString()+ ResourceBundle.getBundle(language).getString("Streets") + selectedRace.getNumOfStreets());
		lblNewLabel.setText(ResourceBundle.getBundle(language).getString("SelectHorse"));
		lbl.setText(ResourceBundle.getBundle(language).getString("WinGain"));
	}

	public static void paintDaysWithRaces(JCalendar jCalendar,List<Date> datesWithRacesCurrentMonth) {
		// For each day with events in current month, the background color for that day is changed.

		Calendar calendar = jCalendar.getCalendar();

		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;

		for (Date d:datesWithRacesCurrentMonth){
	 		calendar.setTime(d);
	 		System.out.println(d);
			Component o =  jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
	 	}
 			calendar.set(Calendar.DAY_OF_MONTH, today);
	 		calendar.set(Calendar.MONTH, month);
	 		calendar.set(Calendar.YEAR, year);
	}
}
