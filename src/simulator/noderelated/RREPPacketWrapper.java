package simulator.noderelated;

import simulator.Packets.RREPPacket;
import simulator.Node;

/**
 *
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 4, 2006
 * Time: 11:20:01 AM
 * a class which wraps RREPPacket and the recieved node into a single class
 */
public class RREPPacketWrapper {
    RREPPacket rrepPacket;
    Node recievedFrom;

    public RREPPacketWrapper(RREPPacket rrepPacket, Node recievedFrom) {
        this.rrepPacket = rrepPacket;
        this.recievedFrom = recievedFrom;
    }

    public RREPPacket getRrepPacket() {
        return rrepPacket;
    }

    public void setRrepPacket(RREPPacket rrepPacket) {
        this.rrepPacket = rrepPacket;
    }

    public Node getRecievedFrom() {
        return recievedFrom;
    }

    public void setRecievedFrom(Node recievedFrom) {
        this.recievedFrom = recievedFrom;
    }
}
