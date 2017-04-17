package simulator;

import simulator.Packets.Packet;
import simulator.mapmanagerrelated.TaskSpeedSimulator;

import static java.lang.Math.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import logger.MyLogger;

/**
 * Created by IntelliJ IDEA.
 * User: Ali
 * Date: Aug 3, 2006
 * Time: 5:47:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class Map_Manager {

    private List node_list = new ArrayList();
    private static Map_Manager map_manager = new Map_Manager();
    private static long speedPercent = 200;

    private Map_Manager() {

    }

    public static Map_Manager get_instance() {
        return map_manager;
    }

    public List getNode_list() {
        return node_list;
    }

    public void setNode_list(List node_list) {
        this.node_list = node_list;
    }

    public void addNode(Node new_node) {
        node_list.add(new_node);
    }

    public void delNode(Node garbage_node) {
        node_list.remove(garbage_node);
    }

    public double getDistance(Node a, Node b) {

        int x_distance = a.getNode_coordinates().getX_coordinate() - b.getNode_coordinates().getX_coordinate();
        int y_distance = a.getNode_coordinates().getY_coordinate() - b.getNode_coordinates().getY_coordinate();
        double distance = Math.sqrt((x_distance * x_distance) + (y_distance * y_distance));
        MyLogger.logger.debug(a.getNode_coordinates() + ", " + b.getNode_coordinates() + ", " + distance + "," + a.getPower());

        return distance;
    }

    public void sendPacket(Packet packet_to_send, Node src_node) {

        for (Object aNode_list : node_list) {
            Node tempNode = (Node) aNode_list;
            if (getDistance(src_node, tempNode) <= src_node.getPower() && !tempNode.equals(src_node)) {
                new Timer("Mapmanager: Sending packet from " + src_node + " to " + tempNode, true)
                        .schedule(new TaskSpeedSimulator(packet_to_send, src_node, tempNode),
                                Math.round(getDistance(src_node, tempNode)) / 100 * speedPercent);
            }
        }
    }

    public boolean sendPacket(Packet packet_to_send, Node src_node, Node dest_node) {

        if (getDistance(src_node, dest_node) <= src_node.getPower()) {
            new Timer("Mapmanager: Sending packet from " + src_node + " to " + dest_node, true)
                    .schedule(new TaskSpeedSimulator(packet_to_send, src_node, dest_node),
                            Math.round(getDistance(src_node, dest_node)) / 100 * speedPercent);
            return true;
        }
        return false;
    }
}