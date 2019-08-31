import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 12-Jan-2006
 * Time: 15:50:23
 * To change this template use File | Settings | File Templates.
 */
public class ShowTimePanelListener implements ActionListener {
    private ControlFrame controlFrame;

    JButton captureBtn;
    JButton stopBtn;
    JLabel blocksWrittenLabel;
    JLabel startTimeLabel;
    JLabel endTimeLabel;
    JSpinner numberOfBlocksSpinner;
    ControlPanelComponents controlPanelComponents;

    public ShowTimePanelListener(ControlFrame controlFrame,
                                 ControlPanelComponents controlPanelComponents) {
        this.controlPanelComponents = controlPanelComponents;
        this.controlFrame = controlFrame;



        System.out.println("ShowTimePanelListener button listener constructor");
    }

    public void actionPerformed(ActionEvent e) {
        String lnfName;
        lnfName = e.getActionCommand();
        System.out.println("ShowTimePanelListener button listener action :" + lnfName);

        JCalendar jCalendar;

        jCalendar = new JCalendar(controlFrame,
                lnfName,
                controlPanelComponents);

        // clear the previous time labels
        if (lnfName.equals("Set start time")){
        controlPanelComponents.getStartTimeLabel().setText(" ");
        controlPanelComponents.getEndTimeLabel().setText(" ");
        }

        jCalendar.setVisible(true);
     //   jCalendar.setBackground(Color.black);
      //  jCalendar.setForeground(Color.black);
        controlFrame.setEnabled(false);
    }

}
