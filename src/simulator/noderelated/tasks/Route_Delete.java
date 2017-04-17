package simulator.noderelated.tasks;

import simulator.Node;
import simulator.noderelated.Route;

import java.util.TimerTask;
import java.util.Date;
import java.util.Iterator;

import logger.MyLogger;
import logger.StatusManager;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 15, 2006
 * Time: 10:14:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class Route_Delete extends TimerTask {
    Node mynode;

    public Route_Delete ( Node mynode) {
        super();
        this.mynode = mynode;
    }


    public void run() {
         synchronized(mynode.getRout_Arr()){

             //for (Node node : mynode.getRout_Arr().keySet()) {
             for (Iterator itr = mynode.getRout_Arr().keySet().iterator(); itr.hasNext();){
                Node node = (Node)itr.next();
                Route route = mynode.getRout_Arr().get(node);
                if (route.getLifeTime() +Node.DELETE_PERIOD * route.getIswaiting()<new Date().getTime()
                        && !mynode.equals(route.getDestination())){
                    MyLogger.logger.info("Node "+mynode +" : "+route+" Deleted!");
                    StatusManager.get_instance().showNodeStatus(mynode,"Delete: "+route);
                    itr.remove();
                }
            }
        }
    }
}
