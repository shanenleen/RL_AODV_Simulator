package simulator.Packets;

import simulator.Node;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 1, 2006
 * Time: 5:35:52 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Packet {

    public Node source = null;
    public Node dest = null;
    public int ttl = 0;   //TODO generate edefault ttl
    public int hop_count = 0;
    public int seq_no;                                           //$masoud
    public int type;

    public Packet copy_packet() {
        return this;
    }

    public Packet copy_parentvalue(Packet packet){
        packet.source = this.source;
        packet.dest = this.dest;
        packet.ttl = this.ttl;
        packet.hop_count = this.hop_count;
        packet.seq_no = this.seq_no;
        packet.type = this.type;
        return packet;
    }
    public abstract void recieve(Node reciever,Node prev_hop);
    public  String toString(){
        return "seq_no="+seq_no;
    }
}
