package UI;

import UI.actions.NodePropOKBtnAction;
import UI.actions.NodePropResetBtnAction;
import UI.actions.NodePropSendtoBtnAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 5, 2006
 * Time: 11:30:45 PM
 * To change this template use File | Settings | File Templates.
 * this is a class that implements right panel which shows node properties and have a method
 * for colorChooser action
 */
public class Node_Properties extends JPanel implements ActionListener {

    JButton resetBtn,OkBtn,SendBtn;
    public JTextField IPText,nameText,xText,yText,powerText,sendToText;
    public JTextArea dataText,recievedDataText,statusText;
    public JButton colorBtn;
    private Myform myForm;

    public Node_Properties(Myform myForm) {

        this.myForm = myForm;
        JLabel IPLbl,nameLbl,xLbl,yLbl,powerLbl,colorLbl,sendToLbl,dataLbl,recievedDataLbl,statusLbl;

        //create main layout
        Box v1 = Box.createVerticalBox();
        this.add(v1);

        //create first box labels
        IPLbl = new JLabel("IP: ");
        nameLbl = new JLabel("Name: ");
        xLbl = new JLabel("X :");
        yLbl = new JLabel("Y :");
        powerLbl = new JLabel("Power: ");
        colorLbl = new JLabel("Color: ");

        //create first box textfield
        ActionListener okAction = new NodePropOKBtnAction(this.myForm);
        IPText = new JTextField(8);
        IPText.addActionListener(okAction);
        nameText = new JTextField(8);
        nameText.addActionListener(okAction);
        xText = new JTextField(1);
        xText.addKeyListener(myForm.nkl);
        yText = new JTextField(1);
        yText.addKeyListener(myForm.nkl);
        powerText = new JTextField(3);
        powerText.addKeyListener(myForm.nkl);
        powerText.addActionListener(okAction);
        colorBtn = new JButton();
        colorBtn.setMaximumSize(new Dimension(50,50));
        colorBtn.addActionListener(this);

        //create first box buttons
        OkBtn = new JButton("OK");
        OkBtn.addActionListener(okAction );
        resetBtn = new JButton("Reset");
        resetBtn.addActionListener(new NodePropResetBtnAction(this.myForm));

        //create layout for first box
        JPanel firstBox = new JPanel();
        v1.add(firstBox);
        firstBox.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Node Properties"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));

        Box v11 = Box.createVerticalBox();
        firstBox.add(v11);
        Box h11 = Box.createHorizontalBox();
        h11.add(IPLbl);
        h11.add(Box.createHorizontalGlue());
        h11.add(IPText);
        v11.add(h11);
        Box h12 = Box.createHorizontalBox();
        h12.add(nameLbl);
        h12.add(Box.createHorizontalGlue());
        h12.add(nameText);
        v11.add(h12);
        Box h13 = Box.createHorizontalBox();
        h13.add(xLbl);
        h13.add(Box.createHorizontalStrut(7));
        h13.add(xText);
        h13.add(Box.createHorizontalGlue());
        h13.add(yLbl);
        h13.add(Box.createHorizontalStrut(7));
        h13.add(yText);
        v11.add(h13);
        Box h14 = Box.createHorizontalBox();
        h14.add(powerLbl);
        h14.add(Box.createHorizontalStrut(7));
        h14.add(powerText);
        h14.add(Box.createHorizontalGlue());
        h14.add(colorLbl);
        h14.add(Box.createHorizontalStrut(7));
        h14.add(colorBtn);
        v11.add(h14);
        v11.add(Box.createVerticalStrut(10));
        Box h15 = Box.createHorizontalBox();
        h15.add(Box.createHorizontalGlue());
        h15.add(resetBtn);
        h15.add(Box.createHorizontalStrut(7));
        h15.add(OkBtn);
        v11.add(h15);

        //create second box labels
        dataLbl = new JLabel("Data: ");
        sendToLbl = new JLabel("Send To:");
        //create second box text field
        dataText = new JTextArea(4,12);
        JScrollPane dataSP= new JScrollPane(dataText);
        sendToText = new JTextField(10);
        //create second box buttons
        SendBtn = new JButton("Send");
        SendBtn.addActionListener(new NodePropSendtoBtnAction(this.myForm));
        //create second box layouts
        JPanel secondBox = new JPanel();
        v1.add(secondBox);
        secondBox.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Data to send"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        Box v21 = Box.createVerticalBox();
        secondBox.add(v21);
        Box h21 = Box.createHorizontalBox();
        h21.add(sendToLbl);
        h21.add(sendToText);
        v21.add(h21);
        v21.add(Box.createVerticalStrut(3));
        Box h22 = Box.createHorizontalBox();
        Box v221 = Box.createVerticalBox();
        v221.add(dataLbl);
        v221.add(Box.createVerticalGlue());
        h22.add(v221);
        h22.add(dataSP);
        v21.add(h22);
        v21.add(Box.createVerticalStrut(10));
        Box h23 = Box.createHorizontalBox();
        h23.add(Box.createHorizontalGlue());
        h23.add(SendBtn);
        v21.add(h23);

        //create third box labels
        recievedDataLbl = new JLabel("RData: ");
        statusLbl = new JLabel("Status: ");
        //create third box text fields
        recievedDataText = new JTextArea(5,20);
        recievedDataText.setEditable(false);
        JScrollPane rdataSP= new JScrollPane(recievedDataText);
        statusText = new JTextArea(5,20);
        statusText.setEditable(false);
        JScrollPane statusSP = new JScrollPane(statusText);
        //create third box layouts
        JPanel thirdBox = new JPanel();
        v1.add(thirdBox);
        thirdBox.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Status"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        Box v31 = Box.createVerticalBox();
        thirdBox.add(v31);
        Box h31 = Box.createHorizontalBox();
        Box v311 = Box.createVerticalBox();
        v311.add(recievedDataLbl);
        v311.add(Box.createVerticalGlue());
        h31.add(v311);
        h31.add(rdataSP);
        v31.add(h31);
        v31.add(Box.createVerticalStrut(3));
        Box h32 = Box.createHorizontalBox();
        Box v321 = Box.createVerticalBox();
        v321.add(statusLbl);
        v321.add(Box.createVerticalGlue());
        h32.add(v321);
        h32.add(statusSP);
        v31.add(h32);

    }

    public void actionPerformed(ActionEvent e) {
        Color newColor = JColorChooser.showDialog(
                Node_Properties.this,
                "Choose Background Color",
                colorBtn.getBackground());
        if (newColor != null) {
            colorBtn.setBackground(newColor);
        }
    }

    public void resetNodePropertiest() {
        this.nameText.setText("");

        this.IPText.setText("");
        this.xText.setText("");
        this.yText.setText("");
        this.powerText.setText("");
        this.colorBtn.setBackground(this.getBackground());

        this.nameText.setEnabled(true);

    }

    public void resetNodePanel() {
        resetNodePropertiest();
        this.sendToText.setText("");
        this.dataText.setText("");
        this.recievedDataText.setText("");
        this.statusText.setText("");
    }
}