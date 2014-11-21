package com.mxgraph.smartml.view;


import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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

import com.mxgraph.smartml.control.H_CapabilitiesPerspective;
import com.mxgraph.smartml.control.H_Service_Perspective;
import com.mxgraph.smartml.model.XMLParser;


public class CapabilitiesPerspective extends JFrame{

	private Container content;
	
	public H_CapabilitiesPerspective _handler = null;
	
	private JPanel northPanel;
	private JLabel blankLabel_1;
	private DefaultListModel capabilitiesListModel;
	private JList capabilitiesList;
	private JScrollPane capabilitiesScrollPane;
	private JPanel capabilitiesButtonPanel;
	private JButton addCapabilityButton;
	private JButton deleteCapabilityButton;
	//private JButton editCapabilityButton;
	
	private JPanel centerPanel;
	private JLabel blankLabel_2;
	private DefaultListModel providesListModel;
	private JList providesList;
	private JScrollPane providesScrollPane;
	private JPanel providesButtonPanel;
	private JButton addProvidesButton;
	private JButton deleteProvidesButton;
	
	private JPanel southPanel;
	private JLabel blankLabel_3;
	private DefaultListModel requiresListModel;
	private JList requiresList;
	private JScrollPane requiresScrollPane;
	private JPanel requiresButtonPanel;
	private JButton addRequiresButton;
	private JButton deleteRequiresButton;
	
	private JPanel buttonPanel;
	private JButton OkButton;

	
	public CapabilitiesPerspective(){
		super();
		initComponent();
		initHandler();		
	}

	private void initComponent() {
		
		content = getContentPane();
		content.setLayout(new FlowLayout());
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
		
		this.setTitle("Capabilities Perspective");
		
		//NORTH PANEL concerning capabilities
		
		blankLabel_1 = new JLabel(" ");
		blankLabel_1.setPreferredSize(new Dimension(470,5));
			
		northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(460,150));
		northPanel.setBorder(new TitledBorder(null, "Capabilities: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		northPanel.setLayout(new FlowLayout());

		capabilitiesListModel = new DefaultListModel();
		loadExistingCapabilities();

		capabilitiesList = new JList(capabilitiesListModel);
		capabilitiesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		capabilitiesList.setSelectedIndex(-1);
		
		capabilitiesScrollPane = new JScrollPane(capabilitiesList);
		capabilitiesScrollPane.setPreferredSize(new Dimension(340,100));
		capabilitiesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		capabilitiesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		capabilitiesButtonPanel = new JPanel();
		capabilitiesButtonPanel.setPreferredSize(new Dimension(90,80));
		
		addCapabilityButton = new JButton("Add");
		addCapabilityButton.setPreferredSize(new Dimension(70,25));
		//editCapabilityButton = new JButton("Edit");
		//editCapabilityButton.setPreferredSize(new Dimension(70,25));
		deleteCapabilityButton = new JButton("Delete");
		deleteCapabilityButton.setPreferredSize(new Dimension(70,25));
		
		capabilitiesButtonPanel.add(addCapabilityButton);
		//capabilitiesButtonPanel.add(editCapabilityButton);
		capabilitiesButtonPanel.add(deleteCapabilityButton);
		
		northPanel.add(blankLabel_1);		
		northPanel.add(capabilitiesScrollPane);
		northPanel.add(capabilitiesButtonPanel);
		
		//CENTER PANEL concerning associations between capabilities and services
		
		blankLabel_2 = new JLabel(" ");
		blankLabel_2.setPreferredSize(new Dimension(470,5));
		
		centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(460,150));
		centerPanel.setBorder(new TitledBorder(null, "Services Skills: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		centerPanel.setLayout(new FlowLayout());
		
		
		providesListModel = new DefaultListModel();
		loadExistingProvides();
		
		providesList = new JList(providesListModel);
		providesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		providesList.setSelectedIndex(-1);

		//providersList.setVisibleRowCount(8);		
		
        providesScrollPane = new JScrollPane(providesList);
        providesScrollPane.setPreferredSize(new Dimension(340,100));
        providesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        providesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		providesButtonPanel = new JPanel();
		providesButtonPanel.setPreferredSize(new Dimension(90,80));
		
		addProvidesButton = new JButton("Add");
		addProvidesButton.setPreferredSize(new Dimension(70,25));
		deleteProvidesButton = new JButton("Delete");
		deleteProvidesButton.setPreferredSize(new Dimension(70,25));
		
		providesButtonPanel.add(addProvidesButton);
		providesButtonPanel.add(deleteProvidesButton);
		
		centerPanel.add(blankLabel_2);		
		centerPanel.add(providesScrollPane);
		centerPanel.add(providesButtonPanel);
		
		//SOUTH PANEL concerning associations between capabilities and services
		
		blankLabel_3 = new JLabel(" ");
		blankLabel_3.setPreferredSize(new Dimension(470,5));
		
		southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(460,150));
		southPanel.setBorder(new TitledBorder(null, "Capabilities required by tasks: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		southPanel.setLayout(new FlowLayout());
		
		
		requiresListModel = new DefaultListModel();
		loadExistingRequires();
		
		requiresList = new JList(requiresListModel);
		requiresList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		requiresList.setSelectedIndex(-1);

		//providersList.setVisibleRowCount(8);		
		
		requiresScrollPane = new JScrollPane(requiresList);
		requiresScrollPane.setPreferredSize(new Dimension(340,100));
		requiresScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		requiresScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		requiresButtonPanel = new JPanel();
		requiresButtonPanel.setPreferredSize(new Dimension(90,80));
		
		addRequiresButton = new JButton("Add");
		addRequiresButton.setPreferredSize(new Dimension(70,25));
		deleteRequiresButton = new JButton("Delete");
		deleteRequiresButton.setPreferredSize(new Dimension(70,25));
		
		requiresButtonPanel.add(addRequiresButton);
		requiresButtonPanel.add(deleteRequiresButton);
		
		southPanel.add(blankLabel_2);		
		southPanel.add(requiresScrollPane);
		southPanel.add(requiresButtonPanel);

		//BUTTON PANEL
		
		OkButton = new JButton("OK");
		OkButton.setPreferredSize(new Dimension(70,30));
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(460,100));
		buttonPanel.add(OkButton);
		
		//MAIN PANEL
		
		content.add(northPanel);
		content.add(centerPanel);
		content.add(southPanel);
		content.add(buttonPanel);

		this.setSize(490,550);
		this.setResizable(false);
		
	}
	
	public void loadExistingCapabilities() {
		
		boolean lowerCase = true;
		Vector services_vector = XMLParser.getCapabilities(!lowerCase);
		
		capabilitiesListModel.removeAllElements();
		
		if(!services_vector.isEmpty()){
			for(int i=0; i<services_vector.size();i++){
				capabilitiesListModel.addElement(services_vector.get(i));
			}
	}
	}
	
	public void loadExistingProvides() {
		
		Vector <Vector<String>> provides_vector = XMLParser.getAssociationsBetweenServicesAndCapabilities(false);
		
        providesListModel.removeAllElements();
        
		if(!provides_vector.isEmpty()) {
			
			String provides = new String();
			
			for(int i=0; i<provides_vector.size();i++){
				provides = (String) ((Vector)provides_vector.elementAt(i)).elementAt(0) + " -->provides--> " + (String) ((Vector)provides_vector.elementAt(i)).elementAt(1);
			
			providesListModel.addElement(provides);
			}
			
		}
		
	}
	
	public void loadExistingRequires() {
		
		Vector <Vector<String>> requires_vector = XMLParser.getAssociationsBetweenTasksAndCapabilities(false);
		
        requiresListModel.removeAllElements();
        
		if(!requires_vector.isEmpty()) {
			
			String requires = new String();
			
			for(int i=0; i<requires_vector.size();i++){
				requires = (String) ((Vector)requires_vector.elementAt(i)).elementAt(0) + " -->requires--> " + (String) ((Vector)requires_vector.elementAt(i)).elementAt(1);
			
			requiresListModel.addElement(requires);
			}
			
		}
		
	}

	public JPanel getNorthPanel() {
		return northPanel;
	}
	
	public JList getCapabilitiesList() {
		return capabilitiesList;
	}
	
	public DefaultListModel getCapabilitiesListModel() {
		return capabilitiesListModel;
	}

	public JButton getAddCapabilityButton() {
		return addCapabilityButton;
	}

	public JButton getDeleteCapabilityButton() {
		return deleteCapabilityButton;
	}

	/*
	public JButton getEditCapabilityButton() {
		return editCapabilityButton;
	}	
	*/
	public JList getProvidesList() {
		return providesList;
	}
	
	public DefaultListModel getProvidesListModel() {
		return providesListModel;
	}
	
	public JList getRequiresList() {
		return requiresList;
	}
	
	public DefaultListModel getRequiresListModel() {
		return requiresListModel;
	}

	public JButton getAddProvidesButton() {
		return addProvidesButton;
	}

	public JButton getDeleteProvidesButton() {
		return deleteProvidesButton;
	}
	
	public JButton getAddRequiresButton() {
		return addRequiresButton;
	}

	public JButton getDeleteRequiresButton() {
		return deleteRequiresButton;
	}
	
	public JButton getOkButton() {
		return OkButton;
	}

	private void initHandler() {
		
		_handler = new H_CapabilitiesPerspective(this);
		
	}
	
}
