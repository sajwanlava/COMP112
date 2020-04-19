import ecs100.*;
import java.awt.Color;
import java.util.*;
import java.io.*;
import java.io.Serializable;

/**
 *  Scrabble Board
 *  methods:
 *    - constructor
 *    - boolean on(double x, double y) : is the point x,y on the board
 *    - int[] rowCol(double x, double y) : returns the row/col at the point x,y on the board
 *    - Tile pickup(int row, int col) : pick up the tile in the cell at row/col (null if no tile)
 *    - boolean place(Tile tile, int row, int col) : place tile at the cell at row/col on the board
 *                (return true if successful, and false if unsuccessful (no space at row/col)
 *    - boolean validPlay() :  the working tiles constitute a valid play.
 *    - void commit() :  commit all the working tiles
 *    - void draw() :  draw the board
 *    - void reset() : reset the board to initial empty state.
 *    
 *   NOTE: you MUST use these methods for this class so that we can run an automated test
 *   procedure to check your code (A copy of it is given at the end of this file).
 */

public class Board implements Serializable{

    // the board
    private static final int BoardSize = 15;
    private static final int CellSize = Tile.Size;
    private static final int Left = 70;            // The horizontal position of the top left tile on the "board"
    private static final int Top = 70;             // The vertical position of the top row of tiles on the "board"
    private static final int Right = Left + BoardSize* CellSize; 
    private static final int Bot = Top + BoardSize* CellSize; 

    private Tile[][] tiles = new Tile[BoardSize][BoardSize];               // The tiles on the board that are fixed
    private Tile[][] workingTiles = new Tile[BoardSize][BoardSize];        // The tiles on the board that can be moved

    private boolean placed;

    /** Is the position (x,y) on the board */
    public boolean on(double x, double y){
        return (y>=Top && y < Bot  && x>=Left && x < Right);
    }

    /**
     * Return the row/col corresponding to the point x,y.
     */
    public int[] rowCol(double x, double y){
        int row = (int) ((y-Top)/CellSize);
        int col = (int) ((x-Left)/CellSize);
        return new int[]{row, col};
    }

    /*public void save(PrintStream out){

    //StringBuilder builder = new StringBuilder();
    for (int r=0; r<BoardSize; r++){
    for(int c=0; c<BoardSize; c++){
    //  if(workingTiles[r][c] !=null){
    // builder.append(workingTiles[r][c]+"");
    //if(c <workingTiles.length - 1){
    //    builder.append(",");
    //}
    /*if(workingTiles[r][c]!=null){
    if(tiles[r][c] = workingTiles[r][c]){
    out.printf(tiles[r][c] + " ");
    }
    if(workingTiles[r][c] = tile){
    out.printf(workingTiles[r][c] + " ");
    }
    }
    {

    }

    }
    out.printf("\n");
    }
    out.printf("\n");
    out.close();

    }*/

    /**
     * Pickup tile from the board, if the board position contains
     * a working tile and return it.
     */
    public Tile pickup(int row, int col){
        if (row<0 || row >=BoardSize || col<0 || col >=BoardSize) { return null; }
        if (workingTiles[row][col]!=null){
            Tile t = workingTiles[row][col];
            workingTiles[row][col]=null;
            return t;
        }
        return null;
    }

    /**
     * Place the tile on the board, if the board position is empty
     */
    public boolean place(Tile tile, int row, int col){
        if (row<0 || row >=BoardSize || col<0 || col >=BoardSize) { return false; }
        if (tiles[row][col]==null && workingTiles[row][col]==null){
            workingTiles[row][col] = tile;
            return true;
        }
        return false;
    }

    /**
     * Commit all the workingTiles to the board
     */
    public void commit(){
        for (int r=0; r<BoardSize; r++){
            for (int c=0; c<BoardSize; c++){
                if (workingTiles[r][c]!= null){
                    tiles[r][c] = workingTiles[r][c];
                    workingTiles[r][c] = null;
                }
            }
        }
    }

    /**
     * Draw the board.
     * Assumes that the graphics pane has been cleared
     */
    public void draw(){
        int left = Left;
        int top = Top;
        for (int r=0; r<BoardSize; r++){
            for (int c=0; c<BoardSize; c++){
                int x = Left+(c*CellSize);
                int y = Top+(r*CellSize);
                UI.setColor(Color.white);
                UI.fillRect(x, y, CellSize, CellSize);
                if (tiles[r][c] != null){
                    tiles[r][c].draw(x, y);
                }
                else if  (workingTiles[r][c] != null){
                    workingTiles[r][c].draw(x, y);
                }
                UI.setColor(Color.black);
                UI.drawRect(x, y, CellSize, CellSize);
            }
        }
    }

    public void reset(){
        for (int r=0; r<BoardSize; r++){
            for (int c=0; c<BoardSize; c++){
                tiles[r][c] = null;
                workingTiles[r][c] = null;
            }
        }
    }

}
