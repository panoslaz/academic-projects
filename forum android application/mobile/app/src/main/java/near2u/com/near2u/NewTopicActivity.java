package near2u.com.near2u;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import near2u.com.near2u.helpers.ServerHelper;

public class NewTopicActivity extends AppCompatActivity {

    Intent intent;
    TextView view;
    EditText titleET;
    EditText textET;
    ProgressDialog prgDialog;
    TextView errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_topic);

        // Get title Edit View control by ID
        titleET = (EditText)findViewById(R.id.topicTitle);
        // Get text Edit View control by ID
        textET = (EditText)findViewById(R.id.topicText);

        intent = getIntent();
        String title = intent.getStringExtra("title");
        // set title
        view = (TextView)findViewById(R.id.new_topic_category_text);
        view.setText(title);
        // Instantiate Progress Dialog object
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);
    }

    public void postTopic(View view) {

        // Get title value
        String title = titleET.getText().toString();


        // Get text value
        String text = textET.getText().toString();

        // Get forumId
        Integer forumId = intent.getIntExtra("forumId", 1);

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(title)) {
            titleET.setError("This field requied");
            focusView = titleET;
            cancel = true;
        }

        if (TextUtils.isEmpty(text)) {
            textET.setError("This field is required");
            focusView = textET;
            cancel = true;
        }

        if (cancel) {

            // form field with an error.
            focusView.requestFocus();
        } else {


            // *** Build the Http Request ***/
            // Instantiate Http Request Param Object
            RequestParams params = new RequestParams();
            params.put("title", title);
            params.put("text", text);
            params.put("forumId", forumId);
            params.put("userId", 1);
            // Invoke RESTful Web Service with Http parameters
            invokeWS(params);

        }
    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     * @throws JSONException
     * @throws UnsupportedEncodingException
     */
    public void invokeWS(RequestParams params) {
        // Show Progress Dialog
        prgDialog.show();

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://" + ServerHelper.getIP() + "/near2u2/topics", params, new AsyncHttpResponseHandler() {

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
//                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                        // Navigate to topics screen
                        navigateToTopicsPage();

                    }
                    // Else display error message
                    else {
                        errorMsg.setText(obj.getString("error_msg"));
                        navigateToTopicsPage();
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

    public void cancelTopic(View view) {
        navigateToTopicsPage();
    }

    public void navigateToTopicsPage() {
        Intent topicsPageIntent = new Intent(NewTopicActivity.this, TopicActivity.class);
        topicsPageIntent.putExtra("title", intent.getStringExtra("title"));
        topicsPageIntent.putExtra("forumId", intent.getIntExtra("forumId", 1));
        startActivity(topicsPageIntent);
    }

}
