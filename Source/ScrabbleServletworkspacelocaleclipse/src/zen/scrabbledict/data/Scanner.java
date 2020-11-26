/**
 *  This file is part of Scrabble Dictionary.
 *
 *  Scrabble Dictionary is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Scrabble Dictionary is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Scrabble Dictionary.  If not, see <http://www.gnu.org/licenses/>.
 */
package zen.scrabbledict.data;

import java.util.Iterator;
import java.util.Vector;

/**
 * The Scanner performs WorkUnits to process matches on the game board.
 * @author flszen
 */
class Scanner implements Runnable {

    //<editor-fold desc="Fields" defaultstate="collapsed">
    private ScannerProvider provider;
    private AbstractBoard board;
    private String rack;
    private Dictionary dictionary;

    //</editor-fold>
    //<editor-fold desc="Constructors" defaultstate="collapsed">
    /**
     * Instantiates a Scanner.
     * @param provider The ScannerProvider to source WorkUnits from and post matches to.
     * @param board The AbstractBoard to check words against. 
     * @param dictionary The Dictionary to look up words in.
     * @param rack The rack of letters to use.
     */
    protected Scanner(ScannerProvider provider, AbstractBoard board, Dictionary dictionary, String rack) {
        this.provider = provider;
        this.board = board;
        this.dictionary = dictionary;
        this.rack = rack.toUpperCase();
    }

    //</editor-fold>
    //<editor-fold desc="Methods" defaultstate="collapsed">
    public void run() {
        WorkUnit wu = provider.getWorkUnit();
        Vector<WordMatch> matches = new Vector<WordMatch>();

        while (wu != null) {
            // Cell Scanning.
            // Perform executeExpansion?
            if ((wu.getX() == 14 && wu.getDirection() == WordDirection.RIGHT) ||
                    (wu.getY() == 14 && wu.getDirection() == WordDirection.DOWN)) {
            // Never perform executeExpansion when the letter is on the right or
            // bottom edge and the direction is off the board.
            // (Two letter word minimum wether existing or not.)
            } else if ((wu.getX() > 0 && wu.getDirection() == WordDirection.RIGHT && board.getLetter(wu.getX() - 1, wu.getY()) != ' ') ||
                    (wu.getY() > 0 && wu.getDirection() == WordDirection.DOWN && board.getLetter(wu.getX(), wu.getY() - 1) != ' ')) {
            // Do not perform executeExpansion when this is a continuation.
            } else {
                // Perform executeExpansion in the end.
                executeExpansion(wu, matches);
            }

            // Post results.
            if (matches.size() != 0) {
                provider.addMatches(matches);
            }

            // Notify the provider that this unit is finished.
            provider.finishedWorkUnit(wu);

            // Get the next work unit.
            wu = provider.getWorkUnit();
        }
    }

    /**
     * Sets the rack.
     * @param rack The rack to use.
     */
    protected void setRack(String rack) {
        this.rack = rack;
    }

    /**
     * Expands the possibilities from the given location.
     * @param wu The WorkUnit that is being processed.
     * @param matches A Vector<WordMatch> of matching words.
     */
    protected void executeExpansion(WorkUnit wu, Vector<WordMatch> matches) {
        // Expand in the given direction.
        int x = wu.getX();
        int y = wu.getY();
        int len = rack.length();
        boolean consider = false;
        boolean lettersUsed = false;
        String mask = "";

        if (wu.getDirection() == WordDirection.RIGHT) {
            // Expand a right word.
            for (int z = 0; x + z <= 14; z++) {
                // Reduce the length of the rack when the cell is empty.
                if (board.getLetter(x + z, y) == ' ') {
                    len--;
                    lettersUsed = true;

                    // Add the wildcard to the mask.
                    mask += ".";

                    // Break out of the loop when len drops below zero.
                    if (len < 0) {
                        break;
                    }
                } else {
                    // Any letter immediately makes the word considerable.
                    consider = true;

                    // Add the letter to the mask.
                    mask += board.getLetter(x + z, y);
                }

                // Process considerations.
                if (!consider) {
                    if (y > 0 && board.getLetter(x + z, y - 1) != ' ') {
                        // Consider the word when a letter above is present.
                        consider = true;
                    } else if (y < 14 && board.getLetter(x + z, y + 1) != ' ') {
                        // Consider the word when a letter below is present.
                        consider = true;
                    } else if (x + z == 7 && y == 7) {
                        // Consider the word when the center tile is crossed.
                        consider = true;
                    }

                }

                // The first cell on its own is not considered for expansion.
                if (z != 0 && consider) {
                    // Consider only when the next letter is empty
                    // and there have been letters used.
                    //if ((x + z == 14 || board.getLetter(x + z, y) == ' ') &&
                    //        lettersUsed && (x + z < 14 && board.getLetter(x + z + 1, y) == ' ')) {
                    if (lettersUsed &&
                            ((x + z < 14 && board.getLetter(x + z + 1, y) == ' ') || x + z == 14)) {
                        executeMatching(wu, matches, mask);
                    }
                }
            }
        } else {
            // Expand a down word.
            for (int z = 0; y + z <= 14; z++) {
                // Reduce the length of the rack when the cell is empty.
                if (board.getLetter(x, y + z) == ' ') {
                    len--;
                    lettersUsed = true;

                    // Add the wildcard to the mask.
                    mask += ".";

                    // Break out of the loop when len drops below zero.
                    if (len < 0) {
                        break;
                    }
                } else {
                    // Any letter immediately makes the word considerable.
                    consider = true;

                    // Add the letter to the mask.
                    mask += board.getLetter(x, y + z);
                }

                // Process considerations.
                if (!consider) {
                    if (x > 0 && board.getLetter(x - 1, y + z) != ' ') {
                        // Consider the word when a letter left is present.
                        consider = true;
                    } else if (x < 14 && board.getLetter(x + 1, y + z) != ' ') {
                        // Consider the word when a letter right is present.
                        consider = true;
                    } else if (x == 7 && y + z == 7) {
                        // Consider the word when the center tile is crossed.
                        consider = true;
                    }

                }

                // The first cell on its own is not considered for expansion.
                if (z != 0 && consider) {
                    // Consider only when the next letter is empty
                    // and there have been letters used.
                    //if ((y + z == 14 || board.getLetter(x, y + z + 1) == ' ') &&
                    //        lettersUsed && (y + z < 14 && board.getLetter(x, y + z + 1) == ' ')) {
                    if (lettersUsed &&
                            ((y + z < 14 && board.getLetter(x, y + z + 1) == ' ') || y + z == 14)) {
                        executeMatching(wu, matches, mask);
                    }
                }
            }
        }
    }

    /**
     * Attempt to match words from the dictionary.
     * @param wu The WorkUnit being executed.
     * @param matches The Vector<WordMatch> of matches.
     * @param mask The regex mask to use for matching.
     */
    protected void executeMatching(WorkUnit wu, Vector<WordMatch> matches, String mask) {
        // Get all words that match this mask.
        Vector<Word> matchingWords = dictionary.matchLetters(mask, rack, false);
        
        // Iterate through the results.
        Iterator<Word> iw = matchingWords.iterator();
        while (iw.hasNext()) {
            Word w = iw.next(); 
             // Check to make sure the word is valid.
            if (isCandidate(wu, w, new BasicBoard(board))) {
                // Create the WordMatch.
                WordMatch wm = new WordMatch(wu.getX(), wu.getY(), w.getWord(), wu.getDirection());

                // Score the WordMatch.
                scoreWordMatch(wm, new BasicBoard(board));
                 
                // Add it to the matches vector.
                matches.add(wm);
            }
        }
    }

    /**
     * Validates that a word addition does not create invalid words.
     * @param wu The WorkUnit.
     * @param w The word.
     * @param board The board to place the word on.
     * @return True when the addition is valid, false otherwise.
     */
    protected boolean isCandidate(WorkUnit wu, Word w, BasicBoard board) {
        if (wu.getDirection() == WordDirection.RIGHT) {
            // Add the word to the right.
            for (int z = 0; z < w.getWord().length(); z++) {
                // Set the character.
                board.setLetter(wu.getX() + z, wu.getY(), w.getWord().toCharArray()[z]);

                // Return false on invalid word.
                if (!dictionary.validWord(getCrossingWord(wu, wu.getX() + z, board))) {
                    return false;
                }
            }
        } else {
            // Add the word to the right.
            for (int z = 0; z < w.getWord().length(); z++) {
                // Set the character.
                board.setLetter(wu.getX(), wu.getY() + z, w.getWord().toCharArray()[z]);

                // Return false on invalid word.
                if (!dictionary.validWord(getCrossingWord(wu, wu.getY() + z, board))) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Gets the word crossing through the given location and word.
     * @param wu The WorkUnit being executed.
     * @param z The anonymous coordinate of the position on the board for the word.
     * @param board The BasicBoard being reviewed.
     * @return The matched word.
     */
    protected String getCrossingWord(WorkUnit wu, int z, BasicBoard board) {
        String word = "";

        if (wu.getDirection() == WordDirection.RIGHT) {
            // Set y2 to topmost letter.
            int y2 = wu.getY();
            while (y2 > 0 && board.getLetter(z, y2 - 1) != ' ') {
                y2--;
            }

            // Run y2 down to bottommost letter.
            while (y2 < 15 && board.getLetter(z, y2) != ' ') {
                // Add letter to word.
                word += board.getLetter(z, y2);

                y2++;
            }
        } else {
            // Set x2 to leftmost letter.
            int x2 = wu.getX();
            while (x2 > 0 && board.getLetter(x2 - 1, z) != ' ') {
                x2--;
            }

            // Run x2 over to rightmost letter.
            while (x2 < 15 && board.getLetter(x2, z) != ' ') {
                // Add letter to word.
                word += board.getLetter(x2, z);

                x2++;
            }
        }

        // Return the word.
        return word;
    }

    /**
     * Scores a WordMatch.
     * @param match The WordMatch to score.
     * @param board The BasicBoard to score with. (Please clone!)
     */
    protected void scoreWordMatch(WordMatch match, BasicBoard board) {
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
                // Must make sure that no points are assigned if it is a wildcard.
                int rackIndex = newRack.indexOf(letter);
                if (rackIndex == -1) {
                	String newword = match.getWord().substring(0,w) + match.getWord().substring(w,w+1).toLowerCase() + match.getWord().substring(w+1,match.getWord().length());
                	match.setWord(newword) ;
                  	// The letter must be a wildcard
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
                newRack = new String((rackIndex > 0 ? newRack.substring(0, rackIndex) : "") + newRack.substring(rackIndex + 1));

                // Add post multipler points.
                crossWordScore += getCrossingWordScore(x + wx, y + wy, board, match.getDirection());
            } else {
                // Is the existing tile not a wildcard?
                if (!board.isWild(x + wx, y + wy)) {
                    score += Tiles.Scores[Tiles.getIndexOf(board.getLetter(x + wx, y + wy))];
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
        match.setScore(score);
        
        // Set the rack after this word is placed.
        match.setRackAfter(newRack);  
    }

    /**
     * Gets the score of a word crossing the given coordinates.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param board The board.
     * @param direction The direction of the original word. (Opposite of the intended scored word.)
     * @return The score of the crossing word.
     */
    protected static int getCrossingWordScore(int x, int y, BasicBoard board, WordDirection direction) {
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
                    int letterScore = Tiles.Scores[Tiles.getIndexOf(board.getLetter(x, y2))];

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
                    int letterScore = Tiles.Scores[Tiles.getIndexOf(board.getLetter(x2, y))];

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
    //</editor-fold>
}
