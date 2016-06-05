package net.mk786110.cityname;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String cityname;

    TextView latTextView;
    TextView longTextView;
    TextView locTextView;
    double latkumail;
    double longkumai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latTextView = (TextView) findViewById(R.id.LattextView);
        longTextView = (TextView) findViewById(R.id.LongtextView);
        locTextView = (TextView) findViewById(R.id.LocationtextView);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();

        locationManager.getBestProvider(criteria, true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));

        Geocoder gcd=new Geocoder(getBaseContext(), Locale.getDefault());
        latTextView.setText(String.valueOf(location.getLatitude()));
        longTextView.setText(String.valueOf(location.getLongitude()));

        Log.d("Tag","1");
        List<Address> addresses;

        try {
            addresses=gcd.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            if(addresses.size()>0)

            {
                //while(locTextView.getText().toString()=="Location") {
                cityname = addresses.get(0).getLocality().toString();
                locTextView.setText(cityname);
               getAddress();
                // }
            }

        } catch (IOException e) {
            e.printStackTrace();

        }

    }
    public void clcikbutton(View view)
    {
        Intent intent=new Intent (this,HomeActivity.class);
       startActivity(intent);
    }
    public void getAddress() {
        Geocoder gcd=new Geocoder(getBaseContext(), Locale.getDefault());

       /* latkumail=Double.parseDouble(lat.toString());
        longkumai=Double.parseDouble(lng.toString());*/
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(33.7295, 73.0373, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);

           /* GUIStatics.currentAddress = obj.getSubAdminArea() + "," + obj.getAdminArea();
            GUIStatics.latitude = obj.getLatitude();
            GUIStatics.longitude = obj.getLongitude();
            GUIStatics.currentCity= obj.getSubAdminArea();
            GUIStatics.currentState= obj.getAdminArea();*/
            add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();

            Log.v("IGA", "Address" + add);
            Toast.makeText(this, add, Toast.LENGTH_SHORT).show();
            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


}

