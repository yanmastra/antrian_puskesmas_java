import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Riwayat extends JFrame {

	private JPanel contentPane;
	private JTable table;
	Koneksi koneksi=new Koneksi();
	Connection con=koneksi.getKoneksi();
	String[] head={"Username","Nomor Antrian","Keluhan","Tanggal Keluar","Nama Pegawai"};
	DefaultTableModel t=new DefaultTableModel(null,head);
	private JTextField textCari;
	String sqldata="SELECT `Username Pasien`,`Nomor Antrian`,Keluhan,`Tanggal Keluar`,`Nama Pegawai` FROM riwayat_antrian";
	JButton btnCari;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Riwayat frame = new Riwayat();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Riwayat(String username){
		this();
		this.sqldata = "SELECT `Username Pasien`,`Nomor Antrian`,Keluhan,`Tanggal Keluar`,`Nama Pegawai` FROM riwayat_antrian WHERE `Username Pasien` = '"+username+"'";
		getData(sqldata);
		textCari.setVisible(false);
		btnCari.setVisible(false);
	}
	public Riwayat() {
		setTitle("RIWAYAT");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				getData(sqldata);
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 611, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 58, 575, 230);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(t);
		
		textCari = new JTextField();
		textCari.setBounds(362, 11, 143, 20);
		contentPane.add(textCari);
		textCari.setColumns(10);
		
		btnCari = new JButton("Cari");
		btnCari.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getData("SELECT `Username Pasien`,`Nomor Antrian`,Keluhan,`Tanggal Keluar`,`Nama Pegawai` FROM riwayat_antrian WHERE `Username Pasien` LIKE '%"+textCari.getText()+"%' ORDER BY `Username Pasien`");
			}
		});
		btnCari.setBounds(506, 10, 79, 23);
		contentPane.add(btnCari);
		
		label = new JLabel("");
		label.setBounds(10, 11, 46, 14);
		contentPane.add(label);
	}
	public void getData(String sql){
		ResultSet rs;
		try{
			Statement st=con.createStatement();
			rs=st.executeQuery(sql);
			t.fireTableDataChanged();
			t.getDataVector().removeAllElements();
		
			while(rs.next()){
				Object obj[]=new Object[5];
				obj [0]=rs.getString(1);
				obj [1]=rs.getString(2);
				obj [2]=rs.getString(3);
				obj [3]=rs.getString(4);
				obj [4]=rs.getString(5);
				t.addRow(obj);
			}
			rs.close();
			st.close();
			
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}	
}
