// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 - 2017T1, Assignment 4
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;
import java.util.*;
import java.io.*;
import java.awt.Color;

/**    
 *  The rack is represented by an array of tiles.
 *  The rack should be displayed with a rectangular border and each tile displayed
 *  by drawing the image of the tile at that place. Empty spaces in the rack 
 *  should be represented by nulls and displayed as empty.
 *  The user can select a position on the rack using the mouse. The selected tile
 *  (or empty space) should be highlighted with a border around it.
 *
 * Suggested methods:
 *   - constructor
 *   - boolean on(double x, double y) : is the point x,y on the rack
 *   - int index(double x, double y) : returns the index of the cell at the point x,y on the rack
 *   - Tile pickup(int pos) : pick up the tile at the index pos (null if no tile)
 *   - boolean place(Tile tile, int pos) : place tile at the index pos on the rack
 *        pushing tiles to the side if necessary. (return true if successful, and false if rack full)
 *   - void fill(Bag bag) :  fill all the space on the rack from the bag
 *   - void draw() :  draw the rack
 *   - void reset() : reset the rack to initial empty state.
 *    
 */

public class Rack implements Serializable{

    //Fields 
    private static final int RackSize = 7;

    private Tile[] tiles = new Tile[RackSize];

    //Constants for layout
    private static final int CellSize = Tile.Size;
    private static final int Left = 200;             // The horizontal position of the leftmost tile on the rack
    private static final int Right = Left +CellSize * RackSize; 
    private static final int Top = 10;               // The vertical position of the tiles on the rack

    /**
     * Reset the rack to be empty
     */
    public void reset(){
        for (int i=0; i<RackSize; i++){
            tiles[i] = null;
        }
    }

    /**
     * Returns true iff (x,y) is on the rack
     */
    public boolean on(double x, double y){
        return (y>=Top && y <= Top+CellSize  && x>=Left && x <= Left+RackSize* CellSize);
    }

    /**
     * Return the index of the cell under the position (x, y)
     */
    public int index(double x, double y){
        return (int) ((x-Left)/CellSize);
    }

    /**
     * Remove and return the tile at the index pos if there is one.
     * Else, return null
     */
    public Tile pickup(int pos){
        if (pos<0 || pos>=RackSize){ return null; }
        if (tiles[pos]==null){ 	return null; }
        Tile t = tiles[pos];
        tiles[pos]=null;
        return t;
    }

    /**
     * Insert the tile at position pos in the rack, if there is room.
     * Puts at position pos if it is currently empty.
     * If there is no room, it returns false.
     */
    public boolean place(Tile tile, int pos){
        if (pos<0 || pos>=RackSize){ return false; }
        // look at the position for a space
        if (tiles[pos] == null){
            tiles[pos] = tile;
            return true;
        }
        return false;
    }

    /**
     * Fill the rack from the given bag of tiles
     */
    public void fill(Bag bag){
        for (int pos=0; pos<RackSize; pos++){
            if (tiles[pos]==null){
                tiles[pos] = bag.pick();
            }
        }
    }

    /**
     * Draw the rack.
     * Assumes the graphics pane has been cleared
     */
    public void draw(){
        UI.setColor(Color.black);
        int left = Left;
        for (int pos=0; pos<RackSize; pos++){
            if (tiles[pos] != null){
                tiles[pos].draw(left, Top);
            }
            UI.drawRect(left, Top, CellSize, CellSize);
            left += CellSize;
        }
    }
}
