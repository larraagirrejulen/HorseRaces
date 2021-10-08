package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import business_logic.BLFacade;
import domain.Client;

@SuppressWarnings("serial")
public class WalletGUI extends JFrame {

	private static final String BALANCE_LBL = "Balance";
	private static final String INVALID_AMOUNT_LBL = "InvalidAmount";
	private static final String FONT = "Verdana";
	private WalletGUI frame = this;
	private static BLFacade facade;
	private JPanel contentPane;
	private JButton btnAddMoney;
	private JButton btnRestMoney;
	private JTextField textField;
	private Client client1;
	private JLabel currentBalancelbl;
	private JLabel lblError;

	public WalletGUI(Client client, ClientGUI clientFrame, String language) {
		setLocation(new Point(610, 260));
		setUndecorated(true);
		setBackground(Color.WHITE); //aaaaa

		client1 = client;
		LoginGUI.getBusinessLogic();

		setBounds(800, 800, 550, 400);
		contentPane = new JPanel();
		contentPane.setLocation(new Point(610, 260));
		contentPane.setBackground(new Color(32, 178, 170));
		contentPane.setBorder(new LineBorder(new Color(0, 128, 128), 3));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle(language).getString("AmountAddRest"));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(0, 0, 51));
		lblNewLabel.setFont(new Font(FONT, Font.PLAIN, 11));
		lblNewLabel.setBounds(178, 114, 200, 14);
		contentPane.add(lblNewLabel);

		lblError = new JLabel("");
		lblError.setBackground(Color.WHITE);
		lblError.setFont(new Font(FONT, Font.ITALIC, 9));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(188, 170, 179, 27);
		contentPane.add(lblError);

		textField = new JTextField();
		textField.setForeground(new Color(255, 255, 255));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBackground(new Color(0, 128, 128));
		textField.setBorder(new MatteBorder(0, 0, 3, 0, new Color(0, 0, 51)));
		textField.setFont(new Font(FONT, Font.PLAIN, 11));
		textField.setBounds(178, 139, 200, 20);
		contentPane.add(textField);
		textField.setColumns(10);


		//Button to rest money from the current clients wallet

		btnRestMoney = new JButton(ResourceBundle.getBundle(language).getString("RestMoney"));
		btnRestMoney.setBorder(new LineBorder(new Color(0, 0, 51)));
		btnRestMoney.setForeground(Color.WHITE);
		btnRestMoney.setFont(new Font(FONT, Font.PLAIN, 11));
		btnRestMoney.setBackground(new Color(0, 0, 51));
		btnRestMoney.setBounds(188, 242, 179, 23);
		contentPane.add(btnRestMoney);
		btnRestMoney.addActionListener(input -> {
				try {
					double amount = Double.parseDouble(textField.getText());
					if(amount>0 && amount<=client1.getWallet()) {
						client1 = facade.restMoney(client, amount);
						currentBalancelbl.setText(ResourceBundle.getBundle(language).getString(BALANCE_LBL) + client1.getWallet() + " $");
						lblError.setForeground(Color.BLACK);
						lblError.setText(ResourceBundle.getBundle(language).getString("MoneyRested"));
					}else {
						lblError.setForeground(Color.RED);
						lblError.setText(ResourceBundle.getBundle(language).getString(INVALID_AMOUNT_LBL));
					}
				}catch(java.lang.NumberFormatException ex) {
					lblError.setForeground(Color.RED);
					lblError.setText(ResourceBundle.getBundle(language).getString(INVALID_AMOUNT_LBL));
				}

		});


		//Button to add money to the current clients wallet

		btnAddMoney = new JButton(ResourceBundle.getBundle(language).getString("AddMoney"));
		btnAddMoney.setBorder(new LineBorder(new Color(0, 0, 51)));
		btnAddMoney.setForeground(Color.WHITE);
		btnAddMoney.setBackground(new Color(0, 0, 51));
		btnAddMoney.setFont(new Font(FONT, Font.PLAIN, 11));
		btnAddMoney.setBounds(188, 208, 179, 23);
		contentPane.add(btnAddMoney);
		btnAddMoney.addActionListener(input -> {
				try {
					double amount = Double.parseDouble(textField.getText());
					if(amount>0) {
						client1 = facade.addMoney(client, amount);
						currentBalancelbl.setText(ResourceBundle.getBundle(language).getString(BALANCE_LBL) + client1.getWallet() + " $");
						lblError.setForeground(Color.BLACK);
						lblError.setText(ResourceBundle.getBundle(language).getString("MoneyAdded"));
					}
				}catch(java.lang.NumberFormatException ex) {
					lblError.setForeground(Color.RED);
					lblError.setText(ResourceBundle.getBundle(language).getString(INVALID_AMOUNT_LBL));
				}
		});


		//Button to close current window

		JButton btnClose = new JButton(ResourceBundle.getBundle(language).getString("Back"));
		btnClose.setBorder(new LineBorder(new Color(0, 0, 51)));
		btnClose.setBounds(460, 8, 80, 20);
		contentPane.add(btnClose);
		btnClose.setFont(new Font(FONT, Font.PLAIN, 10));
		btnClose.setBackground(new Color(0, 128, 128));
		btnClose.setForeground(Color.WHITE);

		currentBalancelbl = new JLabel(ResourceBundle.getBundle(language).getString(BALANCE_LBL) + client.getWallet() + " $");
		currentBalancelbl.setOpaque(true);
		currentBalancelbl.setBounds(10, 8, 150, 20);
		contentPane.add(currentBalancelbl);
		currentBalancelbl.setHorizontalAlignment(SwingConstants.CENTER);
		currentBalancelbl.setFont(new Font(FONT, Font.PLAIN, 10));
		currentBalancelbl.setForeground(Color.WHITE);
		currentBalancelbl.setBackground(new Color(0, 128, 128));
		btnClose.addActionListener(input -> {
				clientFrame.setVisible(true);
				clientFrame.setClient(client1);
				frame.dispose();
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				clientFrame.setVisible(true);
				clientFrame.setClient(client1);
			}
		});
	}
}
