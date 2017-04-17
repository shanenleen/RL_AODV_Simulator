package UI.actions;

import UI.Myform;
import UI.myobjects.GraphicalNode;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 11, 2006
 * Time: 11:17:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodePropResetBtnAction implements ActionListener {
    Myform myForm;

    public NodePropResetBtnAction(Myform myForm) {
        this.myForm = myForm;
    }

    public void actionPerformed(ActionEvent e) {
        if (myForm.getSelectedGNode()!=null){
            myForm.getSelectedGNode().fillNodePanel();
        }else{
            myForm.getNodePanel().resetNodePropertiest();
        }
    }
}
