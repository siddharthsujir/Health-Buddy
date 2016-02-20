package project.sreesh.healthbuddy;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.hardware.SensorEventListener;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Settings extends ActionBarActivity implements View.OnClickListener {


    EditText mealH,mealM;
    int hours;
    int minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button save = (Button) findViewById(R.id.button16);
        mealH=(EditText) findViewById(R.id.editText8);
        mealM=(EditText) findViewById(R.id.editText9);
        save.setOnClickListener(this);


//
//        Calendar bfTime= Calendar.getInstance();
//        bfTime.set(Calendar.HOUR_OF_DAY, 8);
//        bfTime.set(Calendar.MINUTE, 30);
//
//        Calendar dinnerTime= Calendar.getInstance();
//        dinnerTime.set(Calendar.HOUR_OF_DAY, 20);
//        dinnerTime.set(Calendar.MINUTE, 30);

        //Calendar breakFast=


       // ((AlarmManager) getSystemService(ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, bfTime.getTimeInMillis(), pendingIntent);
        //((AlarmManager) getSystemService(ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, dinnerTime.getTimeInMillis(), pendingIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
    public void onClick(View v) {
        switch (v.getId()) {

                case R.id.button16:
                    try {
                        hours = Integer.parseInt(mealH.getText().toString());
                        minutes = Integer.parseInt(mealM.getText().toString());
                        Toast.makeText(getBaseContext(), "Meal time Set", Toast.LENGTH_SHORT).show();
                        System.out.println(hours);
                        System.out.println(minutes);
                        Calendar lunchTime = Calendar.getInstance();
                        lunchTime.set(Calendar.HOUR_OF_DAY, hours);
                        lunchTime.set(Calendar.MINUTE, minutes);

                        Calendar dinnerTime = Calendar.getInstance();
                        dinnerTime.set(Calendar.HOUR_OF_DAY, 20);
                        dinnerTime.set(Calendar.MINUTE, 30);

                        Intent intent = new Intent(Settings.this, Eat.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(Settings.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                        ((AlarmManager) getSystemService(ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, lunchTime.getTimeInMillis(), pendingIntent);
                        ((AlarmManager) getSystemService(ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, dinnerTime.getTimeInMillis(), pendingIntent);
                        break;
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(getBaseContext(), "Enter a proper mealtime", Toast.LENGTH_SHORT).show();
                    }


        }
    }
}
