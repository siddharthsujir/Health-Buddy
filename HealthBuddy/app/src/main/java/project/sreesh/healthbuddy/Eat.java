package project.sreesh.healthbuddy;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Calendar;

import java.util.Calendar;


public class Eat extends ActionBarActivity  implements View.OnClickListener {

    Location location;
    double hotelLat;
    double hotelLon;
    Context context = this;
    Context CTX = this;
    double calories;
    EditText editText7;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eat);

        Button confirm = (Button) findViewById(R.id.button4);
        confirm.setOnClickListener(this);
        Button save = (Button) findViewById(R.id.button90);
        save.setOnClickListener(this);
        editText7 = (EditText)findViewById(R.id.editText);

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button90:
                try {
                    hotelLat = location.getLatitude();
                    hotelLon = location.getLongitude();
                    DBedit DB = new DBedit(context);
                    DB.inserth(DB, hotelLat + "", hotelLon + "");
                    Toast.makeText(getBaseContext(), "Restaurant Location Saved", Toast.LENGTH_LONG).show();
                    break;
                }
                catch(Exception e)
                {
                    Toast.makeText(getBaseContext(), "Please switch on your GPS", Toast.LENGTH_LONG).show();
                }

            case R.id.button4:
                    try {
                        DBedit e = new DBedit(CTX);
                        Cursor CR = e.pullc(e);
                        int exists = 0;
                        double totalcal;
                        String date = Calendar.DATE + "";
                        if (CR != null && CR.moveToFirst()) {
                            Log.d("Eat method", "Inside Eat ");
                            do {

                                if (date.equals(CR.getString(0))) {
                                    calories = Double.parseDouble(CR.getString(1)) + Double.parseDouble(editText7.getText().toString());
                                    totalcal = Double.parseDouble(CR.getString(1)) + calories;
                                    e.insertc(e, date, (CR.getString(1)), (CR.getString(2)), calories + "", totalcal + "");
                                    System.out.println("Calories updated is " + calories);
                                    exists++;
                                    Toast.makeText(getBaseContext(), "Food Details entered", Toast.LENGTH_SHORT).show();
                                }

                            } while (CR.moveToNext());

                            if (exists == 0) {
                                calories = Double.parseDouble(CR.getString(1)) + Double.parseDouble(editText7.getText().toString());
                                totalcal = 0 + calories;
                                e.insertc(e, date, (CR.getString(1)), (CR.getString(2)), calories + "", totalcal + "");
                                System.out.println("Calories inserted is " + calories);
                                Toast.makeText(getBaseContext(), "Food Details entered", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("Eat method", "Cursor is null ");
                            calories = Double.parseDouble(editText7.getText().toString());
                            e.insertc(e, date, "0", "0", calories + "", "0");
                            System.out.println("Calories inserted is " + calories);
                            Toast.makeText(getBaseContext(), "Food Details entered", Toast.LENGTH_SHORT).show();
                        }
                        CR.close();
                        break;
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(getBaseContext(), "Enter Proper values", Toast.LENGTH_SHORT).show();
                    }
                }


    }
}