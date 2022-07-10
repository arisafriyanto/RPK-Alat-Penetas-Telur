package aplikasiperpustakaan;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Koneksi {
    Connection koneksi = null;
    public static Connection koneksiDB() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/aplikasi_perpustakaan","root","");
            return koneksi;
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }        
    }
}
