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

import com.mxgraph.smartml.control.H_AddDataType;
import com.mxgraph.smartml.control.H_AddProvider;
import com.mxgraph.smartml.control.H_EditProvider;
import com.mxgraph.smartml.control.H_Service_Perspective;
import com.mxgraph.smartml.model.XMLParser;

public class AddDataType extends JFrame{
	
	private H_AddDataType _handler;
	
	private DataType dataType_view;
	
	private Container content;
	
	private JPanel northPanel;
	private JLabel blankLabel_1;
	
	private JLabel dataTypeNameLabel;
	private JTextField dataTypeNameTextField;
	
	private JLabel firstBoxLabel;
	private JLabel secondBoxLabel;
		
	private JTextField dataObjectTextField;
	
	private JPanel editButtonPanel;
	private JButton rightButton;
	private JButton leftButton;
	
	private DefaultListModel dataTypeListModel;
	private JList dataTypeList;
	private JScrollPane dataTypeScrollPane;
	
	private JPanel southPanel;
	private JLabel blankLabel_2;
	private JButton okButton;
	private JButton cancelButton;
		
	public AddDataType(DataType dataType){
		super();
		this.dataType_view = dataType;
		initComponent();
		initHandler();		
	}

	private void initComponent() {
		
		content = getContentPane();
		content.setLayout(new FlowLayout());
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
		
		this.setTitle("Add a new Data Type");
		
		northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(470,270));
		northPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		northPanel.setLayout(new FlowLayout());
		
		dataTypeNameLabel = new JLabel(" Name:");
		dataTypeNameLabel.setPreferredSize(new Dimension(50,40));
		dataTypeNameTextField = new JTextField();
		dataTypeNameTextField.setText("'Name of the data type'_type");
		dataTypeNameTextField.setPreferredSize(new Dimension(390,30));
		
		firstBoxLabel = new JLabel("Data Object:");
		secondBoxLabel  = new JLabel("Domain of the Data Type:");
		firstBoxLabel.setPreferredSize(new Dimension(200,30));		
		secondBoxLabel.setPreferredSize(new Dimension(240,30));			

		blankLabel_1 = new JLabel();
		blankLabel_1.setPreferredSize(new Dimension(400,10));
		
		//////
		dataObjectTextField = new JTextField();
		dataObjectTextField.setPreferredSize(new Dimension(100,30));	
		//////
		
		dataTypeListModel = new DefaultListModel();
		
		dataTypeList = new JList(dataTypeListModel);
		dataTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dataTypeList.setSelectedIndex(-1);

		dataTypeScrollPane = new JScrollPane(dataTypeList);
		dataTypeScrollPane.setPreferredSize(new Dimension(240,160));
		dataTypeScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		dataTypeScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		editButtonPanel = new JPanel();
		editButtonPanel.setPreferredSize(new Dimension(100,90));		
		
		
		rightButton = new JButton(" Insert >> ");
		leftButton = new JButton(" Remove << ");	
		
		rightButton.setPreferredSize(new Dimension(95,30));	
		leftButton.setPreferredSize(new Dimension(95,30));	
		
		editButtonPanel.add(rightButton);
		editButtonPanel.add(leftButton);
		
		northPanel.add(dataTypeNameLabel);
		northPanel.add(dataTypeNameTextField);
		//northPanel.add(blankLabel_1);
		northPanel.add(firstBoxLabel);	
		northPanel.add(secondBoxLabel);	
		//northPanel.add(providerServicesScrollPane);	
		northPanel.add(dataObjectTextField);
		northPanel.add(editButtonPanel);
		northPanel.add(dataTypeScrollPane);	

		okButton = new JButton("Add");
		cancelButton = new JButton("Cancel");
		okButton.setPreferredSize(new Dimension(70,30));
		cancelButton.setPreferredSize(new Dimension(70,30));

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
	
	private void initHandler() {		
		_handler = new H_AddDataType(this);
		
	}

	public JTextField getDataTypeNameTextField() {
		return dataTypeNameTextField;
	}

	public JButton getRightButton() {
		return rightButton;
	}

	public JButton getLeftButton() {
		return leftButton;
	}

	public DefaultListModel getDataTypeListModel() {
		return dataTypeListModel;
	}

	public JList getDataTypeList() {
		return dataTypeList;
	}
	
	public JButton getOkButton() {
		return okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public DataType getDataTypeView() {
		return this.dataType_view;
	}

	public JTextField getDataObjectTextField() {
		return dataObjectTextField;
	}

}
