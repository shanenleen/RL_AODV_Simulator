package UI;

import UI.myobjects.NodeButton;
import UI.myobjects.draganddrop.DropTargetImp;
import UI.myobjects.GraphicalNode;
import UI.myobjects.PowerShower;
import UI.actions.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import simulator.Node;
import logger.StatusManager;

/**
 * Created by IntelliJ IDEA.
 * User: Masoud
 * Date: Aug 5, 2006
 * Time: 11:28:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Myform extends JFrame {

    public final NumberKeyListener nkl = new NumberKeyListener();
    JPanel content;
    MyMap myMap;
    private Node_Properties nodePanel;
    public int xScale = 10;
    public int yScale = 10;
    public final int mapWidth = 550;
    public final int mapHeight = 550;
    public NodeButton newNodeBtn = new NodeButton(new ImageIcon("images/SendingNode0.png"));
    private final List<GraphicalNode> graphicalNodes = new ArrayList<GraphicalNode>();
    private final JTextField minNumber = new JTextField("3", 3);
    private final JCheckBox doubleDirection = new JCheckBox("DoubleDirection", true);
    private final JTextField searchText = new JTextField(8);
    JButton generateBtn;
    JButton delGnodeBtn = new JButton(new ImageIcon("images/delete.png"));
    PowerShower powerShower;
    JToolBar toolBar;
    private GraphicalNode selectedGNode;

    public List<GraphicalNode> getGraphicalNodes() {
        return graphicalNodes;
    }

    /**
     * returns the {@link GraphicalNode} that currently selecteed
     */
    public GraphicalNode getSelectedGNode() {
        return selectedGNode;
    }

    public MyMap getMyMap() {
        return myMap;
    }

    public void refreshPowerShower() {

        this.powerShower.setVisible(false);
        this.powerShower.setXYrXrY(selectedGNode.getLocation().x, selectedGNode.getLocation().y,
                selectedGNode.getNode().getPower() / this.xScale, selectedGNode.getNode().getPower() / this.yScale);
        this.powerShower.setVisible(true);
        this.powerShower.invalidate();
    }

    public void setSelectedGNode(GraphicalNode selectedGNode) {

        this.selectedGNode = selectedGNode;
        if (selectedGNode != null) {
            this.getNodePanel().nameText.setEnabled(selectedGNode.getName().trim().length() == 0);
            selectedGNode.fillNodePanel();
            this.refreshPowerShower();
        } else {
            this.powerShower.setVisible(false);
        }
    }

    public Myform(String title) {

        super(title);
        content = new JPanel(new BorderLayout());
        content.setOpaque(true);
        myMap = new MyMap();
        myMap.setPreferredSize(new Dimension(this.mapWidth, this.mapHeight));
        myMap.setBorder(BorderFactory.createEtchedBorder());
        this.getContentPane().add(content);
        toolBar = new JToolBar();
        toolBar.add(newNodeBtn);
        toolBar.add(delGnodeBtn);
        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(new JLabel("Min Neighbor: "));
        toolBar.add(minNumber);
        toolBar.add(doubleDirection);
        generateBtn = new JButton("Fill Parameter");
        toolBar.add(generateBtn);
        minNumber.addKeyListener(new NumberKeyListener());

        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        toolBar.add(Box.createHorizontalStrut(5));
        toolBar.add(new JLabel("Search: "));
        toolBar.add(searchText);

        content.add(toolBar, BorderLayout.PAGE_START);
        myMap.setDropTarget(new DropTargetImp(myMap));
        myMap.setLayout(null);

    }

    public boolean isDoubleDirection() {
        return this.doubleDirection.isSelected();
    }

    public int getMinNumberForFillParameter() {
        if (this.minNumber.getText().trim().length() > 0) {
            return Integer.parseInt(this.minNumber.getText());
        } else {
            return 0;
        }
    }

    /**
     * finds GraphicalNode in graphicalNode list
     *
     * @param name
     * @return null: if it didn't find the gnode with name<br/>
     * reference to that node if it found
     */
    public GraphicalNode getGNode(String name) {
        for (GraphicalNode graphicalNode : graphicalNodes) {
            if (graphicalNode.getName().equals(name)) {
                return graphicalNode;
            }
        }
        return null;
    }

    public void putGNode(GraphicalNode gNode) {
        graphicalNodes.add(gNode);
    }

    public Node_Properties getNodePanel() {
        return nodePanel;
    }

    public void setNodePanel(Node_Properties nodePanel) {
        this.nodePanel = nodePanel;
    }

    public GraphicalNode getGnodebyNode(Node node) {
        for (GraphicalNode graphicalNode : graphicalNodes) {
            if (graphicalNode.getNode().equals(node)) {
                return graphicalNode;
            }
        }
        return null;
    }

    public JTextField getSearchText() {
        return searchText;
    }

    public static void main(String[] args) {

        Myform frame = new Myform("AODV Simulator");
        frame.newNodeBtn.myForm = frame;
        frame.setNodePanel(new Node_Properties(frame));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, frame.myMap, frame.getNodePanel());
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(550);
        frame.content.add(splitPane, BorderLayout.CENTER);
        frame.myMap.addMouseListener(new PanelAction(frame));
        frame.generateBtn.addActionListener(new InitParameters(frame));
        frame.powerShower = new PowerShower(frame);
        frame.delGnodeBtn.addActionListener(new DeleteBtnAction(frame));
        frame.searchText.addActionListener(new SearchGNodeAction(frame));
        frame.setGlassPane(frame.powerShower);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        MapForm mapForm = new MapForm(frame, "Initializing Map", true, frame);
        mapForm.pack();
        mapForm.setVisible(true);
        StatusManager.init(frame);

    }


}
