package project.sreesh.healthbuddy;

import android.content.Context;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class Push extends ActionBarActivity implements SensorEventListener,View.OnClickListener {
    SensorManager sm;
    Sensor proxSensor;
    public static int count=0;
    TextView countText;
    TextView caloriestext;
    int seconds=0;
   static double calories=0;
    Context CTX = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        sm=(SensorManager) getSystemService(SENSOR_SERVICE);
        Button save = (Button) findViewById(R.id.button8);
        save.setOnClickListener(this);
        proxSensor=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        caloriestext=(TextView) findViewById(R.id.textView5);
        countText=(TextView) findViewById(R.id.countProxText);
        sm.registerListener(this,proxSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_push, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Calendar c = Calendar.getInstance();


        if(c.get(Calendar.SECOND)>seconds) {
            //System.out.println("Current time =&gt; "+c.getTime());
            if (event.values[0] != 0) {
                count++;
                calories=calories+0.5;
            }
            seconds=c.get(Calendar.SECOND);

            if(seconds==59)
            {
                seconds=0;
            }
        }

        countText.setText(String.valueOf(count));
        caloriestext.setText(String.valueOf(calories));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button8:
                try {
                    DBedit e = new DBedit(CTX);
                    Cursor CR = e.pullc(e);
                    int exists = 0;
                    double totalcal;
                    String date = Calendar.DATE + "";
                    if (CR != null && CR.moveToFirst()) {
                        do {

                            if (date.equals(CR.getString(0))) {
                                calories = Double.parseDouble(CR.getString(1)) + Double.parseDouble(caloriestext.getText().toString());
                                totalcal = Double.parseDouble(CR.getString(1)) - calories;
                                e.insertc(e, date, (CR.getString(1)), (CR.getString(2)), calories + "", totalcal + "");
                                System.out.println("Calories updated is " + calories);
                                exists++;
                                Toast.makeText(getBaseContext(), "Calorie Details entered", Toast.LENGTH_SHORT).show();
                            }

                        } while (CR.moveToNext());

                        if (exists == 0) {
                            calories = Double.parseDouble(CR.getString(1)) + Double.parseDouble(caloriestext.getText().toString());
                            totalcal = 0 - calories;
                            e.insertc(e, date, (CR.getString(1)), (CR.getString(2)), calories + "", totalcal + "");
                            System.out.println("Calories inserted is " + calories);
                            Toast.makeText(getBaseContext(), "Calorie Details entered", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        calories = Double.parseDouble(caloriestext.getText().toString());
                        e.insertc(e, date, calories + "", "0", "0", calories + "");
                        System.out.println("Calories inserted is " + calories);
                        Toast.makeText(getBaseContext(), "Calorie Details entered", Toast.LENGTH_SHORT).show();
                    }
                    CR.close();
                    break;
                }
                catch(Exception e)
                {
                    Toast.makeText(getBaseContext(), "Push ups failed", Toast.LENGTH_SHORT).show();
                }
        }


        //calories = Integer.parseInt(CR.getString(1)) - Integer.parseInt(caloriestext.getText().toString());

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
