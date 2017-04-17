package simulator;

import simulator.noderelated.tasks.*;
import simulator.noderelated.*;
import simulator.Packets.*;

import java.util.*;
import java.io.Serializable;

import logger.MyLogger;
import logger.StatusManager;
import utility.PropertyManager;


/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 1, 2006
 * Time: 5:24:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class Node implements Serializable {
    /**
     * akharin zamani ke hello ia broadcast ferestade
     */
    public long helloTime;
    private int seq_no = 0;
    private Map<Node, Route> Rout_Arr = new HashMap<Node, Route>();
    //Timer discovery_Timer = new Timer("Discovery Timer");
    IPAddress IP;
    private int RREQ_ID = 0;
    private Set<BroadCastField> broadCastTable = new HashSet<BroadCastField>();
    private Coordinates node_coordinates = new Coordinates();                             //$Ali
    private int power;                                                            //$Ali
    private static int c = 3;// PropertyManager.readProperty("coeficient");
    public static final int HELLO_INTERVAL = 1000 * c;
    public static final int ALLOWED_HELLO_LOSS = 2;
    public final static int ACTIVE_ROUTE_TIMEOUT = 2 * 3000 * c;
    private final static long LOOPBACK_EXPIRY_TIME = 100000 * c;
    public final static int NODE_TRAVERSAL_TIME = 100 * c;
    private final static int TTL_START = 1 * 10;
    public static final int TTL_INCREMENT = 2;
    public static final int TTL_THRESHOLD = 7;
    public static final int NET_DIAMETER = 35;
    public static final int RREQ_RETRIES = 2;
    public static final int DELETE_PERIOD = 5 * Math.max(ACTIVE_ROUTE_TIMEOUT, HELLO_INTERVAL) * c;
    public static final int DEFAULT_ROUTE_LIFETIME = 12000 * c;
    public static final int RREP_ACK_REQUIRED = 0;
    public int MY_ROUTE_TIMEOUT = 2 * ACTIVE_ROUTE_TIMEOUT;
    public final static int NET_TRAVERSAL_TIME = 2 * NODE_TRAVERSAL_TIME * NET_DIAMETER;
    public static final int PATH_DISCOVERY_TIME = 2 * NET_TRAVERSAL_TIME;
    private final static int ROUTE_EXPIRY_INTERVAL = ACTIVE_ROUTE_TIMEOUT * 2;//5000 *c;
    public static final int PATH_DISCOVERY_INTERVAL = PATH_DISCOVERY_TIME * 2;//5000 *c;
    public static final int ROUTE_DELETE_INTERVAL = DELETE_PERIOD * 2;//5000 *c;


    public Set<BroadCastField> getBroadCastTable() {
        return broadCastTable;
    }

    public void increaseseq_no() {
        this.seq_no++;
    }

    public Coordinates getNode_coordinates() {                                   //$Ali
        return node_coordinates;
    }

    public void setNode_coordinates(Coordinates xy) {                             //$Ali
        this.node_coordinates = xy;
    }

    public int getPower() {                                                     //$Ali
        return power;
    }

    public void setPower(int power) {                                           //$Ali
        this.power = power;
    }

    /**
     * @param IP
     */
    public static Node getInstance(IPAddress IP) {
        Node node = new Node();
        node.IP = IP;
        init(node);
        Map_Manager.get_instance().addNode(node);
        return node;
    }

    private Node() {

    }

    public int getSeq_no() {
        return seq_no;
    }


    /**
     * the default node constructor which initializes this object <br/>
     * other constructors should call this method
     * this is for when node recieves RREQPacket can understand that have a route to itself
     */
    private static void init(Node node) {
        Route r = new Route(node, node, '1', '0', new HashSet<Node>());
//        MyExpiryTimer timer = new MyExpiryTimer("Self Route: "+node.IP.toString(),r,node);
        r.setLifeTime(LOOPBACK_EXPIRY_TIME);
        node.Rout_Arr.put(node, r);
        new Timer(node + " expriy timer", true).schedule(new Route_Expiry(node), Node.ROUTE_EXPIRY_INTERVAL, Node.ROUTE_EXPIRY_INTERVAL);
        new Timer(node + " broadcasttable expriy timer", true).schedule(new BroadCastTable_Expiry(node), 0, Node.PATH_DISCOVERY_INTERVAL);
        new Timer(node + " delete timer", true).schedule(new Route_Delete(node), Node.ROUTE_DELETE_INTERVAL, Node.ROUTE_DELETE_INTERVAL);
    }

    public String toString() {
        return IP.toString();
    }

    public IPAddress getIP() {
        return IP;
    }

    public void setIP(IPAddress IP) {
        this.IP = IP;
    }

    ///////////////
    private Object discoveryiswaiting;
    private RREPPacketWrapper rrepPacketWrapper;

    public Object getDiscoveryiswaiting() {
        return discoveryiswaiting;
    }

    public RREPPacketWrapper getRrepPacketWrapper() {
        return rrepPacketWrapper;
    }

    public void setRrepPacketWrapper(RREPPacketWrapper rrepPacketWrapper) {
        this.rrepPacketWrapper = rrepPacketWrapper;
    }
    //////////

    public boolean equals(Object obj) {

        return IP.equals(((Node) obj).IP);    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
     * calls map_manager to broadcast a packet
     *
     * @param packet the packet that should be broadcasted
     */
    public void send(Packet packet) {
        MyLogger.logger.info("Node" + IP.toString() + ": Sending Broadcast Packet");
        StatusManager.get_instance().showNodeStatus(this, "BroadCasting " + packet);
        StatusManager.get_instance().NodeSend(this, packet.type);
        Map_Manager.get_instance().sendPacket(packet, this);
    }

    /**
     * calls map_manager to send a packet to a particular node
     *
     * @param packet the packet that should be sent
     * @param dest   the distination of the packet
     * @return the output that map_manager generates
     */
    public boolean send(Packet packet, Node dest) {
        MyLogger.logger.info("Node" + IP.toString() + ": Unicasting packet to " + dest);
        StatusManager.get_instance().showNodeStatus(this, "Unicasting " + packet + " to " + dest);
        StatusManager.get_instance().NodeSend(this, packet.type);
        if (Map_Manager.get_instance().sendPacket(packet, this, dest)) {
            return true;
        }

        Route route = this.search(dest);

        if (Route.isBad(route)) {
            return false;
        }
        return Map_Manager.get_instance().sendPacket(packet, this, route.getNext_hop());
    }

    /**
     * a method that discovers a route for sending data it attempts three time
     * note that it is not thread safe because of rrepPacket field?????????
     *
     * @param dest the Distination Node
     * @return the Route if it have been find </br> null if there isn't any route
     */
    public Route discovery(Node dest) {

        MyLogger.logger.info("Node" + IP.toString() + ": discovery initiated to " + dest);
        Route route = search(dest);

        if (!Route.isBad(route)) {//if there is a route and it was not expired
            return route;
        }

        //if there isn't any route or it was expired
        //create rreqpacket
        RREQPacket rreqPacket = new RREQPacket();
        rreqPacket.dest = dest;
        rreqPacket.source = this;

//      The Destination Sequence Number field in the RREQ message is the last
//      known destination sequence number for this destination and is copied
//      from the Destination Sequence Number field in the routing table
        if (route != null) {
            rreqPacket.seq_no = route.getSeq_no();
            route.setIswaiting();
        } else {
            rreqPacket.U = true;
            //rreqPacket.seq_no=-1;
        }

        //The Hop Count field is set to zero.
        rreqPacket.hop_count = 0;

//        The Originator Sequence Number in the RREQ message is the
//node's own sequence number, which is incremented prior to insertion in a RREQ.
        seq_no++;
        rreqPacket.setSourceSeq_no(this.seq_no);
        //TODO G shoudl be set if user want

        //TODO  RING_TRAVERSAL_TIME SECITON 10
        int retry = 0;
        int ttl;
        if (!Route.isBad(route)) {
            ttl = route.getHop_count() + Node.TTL_INCREMENT;
        } else {
            ttl = TTL_START;
        }
        discoveryiswaiting = new Object();  // note RREP Recieved that it is in discovery
        rrepPacketWrapper = null;
//        To reduce congestion in a network, repeated attempts by a source node
//at route discovery for a single destination MUST utilize a binary exponential backoff.
        for (long timeOut = Node.NET_TRAVERSAL_TIME; retry < RREQ_RETRIES; timeOut *= 2, retry++) {
            // Each new attempt MUST increment and update the RREQ ID.
            rreqPacket.setRREQ_ID(this.RREQ_ID++);
            rreqPacket.ttl = ttl;
//            Before broadcasting the RREQ, the originating node buffers the RREQ
//ID and the Originator IP address (its own address) of the RREQ for PATH_DISCOVERY_TIME
            this.broadCastTable.add(new BroadCastField(this, rreqPacket.getRREQ_ID()));
            send(rreqPacket);               //sending

            try {
                synchronized (discoveryiswaiting) {
                    discoveryiswaiting.wait(timeOut);//waits for RREP
                }
            } catch (InterruptedException e) {
                MyLogger.logger.fatal("Node" + IP.toString() + ": discovery first wating for " + dest + ":ERROR OCCURED!");
                e.printStackTrace();
            }

            if (rrepPacketWrapper != null) {//&& rrepPacketWrapper.getRrepPacket().seq_no == rreqPacket.seq_no){              //rrep packet recieved
                RREPPacketWrapper temprrepPacketWrapper = rrepPacketWrapper;//creates a new reference
                rrepPacketWrapper = null;              //resets it for next try
                discoveryiswaiting = null;      // we are not in discovery any more
                MyLogger.logger.info("Node" + IP.toString() + ": first discovery for " + dest + " :successful");
                Route foundRoute = generateRouteFromRREP(temprrepPacketWrapper);
                foundRoute.resetIswaiting();
                return foundRoute;
            }
//            If a route is not received within NET_TRAVERSAL_TIME milliseconds
            if (ttl >= TTL_THRESHOLD) {
                ttl = NET_DIAMETER;
            } else {
                ttl += Node.TTL_INCREMENT;
            }
            // if wait got out after the time == rreppacket not recieved
            //sending second
            MyLogger.logger.info("Node" + this + ": discovery number " + retry + " for " + dest + " :faild");
            //the node MAY try again to discover a route by broadcasting another RREQ, up to a maximum of RREQ_RETRIES
        }

        MyLogger.logger.info("Node" + this + ": discovery for " + dest + " :failed");

        if (route != null) {
            route.resetIswaiting();
        }
        return null;                                      //route not found
    }

    /**
     * generates a route from RREPPacket that recieved
     *
     * @param rrepPacketWrapper contains RREPPacket and the last node that passed it
     * @return the {@link Route} object
     */
    public Route generateRouteFromRREP(RREPPacketWrapper rrepPacketWrapper) {
        Route route = new Route();
        route.setDestination(rrepPacketWrapper.getRrepPacket().dest);
        route.setHop_count(rrepPacketWrapper.getRrepPacket().hop_count);
        route.setNext_hop(rrepPacketWrapper.getRecievedFrom());
        route.setSeq_no(rrepPacketWrapper.getRrepPacket().seq_no);
        return route;
    }

    /**
     * generates a route from RREQPacket that recieved
     *
     * @param rreqPacket
     * @param recievedFrom the last node that passed it to this node
     * @return the {@link Route} object
     */
    public Route generateRouteFromRREQ(RREQPacket rreqPacket, Node recievedFrom) {
        Route route = new Route();
        route.setDestination(rreqPacket.source);
        route.setHop_count(rreqPacket.hop_count);
        route.setNext_hop(recievedFrom);
        route.setSeq_no(rreqPacket.getSourceSeq_no());
        return route;
    }

    public Route generateRouteFromRREQtoprevHop(RREQPacket rreqPacket, Node recievedFrom) {
        Route route = new Route();
        route.setDestination(recievedFrom);
        route.setHop_count(rreqPacket.hop_count);
        route.setNext_hop(recievedFrom);
        route.setSeq_no(-1);
        route.setLifeTime(new Date().getTime() + Node.DEFAULT_ROUTE_LIFETIME);//todo
        return route;
    }


    /**
     * sends a data packet to a particular destination
     *
     * @param data the data that should be distributed
     * @param dest the destination of data
     * @return true: if it can send the data <br/> false: if it can't send the data
     */

    public boolean send_Data(Data data, Node dest) {

        MyLogger.logger.info("Node" + IP + ": Sending data to " + dest);
        //TODO discovery timer
        Route route = discovery(dest);

        if (route == null) {
            StatusManager.get_instance().showNodeStatus(this, "Failed to send data to " + dest);
            return false;
        }

        DataPacket dataPacket = new DataPacket(data, route.getDestination(), this);
        //route.getLifeTime().reset();\
        if (send(dataPacket, route.getNext_hop())) {
            return true;
        }
        route.setHop_count(Route.INFINITE);
        route.setInvalid(true);
        return send_Data(data, dest);
    }

    /**
     * searchs if there is a route to a node in route table
     *
     * @param dest the destination node
     * @return route : if it finds any route <br/> null : if there isn't any route to that destination
     */
    public Route search(Node dest) {
        MyLogger.logger.info("Node" + IP.toString() + ": Searching for " + dest);
        StatusManager.get_instance().showNodeStatus(this, "Searching for " + dest);
        Route result = Rout_Arr.get(dest);
        if (result == null) {
            MyLogger.logger.info("Node" + IP.toString() + ": Route to " + dest + "not found!");
            StatusManager.get_instance().showNodeStatus(this, "Searching for " + dest + " not found!");
        }
        return result;
    }

    /**
     * recognizes the packet type that this node recieved and run a thread for it
     *
     * @param packet the packet that is recieved
     */
    public void recieve(Packet packet, Node prev_hop) {
        //packet.hop_count++;
        packet.ttl--;
        StatusManager.get_instance().showNodeStatus(this, packet + " Recieved");
        packet.recieve(this, prev_hop);
    }


    /**
     * adds a route in route table and runs its expiry time
     *
     * @param route the route that should be added to table
     */

    public void add_Route(Route route) {

        Rout_Arr.put(route.getDestination(), route);
        MyLogger.logger.info("Node" + this + ": new route to " + route.getDestination()
                + " through " + route.getNext_hop() + " added");
        StatusManager.get_instance().showNodeStatus(this, "new Route: " + route
                + " added");
    }

    /**
     * deletes a route from route table
     *
     * @param route the route that should be deleteed from route table
     */
    public void del_Route(Route route) {
        Rout_Arr.remove(route.getDestination());
        MyLogger.logger.info("Node: " + this + " : " + route + " Deleted!");
        StatusManager.get_instance().showNodeStatus(this, "Delete: " + route);
    }

    /**
     * send a RERR packet which contains all lost nodes to its previous node in the route to the destination.
     *
     * @param next_hop
     */
    public void send_RERR(Node next_hop, int lostSeq_no) {
        RERRPacket RERR = new RERRPacket();
        RERR.source = this;
        RERR.getLost_nodes().put(next_hop, lostSeq_no);
        for (Node next_node : Rout_Arr.keySet()) {
            if (Rout_Arr.get(next_node).getNext_hop().equals(next_hop)) {
                RERR.getLost_nodes().put(next_node, lostSeq_no);
            }
        }
        for (Node node : RERR.getLost_nodes().keySet()) {
            Route lostRoute = Rout_Arr.get(node);
            if (lostRoute != null) {
                Collection<Node> pre = lostRoute.getPrecursor();
                for (Node pre_node : pre) {
                    this.send(RERR, pre_node);
                }
            }
        }
    }

    public void send_RERROneDest(Node dest, int lostSeq_no) {
        RERRPacket RERR = new RERRPacket();
        RERR.source = this;
        RERR.getLost_nodes().put(dest, lostSeq_no);
        for (Node node : RERR.getLost_nodes().keySet()) {
            Route lostRoute = Rout_Arr.get(node);
            if (lostRoute != null) {
                Collection<Node> pre = lostRoute.getPrecursor();
                for (Node pre_node : pre) {
                    this.send(RERR, pre_node);
                }
            }
        }
    }

    public Map<Node, Route> getRout_Arr() {
        return Rout_Arr;
    }

    /**
     * checks broadcasted packet tables
     *
     * @param source     {@link Node} that generates broadcast packet
     * @param RREQPacket
     * @return true: if this packet has not been broadcasted yet <br/>
     * false: if this packet has been broadcasted
     */

    public boolean checkAndUpdateBroadCastTable(Node source, RREQPacket RREQPacket) {

        BroadCastField bfc = null;

        for (BroadCastField broadCastField : broadCastTable) {
            if (broadCastField.getSource().equals(source)) {
                bfc = broadCastField;
                break;
            }
        }

        if (bfc == null) {
            MyLogger.logger.debug(broadCastTable.add(new BroadCastField(source, RREQPacket.getRREQ_ID())));
            return true;
        }

        if (bfc.getRREQ_ID() < RREQPacket.getRREQ_ID()) {
            bfc.setRREQ_ID(RREQPacket.getRREQ_ID());
            return true;
        }

        // If reached here the RREQ is duplicate

        if ( RREQPacket.dest.getIP().toString().equals(this.getIP().toString())) {
            // If this node is the destination of duplicate RREP
            MyLogger.logger.info("****Node"+this+": Duplicate RREQ Received from " + source + ",to dest "
                    +RREQPacket.source+" BFC null? "+(bfc == null));

            return true;
        }

//        MyLogger.logger.debug("Node: "+this+" repeated rreq recieved : "+
//        (seq_noFound == RREQPacket.seq_no && (Rout_Arr.get(source) == null
//                ||  Rout_Arr.get(source).getHop_count() > RREQPacket.hop_count)));
//        return seq_noFound == RREQPacket.seq_no && Rout_Arr.get(source) != null
//                && Rout_Arr.get(source).getHop_count() > RREQPacket.hop_count;
        return false;
    }

    /////////////////////

    ////////////////////

}
