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

import java.net.URL;

/**
 * This is a generic container for an Update (used by UpdateChecker).
 * @author flszen
 */
class Update {

    private String updateVersion;
    private URL updateURL;

    /**
     * Instantiates the class.
     * @param updateVersion The version.
     * @param updateURL The URL to send the user to.
     */
    Update(String updateVersion, URL updateURL) {
        this.updateVersion = updateVersion;
        this.updateURL = updateURL;
    }

    /**
     * Gets the update version.
     * @return The version of this update.
     */
    String getUpdateVersion() {
        return updateVersion;
    }

    /**
     * Gets the URL to send the user to.
     * @return The URL to send the user to.
     */
    URL getUpdateURL() {
        return updateURL;
    }
}
