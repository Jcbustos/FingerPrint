
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

    private static ReaderCollection readerCollection;
    private static Reader reader;
    private static Operator operator;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            if(args[0] != null){
                operator = new Operator(parseInt(args[0]), parseInt(args[1]));
                readerCollection = UareUGlobal.GetReaderCollection();
                System.out.println("Preparando el dispositivo");
                while(readerCollection.isEmpty()){
                    readerCollection = UareUGlobal.GetReaderCollection();
                    readerCollection.GetReaders();
                }
                System.out.println("El dispositivo esta listo");
                reader = readerCollection.get(0);
                WindowInterface interfaz = new WindowInterface(reader,operator);
                interfaz.setVisible(true);
            }else{
                System.out.println("ID de operado no encontrado");
            }
        } catch (UareUException ex) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, "Error al obtener la coleccion");
            return;
        }
        
        //Boolean verifier = Verifier.Run(m_reader);
        //System.out.println("Verifier");
        //System.out.println(verifier);
    }
    
}
