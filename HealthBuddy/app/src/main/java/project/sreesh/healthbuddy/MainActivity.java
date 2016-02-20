package project.sreesh.healthbuddy;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, LocationListener {
    EditText editText1;
    EditText editText2;
    Button login;
    Button sign;
    int counter=3;
    Context CTX = this;
    protected LocationManager locationManager;
    double latMin;
    double latMax;
    double lonMin;
    double lonMax;
    String name;




    Location location;
    double curLat;
    double curLon;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    //    mailTask();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);



    }

    public void checkGym(double latMin,double latMax,double lonMin,double lonMax)
    {
        DBedit e = new DBedit(CTX);
        Cursor CR = e.pullg(e);
        if(CR!=null && CR.moveToFirst()) {

            do {
                if ((latMin < Double.parseDouble(CR.getString(1)) && latMax > Double.parseDouble(CR.getString(1)) && (lonMin < Double.parseDouble(CR.getString(2)) && lonMax > Double.parseDouble(CR.getString(2))))) {
                    Intent i = new Intent(this, Exercise.class);
                    startActivity(i);
                }
            } while (CR.moveToNext());
        }
    }

    public void checkHotel(double latMin,double latMax,double lonMin,double lonMax)
    {
            DBedit e = new DBedit(CTX);
            Cursor CR = e.pullh(e);
        if(CR!=null &&  CR.moveToFirst())
        {
            do {
                if ((latMin < Double.parseDouble(CR.getString(0)) && latMax > Double.parseDouble(CR.getString(0)) && (lonMin < Double.parseDouble(CR.getString(1)) && lonMax > Double.parseDouble(CR.getString(1))))) {
                    Intent i = new Intent(this, Eat.class);
                    startActivity(i);
                }
            } while (CR.moveToNext());
        }
    }

//    public void mailTask()
//    {
//        Calendar sendMailTime= Calendar.getInstance();
//        sendMailTime.set(Calendar.HOUR_OF_DAY, 11);
//        sendMailTime.set(Calendar.MINUTE, 59);
//
//        //Calendar breakFast=
//
//        Intent intent = new Intent(MainActivity.this, MailActivity.class);
//        intent.putExtra("username", name);
//        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//        ((AlarmManager) getSystemService(ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, sendMailTime.getTimeInMillis(), pendingIntent);
//    }
    public void login(View v)
    {
        editText1 = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);
        login = (Button)findViewById(R.id.login1);
        sign = (Button)findViewById(R.id.signup1);
        login.setOnClickListener(this);
        sign.setOnClickListener(this);

    }

    public void onClick(View v)
    {
        name= editText1.getText().toString();
        String password = editText2.getText().toString();

        switch (v.getId()){
            case R.id.login1:
                DBedit e = new DBedit(CTX);
                Cursor CR = e.pull(e);
                if(CR!=null && CR.moveToFirst()) {
                    boolean loginstatus = false;
                    String NAME = "";

                    do {
                        if (name.equals(CR.getString(0)) && (password.equals(CR.getString(1)))) {
                            loginstatus = true;
                            NAME = CR.getString(0);
                        }
                    } while (CR.moveToNext());

                    if (loginstatus) {
                        Toast.makeText(getApplicationContext(), "Login Success  \n Welcome " + NAME, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(this, Dashboard.class);
                        i.putExtra("username", name);
                        System.out.println("check 1 --------");
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                        counter--;
                        if (counter == 0) {
                            Toast.makeText(getApplicationContext(), "Maximum attempts Exceeded", Toast.LENGTH_LONG).show();
                            login.setEnabled(false);

                        }
                    }
                    break;
			/*if (name.equals("a") && password.equals("b"))
					{
					Intent i=new Intent (this,AppTasks.class);
					startActivity(i);
					}
			else {
			      Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
			    	      counter--;
			    	      if(counter==0){
			    	    	 Toast.makeText(getApplicationContext(), "Maximum attempts Exceeded",Toast.LENGTH_LONG).show();
			    	         login.setEnabled(false);
			    	      }
			}*/
                }
            case R.id.signup1:
                Intent j=new Intent (this,Register.class);
                startActivity(j);
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        try {
            curLat = location.getLatitude();
            curLon = location.getLongitude();
            latMin = curLat - 0.002;
            latMax = curLat + 0.002;
            lonMin = curLat - 0.002;
            lonMax = curLat + 0.002;
            System.out.print(curLat);
            checkGym(latMin, latMax, lonMin, lonMax);
            checkHotel(latMin, latMax, lonMin, lonMax);
        }
        catch(Exception e)
        {
            Toast.makeText(getBaseContext(), "Please switch on your GPS", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
