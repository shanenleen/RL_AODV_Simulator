package UI.actions;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 14, 2006
 * Time: 11:29:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class NumberKeyListener extends KeyAdapter {


    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar()<48 || e.getKeyChar()>58){
            e.consume();
           // e.setKeyCode(0);
        }
    }
}
