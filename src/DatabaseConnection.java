import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public static void main(String[] args) {
        DatabaseConnection dbconnection = new DatabaseConnection();
    }

    public Connection get_connection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/satti",
                    "root", "S@tti0630");
            if(con != null) {
                System.out.println("Connection Initiated");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }
}
