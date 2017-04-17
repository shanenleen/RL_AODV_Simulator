package UI.myobjects.draganddrop;

import logger.MyLogger;

import javax.swing.*;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTarget;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.datatransfer.DataFlavor;
import java.awt.*;
import java.io.IOException;

import UI.myobjects.NodeButton;
import UI.myobjects.GraphicalNode;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 11, 2006
 * Time: 6:42:04 PM
 * To change this template use File | Settings | File Templates.
 * it is a class that implements action of coping or moving {@link GraphicalNode} on target place
 */
public class DropTargetImp extends DropTarget
{
    private JComponent c;

    public DropTargetImp(JComponent c) {
        this.c = c;
    }

    public void drop(DropTargetDropEvent dtde) {


        MyLogger.logger.debug("Mappanel importing data");
        if (dtde.isDataFlavorSupported(GraphicalNode.dataFlavor)) {
            dtde.acceptDrop(dtde.getDropAction());
            JPanel panel = (JPanel)this.c;
            Transferable t = dtde.getTransferable();
            try {
                GraphicalNode node = (GraphicalNode)t.getTransferData(GraphicalNode.dataFlavor);
                Insets insets = panel.getInsets();
                Dimension size = node.getPreferredSize();
                if (!node.isShouldRemoved()){
                    panel.add(node);
                }
                node.setSelectGNode();
                node.setBounds(dtde.getLocation().x , dtde.getLocation().y,
                             size.width, size.height);
                node.fillNodePanel();
                panel.invalidate();
                node.myForm.refreshPowerShower();
                MyLogger.logger.debug("Mappanel after invalidate");
//                panel.repaint();

                dtde.dropComplete(true);
            } catch (UnsupportedFlavorException ufe) {
                MyLogger.logger.error("importData: unsupported data flavor");
            } catch (IOException ioe) {
                MyLogger.logger.error("importData: I/O exception");
            }
        }
    }
}
