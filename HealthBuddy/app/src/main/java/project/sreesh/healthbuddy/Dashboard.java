package project.sreesh.healthbuddy;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;


public class Dashboard extends ActionBarActivity implements View.OnClickListener{
String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Button exercise = (Button) findViewById(R.id.button);
        Button eat = (Button) findViewById(R.id.button3);
        Button settings = (Button) findViewById(R.id.button10);
        settings.setOnClickListener(this);
        exercise.setOnClickListener(this);
        eat.setOnClickListener(this);
        mailTask();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name= bundle.getString("username");
    }
    public void mailTask()
    {
        Calendar sendMailTime= Calendar.getInstance();
        sendMailTime.set(Calendar.HOUR_OF_DAY, 23);
        sendMailTime.set(Calendar.MINUTE, 59);

        //Calendar breakFast=

        Intent intent = new Intent(Dashboard.this, MailActivity.class);
        intent.putExtra("username", name);
        PendingIntent pendingIntent = PendingIntent.getActivity(Dashboard.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        ((AlarmManager) getSystemService(ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, sendMailTime.getTimeInMillis(), pendingIntent);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                Intent i = new Intent(this, Exercise.class);
                startActivity(i);
                break;
            case R.id.button3:
                Intent j = new Intent(this, Eat.class);
                startActivity(j);
                break;
            case R.id.button10:
                Intent s = new Intent(this, Settings.class);
                startActivity(s);
                break;


        }
    }
}
