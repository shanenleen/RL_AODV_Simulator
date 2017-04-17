package simulator.noderelated;

import simulator.Node;

import java.util.Set;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 1, 2006
 * Time: 5:32:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class Route {

    public static final int INFINITE = 1000;
    private boolean invalid=false;
    private Node destination;
    private Node next_hop;
    private int seq_no;
    private int hop_count;
    private Set <Node> precursor =  new  HashSet<Node>();                   //$masoud
    private long lifeTime;
    private byte iswaiting=1;

    public byte getIswaiting() {
        return iswaiting;
    }

    public void resetIswaiting(){
        iswaiting=1;
    }
    public void setIswaiting() {
        this.iswaiting = 2;
    }

    public Route() {
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    //$masoud

    public String toString() {
        return "Route to " +this.getDestination()+" through "+this.getNext_hop();
    }

    public Route(Node destination, Node next_hop, int seq_no, int hop_count, Set<Node> precursor) {
        this.destination = destination;
        this.next_hop = next_hop;
        this.seq_no = seq_no;
        this.hop_count = hop_count;
        this.precursor = precursor;
        this.lifeTime = lifeTime;
    }

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public Node getNext_hop() {
        return next_hop;
    }

    public void setNext_hop(Node next_hop) {
        this.next_hop = next_hop;
    }

    public int getSeq_no() {
        return seq_no;
    }

    public void setSeq_no(int seq_no) {
        this.seq_no = seq_no;
    }

    public int getHop_count() {
        return hop_count;
    }

    public void setHop_count(int hop_count) {
        this.hop_count = hop_count;
    }

    public Set<Node> getPrecursor() {
        return precursor;
    }

    public void setPrecursor(Set<Node> precursor) {
        this.precursor = precursor;
    }

    public Long getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(Long lifeTime) {
        this.lifeTime = lifeTime;
    }

    /**
     * if the destination is previously unknown to the node, or if a previously
     * valid route to the destination expires or is marked as invalid
     * @param r
     * @return
     */
    public static boolean isBad(Route r){
        return r ==null || r.getHop_count()== Route.INFINITE
                || r.isInvalid() || r.getSeq_no()<0;
    }
}
