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
 * A spartan AbstractBoard.
 * @author flszen
 */
public class BasicBoard extends AbstractBoard {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Instantiates a basic board.
     */
    public BasicBoard() {
    }

    /**
     * Instantiates a BasicBoard by cloning an existing board.
     * Use this constructor to change board types.
     * @param board The board to clone.
     */
    public BasicBoard(AbstractBoard board) {
        // Clone the board's data local.
        for (int x = 0; x < 15; x++) {
            letters[x] = board.letters[x].clone();
            wild[x] = board.wild[x].clone();
        }
    }

    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Methods">
    @Override
    public AbstractBoard clone() {
        BasicBoard newBoard = new BasicBoard();
        for (int x = 0; x < 15; x++) {
            newBoard.letters[x] = letters[x].clone();
            newBoard.wild[x] = wild[x].clone();
        }

        return newBoard;
    }

    @Override
    protected void fireUpdatedLetterEvent(int x, int y) {
    // Nothing to do.
    }
    //</editor-fold>
}
