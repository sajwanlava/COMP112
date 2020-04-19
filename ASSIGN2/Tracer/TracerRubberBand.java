// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 - 2017T1, Assignment 2
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;
import java.awt.Color;
import javax.swing.JColorChooser;

/** Tracer: the challenge version.
 *  The program displays an image on the screen consisting of a tangle of
 *  microtubules.  
 *  It will enable the user to measure the lengths of the microtubles 
 *   by tracing out polylines (sequences of connected straight line segments)
 *   over the image.
 *  After the user clicks the "Start Line" button, the program will clear all lines,
 *   redisplay the image, and get ready to start a new line
 *  As the user clicks at points on the image, it will build up a "polyline" by
 *   drawing a line from the previous point. For the first point on the line, it
 *   just draws a dot. It constantly shows the line that will be added if the
 *   mouse were clicked at the current position.
 *   It also keeps track of the total length of the line, adding
 *   the length of the new segment to the total on each click.
 *  When the user clicks the "Line Length" button, it will print out the total length of the line.
 *  When the user clicks the "Choose Image" button, it will allow the user to select a different
 *   image, and will restart the line.
 */
public class TracerRubberBand extends Tracer{
    // Fields
    private double x1;  // one end
    private double y1;
    private double x2;  // the other end
    private double y2;

    // Constructor with explicit values
   
    public TracerRubberBand(){
        //CODE DOESNT WORK 
     
    }


    //Returns true if the point (u, v) is on top of the shape
  

    /** Draws the line on the graphics pane.
     */
    public void redraw(){
        
        UI.drawLine(this.x1, this.y1, this.x2, this.y2);
    }

  


    // Main
    public static void main(String[] arguments){
        new TracerRubberBand();
    }   

}
