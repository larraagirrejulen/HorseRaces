package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;

import adapter.ClientBetAdapter;
import business_logic.BLFacade;
import domain.Client;
import javax.swing.JTable;

public class BetHistoryGUI extends JFrame {

	private JPanel contentPane;
	private static final String FONT = "Verdana";
	private BetHistoryGUI frame = this;
	private static BLFacade facade = MainGUI.getBusinessLogic();

	public void createTable(AbstractTableModel model) {        
	    JTable table=new JTable(model);    
	    table.setBounds(30,40,200,300);          
	    JScrollPane sp=new JScrollPane(table);    
	    frame.add(sp);
	}
	
	public BetHistoryGUI (Client client, ClientGUI clientFrame) {

		setUndecorated(true);

		setBounds(100, 100, 700, 500);
		setBackground(Color.WHITE);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(32, 178, 170));
		contentPane.setBorder(new LineBorder(new Color(0, 128, 128), 3));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		this.setLocationRelativeTo(null);

		this.createTable(new ClientBetAdapter(client, facade));
		
		
		//Button to log out

		JButton btnclose = new JButton("Close");
		btnclose.setBorder(new LineBorder(new Color(0, 0, 51)));
		btnclose.setBounds(590, 11, 100, 20);
		contentPane.add(btnclose);
		btnclose.setFont(new Font(FONT, Font.PLAIN, 10));
		btnclose.setForeground(Color.WHITE);
		btnclose.setBackground(new Color(0, 128, 128));
		btnclose.addActionListener(input -> {
			clientFrame.setVisible(true);
			frame.dispose();
		});
	}
}
