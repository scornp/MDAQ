import javax.swing.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 30-Dec-2005
 * Time: 09:07:30
 * To change this template use File | Settings | File Templates.
 */
public class ElapsedTimeCounter extends Thread{
    private Date date1;
    private long startTimeMills;
    private long endTimeMills;
    private boolean isRunning;
    private JLabel elapsedTime;

    public ElapsedTimeCounter(JLabel elapsedTime) {
        date1 = new Date();
        this.elapsedTime = elapsedTime;
        startTimeMills = date1.getTime();

    }

    public void pleaseStop(){
        isRunning = false;
    }

    public void run(){
        isRunning = true;
        int elapsedTimeSec;
        int days;
        int hours;
        int minutes;
        int seconds;
        int remainder;
        Date date2;

        while(isRunning){
            //        System.out.println("ElapsedTimeCounter");
            try {
                Thread.sleep(1000);
                date2 = new Date();
                endTimeMills = date2.getTime();
                elapsedTimeSec = (int)(endTimeMills - startTimeMills)/1000;

                days =  elapsedTimeSec/(24*3600);
                remainder = elapsedTimeSec - days*24*3600;
                hours = remainder/3600;
                remainder = remainder - hours*3600;
                minutes =  remainder/60;
                remainder = remainder - minutes*60;
                seconds = remainder;

                String t = Integer.valueOf(days).toString() + " " +
                        Integer.valueOf(hours).toString() + ":" +
                        Integer.valueOf(minutes).toString() + ":" +
                        Integer.valueOf(seconds).toString();
                //          System.out.println("ElapsedTimeCounter: Elapsed time " + elapsedTimeSec + "   " + t
                //                  + " " + endTimeMills + " " + startTimeMills);
                elapsedTime.setText(t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
