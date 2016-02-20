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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class Sit extends ActionBarActivity implements SensorEventListener,View.OnClickListener {

    private TextView xText,yText,zText,countAccText,caloriesText;
    private Sensor mySensor;
    private SensorManager sm;
    private int count=0;
    int seconds=0;
    double calories=0;
    Context CTX = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sit);



        sm=(SensorManager) getSystemService(SENSOR_SERVICE);
        mySensor=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener((SensorEventListener) this,mySensor,SensorManager.AXIS_Y);
        countAccText=(TextView) findViewById(R.id.countAccText);
        caloriesText=(TextView) findViewById(R.id.textView7);
        Button save = (Button) findViewById(R.id.button9);
        save.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sit,menu);
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

        final float alpha = 0.8f;

        Calendar c = Calendar.getInstance();

        double[] gravity = new double[3];
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        double[] linear_acceleration = new double[3];
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];


//        xText.setText("X: " + linear_acceleration[0]);
//        yText.setText("Y: " + linear_acceleration[1]);
//        zText.setText("Z: " + linear_acceleration[2]);
        double y = linear_acceleration[1];
        double z = linear_acceleration[2];
        double x = linear_acceleration[0];

        if(c.get(Calendar.SECOND)>seconds) {
            if (z > 7) {
                if (y > 2) {
                    count++;
                    calories=calories+0.3;
                }
            } else if (y > 7) {
                if (z > 2) {
                    count++;
                    calories=calories+0.3;
                }
            } else if (x > 7) {
                if (z > 2) {
                    count++;
                    calories=calories+0.3;
                }
            }
            seconds=c.get(Calendar.SECOND);

            if(seconds==59)
            {
                seconds=0;
            }
        }
        countAccText.setText(count+"");
        caloriesText.setText(calories+"");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button9:
                DBedit e = new DBedit(CTX);
                Cursor CR = e.pullc(e);
                int exists = 0;
                double totalcal;
                String date = Calendar.DATE + "";
                if (CR != null && CR.moveToFirst()) {
                    Log.d("Eat method", "Inside Eat ");
                    do {

                        if (date.equals(CR.getString(0))) {
                            calories = Double.parseDouble(CR.getString(1)) + Double.parseDouble(caloriesText.getText().toString());
                            totalcal = Double.parseDouble(CR.getString(1)) - calories;
                            e.insertc(e, date, (CR.getString(1)), (CR.getString(2)), calories + "", totalcal + "");
                            System.out.println("Calories updated is " + calories);
                            exists++;
                            Toast.makeText(getBaseContext(), "Calorie Details entered", Toast.LENGTH_SHORT).show();
                        }

                    } while (CR.moveToNext());

                    if (exists == 0) {
                        calories = Double.parseDouble(CR.getString(1)) + Double.parseDouble(caloriesText.getText().toString());
                        totalcal = 0 - calories;
                        e.insertc(e, date, (CR.getString(1)), (CR.getString(2)), calories + "", totalcal + "");
                        System.out.println("Calories inserted is " + calories);
                        Toast.makeText(getBaseContext(), "Calorie Details entered", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("Eat method", "Cursor is null ");
                    calories = Double.parseDouble(caloriesText.getText().toString());
                    e.insertc(e, date, calories + "", "0", "0", calories + "");
                    System.out.println("Calories inserted is " + calories);
                    Toast.makeText(getBaseContext(), "Calorie Details entered", Toast.LENGTH_SHORT).show();
                }
                CR.close();
                break;
        }
    }
}
