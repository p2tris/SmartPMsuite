package com.mxgraph.smartml.view;


import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import com.mxgraph.smartml.control.H_PluginsManager;
import com.mxgraph.smartml.control.H_Service_Perspective;
import com.mxgraph.smartml.model.XMLParser;


public class PluginsManager extends JFrame{

	private Container content;
	
	public H_PluginsManager _handler = null;
	
	private JPanel northPanel;
	private JLabel blankLabel_1;
	private DefaultListModel pluginsListModel;
	private JList pluginsList;
	private JScrollPane pluginsScrollPane;
	private JPanel pluginsButtonPanel;
	private JButton addPluginButton;
	private JButton deletePluginButton;
	
	private JPanel buttonPanel;
	private JButton OkButton;

	
	public PluginsManager(){
		super();
		initComponent();
		initHandler();		
	}

	private void initComponent() {
		
		content = getContentPane();
		content.setLayout(new FlowLayout());
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
		
		this.setTitle("Configure SmartPM Plugins");
		
		//NORTH PANEL concerning plugins
		
		blankLabel_1 = new JLabel(" ");
		blankLabel_1.setPreferredSize(new Dimension(470,5));
			
		northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(460,200));
		northPanel.setBorder(new TitledBorder(null, "Available Plugins: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		northPanel.setLayout(new FlowLayout());

		pluginsListModel = new DefaultListModel();
		loadExistingPlugins();

		pluginsList = new JList(pluginsListModel);
		pluginsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pluginsList.setSelectedIndex(-1);
		//providersList.setVisibleRowCount(8);		
		
		pluginsScrollPane = new JScrollPane(pluginsList);
		pluginsScrollPane.setPreferredSize(new Dimension(340,150));
		pluginsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pluginsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		pluginsButtonPanel = new JPanel();
		pluginsButtonPanel.setPreferredSize(new Dimension(90,120));
		
		addPluginButton = new JButton("Add");
		addPluginButton.setPreferredSize(new Dimension(70,30));
		deletePluginButton = new JButton("Delete");
		deletePluginButton.setPreferredSize(new Dimension(70,30));
		
		pluginsButtonPanel.add(addPluginButton);
		pluginsButtonPanel.add(deletePluginButton);
		
		northPanel.add(blankLabel_1);		
		northPanel.add(pluginsScrollPane);
		northPanel.add(pluginsButtonPanel);
		
		//BUTTON PANEL
		
		OkButton = new JButton("OK");
		OkButton.setPreferredSize(new Dimension(70,30));
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(460,100));
		buttonPanel.add(OkButton);
		
		//MAIN PANEL
		
		content.add(northPanel);
		content.add(buttonPanel);

		this.setSize(490,290);
		this.setVisible(true);
		this.setResizable(false);
		
	}
	
	public void loadExistingPlugins() {
		
		Vector plugins_vector = XMLParser.getPlugins();
		
		pluginsListModel.removeAllElements();
		
		if(!plugins_vector.isEmpty()){
			for(int i=0; i<plugins_vector.size();i++){

				Vector single_plugin_vector = (Vector) plugins_vector.get(i);					
				pluginsListModel.addElement(single_plugin_vector.firstElement());
			}
	}
	}
	
	public JPanel getNorthPanel() {
		return northPanel;
	}
	
	public JList getPluginList() {
		return pluginsList;
	}
	
	public DefaultListModel getPluginsListModel() {
		return pluginsListModel;
	}

	public JButton getAddPluginButton() {
		return addPluginButton;
	}

	public JButton getDeletePluginButton() {
		return deletePluginButton;
	}
	
	public JButton getOkButton() {
		return OkButton;
	}

	private void initHandler() {
		
		_handler = new H_PluginsManager(this);
		
	}
	
}
