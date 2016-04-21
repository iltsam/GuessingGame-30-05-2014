package au.edu.jcu.it.guessinggame;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class PlayActivity extends ActionBarActivity {
	// Initializes variables
	private PlayersOpenHelper playersOpenHelper;
	int secretNumber;
	ArrayList<Integer> range = new ArrayList<Integer>();
	BinaryCards binCards = new BinaryCards();
	int AIGuess;
	int currentCard = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		playersOpenHelper = new PlayersOpenHelper(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		// On create sets the range to default values 
		range.add(1);
		range.add(10);
		generateSecretNumber(); // Generates the secret number for use in the GTN game
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);
		return true;
	}

	
	public void buttonHandler (View view) {
		// Handles the play buttons via grabbing the text on
		// the clicked using view button to use in if statements
		Button button = (Button) view;
		String buttText = (String) button.getText();
		
		if(buttText.equals("Settings")) { // Goes to settings
			setContentView(R.layout.settings);
		} else if (buttText.split(" ")[0].equals("Guess")) { // goes to GTN
			System.out.println("Guess the Number clicked");
			setContentView(R.layout.play_gtn);
			gtnController();
		} else if (buttText.split(" ")[0].equals("Choose")) { // goes to CAN
			System.out.println("Choose a Number clicked");
			setContentView(R.layout.play_can);
			canController();
		} else if (buttText.split(" ")[0].equals("Free")) { // goes to free play
			System.out.println("Free Play clicked");
			setContentView(R.layout.play_fp);
			fpController();
		}
	}
	
	public void guessingController (View view) {
		// Controls the final guess at the end of GTN
		
		SQLiteDatabase db = playersOpenHelper.getReadableDatabase();// Opens the database 
		TextView response = (TextView)findViewById(R.id.textView2);
		EditText guess = (EditText)findViewById(R.id.guessEdit); // Gets the players guess
		EditText name = (EditText)findViewById(R.id.nameEditText); // Gets the players name
		int currentScore = 0; // Initializes the current score variable to update the score properly
		
		// Some logging for troubleshooting etc
		System.out.println(secretNumber + " " + guess.getText());
		System.out.println(guess.getText().toString().equals(Integer.toString(secretNumber)));
		// Queries the db for everything in Players Database
		Cursor cursor = db.query(true, "Players", null, null, null, null, null,
		null, null);
		while(cursor.moveToNext()){ 		// Iterates over every value returned from the query
			String n = cursor.getString(0);
			int s = cursor.getInt(1);
			if(n.equals(name.getText().toString())) {
				currentScore = s; // if the players name is equal to one in the database it sets the current score
			}
		}
		// checks if guess is correct
		if (guess.getText().toString().equals(Integer.toString(secretNumber))){
			// sets the response textview to a correct message
			response.setText(String.format("Correct!\nYou have earned %d points, based on the maximum value chosen", range.get(1))); 
			System.out.println(name.toString()); // logs the name
			db.execSQL(String.format("INSERT OR IGNORE INTO Players(name) VALUES ('%s');", name.getText().toString())); // Used to make sure the name exists in the database
			db.execSQL(String.format("UPDATE Players SET score = '%d' WHERE name = '%s';", (currentScore + range.get(1)), name.getText().toString())); // updates the previously inserted name
		} else {
			// if incorrect set the response to incorrect
			response.setText("Incorrect!");
		}
		db.close();
	}
	
	// Used change the content view for the players guess
	public void guessSetup(View view) {
		setContentView(R.layout.play_guess);
	}
	
	// settings controller
	public void setSettings (View view) {
		// gets all the radio buttons 
		RadioButton butLow = (RadioButton)findViewById(R.id.low);
		RadioButton butMed = (RadioButton)findViewById(R.id.med);
		RadioButton butHigh = (RadioButton)findViewById(R.id.high);
		RadioButton butCustom = (RadioButton)findViewById(R.id.custom);
		// gets the min and max text box's
		EditText minRange = (EditText)findViewById(R.id.minEdit);
		EditText maxRange = (EditText)findViewById(R.id.maxEdit);
		if(butLow.isChecked()) { 
			// if the low button is checked set the range to 1-10
			System.out.println("low");
				range.set(0,  1);
				range.set(1, 10);
		} else if (butMed.isChecked()) {
			// if the med button is checked set the range to 1-20
			System.out.println("med");
				range.set(0, 1);
				range.set(1, 20);
		} else if (butHigh.isChecked()) {
			// if the high button is checked set the range to 1-50
			System.out.println("high");
				range.set(0, 1);
				range.set(1, 50);
		} else if (butCustom.isChecked()) {
			// if the custom button is checked
			// Check the value to see if in correct range of 1-20
			// then set the range values accordingly
			if(Integer.parseInt(minRange.getText().toString()) >= 1) { 
				if(Integer.parseInt(minRange.getText().toString()) < 20) {
					range.set(0, Integer.parseInt(minRange.getText().toString()));
				} else if (range.set(0, Integer.parseInt(minRange.getText().toString())) < 1) {
					range.set(0, 1);
				} else {
					range.set(0, 20);
				}
			}
			// Check the value to see if in correct range of minRange - 100
			// then set the range values accordingly
			if(Integer.parseInt(maxRange.getText().toString()) > 100) {
				range.set(1, 100);
			} else if (Integer.parseInt(maxRange.getText().toString()) <= Integer.parseInt(minRange.getText().toString())) {
				range.set(1, (Integer.parseInt(minRange.getText().toString()) + Integer.parseInt(maxRange.getText().toString())));
			} else {
				range.set(1, Integer.parseInt(maxRange.getText().toString()));
			}
		}
		generateSecretNumber(); // Updates the secret number when settings change.
		System.out.println("Range : " + range); // Logs the range
		setContentView(R.layout.activity_play); // Changes to content to play
	}
	
	// Controller for the GTN game
	public void gtnController () {
		// Gets all the appropriate views on the layout
		TextView cardText = (TextView)findViewById(R.id.textView2);
		TextView pcResponse = (TextView)findViewById(R.id.textView3);
		Boolean cardsLeft = true; // boolean for checking whether there are any cards left to display
		pcResponse.setText("No!"); // defaulting the pc response to No
		ArrayList<Integer> card = new ArrayList<Integer>();
		// If / Else block for adding each card to the layout
		if (currentCard == 1) { // if the current card is equal to 1
			System.out.println("Card 1");
			card.addAll(binCards.getCard1()); 	// Add the binaryCard1 ArrayList to the card arraylist to 
												// to add to the layout
		} else if (currentCard == 2) {
			System.out.println("Card 2");
			card.addAll(binCards.getCard2());
		} else if (currentCard == 3) {
			System.out.println("Card 3");
			card.addAll(binCards.getCard3());
		} else if (currentCard == 4) {
			System.out.println("Card 4");
			card.addAll(binCards.getCard4());
		} else if (currentCard == 5 & range.get(1) > 16) {	// only displays if the range is larger than 16
			System.out.println("Card 5");
			card.addAll(binCards.getCard5());
		} else if (currentCard == 6 & range.get(1) > 32) { // only displays if the range is larger than 32
			System.out.println("Card 6");
			card.addAll(binCards.getCard6());
		} else if (currentCard == 7 & range.get(1) > 64) { // only displays if the range is larger than 64
			System.out.println("Card 7");
			card.addAll(binCards.getCard7());
		} else {
			cardText.setText("No cards left!"); // Displays if no cards are left
			cardsLeft = false;
		}
		
		if (cardsLeft) {
			// iterate over all the values in the card array
			// if the the card contains the secret number, set response text to yes
			for (int i = 0; i < card.size(); ++i) {
				if (card.get(i).equals(secretNumber)) {
					pcResponse.setText("Yes!");
				}
				// checks to see if the card has a greater value than the range
				// then removes it
				if (card.get(i) >= (range.get(1) + 1)) {
					System.out.println("Card Removed : " + card.get(i));
					card.remove(i);
					--i; 	// --i so it can iterate over all values
				} else if (card.get(i) < range.get(0)) {
					// if card tries to remove the necessary binary digits
					// to make the game work - do nothing
					System.out.println("Card # - " + card.get(i));
					if(card.get(i) != 1){
						// do nothing
					} else if(card.get(i) != 2){
						// do nothing
					} else if(card.get(i) != 4){
						// do nothing
					} else if(card.get(i) != 8){
						// do nothing
					} else if(card.get(i) != 16){
						// do nothing
					} else {
						// if the card is not necessary - remove it
						System.out.println("Card Removed : " + card.get(i));
						card.remove(i);
						--i;
					}
				}
			}
			// sets the card up once all the computing has been done
			cardText.setText(card.toString());
		}
	}
	
	// ++currentCard to go to next card
	public void gtnButtonCont (View view) {
		++currentCard;
		gtnController();
	}
	
	// controller for the choose a number game
	public void canController () {
		// Gets all the appropriate views on the layout
		TextView cardText = (TextView)findViewById(R.id.textView2);
		Boolean cardsLeft = true;
		// See gtnController for comments
		ArrayList<Integer> card = new ArrayList<Integer>();
		if (currentCard == 1) {
			System.out.println("Card 1");
			card.addAll(binCards.getCard1());
		} else if (currentCard == 2) {
			System.out.println("Card 2");
			card.addAll(binCards.getCard2());
		} else if (currentCard == 3) {
			System.out.println("Card 3");
			card.addAll(binCards.getCard3());
		} else if (currentCard == 4) {
			System.out.println("Card 4");
			card.addAll(binCards.getCard4());
		} else if (currentCard == 5 & range.get(1) > 16) {
			System.out.println("Card 5");
			card.addAll(binCards.getCard5());
		} else if (currentCard == 6 & range.get(1) > 32) {
			System.out.println("Card 6");
			card.addAll(binCards.getCard6());
		} else if (currentCard == 7 & range.get(1) > 64) {
			System.out.println("Card 7");
			card.addAll(binCards.getCard7());
		} else {
			cardText.setText("No cards left!");
			cardsLeft = false;
		}
		if (cardsLeft) { // see gtnController
			for (int i = 0; i < card.size(); ++i) {
				if (card.get(i) >= (range.get(1) + 1)) {
					System.out.println("Card Removed : " + card.get(i));
					card.remove(i);
					--i;
				} else if (card.get(i) <= (range.get(0) + 1)) {
					System.out.println("Card # - " + card.get(i));
					if(card.get(i) != 1){
						
					} else if(card.get(i) != 2){
						// do nothing
					} else if(card.get(i) != 4){
						// do nothing
					} else if(card.get(i) != 8){
						// do nothing
					} else if(card.get(i) != 16){
						// do nothing
					} else {
						System.out.println("Card Removed : " + card.get(i));
						card.remove(i);
						--i;
					}
				}
			}
			cardText.setText(card.toString());
		}
	}
	
	// Controls all the buttons on the choose a number layout
	public void canButtonController (View view) {
		// Gets the button that was pressed
		Button butt = (Button) view;
		// Gets the button text then splits it and then compares the 
		// first word to work out what to do
		if(butt.getText().toString().split(" ")[0].equals("Next")){
			++currentCard; // ++ the current card to go to next card
			canController();
		} else if(butt.getText().toString().split(" ")[0].equals("Previous")){
			--currentCard; // -- the current card to go to the previous card
			canController();
		} else if(butt.getText().toString().equals("Yes")){
			// adds the current cards binary number to the computers guess
			AIGuess += binCards.getFirstNumber(currentCard);
			++currentCard;
			canController(); 
			System.out.println(AIGuess);
		} else if(butt.getText().toString().split(" ")[0].equals("See")){
			setContentView(R.layout.play_aiguess); // sets the current layout to guess, then runs the ai guess function
			calcAIguess();
		} else if(butt.getText().toString().split(" ")[0].equals("Return")) {
			finish();
		}
	}
	
	// Calculates and displays the AI's guess.
	public void calcAIguess () {
		// grabs the text view
		TextView guess = (TextView)findViewById(R.id.guessTextView);
		// creates a random number from 1 - 10 so it guesses correct %80 of the time
		int ranNum = (int)((Math.random() * (10 - 1)) + 1);
		if (ranNum > 8) {
			// if the random number is above 8 - Computer guesses incorrectly
			int ranGuess = (int) ((Math.random() * (range.get(1) - range.get(0)) + range.get(0)));
			guess.setText(String.format("The computer guessed %d.\nIs this correct?", ranGuess));
		} else {
			// if the number is below 8 - computer guesses correctly
			guess.setText(String.format("The computer guessed %d.\nIs this correct?", AIGuess));
		}
	}
	
	public void ifCorrect (View view) {
		Button butt = (Button) view;
		TextView guess = (TextView)findViewById(R.id.guessTextView);
		SQLiteDatabase db = playersOpenHelper.getReadableDatabase();
		int currentScore = 0;
		if(butt.getText().toString().equals("Yes")) {
			Cursor cursor = db.query(true, "Players", null, null, null, null, null,
					null, null);
			while(cursor.moveToNext()){ 		// Iterates over every value returned from the query
				String n = cursor.getString(0);
				int s = cursor.getInt(1);
				if(n.equals("Computer")) {
					currentScore = s; // if the players name is equal to one in the database it sets the current score
				}
			}
			guess.setText(String.format("The Computer has guessed your number! %d points have been added to its score!\nIt always knows...\n(̲̅ ͡° ͜ʖ ͡°̲̅)̲̅", range.get(1)));
			db.execSQL("INSERT OR IGNORE INTO Players(name) VALUES ('Computer');"); // Used to make sure the name exists in the database
			db.execSQL(String.format("UPDATE Players SET score = '%d' WHERE name = '%s';", (currentScore + range.get(1)), "Computer")); // updates the previously inserted name
			db.close();
		} else {
			guess.setText("The computer could not guess your number this time!\nTry your luck again!");
		}
		
	}
	
	
	public void generateSecretNumber () {
		// generates a random secret number between the ranges for GTN game
		secretNumber = (int) ((Math.random() * (range.get(1) - range.get(0))) + range.get(0));
	}
	
	// See gtnController for comments
	public void fpController () {
		// Handles processing for Free Play Game mode
		TextView cardText = (TextView)findViewById(R.id.textView2);
		Boolean cardsLeft = true;
		ArrayList<Integer> card = new ArrayList<Integer>();
		if (currentCard == 1) {
			System.out.println("Card 1");
			card.addAll(binCards.getCard1());
		} else if (currentCard == 2) {
			System.out.println("Card 2");
			card.addAll(binCards.getCard2());
		} else if (currentCard == 3) {
			System.out.println("Card 3");
			card.addAll(binCards.getCard3());
		} else if (currentCard == 4) {
			System.out.println("Card 4");
			card.addAll(binCards.getCard4());
		} else if (currentCard == 5 & range.get(1) > 16) {
			System.out.println("Card 5");
			card.addAll(binCards.getCard5());
		} else if (currentCard == 6 & range.get(1) > 32) {
			System.out.println("Card 6");
			card.addAll(binCards.getCard6());
		} else if (currentCard == 7 & range.get(1) > 64) {
			System.out.println("Card 7");
			card.addAll(binCards.getCard7());
		} else {
			cardText.setText("No cards left!");
			cardsLeft = false;
		}
		if (cardsLeft) {
			for (int i = 0; i < card.size(); ++i) {
				if (card.get(i) >= (range.get(1) + 1)) {
					System.out.println("Card Removed : " + card.get(i));
					card.remove(i);
					--i;
				} else if (card.get(i) <= (range.get(0) + 1)) {
					System.out.println("Card # - " + card.get(i));
					if(card.get(i) != 1){
						
					}else if(card.get(i) != 2){
						// do nothing
					} else if(card.get(i) != 4){
						// do nothing
					} else if(card.get(i) != 8){
						// do nothing
					} else if(card.get(i) != 16){
						// do nothing
					} else {
						System.out.println("Card Removed : " + card.get(i));
						card.remove(i);
						--i;
					}
				}
			}
			cardText.setText(card.toString());
		}
	}
	
	public void fpButtonController (View view) {
		// controller for the  free play game buttons
		Button butt = (Button) view;
		if(butt.getText().toString().split(" ")[0].equals("Next")){
			++currentCard; // Displays the next card
			fpController();
		} else if(butt.getText().toString().split(" ")[0].equals("Previous")){
			--currentCard; // Displays the previous card
			fpController();
		}
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
