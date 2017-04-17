package UI;

import UI.actions.NumberKeyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 14, 2006
 * Time: 6:08:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapForm extends JDialog implements ActionListener, PropertyChangeListener {

    JTextField widthText = new JTextField("3000", 4);
    JTextField heightText = new JTextField("3000", 4);
    int mapWidth, mapHeight;
    private String instruction = "Please adjust Map's width and height";
    private String widthString = "Width: ";
    private String heightString = "Height: ";
    private String cancelString = "Cancel";
    private String okString = "OK";
    private JOptionPane optionPane;
    Myform myform;

    public MapForm(Myform owner, String title, boolean modal, Myform myForm) throws HeadlessException {

        super(owner, title, modal);
        this.myform = myForm;
//        JPanel content = new JPanel();
//        getContentPane().add(content);
//        Box h0 = Box.createHorizontalBox();
//        h0.add(Box.createHorizontalGlue());
//        JLabel instructionLbl = new JLabel("Please adjust Map's width and height");
//        h0.add(instructionLbl);
//        Box v1 = Box.createVerticalBox();
//        content.add(v1);
//        v1.add(h0);
//        ////
//        JLabel widthLbl = new JLabel("Width: ");
//        widthText = new JTextField("3000");
//        Box h1 = Box.createHorizontalBox();
//        h1.add(widthLbl);
//        h1.add(widthText);
//        v1.add(h1);
//        ////
//        JLabel heightLbl = new JLabel("Height: ");
//        heightText = new JTextField("3000");
//        Box h2 = Box.createHorizontalBox();
//        h2.add(heightLbl);
//        h2.add(heightText);
//        v1.add(h2);
//        ///
//        v1.add(Box.createVerticalStrut(20));
//        ///
//        JButton cancelBtn = new JButton("Cancel");
//        cancelBtn.setDefaultCapable(false);
//        JButton okBtn = new JButton("OK");
//        okBtn.setDefaultCapable(true);
//        Box h3 = Box.createHorizontalBox();
//        h3.add(Box.createHorizontalGlue());
//        h3.add(cancelBtn);
//        h3.add(okBtn);
//        v1.add(h3);

        widthText.addKeyListener(myForm.nkl);
        heightText.addKeyListener(myForm.nkl);
        Object[] array = {instruction, widthString, widthText, heightString, heightText};

        //Create an array specifying the number of dialog buttons
        //and their text.
        Object[] options = {cancelString, okString};

        //Create the JOptionPane.
        optionPane = new JOptionPane(array,
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION,
                null,
                options,
                options[1]);

        //Make this dialog display it.
        setContentPane(optionPane);
        //Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                /*
                 * Instead of directly closing the window,
                 * we're going to change the JOptionPane's
                 * value property.
                 */
                optionPane.setValue(JOptionPane.CLOSED_OPTION);
            }
        });

        //Ensure the text field always gets the first focus.
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent ce) {
                widthText.requestFocusInWindow();
            }
        });

        //Register an event handler that puts the text into the option pane.
        widthText.addActionListener(this);
        heightText.addActionListener(this);
        //Register an event handler that reacts to option pane state changes.
        optionPane.addPropertyChangeListener(this);

    }

    /**
     * This method handles events for the text field.
     */
    public void actionPerformed(ActionEvent e) {
        optionPane.setValue(okString);
    }

    /**
     * This method reacts to state changes in the option pane.
     */
    public void propertyChange(PropertyChangeEvent e) {

        String prop = e.getPropertyName();

        if (isVisible()
                && (e.getSource() == optionPane)
                && (JOptionPane.VALUE_PROPERTY.equals(prop) ||
                JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
            Object value = optionPane.getValue();

            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                //ignore reset
                return;
            }

            //Reset the JOptionPane's value.
            //If you don't do this, then if the user
            //presses the same button next time, no
            //property change event will be fired.
            optionPane.setValue(
                    JOptionPane.UNINITIALIZED_VALUE);

            if (okString.equals(value)) {
                mapWidth = Integer.parseInt(widthText.getText());
                mapHeight = Integer.parseInt(heightText.getText());
                clearAndHide();
                this.myform.xScale = mapWidth / this.myform.mapWidth;
                this.myform.yScale = mapHeight / this.myform.mapHeight;
                this.myform.setVisible(true);
            } else {
                clearAndHide();
                System.exit(0);
            }
        }
    }

    /**
     * This method clears the dialog and hides it.
     */
    public void clearAndHide() {
        widthText.setText(null);
        heightText.setText(null);
        setVisible(false);
    }

}
