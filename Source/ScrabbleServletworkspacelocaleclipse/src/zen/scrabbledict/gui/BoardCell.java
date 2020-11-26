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

import zen.scrabbledict.data.Square;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 * This is a cell of the board.
 * @author  flszen
 */
public class BoardCell extends JLabel {

    private static final long serialVersionUID = 1;
    private ImageIcon cellImage;
    private ImageIcon cellImageSelected;
    private static Border normalBorder = BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255));
    private static Border selectedBorder = BorderFactory.createLineBorder(new java.awt.Color(128, 128, 128), 2);
    private static Border previewBorder = BorderFactory.createLineBorder(new java.awt.Color(255, 96, 96), 2);
    private static Border processingBorder = BorderFactory.createLineBorder(new Color(0, 0, 0), 2);
    private static Color normalText = new Color(0, 0, 0);
    private static Color wildText = new Color(255, 64, 0);
    private boolean selected = false;
    private boolean preview = false;
    private boolean processing = false;
    private boolean wild = false;
    private String oldText;
    private CellArrowEnum arrow = CellArrowEnum.NONE;
    private ImageIcon rightArrow = new ImageIcon(getClass().getResource("/zen/scrabbledict/gui/icons/arrow-right.png"));
    private ImageIcon downArrow = new ImageIcon(getClass().getResource("/zen/scrabbledict/gui/icons/arrow-down.png"));
    private Square type;

    /**
     * Gets the selected status of this cell.
     * @return True when this cell is selected.
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selected status of this cell. It is safe to call this even when it is already selected.
     */
    public void setSelected() {
        if (!isSelected()) {
            selected = true;
            buildIcon();
        }
    }

    /**
     * Clears the selected status of this cell. It is safe to call this even when it is not selected.
     */
    public void clearSelected() {
        if (isSelected()) {
            selected = false;
            buildIcon();
        }
    }

    /**
     * Sets the processing status.
     */
    public void setProcessing() {
        if (!processing) {
            setBorder(processingBorder);
            processing = true;
        }
    }

    /**
     * Clears the processing status.
     */
    public void clearProcessing() {
        if (processing) {
            if (selected) {
                setBorder(selectedBorder);
            } else {
                setBorder(normalBorder);
            }
            processing = false;
        }
    }

    /**
     * Sets the wild state.
     */
    public void setWild() {
        if (!wild) {
            setForeground(wildText);
            wild = true;
        }
    }

    /**
     * Clears the wild state.
     */
    public void clearWild() {
        if (wild) {
            setForeground(normalText);
            wild = false;
        }
    }

    /**
     * Instantiates a board cell of the given type.
     * @param type The type of cell this is.
     */
    public BoardCell(Square type) {
        this.type = type;
        initComponents();
        buildIcon();
    }

    private void buildIcon() {
        // Set the border.
        setBorder(normalBorder);

        // Set the background color.
        switch (type) {
            case CENTER:
                cellImage = new javax.swing.ImageIcon(getClass().getResource("/zen/scrabbledict/gui/icons/Center.png"));
                cellImageSelected = new javax.swing.ImageIcon(getClass().getResource("/zen/scrabbledict/gui/icons/CenterSelected.png"));
                break;
            case DOUBLE_LETTER:
                cellImage = new javax.swing.ImageIcon(getClass().getResource("/zen/scrabbledict/gui/icons/LightBlue.png"));
                cellImageSelected = new javax.swing.ImageIcon(getClass().getResource("/zen/scrabbledict/gui/icons/LightBlueSelected.png"));
                break;
            case DOUBLE_WORD:
                cellImage = new javax.swing.ImageIcon(getClass().getResource("/zen/scrabbledict/gui/icons/Pink.png"));
                cellImageSelected = new javax.swing.ImageIcon(getClass().getResource("/zen/scrabbledict/gui/icons/PinkSelected.png"));
                break;
            case NORMAL:
                cellImage = new javax.swing.ImageIcon(getClass().getResource("/zen/scrabbledict/gui/icons/Default.png"));
                cellImageSelected = new javax.swing.ImageIcon(getClass().getResource("/zen/scrabbledict/gui/icons/DefaultSelected.png"));
                break;
            case TRIPLE_LETTER:
                cellImage = new javax.swing.ImageIcon(getClass().getResource("/zen/scrabbledict/gui/icons/DarkBlue.png"));
                cellImageSelected = new javax.swing.ImageIcon(getClass().getResource("/zen/scrabbledict/gui/icons/DarkBlueSelected.png"));
                break;
            case TRIPLE_WORD:
                cellImage = new javax.swing.ImageIcon(getClass().getResource("/zen/scrabbledict/gui/icons/Red.png"));
                cellImageSelected = new javax.swing.ImageIcon(getClass().getResource("/zen/scrabbledict/gui/icons/RedSelected.png"));
                break;
        }

        // Merge in the arrow.
        if (arrow != CellArrowEnum.NONE) {
            // Normal image.
            BufferedImage image = new BufferedImage(25, 25, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = image.createGraphics();
            graphics.drawImage(cellImage.getImage(), 0, 0, this);
            switch (arrow) {
                case DOWN:
                    graphics.drawImage(downArrow.getImage(), 0, 0, this);
                    break;
                case RIGHT:
                    graphics.drawImage(rightArrow.getImage(), 0, 0, this);
                    break;
            }
            graphics.dispose();
            cellImage = new ImageIcon(image);

            // Selected image.
            image = new BufferedImage(25, 25, BufferedImage.TYPE_INT_RGB);
            graphics = image.createGraphics();
            graphics.drawImage(cellImageSelected.getImage(), 0, 0, this);
            switch (arrow) {
                case DOWN:
                    graphics.drawImage(downArrow.getImage(), 0, 0, this);
                    break;
                case RIGHT:
                    graphics.drawImage(rightArrow.getImage(), 0, 0, this);
                    break;
            }
            graphics.dispose();
            cellImageSelected = new ImageIcon(image);
        }

        // Set the border.
        if (selected) {
            setBorder(selectedBorder);
        } else {
            setBorder(normalBorder);
        }

        // Set the icon.
        setIcon(cellImage);
    }

    /**
     * Previews a letter.
     * @param c The character
     */
    public void previewLetter(char c) {
        if (!preview) {
            setBorder(previewBorder);
            oldText = getText();
            setText(String.valueOf(c));
            preview = true;
        }
    }

    /**
     * Clears the previewed letter.
     */
    public void clearPreview() {
        if (preview) {
            setBorder(normalBorder);
            setText(oldText);
            preview = false;
        }
    }

    /**
     * Sets the arrow.
     * @param arrow The CellArrowEnum to use.
     */
    public void setArrow(CellArrowEnum arrow) {
        if (arrow != this.arrow) {
            this.arrow = arrow;
            buildIcon();
        }
    }

    /**
     * Gets the CellArrowEnum for this cell.
     * @return The CellArrowEnum for this cell.
     */
    public CellArrowEnum getArrow() {
        return this.arrow;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setFont(new java.awt.Font("Lucida Grande", 1, 14));
        setForeground(normalText);
        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        setIconTextGap(0);
        setMaximumSize(new java.awt.Dimension(25, 25));
        setMinimumSize(new java.awt.Dimension(25, 25));
        setOpaque(true);
        setPreferredSize(new java.awt.Dimension(25, 25));
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
