import ecs100.*;
import java.awt.Color;
import java.util.*;
import java.io.*;

public class Connect {

    public final Shape one;
    public final Shape two;

    public Connect(Shape s1, Shape s2){
        this.one = s1;
        this.two = s2;
    }

    public void draw() {
        UI.drawLine(one.getstartX(), one.getstartY(), two.getendX(), two.getendY());	
    }
}
