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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;
import java.util.prefs.Preferences;

/**
 * This class provides the word matching functionality.
 * @author flszen
 */
public class Dictionary implements Runnable {
    // <editor-fold defaultstate="collapsed" desc="Fields">
    private Preferences prefs = Preferences.userRoot().node("zen.scrabbledict");
    private Vector<Word> words;
    private WordsCollection wordsCollection;
    private TreeMap<String, TreeMap<String, Vector<Word>>> lettersCache;
    private DictionaryListener listener;
    private String name;
    private String curDir;
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Gets the words in the dictionary.
     * @return The Vector of the words in the dictionary.
     */
    public Vector<Word> getWords() {
        return words;
    }
    public void setCurDir(String dir){
    	this.curDir =dir;
    }

    /**
     * Sets the name of the dictionary.
     * @param name The name of the dictionary.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the dictionary.
     * @return The name of the dictionary.
     */
    public String getName() {
        if (name == null) {
            return "No Dictionary Loaded";
        } else {
            return name;
        }
    }

    /**
     * Gets the number of words in the dictionary.
     * @return The word count.
     */
    public long getWordCount() {
        if (words == null) {
            return 0;
        } else {
            return words.size();
        }
    }

    /**
     * Constructs a new Dictionary.
     * @param listener The listener of the Dictionary.
     */
    public Dictionary(DictionaryListener listener) {
        this.listener = listener;
    }

    /**
     * Loads the words contained in the parameter into the Dictionary.
     * @param contents The string of words. These must be separated by line endings.
     */
    public void loadWords(String contents) {
        // Initialize
        words = new Vector<Word>();
        wordsCollection = new WordsCollection();
        lettersCache = new TreeMap<String, TreeMap<String, Vector<Word>>>();

        // Convert to upper case.
        contents = contents.toUpperCase();

        // Clean up the contents. Replcae line endings.
        contents = contents.replaceAll("\r\n", "\n");

        // Break up the contents into words, one per line.
        String[] givenWords = contents.split("\n");

        // Loop through to create the vector.
        for (int x = 0; x < givenWords.length; x++) {
            Word word = new Word(givenWords[x]);
            words.add(word);
            wordsCollection.addWord(word);
        }

        // Fire the listener update.
        fireWordCountUpdated();
    }

    /**
     * Given a mask and rack, this gets all words that match.
     * @param regexMask The Regex mask.
     * @param rack The rack.
     * @param initial Specify True when this is an initial search.
     * @return The Vector of matching words.
     */
    public Vector<Word> matchLetters(String regexMask, String rack, boolean initial) {
        // The return vector.
        Vector<Word> returnWords;

        // Check for the cached rack.
    TreeMap<String, Vector<Word>> matchedRack = lettersCache.get(rack);

        // Try and get a cache hit.
        if (matchedRack != null) {
            // Check for regexMask match.
            returnWords = matchedRack.get(regexMask);

            if (returnWords == null) {
                // Run the actual process.
                returnWords = matchLettersProcess(regexMask, rack, initial);

                // Insert the returnWords into the matched rack.
                synchronized (matchedRack) {
                    matchedRack.put(regexMask, returnWords);
                }
            } else {
                // Nothing needs to be done, returnWords is set.
            }
        } else { 
            // Run the actual process.
            returnWords = matchLettersProcess(regexMask, rack, initial);

            // Create the rack for the cache.
            TreeMap<String, Vector<Word>> newRack = new TreeMap<String, Vector<Word>>();

            // Add the regex mask into the rack.
            newRack.put(regexMask, returnWords);

            // Add the rack into the cache.
            synchronized (lettersCache) {
                lettersCache.put(rack, newRack);
            }
      } 

        return returnWords;
    }

    private Vector<Word> matchLettersProcess(String regexMask, String rack, boolean initial) {
        Vector<Word> returnWords = new Vector<Word>();
        Vector<String> matchedWords = wordsCollection.matchWords(regexMask);

        // Add the characters in the regex string to available letters.
        for (int x = 0; x < regexMask.length(); x++) {
            if (regexMask.charAt(x) != '.') {
                rack += regexMask.charAt(x);
            }
        }

        // Count instances of "." in the rack.
        int wildcardCount = 0;
        for (int x = 0; x < rack.length(); x++) {
            if (rack.charAt(x) == '.') {
                wildcardCount++;
            }
        }

        // Process each matched word.
        Iterator<String> i = matchedWords.iterator();
        while (i.hasNext()) {
            String originalWord = i.next();
            String word = originalWord;

            // Remove the available letters from the word.
            boolean atLeastOne = false;
            for (int x = 0; word.length() != 0 && x < rack.length(); x++) {
                int loc = word.indexOf(rack.charAt(x));

                if (loc != -1) {
                    String newWord = "";
                    if (loc > 0) {
                        newWord += word.substring(0, loc);
                    }
                    if (loc < word.length()) {
                        newWord += word.substring(loc + 1);
                    }
                    word = newWord;
                    atLeastOne = true;
                }
            }

            // If the word has zero length, add it.
            if (word.length() <= wildcardCount && (atLeastOne || initial)) {
                returnWords.add(new Word(originalWord));
            }
        }

        // Return
        return returnWords;
    }

    /**
     * Determines if a given word exists in the dictionary.
     * @param word The word to look up.
     * @return True when it exists, false otherwise.
     */
    public boolean validWord(String word) {
        return wordsCollection.validWord(word);
    }

    private void fireWordCountUpdated() {
        if (listener != null) {
            listener.wordCountUpdated();
        }
    }
    
    private void fireCantLoadDictionary() {
        if (listener != null) {
            listener.dictionaryCantLoad();
        }
    }

    public void run() {
        // Determine if the dictionary is configured.
      //  if (prefs.get("dictionary", null) != null) {
            // The preference exists.
            // Attempt to load the file.
            try {
              //  File f = new File(prefs.get("dictionary", null));
            	String sep = File.separator; // if needed
            	File f = new File(curDir + "twl.txt");
                FileReader fr = new FileReader(f);
                char cb[] = new char[(int) f.length()];

                try {
                    // Read the file.
                    fr.read(cb);

                    // Set the dictionary name.
                    this.setName(f.getName());

                    // Load the words.
                    this.loadWords(String.valueOf(cb));

                    if (this.getWords().size() == 0) {
                        fireWordCountUpdated();
                    } else {
                        // Store the preference.
                      //  prefs.put("dictionary", f.getAbsolutePath());
                      //  System.out.println(f.getAbsolutePath());
                    }
                } catch (IOException ex) {
                    System.err.println(ex);
                    fireCantLoadDictionary();
                    fireWordCountUpdated();
                }
            } catch (FileNotFoundException ex) {
                System.err.println(ex);
                fireCantLoadDictionary();
                fireWordCountUpdated();
            }
     /**   } else {
            // The dictinoary is not configured. Present the user with the dialog.
            fireCantLoadDictionary();
        } */

  }
    //</editor-fold>
}

