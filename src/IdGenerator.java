import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IdGenerator {
	private Koneksi koneksi = new Koneksi();
	private Connection con = koneksi.getKoneksi();
	String[] nol = new String[] {"","0","00","000", "0000"};
	String hasil = "";
	int index = 0;
	
	public String getIdNumber(String number, boolean x){
		int index = this.index;
		if(index==0) index = 4;
		if(x){
			try {
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM dt_antrian WHERE id_antrian LIKE '%"+number+"%'");
				rs.last();
				int jumlah = rs.getRow();
				int panjang = String.valueOf(jumlah).length();
				hasil = number+nol[index-panjang]+jumlah+"";
			}catch(SQLException e){
				e.printStackTrace();
			}
		}else{
			int panjang = number.length();
			String val = number.substring(panjang-2);
			int valHasil = Integer.parseInt(val)+1;
			int panjangHasil = String.valueOf(valHasil).length();
			
			hasil = number.substring(0, panjang-2) + nol[2-panjangHasil] + valHasil + ""; 
		}
		try{
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM dt_antrian WHERE id_antrian = '"+hasil+"'");
			if(rs.next()){
				return getIdNumber(hasil, false);
			}else{
				return hasil;
			}
		}catch(SQLException e){
			e.printStackTrace();
			return "";
		}
	}
	
	public String getIdNumber(String number, int index){
		this.index = index;
		return getIdNumber(number, true);
	}
}
