import javax.swing.*;
import java.util.Date;
import java.util.Locale;
import java.io.*;
import java.text.SimpleDateFormat;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 21-Nov-2005
 * Time: 23:03:40
 * To class attempts to write out the audio data as binary and ascii
 * The ascii format is for testing only
 * a second file does a some point need to be written containing the
 * meta data
 */
public class DataWriter extends Thread {
    private LinkList linkList;
    //  private FileWriter outsrc;
    private long outputDataSize;
    private long megaByteSize = 1024 * 1024;
    private JLabel dataAquiredLabel;
    private JButton captureBtn, stopBtn;
    private JPanel writingPanel;
    JSpinner blockSizeSpinner;
    JSpinner numberOfBlocksSpinner;
    JRadioButton manualRadioButton;
    ControlPanelComponents controlPanelComponents;
    JButton setButton;
    JButton cancelButton;
    RMSDisplayPanel rmsDisplayPanel;
    SpectrumDataPanel fourierTransformPanel;
    SpectrumDataPanel cumulativeFourierTransformPanel;

    int FFTBlockSize = 8192;

    /**
     * Datawriter to write data from the sound card to disk files,
     * currently it writes three files
     * .txt to contain start and stop time and length of data
     * .bin to contain the binary data and
     * .dat to contain the ascii version
     * eventually the .dat form will disappear
     * data is aquired from the input paramemter linklist
     *
     * @param linkList               data storage
     * @param controlPanelComponents controlframe controls
     */
    public DataWriter(LinkList linkList, ControlPanelComponents controlPanelComponents) {
        this.linkList = linkList;
        this.dataAquiredLabel = controlPanelComponents.getDataAquiredLabel();
        outputDataSize = 0;
        this.captureBtn = controlPanelComponents.getCaptureBtn();
        this.stopBtn = controlPanelComponents.getStopBtn();
        this.writingPanel = controlPanelComponents.getWritingPanel();
        this.blockSizeSpinner = controlPanelComponents.getBlockSizeSpinner();
        this.numberOfBlocksSpinner = controlPanelComponents.getNumberOfBlocksSpinner();
        this.manualRadioButton = controlPanelComponents.getManualRadioButton();
        this.controlPanelComponents = controlPanelComponents;
        this.setButton = controlPanelComponents.getSetButton();
        this.cancelButton = controlPanelComponents.getCancelButton();
        this.rmsDisplayPanel = controlPanelComponents.getRmsDisplayPanel();
        this.fourierTransformPanel = controlPanelComponents.getFourierTransformPanel();
        this.cumulativeFourierTransformPanel = controlPanelComponents.getCumulativeFourierTransformPanel();
    }


    boolean stopWriting = false;

    public void stopWriting() {
        stopWriting = true;

    }

    public void run() {
        String filename;
        String rmsFileName;
        String binaryDataFileName;
        String outputBaseDir = null;
        String catalogFileName;
        String masterIndexFileName;
        PrintStream masterIndexFile = null;

        RandomAccessFile outputBinaryDataFile = null;

        PrintStream rmsOutputFileStream = null;

        // run start and end time
        String startTime = null;
        String endTime = null;

        // set up the float and byte arrays
        // the byte array will be written to disk

        float[] floatArray = null;
        byte[] byteArray = null;
        Element element;
        int numberOfBlocksWriten = 0;
        dataAquiredLabel.setText(Integer.valueOf(0).toString());
        boolean firstTime;
        firstTime = true;
        XMLDataWriter xmlDataWriter = null;

        Complex[] cultotalPowerSignal;

             cultotalPowerSignal = new Complex[FFTBlockSize];
        for(int i = 0; i < cultotalPowerSignal.length; i++)  cultotalPowerSignal[i] = new Complex(0, 0);
        // at this point we need to create the output frame

        while (linkList.carryOn() & !stopWriting) {
            try {
                element = linkList.getElement();
                writingPanel.setBackground(Color.GREEN);
                floatArray = element.getData();
                byteArray = element.getByteData();
                System.out.println("Datawriter: starting time " + element.getStartTime());
                System.out.println("Datawriter: ending time " + element.getEndTime());

                //  set the base filename
                filename = element.getStartTime();

                // create catalog temporary
                if (firstTime) {
                    // make new directory
                    // old version
                    //   outputBaseDir =  baseDir + element.getStartTime();
                    System.out.println("We are going to write to : \n "
                            + controlPanelComponents.getDataRootDirectory()
                            + System.getProperty("file.separator") + element.getStartTime()
                            + System.getProperty("file.separator") + "data");
                    outputBaseDir = controlPanelComponents.getDataRootDirectory()
                            + System.getProperty("file.separator") + element.getStartTime()
                            + System.getProperty("file.separator") + "data";
                    boolean success = (new File(outputBaseDir)).mkdirs();


                    catalogFileName = outputBaseDir + System.getProperty("file.separator") + "Catalogue.cat";

                    xmlDataWriter = new XMLDataWriter(catalogFileName);

                    // write header information

                    // program description
                    xmlDataWriter.startClause("Description");
                    xmlDataWriter.setElement("Program", "MDAQ");
                    xmlDataWriter.setElement("Version", "1.0");
                    xmlDataWriter.setElement("Comments", "comments");
                    xmlDataWriter.setElement("Abstract", "some description");
                    xmlDataWriter.setElement("Author", "Roger Philp");
                    xmlDataWriter.setElement("Date", "03/04/2006");
                    xmlDataWriter.endClause("Description");

                    CaptureDefaults captureDefaults = new CaptureDefaults();

                    // audio settings
                    xmlDataWriter.startClause("Audio");
                    xmlDataWriter.setElement("sampleRate", captureDefaults.getSampleRate());
                    xmlDataWriter.setElement("sampleSizeInBits", captureDefaults.getSampleSizeInBits());
                    xmlDataWriter.setElement("channels", captureDefaults.getChannels());
                    xmlDataWriter.setElement("signed", captureDefaults.isSigned());
                    xmlDataWriter.setElement("bigEndian", captureDefaults.isBigEndian());
                    xmlDataWriter.endClause("Audio");

                    // data format
                    xmlDataWriter.startClause("DataType");
                    xmlDataWriter.setElement("fileType", "binary");
                    xmlDataWriter.setElement("numberType", "float");
                    xmlDataWriter.endClause("DataType");

                    xmlDataWriter.startClause("DataFiles");
                    xmlDataWriter.setElement("rmsDataFile", "RMS.dat");

                    xmlDataWriter.write();
                    rmsFileName = outputBaseDir + System.getProperty("file.separator") + "RMS.dat";
                    try {
                        rmsOutputFileStream = new PrintStream(new FileOutputStream(rmsFileName));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();

                    }
                    firstTime = false;
                }

                // set the filenames
                binaryDataFileName = outputBaseDir + System.getProperty("file.separator") + filename + ".bin";

                // open up all other files
                try {
                    outputBinaryDataFile = new RandomAccessFile(binaryDataFileName, "rw");
                } catch (FileNotFoundException e) {
                    System.out.println("Data writer exception !!!! trying to write data");
                    e.printStackTrace();
                }

                // add entry to catalog
                xmlDataWriter.startClause("element");
                xmlDataWriter.setElement("fileName", filename + ".bin");
                xmlDataWriter.setElement("fileSize", floatArray.length);
                xmlDataWriter.endClause("element");
                xmlDataWriter.write();
                // create datafile
                outputDataSize += floatArray.length;
                dataAquiredLabel.setText(Long.valueOf(outputDataSize * 4 / (megaByteSize)).toString());
                numberOfBlocksWriten++;
                byte[] bb;

                bb = new byte[floatArray.length * 4];

                float xfloat = 12.0f;
                byte[] xbyte = new byte[4];

                int intBits1 = Float.floatToIntBits(xfloat);

                bb[0] = (byte) ((intBits1 & 0x000000ff) >> 0);
                bb[1] = (byte) ((intBits1 & 0x0000ff00) >> 8);
                bb[2] = (byte) ((intBits1 & 0x00ff0000) >> 16);
                bb[3] = (byte) ((intBits1 & 0xff000000) >> 24);

                System.out.println("Data write float to byte ");
                printBits(intBits1, 0, 32);

                int byteOffset = 0;
                for (int ii = 0; ii < floatArray.length; ii++) {
                    //      floatToByteArray(floatArray[ii] , bb, byteOffset);
                    int intBits = Float.floatToIntBits(floatArray[ii]);

     // original to pc format
                    bb[byteOffset + 0] = (byte) ((intBits & 0x000000ff) >> 0);
                    bb[byteOffset + 1] = (byte) ((intBits & 0x0000ff00) >> 8);
                    bb[byteOffset + 2] = (byte) ((intBits & 0x00ff0000) >> 16);
                    bb[byteOffset + 3] = (byte) ((intBits & 0xff000000) >> 24);
      // byte swapped  to sun format
      /*              bb[byteOffset + 0] = (byte) ((intBits & 0xff000000) >> 24);
                    bb[byteOffset + 1] = (byte) ((intBits & 0x00ff0000) >> 16);
                    bb[byteOffset + 2] = (byte) ((intBits & 0x0000ff00) >> 8);
                    bb[byteOffset + 3] = (byte) ((intBits & 0x000000ff) >> 0);*/

                    byteOffset += 4;
                   /* if(ii == 0) {
                        System.out.println("first element = " + floatArray[ii]);
                        printBits(intBits, 0, 32);
                    }*/
                }

                System.out.println("Data writer: start writing binary file ");
                float sum = 0;
                   for (int i = 0; i < floatArray.length; i++)  sum += floatArray[i] * floatArray[i]; 
                try {
                    //    for (int i = 0; i < floatArray.length; i++)  {
                    //     outputBinaryDataFile.writeFloat(floatArray[i]);
                    System.out.println("writing data element 0 " + bb[0] + " " + floatArray[0]);
                    outputBinaryDataFile.write(bb, 0, floatArray.length * 4);

                    // sum += floatArray[i] * floatArray[i];      }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Data writer: stop writing binary file ");

                // put the rms value in the RMS file
                rmsOutputFileStream.println(Math.sqrt(sum / floatArray.length));

                rmsDisplayPanel.addTotalObservation(sum / floatArray.length);

/*  ****************************************

               Fourier bit starts here

    **************************************** */
                // We will do the fft in 8096 block;

                int fftOffSet = 0;
                int numFFTBlocks = floatArray.length / FFTBlockSize;
                Complex[] signalBlock = new Complex[FFTBlockSize];
                Complex[] FFTSignal;
                Complex[] FFTSignalTmp;
                float[] powerSignal = new float[FFTBlockSize];

                FFTSignalTmp = new Complex[FFTBlockSize];


                fftOffSet = 0;
                // reset the power signal for this time segment
                for (int k = 0; k < FFTBlockSize; k++) powerSignal[k] = 0;
                for (int k = 0; k < FFTBlockSize; k++) FFTSignalTmp[k] = new Complex(0, 0);

                for (int jj = 0; jj < numFFTBlocks; jj++) {
                    int i = 0;
                    for (int j = fftOffSet; j < fftOffSet + FFTBlockSize; j++) {
                        if (j < floatArray.length) {
                            signalBlock[i] = new Complex(floatArray[j], 0);
                        } else {
                            signalBlock[i] = new Complex(0, 0);
                        }
                        i++;
                    }
                    FFTSignal = FFT.fft(signalBlock);
                    for (int k = 0; k < FFTBlockSize; k++) {
                        FFTSignalTmp[k] = FFTSignalTmp[k].plus(FFTSignal[k]);
                    }
                    fftOffSet += FFTBlockSize;
                }

                float[] xx, yy;
                float wmin = 0, wmax = 44100 / 2, wdelta;

                wdelta = (wmax - wmin) / (FFTBlockSize / 2 - 1);

                xx = new float[FFTBlockSize / 2];
                yy = new float[FFTBlockSize / 2];

                for (int i = 0; i < FFTBlockSize / 2; i++) {
                    xx[i] = wmin + i * wdelta;
                    yy[i] = (float)FFTSignalTmp[i].abs();
                }

                fourierTransformPanel.setNewDataSeries(xx, yy);

                for (int i = 0; i < FFTBlockSize; i++) {
                   cultotalPowerSignal[i] = cultotalPowerSignal[i].plus(FFTSignalTmp[i]);
                }

                for (int i = 0; i < FFTBlockSize / 2; i++) {
                    xx[i] = wmin + i * wdelta;
                    yy[i] = (float)cultotalPowerSignal[i].abs();
                }

                cumulativeFourierTransformPanel.setNewDataSeries(xx, yy);

/*  ****************************************

               Fourier bit ends here

    **************************************** */

                //  RMSDisplayPanel.addTotalObservation(0);
                System.out.println("rmsOutputFileStream here");
                try {
                    outputBinaryDataFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // fill the entries into master index file
                if (startTime == null) startTime = element.getStartTime() + '\n';
                endTime = element.getEndTime() + '\n';

                // remove the last element from the list
                linkList.popElement();
                writingPanel.setBackground(Color.RED);

                System.out.println("DataWriter: Number of blocks written " + numberOfBlocksWriten +
                        " " + floatArray.length);
            } catch (ReaderEmptykException e) {
                try {

                    Thread.sleep(50);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        writingPanel.setBackground(Color.RED);
        controlPanelComponents.getSetButton().setEnabled(true);
        controlPanelComponents.getCancelButton().setEnabled(false);
        controlPanelComponents.getModePanel().setVisible(true);

        // write the master index entry

        // setup the master index filename
        //    masterIndexFileName = baseDir + "Catalogue.idx";
        masterIndexFileName = controlPanelComponents.getDataRootDirectory() + System.getProperty("file.separator") + "Catalogue.idx";
        try {
            // need to test if file exists
            boolean exists = (new File(masterIndexFileName)).exists();
            if (exists) {
                masterIndexFile = new PrintStream(new FileOutputStream(masterIndexFileName, true));
            } else {
                masterIndexFile = new PrintStream(new FileOutputStream(masterIndexFileName));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        rmsOutputFileStream.close();
        masterIndexFile.append("#-Begin run\n");
        masterIndexFile.append(startTime);
        masterIndexFile.append(endTime);
        masterIndexFile.append(Integer.toString(floatArray.length) + '\n');
        masterIndexFile.append(Integer.toString(numberOfBlocksWriten) + '\n');
        masterIndexFile.append("#-End run\n");
        masterIndexFile.close();

        // close the catalog file
        xmlDataWriter.display();
        xmlDataWriter.endClause("DataFiles");
        xmlDataWriter.write();

        xmlDataWriter.close();
        //     if (outputCatalogFile != null){
        //  outputCatalogFile.close();
        //       xmlDataWriter.display();

        //    }
        // reset the system state

        if (manualRadioButton.isSelected()) {

            captureBtn.setEnabled(true);
            stopBtn.setEnabled(false);
        }
        blockSizeSpinner.setEnabled(true);
        numberOfBlocksSpinner.setEnabled(true);


        System.out.println("DataWriter exiting");
        //   return;
    }

    /**
     * A routine to generate a string representation of the current date and time
     *
     * @return String
     */
    private String getDateString() {
        String dateStamp;
        Date currentDate;            // Used to get date to display
        SimpleDateFormat formatter;  // Formats the date displayed
        formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss yyyy", Locale.getDefault());
        currentDate = new Date();
        formatter.applyPattern("yyyy MM dd HH mm ss");
        dateStamp = formatter.format(currentDate).toString();
        return dateStamp;
    }

    private void floatToByteArray(float value, byte[] array, int offset) {
        int intBits = Float.floatToIntBits(value);
        array[offset + 0] = (byte) ((intBits & 0x000000ff) >> 0);
        array[offset + 1] = (byte) ((intBits & 0x0000ff00) >> 8);
        array[offset + 2] = (byte) ((intBits & 0x00ff0000) >> 16);
        array[offset + 3] = (byte) ((intBits & 0xff000000) >> 24);
    }

    public void printBits(int number, int startBit, int numBits) {
        int bitmask;
        bitmask = 0x80000000 >>> startBit;
        numBits += startBit;
        for (int i = startBit; i < numBits; ++i) {
            if ((number & bitmask) == 0)
                System.out.print('0');
            else
                System.out.print('1');
            bitmask >>>= 1;
        }
        System.out.println();
    }
}
