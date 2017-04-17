package simulator.Packets;

import simulator.Node;
import simulator.noderelated.tasks.RREP_Recieved;
import simulator.noderelated.RREPPacketWrapper;
import logger.MyLogger;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 1, 2006
 * Time: 5:36:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class RREPPacket extends Packet {

    public boolean R,A;
    private long LifeTime;
    public RREPPacket() {
        this.type=2;
    }

    public Packet copy_packet() {
        RREPPacket copy_of = new RREPPacket();
        this.copy_parentvalue(copy_of);
        copy_of.A =A;
        copy_of.R =R;
        copy_of.LifeTime = this.LifeTime;
        return copy_of;
    }


    public void recieve(Node reciever, Node prev_hop) {
        new RREP_Recieved("RREP_Recieved"+reciever.getIP().toString(),reciever,
                    new RREPPacketWrapper(this,prev_hop));
            MyLogger.logger.info("Node"+ reciever.getIP().toString()+": RREP_Recieved from "+this.source+" through "+prev_hop);
    }

    public String toString() {
        return "RREPPacket "+super.toString();
    }

    public long getLifeTime() {
        return LifeTime;
    }

    public void setLifeTime(long lifeTime) {
        LifeTime = lifeTime;
    }

}
