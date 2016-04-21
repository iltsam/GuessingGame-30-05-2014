package au.edu.jcu.it.guessinggame;

import java.util.ArrayList;
import java.util.Arrays;

public class BinaryCards {
	// Initializes all of the array for the 7 cards need for 1 - 100 in binary
	private ArrayList<Integer> binaryCard1 = new ArrayList<Integer>();
	private ArrayList<Integer> binaryCard2 = new ArrayList<Integer>();
	private ArrayList<Integer> binaryCard3 = new ArrayList<Integer>();
	private ArrayList<Integer> binaryCard4 = new ArrayList<Integer>();
	private ArrayList<Integer> binaryCard5 = new ArrayList<Integer>();
	private ArrayList<Integer> binaryCard6 = new ArrayList<Integer>();
	private ArrayList<Integer> binaryCard7 = new ArrayList<Integer>();
	public BinaryCards () {
		// creates a list that we can add to the binaryCards
		// b1 : binaryCard1
		Integer[] b1 = {1,3,5,7,9,11,21,23,25,27,29,31,33,35,37,39,41,43,45,47,49,51,53,55,57,59,61,63,65,67,69,71,73,75,77,79,81,83,85,87,89,91,93,95,97,99};
		Integer[] b2 = {2,3,6,7,10,11,14,15,18,19,22,23,26,27,30,31,34,35,38,39,42,43,47,48,51,52,55,56,59,60,63,64,67,68,71,72,75,76,79,80,83,84,87,88,91,92,95,96,99,100};
		Integer[] b3 = {4,5,6,7,12,13,14,15,20,21,22,23,28,29,30,31,36,37,38,39,44,45,46,47,52,53,54,55,60,61,62,63,68,69,70,71,76,77,78,79,84,85,86,87,92,93,94,95,100};
		Integer[] b4 = {8,9,10,11,12,13,14,15,24,25,26,27,28,29,30,31,40,41,42,43,44,45,46,47,56,57,58,59,60,61,62,63,71,72,73,74,75,76,77,78,79,88,89,90,91,92,93,94,95,};
		Integer[] b5 = {16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95};
		Integer[] b6 = {32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,96,97,98,99};
		Integer[] b7 = {64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100};
		
		// adds all the binary lists to the binaryCards
		binaryCard1.addAll(Arrays.asList(b1));
		binaryCard2.addAll(Arrays.asList(b2));
		binaryCard3.addAll(Arrays.asList(b3));
		binaryCard4.addAll(Arrays.asList(b4));
		binaryCard5.addAll(Arrays.asList(b5));
		binaryCard6.addAll(Arrays.asList(b6));
		binaryCard7.addAll(Arrays.asList(b7));
		System.out.println("Cards Created : working");
	}
	
	// Set the getters for each card
	public ArrayList<Integer> getCard1 () {
		return binaryCard1;
	}
	public ArrayList<Integer> getCard2 () {
		return binaryCard2;
	}
	public ArrayList<Integer> getCard3 () {
		return binaryCard3;
	}
	public ArrayList<Integer> getCard4 () {
		return binaryCard4;
	}
	public ArrayList<Integer> getCard5 () {
		return binaryCard5;
	}
	public ArrayList<Integer> getCard6 () {
		return binaryCard6;
	}
	public ArrayList<Integer> getCard7 () {
		return binaryCard7;
	}
	
	// Returns the first number in each card
	// Used for computing the computers guess
	public int getFirstNumber (int cardNumber) {
		if (cardNumber == 1) {
			return binaryCard1.get(0);
		} else if (cardNumber == 2) {
			return binaryCard2.get(0);
		} else if (cardNumber== 3) {
			return binaryCard3.get(0);
		} else if (cardNumber == 4) {
			return binaryCard4.get(0);
		} else if (cardNumber == 5) {
			return binaryCard5.get(0);
		} else if (cardNumber == 6) {
			return binaryCard6.get(0);
		} else if (cardNumber == 7) {
			return binaryCard7.get(0);
		}
		return 0;
	}
}
