import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Statement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProfilPegawai extends JFrame {

	private JPanel contentPane;
	private JTextField txtUserName;
	private JTextField txtNoPegawai;
	private JTextField txtNamaLengkap;
	private JTextField txtTempatLahir;
	private JTextField txtAlamat;
	private JLabel lblJenisKelamin;
	private JLabel lblNoTelepon;
	private JTextField txtNoTelepon;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	String NamaAdmin;
	String NomorPegawai;
	
	private JRadioButton rdbtnLakiLaki, rdbtnPerempuan;

	/**
	 * Launch the application.
	 */
	
	Koneksi koneksi=new Koneksi();
	Connection con=koneksi.getKoneksi();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JButton btnSimpanPerubahan;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfilPegawai frame = new ProfilPegawai();
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
	public ProfilPegawai() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
			
			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(44, 63, 86, 14);
		contentPane.add(lblUsername);
		
		txtUserName = new JTextField();
		txtUserName.setEditable(false);
		txtUserName.setBounds(154, 60, 220, 20);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);
		
		JLabel lblNoPegawai = new JLabel("No Pegawai");
		lblNoPegawai.setBounds(44, 91, 86, 14);
		contentPane.add(lblNoPegawai);
		
		txtNoPegawai = new JTextField();
		txtNoPegawai.setEditable(false);
		txtNoPegawai.setColumns(10);
		txtNoPegawai.setBounds(154, 88, 220, 20);
		contentPane.add(txtNoPegawai);
		
		JLabel lblNamaLengkap = new JLabel("Nama Lengkap");
		lblNamaLengkap.setBounds(44, 119, 86, 14);
		contentPane.add(lblNamaLengkap);
		
		txtNamaLengkap = new JTextField();
		txtNamaLengkap.setColumns(10);
		txtNamaLengkap.setBounds(154, 116, 220, 20);
		contentPane.add(txtNamaLengkap);
		
		JLabel lblTempatLahir = new JLabel("Tempat Lahir");
		lblTempatLahir.setBounds(44, 147, 86, 14);
		contentPane.add(lblTempatLahir);
		
		txtTempatLahir = new JTextField();
		txtTempatLahir.setColumns(10);
		txtTempatLahir.setBounds(154, 144, 220, 20);
		contentPane.add(txtTempatLahir);
		
		JLabel lblAlamat = new JLabel("Alamat");
		lblAlamat.setBounds(44, 175, 86, 14);
		contentPane.add(lblAlamat);
		
		txtAlamat = new JTextField();
		txtAlamat.setColumns(10);
		txtAlamat.setBounds(154, 172, 220, 20);
		contentPane.add(txtAlamat);
		
		lblJenisKelamin = new JLabel("Jenis Kelamin");
		lblJenisKelamin.setBounds(44, 203, 86, 14);
		contentPane.add(lblJenisKelamin);
		
		lblNoTelepon = new JLabel("No Telepon");
		lblNoTelepon.setBounds(44, 231, 86, 14);
		contentPane.add(lblNoTelepon);
		
		txtNoTelepon = new JTextField();
		txtNoTelepon.setColumns(10);
		txtNoTelepon.setBounds(154, 228, 220, 20);
		contentPane.add(txtNoTelepon);
		
		rdbtnLakiLaki = new JRadioButton("Laki - Laki");
		buttonGroup.add(rdbtnLakiLaki);
		rdbtnLakiLaki.setBounds(154, 199, 109, 23);
		contentPane.add(rdbtnLakiLaki);
		
		rdbtnPerempuan = new JRadioButton("Perempuan");
		buttonGroup.add(rdbtnPerempuan);
		rdbtnPerempuan.setBounds(265, 199, 109, 23);
		contentPane.add(rdbtnPerempuan);
		
		btnSimpanPerubahan = new JButton("Simpan Perubahan");
		btnSimpanPerubahan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PreparedStatement ps;
				String sql= "UPDATE dt_pegawai SET nama_lengkap=?,tempat_lahir=?,alamat=?,jenis_kelamin=?, no_telp=? WHERE username=? ";
				try {
					ps=con.prepareStatement(sql);
					ps.setString(1, txtNamaLengkap.getText());
					ps.setString(2, txtTempatLahir.getText());
					ps.setString(3, txtAlamat.getText());
					if(rdbtnLakiLaki.isSelected()){
						ps.setString(4, "Laki-laki");
					}else{
						ps.setString(4, "Perempuan");
					}
					ps.setString(5, txtNoTelepon.getText());
					ps.setString(6, NamaAdmin);
					int hasil = ps.executeUpdate();
					if(hasil>0){
						JOptionPane.showMessageDialog(null, "Data Berasil Di UBAH ");
						setVisible(false);
					}else{
						JOptionPane.showMessageDialog(null, "Data Gagal diubah ");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnSimpanPerubahan.setBounds(154, 259, 168, 23);
		contentPane.add(btnSimpanPerubahan);
	}
	public void setNama(String namapegawai){
		NamaAdmin=namapegawai;
		txtUserName.setText(NamaAdmin);
		ResultSet rs;
		try{
			java.sql.Statement st=con.createStatement();
			rs=st.executeQuery("SELECT * FROM dt_pegawai WHERE username = '"+NamaAdmin+"'");
			rs.first();
			txtNoPegawai.setText(rs.getString(2));
			txtNamaLengkap.setText(rs.getString(3));
			txtTempatLahir.setText(rs.getString(4));
			txtAlamat.setText(rs.getString(5));
			if(rs.getString(6).equals("Laki-laki")){
				rdbtnLakiLaki.setSelected(true);
			}else{
				rdbtnPerempuan.setSelected(true);
			}
			txtNoTelepon.setText(rs.getString(7));
		}catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}
