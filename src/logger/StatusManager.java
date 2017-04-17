package logger;

import simulator.Node;
import simulator.Data;
import UI.Myform;
import UI.myobjects.GraphicalNode;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 14, 2006
 * Time: 4:19:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatusManager {
    Myform myform;
    boolean test = false;
    private static StatusManager status_manager;

    private StatusManager(Myform myform) {
        this.myform = myform;
    }

    public StatusManager(boolean test) {
        this.test = test;
    }

    public static void init (Myform myForm){
         status_manager = new StatusManager(myForm);
    }
    public static void init (){
         status_manager = new StatusManager(true);
    }
    public static StatusManager get_instance(){
        return status_manager;
    }

    public void showNodeStatus(Node node,String status){
        if (!this.test){
            GraphicalNode gNode = myform.getGnodebyNode(node);
            gNode.addStatus(status);
        }
    }
    public void showRecievedData(Node receiver,Node sender, Data data){
        if (!this.test){
            GraphicalNode gNode = myform.getGnodebyNode(receiver);
            gNode.addRecievedData("From: "+sender+"\n"+data.getContent());
        }
    }
    public void NodeSend(Node sender,int type){
         if (!this.test){
            GraphicalNode gNode = myform.getGnodebyNode(sender);
            gNode.sending(type);
        }
    }
}
