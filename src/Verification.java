
import com.digitalpersona.uareu.Engine;
import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.UareUException;
import com.digitalpersona.uareu.UareUGlobal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author juanko
 */
public class Verification {
    private Engine engine;
    private Reader.CaptureResult result;
    private Fmd fmd, oldEnrolledFmd;
    private String oldBase64, newBase64;
    
    private final Reader reader;
    private final JTextArea message;
    private final ImagePanel imagePanel;
    private final WindowInterface windowInterface;
    private int validate;

    public Verification(Reader reader, JTextArea message, ImagePanel imagePanel, WindowInterface windowInterface) {
        this.reader = reader;
        this.message = message;
        this.imagePanel = imagePanel;
        this.windowInterface = windowInterface;
    }
    
    public boolean start(){
        engine = UareUGlobal.GetEngine();
        readFingerprint();
        getFinger();
        try {
            validate = engine.Compare(this.fmd, 0, oldEnrolledFmd, 0);
        } catch (UareUException ex) {
            Logger.getLogger(Verification.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(validate == 0){
            if(windowInterface.operator.setAssistance()){
                message.append("Huella validada correctamente \n");
                windowInterface.update(windowInterface.getGraphics());
                return true;
            }else{
                message.append("No se pudo guardar la asistencia, intentelo nuevamente \n");
                windowInterface.update(windowInterface.getGraphics());
                return false;
            }
        }else{
            message.append("Huella no coincide con la Huella enrolada \n");
            windowInterface.update(windowInterface.getGraphics());
            return false;
        }
    }
    
    public void getFinger(){
        String enrolledBase64Fmd = windowInterface.operator.finger;
        byte[] enrolledFmdBytes = DatatypeConverter.parseBase64Binary(enrolledBase64Fmd);
        try {
            this.oldEnrolledFmd = UareUGlobal.GetImporter().ImportFmd(enrolledFmdBytes, Fmd.Format.ANSI_378_2004, Fmd.Format.ANSI_378_2004);
        } catch (UareUException ex) {
            Logger.getLogger(Verification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void readFingerprint() {
        try {
            message.append("Colocar dedo en el lector \n");
            windowInterface.update(windowInterface.getGraphics());
            result = reader.Capture(
                                    Fid.Format.ANSI_381_2004, 
                                    Reader.ImageProcessing.IMG_PROC_DEFAULT, 
                                    500, 
                                    -1
                                );
            imagePanel.showImage(result.image);
            windowInterface.update(windowInterface.getGraphics());
        } catch (UareUException ex) {
          Logger.getLogger(Verification.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fmd = engine.CreateFmd(result.image, Fmd.Format.ANSI_378_2004);
        } catch (UareUException ex) {
            Logger.getLogger(Verification.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.newBase64 = DatatypeConverter.printBase64Binary(this.fmd.getData());
        
      }
}
