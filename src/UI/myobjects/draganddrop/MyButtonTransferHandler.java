package UI.myobjects.draganddrop;

import logger.MyLogger;

import javax.swing.*;
import java.awt.datatransfer.Transferable;

import UI.myobjects.NodeButton;
import UI.myobjects.GraphicalNode;
import UI.actions.NodePropOKBtnAction;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 6, 2006
 * Time: 11:00:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyButtonTransferHandler extends TransferHandler {


    protected Transferable createTransferable(JComponent c) {

        NodeButton source = (NodeButton) c;
        MyLogger.logger.debug("NodeButton createTranserable");
        GraphicalNode nodeTransferable = new GraphicalNode(source.getIcon(), source.myForm, false);
        source.myForm.putGNode(nodeTransferable);
        nodeTransferable.setTransferHandler(new MyNodeTransferHandler());
        return nodeTransferable;
    }

    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

}
