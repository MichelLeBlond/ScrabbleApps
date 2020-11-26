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
package zen.scrabbledict.gui;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import zen.scrabbledict.data.WordMatch;

/**
 * This class is used to store the results in a table.
 * @author flszen
 */
public class ResultsModel extends AbstractTableModel {

    private static final long serialVersionUID = 1;
    private Vector<WordMatch> results = new Vector<WordMatch>();

    /**
     * Constructs a ResultsModel.
     */
    public ResultsModel() {
    }

    /**
     * Sets the model to contain the specified results.
     * @param matches
     */
    public void setResults(Vector<WordMatch> matches) {
        results = matches;
        fireTableDataChanged();
    }

    /**
     * Clears the results from this model.
     */
    public void clearResults() {
        results.clear();
        fireTableDataChanged();
    }

    /**
     * Gets the WordMatch at the specified index.
     * @param index The index to get the WordMatch at.
     * @return The WordMatch.
     */
    public WordMatch getWordMatch(int index) {
        return results.get(index);
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Word";
            case 1:
                return "Score";
            case 2:
                return "Remaining";
            default:
                throw new UnsupportedOperationException("The column at index " + column + " doesn't exist.");
        }
    }

    public int getRowCount() {
        return results.size();
    }

    public int getColumnCount() {
        return 3;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return results.get(rowIndex).getWord();
            case 1:
                return results.get(rowIndex).getScore();
            case 2:
                return results.get(rowIndex).getRackAfter();
            case 3:
                return results.get(rowIndex).getX();
            case 4: 
                return results.get(rowIndex).getY();
            case 5: 
                return results.get(rowIndex).getDirection();
            default:
                throw new UnsupportedOperationException("The column at index " + columnIndex + " doesn't exist.");
        }
    }
}
