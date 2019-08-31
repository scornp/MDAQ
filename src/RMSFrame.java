import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: scornp
 * Date: 21-Nov-2006
 * Time: 16:24:07
 * To change this template use File | Settings | File Templates.
 */
public class RMSFrame{
        DisplayPanel displayPanel;
    RMSFrame(){
        System.out.println("LoadListedDataButtonListener : " + "About to set displayPanel float data array");
        JFrame newFrame;       
        newFrame = new JFrame("RMS Panel");
        int width = 400;
        int height = 400;
        newFrame.setSize(400 + 8, 400 + 32);
   //     newFrame.setLayout();

   //     AnalysisPanelComponents analysisPanelComponents = new AnalysisPanelComponents();

        float [] dataArray;
        dataArray = new float[10];
        // turn off the entire frame bar, including title, close, minimize, resize...
//newFrame.setUndecorated( true );

// turn off just resizing controls
//newFrame.setResizable( false );
        displayPanel = new DisplayPanel(width, height);
        displayPanel.setPreferredSize(new Dimension(width, height));
         newFrame.add(displayPanel);
        displayPanel.setVisible(true);
        newFrame.setVisible(true);
        displayPanel.pleaseStart();

   /*     for(int i = 0; i < 10; i++) {
                    displayPanel.addData(i);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("Can't sleep");
            }
            
        }     */



    }


    public DisplayPanel getDisplayPanel() {
        System.out.println("MSFRAME   -------------------------------------------");
        return displayPanel;
    }
}
