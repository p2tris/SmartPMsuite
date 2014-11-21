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

import com.mxgraph.smartml.control.H_AddProvider;
import com.mxgraph.smartml.control.H_AddServiceProvidesCapability;
import com.mxgraph.smartml.control.H_AddTaskRequiresCapability;
import com.mxgraph.smartml.control.H_EditProvider;
import com.mxgraph.smartml.control.H_Service_Perspective;
import com.mxgraph.smartml.model.XMLParser;

public class AddTaskRequiresCapability extends JFrame{
	
	private H_AddTaskRequiresCapability _handler;
	
	private CapabilitiesPerspective capabilitiesPerspective_view;
	
	private Container content;
	
	private JPanel northPanel;
	
	private JLabel fistBoxLabel;
	private JLabel requiresLabel;
	private JLabel secondBoxLabel;
		
	private DefaultListModel tasksListModel;
	private JList tasksList;
	private JScrollPane tasksScrollPane;
	
	private DefaultListModel capabilitiesListModel;
	private JList capabilitiesList;
	private JScrollPane capabilitiesScrollPane;
	
	private JLabel taskLabel;
	private JLabel capabilitiesLabel;
	private JLabel blankLabel;
	
	private JPanel southPanel;
	private JButton okButton;
	private JButton cancelButton;
		
	public AddTaskRequiresCapability(CapabilitiesPerspective capPersp){
		super();
		this.capabilitiesPerspective_view = capPersp;
		initComponent();
		initHandler();		
	}

	private void initComponent() {
		
		content = getContentPane();
		content.setLayout(new FlowLayout());
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
		
		this.setTitle("Associating tasks to capabilities");
		
		northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(470,270));
		northPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		northPanel.setLayout(new FlowLayout());
		
		tasksListModel = new DefaultListModel();
		
		fistBoxLabel = new JLabel("Existing Services");
		secondBoxLabel  = new JLabel("Available Capabilities");
		fistBoxLabel.setPreferredSize(new Dimension(255,30));		
		secondBoxLabel.setPreferredSize(new Dimension(180,30));			
		
		
		taskLabel = new JLabel("Selected Task: ");
		taskLabel.setPreferredSize(new Dimension(400,15));
		capabilitiesLabel = new JLabel("Required Capabilities: ");
		capabilitiesLabel.setPreferredSize(new Dimension(400,15));
		blankLabel = new JLabel("");
		blankLabel.setPreferredSize(new Dimension(400,10));	
		
		tasksList = new JList(tasksListModel);
		tasksList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tasksList.setSelectedIndex(-1);

		tasksScrollPane = new JScrollPane(tasksList);
		tasksScrollPane.setPreferredSize(new Dimension(180,150));
		tasksScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tasksScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		requiresLabel = new JLabel("   requires   ");
				
		loadAllTasks();
		
		capabilitiesListModel = new DefaultListModel();
		
		capabilitiesList = new JList(capabilitiesListModel);
		capabilitiesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		capabilitiesList.setSelectedIndex(-1);

		capabilitiesScrollPane = new JScrollPane(capabilitiesList);
		capabilitiesScrollPane.setPreferredSize(new Dimension(180,150));
		capabilitiesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		capabilitiesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		loadAllCapabilities();
		
		northPanel.add(fistBoxLabel);	
		northPanel.add(secondBoxLabel);	
		northPanel.add(tasksScrollPane);	
		northPanel.add(requiresLabel);
		northPanel.add(capabilitiesScrollPane);	
		northPanel.add(blankLabel);
		northPanel.add(taskLabel);	
		northPanel.add(capabilitiesLabel);	

		okButton = new JButton("Add");
		cancelButton = new JButton("Cancel");
		okButton.setPreferredSize(new Dimension(70,25));
		cancelButton.setPreferredSize(new Dimension(70,25));
		

		southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(470,80));
		southPanel.setLayout(new FlowLayout());
		southPanel.add(okButton);
		southPanel.add(cancelButton);	
			
		this.add(northPanel);
		this.add(southPanel);		
		
		this.setSize(500,360);
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
	private void loadAllTasks() {

			
			Vector tasks_vector = XMLParser.getTaskNames(false);
			
			tasksListModel.removeAllElements();
			
			if(!tasks_vector.isEmpty()){
				for(int i=0; i<tasks_vector.size();i++){
						tasksListModel.addElement(tasks_vector.get(i));
				}
			}
		
	}
	
	private void loadAllCapabilities() {

		
		Vector capabilities_vector = XMLParser.getCapabilities(false);
		
		capabilitiesListModel.removeAllElements();
		
		if(!capabilities_vector.isEmpty()){
			for(int i=0; i<capabilities_vector.size();i++){
				String capability = (String) capabilities_vector.get(i);
					capabilitiesListModel.addElement(capabilities_vector.get(i));				
			}
		}
	
}
	
public void loadOtherTaskCapabilities(Vector task_capabilities_vector) {

		
		Vector capabilities_vector = XMLParser.getCapabilities(false);
		
		capabilitiesListModel.removeAllElements();
		
		if(!capabilities_vector.isEmpty()){
			for(int i=0; i<capabilities_vector.size();i++){
				String capability = (String) capabilities_vector.get(i);
					if(!task_capabilities_vector.contains(capabilities_vector.get(i)))
						capabilitiesListModel.addElement(capabilities_vector.get(i));
						
			}
		}
	
}
	
	
	private void initHandler() {
		
		_handler = new H_AddTaskRequiresCapability(this);
		
	}

	public DefaultListModel getTasksListModel() {
		return tasksListModel;
	}

	public JList getTasksList() {
		return tasksList;
	}

	public DefaultListModel getCapabilitiesListModel() {
		return capabilitiesListModel;
	}

	public JList getCapabilitiesList() {
		return capabilitiesList;
	}
	
	public JLabel getTaskLabel() {
		return taskLabel;
	}
	public JLabel getCapabilitiesLabel() {
		return capabilitiesLabel;
	}
	
	public JButton getOkButton() {
		return okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public CapabilitiesPerspective getCapabilitiesPerspective() {
		return this.capabilitiesPerspective_view;
	}

}
