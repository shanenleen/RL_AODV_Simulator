package simulator.noderelated.tasks;

import simulator.Node;
import simulator.noderelated.Route;
import simulator.Packets.DataPacket;
import logger.MyLogger;
import logger.StatusManager;
import test.AODV_Test;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 4, 2006
 * Time: 1:23:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Data_Recieved extends Thread {
    Node mynode;
    private DataPacket packet;
    private Node recievedFrom;

    public Data_Recieved(String name, Node mynode, DataPacket packet, Node recievedFrom) {
        super(name);
        this.mynode = mynode;
        this.packet = packet;
        this.recievedFrom = recievedFrom;
        start();
    }

    public void run() {
        if (packet.dest.equals(mynode)) {
            MyLogger.logger.info("Node" + mynode + ":recieved DataPacket from " +
                    packet.source + " which handded from " + recievedFrom
                    + ":I'm the destination");

            //only for test
            synchronized (AODV_Test.waiting) {
                AODV_Test.waiting.s = "true";
                AODV_Test.waiting.notify();
            }
            //
            //call UI recieved data
            StatusManager.get_instance().showRecievedData(mynode, packet.source, packet.getData());
        } else {

            Route route = mynode.search(packet.dest);

            if (!Route.isBad(route)) {

                MyLogger.logger.info("Node" + mynode + ":passing DataPacket from " +
                        packet.source + " which handded from " + recievedFrom
                        + " to " + route.getNext_hop());

                route.setLifeTime(new Date().getTime() + Node.ACTIVE_ROUTE_TIMEOUT);
                Route route2 = mynode.search(packet.source);
                if (route2 != null) {
                    route2.setLifeTime(new Date().getTime() + Node.ACTIVE_ROUTE_TIMEOUT);
                }
                if (!mynode.send(packet, route.getNext_hop())) {
                    MyLogger.logger.info("Node" + mynode + ": node "
                            + route.getNext_hop() + " lost.");
                    route.setSeq_no(route.getSeq_no() + 1);
                    route.setInvalid(true);
                    route.setLifeTime(new Date().getTime() + Node.DELETE_PERIOD);
                    mynode.send_RERR(route.getNext_hop(), route.getSeq_no());

                }
            } else {
                int lostNodeseq_no = -1;
                MyLogger.logger.debug("Node" + mynode + ":receiving DataPacket from " +
                        packet.source + " which handded from " + recievedFrom
                        + "but have no route!");
                if (route != null) {
                    route.setInvalid(true);
                    route.setSeq_no(route.getSeq_no() + 1);
                    route.setLifeTime(new Date().getTime() + Node.DELETE_PERIOD);
                    lostNodeseq_no = route.getSeq_no();
                }

                mynode.send_RERROneDest(route.getDestination(), lostNodeseq_no);
            }
        }
    }
}
