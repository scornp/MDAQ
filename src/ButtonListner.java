import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 27-Dec-2005
 * Time: 01:24:38
 * This class listens only to the start and stop buttons
 * gracefully starting new runs and stopping existing ones
 */
class ButtonListner implements ActionListener {
    private JButton captureBtn, stopBtn;
    int blockSize;
    int numberOfBlocks;
    byte [][] newBuffer;
    int numberOfBuffers;
    CaptureDefaults captureDefaults;
    JLabel startTimeLabel;
    JLabel endTimeLabel;
    JSpinner blockSizeSpinner;
    JSpinner numberOfBlocksSpinner;
    ControlPanelComponents controlPanelComponents;
    Date date;
    long startTimeMills;
    long endTimeMills;
    DataCapture dataCapture = null;
    LinkList linkList = null;
    DataWriter dataWriter = null;

    public ButtonListner(ControlPanelComponents controlPanelComponents) {
        this.controlPanelComponents = controlPanelComponents;
        this.captureBtn = controlPanelComponents.getCaptureBtn();
        this.stopBtn = controlPanelComponents.getStopBtn();
        this.startTimeLabel = controlPanelComponents.getStartTimeLabel();
        this.endTimeLabel = controlPanelComponents.getEndTimeLabel();
        this.captureDefaults = controlPanelComponents.getCaptureDefaults();
        this.blockSizeSpinner = controlPanelComponents.getBlockSizeSpinner();
        this.numberOfBlocksSpinner = controlPanelComponents.getNumberOfBlocksSpinner();
        this.numberOfBuffers = controlPanelComponents.getNumberOfBuffers();
        this.blockSize = controlPanelComponents.getBlockSize();
        this.numberOfBlocks = controlPanelComponents.getNumberOfBlocks();
    }


    public void actionPerformed(ActionEvent e) {

        int tempByteBufferSize;
        String lnfName = e.getActionCommand();
        date = new Date();

        if (lnfName.equals("Capture")) {
            captureBtn.setEnabled(false);
            stopBtn.setEnabled(true);
            blockSizeSpinner.setEnabled(false);
            numberOfBlocksSpinner.setEnabled(false);
            controlPanelComponents.getModePanel().setVisible(false);
            controlPanelComponents.getBlockSizeSpinner().setEnabled(false);
            /*
              If datacapture, dataWriter or linkList already exist they need to be stopped
              then destroyed
            */
            if (dataCapture != null) {
                dataCapture.stopCapture();
                dataCapture = null;
            }
            if (dataWriter != null) {
                dataWriter.stopWriting();
                dataWriter = null;
            }

            if (linkList != null) linkList = null;

            // now need to calculate the size of buffers

            tempByteBufferSize = controlPanelComponents.getBlockSize() * (int) captureDefaults.getSampleRate()
                    * captureDefaults.getSampleSizeInBits() / 8;
            System.out.println("xxxxxxxxxxxxxxxxxx " + blockSize
                    + "   " + controlPanelComponents.getBlockSize());
            newBuffer = new byte[numberOfBuffers][];
            for (int i = 0; i < numberOfBuffers; i++) {
                newBuffer[i] = new byte[tempByteBufferSize];
            }

            startTimeLabel.setText(Utils.getDateString());
            startTimeLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            endTimeLabel.setText("");
            endTimeLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            // Now we nee to recreate the linklist, the dataCapture and the dataWriter
            linkList = new LinkList(newBuffer, numberOfBuffers);


            dataCapture = new DataCapture(
                    linkList,
                    newBuffer,
                    tempByteBufferSize,
                    controlPanelComponents);

            dataCapture.start();

            dataWriter = new DataWriter(linkList, controlPanelComponents);

            dataWriter.start();
            //   elapsedTimeLabel.setText("0 00:00:00");

        } else if (lnfName.equals("Stop")) {
            captureBtn.setEnabled(true);
            stopBtn.setEnabled(false);
            blockSizeSpinner.setEnabled(true);
            numberOfBlocksSpinner.setEnabled(true);
            controlPanelComponents.getModePanel().setVisible(true);
            endTimeLabel.setText(Utils.getDateString());
            endTimeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            controlPanelComponents.getBlockSizeSpinner().setEnabled(true);

            /*
              If datacapture, dataWriter or linkList already exist they need to be stopped
              then destroyed
            */
            if (dataCapture != null) {
                dataCapture.stopCapture();
                dataCapture = null;
            }
            if (dataWriter != null) {
                dataWriter.stopWriting();
                dataWriter = null;
            }
            linkList = null;
        }
    }
}