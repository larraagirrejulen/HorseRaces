package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import business_logic.BLFacade;
import domain.Client;

@SuppressWarnings("serial")
public class ClientGUI extends JFrame {

	private static final String FONT = "Verdana";
	private JPanel contentPane;
	private ClientGUI frame = this;
	private transient Client client;
	private JLabel lblCash;
	private String language1;
	private static BLFacade facade = LoginGUI.getBusinessLogic();
	
	public void setClient(Client client) {
		this.client = client;
		lblCash.setText(ResourceBundle.getBundle(language1).getString("Balance") + this.client.getWallet() + " $");
	}

	public ClientGUI(Client cl, LoginGUI mainFrame, String language) {
		setLocation(new Point(610, 260));
		setUndecorated(true);

		setBounds(100, 100, 700, 500);
		setBackground(Color.WHITE);

		this.language1=language;
		this.client = cl;

		contentPane = new JPanel();
		contentPane.setBackground(new Color(32, 178, 170));
		contentPane.setBorder(new LineBorder(new Color(0, 128, 128), 3));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		lblCash = new JLabel(ResourceBundle.getBundle(language).getString("Balance") + this.client.getWallet() + " $");
		lblCash.setBorder(null);
		lblCash.setOpaque(true);
		lblCash.setFont(new Font(FONT, Font.PLAIN, 10));
		lblCash.setForeground(new Color(255, 255, 255));
		lblCash.setBackground(new Color(0, 128, 128));
		lblCash.setHorizontalAlignment(SwingConstants.CENTER);
		lblCash.setBounds(10, 11, 150, 20);
		contentPane.add(lblCash);


		//Button to view races and bet

		JButton btnViewRaces = new JButton(ResourceBundle.getBundle(language).getString("UpcomingRace"));
		btnViewRaces.setBorder(new LineBorder(new Color(0, 0, 51)));
		btnViewRaces.setForeground(Color.WHITE);
		btnViewRaces.setFont(new Font(FONT, Font.PLAIN, 11));
		btnViewRaces.setBackground(new Color(0, 0, 51));
		btnViewRaces.addActionListener(input -> {
				new ViewRacesClientGUI(frame, client, language).setVisible(true);
				frame.dispose();
		});
		btnViewRaces.setBounds(250, 150, 200, 35);
		contentPane.add(btnViewRaces);


		//Button to add or rest money to the account

		JButton btnAddRestMoney = new JButton(ResourceBundle.getBundle(language).getString("AddRestMoney"));
		btnAddRestMoney.setBorder(new LineBorder(new Color(0, 0, 51)));
		btnAddRestMoney.setFont(new Font(FONT, Font.PLAIN, 10));
		btnAddRestMoney.setForeground(Color.WHITE);
		btnAddRestMoney.setBackground(new Color(0, 128, 128));
		btnAddRestMoney.addActionListener(input -> {
				WalletGUI gui = new WalletGUI(client, frame, language);
				gui.setVisible(true);
				frame.dispose();
		});
		btnAddRestMoney.setBounds(10, 42, 150, 20);
		contentPane.add(btnAddRestMoney);


		//Button to log out

		JButton btnLogout = new JButton(ResourceBundle.getBundle(language).getString("Logout"));
		btnLogout.setBorder(new LineBorder(new Color(0, 0, 51)));
		btnLogout.setBounds(590, 11, 100, 20);
		contentPane.add(btnLogout);
		btnLogout.setFont(new Font(FONT, Font.PLAIN, 10));
		btnLogout.setForeground(Color.WHITE);
		btnLogout.setBackground(new Color(0, 128, 128));


		//Button to delete current account

		JButton btnDeleteAcount = new JButton(ResourceBundle.getBundle(language).getString("DeleteAcount"));
		btnDeleteAcount.setBorder(new LineBorder(new Color(0, 0, 51)));
		btnDeleteAcount.setBounds(590, 41, 100, 20);
		contentPane.add(btnDeleteAcount);
		btnDeleteAcount.setForeground(Color.WHITE);
		btnDeleteAcount.setFont(new Font(FONT, Font.PLAIN, 10));
		btnDeleteAcount.setBackground(new Color(0, 128, 128));
		btnDeleteAcount.addActionListener(input -> {
				if(client.getWallet()==0 && client.getBet()==null) {
					facade.deleteAcount(client);
					mainFrame.setVisible(true);
					frame.dispose();
				}
		});
		btnLogout.addActionListener(input -> {
				mainFrame.setVisible(true);
				frame.dispose();
		});
	}
}
