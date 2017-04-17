package UI.actions;

import UI.Myform;
import UI.utitlity.NthMinimum;
import UI.utitlity.GNodeWrapper;
import UI.myobjects.GraphicalNode;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;

import simulator.noderelated.IPAddress;
import simulator.Map_Manager;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 20, 2006
 * Time: 3:07:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class InitParameters implements ActionListener {
    Myform  myForm;
    public InitParameters(Myform myForm) {
        this.myForm = myForm;
    }

    public void actionPerformed(ActionEvent e) {
        if (myForm.getMinNumberForFillParameter()<=0){
            JOptionPane.showMessageDialog(myForm,"Please Enter minimum number of nodes to see");
            return ;
        }
        for (GraphicalNode gnode : myForm.getGraphicalNodes()) {
                gnode.getNode().setPower(0);
        }
        int margin =1; //myForm.xScale * 2;
        //for name
        char s = 'a';
        StringBuffer prefix = new StringBuffer("");
        String name= ""+s;
        //for ip
        IPAddress ipBase = new IPAddress("192.168.10.1");
        //contains nodes that their power is changed by their couple
        Set<GraphicalNode> dirties = new HashSet<GraphicalNode>();
        for (GraphicalNode gnode : myForm.getGraphicalNodes()) {
            gnode.setName(name);
            name = prefix.toString()+ ++s;
            if (s>'z'){
                prefix.append("a");
                s='a';
            }
            gnode.getNode().setIP(ipBase);
            ipBase = IPAddress.createNext(ipBase);

            if (myForm.getGraphicalNodes().size()>1){
                GNodeWrapper [] gnodesW = new GNodeWrapper [myForm.getGraphicalNodes().size() -1];
                int i = 0;
                for (GraphicalNode graphicalNode : myForm.getGraphicalNodes()) {
                    if (!gnode.equals(graphicalNode)){
                        gnodesW[i++] = new GNodeWrapper(
                                Map_Manager.get_instance().getDistance(gnode.getNode(),graphicalNode.getNode()),
                                graphicalNode);
                    }
                }
                int index = NthMinimum.randomized_Select(gnodesW,0,gnodesW.length - 1,myForm.getMinNumberForFillParameter());
                int nthPower = (int)Math.round(gnodesW[index].getDistace())+margin;
                if (nthPower > gnodesW[index].getGnode().getNode().getPower() && myForm.isDoubleDirection()){
                    gnodesW[index].getGnode().getNode().setPower(nthPower);
                    for (int k = 0; k<=index; k++){
                        dirties.add(gnodesW[k].getGnode());
                        //System.out.println(gnodesW[k].getGnode()+" added");
                    }
                }
                gnode.getNode().setPower(nthPower);
            }else{
                gnode.getNode().setPower(0);
                return;
            }
        }
        if (myForm.isDoubleDirection()){
            Set<GraphicalNode> tempDirties = new HashSet<GraphicalNode>();
            while (!dirties.isEmpty()){
                for (Iterator itr = dirties.iterator(); itr.hasNext();){
                    GraphicalNode gnode = (GraphicalNode) itr.next();
                    for (GraphicalNode graphicalNode : myForm.getGraphicalNodes()) {
                        if (!gnode.equals(graphicalNode)){
                            //if i see that and it can't see me update its power and add it to dirty list
                            double distance = Map_Manager.get_instance().getDistance(gnode.getNode(),
                                    graphicalNode.getNode());
//                            System.out.println(gnode+":"+gnode.getNode().getPower()+","
//                                    +graphicalNode+":"+graphicalNode.getNode().getPower()+","+distance);
                            if ( distance< gnode.getNode().getPower()
                                    && distance > graphicalNode.getNode().getPower()){
                                graphicalNode.getNode().setPower((int)distance +margin);
//                                System.out.println(""+ graphicalNode.toString() +" become dirty");
                                tempDirties.add(graphicalNode);
                            }
                            if ( distance> gnode.getNode().getPower()
                                    && distance < graphicalNode.getNode().getPower()){
                                gnode.getNode().setPower((int)distance +margin);
//                                System.out.println(""+ gnode.toString() +" become dirty");
                                tempDirties.add(gnode);
                            }
                        }
                    }
                }
                dirties = tempDirties;
                tempDirties = new HashSet<GraphicalNode>();
//                System.out.println("|");
            }
        }
    }
}