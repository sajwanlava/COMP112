// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP112 - 2017T1, Assignment 5
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;
import java.util.*;
import java.io.*;
import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.JColorChooser;

/** ImageProcessor allows the user to load, display, modify, and save an image in a number of ways.
 *  The program includes
 * CORE:
 *  - Load, commit, save
 *  - Brightness adjustment
 *  - Horizontal and vertical flips and 90 degree rotations (clockwise and anticlockwise)
 *  - Merge 
 *
 * COMPLETION
 *  - Crop&Zoom
 *  - Blur (3x3 filter)
 *  - Rotate arbitrary angle
 *
 * CHALLENGE
 *  - General Convolution Filter
 *  - Pour (spread-fill)
 *  - Red-eye
 */
public class ImageProcessor {
    private Color[][] image =   null; // current version of the image
    private Color[][] working = null; // temporary image
    private Color[][] toMerge = null;

    // current selected region (rows, and columns of the image)
    private int regionLeft=-1;
    private int regionTop=-1;
    private int regionWidth;
    private int regionHeight;

    private Color paint = Color.red;

    private String mouseAction = "none";   // what should the mouse do?

    private static final double LEFT = 10;  // position of working .
    private static final double TOP = 10;
    private static final double MARGIN = 10; // space between working and image

    //-------------------------------------------------------
    // Methods for the image operations
    //-------------------------------------------------------

    /**
     * CORE
     * 
     * Make the image brighter or darker.
     *  The value is between -1.0 and 1.0
     *  Sets the fraction to move the color towards the min max
     */
    public void brightness(float value){
        this.checkWorking();

        int maximumValue = 255;
        if (value< 0) {
            maximumValue = 0;
        }
        for (int row = 0; row < rows(image); row ++) {
            for (int column = 0; column < cols(image); column ++) {
                int r= newCol(image[row][column].getRed(), maximumValue, value);
                int g= newCol(image[row][column].getGreen(), maximumValue, value);
                int b = newCol(image[row][column].getBlue(), maximumValue, value);
                working[row][column] = new Color(r,g, b); //input the new calculated r,g,b values into the picture
            }
        }
    }

    //calculates how much you need to change to get the new colour brightness
    private int newCol(int oldColor, int maximumValue, float val) {
        int range = Math.abs(maximumValue - oldColor); 
        float add = val * range; 
        int col = oldColor + (int)add; //color
        if(col>255){
            col=255;
        }
        else if(col<0){
            col = 0;
        }
        return col;
    }

    /**
     * CORE
     * 
     * Flip the image horizontally
     */
    public void horizontalFlip(){
        this.checkWorking();

        working = new Color[image.length][image[0].length];

        for(int x=0; x<image.length; x++){
            for(int y=0; y<image[x].length; y++){
                working[x][y] = image[image.length-1-x][y];
            }
        }

    }

    /**
     * CORE
     * 
     * Flip the image vertically
     */
    public void verticalFlip(){
        this.checkWorking();

        working = new Color[image.length][image[0].length];
        for(int x=0; x<image.length; x++){
            for(int y=0; y<image[x].length; y++){
                working[x][y] = image[x][image[x].length -1 - y];
            }
        }
    }

    /**
     * CORE
     * 
     * Rotate the image 90 degrees clockwise
     */
    public void rotate90clockwise(){
        /*# YOUR CODE HERE 

        int rows = rows(image);
        int cols = cols(image);
        working = new Color[cols][rows];
        for(int row=0; row<rows; row++){
        for(int col=0; col<cols; col++){
        working[rows-row-1][col]= image[row][col];
        }

        }*/
        working= new Color[image[0].length][image.length];

        //int col = 0;
        for(int x = 0; x < image[0].length; x++){
            for(int y = 0; y<image.length; y++){
                working[x][y] = this.image[image.length-1-y][x];
            }
            //col++;
        }
        //this.image = tempArray;

    }

    /**
     * CORE
     * 
     * Rotate the image 90 degrees anticlockwise
     */
    public void rotate90anticlockwise(){
        /*# YOUR CODE HERE */
        //got help from priyal
        working = new Color[image[0].length][image.length];
        for(int x=0;x<image.length;x++){
            for(int y=0;y<image[x].length;y++){
                working[working.length-y-1][x]=image[x][y];
            }
        }
    }

    /** 
     * CORE
     *
     * Merges the current image and the toMerge image, if there is one.
     * Work out the rows and columns shared by the images
     * For each pixel value in the shared region, replace the current pixel value
     * by the average of the pixel value in current image and the corresponding
     * pixel value in the other image.
     */
    public void merge(float factor){

        //DOESN'T WORK
        UI.println("merging: "+factor);
        if (toMerge==null){
            UI.println("no image to merge with");
            return;
        }
        /*# YOUR CODE HERE */
        String fname = UIFileChooser.open();
        try{
            BufferedImage img = ImageIO.read(new File(fname));
            int rows = img.getHeight();     
            int cols = img.getWidth();

            Color[][] other = new Color[cols][rows];

            for (int x=0; x<rows; x++){
                for (int y=0; y<cols; y++) {
                    Color c = new Color(img.getRGB(x,y)* (int)factor);
                    other[x][y]= c;
                }
            }	

        }catch (IOException e){
            UI.println("File read error " + e);
        }
    }

    /**
     * CORE
     *
     * Write the current image to a file
     */
    public  void saveImage() {
        /*# YOUR CODE HERE */
        int rows = working.length;
        int cols = working[0].length;
        BufferedImage img = new BufferedImage(cols,rows, BufferedImage.TYPE_INT_RGB);

        for(int row = 0; row<rows; row++){
            for(int col=0; col<cols; col++){
                Color c = this.working[row][col];
                img.setRGB(col,row, c.getRGB());
            }
        }
        try{
            String fname = UIFileChooser.save("save to png image file");
            if(fname==null)return;
            File imageFile = new File(fname);
            ImageIO.write(img, "png", new File(fname));
        }catch(IOException e){UI.println("Image reading failed: "+e);}

    }

    /**
     * COMPLETION
     *
     * Scales the currently selected region of the image (if there is one) to fill
     * the working image.
     * This is a combination scale, translate, and crop.
     */
    public void cropAndZoom(){
        this.checkWorking();

        // requires that a region has been selected.
        /*# YOUR CODE HERE */

    }

    /** 
     * COMPLETION
     *
     * CONVOLVE  Matrix   
     *   Modify each pixel to make it a weighted average of itself and the pixels around it
     *   A simple blur will weight the pixel by 0.4, its horizontal and vertical neighbours by 0.1, 
     *   and the diagonal neighbours by 0.05.
     * Hint: It is easier to make a new image array of the same size as the image,
     *       then work out the weighted averages in the new array and then assign the new array to the image field.
     */

    public void convolve(float[][] weights){   
        /*# YOUR CODE HERE */

    }

    /**
     * COMPLETION
     *
     * Rotate the image by the specified angle.
     * Rotates around the center of the image, or around the center
     * of the selected region if there is a selected region.
     */
    public void rotate(double angle){
        this.checkWorking();
        /*# YOUR CODE HERE */

    }

    //-------------------------------------------------------
    // Constructor, GUI setup and GUI methods  
    //-------------------------------------------------------

    /** Construct a new ImageProcessor object
     * and set up the GUI
     */
    public ImageProcessor(){
        UI.setMouseMotionListener(this::doMouse);
        UI.addButton("Load", this::doLoad);
        UI.addButton("Save", this::buttonSave);
        UI.addButton("Commit", this::buttonCommit);
        UI.addSlider("Brightness", -100, 100, 0, this::sliderBrightness);
        UI.addButton("Horizontal Flip", this::buttonHorizontalFlip);
        UI.addButton("Vertical Flip", this::buttonVerticalFlip);
        UI.addButton("Rotate 90 Clockwise", this::buttonRotate90clockwise);
        UI.addButton("Rotate 90 Anticlockwise", this::buttonRotate90anticlockwise);
        UI.addButton("Load Merge", this::buttonLoadMerge);
        UI.addSlider("Merge level", 0, 100, 50, this::sliderMerge);

        UI.addButton("Crop&Zoom", this::buttonCropZoom);
        UI.addButton("Blur", this::buttonBlur);
        UI.addSlider("Rotate", -180, 180, 0, this::sliderRotate);

    }

    /** Respond to button presses */
    public void buttonSave(){
        if (this.image == null) {
            UI.printMessage("Nothing to save");
            return;
        }
        this.mouseAction="none";  // reset the current mouse action
        this.saveImage();
        this.redisplay();
    }

    public void buttonCommit(){
        if (this.working == null) {
            UI.printMessage("Nothing to commit");
            return;
        }
        this.mouseAction="none";  // reset the current mouse action
        this.image = copyImage(this.working); 
        this.redisplay();
    }

    /** Respond to sliders changes */
    public void sliderBrightness(double num) {
        if (this.image == null) {
            UI.printMessage("No image");
            return;
        }
        this.brightness((float)num/100);
        this.redisplay();
    }

    public void buttonHorizontalFlip(){
        if (this.image == null) {
            UI.printMessage("No image");
            return;
        }
        this.mouseAction="none";  // reset the current mouse action
        this.horizontalFlip(); 
        this.redisplay();
    }

    public void buttonVerticalFlip(){
        if (this.image == null) {
            UI.printMessage("No image");
            return;
        }
        this.mouseAction="none";  // reset the current mouse action
        this.verticalFlip(); 
        this.redisplay();
    }

    public void buttonRotate90clockwise(){
        if (this.image == null) {
            UI.printMessage("No image");
            return;
        }
        this.mouseAction="none";  // reset the current mouse action
        this.rotate90clockwise();
        this.redisplay();
    }

    public void buttonRotate90anticlockwise(){
        if (this.image == null) {
            UI.printMessage("No image");
            return;
        }
        this.mouseAction="none";  // reset the current mouse action
        this.rotate90anticlockwise();
        this.redisplay();
    }

    public void buttonLoadMerge(){
        if (this.image == null) {
            UI.printMessage("No image");
            return;
        }
        this.mouseAction="none";  // reset the current mouse action
        this.toMerge = this.loadImage(UIFileChooser.open("Image to merge"));
        this.merge(0.5f);
        this.redisplay();
    }

    public void sliderMerge(double num) {
        if (this.image == null) {
            UI.printMessage("No image");
            return;
        }
        this.merge((float)num/100);
        this.redisplay();
    }

    public void buttonCropZoom(){
        if (this.image == null) {
            UI.printMessage("No image");
            return;
        }
        this.mouseAction="none";  // reset the current mouse action
        this.cropAndZoom(); 
        this.redisplay();
    }

    public void buttonBlur(){
        if (this.image == null) {
            UI.printMessage("No image");
            return;
        }
        this.mouseAction="none";  // reset the current mouse action
        //this.convolve(blurWeights);
        this.redisplay();
    }

    public void sliderRotate(double num) {
        if (this.image == null) {
            UI.printMessage("No image");
            return;
        }
        this.rotate(num);
        this.redisplay();
    }

    /**
     * Respond to mouse events "pressed", "released"".
     * If mouseAction field is "none", then pressed and released set the region
     * (can be on either of the working or the image).
     * If mouseAction field is "pour", then released will pour the current paint
     *  at the point.
     */
    public void doMouse(String action, double x, double y) {
        if (action.equals("pressed")){
            if (mouseAction=="none"){
                int[] rowCol = getRowColAtMouse(x, y);
                if (rowCol!=null) {
                    this.regionTop = rowCol[0];
                    this.regionLeft = rowCol[1];
                }
                else{
                    this.regionLeft = -1;
                }
            }
        }
        else if (action.equals("released")){
            if (mouseAction=="none"){
                int[] rowCol = getRowColAtMouse(x, y);
                if (rowCol!=null && this.regionLeft>-1) {
                    this.regionHeight = Math.abs(rowCol[0]-this.regionTop);
                    if (rowCol[0] < this.regionTop){this.regionTop = rowCol[0];}

                    int regionRight = rowCol[1];
                    this.regionWidth = Math.abs(regionRight-this.regionLeft);
                    if (regionRight < this.regionLeft){this.regionLeft = regionRight;}
                }
                else {this.regionLeft = -1;}
                this.redisplay();
            }

        }
    }

    //-------------------------------------------------------
    // UTILITY METHODS: load, save, copy clear, check,  computing rows/cols
    //-------------------------------------------------------

    /**
     * Returns the number of rows in an image
     */
    public int rows(Color[][] array){return array.length;}

    /**
     * Returns the number of columns in an image
     */
    public int cols(Color[][] array){return array[0].length;}

    /**
     * Returns the row and column of the image that the point (x, y) is on.
     * Note that the user may be clicking on the working image or the original image.
     */
    private int[] getRowColAtMouse(double x, double y){
        if (y<TOP || y >= TOP+this.rows(this.image)){return null;}
        int row = (int)(y-TOP);

        if (x>=LEFT && x<LEFT+this.cols(this.working)){
            return new int[]{row, (int)(x-LEFT)};
        }
        double imageLeft = LEFT+this.cols(this.working)+MARGIN;
        if (x>=imageLeft && x<imageLeft+this.cols(this.image)){
            return new int[]{row, (int)(x-imageLeft)};
        }
        return null;
    }

    /**
     * Loads an image from a file into both the current image and the working image
     */
    public void doLoad(){
        String fname = UIFileChooser.open();
        this.image = loadImage(fname);
        this.working = copyImage(this.image);
        this.redisplay();
    }

    /**
     * Load image from a file and return as a two-dimensional array of Color.
     */
    public Color[][] loadImage(String imageName) {
        Trace.println("loading " + imageName);
        if (imageName==null || !new File(imageName).exists()){ return null; }
        try {
            BufferedImage img = ImageIO.read(new File(imageName));
            int rows = img.getHeight();
            int cols = img.getWidth();
            Color[][] ans = new Color[rows][cols];
            for (int row = 0; row < rows; row++){
                for (int col = 0; col < cols; col++){                 
                    Color c = new Color(img.getRGB(col, row));
                    ans[row][col] = c;
                }
            }
            UI.printMessage("Loaded "+ imageName);
            return ans;
        } catch(IOException e){UI.println("Image reading failed: "+e);}
        return null;
    }

    /**
     * Ensures that the working image is the same size as the current image.
     * Makes a new working image array if not.
     */
    public void checkWorking(){
        int rows = this.rows(this.image);
        int cols = this.cols(this.image);
        if (this.rows(this.working) != rows | this.cols(this.working) != cols){
            this.working = new Color[rows][cols];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    this.working[row][col]= Color.BLACK;
                }
            }
        }
    }

    /**
     * Set the working image to all {0,0,0} (needed for convolution}
     */
    public void clearWorking(){
        for(int row =0; row<working.length;row++){
            for(int col=0;col< working[0].length;col++){
                working[row][col] = Color.BLACK;

            }
        }
    }

    /**
     * Make a deep copy of an image array
     */
    public Color[][] copyImage(Color[][] from){
        int rows = from.length;
        int cols = from[0].length;
        Color[][] to = new Color[rows][cols];
        for (int row = 0 ; row < rows ; row++){
            for (int col = 0; col<cols; col++) {
                to[row][col] = new Color(from[row][col].getRGB());
            }
        }
        return to;
    }

    //=========================================================================
    /** ReDisplay the images (image and working) each pixel as a square of size 1
     *  Called after each button pressed.
     */
    public void redisplay(){
        if (this.image ==null) {
            UI.println("no image to display");
            return;
        }
        double imageLeft = LEFT + this.cols(this.working);
        UI.clearGraphics();
        displayImage(this.working, LEFT);
        displayImage(this.image, imageLeft + MARGIN);

        if (this.regionLeft>-1){
            UI.setColor(Color.red);
            UI.drawRect(LEFT+this.regionLeft, TOP+this.regionTop,
                this.regionWidth, this.regionHeight);
            UI.drawRect(imageLeft+this.regionLeft, TOP+this.regionTop,
                this.regionWidth, this.regionHeight);
        }
        UI.repaintGraphics();
    }

    public void displayImage(Color[][] img, double left){
        double y = TOP;
        for(int row=0; row<img.length; row++){
            double x = left;
            for(int col=0; col<img[row].length; col++){
                UI.setColor(img[row][col]);
                UI.fillRect(x, y, 1, 1);
                x++;
            }
            y++;
        }
    }

    // Main
    public static void main(String[] arguments){
        ImageProcessor ob = new ImageProcessor();
    }       

}
