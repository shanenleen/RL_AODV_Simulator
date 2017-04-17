package UI.myobjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import logger.MyLogger;
import UI.Myform;
import UI.myobjects.draganddrop.MyButtonTransferHandler;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 6, 2006
 * Time: 11:02:08 PM
 * To change this template use File | Settings | File Templates.
 * represents a nonreal node for Toolbar
 */
public class NodeButton extends JButton implements MouseMotionListener, MouseListener {

    private MouseEvent firstMouseEvent = null;

    protected boolean shouldRemoved = false;

    public Myform myForm;

    public NodeButton(Icon icon) {

        super(icon);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setPreferredSize(new Dimension(32, 32));
        this.setTransferHandler(new MyButtonTransferHandler());
    }


    public boolean isShouldRemoved() {
        return shouldRemoved;
    }

    public void setShouldRemoved(boolean shouldRemoved) {
        this.shouldRemoved = shouldRemoved;
    }

    //---------------------------------mouse events seciton--------------------------
    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        firstMouseEvent = e;
        MyLogger.logger.debug("NodeButton mouse pressed");
        e.consume();
    }

    public void mouseReleased(MouseEvent e) {
        firstMouseEvent = null;
    }

    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * initiates drag and drop action
     *
     * @param e
     */
    public void mouseDragged(MouseEvent e) {

        if (firstMouseEvent != null) {
            e.consume();
            //If they are holding down the control key, COPY rather than MOVE
            int action = TransferHandler.COPY;//: TransferHandler.MOVE;

            int dx = Math.abs(e.getX() - firstMouseEvent.getX());
            int dy = Math.abs(e.getY() - firstMouseEvent.getY());
            //Arbitrarily define a 5-pixel shift as the
            //official beginning of a drag.
            if (dx > 5 || dy > 5) {
                //This is a drag, not a click.
                JComponent c = (JComponent) e.getSource();
                TransferHandler handler = c.getTransferHandler();
                //Tell the transfer handler to initiate the drag.
                MyLogger.logger.debug("NodeButton exporting initiated");
                handler.exportAsDrag(c, firstMouseEvent, action);
                firstMouseEvent = null;
            }
        }

    }

    public void mouseMoved(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
