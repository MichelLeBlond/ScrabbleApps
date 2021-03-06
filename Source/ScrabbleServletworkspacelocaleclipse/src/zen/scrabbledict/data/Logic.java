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

import java.util.Vector;

/**
 * The Logic class provides the game logic to the GUI.
 * @author flszen
 */
public  class Logic extends Thread implements Runnable {
    // <editor-fold defaultstate="collapsed" desc="Fields">
    private String rack;
    private EnhancedBoard board;
    private Dictionary dict;
    private LogicListener listener;
    private Vector<WordMatch> results;
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Constructs the Logic thread.
     * @param rack The rack.
     * @param board The board.
     * @param dictionary The dictionary.
     * @param listener The LogicListerner that is notified of completion.
     */
      public Logic(String rack, EnhancedBoard board, Dictionary dictionary, LogicListener listener) {
        this.rack = rack.toUpperCase();
        this.board = board;
        dict = dictionary;
        this.listener = listener;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Methods">
    @Override
    public void run() {
        // Create the ScannerProvider.
        ScannerProvider sp = new ScannerProvider(board);

        // Create the threads.
        Vector<Thread> threads = new Vector<Thread>();
        for (int z = 0; z < Runtime.getRuntime().availableProcessors(); z++) {
            Scanner s = new Scanner(sp, board, dict, rack);
            Thread t = new Thread(s);
            threads.add(t);
            t.start();
        }

        // Wait until the threads finish.
        for (int x = 0; x < threads.size(); x++) {
            while (threads.get(x).isAlive()) {
                try {
                    sleep(250);
                } catch (InterruptedException ex) {
                    System.err.println("Sleep interrupted: " + ex);
                }
            }
        }

        // Compile the results.
        //results = logicRight.getResults();
        //results.addAll(logicDown.getResults());
        results = sp.getMatches();

        // Notify the listener of completion.        
        if (listener != null) {
            listener.processingComplete();
        }
    }

    /**
     * Gets the results vector.
     * @return The results vector.
     */
    public Vector<WordMatch> getResults() {
        return results;
    }
    //</editor-fold>
}
