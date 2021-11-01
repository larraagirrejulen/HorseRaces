package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import business_logic.BLFacade;
import domain.Admin;
import domain.Client;
import domain.Registered;

@SuppressWarnings("serial")
public class MainGUI extends JFrame {

	private static BLFacade appFacadeInterface;
	private MainGUI frame = this;
	private String language = "Etiquetas_en";
	private static final String FONT = "Verdana";
	private static final String PSWLBL = "Verdana";

	private JLabel lblError;
	private JPasswordField pwdPassword;
	private JTextField userNameField;
	private JButton registerButton;
	private JButton loginButton;
	private JButton viewRacesButton;
	private DefaultComboBoxModel<String> selectedLanguage = new DefaultComboBoxModel<>();


	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}

	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface = afi;
	}

	public void refresh() {
		language = selectedLanguage.getSelectedItem().toString();
		if(language.equals("English"))language="Etiquetas_en";
		else if(language.equals("Español"))language="Etiquetas_es";
		else language="Etiquetas_eus";
		this.pwdPassword.setText(PSWLBL);
		userNameField.setText(ResourceBundle.getBundle(language).getString("Username"));
		lblError.setText(ResourceBundle.getBundle(language).getString("UPerror"));
		loginButton.setText(ResourceBundle.getBundle(language).getString("Login"));
		registerButton.setText(ResourceBundle.getBundle(language).getString("Register"));
		viewRacesButton.setText(ResourceBundle.getBundle(language).getString("UpcomingRace"));
	}

	public MainGUI() {
		setLocation(new Point(610, 260));
		setUndecorated(true);
		setBackground(Color.WHITE);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(32, 178, 170));
		contentPane.setBorder(new LineBorder(new Color(0, 128, 128), 3));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setForeground(new Color(255, 255, 255));
		comboBox.setFont(new Font(FONT, Font.PLAIN, 11));
		comboBox.setModel(selectedLanguage);
		selectedLanguage.addElement("English");
		selectedLanguage.addElement("Español");
		selectedLanguage.addElement("Euskara");
		comboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		comboBox.setBackground(new Color(0, 128, 128));
		comboBox.setBounds(10, 11, 100, 20);
		comboBox.addActionListener(input -> refresh());
		contentPane.add(comboBox);

		userNameField = new JTextField();
		userNameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				String usname = userNameField.getText();
				if(usname.equals("User name") || usname.equals("Nombre de usuario") || usname.equals("Erabiltzaile izena")) userNameField.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				String usname = userNameField.getText();
				if(usname.length()==0)userNameField.setText(ResourceBundle.getBundle(language).getString("Username"));
			}
		});
		userNameField.setFont(new Font(FONT, Font.PLAIN, 11));
		userNameField.setHorizontalAlignment(SwingConstants.CENTER);
		userNameField.setBorder(new MatteBorder(0, 0, 3, 0,  new Color(0, 0, 51)));
		userNameField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		userNameField.setForeground(new Color(255, 255, 255));
		userNameField.setBackground(new Color(0, 128, 128));
		userNameField.setBounds(254, 184, 200, 35);
		contentPane.add(userNameField);
		userNameField.setColumns(10);

		pwdPassword = new JPasswordField();
		pwdPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(new String(pwdPassword.getPassword()).equals(PSWLBL)) pwdPassword.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(new String(pwdPassword.getPassword()).length()==0)pwdPassword.setText(PSWLBL);
			}
		});
		pwdPassword.setFont(new Font(FONT, Font.PLAIN, 11));
		pwdPassword.setHorizontalAlignment(SwingConstants.CENTER);
		pwdPassword.setBorder(new MatteBorder(0, 0, 3, 0,  new Color(0, 0, 51)));
		pwdPassword.setForeground(new Color(255, 255, 255));
		pwdPassword.setBackground(new Color(0, 128, 128));
		pwdPassword.setBounds(254, 230, 200, 35);
		contentPane.add(pwdPassword);

		loginButton = new JButton("");
		loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		loginButton.setBorder(new LineBorder(new Color(0, 0, 51)));
		loginButton.setFont(new Font(FONT, Font.PLAIN, 11));
		loginButton.setForeground(Color.WHITE);
		loginButton.setBackground(new Color(0, 51, 51));
		loginButton.addActionListener(input -> {Registered user = appFacadeInterface.login(userNameField.getText(), new String(pwdPassword.getPassword()));
												if(user == null) {
													lblError.setVisible(true);
												} else if(user instanceof Admin) {
													new AdminGUI(frame, language).setVisible(true);
													frame.setVisible(false);
												} else if(user instanceof Client) {
													new ClientGUI((Client)user, frame, language).setVisible(true);
													frame.setVisible(false);
												}});
		loginButton.setBounds(254, 276, 200, 35);
		contentPane.add(loginButton);

		lblError = new JLabel("Wrong username or password");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setFont(new Font(FONT, Font.ITALIC, 9));
		lblError.setBounds(254, 322, 200, 35);
		contentPane.add(lblError);
		lblError.setBackground(Color.WHITE);
		lblError.setForeground(new Color(0, 0, 51));


		//Button to register

		registerButton = new JButton("");
		registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		registerButton.setBorder(new LineBorder(new Color(0, 0, 51)));
		registerButton.setBounds(144, 368, 200, 35);
		contentPane.add(registerButton);
		registerButton.setForeground(Color.WHITE);
		registerButton.setFont(new Font(FONT, Font.PLAIN, 11));
		registerButton.setBackground(new Color(0, 128, 128));


		//Button to view races

		viewRacesButton = new JButton("");
		viewRacesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		viewRacesButton.setBorder(new LineBorder(new Color(0, 0, 51)));
		viewRacesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		viewRacesButton.setBounds(367, 368, 200, 35);
		contentPane.add(viewRacesButton);
		viewRacesButton.setForeground(Color.WHITE);
		viewRacesButton.setFont(new Font(FONT, Font.PLAIN, 11));
		viewRacesButton.setBackground(new Color(0, 128, 128));

		JLabel lblNewLabel = new JLabel("X");
		lblNewLabel.setBounds(640, 11, 50, 25);
		contentPane.add(lblNewLabel);
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		lblNewLabel.setFont(new Font(FONT, Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JLabel lblNewLabel1 = new JLabel("");
		lblNewLabel1.setIcon(new ImageIcon("src\\main\\resources\\icon.png"));
		lblNewLabel1.setBounds(302, 46, 133, 127);
		contentPane.add(lblNewLabel1);

		viewRacesButton.addActionListener(input -> {new ViewRacesGUI(frame, language).setVisible(true); frame.dispose();});

		registerButton.addActionListener(input -> {frame.setVisible(false); new RegisterGUI(frame, language).setVisible(true);});

		lblError.setVisible(false);
		refresh();

	}
}
