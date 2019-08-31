import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 05-Jan-2006
 * Time: 15:44:07
 * To change this template use File | Settings | File Templates.
 */
public class JHourChooser extends JPanel implements ChangeListener {
    JSpinner spinner;
    int value = 0;
    int oldValue;


    public JHourChooser() {
        //   Calendar calendar = Calendar.getInstance();
        //     String dateStamp;
        Date currentDate;            // Used to get date to display
        SimpleDateFormat formatter;  // Formats the date displayed
        formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss yyyy", Locale.getDefault());
        currentDate = new Date();
        formatter.applyPattern("HH");
        //    dateStamp = formatter.format(currentDate).toString();
        spinner = new JSpinner();
        spinner.addChangeListener(this);
        spinner.setPreferredSize(new Dimension(45, 20));
        spinner.setValue(new Integer(formatter.format(currentDate)));
        add(spinner);
        setVisible(true);
    }

    /**
     * Sets the value attribute of the JSpinField object.
     *
     * @param newValue        The new value
     * @param updateTextField true if text field should be updated
     */
    protected void setValue(int newValue, boolean updateTextField, boolean firePropertyChange) {
        if (firePropertyChange) {
      //      System.out.println("HourChooser trying to fire event");
            firePropertyChange("hour", oldValue, newValue);
        }
    }

    public void stateChanged(ChangeEvent e) {
        SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();
        oldValue = value;
        value = model.getNumber().intValue();
        if (value > 23) value = 0;
        if (value < 0) value = 23;
  //      System.out.println("Spinner state changed " + value);
        spinner.removeChangeListener(this);
        spinner.setValue(new Integer(value));
        spinner.addChangeListener(this);
        this.setValue(value, true, true);
    }
}
