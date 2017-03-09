
import javax.swing.JOptionPane;
import com.digitalpersona.uareu.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.xml.bind.DatatypeConverter.parseInt;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author juanko
 */
public class FingerPrint {

    private static Operator operator;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args[0] != null){
            operator = new Operator(parseInt(args[0]), parseInt(args[1]));
            WindowInterface interfaz = new WindowInterface(operator);
        }else{
            System.out.println("ID de operado no encontrado");
            System.exit(-1);
        }
    }
    
}
