import javax.swing.*;
import java.util.Date;

/**
 * User: Roger Philp
 * Date: 11-Feb-2006
 * Time: 02:22:35
 * This class contains all of the active component defined in the controlFrame
 * for the main component KickOff
 */
public class ControlPanelComponents {

    JPanel manualStartStopPanel;
    JPanel timedStartStopPanel;
    JPanel startStopTimePanel;

    JLabel dataLocationTextLabel;
    JButton captureBtn;
    JButton stopBtn;
    JRadioButton manualRadioButton;
    JRadioButton timedRadioButton;
    JSpinner numberOfBlocksSpinner;
    JLabel currentTimeLabel;
    JLabel startTimeLabel;
    JLabel endTimeLabel;
    JButton setButton;
    JButton cancelButton;
    JLabel timerSEndTimeLabel;
    JButton showStartTimerBtn;
    JButton showEndTimerBtn;
    JLabel elapsedTimeLabel;
    JLabel dataAquiredLabel;
    JPanel readingPanel;
    JPanel writingPanel;
    JSpinner blockSizeSpinner;
    JLabel blocksWrittenLabel;
    JLabel diskkSizeRequirement;
    CaptureDefaults captureDefaults;
    RMSDisplayPanel rmsDisplayPanel;

    SpectrumDataPanel fourierTransformPanel;
    SpectrumDataPanel cumulativeFourierTransformPanel;


    public SpectrumDataPanel getFourierTransformPanel() {
        return fourierTransformPanel;
    }

    public void setFourierTransformPanel(SpectrumDataPanel fourierTransformPanel) {
        this.fourierTransformPanel = fourierTransformPanel;
    }

    public SpectrumDataPanel getCumulativeFourierTransformPanel() {
        return cumulativeFourierTransformPanel;
    }

    public void setCumulativeFourierTransformPanel(SpectrumDataPanel cumulativeFourierTransformPanel) {
        this.cumulativeFourierTransformPanel = cumulativeFourierTransformPanel;
    }

    public RMSDisplayPanel getRmsDisplayPanel() {
        return rmsDisplayPanel;
    }

    public void setRmsDisplayPanel(RMSDisplayPanel rmsDisplayPanel) {
        this.rmsDisplayPanel = rmsDisplayPanel;
    }

    int numberOfBuffers;
    int blockSize;
    Date startTime;
    ButtonGroup modeButtonGroup;
    JPanel modePanel;

    String dataRootDirectory;

    public JLabel getDataLocationTextLabel() {
        return dataLocationTextLabel;
    }

    public void setDataLocationTextLabel(JLabel dataLocationTextLabel) {
        this.dataLocationTextLabel = dataLocationTextLabel;
    }

    public String getDataRootDirectory() {
        return dataRootDirectory;
    }

    public void setDataRootDirectory(String dataRootDirectory) {
        this.dataRootDirectory = dataRootDirectory;
    }

    public JPanel getModePanel() {
        return modePanel;
    }

    public void setModePanel(JPanel modePanel) {
        this.modePanel = modePanel;
    }

    public ButtonGroup getModeButtonGroup() {
        return modeButtonGroup;
    }

    public void setModeButtonGroup(ButtonGroup modeButtonGroup) {
        this.modeButtonGroup = modeButtonGroup;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
        endTimeLabel.setText(endTime.toString());
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
        startTimeLabel.setText(startTime.toString());
    }

    Date endTime;

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public int getNumberOfBlocks() {
        return numberOfBlocks;
    }

    public void setNumberOfBlocks(int numberOfBlocks) {
        this.numberOfBlocks = numberOfBlocks;
    }

    int numberOfBlocks;

    public JPanel getManualStartStopPanel() {
        return manualStartStopPanel;
    }

    public void setManualStartStopPanel(JPanel manualStartStopPanel) {
        this.manualStartStopPanel = manualStartStopPanel;
    }

    public JPanel getTimedStartStopPanel() {
        return timedStartStopPanel;
    }

    public void setTimedStartStopPanel(JPanel timedStartStopPanel) {
        this.timedStartStopPanel = timedStartStopPanel;
    }

    public JPanel getStartStopTimePanel() {
        return startStopTimePanel;
    }

    public void setStartStopTimePanel(JPanel startStopTimePanel) {
        this.startStopTimePanel = startStopTimePanel;
    }

    public JButton getCaptureBtn() {
        return captureBtn;
    }

    public void setCaptureBtn(JButton captureBtn) {
        this.captureBtn = captureBtn;
    }

    public JButton getStopBtn() {
        return stopBtn;
    }

    public void setStopBtn(JButton stopBtn) {
        this.stopBtn = stopBtn;
    }

    public JRadioButton getManualRadioButton() {
        return manualRadioButton;
    }

    public void setManualRadioButton(JRadioButton manualRadioButton) {
        this.manualRadioButton = manualRadioButton;
    }

    public JRadioButton getTimedRadioButton() {
        return timedRadioButton;
    }

    public void setTimedRadioButton(JRadioButton timedRadioButton) {
        this.timedRadioButton = timedRadioButton;
    }

    public JSpinner getNumberOfBlocksSpinner() {
        return numberOfBlocksSpinner;
    }

    public void setNumberOfBlocksSpinner(JSpinner numberOfBlocksSpinner) {
        this.numberOfBlocksSpinner = numberOfBlocksSpinner;
    }

    public JLabel getCurrentTimeLabel() {
        return currentTimeLabel;
    }

    public void setCurrentTimeLabel(JLabel currentTimeLabel) {
        this.currentTimeLabel = currentTimeLabel;
    }

    public JLabel getStartTimeLabel() {
        return startTimeLabel;
    }

    public void setStartTimeLabel(JLabel startTimeLabel) {
        this.startTimeLabel = startTimeLabel;
    }

    public JLabel getEndTimeLabel() {
        return endTimeLabel;
    }

    public void setEndTimeLabel(JLabel endTimeLabel) {
        this.endTimeLabel = endTimeLabel;
    }

    public JButton getSetButton() {
        return setButton;
    }

    public void setSetButton(JButton setButton) {
        this.setButton = setButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(JButton cancelButton) {
        this.cancelButton = cancelButton;
    }

    public JLabel getTimerSEndTimeLabel() {
        return timerSEndTimeLabel;
    }

    public void setTimerSEndTimeLabel(JLabel timerSEndTimeLabel) {
        this.timerSEndTimeLabel = timerSEndTimeLabel;
    }

    public JButton getShowStartTimerBtn() {
        return showStartTimerBtn;
    }

    public void setShowStartTimerBtn(JButton showStartTimerBtn) {
        this.showStartTimerBtn = showStartTimerBtn;
    }

    public JButton getShowEndTimerBtn() {
        return showEndTimerBtn;
    }

    public void setShowEndTimerBtn(JButton showEndTimerBtn) {
        this.showEndTimerBtn = showEndTimerBtn;
    }

    public JLabel getElapsedTimeLabel() {
        return elapsedTimeLabel;
    }

    public void setElapsedTimeLabel(JLabel elapsedTimeLabel) {
        this.elapsedTimeLabel = elapsedTimeLabel;
    }

    public JLabel getDataAquiredLabel() {
        return dataAquiredLabel;
    }

    public void setDataAquiredLabel(JLabel dataAquiredLabel) {
        this.dataAquiredLabel = dataAquiredLabel;
    }

    public JPanel getReadingPanel() {
        return readingPanel;
    }

    public void setReadingPanel(JPanel readingPanel) {
        this.readingPanel = readingPanel;
    }

    public JPanel getWritingPanel() {
        return writingPanel;
    }

    public void setWritingPanel(JPanel writingPanel) {
        this.writingPanel = writingPanel;
    }

    public JSpinner getBlockSizeSpinner() {
        return blockSizeSpinner;
    }

    public void setBlockSizeSpinner(JSpinner blockSizeSpinner) {
        this.blockSizeSpinner = blockSizeSpinner;
    }

    public JLabel getBlocksWrittenLabel() {
        return blocksWrittenLabel;
    }

    public void setBlocksWrittenLabel(JLabel blocksWrittenLabel) {
        this.blocksWrittenLabel = blocksWrittenLabel;
    }

    public JLabel getDiskkSizeRequirement() {
        return diskkSizeRequirement;
    }

    public void setDiskkSizeRequirement(JLabel diskkSizeRequirement) {
        this.diskkSizeRequirement = diskkSizeRequirement;
    }

    public CaptureDefaults getCaptureDefaults() {
        return captureDefaults;
    }

    public void setCaptureDefaults(CaptureDefaults captureDefaults) {
        this.captureDefaults = captureDefaults;
    }

    public int getNumberOfBuffers() {
        return numberOfBuffers;
    }

    public void setNumberOfBuffers(int numberOfBuffers) {
        this.numberOfBuffers = numberOfBuffers;
    }

}

