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

import java.util.Comparator;

/**
 * This comparator is used to compare WordMatch scores.
 * @author flszen
 */
public class ScoreComparator implements Comparator<WordMatch> {

    private ScoreComparisonType comparisonType;
    
    /**
     * Creates a ScoreCompoarator that compares the points only.
     * @param comparisonType The type of comparison to perform.
     */
    public ScoreComparator(ScoreComparisonType comparisonType) {
        this.comparisonType = comparisonType;
    }
    
    public int compare(WordMatch wm1, WordMatch wm2) {
        // Abort on nulls.
        if (wm1 == null || wm2 == null) {
            throw new UnsupportedOperationException("One of the objects is null!");
        }

        // Do different types first.
        switch (comparisonType) {
            case SCORE_ONLY:
                break;
            case WORD_ONLY:
                return wm1.getWord().compareTo(wm2.getWord());
            case RACK_USAGE:
                if (wm1.getRackAfter().length() != wm2.getRackAfter().length()) {
                    return wm1.getRackAfter().length() - wm2.getRackAfter().length();
                }
                break;
        }
        
        // An exhaustive comparison is made:        
        // Determine if scores are equal.
        if (wm1.getScore() == wm2.getScore()) {
            // Determine if the words are equal.
            if (!wm1.getWord().equals(wm2.getWord())) {
                // Return the word comparison.
                return wm1.getWord().compareTo(wm2.getWord());
            } else {
                // Generate the order by X, then Y, then arbitrary.
                if (wm1.getX() != wm2.getX()) {
                    return wm1.getX() - wm2.getX(); // Return the X difference.
                } else if (wm1.getY() != wm2.getY()) {
                    return wm1.getY() - wm2.getY(); // Return Y difference.
                } else {
                    return 1;
                }
            }
        } else {
            // Return the score comparison.
            return wm2.getScore() - wm1.getScore();
        }
    }
}
