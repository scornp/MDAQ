

import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import javax.swing.border.Border;
import javax.sound.sampled.*;
import java.io.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Roger philp
 * Date: 25-Aug-2005
 * Time: 10:06:44
 * The purpose of this code is to aquire the ion trails of meteors using
 * audio cards. It based on code taken in part from the internet whaen the author
 * is identified he will be acknowledged.
 */
public class MDAQ {
    public static void main(String args[]) {

        /*    LineChartDemo2 demo = new LineChartDemo2("Line Chart Demo 2");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);*/
/*        int i = 0;

        int N = 4096*2;//Integer.parseInt(args[0]);
        Complex[] x = new Complex[N];

        for (i = 0; i < N; i++) {
            if (i < N / 2 - 50 || i > N / 2 + 50) {
                x[i] = new Complex(0, 0);
            } else {
                x[i] = new Complex(1, 0);
            }
         }
        System.out.println("x");
        System.out.println("-------------------");
        for (i = 0; i < N; i++)
            System.out.println(x[i]);
        System.out.println();
        PrintStream fourierStream = null;
        try {
            fourierStream = new PrintStream(new FileOutputStream("Fourrier.dat"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
        // FFT of original data
        Complex[] y = FFT.fft(x);
        System.out.println("y = fft(x)");
        System.out.println("-------------------");
        for (i = 0; i < N; i++) {
            System.out.println(y[i].abs());
            fourierStream.println(y[i].abs());
        }

        fourierStream.close();

        System.out.println();

        float [] xx, yy;
        float wmin = 0, wmax = 44100/2, wdelta;

                 wdelta = (wmax - wmin)/(N/2 - 1);

        xx = new float[N/2];
        yy = new float[N/2];

        for(i = 0; i < N/2; i++){
            xx[i] = wmin + i*wdelta;
            yy[i] = (float)y[i].Re();
        }

        LineChartDemo2 demo = new LineChartDemo2("Fourier transform", xx, yy);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);*/
        /*       while(true){
     try {
       Thread.currentThread().sleep( 1000);

         System.out.println("hello world" + i++);
         demo.chartModifier ();
       }
     catch (InterruptedException e) {
       e.printStackTrace();
       }
        }*/

        System.out.println("hello world");

        String version = new String("Version 3.2");
            SplashWindowFrame splashFrame;
splashFrame = new SplashWindowFrame(version);

splashFrame.dispose();
ControlFrame   controlFrame = new ControlFrame(version);

    }
}//end outer class AudioCapture01.java
