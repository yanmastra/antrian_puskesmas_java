import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Admin_menu extends JFrame {

	private JPanel contentPane;
	String username;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin_menu frame = new Admin_menu();
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
	public Admin_menu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 630, 456);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnSetting = new JMenu("Setting ");
		menuBar.add(mnSetting);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mnSetting.add(mntmLogout);
		
		JMenuItem mntmSettingAdmin = new JMenuItem("Setting Admin");
		mnSetting.add(mntmSettingAdmin);
		
		JMenu mnManageAntrian = new JMenu("Manage Antrian");
		menuBar.add(mnManageAntrian);
		
		JMenuItem mntmTanganiAntrian = new JMenuItem("Tangani Antrian");
		mntmTanganiAntrian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TanganiAntrian ta=new TanganiAntrian();
				TambahAntrian tam=new TambahAntrian();
				ta.setVisible(true);
				ta.setNama(username);
				tam.setNama(username);
			}
		});
		mnManageAntrian.add(mntmTanganiAntrian);
		
		JMenuItem mntmTambahkanAntrian = new JMenuItem("Tambahkan Antrian");
		mnManageAntrian.add(mntmTambahkanAntrian);
		
		JMenuItem mntmLihatRiwayat = new JMenuItem("Lihat Riwayat");
		mnManageAntrian.add(mntmLihatRiwayat);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}
	public void setAdmin(String admin){
		username=admin;	
	}

}
