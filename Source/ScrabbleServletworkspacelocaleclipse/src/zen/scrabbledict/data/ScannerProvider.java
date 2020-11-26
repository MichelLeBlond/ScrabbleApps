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

import java.util.Collection;
import java.util.Vector;

/**
 * The ScannerProvider provides WorkUnits to a scanner.
 * Each cell on the board is a given as a WorkUnit.
 * @author flszen
 */
class ScannerProvider {

    //<editor-fold desc="Fields" defaultstate="collapsed">
    private EnhancedBoard board;
    private Vector<WordMatch> matches = new Vector<WordMatch>();
    private int x = 0;
    private int y = 0;
    private WordDirection direction = WordDirection.RIGHT;
    private boolean finished = false;
    //</editor-fold>
    //<editor-fold desc="Constructors" defaultstate="collapsed">
    /**
     * Constructs the scanner provider.
     * @param board The board to use for sending status updates.
     */
    ScannerProvider(EnhancedBoard board) {
        // Localize
        this.board = board;
    }
    //</editor-fold>
    //<editor-fold desc="Methods" defaultstate="collapsed">
    /**
     * Gets the matches found.
     * @return The matches found.
     */
    protected Vector<WordMatch> getMatches() {
        return matches;
    }

    /**
     * Adds matches from a WrokUnit.
     * @param matches The Queue<WordMatch> of matches.
     */
    protected void addMatches(Collection<WordMatch> matches) {
        if (!matches.isEmpty()) {
            this.matches.addAll(matches);
            matches.clear();
        }
    }

    /**
     * Gets a WorkUnit.
     * @return The WorkUnit to process, or null if there are no more work units.
     */
    protected synchronized WorkUnit getWorkUnit() {
        WorkUnit wu = null;

        // Create the work unit if it's not over.
        if (!finished) {
            wu = new WorkUnit(x, y, direction);

            // Advance the thing.
            if (direction == WordDirection.RIGHT) {
                direction = WordDirection.DOWN;
            } else {
                // Increment x.
                x++;

                // If x is off the board, reset and increment y, but not past 15.
                if (x == 15) {
                    x = 0;
                    y++;
                }

                // It's over when y = 15.
                if (y == 15) {
                    finished = true;
                }

                // Reset the direction.
                direction = WordDirection.RIGHT;
            }
        }

        // Fire the processing event.
        if (wu != null) {
            board.fireBeginProcessingCellEvent(wu.getX(), wu.getY());
        }

        // Return the WorkUnit (or null if it's finished)
        return wu;
    }

    /**
     * Finish processing a WorkUnit.
     * @param wu The WorkUnit that is done being processed.
     */
    protected void finishedWorkUnit(WorkUnit wu) {
        // Fire the processing event.
        board.fireEndProcessingCellEvent(wu.getX(), wu.getY());
    }
    //</editor-fold>
}
