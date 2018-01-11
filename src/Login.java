import java.awt.BorderLayout;
import java.awt.EventQueue;
//import java.awt.Menu;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//import jdk.nashorn.internal.scripts.JO;

import java.sql.PreparedStatement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	Koneksi koneksi = new Koneksi();
	Connection con = koneksi.getKoneksi();
	private JTextField textIDPegawai;
	private JPasswordField passwordField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIdPegawai = new JLabel("ID Pegawai");
		lblIdPegawai.setBounds(133, 55, 65, 14);
		contentPane.add(lblIdPegawai);
		
		textIDPegawai = new JTextField();
		textIDPegawai.setBounds(249, 52, 86, 20);
		contentPane.add(textIDPegawai);
		textIDPegawai.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password\r\n");
		lblPassword.setBounds(133, 80, 65, 14);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(249, 77, 86, 20);
		contentPane.add(passwordField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PreparedStatement ps;
				String sql="SELECT * FROM user WHERE username=? AND password=MD5(?) AND type='pegawai'";
				try{
					ResultSet rs;
					ps=con.prepareStatement(sql);
					ps.setString(1, textIDPegawai.getText());
					ps.setString(2, String.valueOf(passwordField.getPassword()));
					rs=ps.executeQuery();
					if(rs.next()
							){
						Admin_menu frMenu=new Admin_menu();
						frMenu.setVisible(true);
						frMenu.setAdmin(textIDPegawai.getText());
						setVisible(false);
						
					
					}
					else{
						JOptionPane.showMessageDialog(null, "Login Gagal");
					}
					
					
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		btnLogin.setBounds(176, 144, 89, 23);
		contentPane.add(btnLogin);
	}
}
