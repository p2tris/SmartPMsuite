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

import com.mxgraph.smartml.control.H_AddCapability;
import com.mxgraph.smartml.control.H_AddProvider;
import com.mxgraph.smartml.control.H_AddService;
import com.mxgraph.smartml.control.H_EditProvider;
import com.mxgraph.smartml.control.H_Service_Perspective;
import com.mxgraph.smartml.model.XMLParser;

public class AddCapability extends JFrame{
	
	private H_AddCapability _handler;
	
	private CapabilitiesPerspective capPersp;
	
    private Container content;
	
	private JPanel northPanel;
	
	private JLabel capabilityNameLabel;
	private JTextField capabilityNameTextField;

	private JPanel southPanel;
	private JButton okButton;
	private JButton cancelButton;
		
	public AddCapability(CapabilitiesPerspective capPersp){
		super();
		this.capPersp = capPersp;
		initComponent();
		initHandler();		
	}

	private void initComponent() {
		
		content = getContentPane();
		content.setLayout(new FlowLayout());
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
		
		this.setTitle("Add a new Capability");
		
		northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(330,60));
		northPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		northPanel.setLayout(new FlowLayout());
		
		capabilityNameLabel = new JLabel(" Name:");
		capabilityNameLabel.setPreferredSize(new Dimension(50,40));
		capabilityNameTextField = new JTextField();
		capabilityNameTextField.setPreferredSize(new Dimension(250,25));
		
		northPanel.add(capabilityNameLabel);
		northPanel.add(capabilityNameTextField);

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
		_handler = new H_AddCapability(this);
	}

	public JTextField getCapabilityNameTextField() {
		return capabilityNameTextField;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public CapabilitiesPerspective getCapabilitiesPerspective() {
		return capPersp;
	}

}
