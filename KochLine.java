/*
 * KochLine.java
 *
 * We pledge our honor that we have abided by the Stevens Honor System
 * David Kim, Mark Del Rosario
 *
 */
package homework4;

/**
 * KochLine is a class for each line that is drawn 
 * @author davidkim
 *
 */
public class KochLine {
    
    private double x1, x2, y1, y2;		//these are the coordinates for drawing lines
    private KochLine[] kochCurve;		//this array will hold the new set of lines after running the algorithm for drawing Kochcurves
    private boolean doubleToggle;		//false gives no-double sided koch curve while true gives the opposite
    
    
    /**
     * for single-sided koch curve creation
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public KochLine (double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        doubleToggle = false;
    }
    
    /**
     * for double sided koch curve creation
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param doubleToggle
     */
    public KochLine (double x1, double y1, double x2, double y2, boolean doubleToggle) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.doubleToggle = doubleToggle;			//for double sided koch
    }
    
    //in the SnowflakeGUI, we use these getter methods to draw the line, since drawLine takes integers, we round the values
    public int getX1() {
        return (int) (x1+0.5);
    }
    
    public int getY1() {
        return (int) (y1+0.5);
    }
    
    public int getX2() {
        return (int) (x2+0.5);
    }
    
    public int getY2() {
        return (int) (y2+0.5);
    }
    
    /**
     * creates KochCurve
     * @return the array of KochLines to be drawn
     */
    public KochLine[] getKoch() {
    	if (kochCurve == null) {		//if the array is empty, if the iteration has not happened yet
    		if (doubleToggle) 			//if doubleToggle is true, then create double sided koch curve
    			createDoubleKochCurve();
    		else 						//otherwise create single koch curve
    			createKochCurve();
    	}
    	return kochCurve;
    }
    
    public boolean hasIterated() {
    	return kochCurve != null;
    }
    
    public void setDoubleToggle(boolean doubleToggle) {
    	this.doubleToggle = doubleToggle;
    }
    
    
    /**
     * creates single-sided koch curve going outwards
     */
    private void createKochCurve() {
    	kochCurve = new KochLine[4];			//makes space for 4 elements
    	double[] xThirds = splitThirds(x1,x2);	//finds the x coordinates for the partition of the line in thirds
    	double[] yThirds = splitThirds(y1,y2);	//finds the y coordinates for the partition of the line in thirds
    	
    	//gets the altitude of the equilateral triangle coming out of the line
    	double kochAlt = Math.sqrt(3) * Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2)) / 6;	
    	
    	//finds theta so we can use trigonometry to form the koch curve
    	double theta = Math.atan(Math.abs((x2-x1)/(y2-y1)));			
    	
    	//finds the midpoint coordinates for the lines
        double xMid = (x1 + x2)/2.0;
        double yMid = (y1 + y2)/2.0;
        double xKoch, yKoch;
        
        //when there is a vertical line
        if (x2 == x1) {
            if (y1 < y2)				//checks the orientation of the line
            	xKoch = xMid + kochAlt;	//gets the new coordinate for the curve
            else
            	xKoch = xMid - kochAlt;
        } else {
            if (y1 < y2) {				//checks the orientation of the line
                xKoch = xMid + (kochAlt * Math.cos(theta));	//uses trig functions to find new coordinate for the curve
            } else if (y1 > y2) {
                xKoch = xMid - (kochAlt * Math.cos(theta)); //uses trig functions to find new coordinate for the curve
            } else {
                xKoch = xMid;
            }
        }
        //when there is a horizontal line
        if (y2 == y1) {
            if (x1 < x2)				//checks the orientation of the line
            	yKoch =  yMid - kochAlt;
            else
            	yKoch =  yMid + kochAlt;
        } 
        //when there is a slanted line
        else {
            if (x1 < x2) {				//checks the orientation of the line
                yKoch = yMid - (kochAlt * Math.sin(theta));
            } else if (x1 > x2) {		//checks the orientation of the line
                yKoch = yMid + (kochAlt * Math.sin(theta));
            } else {
                yKoch = yMid;
            }
        }
        
        //creates the new four lines, forming the koch curve
        kochCurve[0] = new KochLine(x1, y1, xThirds[0], yThirds[0]);
        kochCurve[1] = new KochLine(xThirds[0], yThirds[0], xKoch, yKoch);
        kochCurve[2] = new KochLine(xKoch, yKoch, xThirds[1], yThirds[1]);
        kochCurve[3] = new KochLine(xThirds[1], yThirds[1], x2, y2);
    }
    
    /**
     * creates double-sided Koch curve
     */
    private void createDoubleKochCurve() {
    	kochCurve = new KochLine[6];			//makes space for 6 elements
    	double[] xThirds = splitThirds(x1,x2);
    	double[] yThirds = splitThirds(y1,y2);
    	double kochAlt = Math.sqrt(3) * Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2)) / 6;
    	double theta = Math.atan(Math.abs((x2-x1)/(y2-y1)));
        double xMid = (x1 + x2)/2.0;
        double yMid = (y1 + y2)/2.0;
        double xKoch1, yKoch1, xKoch2, yKoch2;	//these are for the coordinates of the double-sided koch, the extra side
    	
        // for vertical line
        if (x2 == x1) {
        	xKoch1 = xMid + kochAlt;
        	xKoch2 = xMid - kochAlt;
        } 
        
        else {
        	if (y1 < y2) {			//checks orientation of given line
                xKoch2 = xMid + (kochAlt * Math.cos(theta));
                xKoch1 = xMid - (kochAlt * Math.cos(theta));
            } else if (y1 > y2) {	//checks orientation of given line
                xKoch2 = xMid - (kochAlt * Math.cos(theta));
                xKoch1 = xMid + (kochAlt * Math.cos(theta));
            } else {
                xKoch1 = xMid;
                xKoch2 = xMid;
            }
        }
        
        //for horizontal lines
        if (y2 == y1) {
        	yKoch1 =  yMid - kochAlt;
        	yKoch2 =  yMid + kochAlt;
        } else {
        	if (x1 < x2) {			//checks orientation of given line
                yKoch1 = yMid + (kochAlt * Math.sin(theta));
                yKoch2 = yMid - (kochAlt * Math.sin(theta));
            } else if (x1 > x2) {	//checks orientation of given line
                yKoch1 = yMid - (kochAlt * Math.sin(theta));
                yKoch2 = yMid + (kochAlt * Math.sin(theta));
            } else {
                yKoch1 = yMid;
                yKoch2 = yMid;
            }
        }
        
        // forms the double-sided koch line
        kochCurve[0] = new KochLine(x1, y1, xThirds[0], yThirds[0], true);
        kochCurve[1] = new KochLine(xThirds[0], yThirds[0], xKoch1, yKoch1, true);
        kochCurve[2] = new KochLine(xKoch1, yKoch1, xThirds[1], yThirds[1], true);
        kochCurve[3] = new KochLine(xThirds[0], yThirds[0], xKoch2, yKoch2, true);
        kochCurve[4] = new KochLine(xKoch2, yKoch2, xThirds[1], yThirds[1], true);
        kochCurve[5] = new KochLine(xThirds[1], yThirds[1], x2, y2, true);
    }
    
    /**
     * splits the line into thirds (by coordinates) so that we can create the koch line
     * @param val1 the first coordinate (x or y)
     * @param val2 the second coordinate (x or y)
     * @return	the array of the coordinates that set where the thirds of the line are
     */
    private static double[] splitThirds(double val1, double val2) {
    	double diff = Math.abs(val1-val2);
    	double[] parts = new double[2];			//array will hold the coordinates of where the thirds of the line are
    	
    	if (val1 < val2) {
            parts[0] = val1+(diff/3);
            parts[1] = val1+(diff*2/3);
        } else {
            parts[0] = val1-(diff/3);
            parts[1] = val1-(diff*2/3);
        }
    	return parts;
    }
}
