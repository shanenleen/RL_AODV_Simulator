package simulator.Packets;

import simulator.Node;
import simulator.noderelated.tasks.RERR_Recieved;

import java.util.*;

import logger.MyLogger;

/**
 * Created by IntelliJ IDEA.
 * User: Ali
 * Date: Aug 5, 2006
 * Time: 10:08:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class RERRPacket extends Packet {
    private Map<Node,Integer> lost_nodes = new HashMap<Node,Integer>();

    boolean N;
    public Map <Node,Integer> getLost_nodes() {
        return lost_nodes;
    }

    public RERRPacket() {
        this.type = 3;
    }

    public void recieve(Node reciever, Node prev_hop) {
        new RERR_Recieved("RERR_Recieved"+reciever.getIP().toString(),reciever,this,prev_hop);
            MyLogger.logger.info("Node"+ reciever.getIP().toString()+": RERR_Recieved from "+this.source+" through "+prev_hop);
    }

    public String toString() {
        return "RERRPacket "+super.toString();
    }

}
