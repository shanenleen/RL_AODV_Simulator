package UI.actions;

import UI.Myform;
import UI.myobjects.GraphicalNode;
import UI.actions.threads.SendDataThread;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import simulator.Data;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 14, 2006
 * Time: 10:06:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class NodePropSendtoBtnAction implements ActionListener {

    Myform myForm;

    public NodePropSendtoBtnAction(Myform myForm) {
        this.myForm = myForm;
    }

    public void actionPerformed(ActionEvent e) {

        if (myForm.getSelectedGNode() != null) {

            GraphicalNode dest = myForm.getGNode(
                    myForm.getNodePanel().sendToText.getText().trim().toLowerCase());

            if (dest != null) {
                new SendDataThread(myForm.getSelectedGNode().getNode(), dest.getNode(),
                        new Data(myForm.getNodePanel().dataText.getText()));
            } else {
                JOptionPane.showMessageDialog(myForm, "Destination node not found!");
            }
        }
    }
}
