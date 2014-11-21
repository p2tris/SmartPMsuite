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

import com.mxgraph.smartml.control.H_EditProvider;
import com.mxgraph.smartml.control.H_Service_Perspective;
import com.mxgraph.smartml.model.XMLParser;

public class EditProvider extends JFrame{
	
	private H_EditProvider _handler;
	
	private ServicePerspective servicePerspective_view;
	
	private String providerWithAssociatedServices;
	private String originalProvider;
	private Vector originalProviderServicesVector;
	
	private Container content;
	
	private JPanel northPanel;
	private JLabel blankLabel_1;
	
	private JLabel providerNameLabel;
	private JTextField providerNameTextField;
	
	private JLabel fistBoxLabel;
	private JLabel secondBoxLabel;
		
	private DefaultListModel providerServicesListModel;
	private JList providerServicesList;
	private JScrollPane providerServicesScrollPane;
	
	private JPanel editButtonPanel;
	private JButton rightButton;
	private JButton leftButton;
	
	private DefaultListModel otherServicesListModel;
	private JList otherServicesList;
	private JScrollPane otherServicesScrollPane;
	
	private JPanel southPanel;
	private JLabel blankLabel_2;
	private JButton okButton;
	private JButton cancelButton;
		
	public EditProvider(ServicePerspective servPersp, String providerWithAssociatedServices){
		super();
		this.servicePerspective_view = servPersp;
		this.providerWithAssociatedServices = providerWithAssociatedServices;
		initComponent();
		initHandler();		
	}

	private void initComponent() {
		
		content = getContentPane();
		content.setLayout(new FlowLayout());
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); 

		
		northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(470,270));
		northPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		northPanel.setLayout(new FlowLayout());
		
		providerServicesListModel = new DefaultListModel();
		loadProviderServices();
		
		providerNameLabel = new JLabel(" Name:");
		providerNameLabel.setPreferredSize(new Dimension(50,40));
		providerNameTextField = new JTextField();
		providerNameTextField.setText(originalProvider);
		providerNameTextField.setPreferredSize(new Dimension(390,25));
		
		fistBoxLabel = new JLabel("Provider's Services");
		secondBoxLabel  = new JLabel("Other Services");
		fistBoxLabel.setPreferredSize(new Dimension(255,30));		
		secondBoxLabel.setPreferredSize(new Dimension(180,30));			

		blankLabel_1 = new JLabel();
		blankLabel_1.setPreferredSize(new Dimension(400,10));
		
		providerServicesList = new JList(providerServicesListModel);
		providerServicesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		providerServicesList.setSelectedIndex(-1);

		providerServicesScrollPane = new JScrollPane(providerServicesList);
		providerServicesScrollPane.setPreferredSize(new Dimension(180,150));
		providerServicesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		providerServicesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		otherServicesListModel = new DefaultListModel();
		loadOtherServices();
		
		otherServicesList = new JList(otherServicesListModel);
		otherServicesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		otherServicesList.setSelectedIndex(-1);

		otherServicesScrollPane = new JScrollPane(otherServicesList);
		otherServicesScrollPane.setPreferredSize(new Dimension(180,150));
		otherServicesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		otherServicesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		editButtonPanel = new JPanel();
		editButtonPanel.setPreferredSize(new Dimension(70,90));		
		rightButton = new JButton(" >> ");
		leftButton = new JButton(" << ");

		editButtonPanel.add(rightButton);
		editButtonPanel.add(leftButton);
		
		northPanel.add(providerNameLabel);
		northPanel.add(providerNameTextField);
		//northPanel.add(blankLabel_1);
		northPanel.add(fistBoxLabel);	
		northPanel.add(secondBoxLabel);	
		northPanel.add(providerServicesScrollPane);	
		northPanel.add(editButtonPanel);
		northPanel.add(otherServicesScrollPane);	

		okButton = new JButton("Update");
		cancelButton = new JButton("Cancel");
		okButton.setPreferredSize(new Dimension(75,25));
		cancelButton.setPreferredSize(new Dimension(75,25));

		southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(470,80));
		southPanel.setLayout(new FlowLayout());
		southPanel.add(okButton);
		southPanel.add(cancelButton);	
			
		this.add(northPanel);
		this.add(southPanel);		
		
		this.setSize(500,360);		
		this.setTitle("Edit Provider '" + originalProvider + "'");
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
	private void loadProviderServices() {
		String[] split = providerWithAssociatedServices.split(" = ");
	   	originalProvider = split[0];
   	
	   	originalProviderServicesVector = new Vector();
	   	
	   	if (!providerWithAssociatedServices.contains("<>")) {
		
	   	if (providerWithAssociatedServices.contains(",")) {
	   		String[] split_2 = split[1].split(",");
	   		for(int i=0;i<split_2.length;i++){
	   			if(i==0){ 
	   				String first = split_2[i].substring(1);
	   				originalProviderServicesVector.addElement(first);
	   				providerServicesListModel.addElement(first);
	   			
	   			}
	   			else if(i==split_2.length-1) { 
	   				String end = split_2[i].substring(0,split_2[i].length()-1);
	   				originalProviderServicesVector.addElement(end);	   		
	   				providerServicesListModel.addElement(end);
	   			}
	   			else {
	   				originalProviderServicesVector.addElement(split_2[i]);	   		
	   				providerServicesListModel.addElement(split_2[i]);
	   			}
	   		
	   		}

	   	}
	   	else{ //ONLY ONE SERVICE IS PART OF THE PROVIDER
	   		String[] split_2 = split[1].split(">");
	   				originalProviderServicesVector.addElement(split_2[0].substring(1));	   		
					providerServicesListModel.addElement(split_2[0].substring(1));
	   	}
	 	}
	}

	private void loadOtherServices() {

			
			Vector services_vector = XMLParser.getServices(false);
			
			otherServicesListModel.removeAllElements();
			
			if(!services_vector.isEmpty()){
				for(int i=0; i<services_vector.size();i++){
					String service = (String) services_vector.get(i);
					if(!originalProviderServicesVector.contains(service))
						otherServicesListModel.addElement(services_vector.get(i));
				}
		}

		
	}
	
	
	private void initHandler() {
		
		_handler = new H_EditProvider(this);
		
	}

	public String getOriginalProvider() {
		return originalProvider;
	}

	public Vector getOriginalProviderServicesVector() {
		return originalProviderServicesVector;
	}

	public JTextField getProviderNameTextField() {
		return providerNameTextField;
	}

	public DefaultListModel getProviderServicesListModel() {
		return providerServicesListModel;
	}

	public JList getProviderServicesList() {
		return providerServicesList;
	}

	public JButton getRightButton() {
		return rightButton;
	}

	public JButton getLeftButton() {
		return leftButton;
	}

	public DefaultListModel getOtherServicesListModel() {
		return otherServicesListModel;
	}

	public JList getOtherServicesList() {
		return otherServicesList;
	}
	
	public JButton getOkButton() {
		return okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public String getProviderWithAssociatedServices() {
		return providerWithAssociatedServices;
	}

	public ServicePerspective getServicePerspective() {
		return this.servicePerspective_view;
	}

}
