package au.edu.jcu.it.guessinggame;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Instructions extends ActionBarActivity  {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instructions);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.instructions, menu);
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
	
	public void instructionsButtonPressed(View view) {
		// Controller for the instructions buttons
		Button button = (Button) view;
		String text = (String)button.getText(); // splits the text to be used for navigation
		// compares the first word in the text array
		// to work out what instructions to show
		if (text.split(" ")[0].equals("Guess")) { 
			setContentView(R.layout.instructions_gtn);
		} else if (text.split(" ")[0].equals("Choose")) {
			setContentView(R.layout.instructions_can);
		} else {
			setContentView(R.layout.instructions_fp);
		}
	}
	
	public void returnToMain(View view) {
		// finishes the current activity returning it to main.
		finish();
	}
}
