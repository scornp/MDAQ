import java.awt.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 20-Jan-2006
 * Time: 15:35:29
 * To change this template use File | Settmings | File Templates.
 */


class SplashWindowFrame extends JFrame {
    SplashWindow sw;
    Image splashIm;
    String version;

    SplashWindowFrame(String version) {
        super();

        this.version = version;
        /* Size the frame */
        setSize(615, 370);

        /* Center the frame */
        Dimension screenDim =
                Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle frameDim = getBounds();
        setLocation((screenDim.width - frameDim.width) / 2,
                (screenDim.height - frameDim.height) / 2);


        MediaTracker mt = new MediaTracker(this);
        splashIm = Toolkit.getDefaultToolkit().getImage("./images/meteor.jpg");
        mt.addImage(splashIm, 0);
        try {
            mt.waitForID(0);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        sw = new SplashWindow(this, splashIm, version);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}

class SplashWindow extends JWindow {
    Image splashIm;
    String version;

    SplashWindow(Frame parent, Image splashIm, String version) {
        super(parent);
        this.splashIm = splashIm;
        this.version = version;
        setSize(615, 370);
        //    setSize(300,200);

        /* Center the window */
        Dimension screenDim =
                Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winDim = getBounds();
        setLocation((screenDim.width - winDim.width) / 2,
                (screenDim.height - winDim.height) / 2);
        setVisible(true);
    }

    public void paint(Graphics g) {
        if (splashIm != null) {
            g.drawImage(splashIm, 0, 0, this);
            g.setFont(new Font("Dialog", Font.ITALIC, 12));
            g.drawString(version, 280, 360);
        }
    }
}
