package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import business_logic.BLFacade;

@SuppressWarnings("serial")
public class RegisterGUI extends JFrame {

	private static final String FONT = "Verdana";
	private static BLFacade facade;
	private RegisterGUI nireFrame = this;

	public RegisterGUI(LoginGUI mainFrame, String language) {
		setLocation(new Point(610, 260));
		setUndecorated(true);
		setBackground(Color.WHITE);

		facade = LoginGUI.getBusinessLogic();

		setBounds(100, 100, 700, 500);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(32, 178, 170));
		contentPane.setBorder(new LineBorder(new Color(0, 128, 128), 3));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextField userNameField = new JTextField();
		userNameField.setBorder(new MatteBorder(0, 0, 3, 0,  new Color(0, 0, 51)));
		userNameField.setBackground(new Color(0, 128, 128));
		userNameField.setForeground(new Color(255, 255, 255));
		userNameField.setFont(new Font(FONT, Font.PLAIN, 11));
		userNameField.setBounds(250, 131, 200, 35);
		contentPane.add(userNameField);
		userNameField.setColumns(10);

		JPasswordField pwdPassword = new JPasswordField();
		pwdPassword.setBorder(new MatteBorder(0, 0, 3, 0,  new Color(0, 0, 51)));
		pwdPassword.setForeground(new Color(255, 255, 255));
		pwdPassword.setBackground(new Color(0, 128, 128));
		pwdPassword.setFont(new Font(FONT, Font.PLAIN, 11));
		pwdPassword.setBounds(250, 200, 200, 35);
		contentPane.add(pwdPassword);

		JPasswordField pwdRepeatPassword = new JPasswordField();
		pwdRepeatPassword.setBorder(new MatteBorder(0, 0, 3, 0,  new Color(0, 0, 51)));
		pwdRepeatPassword.setBackground(new Color(0, 128, 128));
		pwdRepeatPassword.setForeground(new Color(255, 255, 255));
		pwdRepeatPassword.setFont(new Font(FONT, Font.PLAIN, 11));
		pwdRepeatPassword.setBounds(250, 268, 200, 35);
		contentPane.add(pwdRepeatPassword);

		JLabel lblError = new JLabel("User already exists");
		lblError.setFont(new Font(FONT, Font.ITALIC, 9));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setBounds(236, 314, 231, 32);
		contentPane.add(lblError);
		lblError.setVisible(false);

		JLabel lblUsername = new JLabel(ResourceBundle.getBundle(language).getString("Username") + ":");
		lblUsername.setForeground(new Color(0, 0, 51));
		lblUsername.setBackground(Color.WHITE);
		lblUsername.setFont(new Font(FONT, Font.PLAIN, 11));
		lblUsername.setBounds(250, 97, 200, 35);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel(ResourceBundle.getBundle(language).getString("Password"));
		lblPassword.setBackground(Color.WHITE);
		lblPassword.setForeground(new Color(0, 0, 51));
		lblPassword.setFont(new Font(FONT, Font.PLAIN, 11));
		lblPassword.setBounds(250, 165, 200, 35);
		contentPane.add(lblPassword);

		JLabel lblRepeatPassowrd = new JLabel(ResourceBundle.getBundle(language).getString("RepeatPassword"));
		lblRepeatPassowrd.setForeground(new Color(0, 0, 51));
		lblRepeatPassowrd.setBackground(Color.WHITE);
		lblRepeatPassowrd.setFont(new Font(FONT, Font.PLAIN, 11));
		lblRepeatPassowrd.setBounds(250, 235, 200, 35);
		contentPane.add(lblRepeatPassowrd);


		//Button to register new client

		JButton registerButton = new JButton(ResourceBundle.getBundle(language).getString("Register"));
		registerButton.setBorder(new LineBorder(new Color(0, 0, 51)));
		registerButton.setBackground(new Color(0, 0, 51));
		registerButton.setForeground(Color.WHITE);
		registerButton.setFont(new Font(FONT, Font.PLAIN, 11));
		registerButton.setBounds(250, 357, 200, 35);
		contentPane.add(registerButton);
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String password = new String(pwdPassword.getPassword());
				String password2 = new String(pwdRepeatPassword.getPassword());
				if(password.equals(password2)) {
					boolean correct = facade.register(userNameField.getText(), password);
					if(!correct) {
						lblError.setText(ResourceBundle.getBundle(language).getString("AlreadyExists"));
						lblError.setForeground(new Color(255, 0, 0));
						lblError.setVisible(true);
					}
					else {
						lblError.setText(ResourceBundle.getBundle(language).getString("Registered"));
						lblError.setForeground(new Color(10, 10, 10));
						lblError.setVisible(true);
						userNameField.setText("");
						pwdPassword.setText("");
						pwdRepeatPassword.setText("");
					}
				}
				else {
					lblError.setText(ResourceBundle.getBundle(language).getString("PasswordNoMach"));
					lblError.setForeground(new Color(255, 0, 0));
					lblError.setVisible(true);
				}
			}
		});


		//Button to go to previous interface

		JButton btnClose = new JButton(ResourceBundle.getBundle(language).getString("Back"));
		btnClose.setBorder(new LineBorder(new Color(0, 0, 51)));
		btnClose.setBounds(610, 11, 80, 20);
		contentPane.add(btnClose);
		btnClose.setBackground(new Color(0, 128, 128));
		btnClose.setForeground(Color.WHITE);
		btnClose.setFont(new Font(FONT, Font.PLAIN, 10));
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.setVisible(true);
				nireFrame.dispose();
			}
		});

	}

}