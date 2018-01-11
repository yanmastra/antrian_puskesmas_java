

	import java.sql.Connection;
	import java.sql.DriverManager;

	import javax.swing.JOptionPane;


	public class Koneksi {
		Connection con;
		public Connection getKoneksi(){
			if(con==null){
				try{
					Class.forName("com.mysql.jdbc.Driver");
					con=DriverManager.getConnection("jdbc:mysql://localhost/db_puskesmas","root","");
				}
				catch(ClassNotFoundException e1){
					JOptionPane.showMessageDialog(null,e1.getMessage());
				}catch(Exception e2){
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
			}
			return con;
		}
	}


