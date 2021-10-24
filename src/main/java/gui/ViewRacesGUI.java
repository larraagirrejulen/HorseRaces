package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import business_logic.BLFacade;
import domain.Race;
import domain.RaceHorse;

@SuppressWarnings("serial")
public class ViewRacesGUI extends JFrame {

	private LoginGUI loginFrame;
	private String language;
	private ViewRacesGUI frame = this;
	private static BLFacade facade = LoginGUI.getBusinessLogic();
	private static final String FONT = "Verdana";
	private DefaultListModel<RaceHorse> raceHorses = new DefaultListModel<>();
	private transient Race nextRace;


	public ViewRacesGUI(LoginGUI loFrame, String language) {
		setLocation(new Point(610, 260));
		setUndecorated(true);
		getContentPane().setFont(new Font(FONT, Font.PLAIN, 11));
		setBackground(Color.WHITE);
		getContentPane().setBackground(new Color(32, 178, 170));
		this.language=language;
		loginFrame = loFrame;
		nextRace = facade.getNextRace();
		jbInit();
	}

	private void jbInit(){

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 128, 128), 3));
		panel.setBackground(new Color(32, 178, 170));
		panel.setBounds(0, 0, 700, 500);
		getContentPane().add(panel);
		panel.setLayout(null);

		JList<RaceHorse> raceHorsesList = new JList<>();
		raceHorsesList.setBorder(new MatteBorder(0, 0, 3, 0, new Color(0, 0, 51)));
		raceHorsesList.setBounds(206, 179, 280, 150);
		panel.add(raceHorsesList);
		raceHorsesList.setForeground(new Color(255, 255, 255));
		raceHorsesList.setFont(new Font(FONT, Font.PLAIN, 11));
		raceHorsesList.setBackground(new Color(0, 128, 128));
		raceHorsesList.setModel(raceHorses);

		JLabel lblRace = new JLabel("");
		lblRace.setBorder(null);
		lblRace.setOpaque(true);
		lblRace.setBounds(206, 148, 280, 20);
		panel.add(lblRace);
		lblRace.setHorizontalAlignment(SwingConstants.CENTER);
		lblRace.setFont(new Font(FONT, Font.PLAIN, 11));
		lblRace.setForeground(new Color(255, 255, 255));
		lblRace.setBackground(new Color(0, 128, 128));

		JLabel jLabelRaceDate = new JLabel(ResourceBundle.getBundle(language).getString("UpcomingRace"));
		jLabelRaceDate.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelRaceDate.setBounds(262, 112, 171, 20);
		panel.add(jLabelRaceDate);
		jLabelRaceDate.setFont(new Font(FONT, Font.BOLD, 11));
		jLabelRaceDate.setForeground(new Color(0, 0, 51));
		jLabelRaceDate.setBackground(new Color(0, 128, 128));

		List<RaceHorse> rhl = nextRace.getRaceHorses();
		for(RaceHorse rh: rhl)
			raceHorses.addElement(rh);
		lblRace.setText(nextRace.getDate() + ResourceBundle.getBundle(language).getString("Streets") + nextRace.getNumOfStreets());

		//Button to check selected horses stats

		JButton btnCheckStats = new JButton(ResourceBundle.getBundle(language).getString("CheckStats"));
		btnCheckStats.setBorder(new LineBorder(new Color(0, 0, 51)));
		btnCheckStats.setBounds(232, 340, 230, 35);
		panel.add(btnCheckStats);
		btnCheckStats.setForeground(Color.WHITE);
		btnCheckStats.setBackground(new Color(0, 0, 51));
		btnCheckStats.setFont(new Font(FONT, Font.PLAIN, 11));
		btnCheckStats.addActionListener(input -> {
			RaceHorse rh = raceHorsesList.getSelectedValue();
			if(rh!=null) {
				HorseStatsGUI gui = new HorseStatsGUI(rh, language);
				gui.setVisible(true);
			}
		});



		//Button to close current window

		JButton jButtonClose = new JButton(ResourceBundle.getBundle(language).getString("Back"));
		jButtonClose.setBorder(new LineBorder(new Color(0, 0, 51)));
		jButtonClose.setBounds(610, 11, 80, 20);
		panel.add(jButtonClose);
		jButtonClose.setFont(new Font(FONT, Font.PLAIN, 10));
		jButtonClose.setBackground(new Color(0, 128, 128));
		jButtonClose.setForeground(Color.WHITE);
		jButtonClose.addActionListener(input -> {
			loginFrame.setVisible(true);
			frame.dispose();
		});

	}
}
