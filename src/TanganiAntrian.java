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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TanganiAntrian extends JFrame {

	private JPanel contentPane;
	private JTextField textNamaAdmin;
	private JTable table;
	String[] head={"Username","Nomor Antrian","NIK","Keluhan","Tanggal Masuk"};
	DefaultTableModel t=new DefaultTableModel(null,head);
	private JTextField textKeluhan;
	private JTextField textNomorAntrian;
	private JButton btnTolak;
	String NamaAdmin;
	Koneksi koneksi=new Koneksi();
	Connection con=koneksi.getKoneksi();
	
	String sqlData = "SELECT Username,`Nomor Antrian`,`NIK`,Keluhan,`Tanggal Masuk` FROM antrian_aktif WHERE Status = 'ngantre'";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TanganiAntrian frame = new TanganiAntrian();
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
	public TanganiAntrian() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				getData(sqlData);
				
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 497);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNamaUser = new JLabel("Nama Admin\r\n");
		lblNamaUser.setBounds(10, 29, 75, 50);
		contentPane.add(lblNamaUser);
		
		textNamaAdmin = new JTextField();
		textNamaAdmin.setEditable(false);
		textNamaAdmin.setBounds(122, 44, 86, 20);
		contentPane.add(textNamaAdmin);
		textNamaAdmin.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 137, 643, 310);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				int row=table.getSelectedRow();
				if(row==-1){
					JOptionPane.showMessageDialog(null, "Pilih data terlebih dulu");
				}
				else{
					textNomorAntrian.setText(t.getValueAt(row, 1).toString());
					textKeluhan.setText(t.getValueAt(row, 3).toString());
					
				}
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(t);
		
		textKeluhan = new JTextField();
		textKeluhan.setEditable(false);
		textKeluhan.setBounds(260, 96, 86, 20);
		contentPane.add(textKeluhan);
		textKeluhan.setColumns(10);
		
		JLabel lblAntrianSaatIni = new JLabel("Antrian saat ini :");
		lblAntrianSaatIni.setBounds(63, 81, 81, 50);
		contentPane.add(lblAntrianSaatIni);
		
		textNomorAntrian = new JTextField();
		textNomorAntrian.setEditable(false);
		textNomorAntrian.setBounds(164, 96, 86, 20);
		contentPane.add(textNomorAntrian);
		textNomorAntrian.setColumns(10);
		
		JButton btnNewButton = new JButton("Tangani\r\n");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PreparedStatement ps;
				String sql="UPDATE dt_antrian SET status='keluar' WHERE id_antrian=?";
				try{
					ps=con.prepareStatement(sql);
					int row=table.getSelectedRow();
					ps.setString(1, table.getValueAt(row, 1).toString());
					int hasil=ps.executeUpdate();
					if (hasil>0) {
						JOptionPane.showMessageDialog(null, "Data TERTANGANI");
						getData(sqlData);
						textKeluhan.setText("");
						textNomorAntrian.setText("");
					}
					else{
						JOptionPane.showMessageDialog(null, "Data TIDAK TERTANGANI");
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnNewButton.setBounds(384, 95, 75, 23);
		contentPane.add(btnNewButton);
		
		JButton btnBelumDatang = new JButton("Belum Datang");
		btnBelumDatang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PreparedStatement ps;
				String sql="UPDATE dt_antrian SET status='tidak datang' WHERE id_antrian=?";
				try{
					ps=con.prepareStatement(sql);
					int row=table.getSelectedRow();
					ps.setString(1, table.getValueAt(row, 1).toString());
					int hasil=ps.executeUpdate();
					if (hasil>0) {
						JOptionPane.showMessageDialog(null, "Data TERTANGANI");
						getData(sqlData);
						textKeluhan.setText("");
						textNomorAntrian.setText("");
					}
					else{
						JOptionPane.showMessageDialog(null, "Data TIDAK TERTANGANI");
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnBelumDatang.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnBelumDatang.setBounds(471, 95, 99, 23);
		contentPane.add(btnBelumDatang);
		
		btnTolak = new JButton("Tolak\r\n");
		btnTolak.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btnTolak.setBounds(583, 95, 99, 23);
		contentPane.add(btnTolak);		
		
	}
	public void setNama(String namaad){
		NamaAdmin=namaad;
		textNamaAdmin.setText(NamaAdmin);
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
