package UI.myobjects;

import simulator.noderelated.Coordinates;
import simulator.noderelated.IPAddress;
import simulator.Node;

import javax.swing.*;

import UI.Myform;
import UI.Node_Properties;

import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import logger.MyLogger;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 11, 2006
 * Time: 9:03:08 PM
 * To change this template use File | Settings | File Templates.
 * a graphical represention of a node
 * it implements some method to make a communication between graphical properties and node properties
 */
public class GraphicalNode extends NodeButton implements Transferable {
    private static final String mimeType = DataFlavor.javaJVMLocalObjectMimeType +
            ";class=UI.myobjects.GraphicalNode";
    public static DataFlavor dataFlavor;
    public final IntegerWrapper currentIconNumber = new IntegerWrapper();
    private Node node;
    public static final int ANIMATION_PERIOD = 500;
    private String name = "";
    private Color color;
    private StringBuffer recievedData = new StringBuffer();
    private StringBuffer statues = new StringBuffer();


    public String toString() {
        return this.getName();
    }

    /**
     * it overrides default setBounds method of {@link JComponent} class to make a communication between x,y poroperties
     * of graphical node and actual node by {@link myForm.xScale} and {@link myForm.yScale}
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */

    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);    //To change body of overridden methods use File | Settings | File Templates.
        this.node.setNode_coordinates(new Coordinates(x * myForm.xScale, y * myForm.yScale));
        MyLogger.logger.debug("Mappanel setting boound");
    }

    public String getStatus() {
        return this.statues.toString();
    }

    public void addStatus(String st) {
        this.statues.append(st).append("\n");
        this.refreshNodePanel();
    }

    public void resetStatus() {
        this.statues.delete(0, this.statues.length());
    }

    public String getRecievedData() {
        return this.recievedData.toString();
    }

    public void addRecievedData(String st) {
        this.recievedData.append(st).append("\n");
        this.refreshNodePanel();
    }

    public void resetRecievedData() {
        this.recievedData.delete(0, this.recievedData.length());
    }


    public GraphicalNode(Icon icon) {
        super(icon);
        try {
            dataFlavor = new DataFlavor(GraphicalNode.mimeType);
        } catch (ClassNotFoundException e) {
        }
        this.node = Node.getInstance(new IPAddress("192.168.10.1"));
        this.color = this.getBackground();
    }

    /**
     * makes a Graphical Node componnet and calls another constructor
     *
     * @param icon
     * @param myForm        the frame object that should contains this component
     * @param shouldRemoved says if in drag and drop action this component should be moved or copied
     */
    public GraphicalNode(Icon icon, Myform myForm, boolean shouldRemoved) {
        this(icon);
        this.shouldRemoved = shouldRemoved;
        this.myForm = myForm;
    }

    /**
     * sets this components x and y coresponding to x and y of actual node
     *
     * @param x x dimension of actual node
     * @param y y dimension of actual node
     */
    public void setScaledCoordinates(int x, int y) {
        Dimension size = getSize();
        super.setBounds(x / myForm.xScale, y / myForm.yScale, size.width, size.height);    //To change body of overridden methods use File | Settings | File Templates.
        this.node.setNode_coordinates(new Coordinates(x, y));
    }

    public void setNodePower(int power) {
        this.node.setPower(power);
    }

    public void setNodeIP(String IP) {
        this.node.setIP(new IPAddress(IP));
    }

    public Node getNode() {
        return node;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.setToolTipText(name);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.setBackground(color);
        this.color = color;
    }

    private void refreshNodePanel() {
        if (myForm.getSelectedGNode() != null && myForm.getSelectedGNode().equals(this)) {
            this.refreshNodePanelDynamicData();
        }
    }

    public void fillNodePanel() {
        Node_Properties np = myForm.getNodePanel();
        np.nameText.setText(this.name);
        np.IPText.setText(node.getIP().toString());
        np.xText.setText(Integer.toString(node.getNode_coordinates().getX_coordinate()));
        np.yText.setText(Integer.toString(node.getNode_coordinates().getY_coordinate()));
        np.powerText.setText(Integer.toString(node.getPower()));
        np.colorBtn.setBackground(this.color);

        refreshNodePanelDynamicData();


    }

    private void refreshNodePanelDynamicData() {
        Node_Properties np = myForm.getNodePanel();
        np.statusText.setText(getStatus());
        np.recievedDataText.setText(getRecievedData());

    }

    public void setSelectGNode() {
        myForm.setSelectedGNode(this);
    }

    public void sending(int type) {
        java.util.Timer atimer = new java.util.Timer("Animating " + this.name, true);
        atimer.schedule(new IconAnimator(this.myForm, this, atimer, "Animating " + this.name, type), 0, GraphicalNode.ANIMATION_PERIOD);

    }

    /**
     * when mouse pressed on this component the left hand node properties should be filled
     * it also sets frame selected node and initiates drag and drop action
     *
     * @param e
     */
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        myForm.setSelectedGNode(this);
    }

    //--------------------------------Transferable section------------------------
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{dataFlavor};
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return dataFlavor.equals(flavor);
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (!isDataFlavorSupported(flavor)) {
            throw new UnsupportedFlavorException(flavor);
        }
        return this;

    }
}

class IntegerWrapper {
    int value;
    boolean shouldStop;
    int type = 0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public boolean isShouldStop() {
        return shouldStop;
    }

    public void setShouldStop(boolean shouldStop) {
        this.shouldStop = shouldStop;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void increaseValue() {
        this.value++;
    }

    public String toString() {
        return value + "|" + shouldStop;
    }
}