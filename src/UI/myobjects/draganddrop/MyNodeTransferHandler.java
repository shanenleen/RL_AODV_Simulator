package UI.myobjects.draganddrop;

import logger.MyLogger;

import javax.swing.*;
import java.awt.datatransfer.Transferable;

import UI.myobjects.NodeButton;
import UI.myobjects.GraphicalNode;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 11, 2006
 * Time: 8:37:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyNodeTransferHandler extends TransferHandler {

    protected Transferable createTransferable(JComponent c) {
        GraphicalNode source = (GraphicalNode) c;
        MyLogger.logger.debug("NodeButton createTranserable");
        source.setShouldRemoved(true);
        return source;
    }

    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    protected void exportDone(JComponent c, Transferable data, int action) {
//            if (shouldRemove && (action == MOVE)) {
//                sourcePic.setImage(null);
//            }
//            sourcePic = null;
    }
}
