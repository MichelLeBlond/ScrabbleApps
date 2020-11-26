import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import zen.scrabbledict.data.BasicBoard;
import zen.scrabbledict.data.Dictionary;
import zen.scrabbledict.data.DictionaryListener;
import zen.scrabbledict.data.EnhancedBoard;
import zen.scrabbledict.data.Tiles;
import zen.scrabbledict.data.WordDirection;
import zen.scrabbledict.data.WordMatch;

public class CopyOfValidate extends HttpServlet implements DictionaryListener {
	private static final long serialVersionUID = 1L;
	// 20:29
	private EnhancedBoard board;
	private Dictionary dictionary = new Dictionary(this);;
	private String curDir;
	private String rack;
	private int x;
	private int y;
	private String word;
	private String wordDirection;

	private boolean dicFlag = false;

	public void init(ServletConfig config) throws ServletException {
		// Always call super.init
		super.init(config);
		/* grabs Directory of Context on server */
		String sep = File.separator;
		this.curDir = config.getServletContext().getRealPath(sep);

		dictionary.setCurDir(curDir);
		Thread t = new Thread(dictionary);
		t.start();

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		board = new EnhancedBoard();
		String scrabbleBoard = request.getParameter("board");
		this.rack = request.getParameter("rack");
		this.x = Integer.parseInt(request.getParameter("x"));
		this.y = Integer.parseInt(request.getParameter("y"));
		this.word = request.getParameter("word");
		this.wordDirection = request.getParameter("wordDirection");
		loadBoard(scrabbleBoard);
		WordDirection wd = WordDirection.DOWN;
		if (wordDirection.equals("right")) {
			wd = WordDirection.RIGHT;
			}
		int score =0;
		WordMatch wm = new WordMatch(x, y, word, wd);
		score = scoreWordMatch(wm, new BasicBoard(this.board));
		// need to add word to board so that can be validate one way or the other
	    addSelectedWord(wm);
		boolean waits = false;
		while (!waits) {
			waits = this.dicFlag;
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<words>");
		out.println("<word><valid>" + valid() + "</valid><value>" + score +"</value></word>");
		out.println("</words>");
	}

	public void dictionaryCantLoad() {
		// TODO Auto-generated method stub

	}

	public void wordCountUpdated() {
		this.dicFlag = true;
	}

	private String valid() {

		boolean pass = true;
		String returnvar = "";
		// Validate the board.
		for (int x = 0; x < 14; x++) {
			for (int y = 0; y < 14; y++) {
				if (board.getLetter(x, y) != ' ') {
					boolean singleLetter = false;
					if (x == 0 || board.getLetter(x - 1, y) == ' ') {
						String w = getRightWord(x, y).trim();
						if (w.length() > 1 && !dictionary.validWord(w)) {
							returnvar = w + " is not a word \r";
							pass = false;
						} else if (w.length() == 1) {
							singleLetter = true;
						}
					}
					if (y == 0 || board.getLetter(x, y - 1) == ' ') {
						String w = getDownWord(x, y).trim();
						if (w.length() > 1 && !dictionary.validWord(w)) {
							returnvar = returnvar + w + " is not a word \r";
							pass = false;
						} else if (w.length() == 1 && singleLetter) {
							returnvar = returnvar + w + " is not a word \r";
							pass = false;
						}
					}
				}
			}
		}

		// Show the success message.
		if (pass) {
			returnvar = "ok";
		}

		return returnvar;
	}

	private String getDownWord(int x, int Y) {
		String word = "";
		for (int y = Y; y < 15 && board.getLetter(x, y) != ' '; y++) {
			word += String.valueOf(board.getLetter(x, y));
		}
		return word;
	}

	private String getRightWord(int X, int y) {
		String word = "";
		for (int x = X; x < 15 && board.getLetter(x, y) != ' '; x++) {
			word += String.valueOf(board.getLetter(x, y));
		}
		return word;
	}

	private void loadBoard(String scrabbleBoard) {
		String[] cells = null;
		cells = scrabbleBoard.split(",");
		for (int i = 0; i < cells.length; i++) {
			String cell = cells[i];
			String[] members = cell.split("@");
			int x = Integer.parseInt(members[0]);
			int y = Integer.parseInt(members[1]);
			char ch = members[2].charAt(0);
			if(!members[2].equals("null")){
			board.setLetter(x, y, ch); }
		}

	}

	private void addSelectedWord(WordMatch wm) {
		if (wm.getDirection() == WordDirection.RIGHT) {
			for (int x = wm.getX(); x < wm.getX() + wm.getWord().length(); x++) {
				// Set the letter on the board.
				char c = wm.getWord().charAt(x - wm.getX());
				board.setLetter(x, wm.getY(), c);
			}
		} else {
			for (int y = wm.getY(); y < wm.getY() + wm.getWord().length(); y++) {
				// Set the letter on the board.
				char c = wm.getWord().charAt(y - wm.getY());
				board.setLetter(wm.getX(), y, c);
			}

		}
	}

	/**
	 * Scores a WordMatch.
	 * 
	 * @param match
	 *            The WordMatch to score.
	 * @param board
	 *            The BasicBoard to score with. (Please clone!)
	 */
	protected int scoreWordMatch(WordMatch match, BasicBoard board) {
		// Calculate the score at this time.
		// Calculate the new rack at this time.
		int score = 0;
		int crossWordScore = 0;
		int wordMultiplier = 1;
		String newRack = new String(rack);
		for (int w = 0; w < match.getWord().length(); w++) {
			int x = match.getX();
			int y = match.getY();
			int wx = (match.getDirection() == WordDirection.RIGHT ? w : 0);
			int wy = (match.getDirection() == WordDirection.DOWN ? w : 0);

			// Only set the letter if there is no letter there yet.
			if (board.getLetter(x + wx, y + wy) == ' ') {
				char letter = match.getWord().substring(w, w + 1).toCharArray()[0];

				// Set the letter.
				board.setLetter(x + wx, y + wy, letter);

				// Prepare the remove the letter after the next switch.
				// Must make sure that no points are assigned if it is a
				// wildcard.
				int rackIndex = newRack.indexOf(letter);
				if (rackIndex == -1) {
					// The letter must be a wildcard.
					letter = '.';
					rackIndex = newRack.indexOf(letter);
					board.setWild(x + wx, y + wy);
				}

				// Add the score of this letter.
				switch (board.getSquare(x + wx, y + wy)) {
				case TRIPLE_LETTER:
					score += Tiles.Scores[Tiles.getIndexOf(letter)] * 3;
					break;
				case DOUBLE_LETTER:
					score += Tiles.Scores[Tiles.getIndexOf(letter)] * 2;
					break;
				case CENTER:
					wordMultiplier *= 2;
					score += Tiles.Scores[Tiles.getIndexOf(letter)];
					break;
				case DOUBLE_WORD:
					wordMultiplier *= 2;
					score += Tiles.Scores[Tiles.getIndexOf(letter)];
					break;
				case TRIPLE_WORD:
					wordMultiplier *= 3;
					score += Tiles.Scores[Tiles.getIndexOf(letter)];
					break;
				case NORMAL:
					score += Tiles.Scores[Tiles.getIndexOf(letter)];
					break;
				}

				// Remove the letter from the rack.
				newRack = new String((rackIndex > 0 ? newRack.substring(0,
						rackIndex) : "")
						+ newRack.substring(rackIndex + 1));

				// Add post multipler points.
				crossWordScore += getCrossingWordScore(x + wx, y + wy, board,
						match.getDirection());
			} else {
				// Is the existing tile not a wildcard?
				if (!board.isWild(x + wx, y + wy)) {
					score += Tiles.Scores[Tiles.getIndexOf(board.getLetter(x
							+ wx, y + wy))];
				}
			}
		}

		// Do the multiplier.
		score *= wordMultiplier;

		// Add 50 if the rack is now empty, but was full.
		if (newRack.length() == 0 && rack.length() == 7) {
			score += 50;
		}

		// Add score from crossing words.
		score += crossWordScore;

		// Set the score for this match.
		// match.setScore(score);

		// Set the rack after this word is placed.
		// match.setRackAfter(newRack);
		return score;
	}

	/**
	 * Gets the score of a word crossing the given coordinates.
	 * 
	 * @param x
	 *            The x coordinate.
	 * @param y
	 *            The y coordinate.
	 * @param board
	 *            The board.
	 * @param direction
	 *            The direction of the original word. (Opposite of the intended
	 *            scored word.)
	 * @return The score of the crossing word.
	 */
	   private int getCrossingWordScore(int x, int y, BasicBoard board,
			WordDirection direction) {
		int score = 0;
		int length = 0;
		int multiplier = 1;

		if (direction == WordDirection.RIGHT) {
			// Set y2 to topmost letter.
			int y2 = y;
			while (y2 > 0 && board.getLetter(x, y2 - 1) != ' ') {
				y2--;
			}

			// Run y2 down to bottommost letter.
			while (y2 < 15 && board.getLetter(x, y2) != ' ') {
				// Add score if it's not wild.
				if (!board.isWild(x, y2)) {
					int letterScore = Tiles.Scores[Tiles.getIndexOf(board
							.getLetter(x, y2))];

					// Add the score of this letter.
					if (y != y2) {
						// Add the score of this letter.
						score += letterScore;
					} else {
						// Add the score of this common letter.
						switch (board.getSquare(x, y)) {
						case TRIPLE_LETTER:
							score += letterScore * 3;
							break;
						case DOUBLE_LETTER:
							score += letterScore * 2;
							break;
						case CENTER:
							multiplier *= 2;
							score += letterScore;
							break;
						case DOUBLE_WORD:
							multiplier *= 2;
							score += letterScore;
							break;
						case TRIPLE_WORD:
							multiplier *= 3;
							score += letterScore;
							break;
						case NORMAL:
							score += letterScore;
							break;
						}
					}
				}

				y2++;
				length++;
			}
		} else {
			// Set x2 to leftmost letter.
			int x2 = x;
			while (x2 > 0 && board.getLetter(x2 - 1, y) != ' ') {
				x2--;
			}

			// Run y2 down to bottommost letter.
			while (x2 < 15 && board.getLetter(x2, y) != ' ') {
				// Add score if it's not wild.
				if (!board.isWild(x2, y)) {
					int letterScore = Tiles.Scores[Tiles.getIndexOf(board
							.getLetter(x2, y))];

					if (x != x2) {
						// Add the score of this letter.
						score += letterScore;
					} else {
						// Add the score of this common letter.
						switch (board.getSquare(x, y)) {
						case TRIPLE_LETTER:
							score += letterScore * 3;
							break;
						case DOUBLE_LETTER:
							score += letterScore * 2;
							break;
						case CENTER:
							multiplier *= 2;
							score += letterScore;
							break;
						case DOUBLE_WORD:
							multiplier *= 2;
							score += letterScore;
							break;
						case TRIPLE_WORD:
							multiplier *= 3;
							score += letterScore;
							break;
						case NORMAL:
							score += letterScore;
							break;
						}
					}
				}

				x2++;
				length++;
			}
		}

		// Return the score, or 0 if no word was formed.
		return (length > 1 ? score * multiplier : 0);
	}

}
