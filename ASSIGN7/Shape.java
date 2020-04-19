import ecs100.*;

public interface Shape{

    public boolean on(double x, double y);

    public boolean onPoint(double x, double y);

    public void moveBy(double dx, double dy);

    public void redraw();

    public void resize(double changeWd, double changeHt);

    public double getWidth();

    public double getHeight();

    public void setWidth(double x);

    public void setHeight(double y);

    public void setText(String newText);

    public double getstartX();

    public double getstartY();

    public double getendX();

    public double getendY();

    /** Returns a string description of the shape in a form suitable for
     *  writing to a file in order to reconstruct the shape later
     */
    public String toString();

}