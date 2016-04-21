package au.edu.jcu.it.guessinggame;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HighscoresActivity extends ActionBarActivity {
	// Initializes players constructor and the players database
	private ArrayAdapter<Player> players;
	private PlayersOpenHelper playersOpenHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_highscores);
		
		playersOpenHelper = new PlayersOpenHelper(this);
		// Opens the database
		SQLiteDatabase db = playersOpenHelper.getReadableDatabase();
		
		// Creates the controller for the listView
		players = new ArrayAdapter<Player>(this,
				R.layout.custom_textview);
		ListView highScoresList = (ListView) findViewById(R.id.listView1);
		highScoresList.setAdapter(players); // Sets the adapter to add players into the highscores list
		// Retrieves all the records from the database in DESCENDING ORDER of score
		Cursor cursor = db.query(true, "Players", null, null, null, null, null,
				"score DESC", null);
		// Iterates over all the results and places them in the highscores list
		while(cursor.moveToNext()) {
			String name = cursor.getString(0);
			int score = cursor.getInt(1);
			System.out.println(String.format("Name = %s \n Score = %d", name, score));
			players.add(new Player(name, score));
		}
		db.close();
	}
	
	public void returnHome (View view) {
		// For the return home button, finishes the current activity
		// sending it back to mainActivity
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
 		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.highscores, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
