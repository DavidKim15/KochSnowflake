/*
 * KochPanel.java
 *
 * We pledge our honor that we have abided by the Stevens Honor System
 * Mark Del Rosario, David Kim
 *
 */
package homework4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * JComponent which is able to simulate iterations of the koch curve
 * 
 * @version 1.0
 * @since 20161201
 * @author Mark Del Rosario, David Kim
 *
 */
public class KochPanel extends JComponent implements MouseListener, MouseMotionListener {

    public final static int DRAW_WIDTH = 500;
    public final static int DRAW_HEIGHT = 500;
    public final static int LINE_LIMIT = 1000000;
    private final static Color BACKGROUND_COLOR = Color.WHITE;
    private final static Color LINE_COLOR = Color.BLACK;
    
    private ArrayList<KochLine> lineArray;
    private boolean drawToggle;    // true if able to draw
    private boolean drawState;     // true if currently drawing
    private boolean doubleToggle;
    private int startX, startY, endX, endY;
    
    /**
     * Initializes a panel to create koch curves
     */
    public KochPanel() {
        lineArray = new ArrayList<KochLine>();    
        drawToggle = false;
        drawState = false;
        addMouseListener(this);
        addMouseMotionListener(this);
        doubleToggle = false;
    }
    
    /**
     * Adds the given KochLine to the panel.
     * Throws an exception when amount of lines exceeds limit.
     * Repaint is expected to be called by containing element.
     * 
     * @param newLine the line to be added
     * @throws KochOverflowException thrown when adding past LINE_LIMIT
     */
    public void addLine(KochLine newLine) throws KochOverflowException {
        if (lineArray.size() <= LINE_LIMIT) {
            newLine.setDoubleToggle(doubleToggle);
            lineArray.add(newLine);
        } else
            throw new KochOverflowException();
    }
    
    /**
     * Adds each KochLine in the array to the panel.
     * Throws an exception when amount of lines exceeds limit.
     * Repaint is expected to be called by containing element.
     * 
     * @param newLines array of lines to be added
     * @throws KochOverflowException thrown when adding past LINE_LIMIT
     */
    public void addLines(KochLine[] newLines) throws KochOverflowException {
        for (KochLine newLine : newLines)
            addLine(newLine);
    }
    
    /**
     * Returns ArrayList containing each KochLine
     * 
     * @return ArrayList of every KochLine
     */
    public ArrayList<KochLine> getLines() {
        return lineArray;
    }
    
    /**
     * Deletes all lines in the panel.
     * Repaint is expected to be called by containing element.
     */
    public void emptyOut() {
        lineArray = new ArrayList<KochLine>();
    }
    
    /**
     * Toggles the ability to draw a line on the panel
     */
    public void toggleDraw() {
        drawToggle = (drawToggle) ? false : true;                
    }
    
    /**
     * Toggles whether or not to iterate the koch curve on both sides 
     * of the line.
     * Default is false.
     */
    public void toggleDouble() {
        doubleToggle = (doubleToggle) ? false : true;
        for (KochLine line : lineArray)
            line.setDoubleToggle(doubleToggle);
    }
    
    /**
     * Checks if the panel is at carrying capacity
     * 
     * @return true if panel is at or over set maximum capacity
     */
    public boolean isFull() {
        return lineArray.size() >= LINE_LIMIT;
    }
    
    /**
     * Iterates each line using the KochCurve algorithm.
     * Boolean parameter to leave behind original lines.
     * Throws an exception is panel is at carrying capacity.
     * 
     * @param leaveOldLines true to leave old lines
     * @throws KochOverflowException
     */
    public void iterate(boolean leaveOldLines) throws KochOverflowException{
        if (leaveOldLines) {
            int originalSize = lineArray.size();
            for (int i = 0; i < originalSize; i++) {
                KochLine line = lineArray.get(i);
                if (!line.hasIterated())
                    addLines(line.getKoch());
            }
        } else {
            ArrayList<KochLine> oldLineArray = lineArray;
            emptyOut();
            for (KochLine oldLine : oldLineArray) {
                addLines(oldLine.getKoch());
            }
        }
    }
    
    @Override
    /**
     * Sets the size of the panel
     */
    public Dimension getPreferredSize() {
        return new Dimension(DRAW_WIDTH,DRAW_HEIGHT);
    }

    @Override
    /**
     * Draws each line stored. 
     * If a line is being inputted by the user, draws it's current state
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, DRAW_WIDTH, DRAW_HEIGHT);
        g.setColor(LINE_COLOR);
        for (KochLine line: lineArray) {
            g.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
        }
        if (drawState) {
            g.drawLine(startX, startY, endX, endY);
        }
    }  
    
    @Override
    /**
     * Sets the starting values for a new line where clicked.
     * Only active if drawToggle == true
     */
    public void mousePressed(MouseEvent e) {
        if (drawToggle) {
            startX = e.getX();
            startY = e.getY();
            endX = e.getX();
            endY = e.getY();
            drawState = true;
        }
    }

    @Override
    /**
     * Creates new KochLine with the given start and end coordinates, 
     * then repaints.
     * Only active if drawToggle == true
     */
    public void mouseReleased(MouseEvent e) {
        if (drawToggle) {
            lineArray.add(new KochLine(startX, startY, endX, endY,doubleToggle));
            repaint();
            drawState = false;
        }
    }

    @Override
    /**
     * Redefines the end point values of the line currently being drawn,
     * then repaints.
     * Only active if drawToggle == true
     */
    public void mouseDragged(MouseEvent e) {
        if (drawToggle) {
            endX = e.getX();
            endY = e.getY();
            repaint();
        }
    }

    @Override
    /**
     * Unused
     */
    public void mouseClicked(MouseEvent e) {}

    @Override
    /**
     * Unused
     */
    public void mouseMoved(MouseEvent e) {}
    
    @Override
    /**
     * Unused
     */
    public void mouseEntered(MouseEvent e) {}

    @Override
    /**
     * Unused
     */
    public void mouseExited(MouseEvent e) {}
}