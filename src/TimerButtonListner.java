import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;

/**
 * Data Aquisition
 * User: Roger Philp
 * Date: 12-Jan-2006
 * Time: 15:38:16
 * To change this template use File | Settings | File Templates.
 */
public class TimerButtonListner implements ActionListener {
    private ControlFrame controlFrame;
    private JCalendar jcalendar;
    private String lnfName;
     ControlPanelComponents controlPanelComponents;

    public TimerButtonListner(ControlFrame controlFrame,
                              JCalendar jcalendar,
                              String lnfName,
                              ControlPanelComponents controlPanelComponents) {

        this.controlPanelComponents = controlPanelComponents;

        this.controlFrame = controlFrame;
        this.jcalendar = jcalendar;
        this.lnfName = lnfName;
    }

    /**
     * 
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        Date startTime = null;
        Date endTime = null;
        System.out.println("Setting times is " + startTime + " ------- " + endTime);
        System.out.println(lnfName);

        jcalendar.setVisible(false);
        controlFrame.setEnabled(true);
        controlFrame.setVisible(true);
        if (lnfName.equals("Set start time")) {
            controlFrame.setStartDate(jcalendar.getDate());
            startTime = jcalendar.getDate();
            controlPanelComponents.setStartTime(startTime);
        } else {
            controlFrame.setEndDate(jcalendar.getDate());
            endTime = jcalendar.getDate();
            controlPanelComponents.setEndTime(endTime);
            System.out.println("End time is " + endTime);
        }
    }
}
