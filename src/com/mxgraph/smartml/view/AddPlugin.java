package com.mxgraph.smartml.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import com.mxgraph.smartml.control.H_AddPlugin;
import com.mxgraph.smartml.control.H_AddProvider;
import com.mxgraph.smartml.control.H_AddService;
import com.mxgraph.smartml.control.H_EditProvider;
import com.mxgraph.smartml.control.H_Service_Perspective;
import com.mxgraph.smartml.model.XMLParser;

public class AddPlugin extends JFrame{
	
	private H_AddPlugin _handler;
	
	private PluginsManager pluginMngr;
	
    private Container content;
	
	private JPanel northPanel;
	
	private JLabel pluginNameLabel;
	private JTextField pluginNameTextField;
	private JLabel pluginURLNameLabel;
	private JTextField pluginURLNameTextField;
	private JLabel pluginDescriptionNameLabel;
	private JTextArea pluginDescriptionArea;
	private JScrollPane pluginDescriptionScrollPane;

	private JPanel southPanel;
	private JButton okButton;
	private JButton cancelButton;
		
	public AddPlugin(PluginsManager plugManager){
		super();
		this.pluginMngr = plugManager;
		initComponent();
		initHandler();		
	}

	private void initComponent() {
		
		content = getContentPane();
		content.setLayout(new FlowLayout());
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
		
		this.setTitle("Add a new Plugin");
		
		northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(330,190));
		northPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		northPanel.setLayout(new FlowLayout());
		
		pluginNameLabel = new JLabel(" Name:");
		pluginNameLabel.setPreferredSize(new Dimension(50,40));
		pluginNameTextField = new JTextField();
		pluginNameTextField.setPreferredSize(new Dimension(250,25));
		
		pluginURLNameLabel = new JLabel(" URL:");
		pluginURLNameLabel.setPreferredSize(new Dimension(50,40));
		pluginURLNameTextField = new JTextField();
		pluginURLNameTextField.setPreferredSize(new Dimension(250,25));
		
		pluginDescriptionNameLabel = new JLabel(" Description:");
		pluginDescriptionNameLabel.setPreferredSize(new Dimension(308,20));
		
		pluginDescriptionArea = new JTextArea(3,23);
		pluginDescriptionScrollPane = new JScrollPane();
		pluginDescriptionScrollPane.setViewportView(pluginDescriptionArea);
		pluginDescriptionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		pluginDescriptionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		northPanel.add(pluginNameLabel);
		northPanel.add(pluginNameTextField);
		northPanel.add(pluginURLNameLabel);
		northPanel.add(pluginURLNameTextField);
		northPanel.add(pluginDescriptionNameLabel);
		northPanel.add(pluginDescriptionScrollPane);
		//northPanel.add(blankLabel_1);

		okButton = new JButton("Add");
		cancelButton = new JButton("Cancel");
		okButton.setPreferredSize(new Dimension(70,25));
		cancelButton.setPreferredSize(new Dimension(70,25));
		
		southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(340,90));
		southPanel.setLayout(new FlowLayout());
		southPanel.add(okButton);
		southPanel.add(cancelButton);	
			
		this.add(northPanel);
		this.add(southPanel);		
		
		this.setSize(350,270);
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
	private void initHandler() {
		
		_handler = new H_AddPlugin(this);
		
	}

	public JTextField getPluginNameTextField() {
		return pluginNameTextField;
	}
	
	public JTextField getPluginURLNameTextField() {
		return pluginURLNameTextField;
	}

	public JTextArea getPluginDescriptionArea() {
		return pluginDescriptionArea;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public PluginsManager getPluginManager() {
		return pluginMngr;
	}

}
