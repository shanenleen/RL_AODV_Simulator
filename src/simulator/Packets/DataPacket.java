package simulator.Packets;

import simulator.Data;
import simulator.Node;
import simulator.noderelated.tasks.Data_Recieved;
import logger.MyLogger;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 1, 2006
 * Time: 8:16:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataPacket extends Packet {

    private Data data;

    public DataPacket(Data data, Node dest, Node src) {
        this.data = data;
        this.dest = dest;
        this.source = src;
        this.type = 0;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void recieve(Node reciever, Node prev_hop) {
        new Data_Recieved("Data_Recieved"+reciever.getIP().toString(),reciever,this,prev_hop);
            MyLogger.logger.info("Node"+ reciever.getIP().toString()+": Data_Recieved from "+this.source+" through "+prev_hop);
    }

    public String toString() {
        return "DataPacket "+super.toString();
    }
}
