// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 Assignment 1  
 * Name:
 * Usercode:
 * ID:
 */

import ecs100.*;
import java.awt.Color;

/**
 * Makes a dirty floor (a grey rectangle), and then makes a robot floor
 *  cleaner run around on the floor to clean it all up.
 * The robot will go forward one unit on each step in its current
 *  direction, and erase the "dirt" (by erasing where it was, moving
 *  one step, and redrawing itself).
 * When it is about to take a step, the program must check that
 *  it won't go over edges of the floor, and change its direction
 *  to a new random direction if it would go over the edge.
 *
 * Hints:
 *   - You can use Math.random() to create a random number (between 0.0 and 1.0)
 *
 *   - If the robot needs to move a distance s in the direction d, then it can
 *     work out the amounts to move in the x and y directions respectively using
 *     basic trigonometry:
 *       s * Math.cos(d * Math.PI/180)   and   s * Math.sin(d * Math.PI/180)
 *     (assuming that d is measured in degrees from 0 to 360)
 *
 *   - Write one method with the main loop, and a separate method for
 *     drawing the floor cleaner. It should show the direction the
 *     floor cleaner is heading in some way. 
 */

public class FloorCleaner{
    // Constants:  The edges of the floor and the robot size
    public static final double Left = 50; //x
    public static final double Right = 550; //width
    public static final double Top = 50;//y
    public static final double Bot = 420; //height
    public static final double Radius = 30;
    public static final double Diam = 2*Radius;

    /** cleanFloor
     *  The main simulation loop,
     *  Draws the floor area
     *  Initialises the position and direction of the floor cleaning robot and draws it
     *  Loops forever
     *    erase the robot from its current position
     *    work out position of one step in the current direction
     *    check if it is hitting a wall or not
     *    then either move it to the new position, or change its direction
     *    redraw
     *    sleep for a short time.
     */
    public void cleanFloor(){
        UI.setColor(Color.gray);
        UI.fillRect(Left,Top,Right,Bot);
        UI.setColor(Color.yellow);
        UI.fillRect(Left+100,Top+120,20,300); //furniture was not going to be a thing
        //boolean initial = true;

        double dir = Math.random()*360;
        double x = Left-Radius+Right/2;
        double y = Top-Radius+Bot/2;
        this.draw(x,y,dir);

        while(true){

            UI.eraseOval(x,y,Diam,Diam);

            x += Math.cos(dir * Math.PI/180); //x component of robot movement
            y += Math.sin(dir * Math.PI/180); //y component of robot movement

            /*if(initial == true){
            UI.eraseOval(x,y,Diam,Diam);
            initial = false;
            }*/

            //when the robot hits the sides
            if(x+10/*+Radius*/>=Right || x/*-Radius*/<=Left) { //had to +10 to the x because robot was going past the left wall - don't know why this was happening
                //UI.eraseOval(x,y,Diam,Diam);
                dir = Math.random()*360; //change the direction
            }
            //when the robot hits top or bottom 
            else if (y+10/*+Radius*/>=Bot || y/*-Radius*/<=Top) { //had to +10 to the y because the robot was sinking - don't know why this was happening 
                //UI.eraseOval(x,y,Diam,Diam);
                dir = Math.random()*360;
            }
            else{
                this.draw(x,y,dir);
                UI.sleep(20); //made sleep larger so movement of the robot was smoother
            }
        }
    }

    /**
     * Draws the robot as a red circle at the specified position and direction
     */
    public void draw(double x, double y, double dir) {
        //insert  code
        UI.setColor(Color.red);
        UI.fillOval(x,y,Diam,Diam);
        UI.setColor(Color.black);
        UI.drawOval(x,y,Diam,Diam);
        UI.setColor(Color.green);
        
        double x1 = x+Radius; //middle
        double x2 = x1 + 10*Math.cos(dir*Math.PI/180); //moving in direction + position of smaller circle
        double y1 = y+Radius; //middle
        double y2 = y1 + 10*Math.sin(dir*Math.PI/180);//moving in direction + position of smaller circle
        
        UI.fillOval(x2-10,y2-10,Radius/2, Radius/2);
        

    }
    // Main
    /** Create a new FloorCleaner object and call cleanFloor.   */
    public static void main(String[] arguments){
        FloorCleaner fc =new FloorCleaner();
        UI.addButton("Quit", UI::quit);
        UI.setDivider(0);
        fc.cleanFloor();
    }        

}