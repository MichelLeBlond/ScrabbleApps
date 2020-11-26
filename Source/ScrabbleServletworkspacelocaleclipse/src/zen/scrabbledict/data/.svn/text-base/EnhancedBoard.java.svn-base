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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.Iterator;

/**
 * This extension of AbstractBoard provides processing events.
 * @author flszen
 */
public class EnhancedBoard extends AbstractBoard {
    //<editor-fold desc="Fields" defaultstate="collapsed">
    private int update[][] = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    private HashSet<BoardListener> listeners_ = new HashSet<BoardListener>();
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Constructs a new EnhancedBoard.
     */
    public EnhancedBoard() {
    }

    /**
     * Constructs an EnhancedBoard from an existing board.
     * @param existingBoard
     */
    public EnhancedBoard(AbstractBoard existingBoard) {
        // It's like clone.
        for (int x = 0; x < 15; x++) {
            letters[x] = existingBoard.letters[x].clone();
            wild[x] = existingBoard.wild[x].clone();
        }

    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Methods">
    /**
     * Loads an EnhancedBoard from the given file.
     * @param f The file to load from. 
     * @return The loaded file.
     */
    public static EnhancedBoard LoadEnhancedBoard(File f) {
        try {
            // Prepare to read the file.
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Construct and return the EnhancedBoard.
            try {
                return new EnhancedBoard(((SerializableBoard) ois.readObject()).getAbstractBoard());
            } catch (ClassNotFoundException ex) {
                System.err.println("While loading board: " + ex);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Can't load a file that's not found: " + ex);
        } catch (IOException ex) {
            System.err.println("Saved game seems invalid: " + ex);
        }

        return new EnhancedBoard();
    }

    //</editor-fold>
    //<editor-fold desc="Accessors" defaultstate="collapsed">
    /**
     * Adds a BoardListener to this board's set of listeners.
     * @param listener
     */
    public void addBoardListener(BoardListener listener) {
        listeners_.add(listener);
    }

    /**
     * Removes a board listener.
     * @param listener The listener to be removed.
     */
    public void removeBoardListener(BoardListener listener) {
        listeners_.remove(listener);
    }

    /**
     * Fires the updatedLetter event.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    protected void fireUpdatedLetterEvent(int x, int y) {
        Iterator<BoardListener> i = listeners_.iterator();
        while (i.hasNext()) {
            BoardListener listener = i.next();
            listener.updatedLetter(x, y);
        }
    }

    /**
     * Fires the beginProcessingCell event.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    protected synchronized void fireBeginProcessingCellEvent(int x, int y) {
        update[x][y]++;

        if (update[x][y] == 1) {
            Iterator<BoardListener> i = listeners_.iterator();
            while (i.hasNext()) {
                BoardListener listener = i.next();
                listener.beginProcessingCell(x, y);
            }
        }
    }

    /**
     * Fires the endProcessingCell event.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    protected synchronized void fireEndProcessingCellEvent(int x, int y) {
        update[x][y]--;

        if (update[x][y] == 0) {
            Iterator<BoardListener> i = listeners_.iterator();
            while (i.hasNext()) {
                BoardListener listener = i.next();
                listener.endProcessingCell(x, y);
            }
        }
    }

    @Override
    public EnhancedBoard clone() {
        EnhancedBoard newBoard = new EnhancedBoard();
        for (int x = 0; x < 15; x++) {
            newBoard.letters[x] = letters[x].clone();
            newBoard.wild[x] = wild[x].clone();
        }

        return newBoard;
    }
    //</editor-fold>
}
