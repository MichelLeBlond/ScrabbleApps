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
 * This class describes a word match (location on the board, score, etc).
 * @author flszen
 */
public class WordMatch {

    //<editor-fold desc="Fields" defaultstate="collapsed">
    private String word;
    private int x;
    private int y;
    private int score = 0;
    private String rackAfter;
    private WordDirection direction;
    //</editor-fold>
    //<editor-fold desc="Constructors" defaultstate="collapsed">

    /**
     * Constructs a WordMatch with the given parameters.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param word The word.
     * @param direction The word's direction.
     */
    public WordMatch(int x, int y, String word, WordDirection direction) {
        this.x = x;
        this.y = y;
        this.word = word;
        this.direction = direction;
    }
    //</editor-fold>
    //<editor-fold desc="Methods" defaultstate="collapsed">

    /**
     * Gets the X coordinate.
     * @return The x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the Y coordinate.
     * @return The Y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the score.
     * @return The score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score.
     * @param score The score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Sets the rack after this word has been placed.
     * @param rackAfter The rack after this word is placed.
     */
    public void setRackAfter(String rackAfter) {
        this.rackAfter = rackAfter;
    }

    /**
     * Gets the rack after this word has been placed.
     * @return The rack after this word had been placed.
     */
    public String getRackAfter() {
        return rackAfter;
    }

    /**
     * Gets the word.
     * @return The word.
     */
    public String getWord() {
        return word;
    }
    
    public void setWord(String word){
    	this.word = word;
    }

    /**
     * Gets the word's direction.
     * @return The word's direction.
     */
    public WordDirection getDirection() {
        return direction;
    }

    @Override
    public String toString() {
     // return "\"" + getWord() + "\" (" + String.valueOf(x + 1) + ", " + String.valueOf(y + 1) + ", " + (getDirection() == WordDirection.RIGHT ? "R" : "D") + ") " + String.valueOf(getScore()) + "pts";
    	return "<word>" +"<value>" + getWord() + "</value>" + "<column>" +  String.valueOf(x) + "</column>" + "<row>" + String.valueOf(y) + "</row>" + 
    	"<orientation>" + (getDirection() == WordDirection.RIGHT ? "1" : "0")  +  "</orientation>" + 
    	"<score>" + getScore() + "</score>" + "<rackafter>" + getRackAfter() + "</rackafter>" + "</word>";
    }
    //</editor-fold>
}
