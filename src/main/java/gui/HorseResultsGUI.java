package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import com.toedter.calendar.JCalendar;

import business_logic.BLFacade;
import configuration.UtilDate;
import domain.Bet;
import domain.Race;
import domain.RaceHorse;

@SuppressWarnings("serial")
public class HorseResultsGUI extends JFrame {

	private BLFacade facade = LoginGUI.getBusinessLogic();
	private AdminGUI adminFrame;
	private String language;
	private HorseResultsGUI frame = this;
	private static final String FONT = "Verdana";

	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct;
	private Calendar calendarAnt;
	private DateFormat dateformat1;
	private ArrayList<Date> datesWithRacesCurrentMonth = new ArrayList<>();

	private JComboBox<RaceHorse> horseList;
	private DefaultComboBoxModel<RaceHorse> horses = new DefaultComboBoxModel<>();
	private JList<RaceHorse> orderList;
	private DefaultListModel<RaceHorse> raceHorses = new DefaultListModel<>();

	private Race selectedRace;
	private JButton btnAddHorse;
	private JButton btnConfirm;
	private JButton btnRemoveHorse;
	private JLabel lblRace;
	private JLabel lblInOrder;

	public HorseResultsGUI(AdminGUI adminFrame, String language) {
		setUndecorated(true);
		setBackground(Color.WHITE);
		getContentPane().setBackground(Color.WHITE);
		try {
			this.language = language;
			this.adminFrame = adminFrame;
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle(language).getString("RaceResults"));

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 128, 128), 3));
		panel.setBackground(new Color(32, 178, 170));
		panel.setBounds(0, 0, 700, 500);
		getContentPane().add(panel);
		panel.setLayout(null);

		JButton jButtonClose = new JButton(ResourceBundle.getBundle(language).getString("Back"));
		jButtonClose.setBorder(new LineBorder(new Color(0, 0, 0)));
		jButtonClose.setBounds(610, 11, 80, 20);
		panel.add(jButtonClose);
		jButtonClose.setBackground(new Color(0, 128, 128));
		jButtonClose.setForeground(Color.WHITE);
		jButtonClose.setFont(new Font(FONT, Font.PLAIN, 10));

		lblInOrder = new JLabel("");
		lblInOrder.setBorder(null);
		lblInOrder.setBounds(321, 266, 292, 20);
		panel.add(lblInOrder);
		lblInOrder.setFont(new Font(FONT, Font.PLAIN, 11));
		lblInOrder.setForeground(new Color(0, 0, 51));
		lblInOrder.setBackground(new Color(0, 128, 128));

		orderList = new JList<>();
		orderList.setForeground(new Color(255, 255, 255));
		orderList.setBorder(new MatteBorder(0, 0, 3, 0,  new Color(0, 0, 51)));
		orderList.setBounds(321, 105, 292, 150);
		panel.add(orderList);
		orderList.setFont(new Font(FONT, Font.PLAIN, 11));
		orderList.setBackground(new Color(0, 128, 128));
		orderList.setModel(raceHorses);

		lblRace = new JLabel(ResourceBundle.getBundle(language).getString("NoRace"));
		lblRace.setOpaque(true);
		lblRace.setLocation(292, 20);
		lblRace.setBounds(57, 74, 225, 20);
		panel.add(lblRace);
		lblRace.setFont(new Font(FONT, Font.PLAIN, 11));
		lblRace.setHorizontalAlignment(SwingConstants.CENTER);
		lblRace.setForeground(new Color(255, 255, 255));
		lblRace.setBackground(new Color(0, 128, 128));

		horseList = new JComboBox<>();
		horseList.setBorder(new LineBorder(new Color(0, 0, 0)));
		horseList.setBounds(321, 297, 292, 22);
		panel.add(horseList);
		horseList.setBackground(new Color(0, 128, 128));
		horseList.setEditable(true);
		horseList.setForeground(new Color(255, 255, 255));
		horseList.setFont(new Font(FONT, Font.PLAIN, 11));
		horseList.setModel(horses);


		//Button to add horse to the final positions list

		btnAddHorse = new JButton(ResourceBundle.getBundle(language).getString("AddHorse"));
		btnAddHorse.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnAddHorse.setBounds(321, 330, 145, 29);
		panel.add(btnAddHorse);
		btnAddHorse.setForeground(Color.WHITE);
		btnAddHorse.setBackground(new Color(0, 128, 128));
		btnAddHorse.setFont(new Font(FONT, Font.PLAIN, 11));

		btnRemoveHorse = new JButton(ResourceBundle.getBundle(language).getString("RemoveHorse"));
		btnRemoveHorse.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnRemoveHorse.setBounds(476, 330, 137, 29);
		panel.add(btnRemoveHorse);
		btnRemoveHorse.setForeground(Color.WHITE);
		btnRemoveHorse.setBackground(new Color(0, 128, 128));
		btnRemoveHorse.setFont(new Font(FONT, Font.PLAIN, 11));

		JLabel lblError = new JLabel("");
		lblError.setBounds(321, 370, 292, 20);
		panel.add(lblError);
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(Color.DARK_GRAY);
		lblError.setFont(new Font(FONT, Font.ITALIC, 9));
		lblError.setBackground(Color.WHITE);


		//Button to confirm final positions

		btnConfirm = new JButton(ResourceBundle.getBundle(language).getString("Confirm"));
		btnConfirm.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnConfirm.setBounds(321, 401, 292, 33);
		panel.add(btnConfirm);
		btnConfirm.setForeground(Color.WHITE);
		btnConfirm.setBackground(new Color(0, 0, 51));
		btnConfirm.setFont(new Font(FONT, Font.PLAIN, 11));

		jCalendar.getMonthChooser().getComboBox().setFont(new Font(FONT, Font.PLAIN, 10));
		jCalendar.getMonthChooser().getComboBox().setForeground(new Color(0, 0, 0));
		jCalendar.getMonthChooser().getSpinner().setFont(new Font(FONT, Font.PLAIN, 10));
		jCalendar.getMonthChooser().getSpinner().setBackground(Color.WHITE);
		jCalendar.getMonthChooser().getSpinner().setForeground(Color.WHITE);
		jCalendar.getYearChooser().getSpinner().setFont(new Font(FONT, Font.PLAIN, 10));
		jCalendar.getYearChooser().getSpinner().setBackground(new Color(255, 255, 255));
		jCalendar.getYearChooser().getSpinner().setForeground(new Color(0, 0, 0));
		jCalendar.getDayChooser().getDayPanel().setBackground(new Color(255, 255, 255));
		jCalendar.getDayChooser().getDayPanel().setForeground(new Color(0, 0, 0));
		jCalendar.getDayChooser().getDayPanel().setBorder(new LineBorder(new Color(0, 0, 0)));
		jCalendar.getMonthChooser().getComboBox().setBackground(new Color(255, 255, 255));
		jCalendar.setBounds(57, 105, 225, 150);
		panel.add(jCalendar);
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale"))
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				else if (propertychangeevent.getPropertyName().equals("calendar")) {
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
					selectedRace = facade.getRace(date);
					if (selectedRace == null || selectedRace.getFinished())
						noRace();
					else
						race();
				}
			}
		});

		datesWithRacesCurrentMonth=(ArrayList<Date>) facade.getRacesMonth(jCalendar.getDate());
		paintDaysWithRaces(jCalendar,datesWithRacesCurrentMonth);
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double betAmount;
				if(horses.getSize()==0) {
					int points = raceHorses.getSize();
					RaceHorse winner = raceHorses.getElementAt(0);
					for(Bet b: winner.getBets()) {
						betAmount = b.getAmount();
						facade.addMoney(b.getClient(), betAmount*winner.getWinGain());
					}
					for(int i=0; i<raceHorses.getSize(); i++) {
						facade.addPoints(raceHorses.getElementAt(i).getHorse(), points);
						points--;
					}
					selectedRace = facade.finishRace(selectedRace);
					noRace();
					lblError.setForeground(Color.DARK_GRAY);
					lblError.setText("");
				}else {
					lblError.setForeground(Color.RED);
					lblError.setText(ResourceBundle.getBundle(language).getString("AllHorses"));
				}
			}
		});
		btnConfirm.setVisible(false);
		btnRemoveHorse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					RaceHorse rh=orderList.getSelectedValue();
					horses.addElement(rh);
					raceHorses.removeElement(rh);
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnRemoveHorse.setVisible(false);
				btnAddHorse.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							RaceHorse rh=(RaceHorse)horses.getSelectedItem();
							raceHorses.addElement(rh);
							horses.removeElement(rh);
						}catch(Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				btnAddHorse.setVisible(false);
				horseList.setVisible(false);
				orderList.setVisible(false);
		jButtonClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				adminFrame.setVisible(true);
				frame.dispose();
			}
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
		if(selectedRace!=null && selectedRace.getFinished())
			lblRace.setText(ResourceBundle.getBundle(language).getString("AlreadySet"));
		else
			lblRace.setText(ResourceBundle.getBundle(language).getString("NoRace"));
		lblInOrder.setText("");
		lblRace.setLocation(57, 74);
		lblRace.setSize(225, 20);
		orderList.setVisible(false);
		horseList.setVisible(false);
		btnAddHorse.setVisible(false);
		btnRemoveHorse.setVisible(false);
		btnConfirm.setVisible(false);
	}


	//Function to change interface when there is a race

	private void race() {
		selectedRace = facade.getRace(UtilDate.trim(calendarAct.getTime()));
		raceHorses.clear();
		horses.removeAllElements();
		List<RaceHorse> rhl = selectedRace.getRaceHorses();
		for(RaceHorse rh: rhl) {
			horses.addElement(rh);
		}

		lblRace.setLocation(321, 74);
		lblRace.setSize(292, 20);
		lblRace.setText(dateformat1.format(calendarAct.getTime()) + ResourceBundle.getBundle(language).getString("Streets") + selectedRace.getNumOfStreets());
		lblInOrder.setText(ResourceBundle.getBundle(language).getString("InOrder"));

		orderList.setVisible(true);
		horseList.setVisible(true);
		btnAddHorse.setVisible(true);
		btnRemoveHorse.setVisible(true);
		btnConfirm.setVisible(true);
	}

	public static void paintDaysWithRaces(JCalendar jCalendar,List<Date> datesWithRacesCurrentMonth) {
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
