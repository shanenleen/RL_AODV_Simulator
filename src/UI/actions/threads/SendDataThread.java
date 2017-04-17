package UI.actions.threads;

import simulator.Node;
import simulator.Data;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 14, 2006
 * Time: 10:31:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class SendDataThread extends Thread {
    Node source,dest;
    Data data;

    public SendDataThread(Node source, Node dest, Data data) {
        super ("Swing send data from : "+source.getIP()+" to "+dest.getIP());
        this.source = source;
        this.dest = dest;
        this.data = data;
        start();
    }

    public void run() {
        source.send_Data(data,dest);
    }
}
