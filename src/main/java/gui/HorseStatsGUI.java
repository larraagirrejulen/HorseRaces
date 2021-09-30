package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;

import domain.*;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class HorseStatsGUI extends JFrame {

	private HorseStatsGUI frame = this;
	private JPanel contentPane;
	private static final String FONT = "Verdana";
	
	public HorseStatsGUI(RaceHorse raceHorse, String language) {
		setBackground(new Color(0, 128, 128));
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				frame.dispose();
			}
		});
		
		setBounds(800, 800, 500, 400);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBackground(new Color(32, 178, 170));
		contentPane.setBorder(new LineBorder(new Color(0, 128, 128), 3));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel street = new JLabel(raceHorse.getStreet() + "");
		street.setOpaque(true);
		street.setFont(new Font(FONT, Font.PLAIN, 10));
		street.setForeground(new Color(255, 255, 255));
		street.setBackground(new Color(0, 128, 128));
		street.setBounds(264, 99, 100, 20);
		contentPane.add(street);
		
		JLabel winGain = new JLabel("x" + raceHorse.getWinGain() + "");
		winGain.setOpaque(true);
		winGain.setFont(new Font(FONT, Font.PLAIN, 10));
		winGain.setForeground(new Color(255, 255, 255));
		winGain.setBackground(new Color(0, 128, 128));
		winGain.setBounds(264, 122, 100, 20);
		contentPane.add(winGain);
		
		JLabel totalPoints = new JLabel(raceHorse.getHorse().getPoints() + "");
		totalPoints.setOpaque(true);
		totalPoints.setFont(new Font(FONT, Font.PLAIN, 10));
		totalPoints.setForeground(new Color(255, 255, 255));
		totalPoints.setBackground(new Color(0, 128, 128));
		totalPoints.setBounds(264, 146, 100, 20);
		contentPane.add(totalPoints);
		
		JLabel horseName = new JLabel(raceHorse.getHorse().getName());
		horseName.setOpaque(true);
		horseName.setFont(new Font(FONT, Font.PLAIN, 10));
		horseName.setForeground(new Color(255, 255, 255));
		horseName.setBackground(new Color(0, 128, 128));
		horseName.setBounds(264, 170, 100, 20);
		contentPane.add(horseName);
		
		JLabel horseSex = new JLabel(raceHorse.getHorse().getCavalryOrigin());
		horseSex.setOpaque(true);
		horseSex.setFont(new Font(FONT, Font.PLAIN, 10));
		horseSex.setForeground(new Color(255, 255, 255));
		horseSex.setBackground(new Color(0, 128, 128));
		horseSex.setBounds(264, 194, 100, 20);
		contentPane.add(horseSex);
		
		JLabel cavalryOrigin = new JLabel(ResourceBundle.getBundle(language).getString(raceHorse.getHorse().getSex()));
		cavalryOrigin.setOpaque(true);
		cavalryOrigin.setFont(new Font(FONT, Font.PLAIN, 10));
		cavalryOrigin.setForeground(new Color(255, 255, 255));
		cavalryOrigin.setBackground(new Color(0, 128, 128));
		cavalryOrigin.setBounds(264, 218, 100, 20);
		contentPane.add(cavalryOrigin);
		
		JLabel age = new JLabel(raceHorse.getHorse().getAge() + "");
		age.setOpaque(true);
		age.setFont(new Font(FONT, Font.PLAIN, 10));
		age.setForeground(new Color(255, 255, 255));
		age.setBackground(new Color(0, 128, 128));
		age.setBounds(264, 242, 100, 20);
		contentPane.add(age);
		
		JLabel lblStreet = new JLabel(ResourceBundle.getBundle(language).getString("StreetNum"));
		lblStreet.setOpaque(true);
		lblStreet.setBounds(113, 99, 150, 20);
		contentPane.add(lblStreet);
		lblStreet.setFont(new Font(FONT, Font.PLAIN, 10));
		lblStreet.setForeground(new Color(255, 255, 255));
		lblStreet.setBackground(new Color(0, 128, 128));
		
		JLabel lblWinGain = new JLabel(ResourceBundle.getBundle(language).getString("WinGain1"));
		lblWinGain.setOpaque(true);
		lblWinGain.setBounds(113, 122, 150, 20);
		contentPane.add(lblWinGain);
		lblWinGain.setFont(new Font(FONT, Font.PLAIN, 10));
		lblWinGain.setForeground(new Color(255, 255, 255));
		lblWinGain.setBackground(new Color(0, 128, 128));
		
		JLabel lblTotalPoints = new JLabel(ResourceBundle.getBundle(language).getString("Points"));
		lblTotalPoints.setOpaque(true);
		lblTotalPoints.setBounds(113, 146, 150, 20);
		contentPane.add(lblTotalPoints);
		lblTotalPoints.setFont(new Font(FONT, Font.PLAIN, 10));
		lblTotalPoints.setForeground(new Color(255, 255, 255));
		lblTotalPoints.setBackground(new Color(0, 128, 128));
		
		JLabel lblHorseName = new JLabel(ResourceBundle.getBundle(language).getString("Name"));
		lblHorseName.setOpaque(true);
		lblHorseName.setBounds(113, 170, 150, 20);
		contentPane.add(lblHorseName);
		lblHorseName.setFont(new Font(FONT, Font.PLAIN, 10));
		lblHorseName.setForeground(new Color(255, 255, 255));
		lblHorseName.setBackground(new Color(0, 128, 128));
		
		JLabel lblCavalryOrigin = new JLabel(ResourceBundle.getBundle(language).getString("Cavalry"));
		lblCavalryOrigin.setOpaque(true);
		lblCavalryOrigin.setBounds(113, 194, 150, 20);
		contentPane.add(lblCavalryOrigin);
		lblCavalryOrigin.setFont(new Font(FONT, Font.PLAIN, 10));
		lblCavalryOrigin.setForeground(new Color(255, 255, 255));
		lblCavalryOrigin.setBackground(new Color(0, 128, 128));
		
		JLabel lblHorseSex = new JLabel(ResourceBundle.getBundle(language).getString("Sex"));
		lblHorseSex.setOpaque(true);
		lblHorseSex.setBounds(113, 218, 150, 20);
		contentPane.add(lblHorseSex);
		lblHorseSex.setFont(new Font(FONT, Font.PLAIN, 10));
		lblHorseSex.setForeground(new Color(255, 255, 255));
		lblHorseSex.setBackground(new Color(0, 128, 128));
		
		JLabel lblAge = new JLabel(ResourceBundle.getBundle(language).getString("Age"));
		lblAge.setOpaque(true);
		lblAge.setBounds(113, 242, 150, 20);
		contentPane.add(lblAge);
		lblAge.setFont(new Font(FONT, Font.PLAIN, 10));
		lblAge.setForeground(new Color(255, 255, 255));
		lblAge.setBackground(new Color(0, 128, 128));

	}
}
