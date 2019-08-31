import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

class SpinnerHourListner implements ChangeListener {
    private JSpinner hour;

    public SpinnerHourListner(JSpinner hour) {
        this.hour = hour;
    }

    public void stateChanged(ChangeEvent e) {
        SpinnerNumberModel model = (SpinnerNumberModel) ((JSpinner) e.getSource()).getModel();
        int value = model.getNumber().intValue();

        System.out.println(value);

        if (value > 23) value = 0;
        if (value < 0) value = 23;
        hour.setValue(new Integer(value));
        int oldYear = 0;
   //     firePropertyChange("year", oldYear, value);


    }
}

class SpinnerMinuteListner implements ChangeListener {
    private JSpinner minute;

    public SpinnerMinuteListner(JSpinner minute) {
        this.minute = minute;
    }

    public void stateChanged(ChangeEvent e) {
        SpinnerNumberModel model = (SpinnerNumberModel) ((JSpinner) e.getSource()).getModel();
        int value = model.getNumber().intValue();

        System.out.println(value);

        if (value > 59) value = 0;
        if (value < 0) value = 59;
        minute.setValue(new Integer(value));

    }
}