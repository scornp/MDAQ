import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Data aquisition system
 * User: Roger Philp
 * Date: 11-Jan-2006
 * Time: 13:47:55
 * BlockSizeChanger get and set the block size and number of blocks
 */
public class BlockSizeChanger implements ChangeListener {
    int blockSize;
    int numberOfBlocks;
    JLabel diskkSizeRequirement;
    CaptureDefaults captureDefaults;
    JSpinner blockSizeSpinner;
    JSpinner numberOfBlocksSpinner;
    ControlPanelComponents controlPanelComponents;

    /**
     * Block size changer construtor
     * @param diskkSizeRequirement  guesstimate of over all disk requirement
     * @param controlPanelComponents the control panel components
     */
    public BlockSizeChanger(JLabel diskkSizeRequirement,
                            ControlPanelComponents controlPanelComponents) {
        this.controlPanelComponents = controlPanelComponents;
        this.blockSize = controlPanelComponents.getBlockSize();
        this.numberOfBlocks = controlPanelComponents.getNumberOfBuffers();
        this.diskkSizeRequirement = diskkSizeRequirement;
        this.captureDefaults = controlPanelComponents.getCaptureDefaults();
        this.blockSizeSpinner = controlPanelComponents.getBlockSizeSpinner();
        this.numberOfBlocksSpinner = controlPanelComponents.getNumberOfBlocksSpinner();
    }


    public void stateChanged(ChangeEvent e) {

        //    System.out.println(spinner.getValue().toString());
        //     System.out.println(e.getSource().equals(blockSizeSpinner));
        Integer xx;

        if (e.getSource().equals(blockSizeSpinner)) {
            xx = (Integer) blockSizeSpinner.getValue();
            if (xx < 1) blockSizeSpinner.setValue(1);
            controlPanelComponents.setBlockSize((Integer) blockSizeSpinner.getValue());
        }
        if (e.getSource().equals(numberOfBlocksSpinner)) {
            xx = (Integer) numberOfBlocksSpinner.getValue();
            if (xx < 1) numberOfBlocksSpinner.setValue(1);
            controlPanelComponents.setNumberOfBlocks((Integer) numberOfBlocksSpinner.getValue());
        }

        long diskSize = (long) (controlPanelComponents.getBlockSize() * controlPanelComponents.getNumberOfBlocks() * captureDefaults.getSampleRate() * 4
                / (1024 * 1024));
        diskkSizeRequirement.setText("   " + Long.toString(diskSize));

    }

}
