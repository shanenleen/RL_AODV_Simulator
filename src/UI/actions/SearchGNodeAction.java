package UI.actions;

import UI.Myform;
import UI.actions.threads.SendDataThread;
import UI.myobjects.GraphicalNode;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import simulator.Data;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 21, 2006
 * Time: 11:53:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class SearchGNodeAction  implements ActionListener{
    Myform myForm;

    public SearchGNodeAction(Myform myForm) {
        this.myForm = myForm;
    }

    public void actionPerformed(ActionEvent e) {
        GraphicalNode dest = myForm.getGNode(
                myForm.getSearchText().getText().trim().toLowerCase());
        if (dest!=null){
            myForm.setSelectedGNode(dest);
        }else{
            JOptionPane.showMessageDialog(myForm,"Node not found!");
        }
    }
}
