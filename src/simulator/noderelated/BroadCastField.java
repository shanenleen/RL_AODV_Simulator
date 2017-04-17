package simulator.noderelated;

import simulator.Node;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 15, 2006
 * Time: 9:14:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class BroadCastField {
    Node source;
    int RREQ_ID;
    long lifeTime;


    public boolean equals(Object obj) {
        return this.source.equals(obj);
    }

    public BroadCastField(Node source, int RREQ_ID) {
        this.source = source;
        this.RREQ_ID = RREQ_ID;
        this.lifeTime = new Date().getTime()+Node.PATH_DISCOVERY_TIME;
    }

    public String toString() {
        return "Broadcast field: "+getSource()+" : "+getRREQ_ID();
    }

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public int getRREQ_ID() {
        return RREQ_ID;
    }

    public void setRREQ_ID(int RREQ_ID) {
        this.RREQ_ID = RREQ_ID;
    }

    public long getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(long lifeTime) {
        this.lifeTime = lifeTime;
    }
}
