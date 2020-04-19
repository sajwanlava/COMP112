// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 - 2017T1, Assignment 2
 * Name: Lavanya Sajwan 
 * Username:
 * ID: 300381661
 */

import ecs100.*;
import java.awt.Color;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import java.text.DecimalFormat;
import javax.swing.JFrame;

/** Tracer
 *  The program displays an image on the screen consisting of a tangle of
 *  microtubules.  
 *  It will enable the user to measure the lengths of the microtubles 
 *  by tracing out polylines (sequences of connected straight line segments)
 *  over the image.
 *  After the user clicks the "Start Line" button, the program will clear all lines,
 *   redisplay the image, and get ready to start a new line
 *  As the user clicks on points on the image, it will build up a "polyline" connecting
 *   the points.  For the first point on the line, it just draws a dot.
 *   For each remaining point the user clicks on, the program will draw
 *   a new line segment from the previous point.
 *  It also keeps track of the total length of the line, adding the length of the
 *   new segment to the total on each click.
 *  When the user clicks the "Line Length" button, it will print out the total length of the line.
 *  When the user clicks the "Choose Image" button, it will allow the user to select a different
 *   image, and will restart the line.
 *
 *  You will need
 *  - fields to record the previous point on the polyline, and the length so far.
 *  - a constructor to set up the GUI (including the mouse listener)
 *  - methods to respond to events (buttons and mouse)
 *  - possibly additional "helper" methods.
 */
public class Tracer{
    // Fields
    private String imageName = "image01.jpg";     // the current image that will be displayed
    private String action;
    private double pressedX;
    private double pressedY;
    private boolean start = true;
    private double length;
    private double x;
    private double y;

    DecimalFormat df = new DecimalFormat("#.##");
    // Other fields to record where the previous point on the polyline was.
    // (negative if there was no previous point), and the length of the line

    // Constructor
    /** 
     *  Construct a new Tracer object and set up the GUI
     */
    public Tracer(){
        UI.addButton("Line Length", this::printLength);
        UI.addButton("Start Line", this::startLine);
        UI.addButton("Choose Image", this::chooseImage);
        UI.setMouseListener(this::doMouse);

        UI.setWindowSize(600, 600);
        UI.drawImage(imageName,50, 50, 500, 500);

    }

    /** Respond to mouse actions, particularly to "released" */
    public void doMouse(String action, double x, double y) {
        this.x = x;
        this.y = y;

        if(action.equals("released")){
            if(this.start){
                this.pressedX = this.x;
                this.pressedY = this.y;
                this.start = false;
            }
            else{
                UI.setColor(Color.green);
                UI.drawLine(this.pressedX, this.pressedY, this.x, this.y);
                this.length = length + Math.hypot(pressedX-this.x, pressedY-this.y);
                this.pressedX = this.x;
                this.pressedY = this.y;
            }
        }

    }

    public void printLength(){
        UI.println("The length of the line is, " + df.format(this.length));
    }

    // other methods: you don't have to define this method, but it may be useful
    /**
     * Clear the screen, redisplay the image, and get ready to start a new line.
     * Sets the values of the fields storing the current point to -1
     */
    private void startLine(){
        UI.clearPanes();
        UI.setWindowSize(600, 600);
        UI.drawImage(imageName,50, 50, 500, 500);
        this.length = 0;

    }

    public void chooseImage(){
        /*JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg");
        int result = jFileChooser.showOpenDialog(new JFrame());
        if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = jFileChooser.getSelectedFile();
        }
        else{
        UI.println("ERROR... CHOOSE ANOTHER IMAGE")
        }*/

        imageName = UIFileChooser.open();
        this.startLine();
    }

    // Main
    public static void main(String[] arguments){
        new Tracer();
    }   

}
