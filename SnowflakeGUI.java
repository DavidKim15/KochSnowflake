/*
 * SnowflakeGUI.java
 *
 * We pledge our honor that we have abided by the Stevens Honor System
 * Mark Del Rosario, David Kim
 *
 */
package homework4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.util.ArrayList;

public class SnowflakeGUI implements ActionListener{
	
	private JFrame frame;
	private KochPanel kPanel;
    private JButton clickButton, squareButton, lineButton, triButton, resetButton;
    private JCheckBox drawBox, leaveBox, doubleBox;
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
    	SnowflakeGUI window = new SnowflakeGUI();
    	window.frame.setVisible(true);
    }

    /**
     * Create the application.
     */
    public SnowflakeGUI() {
    	frame = new JFrame("Koch Curve Simulator");
    	kPanel = new KochPanel();
        //panel for first row of buttons
    	JPanel buttonRow1 = new JPanel();
    	//panel for the second row of buttons
        JPanel buttonRow2 = new JPanel();
        //creates the buttons for specific options
        clickButton = new JButton("Iterate");
        squareButton = new JButton("Create Box");
        lineButton = new JButton("Create Line");
        triButton = new JButton("Create Triangle");
        drawBox = new JCheckBox("Add a line");
        resetButton = new JButton("Reset");
        leaveBox = new JCheckBox("Leave lines");
        doubleBox = new JCheckBox("Double koch");
        
        clickButton.addActionListener(this);
        squareButton.addActionListener(this);
        lineButton.addActionListener(this);
        triButton.addActionListener(this);
        drawBox.addActionListener(this);
        resetButton.addActionListener(this);
        doubleBox.addActionListener(this);
        
       //places the buttons in the respective rows 
        buttonRow1.setLayout(new BoxLayout(buttonRow1, BoxLayout.X_AXIS));
        buttonRow2.setLayout(new BoxLayout(buttonRow2, BoxLayout.X_AXIS));
        buttonRow1.add(clickButton);
        buttonRow1.add(Box.createRigidArea(new Dimension(20,0)));
        buttonRow1.add(drawBox);
        buttonRow1.add(Box.createRigidArea(new Dimension(20,0)));
        buttonRow1.add(leaveBox);
        buttonRow1.add(Box.createRigidArea(new Dimension(20,0)));
        buttonRow1.add(doubleBox);
        buttonRow1.add(Box.createRigidArea(new Dimension(20,0)));
        buttonRow1.add(resetButton);
        buttonRow2.add(squareButton);
        buttonRow2.add(Box.createRigidArea(new Dimension(20,0)));
        buttonRow2.add(lineButton);
        buttonRow2.add(Box.createRigidArea(new Dimension(20,0)));
        buttonRow2.add(triButton);
        
        kPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setResizable(false);
        frame.add(kPanel);
        frame.add(Box.createRigidArea(new Dimension(0, 10)));
        frame.add(buttonRow1);
        frame.add(Box.createRigidArea(new Dimension(0, 10)));
        frame.add(buttonRow2);
        frame.add(Box.createRigidArea(new Dimension(0, 20)));
        frame.pack();
    }

	@Override
	/**
	 * Handle button events
	 */
	public void actionPerformed(ActionEvent e) {
		int paneWidth = kPanel.getWidth();
		int paneHeight = kPanel.getHeight();
		try {
			if (e.getSource() == drawBox) {
				kPanel.toggleDraw();
			} else if (e.getSource() == resetButton) {
				kPanel.emptyOut();
			} else if (e.getSource() == doubleBox) {
				kPanel.toggleDouble();
			} else if (kPanel.isFull()) {
				// Checks if the panel is full before drawing more lines
				throw new KochOverflowException();
			} else if (e.getSource() == clickButton) {
				kPanel.iterate(leaveBox.isSelected());
			} else if (e.getSource() == squareButton) {
				// Creates a box of width/height 3/4 of the drawing panel
				kPanel.addLine(new KochLine(paneWidth/4,paneHeight/4,paneWidth*3/4,paneHeight/4));
				kPanel.addLine(new KochLine(paneWidth/4,paneHeight*3/4,paneWidth/4,paneHeight/4));
				kPanel.addLine(new KochLine(paneWidth*3/4,paneHeight*3/4,paneWidth/4,paneHeight*3/4));
				kPanel.addLine(new KochLine(paneWidth*3/4,paneHeight/4,paneWidth*3/4,paneHeight*3/4));
			} else if (e.getSource() == lineButton) {
				// Creates a line 3/5 the width of the drawing panel
				kPanel.addLine(new KochLine(paneWidth/5, paneHeight/2, 4*paneWidth/5, paneHeight/2));
			} else if (e.getSource() == triButton) {
				// Creates an equilateral triangle with base 3/5 the width of the drawing panel
				int base = 3 * paneWidth / 5;
				int height = (int) ((Math.sqrt(3) * base / 2.0) + 0.5);
				kPanel.addLine(new KochLine(paneWidth*4/5, paneHeight*3/4,paneWidth/5, paneHeight*3/4));
				kPanel.addLine(new KochLine(paneWidth/5, paneHeight*3/4, paneWidth/2, (paneHeight*3/4)-height));
				kPanel.addLine(new KochLine(paneWidth/2, (paneHeight*3/4)-height, paneWidth*4/5, paneHeight*3/4));
			}
			kPanel.repaint();
		} catch (KochOverflowException ex) {
			JOptionPane.showMessageDialog(null,
					"There are too many lines!\nPlease reset before adding more",
					"Too many lines!", JOptionPane.ERROR_MESSAGE);
		}
	}
}