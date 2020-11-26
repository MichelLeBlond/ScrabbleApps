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
 * This class describes a word.
 * @author flszen
 */
public class Word {

    //<editor-fold desc="Fields" defaultstate="collapsed">
    private String word;
    //</editor-fold>
    //<editor-fold desc="Constructors" defaultstate="collapsed">
    /**
     * Constructs a Word.
     * @param word The word as a string.
     */
    public Word(String word) {
        // Localize word.
      //  this.word = word.toUpperCase();
    	this.word = word;

    }
    //</editor-fold>
    //<editor-fold desc="Methods" defaultstate="collapsed">
    /**
     * Gets the word as a string.
     * @return The word as a string.
     */
    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        return word;
    }
    //</editor-fold>
}
