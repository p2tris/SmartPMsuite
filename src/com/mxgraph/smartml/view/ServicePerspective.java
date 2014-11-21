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

import com.mxgraph.smartml.control.H_Service_Perspective;
import com.mxgraph.smartml.model.XMLParser;


public class ServicePerspective extends JFrame{

	private Container content;
	
	public H_Service_Perspective _handler = null;
	
	private JPanel northPanel;
	private JLabel blankLabel_1;
	private DefaultListModel servicesListModel;
	private JList servicesList;
	private JScrollPane servicesScrollPane;
	private JPanel servicesButtonPanel;
	private JButton addServiceButton;
	private JButton deleteServiceButton;
	private JButton editServiceButton;
	
	private JPanel southPanel;
	private JLabel blankLabel_2;
	private DefaultListModel providersListModel;
	private JList providersList;
	private JScrollPane providersScrollPane;
	private JPanel providersButtonPanel;
	private JButton addProviderButton;
	private JButton deleteProviderButton;
	private JButton editProviderButton;
	
	private JPanel buttonPanel;
	private JButton OkButton;

	
	public ServicePerspective(){
		super();
		initComponent();
		initHandler();		
	}

	private void initComponent() {
		
		content = getContentPane();
		content.setLayout(new FlowLayout());
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
		
		this.setTitle("Service Perspective");
		
		//NORTH PANEL concerning services
		
		blankLabel_1 = new JLabel(" ");
		blankLabel_1.setPreferredSize(new Dimension(470,5));
			
		northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(460,200));
		northPanel.setBorder(new TitledBorder(null, "Services: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		northPanel.setLayout(new FlowLayout());

		servicesListModel = new DefaultListModel();
		loadExistingServices();

		servicesList = new JList(servicesListModel);
		servicesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		servicesList.setSelectedIndex(-1);
		//providersList.setVisibleRowCount(8);		
		
		servicesScrollPane = new JScrollPane(servicesList);
		servicesScrollPane.setPreferredSize(new Dimension(340,150));
		servicesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		servicesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		servicesButtonPanel = new JPanel();
		servicesButtonPanel.setPreferredSize(new Dimension(90,120));
		
		addServiceButton = new JButton("Add");
		addServiceButton.setPreferredSize(new Dimension(70,30));
		editServiceButton = new JButton("Edit");
		editServiceButton.setPreferredSize(new Dimension(70,30));
		deleteServiceButton = new JButton("Delete");
		deleteServiceButton.setPreferredSize(new Dimension(70,30));
		
		servicesButtonPanel.add(addServiceButton);
		servicesButtonPanel.add(editServiceButton);
		servicesButtonPanel.add(deleteServiceButton);
		
		northPanel.add(blankLabel_1);		
		northPanel.add(servicesScrollPane);
		northPanel.add(servicesButtonPanel);
		
		//SOUTH PANEL concerning providers
		
		blankLabel_2 = new JLabel(" ");
		blankLabel_2.setPreferredSize(new Dimension(470,5));
		
		southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(460,200));
		southPanel.setBorder(new TitledBorder(null, "Providers: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		southPanel.setLayout(new FlowLayout());
		
		
		providersListModel = new DefaultListModel();
		loadExistingProviders();
		
		providersList = new JList(providersListModel);
		providersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		providersList.setSelectedIndex(-1);
		//providersList.setVisibleRowCount(8);		
		
        providersScrollPane = new JScrollPane(providersList);
        providersScrollPane.setPreferredSize(new Dimension(340,150));
        providersScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        providersScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		providersButtonPanel = new JPanel();
		providersButtonPanel.setPreferredSize(new Dimension(90,120));
		
		addProviderButton = new JButton("Add");
		addProviderButton.setPreferredSize(new Dimension(70,30));
		editProviderButton = new JButton("Edit");
		editProviderButton.setPreferredSize(new Dimension(70,30));
		deleteProviderButton = new JButton("Delete");
		deleteProviderButton.setPreferredSize(new Dimension(70,30));
		
		providersButtonPanel.add(addProviderButton);
		providersButtonPanel.add(editProviderButton);
		providersButtonPanel.add(deleteProviderButton);
		
		southPanel.add(blankLabel_2);		
		southPanel.add(providersScrollPane);
		southPanel.add(providersButtonPanel);

		//BUTTON PANEL
		
		OkButton = new JButton("OK");
		OkButton.setPreferredSize(new Dimension(70,30));
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(460,100));
		buttonPanel.add(OkButton);
		
		//MAIN PANEL
		
		content.add(northPanel);
		content.add(southPanel);
		content.add(buttonPanel);

		this.setSize(490,490);
		this.setResizable(false);
		
	}
	


	public void loadExistingServices() {
		
		Vector services_vector = XMLParser.getServices(false);
		
		servicesListModel.removeAllElements();
		
		if(!services_vector.isEmpty()){
			for(int i=0; i<services_vector.size();i++){
				servicesListModel.addElement(services_vector.get(i));
			}
	}
	}
	
	public void loadExistingProviders() {
		
        Vector <Vector<String>> providers_vector = XMLParser.getProviders(false);
		
        providersListModel.removeAllElements();
        
        String app1 ="";
		String app2 ="";
		String app3 ="";
		if(!providers_vector.isEmpty()) {
			for(int i=0; i<providers_vector.size();i++){
				app1=providers_vector.get(i).get(0)+" = <";
				for(int in=1;in<providers_vector.get(i).size();in++){
					if(in==providers_vector.get(i).size()-1)
						app2=app2+providers_vector.get(i).get(in);
					else
						app2=app2+providers_vector.get(i).get(in)+",";
			}
			app3=">";
			app1=app1+app2+app3;
			app2="";
			providersListModel.addElement(app1);
		}
		}
		
	}

	public JPanel getNorthPanel() {
		return northPanel;
	}
	
	public JList getServicesList() {
		return servicesList;
	}
	
	public DefaultListModel getServicesListModel() {
		return servicesListModel;
	}

	public JButton getAddServiceButton() {
		return addServiceButton;
	}

	public JButton getDeleteServiceButton() {
		return deleteServiceButton;
	}

	public JButton getEditServiceButton() {
		return editServiceButton;
	}	
	
	public JList getProvidersList() {
		return providersList;
	}
	
	public DefaultListModel getProvidersListModel() {
		return providersListModel;
	}

	public JButton getAddProviderButton() {
		return addProviderButton;
	}

	public JButton getDeleteProviderButton() {
		return deleteProviderButton;
	}

	public JButton getEditProviderButton() {
		return editProviderButton;
	}
	
	public JButton getOkButton() {
		return OkButton;
	}

	private void initHandler() {
		
		_handler = new H_Service_Perspective(this);
		
	}
	
}
