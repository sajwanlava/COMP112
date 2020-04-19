// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 - 2017T1, Assignment 3
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;
import java.util.*;
import java.io.*;
import java.awt.Color;

/** Lets a user play a solitaire game of Scrabble.
 *  Scrabble is a word game in which players take turns at adding words to
 *  a crossword by placing tiles on a board. Each tile has a letter and a score.
 *
 *  There are 100 tiles in the whole game, which start off in a bag.
 *
 *  The player has a rack which holds seven tiles.
 *  The player can rearrange tiles on their rack while they try to construct words;
 *  they can move  tiles from their rack onto the board to make a word, and can move them
 *  around on the board or back to the rack, until they commit to their word.
 *  The rack is then refilled with random tiles from the bag to replace the ones they put out.
 *  They can't move any tiles that have been committed.
 *
 *  The user can drag tiles around with the mouse - "pressing" on top of a tile will pick it up
 *  (unless the user is already "holding" a tile), and "releasing" on the rack or on an empty
 *  space on the board will put the currently held tile at that place.  Attempting to place
 *  the tile on a full rack will not succeed. Placing the tile on top of a tile on the rack which
 *  has some space will push the tiles to the side to make space for it.
 *
 *
 *  PROGRAM DESIGN
 *  Each tile has a name, and a score.  It also contains the name of the image file for drawing
 *  the tile.  All the images are square, and have a size of 40 pixels.
 *
 *  The program has two arrays and an ArrayList to keep track of the tiles:
 *    bag:   an arrayList of the remaining tiles.
 *    rack:  an array of up to 7 tiles. Nulls represent cells with no tile .
 *    board: a 15x15 array of cells where the tiles can be placed.
 *
 *  The game is displayed with the board at the top, and the rack above.
 *  When the player has picked up a tile to move it, but hasn't placed it yet,
 *  the tile is shown in the top left corner.
 *
 *  There is a Restart button, which will restart the game with an empty board, a refilled bag,
 *  and a rack with 7 tiles taken from the bag.
 *
 *  There is a "Commit" button, which should check that the play is valid, and then
 *  commit all the tiles on the board that haven't yet been committed,
 *  and will refill the rack from the bag (as long as there are tiles in the bag).
 *
 *
 */

public class ScrabbleSolitaire{

    // fields to hold the bag, rack and board, and the tile in the hand
    private Board board;
    private Rack rack;
    private Bag bag;
    private Tile hold;

    /**
     * Set up the GUI (buttons and mouse listener) and restart the game
     */
    public ScrabbleSolitaire(){
        /*# YOUR CODE HERE */
        UI.initialise();
        UI.addButton("RESTART", this::restart);
        UI.addButton("COMMIT", this::commit);
        UI.setMouseListener(this::doMouse);
        restart();
        board = new Board();
        rack = new Rack();
        bag = new Bag();

    }

    /**
     * User can drag a tile from the board or the rack to a space on the board or the rack.
     * The user can also drag a tile from the board or rack to a full place on the rack if the
     *    tiles on the rack could be shifted up or down to make space for it.
     */
    public void doMouse(String action, double x, double y){
        
    }
    /**
     * Is the current board a valid play?
     */
    public void isValid(){
        /*# YOUR CODE HERE */

    }

    /**
     * Commit to the current play
     */
    public void commit(){
        /*# YOUR CODE HERE */
        board.commit();
        rack.fill(bag);
        draw();
    }

    /** Restart the game:
     * set the board to be empty,
     * refill the bag to contain all the tiles
     * reset the rack and fill it
     */
    public void restart(){
        /*# YOUR CODE HERE */

    }

    /**
     * Draw the board, rack, and hand
     */
    public void draw(){
        UI.clearGraphics();
        /*# YOUR CODE HERE */

    }

    public static void main(String[] args){
        ScrabbleSolitaire obj = new ScrabbleSolitaire();
    }

}
