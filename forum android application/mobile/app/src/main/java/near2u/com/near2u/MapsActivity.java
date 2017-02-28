package near2u.com.near2u;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import near2u.com.near2u.entities.History;
import near2u.com.near2u.entities.UserLocation;
import near2u.com.near2u.helpers.ServerHelper;
import near2u.com.near2u.helpers.SessionManager;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    private AlertDialog mDialog;
    ProgressDialog prgDialog;
    private String m_Text;


    GoogleMap googleMap;
    LatLng myPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

            prgDialog = new ProgressDialog(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Enabling MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(true);

        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            // Getting latitude of the current location
            double latitude = location.getLatitude();

            // Getting longitude of the current location
            double longitude = location.getLongitude();

            // Creating a LatLng object for the current location
            myPosition = new LatLng(latitude, longitude);

            saveUserLocation(latitude, longitude);

            List<UserLocation> userLocations = getUserLocations();

        }

    }

    public List<UserLocation> getUserLocations() {
        AsyncHttpClient client = new AsyncHttpClient();
        final List<UserLocation> userLocations = new ArrayList<UserLocation>();

        client.get("http://" + ServerHelper.getIP()+ "/near2u2/user/location", new RequestParams(),new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObj) {

                try {
                    JSONArray forumsArray = jsonObj.getJSONArray("users");
                    JSONObject o;
                    for (int i = 0; i < forumsArray.length(); i++) {
                        o = forumsArray.getJSONObject(i);
                        userLocations.add(new UserLocation(o.getInt("user_id"), o.getString("username"),
                                o.getDouble("longitude"), o.getDouble("latitude")));
                    }

                    mMap.clear();

                    MarkerOptions markerOptions = new MarkerOptions();
                    UserLocation ul;
                    LatLng pos;

                    for(int i=0;i<userLocations.size(); i++ ) {
                        ul = userLocations.get(i);
                        pos = new LatLng(ul.getLatitude(), ul.getLongitude());
                        markerOptions.position(pos).title(ul.getUserId().toString()).snippet(ul.getUsername()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        mMap.addMarker(markerOptions);
                    }
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 10.0f));
                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                        @Override
                        public boolean onMarkerClick(Marker arg0) {
                            String username = arg0.getSnippet();
                            showUserMenuPopUp(username, arg0.getTitle());
                            return true;
                        }

                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        return userLocations;
    }

    public void showUserMenuPopUp(String username, String userId) {
        final String uname = username;
        final String uId = userId;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("User " + uname);
        // Set up the input
        final TextView tv = new TextView(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        tv.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(tv);

        // Set up the buttons
        builder.setNegativeButton("Send message", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = tv.getText().toString();
                try {
                    sendMessage(m_Text, uId);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNeutralButton("Info", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent userDetailsIntent = new Intent(MapsActivity.this, UserDetailsActivity.class);
                userDetailsIntent.putExtra("username", uname);
                startActivity(userDetailsIntent);
            }
        });
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void showBroadcastPopUp(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Broadcast message");
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                try {
                    broadcastMessage(m_Text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void broadcastMessage(String message) throws UnsupportedEncodingException, JSONException {

        // *** Build the Http Request ***/
        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();
        params.put("message", message);
        params.put("userId", Integer.toString(SessionManager.getIntFromSession(getApplicationContext(), "userId")));
        params.put("radius", SessionManager.getIntFromSession(getApplicationContext(), "distance"));

        // Invoke RESTful Web Service with Http parameters
        invokeWSBroadcast(params);

    }

    public void invokeWSBroadcast(RequestParams params) throws JSONException, UnsupportedEncodingException{
        // Show Progress Dialog
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://" + ServerHelper.getIP() + "/near2u2/messages/broadcast", params, new AsyncHttpResponseHandler() {
//    	 client.post(getBaseContext(), "http://10.0.2.2/near2u2/messages",entity, "application/json" ,new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {
                    String respBody = new String(responseBody);
                    // JSON Object
                    JSONObject obj = new JSONObject(new String(responseBody));
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getString("message") != null) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                    // Else display error message
                    else {
                        Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                  Throwable error) {
                // Hide Progress Dialog
                prgDialog.hide();
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }

            }

        });


    }

    public void sendMessage(String message, String userId) throws UnsupportedEncodingException, JSONException {

        final String uId = userId;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send message");
        // Set up the input
        final EditText inputMessage = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        inputMessage.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(inputMessage);

        // Set up the buttons
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = inputMessage.getText().toString();
                try {
                    // *** Build the Http Request ***/
                    // Instantiate Http Request Param Object
                    RequestParams params = new RequestParams();
                    params.put("message", inputMessage.getText());
                    params.put("userId", Integer.toString(SessionManager.getIntFromSession(getApplicationContext(), "userId")));
                    params.put("recipientIds", uId);

                    // Invoke RESTful Web Service with Http parameters
                    invokeWS(params);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     * @throws JSONException
     * @throws UnsupportedEncodingException
     */
    public void invokeWS(RequestParams params) throws JSONException, UnsupportedEncodingException{
        // Show Progress Dialog
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://" + ServerHelper.getIP() + "/near2u2/messages", params, new AsyncHttpResponseHandler() {
//    	 client.post(getBaseContext(), "http://10.0.2.2/near2u2/messages",entity, "application/json" ,new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {
                    String respBody = new String(responseBody);
                    // JSON Object
                    JSONObject obj = new JSONObject(new String(responseBody));
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getString("message") != null) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                    // Else display error message
                    else {
                        Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                  Throwable error) {
                // Hide Progress Dialog
                prgDialog.hide();
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }

            }

        });


    }

    public void refreshMap(View view) {
        finish();
        startActivity(getIntent());
    }

    public void showGlobe(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    private void saveUserLocation(double latitude, double longitude) {

        RequestParams params = new RequestParams();
        params.add("latitude", Double.toString(latitude));
        params.add("longitude", Double.toString(longitude));
        params.add("userId", Integer.toString(SessionManager.getIntFromSession(getApplicationContext(), "userId")));

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://" + ServerHelper.getIP() + "/near2u2/user/history", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObj) {

                try {
                    jsonObj.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void showHistory(View view) {

        Intent tIntent = new Intent(MapsActivity.this, HistoryActivity.class);
        startActivity(tIntent);

    }


}
