import java.util.*;
import ecs100.*;
import java.awt.Color;
import java.io.*;

public class Rectangle implements Shape {
    //fields

    private double x1; 
    private double y1;
    private double x2;  
    private double y2;
    private String shapeText;

    public Rectangle(double x, double y, double wd, double ht, String text) {
        this.x1 = x;
        this.y1 = y;
        this.x2 = wd;
        this.y2 = ht;

        this.shapeText = text;
    }

    /*/** [Completion] Constructor which reads values from a String
     *  that contains the specification of the Rectangle. 
     *  The format of the String is determined by the toString method.
     *     The first 3 integers specify the color;
     *     the following four numbers specify the position and the size.

    public Rectangle(String description) {
    Scanner data = new Scanner(description);
    int red = data.nextInt();
    int green = data.nextInt();
    int blue = data.nextInt();
    this.col = new Color(red,green,blue);
    this.x1 = data.nextDouble();
    this.y1 = data.nextDouble();
    this.x2 = data.nextDouble();
    this.y2 = data.nextDouble();
    }*/

    /** Returns true if the point (u, v) is on top of the shape */
    public boolean on(double u, double v) {

        if (u >= this.x1 && u <= (this.x1+this.x2) && v >= this.y1 && v <= (this.y1+this.y2)){
            return true;
        }

        return false;
    }

    public boolean onPoint(double x, double y) {
        return (y>=this.y1+y2 && y < this.y1 + y2 && x>=this.x1 + x2 && x < this.x1 + x2);
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
        this.y2 = y;
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

    public void setText(String newText) {
        this.shapeText = newText;
    }

    public void moveBy(double dx, double dy) {
        UI.clearGraphics();
        this.x1 += dx;
        this.y1 += dy;
        this.redraw();

    }

    public void redraw() {

        UI.setColor(Color.black); //default color, but stated it anyway for my own sanity 
        UI.drawRect(this.x1, this.y1, this.x2, this.y2);
        if(this.shapeText==null){return;} 
        UI.setColor(Color.black);
        UI.drawString(this.shapeText, x1+(x2/3), y1+(y2/2)); 

    }
    public void resize (double changeWd, double changeHt) {
        if (x2 > 1 && y2 >1){
            this.x2 += changeWd;
            this.y2 += changeHt;
            this.redraw ();
        }

    }

    public String toString() {
        //return ("rect "+col.getRed()+" "+col.getGreen()+" "+col.getBlue()+" "+this.x1+" "+this.y1+" "+this.x2+" "+this.y2);
        return null;
    }
}