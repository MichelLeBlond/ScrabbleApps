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
 * This interface allows objects to register to listen for board updates.
 * @author flszen
 */
public interface BoardListener {

    /**
     * This method is called when a letter is updated.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void updatedLetter(int x, int y);

    /**
     * This method is called when a cell is being processed.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void beginProcessingCell(int x, int y);

    /**
     * This method is called when a cell is done being processed.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void endProcessingCell(int x, int y);
}
