package control;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.backend.BackendFactory;
import model.backend.IBackend;
import model.entities.Trip;


import com.example.bracha.myfirstapp.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * this class suitable to the activity that invoked  after the main activity finished
 */
public class TripActivity extends AppCompatActivity {

    EditText startLocEdittext;
    LocationManager locationManager;
    LocationListener locationListener;
     long LOCATION_REFRESH_TIME=5000;
    float LOCATION_REFRESH_DISTANCE=100;
    /**
     * that function is invoked first when this activity appears
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        startLocEdittext=(EditText)findViewById(R.id.username_startLoc);
       //init the listener of the location
locationListener=new LocationListener() {
    /**
     * this function called when the location of the user is changed
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        startLocEdittext.setText(getPlace(location));

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
};

        try{
        locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
        //checks permissions in runtime
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);
            }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0, locationListener);
        }
        catch (Exception e)
        {
            Toast.makeText(TripActivity.this, e.getMessage()+"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", Toast.LENGTH_LONG).show();

        }

        EditText startAddress=(EditText)findViewById(R.id.username_startLoc);
        startAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            /**
             * this function is invoked when the text on username_startLoc changed
             * @param s
             * @param start
             * @param before
             * @param count
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Toast toast= Toast.makeText(TripActivity.this,"street name+number,city",Toast.LENGTH_LONG);
                View view=toast.getView();
                view.setBackgroundResource(R.drawable.toast_background);
                toast.show();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        EditText desAddress=(EditText)findViewById(R.id.username_desLoc);
        desAddress.addTextChangedListener(new TextWatcher() {
            /**
             * this function is invoked before the text on username_desLoc edittext changed
             * @param s
             * @param start
             * @param count
             * @param after
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Toast toast= Toast.makeText(TripActivity.this,"street name+number,city",Toast.LENGTH_LONG);
                View view=toast.getView();
                view.setBackgroundResource(R.drawable.toast_background);
                toast.show();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Button ok=(Button)findViewById(R.id.buttonOK);
        final IBackend backend= BackendFactory .getInstance(this);//an instance of the class that implements the IBackend interface
        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Boolean flagName=true;
                Boolean flagEmail=true;
                Boolean flagTel=true;
                Boolean flagStartLoc=true;
                Boolean flagDesLoc=true;
                EditText nameEdittext=(EditText)findViewById(R.id.username_name);
                String name=nameEdittext.getText().toString().trim();
                if(name.length()==0)
                {
                    nameEdittext.setError("please enter your name");
                    flagName=false;
                }
                EditText emailEdittext=(EditText)findViewById(R.id.username_email);
                String email=emailEdittext.getText().toString().trim();
                String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (!email.matches(emailPattern)||email.length()==0)
                {
                    emailEdittext.setError("wrong email! please enter again");
                    flagEmail=false;
                }
                EditText telEdittext=(EditText)findViewById(R.id.username_tel);
                String tel=telEdittext.getText().toString().trim();
                if(tel.length()!=0) {
                    String preNumber = tel.substring(0, 3);
                    if ((!preNumber.matches("050") && !preNumber.matches("052") && !preNumber.matches("053") && !preNumber.matches("054") && !preNumber.matches("055")
                            && !preNumber.matches("057") && !preNumber.matches("058")) || tel.length() != 10) {
                        telEdittext.setError("wrong number! please enter again");
                        flagTel = false;
                    }
                }
                else
                {
                    telEdittext.setError("please enter your phone number");
                    flagTel = false;
                }
                EditText startLocEdittext=(EditText)findViewById(R.id.username_startLoc);
                String startLoc=startLocEdittext.getText().toString().trim();
                if (!isAddress(startLocEdittext))
                {
                    flagStartLoc=false;
                }
                EditText desLocEdittext=(EditText)findViewById(R.id.username_desLoc);
                String desLoc=desLocEdittext.getText().toString().trim();
                if(!isAddress(desLocEdittext))
                {
                    flagDesLoc=false;
                }
                if(flagName&&flagTel&&flagDesLoc&&flagEmail&&flagStartLoc) {
                    final Trip trip = new Trip(startLoc, desLoc, name, tel, email);
                    new AsyncTask<Context, Void, Void>() {

                        /**
                         * this function runs in seperate thread from the UI thread and called the function that add new trip to the database
                         *
                         * @param contexts
                         * @return
                         */
                        @Override
                        protected Void doInBackground(Context... contexts) {
                            backend.addTrip(trip);
                            return null;
                        }
                    }.execute();
                    Toast toast = Toast.makeText(TripActivity.this, "the trip added successfully", Toast.LENGTH_LONG);
                    View view = toast.getView();
                    view.setBackgroundResource(R.drawable.toast_background);
                    toast.show();
                    nameEdittext.setText("");
                    emailEdittext.setText("");
                    telEdittext.setText("");
                }
            }
        });

    }

    /**
     * this function gets the edittext that contains a location and checks if it is a legal location
     * @param editText
     * @return
     */
    public Boolean isAddress(EditText editText)
    {
        if(editText.getText().toString().length()>0)
        {
            try {
                Geocoder gc = new Geocoder(this);
                if (gc.isPresent()) {

                    List<Address> list = gc.getFromLocationName(editText.getText().toString(), 1);
                    Address address=list.get(0);
                    double lat=address.getLatitude();
                    double lng=address.getLongitude();
                    Location locationA=new Location("A");
                    locationA.setLatitude(lat);
                    locationA.setLongitude(lng);

                }
            }
            catch (Exception e)
            {
                editText.setError("invalid address");
                return false;
            }
        }
        else
            return false;
        return true;
    }

    /**
     * this function gets a location object and cast it to a string
     * @param location
     * @return
     */
    public String getPlace(Location location)
    {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {

            addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
           if(addresses.size()>0)
           {
               String cityName=addresses.get(0).getLocality();
               String streetName=addresses.get(0).getThoroughfare();
               String countryName=addresses.get(0).getCountryName();
               String numberHouse=addresses.get(0).getSubThoroughfare();
               String currentLoc= streetName+ " "+numberHouse+", "+cityName+ ", "+countryName;
               locationManager.removeUpdates(locationListener);
               return currentLoc;
           }
    }
    catch (IOException e)
    {
        System.out.println("@@ catch");
        e.printStackTrace();
    }
        return "IOException ...";
}


}
