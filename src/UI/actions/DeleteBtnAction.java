package UI.actions;

import UI.Myform;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import simulator.Map_Manager;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 21, 2006
 * Time: 3:26:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class DeleteBtnAction implements ActionListener {
    Myform myForm;

    public DeleteBtnAction(Myform myForm) {
        this.myForm = myForm;
    }

    /**
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if (myForm.getSelectedGNode()!=null){
            myForm.getMyMap().remove(myForm.getSelectedGNode());
            myForm.getGraphicalNodes().remove(myForm.getSelectedGNode());
            Map_Manager.get_instance().getNode_list().remove(myForm.getSelectedGNode().getNode());
            myForm.setSelectedGNode(null);
        }else{
            JOptionPane.showMessageDialog(myForm,"Please Select a Node");
        }
    }
}
