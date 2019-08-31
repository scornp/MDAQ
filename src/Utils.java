import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 30-Dec-2005
 * Time: 00:09:48
 * To change this template use File | Settings | File Templates.
 */
public final  class Utils {

    /**
     * A routine to generate a string representation of the current date and time
     *
     * @return String
     */
    public static String getDateString() {
        String dateStamp;
        Date currentDate;            // Used to get date to display
        SimpleDateFormat formatter;  // Formats the date displayed
        formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss yyyy", Locale.getDefault());
        currentDate = new Date();
        formatter.applyPattern("yyyy MM dd HH mm ss");
        dateStamp = formatter.format(currentDate).toString();
        return dateStamp;
    }
}
