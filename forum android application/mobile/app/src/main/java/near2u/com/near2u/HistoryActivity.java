package near2u.com.near2u;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import near2u.com.near2u.entities.History;
import near2u.com.near2u.helpers.ServerHelper;
import near2u.com.near2u.helpers.SessionManager;

/**
 * Activity for the user history view.
 */
public class HistoryActivity extends AppCompatActivity {

    ListView historyListView;

    ArrayAdapter<History> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyListView = (ListView) findViewById(R.id.historyListView);
        new getHistory().execute();
    }

    private class getHistory extends AsyncTask<String, Void, History[]> {

        protected void onPostExecute(History[] history) {
            adapter  = new ArrayAdapter<History>(HistoryActivity.this, android.R.layout.simple_list_item_1, history);
            historyListView.setAdapter(adapter);

        }

        @Override
        protected History[] doInBackground(String... params) {
            SyncHttpClient client = new SyncHttpClient();
            final List<History> history = new ArrayList<History>();
            final int userId = SessionManager.getIntFromSession(getApplicationContext(), "userId");
            client.get("http://" + ServerHelper.getIP()+ "/near2u2/user/" + userId + "/history", new RequestParams(),new JsonHttpResponseHandler() {

                /**
                 * Get history messages of the user and create a list of history objects
                 * @param statusCode
                 * @param headers
                 * @param jsonObj
                 */
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObj) {

                    String userName = SessionManager.getFromSession(getApplicationContext(), "username");
                    try {
                        JSONArray historyArray = jsonObj.getJSONArray("history");
                        JSONObject o; int from;int to;String sender;String recipient;
                        for (int i = 0; i < historyArray.length(); i++) {
                            o = historyArray.getJSONObject(i);
                            from = o.getInt("FROM_USER_ID");
                            to = o.getInt("receiver_id");
                            History h= new History();
                            if(from == userId) {
                                h.setIsUserRecipient(false);
                                sender = "You";
                                recipient = o.getString("username");
                            } else {
                                h.setIsUserRecipient(true);
                                recipient = "you";
                                sender = o.getString("username");
                            }
                            h.setDate(o.getString("DATE_SENT"));
                            h.setSenderName(sender);
                            h.setMessage(o.getString("MESSAGE"));
                            h.setRecepientName(recipient);
                            history.add(h);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            return history.toArray(new History[history.size()]);
        }
    }
}
