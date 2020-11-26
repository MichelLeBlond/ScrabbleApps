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

import java.util.HashSet;
import java.util.TreeMap;
import java.util.Vector;

/**
 * This class is constructed to efficiently manage the dictionary of words.
 * @author flszen
 */
public class WordsCollection {
    //<editor-fold desc="Fields" defaultstate="collapsed">
    private TreeMap<String, Vector<String>> regexTree = new TreeMap<String, Vector<String>>();
    private Vector<HashSet<String>> words = new Vector<HashSet<String>>();
    private Vector<Vector<String>> wordsVector = new Vector<Vector<String>>();
    //</editor-fold>
    //<editor-fold desc="Constructors" defaultstate="collapsed">
    /**
     * Creates a new instance of WordsCollection.
     */
    public WordsCollection() {
        // Create the lists for the words.
        for (int x = 0; x <= 13; x++) {
            words.add(new HashSet<String>());
            wordsVector.add(new Vector<String>());
        }
    }
    //</editor-fold>
    //<editor-fold desc="Methods" defaultstate="collapsed">
    /**
     * Adds a word to the words colleciton.
     * @param word The word to add.
     */
    public void addWord(Word word) {
        // Add the word to the vector.
        words.get(word.getWord().length() - 2).add(word.getWord());
        wordsVector.get(word.getWord().length() - 2).add(word.getWord());
    }

    /**
     * Checks a given word for validity.
     * @param word The word to check.
     * @return True when the word is valid, false otherwise.
     */
    public boolean validWord(String word) {
        // Process the words based on length.        
        switch (word.length()) {
            case 1:
                return true;

            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                return words.get(word.length() - 2).contains(word);

            default:
                return false;
        }
    }

    /**
     * Returns all words that match the given regex.
     * @param regex The regex to match words for.
     * @return The Vector of matching words.
     */
    public Vector<String> matchWords(String regex) {
        Vector<String> matchedWords;

        // Attempt to retrieve a duplicate result set.
        matchedWords = regexTree.get(regex);

        // Build a result set if it isn't available.
        if (matchedWords == null) {
            matchedWords = pullWords(regex);
            addRegex(regex, matchedWords);
        }

        // Return the result.
        return matchedWords;
    }
    
    private synchronized void addRegex(String regex, Vector<String> matchedWords) {
        regexTree.put(regex, matchedWords);
    }

    private Vector<String> pullWords(String regex) {
        // Prepare the return vector.
        Vector<String> returnWords = new Vector<String>();

        // Build the pattern.
        // All positions are 0x00 when ".", otherwise they are the correct character.
        char[] pattern = new char[regex.length()];
        int length = regex.length();
        for (int x = 0; x < length; x++) {
            if (regex.charAt(x) != '.') {
                pattern[x] = regex.charAt(x);
            }
        }

        // Iterate through the list of words.
        Vector<String> vec = wordsVector.get(regex.length() - 2);
        int vecLength = vec.size();
        for (int x = 0; x < vecLength; x++) {
            String s = vec.elementAt(x);
            boolean good = true;
            for (int y = 0; y < length; y++) {
                // Assume that all letters not specified are good.

                // If the letter is specified, it must match.
                if (pattern[y] != 0x00 && s.charAt(y) != pattern[y]) {
                    good = false;
                    break;
                }
            }

            // Add good words to the vector.
            if (good) {
                returnWords.add(s);
            }
        }

        return returnWords;
    }
    //</editor-fold>
}
