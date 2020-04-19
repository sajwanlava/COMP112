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

public class Rack{

    public static final double Left = 0;
    public static final double Top = 0;
    public static final double Right = Left + 40*7;
    public static final double Bot = Top + 40;

    private Tile[] rack;
    private Bag bag;
    public Rack(){
        rack = new Tile[7];
        bag = new Bag();
    }

    public boolean on(double x, double y){
        if(x>=Left && x<Right && y>=Top && y<Bot){
            return true;
        }
        else{
            return false;
        }	
    }

    public int index(double x, double y){
        return  0;

    }

    public Tile pickup(int pos){
        Tile giveTile = rack[pos];
        rack[pos] = null;
        return giveTile;

    }
    
    public boolean place(Tile tile, int pos){
     return false;
    }

    public void fill(Bag bag){
    }

    public void draw(){
        //got help from Fariha Tash for this method
        for(Tile tile: rack){
        UI.setColor(Color.black);
        UI.drawRect(Left-2, Top-2, 7*(40+1)+3, 40+3);
        }
    }

    private void reset(){
        rack=new Tile[7];
    }

}
