package near2u.com.near2u;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import near2u.com.near2u.entities.Forum;
import near2u.com.near2u.helpers.ServerHelper;

public class ForumActivity extends Activity {

    ArrayAdapter<Forum> adapter;
    ListView forumListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_activity);

        forumListView = (ListView) findViewById(R.id.forumlistView);

        new getForums().execute();
    }
    private class getForums extends AsyncTask<String, Void, Forum[]> {

        protected void onPostExecute(Forum[] forums) {
            adapter  = new ArrayAdapter<Forum>(ForumActivity.this, android.R.layout.simple_list_item_1, forums);
            forumListView.setAdapter(adapter);

            forumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //get the selected forum
                    Forum f = (Forum) forumListView.getAdapter().getItem(position);
                    //load the topics page
                    Intent intent = new Intent(ForumActivity.this, TopicActivity.class);
                    intent.putExtra("title", f.getDescription());
                    intent.putExtra("forumId", f.getId());
                    startActivity(intent);

//                    Toast.makeText(getBaseContext(), String.valueOf(f.getId()), Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        protected Forum[] doInBackground(String... params) {
            SyncHttpClient client = new SyncHttpClient();
            final List<Forum> forums = new ArrayList<Forum>();

            client.get("http://" + ServerHelper.getIP()+ "/near2u2/forums", new RequestParams(),new JsonHttpResponseHandler() {


                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObj) {

                    try {
                        JSONArray forumsArray = jsonObj.getJSONArray("forums");
                        JSONObject o;
                        for (int i = 0; i < forumsArray.length(); i++) {
                            o = forumsArray.getJSONObject(i);
                            forums.add(new Forum(o.getInt("id"), o.getString("description")));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            return forums.toArray(new Forum[forums.size()]);
        }
    }
}
