package simulator.noderelated.tasks;

import simulator.Node;
import simulator.Packets.RERRPacket;
import simulator.noderelated.Route;
import logger.MyLogger;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 4, 2006
 * Time: 12:54:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class RERR_Recieved extends Thread{
    Node mynode;
    private RERRPacket packet;
    private Node recievedFrom;

    public RERR_Recieved(String name, Node mynode, RERRPacket packet, Node recievedFrom) {
        super(name);
        this.mynode = mynode;
        this.packet = packet;
        this.recievedFrom = recievedFrom;
        start();
    }

    public void run() {
        for (Node lostNode : packet.getLost_nodes().keySet()) {
            Route lostRoute = mynode.search(lostNode);

            if (lostRoute!=null && lostRoute.getNext_hop().equals(recievedFrom)){
                lostRoute.setInvalid(true);
                lostRoute.setLifeTime(new Date().getTime() + Node.DELETE_PERIOD);
                lostRoute.setSeq_no(packet.getLost_nodes().get(lostNode));
                lostRoute.setHop_count(Route.INFINITE);
                for (Node precursorNode : lostRoute.getPrecursor()) {
                    mynode.send(packet,precursorNode);
                }
            }
        }
    }
}
