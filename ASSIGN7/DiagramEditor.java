import ecs100.*;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

/**
 * Assignment 7 Comp112
 * 
 * Lavanya Sajwan - 300381661
 * 2017
 * 
 * God help me
 */
public class DiagramEditor
{
    private ArrayList<Shape> shapes = new ArrayList<Shape>();    // the collection of shapes
    private ArrayList<Connect> lines = new ArrayList<>();
    //where the mouse was pressed
    private double pressedX;                 
    private double pressedY;
    //  the current shape (or null if no shape)
    private Shape currentShape = null;      
    //second shape for connection
    private Shape secondShape = null;
    //whether on or not
    private boolean on = false;

    String currentAction ="";

    public DiagramEditor()
    {
        UI.addButton("New",this::newDrawing);
        // UI.addButton("Save",this::saveDrawing);
        // UI.addButton("Load",this::loadDrawing);
        UI.addButton("Rectangle",()->{currentAction="Rectangle";});
        UI.addButton("Oval",()->{currentAction="Oval";});
        UI.addButton("Hexagon",()->{currentAction="Hexagon";});
        UI.addButton("Select",()->{currentAction="Select";}); //move
        UI.addButton("Resize",()->{currentAction="Resize";});
        UI.addTextField("Text", this::textField);
        UI.addButton("Connect",()->{currentAction="Connect";});
        UI.addButton("Delete",()->{currentAction="Delete";});
        UI.setMouseListener(this::doMouse);

    }

    //Mouse Actions
    public void doMouse(String mouseAction, double x, double y) {
        if (mouseAction.equals("pressed")){
            this.pressedX = x;
            this.pressedY = y;
            if(this.currentShape != null && this.currentShape.onPoint(x, y)) {
                this.on = true;
            }

            else if (currentAction.equals("Resize") &&this.currentShape != null && this.currentShape.on(x, y)){
                this.on = true;
            }
            else{
                this.currentShape = findShape(x,y);

            }
        }
        else if (mouseAction.equals("released")){
            //this.addShape(this.pressedX, this.pressedY, x, y);
            if(this.on == true){
                this.on = false;
                double width = this.currentShape.getWidth();
                double height = this.currentShape.getHeight();
                width += x-pressedX;
                height += y-pressedY;
                this.currentShape.setWidth(width);
                this.currentShape.setHeight(height);
            }
            else{
                if(this.currentShape!=null){
                    if (currentAction.equals("Select")){ //&& (currentShape != null)){
                        this.selectShape(x, y);
                    }else if (currentAction.equals("Delete")){
                        this.deleteShape(x, y);
                    }else if (currentAction.equals("Resize")){
                        this.resizeShape(x - pressedX, y - pressedY);
                    }
                    else if(currentAction.equals("Connect")){
                        this.connectShape(x,y);
                    }

                }
                else{
                    this.addShape(this.pressedX, this.pressedY, x, y);
                }
            }
        }
        this.drawDrawing();
    }

    public void textField(String newText) {
        if(this.currentShape==null){return;}
        this.currentShape.setText(newText);
        drawDrawing();
    }

    public void drawDrawing(){
        UI.clearGraphics();
        drawLines();
        for(Shape s: shapes){
            s.redraw();
        }
        UI.repaintGraphics();
    }   

    public void drawLines(){
        for(Connect line : lines){
            line.draw();
        }
    }

    public Shape findShape(double x, double y){
        /*# YOUR CODE HERE */
        this.pressedX = x;
        this.pressedY = y;
        for(int i = 0; i < shapes.size(); i++){
            if(shapes.get(i).on(x,y)){
                return shapes.get(i);
            }
        }
        return null;  
    }

    public void newDrawing(){
        UI.initialise();
        UI.clearGraphics();
        this.shapes.clear();
        this.shapes = new ArrayList<Shape>();
    }

    //adds shape to the end of the array
    public void addShape(double x1, double y1, double x2, double y2){
        Trace.printf("Drawing shape %s, at (%.2f, %.2f)-(%.2f, %.2f)\n",
            this.currentAction, x1, y1, x2, y2);  //for debugging
        double endX = x2-x1;
        double endY = y2-y1;

        if(this.currentAction.equals("Rectangle")){
            Rectangle rect = new Rectangle(x1, y1, endX, endY, null);
            this.shapes.add(rect);
        }

        else if(this.currentAction.equals("Oval")){
            Oval oval = new Oval(x1, y1, endX, endY, null);
            this.shapes.add(oval);
        }

        else if(this.currentAction.equals("Hexagon")){
            Hexagon hexagon = new Hexagon(x1, y1, endX, endY, null);
            this.shapes.add(hexagon);
        }

    }

    public void saveDrawing(){
        /*# YOUR CODE HERE */
        String filename=UIFileChooser.save();
        if(filename!=null){
            try{
                PrintStream ps = new PrintStream(new File(filename));

                for(int i=0;i<this.shapes.size();i++){
                    Shape s = this.shapes.get(i);
                    String string = s.toString();
                    ps.println(string);
                }
                ps.close();
            }
            catch(IOException e){
                UI.printf("File Failure % \n", e);
            }
        }

    }

    /* public void loadDrawing(){

    shapes = new ArrayList<Shape>();
    try {
    Scanner sc = new Scanner (new File(UIFileChooser.open()));
    while (sc.hasNext()){
    String type = sc.next();
    Color RGB = new Color (sc.nextInt(),sc.nextInt(),sc.nextInt()); 
    if (type.equals("Rectangle")){
    this.shapes.add(new Rectangle (sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextDouble(),RGB));
    }
    else if (type.equals("Oval")){
    this.shapes.add(new Oval (sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextDouble(),RGB));
    }
    }
    this.drawDrawing();
    }catch(IOException e) {UI.printf("File Failure %s \n", e);
    }

    }*/

    public void selectShape(double changeX, double changeY){
        Trace.printf("Moving shape by (%.2f, %.2f)\n", changeX, changeY);  //for debugging

        if(currentShape != null && currentShape.on(this.pressedX, this.pressedY)){
            currentShape.moveBy(changeX-this.pressedX, changeY-this.pressedY);
        }
    }

    /* public void connectShape(double x, double y){
    x = currentShape.getStartx();
    y = currentShape.getStarty();

    }*/

    public boolean removeConnection(Shape s1, Shape s2){
        for(Connect con : lines){
            if(s1 == con.one && s2 == con.two|| s2 == con.one && s1 == con.two)  {      
                lines.remove(con);
                return true;
            }
        }
        return false;
    }

    public void connectShape(double x, double y){
        Shape secondShape = findShape(x, y);
        if(secondShape != null && secondShape != this.currentShape){
            if(!removeConnection(this.currentShape, secondShape)){	//removes connection if there is one
                this.lines.add(new Connect(this.currentShape, secondShape)); //now it is stored
            }
        }
    }

    public void deleteShape(double x, double y){
        Trace.printf("Deleting shape under (%.2f, %.2f)\n", x, y);  //for debugging
        Shape shape = findShape(x,y);
        for(int i =0 ; i <shapes.size(); i++){
            if(shapes.get(i) == shape){
                shapes.remove(i);
            }
        }

    }

    public void resizeShape(double changeX, double changeY){
        Trace.printf("Changing size of shape by (%.2f, %.2f) \n", changeX, changeY);  //for debugging

        currentShape.resize(changeX,changeY);

    }

    public static void main(String args[]){
        new DiagramEditor();
    }

}
