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

import java.awt.Point;
import zen.scrabbledict.data.EnhancedBoard;
import zen.scrabbledict.data.BoardListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;

/**
 * This is a JPanel bean which manages the game board.
 * @author  flszen
 */
public class BoardPanel extends javax.swing.JPanel implements BoardListener {

    private static final long serialVersionUID = 1;
    private BoardCell cells[] = new BoardCell[225];
    private EnhancedBoard board;
    private int selectedCell = -1;
    private boolean preview = false;
    private CellArrowEnum arrow = CellArrowEnum.NONE;
    private Stack<Point> history = new Stack<Point>();

    /**
     * Instantiates the panel.
     * @remark This is only for use in placement in the GUI designer.
     * Use the other constructor!
     */
    public BoardPanel() {
        initComponents();
        add(new javax.swing.JLabel("Remember to instantiate with a Board!"));
    }

    /**
     * Instantiates the panel with a given board object.
     * @param board
     */
    public BoardPanel(EnhancedBoard board) {
        // Localize the board.
        this.board = board;

        // Register as a board listener.
        this.board.addBoardListener(this);

        // Init the panel.
        initComponents();

        // Initialize the cells.
        initCells();
    }

    private void initCells() {
        // Initialize the cells.
        for (int z = 0; z < 225; z++) {
            // Create the cell.
            cells[z] = new BoardCell(board.getSquare(z % 15, z / 15));
            cells[z].setText(String.valueOf(board.getLetter(z % 15, z / 15)));

            // Add the cell to the panel.
            add(cells[z]);

            // Add the mouse listener.
            final int x = z % 15;
            final int y = z / 15;
            cells[z].addMouseListener(new MouseListener() {

                public void mouseClicked(MouseEvent e) {
                    // I don't care.
                }

                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        processClick(x, y);
                    }
                }

                public void mouseReleased(MouseEvent e) {
                    // I don't care.
                }

                public void mouseEntered(MouseEvent e) {
                    // I don't are
                }

                public void mouseExited(MouseEvent e) {
                    // I don't care.
                }
            });
        }

    }

    /**
     * Previews a letter on the board.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param c The character.
     */
    public void previewLetter(int x, int y, char c) {
        cells[x + y * 15].previewLetter(c);
        preview = true;
    }

    /** 
     * Clears the previewed letters.
     */
    public void clearPreview() {
        if (preview) {
            preview = false;

            for (int i = 0; i < 225; i++) {
                cells[i].clearPreview();
            }
        }
    }

    /**
     * Resets this panel to work with a new board.
     * @param board
     */
    public void setBoard(EnhancedBoard board) {
        // Clean up the old board.
        if (this.board != null) {
            this.board.removeBoardListener(this);
        }

        // Relink the board.
        this.board = board;
        this.board.addBoardListener(this);

        // Do the cells.
        this.removeAll();
        initCells();

        // Reset vars.
        selectedCell = -1;
        preview = false;
    }

    private void clearSelected() {
        if (selectedCell != -1) {
            cells[selectedCell].clearSelected();
            cells[selectedCell].setArrow(CellArrowEnum.NONE);
        }
    }

    private void setSelected() {
        if (selectedCell != -1) {
            cells[selectedCell].setSelected();
            cells[selectedCell].setArrow(arrow);
        }
    }

    private void processClick(int x, int y) {
        processClick(x, y, true);
    }

    private void processClick(int x, int y, boolean reset) {
        // Request focus, don't process the click.
        if (!this.hasFocus()) {
            this.requestFocus();
            return;
        }
        
        // Reset the history?
        if (reset) {
            history = new Stack<Point>();
        }

        if (selectedCell == x + y * 15) {
            // Iterate through the arrow.
            switch (arrow) {
                case NONE:
                    arrow = CellArrowEnum.RIGHT;
                    break;
                case RIGHT:
                    arrow = CellArrowEnum.DOWN;
                    break;
                case DOWN:
                    arrow = CellArrowEnum.NONE;
                    break;
            }
            cells[selectedCell].setArrow(arrow);
        } else {
            // Clear any existing selection.
            clearSelected();

            // Set the selected cell.
            selectedCell = x + y * 15;
            setSelected();

            // Request focus, if I don't have it.
            if (!hasFocus()) {
                requestFocus();
            }
        }
    }

    private void advanceCell() {
        int x = selectedCell % 15;
        int y = selectedCell / 15;
        Point current = new Point(x, y);
        boolean success = false;

        switch (arrow) {
            case RIGHT:
                while (x < 14) {
                    x++;
                    if (board.getLetter(x, y) == ' ') {
                        success = true;
                        break;
                    }
                }
                break;
            case DOWN:
                while (y < 14) {
                    y++;
                    if (board.getLetter(x, y) == ' ') {
                        success = true;
                        break;
                    }
                }
                break;
        }

        if (success) {
            history.push(current);
            processClick(x, y, false);
        }
    }

    private void retreatCell() {
        if (!history.empty()) {
            Point last = history.pop();
            board.setLetter(last.x, last.y, ' ');
            processClick(last.x, last.y, false);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMaximumSize(new java.awt.Dimension(375, 375));
        setMinimumSize(new java.awt.Dimension(375, 375));
        setPreferredSize(new java.awt.Dimension(375, 375));
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                formFocusLost(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));
    }// </editor-fold>//GEN-END:initComponents
    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
        // Ensure selection.
        if (selectedCell != -1) {
            // Make sure key is valid.
            if (String.valueOf(evt.getKeyChar()).toUpperCase().matches("^[A-Z]|\\.$")) {
                // Letter or wildcard typed?
                if (evt.getKeyChar() == '.') {
                    if (board.isWild(selectedCell % 15, selectedCell / 15)) {
                        board.clearWild(selectedCell % 15, selectedCell / 15);
                    } else {
                        board.setWild(selectedCell % 15, selectedCell / 15);
                    }
                } else {
                    board.setLetter(selectedCell % 15, selectedCell / 15, String.valueOf(evt.getKeyChar()).toUpperCase().charAt(0));
                    advanceCell();
                }
            } else if (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE || evt.getKeyChar() == KeyEvent.VK_DELETE) {
                // Delete or backspace typed.
                board.setLetter(selectedCell % 15, selectedCell / 15, ' ');
                retreatCell();
            }
        }
    }//GEN-LAST:event_formKeyTyped

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            // Up arrow.
            if (selectedCell / 15 > 0) {
                processClick(selectedCell % 15, selectedCell / 15 - 1);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            // Down arrow.
            if (selectedCell / 15 < 14) {
                processClick(selectedCell % 15, selectedCell / 15 + 1);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            // Left arrow.
            if (selectedCell % 15 > 0) {
                processClick(selectedCell % 15 - 1, selectedCell / 15);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            // Left arrow.
            if (selectedCell % 15 < 14) {
                processClick(selectedCell % 15 + 1, selectedCell / 15);
            }
        }
    }//GEN-LAST:event_formKeyPressed

    private void formFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusLost
        // Clear the selection indicator.
        clearSelected();
    }//GEN-LAST:event_formFocusLost

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // Restore selection indicator.
        setSelected();
    }//GEN-LAST:event_formFocusGained

    public void updatedLetter(int x, int y) {
        // Update the letter.
        cells[x + y * 15].setText(String.valueOf(board.getLetter(x, y)));

        // Set the wild state when appropriate.
        if (board.isWild(x, y)) {
            cells[x + y * 15].setWild();
        } else {
            cells[x + y * 15].clearWild();
        }
    }

    public void beginProcessingCell(int x, int y) {
        cells[x + y * 15].setProcessing();
    }

    public void endProcessingCell(int x, int y) {
        cells[x + y * 15].clearProcessing();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
