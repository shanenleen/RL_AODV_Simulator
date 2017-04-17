package UI.myobjects;

import UI.Myform;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 20, 2006
 * Time: 5:26:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class PowerShower extends JComponent {
    Myform myform;
    int x,y,rX,rY;
    final static float dash1[] = {10.0f};
    final static BasicStroke dashed = new BasicStroke(1.0f,
            BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER,
            10.0f, dash1, 0.0f);



    public PowerShower(Myform myform) {
        this.myform = myform;
    }

    /**
     * initializes the glass pane for painting
     * @param x
     * @param y
     * @param radiusX
     * @param radiusY
     */
    public void setXYrXrY(int x,int y, int radiusX,int radiusY){
        this.x = x;
        this.y = y;
        this.rX = radiusX;
        this.rY = radiusY;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);    //To change body of overridden methods use File | Settings | File Templates.
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        g2.setStroke(dashed);
        g2.drawOval(x-rX+1,y-rY+41,2*rX,2*rY);
    }
}