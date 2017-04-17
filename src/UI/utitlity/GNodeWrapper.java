package UI.utitlity;

import UI.myobjects.GraphicalNode;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 20, 2006
 * Time: 10:16:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class GNodeWrapper implements Comparable{
    double distace;
    GraphicalNode gnode;

    public GNodeWrapper(double distace, GraphicalNode gnode) {
        this.distace = distace;
        this.gnode = gnode;
    }

    public double getDistace() {
        return distace;
    }

    public GraphicalNode getGnode() {
        return gnode;
    }

    public int compareTo(Object o) {
        return Double.compare(this.distace,((GNodeWrapper)o).getDistace());
    }
}

