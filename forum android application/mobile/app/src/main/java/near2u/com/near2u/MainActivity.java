package near2u.com.near2u;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import near2u.com.near2u.helpers.SessionManager;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void goToForum(View view) {
		 Intent intent = new Intent(this, ForumActivity.class);
		 startActivity(intent);
	}
	
	public void goToNear2u(View view) {

		String username = SessionManager.getFromSession(getApplicationContext(), "username");
		if(username.length() == 0) {
			Intent loginIntent = new Intent(this, LoginActivity.class);
			loginIntent.putExtra("nextPage", "maps");
			startActivity(loginIntent);
		} else {
			Intent mapIntent = new Intent(this, MapsActivity.class);
			startActivity(mapIntent);
		}
	}
	
}
