package simulator.noderelated.tasks;

import simulator.Node;
import simulator.Packets.RREPPacket;
import simulator.noderelated.Route;

import java.util.TimerTask;
import java.util.Date;

import logger.MyLogger;
import logger.StatusManager;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 17, 2006
 * Time: 5:47:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Hello_Task extends TimerTask {
    Node mynode;

    public Hello_Task ( Node mynode) {
        super();
        this.mynode = mynode;
    }


    public void run() {
        if (mynode.helloTime + Node.HELLO_INTERVAL < new Date().getTime()){
            boolean haveActive = false;
            synchronized(this.mynode.getRout_Arr()){
            for (Route r : this.mynode.getRout_Arr().values()) {
                if (!Route.isBad(r) && !r.getDestination().equals(this.mynode)){
                    haveActive = true;
                }
            }
            }
            if (haveActive){
                RREPPacket rrepPacket = new RREPPacket();
                rrepPacket.dest = this.mynode;
                rrepPacket.seq_no = this.mynode.getSeq_no();
                rrepPacket.hop_count = 0;
                rrepPacket.setLifeTime(Node.ALLOWED_HELLO_LOSS * Node.HELLO_INTERVAL);
                rrepPacket.ttl = 1;
                this.mynode.send(rrepPacket);
            }
        }
    }
}
