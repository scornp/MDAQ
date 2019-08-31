import javax.swing.*;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 27-Dec-2005
 * Time: 00:12:51
 * To change this template use File | Settings | File Templates.
 */
public class UpDateTime extends Thread{
    private JLabel timeLabel;

    public UpDateTime(JLabel timeLabel) {
        this.timeLabel = timeLabel;
    }

    public void run() {
      while(true) {
        try {
       //     timeLabel.setText(getDateString());
            timeLabel.setText(new Date().toString());
            Thread.sleep(1000);
            
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
      }
    }


    private String getDateString() {
        String dateStamp;
        Date currentDate;            // Used to get date to display
        SimpleDateFormat formatter;  // Formats the date displayed
        formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss yyyy", Locale.getDefault());
        currentDate = new Date();
        //  currentDate.
        formatter.applyPattern("yyyy MM dd HH mm ss");
        dateStamp = formatter.format(currentDate);
        return dateStamp;
    }
}
