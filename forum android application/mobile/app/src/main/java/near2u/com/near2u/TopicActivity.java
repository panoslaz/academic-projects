package near2u.com.near2u;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import near2u.com.near2u.entities.Topic;
import near2u.com.near2u.helpers.ServerHelper;
import near2u.com.near2u.helpers.SessionManager;

public class TopicActivity extends AppCompatActivity  {

    TextView view;
    ListView topicListView;
    ArrayAdapter<Topic> adapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);

        intent = getIntent();
        String title = intent.getStringExtra("title");
        // set title
        view = (TextView)findViewById(R.id.topics_text);
        view.setText(title);

        topicListView = (ListView) findViewById(R.id.topicsListView);
        new getTopics().execute();
    }

    private class getTopics extends AsyncTask<String, Void, Topic[]> {

        protected void onPostExecute(Topic[] topics) {
            adapter  = new ArrayAdapter<Topic>(TopicActivity.this, android.R.layout.simple_list_item_1, topics);
            topicListView.setAdapter(adapter);

            topicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //get the selected topic
                    Topic t = (Topic) topicListView.getAdapter().getItem(position);
                    //load the topics page
                    Intent tIntent = new Intent(TopicActivity.this, PostActivity.class);
                    tIntent.putExtra("topicTitle", t.getTitle());
                    tIntent.putExtra("topicId", t.getId());
                    tIntent.putExtra("topicForumId", intent.getIntExtra("forumId", 1));
                    tIntent.putExtra("topicForumDescription", intent.getStringExtra("title"));
                    startActivity(tIntent);

//                    Toast.makeText(getBaseContext(), String.valueOf(f.getId()), Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        protected Topic[] doInBackground(String... params) {
            SyncHttpClient client = new SyncHttpClient();
            final List<Topic> topics = new ArrayList<Topic>();
            Integer forumId = intent.getIntExtra("forumId", 1);
            client.get("http://" + ServerHelper.getIP()+ "/near2u2/topics/forumId/" + forumId, new RequestParams(),new JsonHttpResponseHandler() {


                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObj) {

                    try {
                        JSONArray forumsArray = jsonObj.getJSONArray("topics");
                        JSONObject o;
                        for (int i = 0; i < forumsArray.length(); i++) {
                            o = forumsArray.getJSONObject(i);
                            topics.add(new Topic(o.getInt("id"), o.getString("title")));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            return topics.toArray(new Topic[topics.size()]);
        }
    }

    public void addNewTopic(View view) {

        String username = SessionManager.getFromSession(getApplicationContext(), "username");
        if(username.length() == 0) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.putExtra("title", intent.getStringExtra("title"));
            loginIntent.putExtra("forumId", intent.getIntExtra("forumId", 1));
            loginIntent.putExtra("nextPage", "newTopic");
            startActivity(loginIntent);
        } else {
            Intent newTopicIntent = new Intent(this, NewTopicActivity.class);
            newTopicIntent.putExtra("title", intent.getStringExtra("title"));
            newTopicIntent.putExtra("forumId", intent.getIntExtra("forumId", 1));
            startActivity(newTopicIntent);
        }
    }

    public void backToForums(View view) {

        Intent forumIntent = new Intent(this, ForumActivity.class);
        startActivity(forumIntent);
    }

}
