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
 * The WorkUnit is a description of a unit of work that needs to be performed.
 * @author flszen
 */
class WorkUnit {
    //<editor-fold desc="Fields" defaultstate="collapsed">
    private int x;
    private int y;
    private WordDirection direction;
    //</editor-fold>
    //<editor-fold desc="Constructors" defaultstate="collapsed">
    /**
     * Constructs a Scanner. This is used for processing.
     * @param x The X value.
     * @param y The Y value.
     * @param direction The WordDirection.
     */
    protected WorkUnit(int x, int y, WordDirection direction) {
        setX(x);
        setY(y);
        setDirection(direction);
    }
    //</editor-fold>
    //<editor-fold desc="Methods" defaultstate="collapsed">
    /**
     * Sets X.
     * @param x The X value.
     */
    protected void setX(int x) {
        this.x = x;
    }

    /**
     * Get X.
     * @return The X value.
     */
    protected int getX() {
        return this.x;
    }

    /**
     * Sets Y.
     * @param y The Y value.
     */
    protected void setY(int y) {
        this.y = y;
    }

    /**
     * Get Y.
     * @return The Y value.
     */
    protected int getY() {
        return this.y;
    }

    /**
     * Sets the WordDirection.
     * @param direction The WordDirection to be set.
     */
    protected void setDirection(WordDirection direction) {
        this.direction = direction;
    }

    /**
     * Gets the WordDirection.
     * @return The WordDirection that has been set.
     */
    protected WordDirection getDirection() {
        return this.direction;
    }
    //</editor-fold>
}