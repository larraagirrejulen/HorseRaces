package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import business_logic.BLFacade;
import domain.Client;
import domain.Race;
import domain.RaceHorse;

@SuppressWarnings("serial")
public class ViewRacesClientGUI extends JFrame {

	private ClientGUI clientFrame;
	private String language;
	private BLFacade facade = LoginGUI.getBusinessLogic();
	private ViewRacesClientGUI frame = this;
	private static final String FONT = "Verdana";

	private JLabel lblMoneyBet;
	private JLabel lblError;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JButton jButtonClose;
	private JButton btnBetForHorse;
	private JButton btnCheckStats;
	private Race nextRace;
	private JTextField moneyBet;
	private Client client;
	private JLabel lblCash;
	private JList<RaceHorse> raceHorsesList;
	private DefaultListModel<RaceHorse> raceHorses = new DefaultListModel<>();

	public ViewRacesClientGUI(ClientGUI clFrame, Client client, String language) {
		setUndecorated(true);
		getContentPane().setFont(new Font(FONT, Font.PLAIN, 11));
		getContentPane().setBackground(new Color(32, 178, 170));
		setBackground(Color.WHITE);
		this.language=language;
		this.client = client;
		clientFrame = clFrame;
		nextRace = facade.getNextRace();
		jbInit();
	}

	private void jbInit() {
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle(language).getString("CreateNewRace"));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 700, 500);
		panel.setBorder(new LineBorder(new Color(0, 128, 128), 3));
		panel.setBackground(new Color(32, 178, 170));
		getContentPane().add(panel);
		panel.setLayout(null);


		lblCash = new JLabel(ResourceBundle.getBundle(language).getString("Balance") + this.client.getWallet() + " $");
		lblCash.setOpaque(true);
		lblCash.setBorder(null);
		lblCash.setHorizontalAlignment(SwingConstants.CENTER);
		lblCash.setForeground(new Color(255, 255, 255));
		lblCash.setFont(new Font(FONT, Font.PLAIN, 10));
		lblCash.setBackground(new Color(0, 128, 128));
		lblCash.setBounds(10, 11, 150, 20);
		panel.add(lblCash);

		JLabel lblRace = new JLabel("");
		lblRace.setOpaque(true);
		lblRace.setHorizontalAlignment(SwingConstants.CENTER);
		lblRace.setForeground(Color.WHITE);
		lblRace.setFont(new Font(FONT, Font.PLAIN, 11));
		lblRace.setBorder(null);
		lblRace.setBackground(new Color(0, 128, 128));
		lblRace.setBounds(206, 87, 280, 20);
		panel.add(lblRace);

		lblMoneyBet = new JLabel(ResourceBundle.getBundle(language).getString("BetMoney"));
		lblMoneyBet.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoneyBet.setBounds(206, 336, 280, 13);
		panel.add(lblMoneyBet);
		lblMoneyBet.setBackground(Color.WHITE);
		lblMoneyBet.setForeground(new Color(0, 0, 51));
		lblMoneyBet.setFont(new Font(FONT, Font.PLAIN, 11));

		moneyBet = new JTextField();
		moneyBet.setHorizontalAlignment(SwingConstants.CENTER);
		moneyBet.setBorder(new MatteBorder(0, 0, 3, 0,  new Color(0, 0, 51)));
		moneyBet.setBounds(232, 354, 230, 25);
		panel.add(moneyBet);
		moneyBet.setForeground(new Color(255, 255, 255));
		moneyBet.setBackground(new Color(0, 128, 128));
		moneyBet.setFont(new Font(FONT, Font.PLAIN, 11));
		moneyBet.setColumns(10);


		//Button to bet for the selected horse

		btnBetForHorse = new JButton(ResourceBundle.getBundle(language).getString("BetHorse"));
		btnBetForHorse.setBorder(new LineBorder(new Color(0, 0, 51)));
		btnBetForHorse.setBounds(232, 418, 230, 35);
		panel.add(btnBetForHorse);
		btnBetForHorse.setFont(new Font(FONT, Font.PLAIN, 11));
		btnBetForHorse.setForeground(Color.WHITE);
		btnBetForHorse.setBackground(new Color(0, 0, 51));

		raceHorsesList = new JList<>();
		raceHorsesList.setBorder(new MatteBorder(0, 0, 3, 0,  new Color(0, 0, 51)));
		raceHorsesList.setBounds(206, 118, 280, 150);
		panel.add(raceHorsesList);
		raceHorsesList.setForeground(new Color(255, 255, 255));
		raceHorsesList.setFont(new Font(FONT, Font.PLAIN, 11));
		raceHorsesList.setBackground(new Color(0, 128, 128));
		raceHorsesList.setModel(raceHorses);

		JLabel jLabelRaceDate = new JLabel(ResourceBundle.getBundle(language).getString("UpcomingRace"));
		jLabelRaceDate.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelRaceDate.setForeground(new Color(0, 0, 51));
		jLabelRaceDate.setFont(new Font(FONT, Font.BOLD, 11));
		jLabelRaceDate.setBackground(new Color(0, 128, 128));
		jLabelRaceDate.setBounds(262, 62, 171, 20);
		panel.add(jLabelRaceDate);

		//Button to check selected horses stats

		btnCheckStats = new JButton(ResourceBundle.getBundle(language).getString("CheckStats"));
		btnCheckStats.setBorder(new LineBorder(new Color(0, 0, 51)));
		btnCheckStats.setBounds(232, 279, 230, 35);
		panel.add(btnCheckStats);
		btnCheckStats.setForeground(Color.WHITE);
		btnCheckStats.setBackground(new Color(0, 128, 128));
		btnCheckStats.setFont(new Font(FONT, Font.PLAIN, 11));
		btnCheckStats.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RaceHorse rh = raceHorsesList.getSelectedValue();
				if(rh!=null) {
					HorseStatsGUI gui = new HorseStatsGUI(rh, language);
					gui.setVisible(true);
				}
			}
		});

		lblError = new JLabel("");
		lblError.setBounds(206, 390, 280, 20);
		panel.add(lblError);
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setFont(new Font(FONT, Font.ITALIC, 10));
		lblError.setBackground(Color.WHITE);
		btnBetForHorse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					RaceHorse rh = raceHorsesList.getSelectedValue();
					double amount = Double.parseDouble(moneyBet.getText());
					if(amount>0 && rh!=null && client.getBet()==null && client.getWallet()>=amount) {
						if(nextRace.getDate().compareTo(new Date())>0){
							client = facade.betForHorse(rh, client, amount);
							lblCash.setText(ResourceBundle.getBundle(language).getString("Balance") + client.getWallet() + " $");
							lblError.setText(ResourceBundle.getBundle(language).getString("Bet"));
							lblError.setForeground(Color.BLACK);
						}
					}else {
						lblError.setText(ResourceBundle.getBundle(language).getString("CantBet"));
						lblError.setForeground(Color.RED);
					}
				}catch(Exception ex) {
					System.out.println(ex.getMessage());
					lblError.setText(ResourceBundle.getBundle(language).getString("WrongInput"));
					lblError.setForeground(Color.RED);
				}
			}
		});

		List<RaceHorse> rhl = nextRace.getRaceHorses();
		for(RaceHorse rh: rhl)
			raceHorses.addElement(rh);
		lblRace.setText(nextRace.getDate() + ResourceBundle.getBundle(language).getString("Streets") + nextRace.getNumOfStreets());


		//Button to close current window

		jButtonClose = new JButton(ResourceBundle.getBundle(language).getString("Back"));
		jButtonClose.setBorder(new LineBorder(new Color(0, 0, 51)));
		jButtonClose.setForeground(Color.WHITE);
		jButtonClose.setBackground(new Color(0, 128, 128));
		jButtonClose.setFont(new Font(FONT, Font.PLAIN, 10));
		jButtonClose.setBounds(610, 11, 80, 20);
		jButtonClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clientFrame.setClient(client);
				clientFrame.setVisible(true);
				frame.dispose();
			}
		});
		panel.add(jButtonClose);
	}

}
