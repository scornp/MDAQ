import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Roger philp
 * Date: 25-Aug-2005
 * Time: 10:06:44
 * The purpose of this code is to aquire the ion trails of meteors using
 * audio cards. It based on code taken in part from the internet whaen the author
 * is identified he will be acknowledged.
 */

public class DataCapture extends Thread {

    boolean stopCapture = false;
    ByteArrayOutputStream byteArrayOutputStream;
    AudioFormat audioFormat;
    TargetDataLine targetDataLine;
    AudioInputStream audioInputStream;
    SourceDataLine sourceDataLine;
    int tempByteBufferSize;
    byte [][] newBuffer;
    byte [] tempBuffer;
    LinkList linkList;
    CaptureDefaults captureDefaults;
    int numberOfBuffers;
    JLabel elapsedTimeLabel;
    JPanel readingPanel;
    JLabel startTimeLabel;
    JLabel endTimeLabel;
    int numberOfBlocks;
    JLabel blocksWrittenLabel;
    JSpinner blockSizeSpinner;
    JSpinner numberOfBlocksSpinner;
    JRadioButton manualRadioButton;
    JButton setButton;
    JButton cancelButton;

   /**
     * Constructor for the audio aquisition
     * @param linkList is the dynamic storage area
     * @param newBuffer is the buffer used for temporary storage which is
     * flipped to ensure contigueous data
     * @param tempByteBufferSize size of buffer to be used
     * @param controlPanelComponents is the control frame components
     */
    public DataCapture(LinkList linkList, byte [][] newBuffer,
                       int tempByteBufferSize,
                       ControlPanelComponents controlPanelComponents) {

        this.captureDefaults = controlPanelComponents.getCaptureDefaults();
        this.setButton = controlPanelComponents.getSetButton();
        this.cancelButton = controlPanelComponents.getCancelButton();

        this.linkList = linkList;
        this.newBuffer = newBuffer;
        this.tempByteBufferSize = tempByteBufferSize;

        this.numberOfBuffers = controlPanelComponents.getNumberOfBuffers();

        tempBuffer = new byte[tempByteBufferSize];

        this.elapsedTimeLabel = controlPanelComponents.getElapsedTimeLabel();
        this.readingPanel = controlPanelComponents.getReadingPanel();
        this.startTimeLabel = controlPanelComponents.getStartTimeLabel();
        this.endTimeLabel = controlPanelComponents.getEndTimeLabel();
        this.numberOfBlocks = controlPanelComponents.getNumberOfBlocks();
        this.blocksWrittenLabel = controlPanelComponents.getBlocksWrittenLabel();
        this.manualRadioButton = controlPanelComponents.getManualRadioButton();

        audioInit();
    }






    private void audioInit(){
 // intialize the data capture
        try {
            audioFormat = getAudioFormat();
            System.out.println(" The sample rate is " + audioFormat.getSampleRate());
            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            targetDataLine.open(audioFormat);
            targetDataLine.start();
            System.out.println(targetDataLine.toString());
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    /**
     * Stop Capture halts the data aquisition process
     */
    public void stopCapture() {
        stopCapture = true;
        try {
           if (byteArrayOutputStream != null) byteArrayOutputStream.close();
        } catch (IOException e) {
           e.printStackTrace();
        }
        targetDataLine.stop();
        targetDataLine.close();
        linkList.stillProducing = false;
    }

    /**
     * getAudioFormat method creates and returns an AudioFormat object for a given set
     * of format parameters. These are set above by default bu may be changed in testing
     *
     * @return AudioFormat
     */
    private AudioFormat getAudioFormat() {
        return new AudioFormat(
                captureDefaults.getSampleRate(),
                captureDefaults.getSampleSizeInBits(),
                captureDefaults.getChannels(),
                captureDefaults.isSigned(),
                captureDefaults.isBigEndian());
    }

    /**
     * The data aquisition core routine
     */
    public void run() {
        int maxNumBlocks;
        int blockCount;
        byteArrayOutputStream = new ByteArrayOutputStream();
        maxNumBlocks = numberOfBlocks - 1;
        blockCount = 0;
        int currentBuffer = 0;
        String startTime, endTime;
        linkList.setBitSize(captureDefaults.getSampleSizeInBits(), tempByteBufferSize);
        ElapsedTimeCounter elapsedTimeCounter;
        elapsedTimeCounter = new ElapsedTimeCounter(elapsedTimeLabel);
        System.out.println("DataCapture numberOfBlocks " + maxNumBlocks);
        System.out.println("DataCapture trying to capture data " + stopCapture);
        elapsedTimeCounter.start();

        try {
            startTimeLabel.setText(new Date().toString());
            while (!stopCapture) {
                blockCount++;
                blocksWrittenLabel.setText("       " + Integer.toString(blockCount));
                readingPanel.setBackground(Color.GREEN);
                //Read data from the internal buffer of the data line.
                startTime = getDateString();

                System.out.println("audio buffersize " + targetDataLine.getBufferSize());

                int cnt = targetDataLine.read(newBuffer[currentBuffer], 0, newBuffer[currentBuffer].length);
                endTime = getDateString();

                linkList.setBuffer(currentBuffer, startTime, endTime);

                currentBuffer++;
                currentBuffer = currentBuffer % numberOfBuffers;

                if (blockCount > maxNumBlocks) stopCapture = true;
            }
            endTimeLabel.setText(new Date().toString());

            readingPanel.setBackground(Color.RED);
      //      setButton.setEnabled(false);
      //      cancelButton.setEnabled(true);
            linkList.stillProducing = false;
            byteArrayOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            System.exit(0);
        }
        elapsedTimeCounter.pleaseStop();
        stopCapture = false;
    }

    /**
     * getDateString creates a string in of the current date for chronologically
     * ordered files
     *
     * @return String
     */
    private String getDateString() {
        String dateStamp;
        Date currentDate;            // Used to get date to display
        SimpleDateFormat formatter;  // Formats the date displayed
    //    formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss yyyy", Locale.getDefault());
        formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss yyyy", Locale.getDefault());
        currentDate = new Date();
    //    formatter.applyPattern("yyyy MM dd HH mm ss");
        formatter.applyPattern("yyyyMMddHHmmss");
        dateStamp = formatter.format(currentDate);
        return dateStamp;
    }
}
