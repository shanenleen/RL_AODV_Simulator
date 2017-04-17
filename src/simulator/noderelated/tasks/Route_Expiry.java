package simulator.noderelated.tasks;

import simulator.Node;
import simulator.noderelated.Route;

import java.util.TimerTask;
import java.util.Date;

import logger.MyLogger;
import logger.StatusManager;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 1, 2006
 * Time: 8:24:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class Route_Expiry extends TimerTask {
    Node mynode;

    public Route_Expiry ( Node mynode) {
        super();
        this.mynode = mynode;
    }


    public void run() {
        synchronized(this.mynode.getRout_Arr()){
        for (Node node : mynode.getRout_Arr().keySet()) {
            Route route = mynode.getRout_Arr().get(node);
            if (route.getLifeTime()<new Date().getTime() && !mynode.equals(route.getDestination())
                    && route.getHop_count()< Route.INFINITE){
                MyLogger.logger.info("Node "+mynode +" : "+route+" Expires!");
                StatusManager.get_instance().showNodeStatus(mynode,"Expire: "+route);
                route.setSeq_no(route.getSeq_no()+1);
                route.setHop_count(Route.INFINITE);
            }
        }
        }
    }
}
