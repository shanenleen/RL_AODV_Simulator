package UI.actions;

import UI.Myform;
import UI.Node_Properties;
import UI.myobjects.GraphicalNode;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 11, 2006
 * Time: 9:47:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodePropOKBtnAction implements ActionListener {
    Myform myForm;

    public NodePropOKBtnAction(Myform myForm) {
        this.myForm = myForm;
    }

    /**
     * it should loads information from right pane (Node Panel Properties) and sets selected graphical node properties
     * @param e
     */
    public void actionPerformed(ActionEvent e) {

        if (myForm.getSelectedGNode()!=null){

            GraphicalNode gNode = myForm.getSelectedGNode();
            Node_Properties np = myForm.getNodePanel();
            gNode.setName(np.nameText.getText().trim().toLowerCase());
            gNode.setNodeIP(np.IPText.getText());
            gNode.setScaledCoordinates(Integer.parseInt(np.xText.getText()),
                    Integer.parseInt(np.yText.getText()));
            gNode.setColor(np.colorBtn.getBackground());
            gNode.setNodePower(Integer.parseInt(np.powerText.getText()));

            if (np.nameText.getText().trim().length()>0){
                np.nameText.setEnabled(false);
            }

            myForm.refreshPowerShower();

        }else{
            JOptionPane.showMessageDialog(myForm,"Please Select a Node");
        }
    }
}
