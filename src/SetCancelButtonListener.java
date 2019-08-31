import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;

/**
 * Data aquisition system
 * User: Roger Philp
 * Date: 16-Jan-2006
 * Time: 23:40:56
 * To change this template use File | Settings | File Templates.
 */
public class SetCancelButtonListener implements ActionListener {

    /**
     * This button sets the timer mode
     */
    private JButton setButton;

    /**
     * This button cancels the timer mode run it should also reset times etc
     */
    private JButton cancelButton;

    /**
     * controlframe components
     */
    ControlPanelComponents controlPanelComponents;


    /**
     * KickOff is the timer thread
     */
    KickOff kickOff = null;

    /**
     *
     */
    public SetCancelButtonListener(ControlPanelComponents controlPanelComponents) {
        this.setButton = controlPanelComponents.getSetButton();
        this.cancelButton = controlPanelComponents.getCancelButton();
        this.controlPanelComponents = controlPanelComponents;
    }

    boolean firstTime = true;

    /**
     * This checks the voracity of the start and stop dates
     * and then sets the thread running
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        String lnfName = e.getActionCommand();
        if (lnfName.equals("Set")) {
            Date date = new Date();
            if (controlPanelComponents.getEndTime() == null) {
                warning("End time not set", "End time error");
                return;
            } else if (controlPanelComponents.getStartTime() == null) {
                warning("Start time not set", "Start time error");
                return;
            } else if (controlPanelComponents.getStartTime().before(date)) {
                warning("Start time is before current time", "Start time error");
                return;
            } else if (controlPanelComponents.getStartTime().after(controlPanelComponents.getEndTime())) {
                warning("Start time is after end time", "Start time error");
                return;
            }

            controlPanelComponents.getShowStartTimerBtn().setEnabled(false);
            controlPanelComponents.getShowEndTimerBtn().setEnabled(false);
            controlPanelComponents.getBlockSizeSpinner().setEnabled(false);
            setButton.setEnabled(false);
            cancelButton.setEnabled(true);
            if (kickOff != null) kickOff = null;
            kickOff = new KickOff(controlPanelComponents);
            kickOff.start();

            //     controlPanelComponents..setEnabled(false);

        } else if (lnfName.equals("Cancel")) {
            setButton.setEnabled(true);
            cancelButton.setEnabled(false);
            kickOff.pleaseStop();
            controlPanelComponents.getShowStartTimerBtn().setEnabled(true);
            controlPanelComponents.getShowEndTimerBtn().setEnabled(true);
            controlPanelComponents.getBlockSizeSpinner().setEnabled(true);
        }

        controlPanelComponents.getBlockSizeSpinner().setEnabled(true);
        controlPanelComponents.getNumberOfBlocksSpinner().setEnabled(true);
        controlPanelComponents.getModePanel().setVisible(false);


    }

    /**
     * Pop up warning messages for badly set times
     *
     * @param message
     * @param frameLabel
     */
    public void warning(String message, String frameLabel) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JOptionPane.showMessageDialog(frame, message,
                frameLabel, JOptionPane.WARNING_MESSAGE);
    }
}
