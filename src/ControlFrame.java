import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Roger philp
 * Date: 25-Aug-2005
 * Time: 10:06:44
 * The purpose of this code is to acquire the ion trails of meteors using
 * audio cards. It based on code taken in part from the internet when the author
 * is identified he will be acknowledged.
 */

public class ControlFrame extends JFrame {
    int tempByteBufferSize;
    byte[] tempBuffer;
    int blockSize = 10;
    int numberOfBlocks = 3;
    int numberOfBuffers = 10;


    Container container;
    GridBagLayout gbLayout;
    GridBagConstraints gbConstraints;

    CaptureDefaults captureDefaults = null;

    public ControlFrame(String version) {

//   aquistion details
        captureDefaults = new CaptureDefaults();
        long diskSize;
        diskSize = (long) (blockSize * numberOfBlocks * captureDefaults.getSampleRate() * 4
                / (1024 * 1024));
        container = getContentPane();
        gbLayout = new GridBagLayout();
        container.setLayout(gbLayout);

        gbConstraints = new GridBagConstraints();
        gbConstraints.fill = GridBagConstraints.NONE;
        this.setForeground(Color.white);

//      some stuff for boarders etc
//      LineBorder lineBorder = (LineBorder) BorderFactory.createLineBorder(Color.black);
//      spectrumPanel1.setBorder(lineBorder);

// lets setup the various panels

        JPanel timeSpectrumPanel = new JPanel(new BorderLayout());
        timeSpectrumPanel.setBackground(Color.white);
        timeSpectrumPanel.setForeground(Color.white);

        RMSDisplayPanel  rmsDisplayPanel = new RMSDisplayPanel(300);
        rmsDisplayPanel.setBackground(Color.white);
        timeSpectrumPanel.add(rmsDisplayPanel, BorderLayout.NORTH);

 /*       SpectrumDataPanel spectrumPanel1 = new SpectrumDataPanel();
        spectrumPanel1.setBackground(Color.white);
        timeSpectrumPanel.add(spectrumPanel1, BorderLayout.NORTH);*/

        SpectrumDataPanel fourierTransformPanel = new SpectrumDataPanel("Fourier Transform", "Frequency (Hz)", "Power", 44100);
        fourierTransformPanel.setBackground(Color.white);

        timeSpectrumPanel.add(fourierTransformPanel, BorderLayout.CENTER);

        SpectrumDataPanel cumulativeFourierTransformPanel = new SpectrumDataPanel("Cumulative Fourier Transform", "Frequency (Hz)", "Power", 44100);
        cumulativeFourierTransformPanel.setBackground(Color.white);

        timeSpectrumPanel.add(cumulativeFourierTransformPanel, BorderLayout.SOUTH);

        timeSpectrumPanel.setBackground(Color.white);
        timeSpectrumPanel.setForeground(Color.white);

        /*     JFrame jframe = new JFrame();
        jframe.setAlwaysOnTop(false);
        jframe.getContentPane().setBackground(Color.RED);
        jframe.setBackground(Color.RED);
        //     timeSpectrumPanel.setOpaque(true);
        //       jframe.add(timeSpectrumPanel);
        jframe.setVisible(true);
        jframe.setPreferredSize(new Dimension(200, 50));
        jframe.pack();
        jframe.validate();*/
        //     JPanel dataLocationPanel = new JPanel(new FlowLayout());
        JPanel dataLocationPanel = new JPanel(new BorderLayout());
        dataLocationPanel.setPreferredSize(new Dimension(300, 50));
        dataLocationPanel.setBackground(Color.white);
        dataLocationPanel.setBorder(BorderFactory.createTitledBorder("Root Data Storage location :"));
        JLabel dataLocationTextLabel = new JLabel(System.getenv("MDAQDataRoot"));

        if (System.getenv("MDAQDataRoot") == null) JOptionPane.showMessageDialog(this,
                "Root Data Storage location is not set with environment variable MDAQDataRoot",
                "Invalid tag will use end of file contents as end marker",
                JOptionPane.ERROR_MESSAGE);

        //    dataLocationTextLabel.setPreferredSize(new Dimension(100,20));
        JButton dataLocationButton = new JButton("...");
        //   dataLocationButton.setPreferredSize(new Dimension(20, 20));
        dataLocationPanel.add(dataLocationTextLabel, BorderLayout.WEST);
        dataLocationPanel.add(dataLocationButton, BorderLayout.EAST);


        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JPanel modePanel = new JPanel();
        JPanel currentTimePanel = new JPanel(new GridLayout(1, 2));
        currentTimePanel.setBorder(BorderFactory.createTitledBorder("Current time"));
        JPanel manualStartStopPanel = new JPanel(new GridLayout(2, 2));
        manualStartStopPanel.setBackground(Color.white);
        JPanel timedStartStopPanel = new JPanel(new GridLayout(3, 2));

        timedStartStopPanel.setBackground(Color.white);
        JPanel aquasitionDataPanel = new JPanel(new GridLayout(7, 2));
        JPanel activityPanel = new JPanel(new FlowLayout());

        buttonPanel.setBackground(Color.white);
        modePanel.setBackground(Color.white);
        currentTimePanel.setBackground(Color.white);
        manualStartStopPanel.setBackground(Color.white);
        aquasitionDataPanel.setBackground(Color.white);
        activityPanel.setBackground(Color.white);

        //      activityPanel.setBackground(new Color(Color.HSBtoRGB(.5f,.5f,.5f)));
        ControlPanelComponents controlPanelComponents = new ControlPanelComponents();

// set up the panels upon which the various items will fit onto in the order given above.

// create the components
        JButton captureBtn = new JButton("Capture");
        JButton stopBtn = new JButton("Stop");
        //    captureBtn.setBackground(Color.white);
        //    stopBtn.setBackground(Color.white);

        JRadioButton manualRadioButton = new JRadioButton("Manual");
        JRadioButton timedRadioButton = new JRadioButton("Timed");
        manualRadioButton.setBackground(Color.white);
        timedRadioButton.setBackground(Color.white);

        JButton showStartTimerBtn = new JButton("Set start time");
        JButton showEndTimerBtn = new JButton("Set end time");

        JLabel currentTime = new JLabel();
        JLabel startTimeLabel = new JLabel(" ");
        JLabel endTimeLabel = new JLabel(" ");

        JButton setButton = new JButton("Set");
        JButton cancelButton = new JButton("Cancel");
        JLabel timerStartTimeLabel = new JLabel(" ");
        JLabel timerSEndTimeLabel = new JLabel(" ");
        JSpinner numberOfBlocksSpinner = new JSpinner();

        JLabel elapsedTimeLabel = new JLabel();
        JLabel dataAquiredLabel = new JLabel();
        JPanel readingPanel = new JPanel();
        JPanel writingPanel = new JPanel();
        JSpinner blockSizeSpinner = new JSpinner();
        JLabel blocksWrittenLabel = new JLabel("0");
        JLabel diskkSizeRequirement = new JLabel(Long.toString(diskSize));

        ButtonGroup modeButtonGroup;
        modeButtonGroup = new ButtonGroup();

        controlPanelComponents.setDataRootDirectory(System.getenv("MDAQDataRoot"));
        controlPanelComponents.setManualStartStopPanel(manualStartStopPanel);
        controlPanelComponents.setTimedStartStopPanel(timedStartStopPanel);

        controlPanelComponents.setCaptureBtn(captureBtn);
        controlPanelComponents.setStopBtn(stopBtn);
        controlPanelComponents.setManualRadioButton(manualRadioButton);
        controlPanelComponents.setTimedRadioButton(timedRadioButton);
        controlPanelComponents.setNumberOfBlocksSpinner(numberOfBlocksSpinner);
        controlPanelComponents.setCurrentTimeLabel(currentTime);
        controlPanelComponents.setStartTimeLabel(startTimeLabel);
        controlPanelComponents.setEndTimeLabel(endTimeLabel);
        controlPanelComponents.setSetButton(setButton);
        controlPanelComponents.setCancelButton(cancelButton);
        controlPanelComponents.setStartTimeLabel(timerStartTimeLabel);
        controlPanelComponents.setEndTimeLabel(timerSEndTimeLabel);
        controlPanelComponents.setShowStartTimerBtn(showStartTimerBtn);
        controlPanelComponents.setShowEndTimerBtn(showEndTimerBtn);
        controlPanelComponents.setElapsedTimeLabel(elapsedTimeLabel);
        controlPanelComponents.setDataAquiredLabel(dataAquiredLabel);
        controlPanelComponents.setReadingPanel(readingPanel);
        controlPanelComponents.setWritingPanel(writingPanel);
        controlPanelComponents.setBlockSizeSpinner(blockSizeSpinner);
        controlPanelComponents.setNumberOfBuffers(numberOfBuffers);
        controlPanelComponents.setCaptureDefaults(captureDefaults);
        controlPanelComponents.setDiskkSizeRequirement(diskkSizeRequirement);
        controlPanelComponents.setBlocksWrittenLabel(blocksWrittenLabel);
        controlPanelComponents.setBlockSize(blockSize);
        controlPanelComponents.setNumberOfBlocks(numberOfBlocks);
        controlPanelComponents.setModeButtonGroup(modeButtonGroup);
        controlPanelComponents.setModePanel(modePanel);

//     set up the display panels
        controlPanelComponents.setRmsDisplayPanel(rmsDisplayPanel);
// frequency panels
        controlPanelComponents.setFourierTransformPanel(fourierTransformPanel);

        controlPanelComponents.setCumulativeFourierTransformPanel(cumulativeFourierTransformPanel);

// setup the data storage location
        controlPanelComponents.setDataLocationTextLabel(dataLocationTextLabel);
        DataLocationButtonListener dataLocationButtonListener = new DataLocationButtonListener(controlPanelComponents);
        dataLocationButton.addActionListener(dataLocationButtonListener);

// first set up the start stop buttons

        buttonPanel.add(captureBtn);
        buttonPanel.add(stopBtn);
        captureBtn.setEnabled(true);
        stopBtn.setEnabled(false);

// add the mode buttons
        manualRadioButton.setSelected(true);

        ModeListener modeListener = new ModeListener(controlPanelComponents);

        manualRadioButton.addActionListener(modeListener);
        timedRadioButton.addActionListener(modeListener);

        modeButtonGroup.add(manualRadioButton);
        modeButtonGroup.add(timedRadioButton);
        modePanel.add(manualRadioButton);
        modePanel.add(timedRadioButton);

// Set up current time panel

        JLabel currentTimeLabelText = new JLabel("Current time :");
        currentTimeLabelText.setHorizontalAlignment(SwingConstants.RIGHT);

        currentTime.setHorizontalAlignment(SwingConstants.RIGHT);

        currentTimeLabelText.setFont(new Font("Roman", 1, 18));
        currentTimePanel.add(currentTime);
        currentTime.setFont(new Font("Roman", 1, 18));
        UpDateTime upDateTime = new UpDateTime(currentTime);
        upDateTime.start();

// setup start stop Panel

        startTimeLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        endTimeLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // manual panel the labels are fixed
        JLabel startTimeLabelText = new JLabel("Start time :");
        JLabel endTimeLabelText = new JLabel("End time :");
        startTimeLabelText.setHorizontalAlignment(SwingConstants.RIGHT);

        endTimeLabelText.setHorizontalAlignment(SwingConstants.RIGHT);

        manualStartStopPanel.add(startTimeLabelText);
        manualStartStopPanel.add(startTimeLabel);
        manualStartStopPanel.add(endTimeLabelText);
        manualStartStopPanel.add(endTimeLabel);

        // timed panel

        cancelButton.setEnabled(false);

        timedStartStopPanel.add(showStartTimerBtn);
        timedStartStopPanel.add(timerStartTimeLabel);
        timedStartStopPanel.add(showEndTimerBtn);
        timedStartStopPanel.add(timerSEndTimeLabel);
        timedStartStopPanel.add(setButton);
        timedStartStopPanel.add(cancelButton);

        timedStartStopPanel.setVisible(false);

        // add set and cancel listner
        SetCancelButtonListener setCancelButtonListener
                = new SetCancelButtonListener(controlPanelComponents);

        setButton.addActionListener(setCancelButtonListener);
        cancelButton.addActionListener(setCancelButtonListener);

// Aquisition details

        // item one
        JLabel elapsedTimeLabelText = new JLabel("Elapsed time :");
        elapsedTimeLabelText.setHorizontalAlignment(SwingConstants.RIGHT);


        elapsedTimeLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // item two
        JLabel aquiredDataLabelText = new JLabel("Data Aquired (MB) :");
        aquiredDataLabelText.setHorizontalAlignment(SwingConstants.RIGHT);

        dataAquiredLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // item three
        JLabel aquistionRateLabel = new JLabel("Aquistion Rate (Hz) :");
        aquistionRateLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel aquistionRate = new JLabel(Float.toString(captureDefaults.getSampleRate()));
        aquistionRate.setHorizontalAlignment(SwingConstants.RIGHT);

        // item four
        JLabel dataBlockSizeLabel = new JLabel("Data Block Size (Sec) :");
        dataBlockSizeLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // item five
        JLabel numberOfBlocksLabel = new JLabel("Number of data blocks :");
        numberOfBlocksLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        //  JSpinner numberOfBlocksSpinner = new JSpinner();

        // item six
        JLabel diskkSizeRequirementLabel = new JLabel("DiskSpace requirement (MB) :");

        // item seven
        JLabel blocksWrittenText = new JLabel("Aquiring block :");
        blocksWrittenText.setHorizontalAlignment(SwingConstants.RIGHT);


        blocksWrittenText.setHorizontalAlignment(SwingConstants.RIGHT);

        aquasitionDataPanel.add(elapsedTimeLabelText);
        aquasitionDataPanel.add(elapsedTimeLabel);
        aquasitionDataPanel.add(aquiredDataLabelText);
        aquasitionDataPanel.add(dataAquiredLabel);
        aquistionRate.setHorizontalAlignment(SwingConstants.RIGHT);

        blockSizeSpinner.setValue(blockSize);


        numberOfBlocksSpinner.setName("numberOfBlocks");
        numberOfBlocksSpinner.setValue(numberOfBlocks);


        diskkSizeRequirement.setHorizontalAlignment(SwingConstants.RIGHT);

        BlockSizeChanger blockSizeChanger = new BlockSizeChanger(
                diskkSizeRequirement,
                controlPanelComponents);

        blockSizeSpinner.setName("blockSize");

        // add the listeners for the size of blocks and there number

        blockSizeSpinner.addChangeListener(blockSizeChanger);
        numberOfBlocksSpinner.addChangeListener(blockSizeChanger);


        blocksWrittenLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        aquasitionDataPanel.add(aquistionRateLabel);
        aquasitionDataPanel.add(aquistionRate);

        aquasitionDataPanel.add(blocksWrittenText);
        aquasitionDataPanel.add(blocksWrittenLabel);

        aquasitionDataPanel.add(diskkSizeRequirementLabel);
        aquasitionDataPanel.add(diskkSizeRequirement);

        aquasitionDataPanel.add(dataBlockSizeLabel);
        aquasitionDataPanel.add(blockSizeSpinner);

        aquasitionDataPanel.add(numberOfBlocksLabel);
        aquasitionDataPanel.add(numberOfBlocksSpinner);

// Activity status panel
        JLabel readingText = new JLabel("Aquiring data");
        JPanel readingLabel = new JPanel();


        readingPanel.setBackground(Color.RED);
        readingPanel.setSize(10, 10);
        readingLabel.add(readingPanel);
        readingLabel.setBackground(Color.white);
        JLabel writingText = new JLabel("Writing data");
        JPanel writingLabel = new JPanel();
        writingLabel.setBackground(Color.white);


        writingPanel.setBackground(Color.RED);
        writingLabel.add(writingPanel);
        writingPanel.setSize(10, 10);

        activityPanel.add(readingText);
        activityPanel.add(readingLabel);
        activityPanel.add(writingText);
        activityPanel.add(writingLabel);

//Register listeners
        ShowTimePanelListener shoeTimePanelListener
                = new ShowTimePanelListener(
                this,
                controlPanelComponents);

        showStartTimerBtn.addActionListener(shoeTimePanelListener);
        showEndTimerBtn.addActionListener(shoeTimePanelListener);

        ButtonListner buttonListner;
        buttonListner = new ButtonListner(controlPanelComponents);
        captureBtn.addActionListener(buttonListner);
        stopBtn.addActionListener(buttonListner);

//  addComponent is locally defined
        addComponent(dataLocationPanel, 0, 0, 1, 1);
        addComponent(modePanel, 1, 0, 1, 1);
        addComponent(buttonPanel, 2, 0, 1, 1);
        addComponent(currentTimePanel, 3, 0, 1, 1);
        addComponent(manualStartStopPanel, 4, 0, 1, 1);
        addComponent(timedStartStopPanel, 5, 0, 1, 1);
        addComponent(aquasitionDataPanel, 6, 0, 1, 1);
        addComponent(activityPanel, 7, 0, 1, 1);
        addComponent(timeSpectrumPanel, 0, 1, 1, 8);
        //    addComponent(spectrumPanel1, 0, 1, 1, 2);
        //  addComponent(fourierTransformPanel, 2, 1, 1, 2);
        //addComponent(cumulativeFourierTransformPanel, 4, 1, 1, 2);

        //    addComponent(setCancelPanel, 7, 0, 1, 1);

// set frame details
        //  String title = n
        setTitle("Data Aquisition system " + version);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // this.setLocation(300, 200);
        //  setSize(1500, 500);
        setResizable(true);

        this.getContentPane().setBackground(Color.white);
        this.getContentPane().setForeground(Color.white);
        this.pack();

        /* Center the window */
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winDim = getBounds();
        setLocation((screenDim.width - winDim.width) / 2,
                (screenDim.height - winDim.height) / 2);

        container.validate();
        setVisible(true);
        repaint();
    }

    public void setStartDate(Date date) {
        // System.out.println("ControlFrame: Setting start date " + date);
    }

    public void setEndDate(Date date) {
        //  System.out.println("ControlFrame: Setting end date " + date);
    }

    private void addComponent(Component c, int row, int column, int width, int height) {
        gbConstraints.gridx = column;
        gbConstraints.gridy = row;
        gbConstraints.gridwidth = width;
        gbConstraints.gridheight = height;
        gbConstraints.weightx = 0;
        gbConstraints.weighty = 0;
        gbLayout.setConstraints(c, gbConstraints);
        container.add(c);
    }

}
