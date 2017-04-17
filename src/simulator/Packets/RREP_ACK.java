package simulator.Packets;

import simulator.Node;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 15, 2006
 * Time: 8:35:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class RREP_ACK extends Packet{
    public RREP_ACK() {
        this.type = 4;
    }

    public void recieve(Node reciever, Node prev_hop) {
        
    }
}
