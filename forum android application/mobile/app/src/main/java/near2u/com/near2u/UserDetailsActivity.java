package near2u.com.near2u;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import cz.msebera.android.httpclient.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import near2u.com.near2u.entities.UserDetails;
import near2u.com.near2u.helpers.ServerHelper;

public class UserDetailsActivity extends Activity {

    Intent intent;
    private TextView userNameView;
    private TextView userOccupationView;
    private TextView userHobbiesView;
    private TextView userInterestsView;
    private TextView userBirthDateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        intent = getIntent();

        userNameView = (TextView)findViewById(R.id.ud_user_name);
        userOccupationView = (TextView)findViewById(R.id.ud_occupation);
        userHobbiesView = (TextView)findViewById(R.id.ud_hobbies);
        userInterestsView = (TextView)findViewById(R.id.ud_interests);
        userBirthDateView = (TextView)findViewById(R.id.ud_birth_date);

        loadUserDetails();
    }

    private void loadUserDetails() {
        AsyncHttpClient client = new AsyncHttpClient();
        final List<UserDetails> userDetails = new ArrayList<UserDetails>();

        client.get("http://" + ServerHelper.getIP()+ "/near2u2/user/details/" + intent.getStringExtra("username"), new RequestParams(),new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObj) {
                try {
                 JSONObject o = jsonObj.getJSONObject("details");

                 userNameView.setText(o.getString("username"));
                 userOccupationView.setText(o.getString("occupation"));
                 userHobbiesView.setText(o.getString("hobbies"));
                 userInterestsView.setText(o.getString("interests"));
                 userBirthDateView.setText(o.getString("birth_date"));

             } catch (JSONException e) {
                 e.printStackTrace();
             }
            }
        });

    }

}
