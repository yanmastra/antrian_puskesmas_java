import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DataPasien extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField txtCari;
	String[] head={"Username","NIK","Nama Lengkap","Tanggal Lahir","Tempat Lahir","Alamat","No Telepon"};
	DefaultTableModel tbModel=new DefaultTableModel(null,head);
	
	Koneksi koneksi=new Koneksi();
	Connection con=koneksi.getKoneksi();
	
	private JButton btnLihatRiwayat;
	
	private JTextField textUsername;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataPasien frame = new DataPasien();
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
	public DataPasien() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
			getData("SELECT * FROM dt_pasien ORDER BY username");
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 676, 476);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 64, 640, 362);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = table.getSelectedRow();
				textUsername.setText(table.getValueAt(row, 0).toString());
				btnLihatRiwayat.setEnabled(true);
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(tbModel);
		
		
		JButton btnCari = new JButton("Cari");
		btnCari.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getData("SELECT * FROM dt_pasien WHERE username Like '%"+txtCari.getText()+"%' OR nik Like '%"+txtCari.getText()+"%' OR nama_lengkap Like '%"+txtCari.getText()+"%' ORDER BY username");
			}
		});
		btnCari.setBounds(561, 30, 89, 23);
		contentPane.add(btnCari);
		
		txtCari = new JTextField();
		txtCari.setBounds(398, 33, 149, 20);
		contentPane.add(txtCari);
		txtCari.setColumns(10);
		
		btnLihatRiwayat = new JButton("Lihat Riwayat");
		btnLihatRiwayat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Riwayat riwayat = new Riwayat(textUsername.getText());
				riwayat.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				riwayat.setVisible(true);
			}
		});
		btnLihatRiwayat.setBounds(156, 10, 110, 23);
		btnLihatRiwayat.setEnabled(false);
		contentPane.add(btnLihatRiwayat);
		
		textUsername = new JTextField();
		textUsername.setEditable(false);
		textUsername.setBounds(10, 11, 136, 20);
		contentPane.add(textUsername);
		textUsername.setColumns(10);
	}
	public void getData(String sql){
		ResultSet rs;
		try{
			Statement st=con.createStatement();
			rs=st.executeQuery(sql);
			tbModel.fireTableDataChanged();
			tbModel.getDataVector().removeAllElements();
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
			while(rs.next()){
				Object obj[]=new Object[8];
				obj [0]=rs.getString(1);
				obj [1]=rs.getString(2);
				obj [2]=rs.getString(3);
				obj [3]=formatter.format(rs.getDate(4));
				obj [4]=rs.getString(5);
				obj [5]=rs.getString(6);
				obj [6]=rs.getString(7);
				
				tbModel.addRow(obj);
			}
			rs.close();
			st.close();			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}	
}
