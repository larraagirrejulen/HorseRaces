package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class AdminGUI extends JFrame {

	private AdminGUI frame = this;
	private JPanel contentPane;

	public AdminGUI(LoginGUI mainFrame, String language) {
		setUndecorated(true);

		setBounds(800, 800, 500, 400);
		setBackground(Color.WHITE);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(32, 178, 170));
		contentPane.setBorder(new LineBorder(new Color(0, 128, 128), 3));
		contentPane.setLayout(null);
		setContentPane(contentPane);


		//Button to create races and add horses

		JButton btnCreateRace = new JButton(ResourceBundle.getBundle(language).getString("CreateRace"));
		btnCreateRace.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnCreateRace.setForeground(Color.WHITE);
		btnCreateRace.setBackground(new Color(0, 0, 51));
		btnCreateRace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame gui = new CreateRaceGUI(frame, language);
				gui.setVisible(true);
				frame.dispose();
			}
		});
		btnCreateRace.setBounds(154, 137, 200, 35);
		contentPane.add(btnCreateRace);


		//Button set race results

		JButton btnSetResults = new JButton(ResourceBundle.getBundle(language).getString("SetResults"));
		btnSetResults.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnSetResults.setForeground(Color.WHITE);
		btnSetResults.setBackground(new Color(0, 0, 51));
		btnSetResults.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame gui = new HorseResultsGUI(frame, language);
				gui.setVisible(true);
				frame.dispose();
			}
		});
		btnSetResults.setBounds(154, 229, 200, 35);
		contentPane.add(btnSetResults);


		//Button to log out

		JButton btnLogout = new JButton(ResourceBundle.getBundle(language).getString("Logout"));
		btnLogout.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnLogout.setBounds(410, 11, 80, 20);
		contentPane.add(btnLogout);
		btnLogout.setForeground(Color.WHITE);
		btnLogout.setBackground(new Color(0, 128, 128));
		btnLogout.setFont(new Font("Verdana", Font.PLAIN, 10));
		btnLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setVisible(true);
				frame.dispose();
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainFrame.setVisible(true);
			}
		});

	}
}
