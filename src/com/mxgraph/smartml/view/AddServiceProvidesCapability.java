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
import com.mxgraph.smartml.control.H_EditProvider;
import com.mxgraph.smartml.control.H_Service_Perspective;
import com.mxgraph.smartml.model.XMLParser;

public class AddServiceProvidesCapability extends JFrame{
	
	private H_AddServiceProvidesCapability _handler;
	
	private CapabilitiesPerspective capabilitiesPerspective_view;
	
	private Container content;
	
	private JPanel northPanel;
	
	private JLabel fistBoxLabel;
	private JLabel providesLabel;
	private JLabel secondBoxLabel;
		
	private DefaultListModel servicesListModel;
	private JList servicesList;
	private JScrollPane servicesScrollPane;
	
	private DefaultListModel capabilitiesListModel;
	private JList capabilitiesList;
	private JScrollPane capabilitiesScrollPane;
	
	private JLabel serviceLabel;
	private JLabel capabilitiesLabel;
	private JLabel blankLabel;
	
	private JPanel southPanel;
	private JLabel blankLabel_2;
	private JButton okButton;
	private JButton cancelButton;
		
	public AddServiceProvidesCapability(CapabilitiesPerspective capPersp){
		super();
		this.capabilitiesPerspective_view = capPersp;
		initComponent();
		initHandler();		
	}

	private void initComponent() {
		
		content = getContentPane();
		content.setLayout(new FlowLayout());
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
		
		this.setTitle("Associating services to capabilities");
		
		northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(470,270));
		northPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		northPanel.setLayout(new FlowLayout());
		
		servicesListModel = new DefaultListModel();
		
		fistBoxLabel = new JLabel("Existing Services");
		secondBoxLabel  = new JLabel("Available Capabilities");
		fistBoxLabel.setPreferredSize(new Dimension(255,30));		
		secondBoxLabel.setPreferredSize(new Dimension(180,30));			

		serviceLabel = new JLabel("Selected Service: ");
		serviceLabel.setPreferredSize(new Dimension(400,15));
		capabilitiesLabel = new JLabel("Provided Capabilities: ");
		capabilitiesLabel.setPreferredSize(new Dimension(400,15));
		blankLabel = new JLabel("");
		blankLabel.setPreferredSize(new Dimension(400,10));	
		
		servicesList = new JList(servicesListModel);
		servicesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		servicesList.setSelectedIndex(-1);

		servicesScrollPane = new JScrollPane(servicesList);
		servicesScrollPane.setPreferredSize(new Dimension(180,150));
		servicesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		servicesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		providesLabel = new JLabel("   provides   ");
				
		loadAllServices();
		
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
		northPanel.add(servicesScrollPane);	
		northPanel.add(providesLabel);
		northPanel.add(capabilitiesScrollPane);	
		northPanel.add(blankLabel);
		northPanel.add(serviceLabel);	
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
	
	private void loadAllServices() {

			
			Vector services_vector = XMLParser.getServices(false);
			
			servicesListModel.removeAllElements();
			
			if(!services_vector.isEmpty()){
				for(int i=0; i<services_vector.size();i++){
					String service = (String) services_vector.get(i);
						servicesListModel.addElement(services_vector.get(i));
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
	
public void loadOtherServiceCapabilities(Vector service_capabilities_vector) {

		
		Vector capabilities_vector = XMLParser.getCapabilities(false);
		
		capabilitiesListModel.removeAllElements();
		
		if(!capabilities_vector.isEmpty()){
			for(int i=0; i<capabilities_vector.size();i++){
				String capability = (String) capabilities_vector.get(i);
					if(!service_capabilities_vector.contains(capabilities_vector.get(i)))
						capabilitiesListModel.addElement(capabilities_vector.get(i));				
			}
		}
	
}
	
	
	private void initHandler() {
		
		_handler = new H_AddServiceProvidesCapability(this);
		
	}

	public DefaultListModel getServicesListModel() {
		return servicesListModel;
	}

	public JList getServicesList() {
		return servicesList;
	}

	public DefaultListModel getCapabilitiesListModel() {
		return capabilitiesListModel;
	}

	public JList getCapabilitiesList() {
		return capabilitiesList;
	}
	
	public JLabel getServiceLabel() {
		return serviceLabel;
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
