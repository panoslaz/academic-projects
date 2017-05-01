package near2u.com.near2u;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import near2u.com.near2u.entities.Post;
import near2u.com.near2u.helpers.ServerHelper;
import near2u.com.near2u.helpers.SessionManager;

/**
 * Activity for the post view
 */
public class PostActivity extends AppCompatActivity {

    TextView view;
    ListView postListView;
    ArrayAdapter<Post> adapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        intent = getIntent();
        String title = intent.getStringExtra("topicTitle");
        // set title
        view = (TextView)findViewById(R.id.posts_text);
        view.setText(title);

        postListView = (ListView) findViewById(R.id.postsListView);
        new getPosts().execute();
    }

    private class getPosts extends AsyncTask<String, Void, Post[]> {

        protected void onPostExecute(Post[] posts) {
            adapter  = new ArrayAdapter<Post>(PostActivity.this, android.R.layout.simple_list_item_1, posts);
            postListView.setAdapter(adapter);
        }

        /**
         * get all posts for topic and create a list of Post objects
         * @param params
         * @return
         */
        @Override
        protected Post[] doInBackground(String... params) {
            SyncHttpClient client = new SyncHttpClient();
            final List<Post> posts = new ArrayList<Post>();
            Integer topicId = intent.getIntExtra("topicId", 1);
            client.get("http://" + ServerHelper.getIP()+ "/near2u2/posts/topicId/" + topicId, new RequestParams(),new JsonHttpResponseHandler() {


                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObj) {

                    try {
                        JSONArray forumsArray = jsonObj.getJSONArray("posts");
                        JSONObject o;
                        for (int i = 0; i < forumsArray.length(); i++) {
                            o = forumsArray.getJSONObject(i);
                            posts.add(new Post(o.getInt("id"), o.getString("text")));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            return posts.toArray(new Post[posts.size()]);
        }
    }

    public void backToTopics(View view) {

        Intent topicsIntent = new Intent(PostActivity.this, TopicActivity.class);
        topicsIntent.putExtra("title", intent.getStringExtra("topicForumDescription"));
        topicsIntent.putExtra("forumId", intent.getIntExtra("topicForumId", 1));
        startActivity(topicsIntent);

    }

    /**
     * if user is not logged in, show the login view, else create a new post
     * @param view
     */
    public void addNewPost(View view) {

        String username = SessionManager.getFromSession(getApplicationContext(), "username");
        if(username.length() == 0) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.putExtra("topicTitle", intent.getStringExtra("topicTitle"));
            loginIntent.putExtra("topicId", intent.getIntExtra("topicId", 1));
            loginIntent.putExtra("topicForumId", intent.getIntExtra("topicForumId", 1));
            loginIntent.putExtra("topicForumDescription", intent.getStringExtra("topicForumDescription"));
            loginIntent.putExtra("nextPage", "newPost");
            startActivity(loginIntent);
        } else {
            Intent categoryIntent = new Intent(this, NewPostActivity.class);
            categoryIntent.putExtra("topicTitle", intent.getStringExtra("topicTitle"));
            categoryIntent.putExtra("topicId", intent.getIntExtra("topicId", 1));
            categoryIntent.putExtra("topicForumId", intent.getIntExtra("topicForumId", 1));
            categoryIntent.putExtra("topicForumDescription", intent.getStringExtra("topicForumDescription"));

            startActivity(categoryIntent);
        }
    }
}
