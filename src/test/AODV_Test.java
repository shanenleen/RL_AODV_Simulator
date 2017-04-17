package test;

import simulator.Map_Manager;
import simulator.Node;
import simulator.Data;
import simulator.noderelated.IPAddress;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import logger.MyLogger;
import logger.StatusManager;

/**
 * Created by IntelliJ IDEA.
 * User: Ali
 * Date: Aug 5, 2006
 * Time: 1:27:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class AODV_Test {

    public static TestFlag waiting = new TestFlag();
    private static final int NODE_NUMBER = 10;
    private static final int MAP_LENGTH = 90;
    private static final int MAP_WIDTH = 90;
    private static final int [][] nodexyp = new int[][]{{15, 15, 43}, {15, 45, 30},
            {15, 75, 30}, {45, 75, 30}};

    public static void randomnode_create() {

        IPAddress ipBase = new IPAddress("192.168.10.1");

        for (int i = 0; i < NODE_NUMBER; i++) {
            Node new_node = Node.getInstance(IPAddress.createNext(ipBase));
            new_node.getNode_coordinates().setX_coordinate((int) (MAP_LENGTH * Math.random()));
            new_node.getNode_coordinates().setY_coordinate((int) (MAP_WIDTH * Math.random()));
            new_node.setPower(43);
//            Map_Manager.get_instance().addNode(new_node);

            MyLogger.logger.info("Node " + i + " created at: x = "
                    + new_node.getNode_coordinates().getX_coordinate()
                    + ", y = " + new_node.getNode_coordinates().getY_coordinate()
                    + ", power = " + new_node.getPower());
        }
    }

    public static void random_send() {

        int src_number = (int) (NODE_NUMBER * Math.random());
        int dest_number = (int) (NODE_NUMBER * Math.random());

        while (src_number == dest_number) {
            dest_number = (int) (NODE_NUMBER * Math.random());
        }

        Node src_node = (Node) Map_Manager.get_instance().getNode_list().get(src_number);
        Node dest_node = (Node) Map_Manager.get_instance().getNode_list().get(dest_number);

        Data test_data = new Data();
        test_data.setContent("test");

        MyLogger.logger.info("Trying to send data form node " + src_number
                + " to node " + dest_number);

        if (src_node.send_Data(test_data, dest_node)) {
            MyLogger.logger.info("#############Data sent successfully.############");
            dest_node.getNode_coordinates().setX_coordinate(90 - dest_node.getNode_coordinates().getX_coordinate());
            dest_node.getNode_coordinates().setY_coordinate(90 - dest_node.getNode_coordinates().getY_coordinate());

            if (src_node.send_Data(test_data, dest_node)) {
                MyLogger.logger.info("%%%%%%%%%%%%%%%%%Data sent successfully.%%%%%%%%%%%%%%");
            } else {
                MyLogger.logger.info("Failed to send data.");
            }
        } else {
            MyLogger.logger.info("Failed to send data.");
        }
    }

    public static void node_create() {
        IPAddress ipBase = new IPAddress("192.168.10.1");
        for (int i = 0; i < nodexyp.length; i++) {
            Node new_node = Node.getInstance(IPAddress.createNext(ipBase));
            new_node.getNode_coordinates().setX_coordinate(nodexyp[i][0]);
            new_node.getNode_coordinates().setY_coordinate(nodexyp[i][1]);
            new_node.setPower(nodexyp[i][2]);
//            Map_Manager.get_instance().addNode(new_node);

            MyLogger.logger.info("Node " + (i) + " created at: x = "
                    + new_node.getNode_coordinates().getX_coordinate()
                    + ", y = " + new_node.getNode_coordinates().getY_coordinate()
                    + ", power = " + new_node.getPower());
        }
    }

    public static void send_test() {
        Node src_node = (Node) Map_Manager.get_instance().getNode_list().get(0);
        Node dest_node = (Node) Map_Manager.get_instance().getNode_list().get(3);

        Data test_data = new Data();
        test_data.setContent("test");
        MyLogger.logger.info("Trying to send data form node " + 0
                + " to node " + 3);
        src_node.send_Data(test_data, dest_node);
        synchronized(waiting){
            try {
                waiting.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        if (waiting.s.equals("true")) {
            MyLogger.logger.info("#############Data sent successfully.############");
            Node tempnode = (Node) Map_Manager.get_instance().getNode_list().get(2);
            tempnode.getNode_coordinates().setX_coordinate(75);
            tempnode.getNode_coordinates().setY_coordinate(45);

            if (src_node.send_Data(test_data, dest_node)) {
                MyLogger.logger.info("%%%%%%%%%%%%%%%%%Data sent successfully.%%%%%%%%%%%%%%");
            } else {
                MyLogger.logger.info("Failed to send data.");
            }
        } else {
            MyLogger.logger.info("Failed to send data.");
        }
    }

    public static void main(String[] args) {
        StatusManager.init();
        randomnode_create();
        random_send();
    }
}
