
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanko
 */
public class ConnectionDB {
    String driver, connectString, user, password;
        
    public Connection CreateConnection(){
        driver = "org.postgresql.Driver";
        connectString = "jdbc:postgresql://localhost:5432/counter";
        user = "counter";
        password = "counter.1234";
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(connectString, user , password);
            return con;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public void close(Connection con){
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
