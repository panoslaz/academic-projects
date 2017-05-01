package near2u.com.near2u;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import near2u.com.near2u.helpers.ServerHelper;
import near2u.com.near2u.helpers.SessionManager;

/**
 * Activity for the send message view.
 */
public class SendMessageActivity extends Activity {

	EditText messageET;
	ProgressDialog prgDialog;
	TextView errorMsg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_message);
		
		// Get message Edit View control by ID
        messageET = (EditText)findViewById(R.id.messageText);
        // Instantiate Progress Dialog object
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);
	}
	
	
	public void sendMessage(View view) throws UnsupportedEncodingException, JSONException {

		// Get message value
        String message = messageET.getText().toString();

        // *** Build the Http Request ***/
        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();
        params.put("message", message);
        params.put("userId", Integer.toString(SessionManager.getIntFromSession(getApplicationContext(), "userId")));
     // Invoke RESTful Web Service with Http parameters
        invokeWS(params);

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
         client.post("http://" + ServerHelper.getIP()+ "/near2u2/message", params, new AsyncHttpResponseHandler() {

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
                         errorMsg.setText(obj.getString("error_msg"));
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
	
	
	
	
}
