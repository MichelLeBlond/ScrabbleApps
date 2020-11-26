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

/**
 * The AbstractBoard class implements the basics of a game board.
 * This class must be extended to allow more functionality.
 * @author flszen
 */
public abstract class AbstractBoard {
    //<editor-fold defaultstate="collapsed" desc="Fields">
    private static final long serialVersionUID = -2083081828951929729L;
    
    /**
     * The Squares values.
     */
    Square squares[][] = {
        {Square.TRIPLE_WORD, Square.NORMAL, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.TRIPLE_WORD, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.NORMAL, Square.TRIPLE_WORD},
        {Square.NORMAL, Square.DOUBLE_WORD, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.TRIPLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.TRIPLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_WORD, Square.NORMAL},
        {Square.NORMAL, Square.NORMAL, Square.DOUBLE_WORD, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_WORD, Square.NORMAL, Square.NORMAL},
        {Square.DOUBLE_LETTER, Square.NORMAL, Square.NORMAL, Square.DOUBLE_WORD, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_WORD, Square.NORMAL, Square.NORMAL, Square.DOUBLE_LETTER},
        {Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_WORD, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_WORD, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.NORMAL},
        {Square.NORMAL, Square.TRIPLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.TRIPLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.TRIPLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.TRIPLE_LETTER, Square.NORMAL},
        {Square.NORMAL, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.NORMAL},
        {Square.TRIPLE_WORD, Square.NORMAL, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.CENTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.NORMAL, Square.TRIPLE_WORD},
        {Square.NORMAL, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.NORMAL},
        {Square.NORMAL, Square.TRIPLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.TRIPLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.TRIPLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.TRIPLE_LETTER, Square.NORMAL},
        {Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_WORD, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_WORD, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.NORMAL},
        {Square.DOUBLE_LETTER, Square.NORMAL, Square.NORMAL, Square.DOUBLE_WORD, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_WORD, Square.NORMAL, Square.NORMAL, Square.DOUBLE_LETTER},
        {Square.NORMAL, Square.NORMAL, Square.DOUBLE_WORD, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_WORD, Square.NORMAL, Square.NORMAL},
        {Square.NORMAL, Square.DOUBLE_WORD, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.TRIPLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.TRIPLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_WORD, Square.NORMAL},
        {Square.TRIPLE_WORD, Square.NORMAL, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.TRIPLE_WORD, Square.NORMAL, Square.NORMAL, Square.NORMAL, Square.DOUBLE_LETTER, Square.NORMAL, Square.NORMAL, Square.TRIPLE_WORD}};
    
    /**
     * The letters on the baord.
     */
    char letters[][] = {
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
        {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}};
    
    /**
     * The wild flags for each position on the board.
     */
    boolean wild[][] = {
        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}};
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Accessors">
    /**
     * Gets the square at the specified coordinates.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The Square at the specified coordinates.
     */
    public Square getSquare(int x, int y) {
        return squares[x][y];
    }

    /**
     * Gets the letter at the specified coordinates.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The letter at the specified coordinates.
     */
    public char getLetter(int x, int y) {
        return letters[x][y];
    }

    /**
     * Sets the letter at the specified coordinates.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param c The character.
     */
    public void setLetter(int x, int y, char c) {
        letters[x][y] = String.valueOf(c).toUpperCase().charAt(0);
        fireUpdatedLetterEvent(x, y);
    }

    /** 
     * Sets the wild state of a position on the board.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void setWild(int x, int y) {
        if (wild[x][y] == false) {
            wild[x][y] = true;
            fireUpdatedLetterEvent(x, y);
        }
    }

    /**
     * Clears the wild state of a position on the board.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void clearWild(int x, int y) {
        if (wild[x][y] == true) {
            wild[x][y] = false;
            fireUpdatedLetterEvent(x, y);
        }
    }

    /**
     * Gets the wild state of a position on the board.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return True if the position is wild.
     */
    public boolean isWild(int x, int y) {
        return wild[x][y];
    }
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * Clones this board. The listeners are NOT cloned.
     * @return The cloned board.
     */
    @Override
    public abstract AbstractBoard clone();

    /**
     * Gets the empty status of the board.
     * @return True when there are no letters on the board, false otherwise.
     */
    public boolean isEmpty() {
        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 15; y++) {
                if (letters[x][y] != ' ') {
                    return false;
                }
            }
        }

        return true;
    }
    //</editor-fold>
    //<editor-fold desc="Abstract Methods" defaultstate="collapsed">
    /**
     * Fires an UpdatedLetter event.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    protected abstract void fireUpdatedLetterEvent(int x, int y);
    //</editor-fold>
}
