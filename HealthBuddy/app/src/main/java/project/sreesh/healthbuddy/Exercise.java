package project.sreesh.healthbuddy;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Exercise extends ActionBarActivity implements View.OnClickListener{

    Location location;
    double gymlLat;
    double gymLon;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Button push_ups = (Button) findViewById(R.id.button5);
        Button sit_ups = (Button) findViewById(R.id.button6);
        Button run = (Button) findViewById(R.id.button7);
        Button save = (Button) findViewById(R.id.button9);
        push_ups.setOnClickListener(this);
        sit_ups.setOnClickListener(this);
        run.setOnClickListener(this);
        save.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button5:
                Intent i = new Intent(this, Push.class);
                startActivity(i);
                break;
            case R.id.button6:
                Intent j = new Intent(this, Sit.class);
                startActivity(j);
                break;
            case R.id.button7:
                Intent k = new Intent(this, MapsActivity.class);
                startActivity(k);
                break;
            case R.id.button9:
               try {
                   gymlLat = location.getLatitude();
                   gymLon = location.getLongitude();
                   DBedit DB = new DBedit(context);
                   DB.insertg(DB, gymlLat + "", gymLon + "");
                   Toast.makeText(getBaseContext(), "Gym Location Saved", Toast.LENGTH_SHORT).show();
                   break;
               }
               catch(Exception e)
               {
                   Toast.makeText(getBaseContext(), "Please Switch on GPS", Toast.LENGTH_SHORT).show();
               }
        }
    }

}
