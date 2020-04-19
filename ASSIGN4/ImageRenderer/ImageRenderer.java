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

/** Renders pnm images (pbm, pgm, or ppm) onto the graphics panel
ppm images are the simplest possible colour image format.
 */

public class ImageRenderer{
    public static final        int top = 20;   // top edge of the image
    public static final int left = 20;  // left edge of the image
    public static final        int pixelSize = 2;  

    public int temp = 0;
    int Columns;
    int Rows;

    public ImageRenderer(){
        UI.initialise();
        UI.addButton("Render", this::renderAFile);
        UI.addButton("Quit", UI::quit);
    }

    public void renderAFile(){
        UI.clearText();
        UI.clearGraphics();
        String fname = UIFileChooser.open("Image file to render");
        if (fname != null){
            renderImage(fname);
        }
    }

    /**
     * Renders a pnm image file with the given name.
     * Renders the image at position (left, top).
     * Each pixel of the image is rendered by a square of size pixelSize
     * The first three tokens (other than comments) are
     *    the magic number (P1, P2, or P3),
     *    number of columns, (integer)
     *    number of rows,  (integer)
     * ppm and pgm files then have 
     *    colour depth  (integer: range of possible color values)
     * The remaining tokens are the pixel values
     *  (0 or 1 for pbm, single integer for pgm; red, green, and blue integers for ppm)
     * There may be comments anywhere in the file, which start with # and go to the end of the line. Comments should be ignored.
     * The image may be "animated", in which case the file contains a sequence of images
     * (ideally, but not necessarily, the same type and size), which should be rendered
     * in sequence.
     * This method should read the magic number then call the appropriate method for rendering the rest of the image
     */                                
    public void renderImage(String fname){
        /*# YOUR CODE HERE */

        try {
            Scanner scan = new Scanner(new File(fname));
            String Format = scan.next();
            scan.useDelimiter("\\D+"); //skips anything not an int
            Columns = scan.nextInt();     //Assigns the next thing to columns
            Rows = scan.nextInt();        //Assigns the next thing to rows
            int maximum = scan.nextInt();

            if(Format.equals("P2")) {    //If the format is equal to "P3" run the while loops
                int row = 0;
                while(row < Rows) {     
                    int col = 0;            //Starts the column in the "grid" from 0
                    while(col < Columns) {
                        int x = (left + col*pixelSize);     //Calculating how far across the next pixel will start
                        int y = (top + row*pixelSize);      //Calculating how far down the next pixel will start

                        temp = scan.nextInt();
                        int r = this.colorVal(temp)  ;  
                        //UI.println(temp);
                        //temp = scan.nextInt();
                        int g = this.colorVal(temp) ;  
                        // temp = scan.nextInt();
                        int b = this.colorVal(temp) ; 

                        Color color = new Color(r, g, b);           //Creates a colour from a combination of the rgb values
                        UI.setColor(color);                         //Sets the pixel colour to the colour just made
                        UI.fillRect(x, y, pixelSize, pixelSize);    //Fills the rectangle/pixel with the colour just made

                        col++;              //Column = Column + 1 (adding on more to the previous place holder)
                    }
                    row++;                  //Row = Row + 1 (adding on more to the previous place holder)

                }
            }

            if(Format.equals("P3")) {    //If the format is equal to "P3" run the while loops
                int row = 0;
                while(row < Rows) {     
                    int col = 0;            //Starts the column in the "grid" from 0
                    while(col < Columns) {
                        int x = (left + col*pixelSize);     //Calculating how far across the next pixel will start
                        int y = (top + row*pixelSize);      //Calculating how far down the next pixel will start

                        int r = scan.nextInt();               //The red colour value is the next number
                        int g = scan.nextInt();               //The green colour value is the next number
                        int b = scan.nextInt();               //The blue colour value is the next number

                        Color color = new Color(r, g, b);           //Creates a colour from a combination of the rgb values
                        UI.setColor(color);                         //Sets the pixel colour to the colour just made
                        UI.fillRect(x, y, pixelSize, pixelSize);    //Fills the rectangle/pixel with the colour just made

                        col++;              //Column = Column + 1 (adding on more to the previous place holder)
                    }
                    row++;                  //Row = Row + 1 (adding on more to the previous place holder)

                }

            }

        }
        catch (IOException e) {
            UI.println("yo");
        }

        // Scanner scan = new Scanner(fname);

    }

    public int colorVal(int temp){
        int col = 0;
        if(temp<255){
            col = (int)255/10 * temp;
        }
        else{
            col = temp;
        }

        return temp;

    }

    public static void main(String[] args){
        ImageRenderer im = new ImageRenderer();
        im.renderImage("image-bee.ppm");   // this is useful for testing.
    }
} 

