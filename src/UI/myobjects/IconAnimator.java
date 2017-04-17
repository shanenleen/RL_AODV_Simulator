package UI.myobjects;

import UI.Myform;

import java.util.TimerTask;
import java.util.Timer;

import logger.MyLogger;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 18, 2006
 * Time: 7:10:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class IconAnimator extends TimerTask {

    Myform myFrom;
    GraphicalNode myNode;
    Timer myTimer;
    private String name;
    private int type;
    private static final int[] TYPETOINDEX = new int[]{2, 3, 1, 0};
    public static final ImageIcon[][] ANIMATIONFRAMES = new ImageIcon[][]{{
            new ImageIcon("images/SendingNode0.png"),
            new ImageIcon("images/Red/RSendingNode1.png"),
            new ImageIcon("images/Red/RSendingNode2.png"),
            new ImageIcon("images/Red/RSendingNode3.png")},

            {
                    new ImageIcon("images/SendingNode0.png"),
                    new ImageIcon("images/Blue/BSendingNode1.png"),
                    new ImageIcon("images/Blue/BSendingNode2.png"),
                    new ImageIcon("images/Blue/BSendingNode3.png")},

            {
                    new ImageIcon("images/SendingNode0.png"),
                    new ImageIcon("images/Green/GSendingNode1.png"),
                    new ImageIcon("images/Green/GSendingNode2.png"),
                    new ImageIcon("images/Green/GSendingNode3.png")},

            {
                    new ImageIcon("images/SendingNode0.png"),
                    new ImageIcon("images/Yellow/YSendingNode1.png"),
                    new ImageIcon("images/Yellow/YSendingNode2.png"),
                    new ImageIcon("images/Yellow/YSendingNode3.png")}

    };

    public IconAnimator(Myform myFrom, GraphicalNode myNode, Timer myTimer, String name, int type) {
        this.myFrom = myFrom;
        this.myNode = myNode;
        this.myTimer = myTimer;
        this.name = name;
        this.type = type;
    }

    public String toString() {
        return this.name;
    }

    public void run() {
        synchronized (myNode.currentIconNumber) {
            myNode.currentIconNumber.setType(TYPETOINDEX[this.type]);
            if (myNode.currentIconNumber.getValue() >= 3) {
                myNode.currentIconNumber.setValue(0);
                myNode.currentIconNumber.setShouldStop(true);
            } else {
                this.myNode.currentIconNumber.increaseValue();
            }
            myNode.setIcon(ANIMATIONFRAMES[myNode.currentIconNumber.getType()][myNode.currentIconNumber.getValue()]);
            MyLogger.logger.debug(this + " " + myNode.currentIconNumber);

            if (myNode.currentIconNumber.isShouldStop()) {// && myNode.currentIconNumber.getValue()>=1 ){
                myNode.currentIconNumber.setValue(0);
                myNode.currentIconNumber.setShouldStop(false);
                myTimer.cancel();
                return;
            }
        }
        this.myNode.invalidate();

    }
}
