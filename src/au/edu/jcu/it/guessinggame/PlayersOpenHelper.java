package au.edu.jcu.it.guessinggame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlayersOpenHelper extends SQLiteOpenHelper {

	public PlayersOpenHelper(Context context) {
		// Creates a database called HighscoresData
		super(context, "HighscoresDatas", null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Sets up the database when called.
		setup(db);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		setup(db);
		
	}
	
	// Sets up the tables.
	private void setup(SQLiteDatabase database) {
		database.execSQL("DROP TABLE IF EXISTS Players;"); // Drops the table if it already exists
		database.execSQL("CREATE TABLE Players(name TEXT PRIMARY KEY UNIQUE, score INT);"); // Creates the table with 2 columns, name and score
		// Inserts default values for the players to compete with
		database.execSQL("INSERT INTO Players(name, score) VALUES ('Sam', '50');");
		database.execSQL("INSERT INTO Players(name, score) VALUES ('Josh', '40');");
		database.execSQL("INSERT INTO Players(name, score) VALUES ('Moe', '30');");
	}
}
