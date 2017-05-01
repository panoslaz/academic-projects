package near2u.com.near2u;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import near2u.com.near2u.helpers.ServerHelper;

/**
 * Activity for registration view
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText unameET;
    private EditText passET;
    private EditText birthDateET;
    private EditText hobET;
    private EditText interET;
    EditText occupET;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        unameET = (EditText)findViewById(R.id.regu_user_name);
        passET = (EditText)findViewById(R.id.regu_password);
        birthDateET = (EditText)findViewById(R.id.regu_birth_date);
        hobET = (EditText)findViewById(R.id.regu_hobbies);
        interET = (EditText)findViewById(R.id.regu_interests);
        occupET = (EditText) findViewById(R.id.regu_occupation);

        intent = getIntent();

    }

    public void saveUser(View view) {
        String username = unameET.getText().toString();
        String password = passET.getText().toString();
        String birthDate = birthDateET.getText().toString();
        String hobbies = hobET.getText().toString();
        String interests = interET.getText().toString();
        String occupation = occupET.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(username)) {
            unameET.setError("This field requied");
            focusView = unameET;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            passET.setError("This field is required");
            focusView = passET;
            cancel = true;
        }

        if (cancel) {

            // form field with an error.
            focusView.requestFocus();
        } else {

            RequestParams params = new RequestParams();
            params.put("username", username);
            params.put("password", password);
            params.put("birthDate", birthDate);
            params.put("hobbies", hobbies);
            params.put("interests", interests);
            params.put("occupation", occupation);
            // Invoke RESTful Web Service with Http parameters
            invokeWS(params);
        }
    }
    public void invokeWS(RequestParams params) {

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://" + ServerHelper.getIP() + "/near2u2/user/register", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    String respBody = new String(responseBody);
                    // JSON Object
                    JSONObject obj = new JSONObject(new String(responseBody));
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getString("message") != null) {
                        // Navigate to posts screen
                        navigateToLoginPage();

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

    public void navigateToLoginPage() {
        Intent loginPageIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        loginPageIntent.putExtra("title", intent.getStringExtra("title"));
        loginPageIntent.putExtra("forumId", intent.getIntExtra("forumId", 1));
        loginPageIntent.putExtra("nextPage", intent.getStringExtra("afterRegister"));
        loginPageIntent.putExtra("topicForumId", intent.getIntExtra("topicForumId", 1));
        loginPageIntent.putExtra("topicForumDescription", intent.getStringExtra("topicForumDescription"));
        startActivity(loginPageIntent);
    }

}
