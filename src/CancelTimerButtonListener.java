import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 17-Jan-2006
 * Time: 00:31:32
 * To change this template use File | Settings | File Templates.
 */
public class CancelTimerButtonListener implements ActionListener {
    JFrame calendar;
    ControlFrame controlFrame;
       public CancelTimerButtonListener(JFrame calendar, ControlFrame controlFrame){
           this.calendar = calendar;
           this.controlFrame = controlFrame;
       }

        public void actionPerformed(ActionEvent e) {
            calendar.setVisible(false);
            controlFrame.setEnabled(true);
            controlFrame.toFront();           
        }
}
