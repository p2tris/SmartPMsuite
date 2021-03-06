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
import com.mxgraph.smartml.control.H_AddService;
import com.mxgraph.smartml.control.H_EditProvider;
import com.mxgraph.smartml.control.H_Service_Perspective;
import com.mxgraph.smartml.model.XMLParser;

public class AddService extends JFrame{
	
	private H_AddService _handler;
	
	private ServicePerspective servPersp;
	
    private Container content;
	
	private JPanel northPanel;
	
	private JLabel serviceNameLabel;
	private JTextField serviceNameTextField;

	private JPanel southPanel;
	private JButton okButton;
	private JButton cancelButton;
		
	public AddService(ServicePerspective servPersp){
		super();
		this.servPersp = servPersp;
		initComponent();
		initHandler();		
	}

	private void initComponent() {
		
		content = getContentPane();
		content.setLayout(new FlowLayout());
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
		
		this.setTitle("Add a new Service");
		
		northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(330,60));
		northPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		northPanel.setLayout(new FlowLayout());
		
		serviceNameLabel = new JLabel(" Name:");
		serviceNameLabel.setPreferredSize(new Dimension(50,40));
		serviceNameTextField = new JTextField();
		serviceNameTextField.setPreferredSize(new Dimension(250,25));
		
		northPanel.add(serviceNameLabel);
		northPanel.add(serviceNameTextField);
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
		
		this.setSize(350,150);
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
	private void initHandler() {
		
		_handler = new H_AddService(this);
		
	}

	public JTextField getServiceNameTextField() {
		return serviceNameTextField;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public ServicePerspective getServicePerspective() {
		return servPersp;
	}

}
