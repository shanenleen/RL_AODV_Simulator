package UI.actions;

import UI.Myform;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 11, 2006
 * Time: 11:26:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class PanelAction extends MouseAdapter {

    Myform myForm;

    public PanelAction(Myform myForm) {
        this.myForm = myForm;
    }

    public void mouseClicked(MouseEvent e) {
        myForm.setSelectedGNode(null);
        myForm.getNodePanel().resetNodePanel();
    }
}
