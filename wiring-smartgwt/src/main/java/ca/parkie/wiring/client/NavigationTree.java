/***********************************************************
 * Wire Tracker
 * Copyright 2011-2014 Christian Parkinson
 * Licensed under the GNU GPL.  See COPYING for full terms.
 ***********************************************************/

package ca.parkie.wiring.client;

import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class NavigationTree extends TreeGrid
{
    private String selectedPath;

    public NavigationTree(final ViewController controller, Collection<Panel> views)
    {
        setWidth100();
        setHeight100();

        setShowOpenIcons(false);
        setShowDropIcons(false);
        setShowSelectedStyle(true);
        setCanSort(false);
        setShowConnectors(true);
        setShowHeader(false);
        setLoadDataOnDemand(false);
        setSelectionType(SelectionStyle.SINGLE);

        int size = views.size();
        List<TreeNode> children = new ArrayList<TreeNode>(size);
        for (Panel p : views)
            children.add(new NavigationNode(p));

        TreeNode[] array = new TreeNode[size];
        TreeNode root = new TreeNode("root", children.toArray(array));

        Tree tree = new Tree();
        tree.setModelType(TreeModelType.CHILDREN);
        tree.setRoot(root);

        setData(tree);

        addNodeClickHandler(new NodeClickHandler()
        {
            public void onNodeClick(NodeClickEvent nodeClickEvent)
            {
                String path = getSelectedPaths();
                if (path.equals(selectedPath))
                    return;

                NavigationNode node = (NavigationNode) nodeClickEvent.getNode();

                // ViewController.showPanel() will return false if the change was vetoed
                if (controller.showPanel(node.panel))
                    selectedPath = path;
                else
                    setSelectedPaths(selectedPath);
            }
        });
    }

    public NavigationTree(ViewController controller, Panel ... views)
    {
        this(controller, Arrays.asList(views));
    }

    private class NavigationNode extends TreeNode
    {
        private Panel panel;

        private NavigationNode(Panel panel)
        {
            super(panel.getName());
            this.panel = panel;
        }
    }
}
