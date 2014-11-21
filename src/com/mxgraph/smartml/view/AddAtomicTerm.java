package com.mxgraph.smartml.view;

import java.awt.Dimension;
import java.util.Vector; 
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.mxgraph.smartml.control.H_AddAtomicTerm;
import com.mxgraph.smartml.model.XMLParser;

public class AddAtomicTerm extends JFrame
{

	private static final long serialVersionUID = 1L;

	public H_AddAtomicTerm _handler = null; 
	
	public JPanel contentPane;
	public JPanel panel;
	public JLabel lblName;
	public JTextField name_textField;
	public JLabel lblValue;
	public JPanel panel_1;
	public JComboBox<String> value_combo;
	public JRadioButton rdbtnStatic;
	public JRadioButton rdbtnDynamic;
	public ButtonGroup group;
	public JPanel panel_2;
	public JLabel lblRelevant;
	public JComboBox<String> rel_combo;
	public JPanel panel_3;
	public SpringLayout sl_contentPane;
	public SpringLayout sl_panel;
	public SpringLayout sl_panel_1;
	public SpringLayout sl_panel_2;
	public SpringLayout sl_panel_3;
	public JCheckBox ck1 ;
	public JLabel lblName1;
	public JTextField name_textField1;
	public JLabel lblType1;
	public JComboBox<String> type_cb1;
	public JCheckBox ck2;
	public JLabel lblName2;
	public JTextField name_textField2;
	public JLabel lblType2;
	public JComboBox<String> type_cb2;
	public JCheckBox ck3;
	public JLabel lblName3;
	public JTextField name_textField3;
	public JLabel lblType3;
	public JComboBox<String> type_cb3;
	public JCheckBox ck4;
	public JLabel lblName4;
	public JTextField name_textField4;
	public JLabel lblType4;
	public JComboBox<String> type_cb4;
	public JCheckBox ck5;
	public JLabel lblName5;
	public JTextField name_textField5;
	public JLabel lblType5;
	public JComboBox<String> type_cb5;
	public JButton btnCancel;
	public JButton btnSave;

	
	public AddAtomicTerm(){
		super("Add a new Atomic Term");
		initComponent();
		initHandler();
	}

	public void initHandler() {
		_handler = new H_AddAtomicTerm(this);
		
	}

	private void initComponent() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(450, 530);
		setResizable(false); // LA FINESTRA NON E' RIDIMENSIONABILE
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel, 0, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel, 87, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel, 414, SpringLayout.WEST, contentPane);
		contentPane.add(panel);
		sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		lblName = new JLabel("Name: ");
		sl_panel.putConstraint(SpringLayout.NORTH, lblName, 10, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, lblName, 10, SpringLayout.WEST, panel);
		panel.add(lblName);
		
		name_textField = new JTextField();
		sl_panel.putConstraint(SpringLayout.EAST, name_textField, 172, SpringLayout.EAST, lblName);
		sl_panel.putConstraint(SpringLayout.WEST, name_textField, 6, SpringLayout.EAST, lblName);
		sl_panel.putConstraint(SpringLayout.SOUTH, name_textField, 0, SpringLayout.SOUTH, lblName);
		panel.add(name_textField);
		name_textField.setColumns(10);
		lblName.setLabelFor(name_textField);
		
		lblValue = new JLabel("Value: ");
		sl_panel.putConstraint(SpringLayout.WEST, lblValue, 0, SpringLayout.WEST, lblName);
		sl_panel.putConstraint(SpringLayout.SOUTH, lblValue, -22, SpringLayout.SOUTH, panel);
		panel.add(lblValue);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel_1, 20, SpringLayout.SOUTH, panel);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel_1, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel_1, 60, SpringLayout.SOUTH, panel);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel_1, 414, SpringLayout.WEST, contentPane);
		contentPane.add(panel_1);
		sl_panel_1 = new SpringLayout();
		panel_1.setLayout(sl_panel_1);
		
		value_combo = new JComboBox<String>(XMLParser.getRelevantTypesForReturnValueOfAtomicTerms());
		sl_panel.putConstraint(SpringLayout.NORTH, value_combo, 14, SpringLayout.SOUTH, name_textField);
		sl_panel.putConstraint(SpringLayout.SOUTH, value_combo, -22, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, value_combo, 0, SpringLayout.EAST, name_textField);
		sl_panel.putConstraint(SpringLayout.WEST, value_combo, 7, SpringLayout.EAST, lblValue);
		panel.add(value_combo);
		lblValue.setLabelFor(value_combo);
		
		rdbtnStatic = new JRadioButton("Static");
		sl_panel_1.putConstraint(SpringLayout.WEST, rdbtnStatic, 10, SpringLayout.WEST, panel_1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, rdbtnStatic, -7, SpringLayout.SOUTH, panel_1);
		panel_1.add(rdbtnStatic);
		
		rdbtnDynamic = new JRadioButton("Dynamic");
		sl_panel_1.putConstraint(SpringLayout.NORTH, rdbtnDynamic, 0, SpringLayout.NORTH, rdbtnStatic);
		sl_panel_1.putConstraint(SpringLayout.EAST, rdbtnDynamic, -90, SpringLayout.EAST, panel_1);
		panel_1.add(rdbtnDynamic);
		
		group = new ButtonGroup();
		group.add(rdbtnStatic);
		group.add(rdbtnDynamic);
		
		rdbtnStatic.doClick();
		setVisible(true);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null)); // PROVA
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel_2, 6, SpringLayout.SOUTH, panel_1);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel_2, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel_2, 61, SpringLayout.SOUTH, panel_1);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel_2, 414, SpringLayout.WEST, contentPane);
		contentPane.add(panel_2);
		sl_panel_2 = new SpringLayout();
		panel_2.setLayout(sl_panel_2);
		panel_2.setVisible(false);  // SI ATTIVA SOLO SE "Dynamic"
		
		lblRelevant = new JLabel("Relevant: ");
		panel_2.add(lblRelevant);
		
		Vector<String> relev_value = new Vector<String>();
		relev_value.add("true");
		relev_value.add("false");
		
		rel_combo = new JComboBox<String>(relev_value);
		sl_panel_2.putConstraint(SpringLayout.WEST, rel_combo, 66, SpringLayout.WEST, panel_2);
		sl_panel_2.putConstraint(SpringLayout.EAST, rel_combo, -177, SpringLayout.EAST, panel_2);
		sl_panel_2.putConstraint(SpringLayout.NORTH, lblRelevant, 5, SpringLayout.NORTH, rel_combo);
		sl_panel_2.putConstraint(SpringLayout.EAST, lblRelevant, 1, SpringLayout.WEST, rel_combo);
		sl_panel_2.putConstraint(SpringLayout.NORTH, rel_combo, 10, SpringLayout.NORTH, panel_2);
		panel_2.add(rel_combo);
		lblRelevant.setLabelFor(rel_combo);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Arguments:", TitledBorder.LEFT, TitledBorder.TOP, null, null)); // PROVA
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel_3, 10, SpringLayout.SOUTH, panel_2);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel_3, -39, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel_3, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel_3, 414, SpringLayout.WEST, contentPane);
		contentPane.add(panel_3);
		sl_panel_3 = new SpringLayout();
		panel_3.setLayout(sl_panel_3);
		
		ck1 = new JCheckBox();
		panel_3.add(ck1);
		
		lblName1 = new JLabel("Name: ");
		sl_panel_3.putConstraint(SpringLayout.EAST, ck1, -6, SpringLayout.WEST, lblName1);
		sl_panel_3.putConstraint(SpringLayout.NORTH, lblName1, 10, SpringLayout.NORTH, panel_3);
		sl_panel_3.putConstraint(SpringLayout.WEST, lblName1, 37, SpringLayout.WEST, panel_3);
		panel_3.add(lblName1);
		
		name_textField1 = new JTextField();
		sl_panel_3.putConstraint(SpringLayout.SOUTH, ck1, 0, SpringLayout.SOUTH, name_textField1);
		sl_panel_3.putConstraint(SpringLayout.NORTH, name_textField1, -3, SpringLayout.NORTH, lblName1);
		sl_panel_3.putConstraint(SpringLayout.WEST, name_textField1, 6, SpringLayout.EAST, lblName1);
		sl_panel_3.putConstraint(SpringLayout.EAST, name_textField1, -193, SpringLayout.EAST, panel_3);
		lblName1.setLabelFor(name_textField1);
		panel_3.add(name_textField1);
		name_textField1.setColumns(10);
		
		lblType1 = new JLabel("Type: ");
		sl_panel_3.putConstraint(SpringLayout.NORTH, lblType1, 0, SpringLayout.NORTH, lblName1);
		sl_panel_3.putConstraint(SpringLayout.WEST, lblType1, 6, SpringLayout.EAST, name_textField1);
		panel_3.add(lblType1);
		
		type_cb1 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfAtomicTerms());
		sl_panel_3.putConstraint(SpringLayout.NORTH, type_cb1, -3, SpringLayout.NORTH, lblName1);
		sl_panel_3.putConstraint(SpringLayout.WEST, type_cb1, 6, SpringLayout.EAST, lblType1);
		sl_panel_3.putConstraint(SpringLayout.EAST, type_cb1, -28, SpringLayout.EAST, panel_3);
		lblType1.setLabelFor(type_cb1);
		panel_3.add(type_cb1);
		
		ck2 = new JCheckBox("");
		sl_panel_3.putConstraint(SpringLayout.NORTH, ck2, 11, SpringLayout.SOUTH, ck1);
		sl_panel_3.putConstraint(SpringLayout.WEST, ck2, 0, SpringLayout.WEST, ck1);
		panel_3.add(ck2);
		
		lblName2 = new JLabel("Name: ");
		sl_panel_3.putConstraint(SpringLayout.WEST, lblName2, 0, SpringLayout.WEST, lblName1);
		sl_panel_3.putConstraint(SpringLayout.SOUTH, lblName2, 0, SpringLayout.SOUTH, ck2);
		panel_3.add(lblName2);
		
		name_textField2 = new JTextField();
		lblName2.setLabelFor(name_textField2);
		sl_panel_3.putConstraint(SpringLayout.WEST, name_textField2, 0, SpringLayout.WEST, name_textField1);
		sl_panel_3.putConstraint(SpringLayout.SOUTH, name_textField2, 0, SpringLayout.SOUTH, ck2);
		sl_panel_3.putConstraint(SpringLayout.EAST, name_textField2, 0, SpringLayout.EAST, name_textField1);
		panel_3.add(name_textField2);
		name_textField2.setColumns(10);
		
		lblType2 = new JLabel("Type: ");
		sl_panel_3.putConstraint(SpringLayout.SOUTH, lblType2, 0, SpringLayout.SOUTH, ck2);
		sl_panel_3.putConstraint(SpringLayout.EAST, lblType2, 0, SpringLayout.EAST, lblType1);
		panel_3.add(lblType2);
		
		type_cb2 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfAtomicTerms());
		lblType2.setLabelFor(type_cb2);
		sl_panel_3.putConstraint(SpringLayout.WEST, type_cb2, 0, SpringLayout.WEST, type_cb1);
		sl_panel_3.putConstraint(SpringLayout.SOUTH, type_cb2, 0, SpringLayout.SOUTH, ck2);
		sl_panel_3.putConstraint(SpringLayout.EAST, type_cb2, 0, SpringLayout.EAST, type_cb1);
		panel_3.add(type_cb2);
		
		ck3 = new JCheckBox("");
		sl_panel_3.putConstraint(SpringLayout.NORTH, ck3, 16, SpringLayout.SOUTH, ck2);
		sl_panel_3.putConstraint(SpringLayout.EAST, ck3, 0, SpringLayout.EAST, ck1);
		panel_3.add(ck3);
		
		lblName3 = new JLabel("Name: ");
		sl_panel_3.putConstraint(SpringLayout.WEST, lblName3, 0, SpringLayout.WEST, lblName1);
		sl_panel_3.putConstraint(SpringLayout.SOUTH, lblName3, 0, SpringLayout.SOUTH, ck3);
		panel_3.add(lblName3);
		
		name_textField3 = new JTextField();
		lblName3.setLabelFor(name_textField3);
		sl_panel_3.putConstraint(SpringLayout.WEST, name_textField3, 0, SpringLayout.WEST, name_textField1);
		sl_panel_3.putConstraint(SpringLayout.SOUTH, name_textField3, 0, SpringLayout.SOUTH, ck3);
		sl_panel_3.putConstraint(SpringLayout.EAST, name_textField3, 0, SpringLayout.EAST, name_textField1);
		panel_3.add(name_textField3);
		name_textField3.setColumns(10);
		
		lblType3 = new JLabel("Type: ");
		sl_panel_3.putConstraint(SpringLayout.WEST, lblType3, 0, SpringLayout.WEST, lblType1);
		sl_panel_3.putConstraint(SpringLayout.SOUTH, lblType3, 0, SpringLayout.SOUTH, ck3);
		panel_3.add(lblType3);
		
		type_cb3 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfAtomicTerms());
		lblType3.setLabelFor(type_cb3);
		sl_panel_3.putConstraint(SpringLayout.WEST, type_cb3, 0, SpringLayout.WEST, type_cb1);
		sl_panel_3.putConstraint(SpringLayout.SOUTH, type_cb3, 0, SpringLayout.SOUTH, ck3);
		sl_panel_3.putConstraint(SpringLayout.EAST, type_cb3, 0, SpringLayout.EAST, type_cb1);
		panel_3.add(type_cb3);
		
		ck4 = new JCheckBox("");
		sl_panel_3.putConstraint(SpringLayout.EAST, ck4, 0, SpringLayout.EAST, ck1);
		panel_3.add(ck4);
		
		lblName4 = new JLabel("Name: ");
		sl_panel_3.putConstraint(SpringLayout.SOUTH, ck4, 0, SpringLayout.SOUTH, lblName4);
		sl_panel_3.putConstraint(SpringLayout.WEST, lblName4, 0, SpringLayout.WEST, lblName1);
		panel_3.add(lblName4);
		
		name_textField4 = new JTextField();
		lblName4.setLabelFor(name_textField4);
		sl_panel_3.putConstraint(SpringLayout.NORTH, name_textField4, 24, SpringLayout.SOUTH, name_textField3);
		sl_panel_3.putConstraint(SpringLayout.SOUTH, lblName4, 0, SpringLayout.SOUTH, name_textField4);
		sl_panel_3.putConstraint(SpringLayout.WEST, name_textField4, 0, SpringLayout.WEST, name_textField1);
		sl_panel_3.putConstraint(SpringLayout.EAST, name_textField4, 0, SpringLayout.EAST, name_textField1);
		panel_3.add(name_textField4);
		name_textField4.setColumns(10);
		
		lblType4 = new JLabel("Type: ");
		sl_panel_3.putConstraint(SpringLayout.NORTH, lblType4, 30, SpringLayout.SOUTH, lblType3);
		sl_panel_3.putConstraint(SpringLayout.WEST, lblType4, 0, SpringLayout.WEST, lblType1);
		panel_3.add(lblType4);
		
		type_cb4 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfAtomicTerms());
		lblType4.setLabelFor(type_cb4);
		sl_panel_3.putConstraint(SpringLayout.NORTH, type_cb4, 24, SpringLayout.SOUTH, type_cb3);
		sl_panel_3.putConstraint(SpringLayout.WEST, type_cb4, 0, SpringLayout.WEST, type_cb1);
		sl_panel_3.putConstraint(SpringLayout.EAST, type_cb4, 0, SpringLayout.EAST, type_cb1);
		panel_3.add(type_cb4);
		
		ck5 = new JCheckBox("");
		sl_panel_3.putConstraint(SpringLayout.WEST, ck5, 0, SpringLayout.WEST, ck1);
		panel_3.add(ck5);
		
		lblName5 = new JLabel("Name: ");
		sl_panel_3.putConstraint(SpringLayout.SOUTH, ck5, 0, SpringLayout.SOUTH, lblName5);
		sl_panel_3.putConstraint(SpringLayout.EAST, lblName5, 0, SpringLayout.EAST, lblName1);
		panel_3.add(lblName5);
		
		name_textField5 = new JTextField();
		lblName5.setLabelFor(name_textField5);
		sl_panel_3.putConstraint(SpringLayout.NORTH, name_textField5, 15, SpringLayout.SOUTH, name_textField4);
		sl_panel_3.putConstraint(SpringLayout.SOUTH, lblName5, 0, SpringLayout.SOUTH, name_textField5);
		sl_panel_3.putConstraint(SpringLayout.WEST, name_textField5, 0, SpringLayout.WEST, name_textField1);
		sl_panel_3.putConstraint(SpringLayout.EAST, name_textField5, 0, SpringLayout.EAST, name_textField1);
		panel_3.add(name_textField5);
		name_textField5.setColumns(10);
		
		lblType5 = new JLabel("Type: ");
		sl_panel_3.putConstraint(SpringLayout.NORTH, lblType5, 21, SpringLayout.SOUTH, lblType4);
		sl_panel_3.putConstraint(SpringLayout.WEST, lblType5, 0, SpringLayout.WEST, lblType1);
		panel_3.add(lblType5);
		
		type_cb5 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfAtomicTerms());
		lblType5.setLabelFor(type_cb5);
		sl_panel_3.putConstraint(SpringLayout.NORTH, type_cb5, 15, SpringLayout.SOUTH, type_cb4);
		sl_panel_3.putConstraint(SpringLayout.WEST, type_cb5, 0, SpringLayout.WEST, type_cb1);
		sl_panel_3.putConstraint(SpringLayout.EAST, type_cb5, 0, SpringLayout.EAST, type_cb1);
		panel_3.add(type_cb5);
		
		btnSave = new JButton("Add"); 
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnSave, 6, SpringLayout.SOUTH, panel_3);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnSave, 97, SpringLayout.WEST, contentPane);
		contentPane.add(btnSave);
		
		btnCancel = new JButton("Cancel");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCancel, 0, SpringLayout.NORTH, btnSave);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnCancel, -95, SpringLayout.EAST, contentPane);
		contentPane.add(btnCancel);
		
	
	}
	
	
	
}
