package simulator.mapmanagerrelated;

import simulator.Packets.Packet;
import simulator.Node;

import java.util.TimerTask;

import logger.MyLogger;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 18, 2006
 * Time: 6:15:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaskSpeedSimulator extends TimerTask {
    private Packet packet;
    private Node src,dest;

    public TaskSpeedSimulator(Packet packet, Node src, Node dest) {
        this.packet = packet;
        this.src = src;
        this.dest = dest;
    }

    public void run() {
        MyLogger.logger.info("MapManager Sending broadcast packet From " + src + " to " + dest);
        dest.recieve(packet.copy_packet(), src);
    }
}
