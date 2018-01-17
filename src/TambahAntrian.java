import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.demo.DateChooserPanel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TambahAntrian extends JFrame {

	private JPanel contentPane;
	private JTextField textKeluhan;
	Koneksi k=new Koneksi();
	Connection con=k.getKoneksi();
	String ID_kategori[];
	DefaultComboBoxModel cbModel=new DefaultComboBoxModel();
	private JTextField textUser;
	String NamaAdmin="Unknown";
	private IdGenerator generator = new IdGenerator();

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private JTextField textNoTelpon;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TambahAntrian frame = new TambahAntrian();
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
	public TambahAntrian() {
		setTitle("TAMBAH ANTRIAN");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 495, 339);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTambahkanAntrian = new JLabel("Tambahkan Antrian");
		lblTambahkanAntrian.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTambahkanAntrian.setBounds(155, 29, 204, 14);
		contentPane.add(lblTambahkanAntrian);
		
		JLabel lblNamaPasien = new JLabel("Username Pasien");
		lblNamaPasien.setBounds(114, 67, 96, 14);
		contentPane.add(lblNamaPasien);
		
		textKeluhan = new JTextField();
		textKeluhan.setBounds(273, 89, 86, 20);
		contentPane.add(textKeluhan);
		textKeluhan.setColumns(10);
		
		JLabel lblKeluhan = new JLabel("Keluhan");
		lblKeluhan.setBounds(114, 92, 46, 14);
		contentPane.add(lblKeluhan);
		
		textUser = new JTextField();
		textUser.setBounds(273, 60, 86, 20);
		contentPane.add(textUser);
		textUser.setColumns(10);
		
		textNoTelpon = new JTextField();
		textNoTelpon.setBounds(273, 123, 86, 20);
		contentPane.add(textNoTelpon);
		textNoTelpon.setColumns(10);
		
		JLabel lblNoTelpon = new JLabel("No Telpon");
		lblNoTelpon.setBounds(114, 126, 96, 14);
		contentPane.add(lblNoTelpon);
		
		JButton btnTambahkan = new JButton("Tambahkan");
		btnTambahkan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("SELECT nik FROM dt_pasien WHERE username = '"+textUser.getText()+"'");
					if(rs.next()){
						SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
						Date date = new Date();
						String id = generator.getIdNumber(formater.format(date), true);
						try{
							PreparedStatement ps = con.prepareStatement("INSERT INTO dt_antrian(id_antrian, pasien, keluhan, no_telp) VALUES (?,?,?,?)");
							ps.setString(1, id);
							rs.first();
							ps.setString(2, rs.getString(1));
							ps.setString(3, textKeluhan.getText());
							ps.setString(4, textNoTelpon.getText());
							int res = ps.executeUpdate();
							if(res > 0 ){
								JOptionPane.showMessageDialog(null, "Antrian berhasil ditambahkan!");
								setVisible(false);
							}else{
								JOptionPane.showMessageDialog(null, "GAGAL");
							}
						}catch(SQLException e){
							e.printStackTrace();
							if(e.getErrorCode()==1406){
								JOptionPane.showMessageDialog(null, "Data Terlalu panjang");
							}
							else{}
							JOptionPane.showMessageDialog(null, "Terjadi kesalahan,silahkan hubungi admin !!!");
						}
					}else{
						JOptionPane.showMessageDialog(null, "Username tidak ada");
					}
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		});
		btnTambahkan.setBounds(191, 208, 89, 23);
		contentPane.add(btnTambahkan);
	}
	public void setNama(String namaad){
		NamaAdmin=namaad;
	}

	
	public void setPegawai(String pegawai){
		NamaAdmin=pegawai;
	}
}
