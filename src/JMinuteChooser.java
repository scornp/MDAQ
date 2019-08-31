import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 09-Jan-2006
 * Time: 14:44:22
 * This class is creates the minute panel chooser it should be rolled into a
 * single class for both the hour chooser and the minute chooser
 */
public class JMinuteChooser extends JPanel implements ChangeListener {

    /**
     * The spinner for the panel
     */
    JSpinner spinner;

    /**
     * the value of the spinner
     */
    int value = 0;

    /**
     * is the previous value of the spinner
     */
    int oldValue;

    /**
     * Construtor for the spinner chooser. It instantiates the spinner and adds
     * a change listner
     */
    public JMinuteChooser() {

        // create the spinner, add a change listner to it
        spinner = new JSpinner();
        spinner.addChangeListener(this);
        spinner.setPreferredSize(new Dimension(45, 20));

        // get the current date and format it
        Date currentDate;
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss yyyy", Locale.getDefault());
        currentDate = new Date();

        // set the formatter pattern
        formatter.applyPattern("mm");

        // set the spinner initial value
        spinner.setValue(new Integer(formatter.format(currentDate)));

        // add the spinner to the panel
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
    //        System.out.println("HourChooser trying to fire event " + oldValue + " " + newValue);
            firePropertyChange("minute", oldValue, newValue);
        }
    }

    /**
     *
     * @param e state change event
     */
    public void stateChanged(ChangeEvent e) {

        SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();
        oldValue = value;
        value = model.getNumber().intValue();
        if (value > 59) value = 0;
        if (value < 0) value = 59;
  //      System.out.println("Spinner state changed " + oldValue + " " + value);
        spinner.removeChangeListener(this);
        spinner.setValue(value);
        spinner.addChangeListener(this);

        this.setValue(value, true, true);
    }

}
