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

/** DodgemGame
 *  Game with two dodgem cars whose steering is controlled by the players
 *  (uses keys: player 1: S/D for left/right;  player 2: K/L for left/right)
 *  Cars run around at a constant speed in an arena with an enclosing wall and
 *  a round obstacle in the of the arena.
 *  If a car hits the wall or the obstacle, then it gets damaged
 *  If the cars collide then they bump apart and their directions are changed
 *  To win the game, a player needs to make the other car crash into the wall
 *  or obstacle enough times.
 *
 *  Controls:
 *  - key to start (space)
 *  - keys to turn the two cars  (s/d  and k/l)
 *    The simplest control is to change the direction of the car directly
 *    when a key is pressed; a better control is to change the direction of
 *    the "steering wheel", which will make the car change direction as it moves
 *
 *  Display:
 *   program constantly shows
 *   - the arena, obstacle, and the cars
 *   - the damage level of each player
 *
 *  Constants:
 *    This class should contain constants specifying the various parameters of
 *    the game, including the geometry of the arena and obstacle.
 *    - Colliding with a wall gives a little bit of damage
 *    - Colliding with the obstacle should give a lot of damage
 *    - Colliding with the other car may give a little damage, but needs to be very low
 *      to allow a player to try to push the other player into the obstacle.
 */

public class DodgemGame{

    // Constants for the Geometry of the game.
    // (You may change or add to these if you wish)

    public static final int ArenaSize = 400;//width/height
    public static final int LeftWall = 30; //x
    public static final int RightWall = 30+ArenaSize;
    public static final int TopWall = 50;//y
    public static final int BotWall = TopWall+ArenaSize;

    public static final int ObstSize = 80;
    public static final int ObstRad = ObstSize/2;
    public static final int ObstX = LeftWall + ArenaSize/2;
    public static final int ObstY = TopWall + ArenaSize/2;

    public static final int DELAY = 20;  // milliseconds to delay each step.

    private static final int size = 30;

    // Fields to store the two cars 
    public DodgemCar car1;
    public DodgemCar car2;

    /** Constructor
     * Set up the GUI,
     * Draw the arena
     */
    public DodgemGame(){
        UI.initialise();
        UI.setImmediateRepaint(false);
        UI.setKeyListener(this::doKey);

        UI.drawRect(LeftWall, TopWall, ArenaSize, ArenaSize);
        UI.setColor(Color.red);
        UI.fillOval(ObstX-ObstRad, ObstY-ObstRad,80,80);
        UI.repaintGraphics();
    }

    // GUI Methods
    /**
     * Respond to keys.
     * the space key should reset the game to have two new cars
     * the s/d/k/l keys should make the appropriate car turn to the left or right
     */
    public void doKey(String key){
        /*# YOUR CODE HERE */
        if(key.equals("Space")){
            this.restartGame();
            UI.println("Working"); //debugging
        }
        else if(key.equals("s")){
            this.car1.turnLeft();
        }
        else if(key.equals("d")){
            this.car1.turnRight();
        }
        else if(key.equals("k")){
            this.car2.turnLeft();
        }
        else if(key.equals("l")){
            this.car2.turnRight();
        }

    }

    /** Run the game
     * Reset the game with two new cars in the starting positions.
     * Loop until one car has run out of life:
     *  - move each car one step,
     *  - call methods on the two cars to check for the different types of collisions
     *    (Core: just with walls; Completion: with obstacle and each other)
     *  - redraw the game (cars, arena, and life status)
     */
    private void restartGame(){
        /*# YOUR CODE HERE */
        this.car1 = null;
        this.car2 = null;

        this.car1 = new DodgemCar(LeftWall+this.size/2, 50+ArenaSize/2-this.size/2, 315);
        this.car2 = new DodgemCar(RightWall-this.size - this.size/2, 50+ArenaSize/2-this.size/2, 135);

        this.redraw();
    }

    /**
     * Redraws
     * - the arena and obstacle
     * - the two cars
     * - the status of the cars  (Completion)
     * Hint: make separate methods for the arena and the status
     * Hint: don't forget to repaint the Graphics pane after redrawing everything.
     */
    private void redraw(){
        /*# YOUR CODE HERE */
        UI.clearGraphics();
        UI.setColor(Color.black);
        UI.drawRect(LeftWall, TopWall, ArenaSize, ArenaSize);
        UI.setColor(Color.red);
        UI.fillOval(ObstX-ObstRad, ObstY-ObstRad,80,80);

        while(true){
            this.car1.draw();
            this.car2.draw();
            UI.sleep(20);
            this.car1.erase();
            this.car2.erase();

            this.car1.move();
            this.car2.move();
            this.car2.checkCollideWall();
            this.car1.checkCollideWall();
           
            UI.repaintGraphics();
        }

    }

    /**
     * Create a new DodgemGame object (which will set up the interface)
     * and then call the run method on it, which will start the game running
     */

    public static void main(String[] arguments){
        new DodgemGame();
    }   

}