
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
public class Enrollment implements Engine.EnrollmentCallback  {
    
    private final Reader reader;
    private final ImagePanel imagePanel;
    private final WindowInterface windowInterface;
    private int count = 0;
    private Engine engine;
    private Reader.CaptureResult result;
    private Fmd fmd;
    private JTextArea message;
    

    public Enrollment(Reader reader, JTextArea message, ImagePanel imagePanel, WindowInterface windowInterface) {
        this.reader = reader;
        this.message = message;
        this.imagePanel = imagePanel;
        this.windowInterface = windowInterface;
    }

    
    
    void start() {
        message.append("Inicio de enrolacion, por favor colocar dedo en el lector \n");
        windowInterface.update(windowInterface.getGraphics());
        engine = UareUGlobal.GetEngine();
        enrollFingerprint();
    }
    
    public void enrollFingerprint() {
        final Engine.EnrollmentCallback callBack = this;
        Fmd enrolledFmd = null;
        String enrolledBase64Fmd;
        Boolean status = false;
        try {
          enrolledFmd = engine.CreateEnrollmentFmd(Fmd.Format.ANSI_378_2004, callBack);
          enrolledBase64Fmd = DatatypeConverter.printBase64Binary(enrolledFmd.getData());
          status = windowInterface.operator.setFinger(enrolledBase64Fmd);
          if(status)
            message.append("Huella Enrolada correctamente \n");
          else
            message.append("Ocurrio un error al ingresar la huella, por favor intentelo nuevamente \n");
        } catch (UareUException ex) {
            Logger.getLogger(Enrollment.class.getName()).log(Level.SEVERE, null, ex);
            message.append("Ocurrio un error al enrolar la huella, por favor intentelo nuevamente \n");
        }
        windowInterface.update(windowInterface.getGraphics());
    }

    @Override
    public Engine.PreEnrollmentFmd GetFmd(Fmd.Format format) {
        Engine.PreEnrollmentFmd prefmd = new Engine.PreEnrollmentFmd();
        try {
            if(count > 0)
                message.append("Colocar el mismo dedo en el lector \n");
            count++;
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
            Logger.getLogger(Enrollment.class.getName()).log(Level.SEVERE, null, ex);
            message.append("Ocurrio un error durante la enrolacion, intentelo nuevamente \n");
            windowInterface.update(windowInterface.getGraphics());
        }

        try {
          fmd = engine.CreateFmd(result.image, Fmd.Format.ANSI_378_2004);
        } catch (UareUException ex) {
          Logger.getLogger(Enrollment.class.getName()).log(Level.SEVERE, null, ex);
          message.append("Ocurrio un error durante la enrolacion, intentelo nuevamente \n");
          windowInterface.update(windowInterface.getGraphics());
        }

        prefmd.fmd = fmd;
        prefmd.view_index = 0;

        return prefmd;
    }
}
