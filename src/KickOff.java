import javax.swing.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 16-Jan-2006
 * Time: 16:57:52
 * To change this template use File | Settings | File Templates.
 */
public class KickOff extends Thread {
    private Date startTime = null;
    private Date endTime = null;
    private JLabel startTimeLabel;
    private JLabel endTimeLabel;
    private boolean continueLoop;
    //   private JButton setButton;
    //   private JButton cancelButton;
    private JSpinner blockSizeSpinner;
    private CaptureDefaults captureDefaults;
    private int numberOfBuffers;
    byte [][] newBuffer;
    private JLabel elapsedTime;
    private JPanel reading;
    private JLabel blocksWrittenLabel;
    private JLabel dataAquired;
    private JButton captureBtn;
    private JButton stopBtn;
    private JPanel writing;
    private JSpinner numberOfBlocksSpinner;
    private JLabel diskkSizeRequirement;
    private LinkList linkList;
    private DataCapture dataCapture;
    private DataWriter dataWriter;
    private JRadioButton manualRadioButton;
    private JButton setButton;
    private JButton cancelButton;
    private ControlPanelComponents controlPanelComponents;
    private int blockSize;
    private int numberOfBlocks;

    /**
     * This class waits until an epoch when it can start aquiring data
     *
     * @param controlPanelComponents
     */
    public KickOff(ControlPanelComponents controlPanelComponents) {
        this.controlPanelComponents = controlPanelComponents;
        this.startTimeLabel = controlPanelComponents.getStartTimeLabel();
        this.endTimeLabel = controlPanelComponents.getEndTimeLabel();

        System.out.println(startTimeLabel.toString());
        System.out.println(endTimeLabel.toString());

        this.blockSizeSpinner = controlPanelComponents.getBlockSizeSpinner();
        this.captureDefaults = controlPanelComponents.getCaptureDefaults();
        this.blockSizeSpinner = controlPanelComponents.getBlockSizeSpinner();
        this.numberOfBuffers = controlPanelComponents.getNumberOfBuffers();
        this.elapsedTime = controlPanelComponents.getElapsedTimeLabel();
        this.reading = controlPanelComponents.getReadingPanel();
        this.blocksWrittenLabel = controlPanelComponents.getBlocksWrittenLabel();
        this.dataAquired = controlPanelComponents.getDataAquiredLabel();
        this.captureBtn = controlPanelComponents.getCaptureBtn();
        this.stopBtn = controlPanelComponents.getStopBtn();
        this.writing = controlPanelComponents.getWritingPanel();
        this.numberOfBlocksSpinner = controlPanelComponents.getNumberOfBlocksSpinner();
        this.diskkSizeRequirement = controlPanelComponents.getDiskkSizeRequirement();
        this.manualRadioButton = controlPanelComponents.getManualRadioButton();
        this.blocksWrittenLabel = controlPanelComponents.getBlocksWrittenLabel();
        this.setButton = controlPanelComponents.getSetButton();
        this.cancelButton = controlPanelComponents.getCancelButton();
        this.blockSize = controlPanelComponents.getBlockSize();
        this.numberOfBlocks = controlPanelComponents.getNumberOfBlocks();
        this.startTime = controlPanelComponents.getStartTime();
        this.endTime = controlPanelComponents.getEndTime();
    }

    public void pleaseStop() {
        startTime = null;
        endTime = null;
        continueLoop = false;
        dataCapture.stopCapture();
        dataWriter.stopWriting();
    }


    public void run() {
        continueLoop = true;
        Date currentDate;
        int tempByteBufferSize;

        numberOfBlocks = (int) ((controlPanelComponents.getEndTime().getTime()
                - controlPanelComponents.getStartTime().getTime()) / 1000) / blockSize;

        controlPanelComponents.setNumberOfBlocks(numberOfBlocks);

        System.out.println("KickOff: The number of blocks is " + numberOfBlocks);

        tempByteBufferSize = blockSize * (int) captureDefaults.getSampleRate()
                * captureDefaults.getSampleSizeInBits() / 8;
        long diskSize;
        System.out.println(blockSize + " " + numberOfBlocks + " " + captureDefaults.getSampleRate());
        diskSize = (long) (blockSize * numberOfBlocks * captureDefaults.getSampleRate() * 4
                / (1024 * 1024));
        diskkSizeRequirement.setText(Long.toString(diskSize));

        newBuffer = new byte[numberOfBuffers][];
        for (int i = 0; i < numberOfBuffers; i++) {
            newBuffer[i] = new byte[tempByteBufferSize];
        }

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
        // need to worry about starttime and end time

        linkList = new LinkList(newBuffer, numberOfBuffers);

        // Now we nee to recreate the linklist, the dataCapture and the dataWriter
        linkList = new LinkList(newBuffer, numberOfBuffers);


        dataCapture = new DataCapture(
                linkList,
                newBuffer,
                tempByteBufferSize,
                controlPanelComponents);

        dataWriter = new DataWriter(linkList, controlPanelComponents);

        while (continueLoop) {

            currentDate = new Date();
            if (currentDate.after(startTime)) {
                System.out.println("Aquiring data");
                dataCapture.start();
                dataWriter.start();
                try {
                    System.out.println("KickOff sleeping for "
                    + (this.endTime.getTime() - this.startTime.getTime()));
                    Thread.sleep((this.endTime.getTime() - this.startTime.getTime()));
                    System.out.println("KickOff is now awake !!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continueLoop = false;
                //        setButton.setEnabled(true);
                //     cancelButton.setEnabled(false);

                return;
            } else {
                try {
                    System.out.println("Sleeping " + "duration is "
                            + ((endTime.getTime() - startTime.getTime()) / 1000)
                            + " " + blockSize + " " + numberOfBlocks);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public void setCancel() {
        startTimeLabel = null;
        endTimeLabel = null;
        dataCapture.stopCapture();
        dataWriter.stopWriting();
    }
}
