package simulator.noderelated.tasks;

import simulator.Node;
import simulator.noderelated.Route;
import simulator.noderelated.BroadCastField;

import java.util.TimerTask;
import java.util.Date;
import java.util.Iterator;

import logger.MyLogger;
import logger.StatusManager;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 15, 2006
 * Time: 9:40:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class BroadCastTable_Expiry  extends TimerTask {

    Node mynode;

    public BroadCastTable_Expiry ( Node mynode) {
       super();
       this.mynode = mynode;
    }


    public void run() {
        for (Iterator itr =  mynode.getBroadCastTable().iterator(); itr.hasNext();) {
            BroadCastField bcf = (BroadCastField)itr.next();
            if (bcf.getLifeTime()<new Date().getTime()){
                MyLogger.logger.info("Node "+mynode +" : "+bcf+" Expires!");
                StatusManager.get_instance().showNodeStatus(mynode,"Expire: "+bcf);
                itr.remove();
            }
        }
    }
}
