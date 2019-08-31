import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: R Philp
 * Date: 20-Apr-2006
 * Time: 20:20:13
 * A component of the MDAQ project to isolate data chunks
 * that contain meteor data
 */
public class DataLocationButtonListener extends JFrame implements ActionListener {
    ControlPanelComponents controlPanelComponents;

    DataLocationButtonListener(ControlPanelComponents controlPanelComponents) {
        this.controlPanelComponents = controlPanelComponents;
    }

    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setVisible(true);
        fileChooser.showOpenDialog(this);

        String file = fileChooser.getSelectedFile().getAbsolutePath();
       controlPanelComponents.getDataLocationTextLabel().setText(file);
       controlPanelComponents.setDataRootDirectory(file); 

   //     controlPanelComponents.getDataLocationTextLabel().setText("xx");
        //    System.out.println( "xx " + fileChooser.getSelectedFile());

    }
}

