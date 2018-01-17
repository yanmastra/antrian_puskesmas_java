import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
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
import javax.swing.JTabbedPane;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

public class TanganiAntrian extends JFrame {

	private JPanel contentPane;
	private JTextField textNamaAdmin;
	private JTable table;
	public static String[] head={"Username","Nomor Antrian","Nomor Pasien","Keluhan","Tanggal Masuk"};
	public static DefaultTableModel t=new DefaultTableModel(null,head);
	DefaultTableModel t2=new DefaultTableModel(null,head);
	private JTextField textKeluhan;
	private JTextField textNomorAntrian;
	private JButton btnTolak;
	String NomorAdmin;
	String NamaAdmin;
	Koneksi koneksi=new Koneksi();
	Connection con=koneksi.getKoneksi();
	 boolean show=true;
	 String username;
	
	String sqlData = "SELECT Username,`Nomor Antrian`,`NIK`,Keluhan,`Tanggal Masuk` FROM antrian_aktif WHERE Status = 'ngantre'";

	String sqlData2 = "SELECT Username,`Nomor Antrian`,`NIK`,Keluhan,`Tanggal Masuk` FROM antrian_aktif WHERE Status = 'tidak datang'";
	private JTable table_1;
	private JTable table_2;
	
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
		setTitle("TANGANI ANTRIAN");
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				getData(sqlData);
				getData2(sqlData2);
				
				
				
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 493);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnSetting = new JMenu("Manage User");
		menuBar.add(mnSetting);
		
		JMenuItem mntmEditPegawai = new JMenuItem("Edit Profilku");
		mntmEditPegawai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProfilPegawai pp=new ProfilPegawai();
				pp.setNama(NamaAdmin);
				pp.setVisible(true);
			}
		});
		mnSetting.add(mntmEditPegawai);
		
		JMenuItem mntmEditPasien = new JMenuItem("Data Pasien");
		mntmEditPasien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DataPasien dataPasien = new DataPasien();
				dataPasien.setVisible(true);
				dataPasien.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		});
		mnSetting.add(mntmEditPasien);
		
		JMenu mnManageAntrian = new JMenu("Manage Antrian");
		menuBar.add(mnManageAntrian);
		
		JMenuItem mntmTambahAntrian = new JMenuItem("Tambah Antrian");
		mntmTambahAntrian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TambahAntrian tam=new TambahAntrian();
			
				tam.setVisible(true);
				
			}
		});
		mnManageAntrian.add(mntmTambahAntrian);
		
		JMenuItem mntmLihatRiwayatAntrian = new JMenuItem("Lihat Riwayat Antrian");
		mntmLihatRiwayatAntrian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Riwayat riwayat = new Riwayat();
				riwayat.setVisible(true);
				riwayat.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		});
		mnManageAntrian.add(mntmLihatRiwayatAntrian);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(31, 81, 643, 362);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Mengantri", null, panel, null);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setBounds(10, 45, 618, 244);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent Enter) {
				PreparedStatement ps;
				String sql="UPDATE dt_antrian SET keluhan=? ";
				try {
					ps=con.prepareStatement(sql);
					ps.setString(1, table.getValueAt(table.getSelectedRow(), 3).toString());
					ps.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
		
		JLabel lblAntrianSaatIni = new JLabel("Antrian saat ini :");
		lblAntrianSaatIni.setBounds(10, 0, 98, 50);
		panel.add(lblAntrianSaatIni);
		
		textNomorAntrian = new JTextField();
		textNomorAntrian.setBounds(118, 15, 122, 20);
		panel.add(textNomorAntrian);
		textNomorAntrian.setEditable(false);
		textNomorAntrian.setColumns(10);
		
		textKeluhan = new JTextField();
		textKeluhan.setBounds(250, 14, 86, 20);
		panel.add(textKeluhan);
		textKeluhan.setEditable(false);
		textKeluhan.setColumns(10);
		
		JButton btnTangani = new JButton("Tangani");
		btnTangani.setBounds(348, 11, 75, 23);
		panel.add(btnTangani);
		btnTangani.setVisible(show);
		btnTangani.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				String sql="SELECT no_pegawai FROM `dt_pegawai` WHERE `username`=?"; 
				ResultSet rs;
				PreparedStatement ps;
				try{
					ps=con.prepareStatement(sql);
					ps.setString(1, textNamaAdmin.getText());
					rs=ps.executeQuery();
					if(rs.next()){
						NomorAdmin=rs.getString(1);
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
						setData("UPDATE dt_antrian SET status='keluar', pegawai=?, tanggal_keluar = '"+format.format(new Date())+"' WHERE id_antrian=?","Data Berhasil Tertangani");
					}
					else{
						JOptionPane.showMessageDialog(null, "Login terlebih dahulu");
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		btnTangani.setFont(new Font(""
				+ ""
				+ "Tahoma", Font.PLAIN, 8));
		
		JButton btnBelumDatang = new JButton("Belum Datang");
		btnBelumDatang.setBounds(436, 11, 99, 23);
		panel.add(btnBelumDatang);
		btnBelumDatang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				setData("UPDATE dt_antrian SET status='tidak datang', pegawai=? WHERE id_antrian=?","Pasien Belum Datang");
				
			}
		});
		btnBelumDatang.setFont(new Font("Tahoma", Font.PLAIN, 8));
		
		btnTolak = new JButton("Tolak\r\n");
		btnTolak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null,"YAKIN INGIN MENGHAPUS ?","KONFIRMASI DIALOG",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				setData("UPDATE dt_antrian SET status='ditolak', pegawai=? WHERE id_antrian=?","Data berhasil ditolak");
				}
			}
		});
		btnTolak.setBounds(545, 11, 83, 23);
		panel.add(btnTolak);
		btnTolak.setFont(new Font("Tahoma", Font.PLAIN, 8));
		
		JPanel panel_1 = new JPanel();
		

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 618, 244);
		panel_1.add(scrollPane_1);
		
		table_2 = new JTable();
		
		scrollPane_1.setViewportView(table_2);
		table_2.setModel(t2);
	
		tabbedPane.addTab("Belum datang", null, panel_1, null);
		panel_1.setLayout(null);
		
		JButton btnSudahDatang = new JButton("Sudah Datang");
		btnSudahDatang.setBounds(252, 273, 129, 23);
		panel_1.add(btnSudahDatang);
		btnSudahDatang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IdGenerator generator = new IdGenerator();
				try{
					Statement st = con.createStatement();
					PreparedStatement ps = con.prepareStatement("SELECT * FROM antrian_aktif WHERE status='ngantre' LIMIT ?,1");
					ResultSet rs = st.executeQuery("SELECT * FROM antrian_aktif WHERE status='ngantre'");
					ResultSet rs1;
					rs.last();
					int jumlah = rs.getRow();
					String newValue = null;
					if(jumlah >= 5){
						ps.setInt(1, 4);
						rs1 = ps.executeQuery();
						rs1.first();
						newValue = rs1.getString(2);
					}else if(jumlah >0){
						ps.setInt(1, (jumlah-1));
						rs1 = ps.executeQuery();
						rs1.first();
						newValue = rs1.getString(2);
					}else{
						SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
						newValue = String.valueOf(format.format(new Date()));
						newValue = generator.getIdNumber(newValue, true);
					}
					newValue = generator.getIdNumber(newValue.substring(0,12), 2);
					setData2("UPDATE dt_antrian SET id_antrian='"+newValue+"', status='ngantre'  WHERE id_antrian=?");
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		});
		
		
		
		JLabel lblNamaUser = new JLabel("Nama Admin\r\n");
		lblNamaUser.setBounds(35, 30, 75, 50);
		contentPane.add(lblNamaUser);
		
		textNamaAdmin = new JTextField();
		textNamaAdmin.setBounds(120, 45, 310, 20);
		contentPane.add(textNamaAdmin);
		textNamaAdmin.setEditable(false);
		textNamaAdmin.setColumns(10);
		
		JLabel lblTambahAntrian = new JLabel("Tambah Antrian ?");
		lblTambahAntrian.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				TambahAntrian ta=new TambahAntrian();
				ta.setVisible(true);
			}
		});
		lblTambahAntrian.setForeground(Color.BLUE);
		lblTambahAntrian.setBounds(598, 11, 106, 14);
		contentPane.add(lblTambahAntrian);
		
		JButton btnRefresh = new JButton("Refresh(F5)");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getData(sqlData);
				getData2(sqlData2);
			}
		});
		btnRefresh.setBounds(492, 67, 150, 23);
		contentPane.add(btnRefresh);
		

		
		
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
	public void getData2(String sql){
		ResultSet rs;
		try{
			Statement st=con.createStatement();
			rs=st.executeQuery(sql);
			t2.fireTableDataChanged();
			t2.getDataVector().removeAllElements();
		
			while(rs.next()){
				Object obj[]=new Object[5];
				obj [0]=rs.getString(1);
				obj [1]=rs.getString(2);
				obj [2]=rs.getString(3);
				obj [3]=rs.getString(4);
				obj [4]=rs.getString(5);
				
				t2.addRow(obj);
			}
			rs.close();
			st.close();
			
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	public void setData(String sql,String info){
		PreparedStatement ps;
		
		try{
			ps=con.prepareStatement(sql);
			int row=table.getSelectedRow();
			ps.setString(1, NomorAdmin);
			ps.setString(2, textNomorAntrian.getText());
			int hasil=ps.executeUpdate();
			if (hasil>0) {
				JOptionPane.showMessageDialog(null, info);
				getData(sqlData);
				getData2(sqlData2);
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
	public void setData2(String sql){
		PreparedStatement ps;
		
		try{
			ps=con.prepareStatement(sql);
			int row=table_2.getSelectedRow();
			ps.setString(1, table_2.getValueAt(row, 1).toString());
			int hasil=ps.executeUpdate();
			if (hasil>0) {
				JOptionPane.showMessageDialog(null, "Data TERTANGANI");
				getData(sqlData);
				getData2(sqlData2);
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
	}

