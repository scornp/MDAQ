import javax.swing.*;
import java.util.Calendar;
import java.util.Date;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/**
 * Data bquisition
 * User: Roger Philp
 * Date: 31-Dec-2005
 * Time: 16:17:29
 * This setsup the calendar panel
 */
public class JCalendar extends JFrame implements PropertyChangeListener {
    private Calendar calendar;

    JPanel monthYearPanel;
    private JMonthChooser monthChooser = null;
    private JYearChooser yearChooser = null;
    private JDayChooser dayChooser = null;

    private JButton setTimeButton = null;
    private Date setDate = null;
    TimerButtonListner timerButtonListener;
    ControlPanelComponents controlPanelComponents;

    /**
     * Calendar constructor
     * @param controlFrame reference to the control panel which needs to be disbaled while choosing a date
     * @param lnfName  is the switch for start and end times
     * @param controlPanelComponents 
     */
    public JCalendar(ControlFrame controlFrame,
                     String lnfName,
                     ControlPanelComponents controlPanelComponents) {

        setVisible(false);
        this.controlPanelComponents = controlPanelComponents;
        this.getContentPane().setBackground(Color.black);
                this.getContentPane().setForeground(Color.black);
        setDate = new Date();

        super.setSize(100, 100);
        setLocation(250, 250);
        setUndecorated(true);
        boolean weekOfYearVisible = true;
    //    Locale locale;
    //    locale = Locale.getDefault();

        calendar = Calendar.getInstance();

        setLayout(new BorderLayout());

        monthYearPanel = new JPanel();
        monthYearPanel.setLayout(new BorderLayout());

        monthChooser = new JMonthChooser();
        monthChooser.setVisible(true);

        yearChooser = new JYearChooser();
        monthChooser.setYearChooser(yearChooser);

        //-rnp
         JHourChooser hourChooser;
         JMinuteChooser minuteChooser;
        hourChooser = new JHourChooser();
        minuteChooser = new JMinuteChooser();
        dayChooser = new JDayChooser(weekOfYearVisible);

        dayChooser.setBackground(Color.black);
        monthChooser.setBackground(Color.black);
        yearChooser.setBackground(Color.black);
        dayChooser.setForeground(Color.black);
        monthChooser.setForeground(Color.black);
        yearChooser.setForeground(Color.black);

        monthYearPanel.add(monthChooser, BorderLayout.WEST);
        monthYearPanel.add(yearChooser, BorderLayout.CENTER);
        monthYearPanel.add(dayChooser, BorderLayout.SOUTH);

        //   add(dayChooser, BorderLayout.CENTER);
        add(monthYearPanel, BorderLayout.NORTH);

        JLabel timeSparatorLabel = new JLabel(":");
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new FlowLayout());
        timePanel.add(new JLabel("Time"));
        timePanel.add(hourChooser);
        timePanel.add(timeSparatorLabel);
        timePanel.add(minuteChooser);
        hourChooser.addPropertyChangeListener(this);
        minuteChooser.addPropertyChangeListener(this);

        //-rnp
        add(timePanel, BorderLayout.CENTER);

        dayChooser.addPropertyChangeListener(this);
        monthChooser.setDayChooser(dayChooser);
        monthChooser.addPropertyChangeListener(this);
        yearChooser.setDayChooser(dayChooser);
        yearChooser.addPropertyChangeListener(this);

        monthYearPanel.setBorder(BorderFactory.createEmptyBorder());

        JPanel setcancelPanel = new JPanel(new FlowLayout());


        setTimeButton = new JButton(lnfName);
        System.out.println("JCalendar creating new ttimebuttonlistener");
        TimerButtonListner timerButtonListener
                = new TimerButtonListner(
                controlFrame,
                this,
                lnfName,
                controlPanelComponents);

        setTimeButton.addActionListener(timerButtonListener);

        JButton cancelTimerButton = new JButton("Cancel");
        CancelTimerButtonListener cancelTimerButtonListener
                = new CancelTimerButtonListener(this, controlFrame);
        cancelTimerButton.addActionListener(cancelTimerButtonListener);
        setcancelPanel.add(setTimeButton);
        setcancelPanel.add(cancelTimerButton);

        add(setcancelPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
        repaint();
    }

    public void setTimeButtonName(String name) {
        setTimeButton.setText(name);

    }

    /**
     * Sets the calendar attribute of the JCalendar object
     *
     * @param c      the new calendar value
     * @param update the new calendar value
     */
    private void setCalendar(Calendar c, boolean update) {
        Calendar oldCalendar = calendar;
        calendar = c;

        if (update) {
            // Thanks to Jeff Ulmer for correcting a bug in the sequence :)
            yearChooser.setYear(c.get(Calendar.YEAR));
            monthChooser.setMonth(c.get(Calendar.MONTH));
            dayChooser.setDay(c.get(Calendar.DATE));
        }

        firePropertyChange("calendar", oldCalendar, calendar);
    }

    public Date getDate() {
        System.out.println(" jcalendar get date " + setDate);
        return setDate;
    }

    /**
     * JCalendar is a PropertyChangeListener, for its day, month and year
     * chooser.
     *
     * @param evt the property change event
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if (calendar != null) {
            Calendar c = (Calendar) calendar.clone();

            if (evt.getPropertyName().equals("day")) {
                c.set(Calendar.DAY_OF_MONTH,
                        (Integer) evt.getNewValue());
                setCalendar(c, false);
            } else if (evt.getPropertyName().equals("month")) {
                c.set(Calendar.MONTH, (Integer) evt.getNewValue());
                setCalendar(c, false);
            } else if (evt.getPropertyName().equals("year")) {
                c.set(Calendar.YEAR, (Integer) evt.getNewValue());
                setCalendar(c, false);
            } else if (evt.getPropertyName().equals("date")) {
                c.setTime((Date) evt.getNewValue());
                setCalendar(c, true);
            } else if (evt.getPropertyName().equals("hour")) {
                //     System.out.println(" In the hour clause");
                c.set(Calendar.HOUR_OF_DAY, (Integer) evt.getNewValue());
                setCalendar(c, true);
            } else if (evt.getPropertyName().equals("minute")) {
                      System.out.println(new StringBuilder().append("-------> In the minute clause").append(evt.getNewValue()).toString());
                c.set(Calendar.MINUTE, (Integer) evt.getNewValue());
                setCalendar(c, true);
            }
            c.set(Calendar.SECOND, 0);
            setCalendar(c, true);


            setDate = c.getTime();

        }
    }

}
