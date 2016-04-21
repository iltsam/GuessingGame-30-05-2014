package au.edu.jcu.it.guessinggame;

public class Player {
	String name;
	int score;
	public Player (String name, int score) {
		this.name = name;
		this.score = score;
	}
	
	@Override
	public String toString() {
		return String.format("%s : Score %d", name, score);
	}
}
