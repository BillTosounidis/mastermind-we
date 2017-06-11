package we.software.mastermind;

import java.util.ArrayList;

public class Game {

	private Player p1;
	private Player p2; // Player or Computer (CodeMaker)

	private int currentRound;
	private int currentGame;
	private int gameScore;
	private int maxRounds = 10;
	private ArrayList<Integer> result;
	private ArrayList<Integer> guess;
	private ArrayList<Integer> code;

	public Game(int difficulty) {
		p1 = new Player();
		p2 = new Computer(difficulty);
		p1.initializeGuessArray();
	}

	public Game() { // PvP / PvE selection
		this.currentRound = 1;
		this.gameScore = 0;
		this.currentGame = 0;

	}

	public int getCurrentRound() {
		return currentRound;
	}

	public void addToResult(int pos, int val) {
		result.set(pos, val);
	}

	public ArrayList<Integer> getResult() {
		return result;
	}

	public void initializeArrays() {

		p1.initializeCodeToBreakArray();
		p2.initializeGuessArray();

		p2.initializeCodeToBreakArray();
		p1.initializeGuessArray();
		result = new ArrayList<Integer>();
		for (int i = 0; i < 4; i++) {
			result.add(0);
		}

	}

	public int getCurrentGame() {
		return currentGame;
	}

	public void addCurrentGame() {
		this.currentGame++;
	}

	public void clearGame() {
		p1.restoreGuessToDefault();
		p2.restoreGuessToDefault();
		p1.restoreCodeToDefault();
		p2.restoreCodeToDefault();
		gameScore = 0;
		currentRound = 1;
	}

	public Player getP1() {
		return p1;
	}

	public Player getP2() {
		return p2;
	}

	public void setP2(Player p2) {
		this.p2 = p2;
	}

	public void setP1(Player p1) {
		this.p1 = p1;
	}

	public void addCurrentRound() {
		currentRound++;
	}

	// Checks right guesses
	public boolean checkIfWin() {

		for (int aPeg : result)
			if (aPeg != 2)
				return false;
		return true;
	}

	// Calculates the score of current round
	public void setRoundScore(ArrayList<Integer> result) {

		for (int resPeg : result) {
			if (resPeg == 2) {
				gameScore = gameScore + 50;
			} else if (resPeg == 1) {
				gameScore = gameScore + 40;
			}
		}
	}

	// Returns current score
	public int getGameScore() {
		if (checkIfWin()) {
			return gameScore + (3000 - (currentRound * 200));
		} else {
			return gameScore;
		}
	}

	// Returns result table
	public ArrayList<Integer> checkGuess() {
		// Player1 table (p1)
		if (!p1.isCodeMaker()) {
			guess = p1.getGuess();
			System.out.println(guess);

			// Player2 or AI table (p2)
			code = p2.getCode();
			System.out.println(code);
		} else {
			guess = p2.getGuess();
			System.out.println(guess);

			// Player2 or AI table (p2)
			code = p1.getCode();
			System.out.println(code);
		}
		// Initializing final table (p3)
		result = new ArrayList<Integer>();

		ArrayList<Integer> ex = new ArrayList<Integer>();

		for (int i = 0; i < 4; i++) {
			if (guess.get(i).equals(code.get(i))) {
				result.add(2);
				ex.add(i);
			}
		}
		for (int i = 0; i < 4; i++) {
			if (!ex.isEmpty()) {
				if (ex.contains(i))
					continue;
			}
			for (int j = 0; j < 4; j++) {
				if (guess.get(i).equals(code.get(j))) {
					result.add(1);
					break;
				}
			}
		}

		while (result.size() < 4) {
			result.add(0);
		}
		setRoundScore(result);
		return result;

	}

	public void setGuess(int[] guess) {
		p1.setGuess(guess);
	}
}
