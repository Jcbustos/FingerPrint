
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanko
 */

public final class Operator {
    private final int id;
    private final int jornada;
    private final ConnectionDB DB; 
    private final Connection con; 
    public String finger;
    public String name;
    public String rut;
    
    public Operator(int operator,int jornada){
        this.id = operator;
        this.jornada = jornada;
        DB = new ConnectionDB();
        con = DB.CreateConnection();
        getOperator();
        getFinger();
    }
    
    public void getOperator(){
        try {
            try (Statement stmt = con.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT rut, nombres, apellido_materno, apellido_paterno FROM operadores WHERE id='"+id+"'");
                
                while (rs.next()){
                    this.rut = rs.getString("rut");
                    this.name = rs.getString("nombres");
                    if(rs.getString("apellido_paterno") != null)
                        this.name += " "+rs.getString("apellido_paterno");
                    if(rs.getString("apellido_materno") != null)
                        this.name += " "+rs.getString("apellido_materno");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeConnection(){
        DB.close(con);
    }
    
    public void getFinger(){
        try {
            try (Statement stmt = con.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT xml_huella FROM huellas_operador WHERE operador_id='"+id+"'");
                
                while (rs.next()){
                    this.finger = rs.getString("xml_huella");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Boolean setFinger(String finger){
        try {
            Statement stmt;
            stmt = con.createStatement();
            String sql;
            if(this.finger == null){
                sql = "INSERT INTO huellas_operador(operador_id,xml_huella) VALUES ('"+this.id+"','"+finger+"')";
            }else{
                sql = "UPDATE huellas_operador set xml_huella = '"+finger+"' WHERE operador_id = '"+this.id+"'";
            }
            stmt.executeUpdate(sql);
            this.finger = finger;
            stmt.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public Boolean setAssistance(){
        try {
            System.out.println("SetAssistance");
            Statement stmt;
            stmt = con.createStatement();
            String sql;
            sql = "UPDATE asistencias set asiste = 'true' WHERE operador_id = '"+this.id+"' and jornada_id = '"+this.jornada+"'";
            System.out.println(sql);
            int result = stmt.executeUpdate(sql);
            stmt.close();
            System.out.println(result);
            return result == 1;
        } catch (SQLException ex) {
            Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
