import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;

/**
 * User: Roger Philp
 * Date: 12-Jan-2006
 * Time: 20:40:59
 * This switchs the mode of the control frame between the manual and timed mode
 */
public class ModeListener implements ActionListener {
    private JPanel manualStartStopPanel;
    private JPanel timedStartStopPanel;
    private JButton captureBtn;
    private JButton stopBtn;
    private JSpinner numberOfBlocksSpinner;
    private ControlPanelComponents controlPanelComponents;

    /**
     * Constructtor supplied with the controlPanelComponets to switch between
     * the timed and manual panels
     *
     * @param controlPanelComponents
     */
    public ModeListener(ControlPanelComponents controlPanelComponents) {
        this.manualStartStopPanel = controlPanelComponents.getManualStartStopPanel();
        this.timedStartStopPanel = controlPanelComponents.getTimedStartStopPanel();
        this.captureBtn = controlPanelComponents.getCaptureBtn();
        this.stopBtn = controlPanelComponents.getStopBtn();
        this.numberOfBlocksSpinner = controlPanelComponents.getNumberOfBlocksSpinner();
        this.controlPanelComponents = controlPanelComponents;

    }

    /**
     * ActionPerformed switches the mode
     *
     * @param e is either event producer is either Manual or  timed
     */
    public void actionPerformed(ActionEvent e) {
        String ln = e.getActionCommand();

                    controlPanelComponents.getStartTimeLabel().setText(" ");
            controlPanelComponents.getEndTimeLabel().setText(" ");
            controlPanelComponents.getElapsedTimeLabel().setText(" ");
            controlPanelComponents.getDataAquiredLabel().setText(" ");
            controlPanelComponents.getBlocksWrittenLabel().setText("0");

        if (ln.equals("Manual")) {
            manualStartStopPanel.setVisible(true);
            timedStartStopPanel.setVisible(false);
            captureBtn.setEnabled(true);
            stopBtn.setEnabled(false);
            captureBtn.setVisible(true);
            stopBtn.setVisible(true);
            numberOfBlocksSpinner.setEnabled(true);
        } else {
            manualStartStopPanel.setVisible(false);
            timedStartStopPanel.setVisible(true);
            captureBtn.setEnabled(false);
            stopBtn.setEnabled(false);
            captureBtn.setVisible(false);
            stopBtn.setVisible(false);
            numberOfBlocksSpinner.setEnabled(false);
        }
    }
}
