package com.srinija.pandatimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    int hrs = 0, mins = 0, secs = 0;
    TextView timeleft;
    CountDownTimer countDownTimer;
    EditText hours, minutes, seconds;
    TextView textView1, textView2, textView3;
    ImageView imageView;
    MediaPlayer mediaPlayer;
    String hrtext, mintext, sectext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeleft = (TextView) findViewById(R.id.countdown);

        timeleft.setText(" ");

        ((Button) findViewById(R.id.stopBut)).animate().alpha(0f);
        ((Button) findViewById(R.id.audioStop)).animate().alpha(0f);
    }

    public void started(View view)
    {
        ((Button) findViewById(R.id.stopBut)).animate().alpha(1f);

        hours = (EditText) findViewById(R.id.hours);
        minutes = (EditText) findViewById(R.id.minutes);
        seconds = (EditText) findViewById(R.id.seconds);

        String h = hours.getText().toString();
        String m = minutes.getText().toString();
        String s = seconds.getText().toString();

        if(checkIfEmpty(h))
            hrs = 0;
        else
            hrs = Integer.parseInt(h);

        if(checkIfEmpty(m))
            mins = 0;
        else
            mins = Integer.parseInt(m);

        if(checkIfEmpty(s))
            secs = 0;
        else
            secs = Integer.parseInt(s);



        textView1 = (TextView) findViewById(R.id.textView4);
        textView2 = (TextView) findViewById(R.id.textView5);
        textView3 = (TextView) findViewById(R.id.textView6);

        hours.animate().alpha(0f);
        minutes.animate().alpha(0f);
        seconds.animate().alpha(0f);
        textView1.animate().alpha(0f);
        textView2.animate().alpha(0f);
        textView3.animate().alpha(0f);

        ((Button) view).animate().alpha(0f);

        //timeleft = (TextView) findViewById(R.id.countdown);

        hrtext = Integer.toString(hrs);
        mintext = Integer.toString(mins);
        sectext = Integer.toString(secs);

        if(hrtext.length()<2)
            hrtext = "0"+hrtext;
        if(mintext.length()<2)
            mintext = "0"+mintext;
        if(sectext.length()<2)
            sectext = "0"+sectext;

        timeleft.setText(hrtext+":"+mintext+":"+sectext);

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(((Button)view).getWindowToken(), 0);
        //Log.i("time:" , Integer.toString(hrs)+":"+Integer.toString(mins)+":"+Integer.toString(secs));

        int totalSecs = hrs*3600 + mins*60 + secs;

       countDownTimer =  new CountDownTimer(totalSecs*1000, 1000)
        {
            @Override
            public void onTick(long l) {
                secs = (int) ((l/1000) %60);
                mins = (int) ((l/(1000*60)) % 60);
                hrs = (int) ((l/1000*3600) % 60);
                Log.i("Left:" , Integer.toString(hrs)+":"+Integer.toString(mins)+":"+Integer.toString(secs));
                hrtext = Integer.toString(hrs);
                mintext = Integer.toString(mins);
                sectext = Integer.toString(secs);

                if(hrtext.length()<2)
                    hrtext = "0"+hrtext;
                if(mintext.length()<2)
                    mintext = "0"+mintext;
                if(sectext.length()<2)
                    sectext = "0"+sectext;

                timeleft.setText(hrtext+":"+mintext+":"+sectext);
                //Log.i("MIlliseconds:", Long.toString(l));
            }

            @Override
            public void onFinish() {

                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.akarm);

                mediaPlayer.start();
                imageView = (ImageView) findViewById(R.id.imageView2);
                imageView.animate().alpha(1f);
                ((Button) findViewById(R.id.audioStop)).animate().alpha(1f);
                ((Button)findViewById(R.id.stopBut)).animate().alpha(0f);

                hours.setText("");
                minutes.setText("");
                seconds.setText("");

            }
        }.start();


    }

    public void stopped(View view)
    {

        countDownTimer.cancel();
        ((Button)findViewById(R.id.startBut)).animate().alpha(1f);
        hours.animate().alpha(1f);
        minutes.animate().alpha(1f);
        seconds.animate().alpha(1f);
        textView1.animate().alpha(1f);
        textView2.animate().alpha(1f);
        textView3.animate().alpha(1f);
        hours.setText(Integer.toString(hrs));
        minutes.setText(Integer.toString(mins));
        seconds.setText(Integer.toString(secs));
        ((Button)view).animate().alpha(0f);
    }

    public void wokeUP(View view)
    {
        mediaPlayer.stop();
        ((Button)view).animate().alpha(0f);
        ((Button)findViewById(R.id.startBut)).animate().alpha(1f);
        hours.animate().alpha(1f);
        minutes.animate().alpha(1f);
        seconds.animate().alpha(1f);
        textView1.animate().alpha(1f);
        textView2.animate().alpha(1f);
        textView3.animate().alpha(1f);
        timeleft.setText(" ");
        ((Button) findViewById(R.id.stopBut)).animate().alpha(0f);
    }


    public boolean checkIfEmpty(String s)
    {
        if(s.matches("")){
            return true;
        }
        return false;
    }

    public void enteredChar(String s)
    {
        try {
            Integer.parseInt(s);
            //Toast.makeText(this, "Enter a number", Toast.LENGTH_SHORT).show();
        }
        catch (NumberFormatException n)
        {
            Toast.makeText(this, "Enter a number", Toast.LENGTH_SHORT).show();
        }
    }
}