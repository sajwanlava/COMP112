
import java.util.*;
import ecs100.*;
import java.awt.Color;
import java.io.*;

/** Oval represents an oval shape
 *  Implements the Shape interface.
 *  Needs fields to record the position, size, and colour
 */

public class Oval implements Shape {
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private String shapeText;

    /** Constructor with explicit values
     *  Arguments are the x and y of the top left corner,
     *  the width and height, and the color.  
     */
    public Oval (double x, double y, double wd, double ht, String text){
        this.x1 = x;
        this.y1 = y;
        this.x2 = wd;
        this.y2 = ht;

        this.shapeText = text;
    }

    public double getWidth(){
        return x2;
    }

    public double getHeight(){
        return y2;
    }

    public void setWidth(double x) {
        this.x2 = x;
    }

    public void setHeight(double y) {
        this.y2= y;
    }

    public void setText(String newText) {
        this.shapeText = newText;
    }

    public double getstartX() {
        return (this.x1);
    }

    public double getstartY() {
        return (this.y1);
    }

    public double getendX(){
        return(this.x2);
    }

    public double getendY(){
        return(this.y2);
    }

    /* /** [Completion] Constructor which reads values from a String
     *  that contains the specification of the Oval. 
     *  The format of the String is determined by the toString method.
     *     The first 3 integers specify the color;
     *     the following four numbers specify the position and the size.

    public Oval(String description) {

    Scanner data = new Scanner(description);
    int red = data.nextInt();
    int green = data.nextInt();
    int blue = data.nextInt();
    this.col = new Color(red,green,blue);
    this.x1 = data.nextDouble();
    this.y1 = data.nextDouble();
    this.x2 = data.nextDouble();
    this.y2 = data.nextDouble();
    } */

    public boolean on(double u, double v){
        if(u >= this.x1 && u <= (this.x1+this.x2) && v >= this.y1 && v <= (this.y1+this.y2)){
            return true;
        }

        return false; 
    }

    public boolean onPoint(double x, double y) {
        return (y>=this.y1+y2 && y < this.y1 + y2 && x>=this.x1 + x2 && x< this.x1 + x2 );
    }

    public double getStartx(){

        return this.x1;
    }

    public double getStarty(){
        return y1;
    }

    public void moveBy(double dx, double dy){
        UI.clearGraphics();
        this.x1 += dx;
        this.y1 += dy;
        this.redraw();

    }

    public void redraw(){
        UI.setColor(Color.black);
        UI.drawOval(this.x1, this.y1, this.x2, this.y2);

        if(this.shapeText==null){return;} 
        UI.setColor(Color.black);
        UI.drawString(this.shapeText, x1+(x2/3), y1+(y2/2)); 
    }

    /** [Completion] Changes the width and height of the shape by the
     *  specified amounts.
     *  The amounts may be negative, which means that the shape
     *  should get smaller, at least in that direction.
     *  The shape should never become smaller than 1 pixel in width or height
     *  The center of the shape should remain the same.
     */
    public void resize(double changeWd, double changeHt){
        this.x2 += changeWd;
        this.y2 += changeHt;

    }

    /** Returns a string description of the oval in a form suitable for
     *  writing to a file in order to reconstruct the oval later
     *  The first word of the string must be Oval 
     */
    public String toString(){

        //return ("oval"+col.getRed()+" "+col.getGreen()+" "+col.getBlue()+" "+this.x1+" "+this.y1+" "+this.x2+" "+this.y2);
        return null;
    }

}
