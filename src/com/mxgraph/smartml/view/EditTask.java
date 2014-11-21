package com.mxgraph.smartml.view;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.mxgraph.smartml.control.H_EditTask;
import com.mxgraph.smartml.model.XMLParser;

public class EditTask extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public H_EditTask _handler = null; 
	
	public JPanel contentPane;
	public JLabel lblName;
	public JTextField name_textField;
	public JPanel panel;
	public JPanel panel_1;
	public JPanel panel_2;
	public JScrollPane prec_scrollPane;
	public JTextArea prec_textArea;
	public JPanel panel_3;
	public JLabel lblNameArg;
	public JTextField argName_textField;
	public JLabel lblType;
	public JComboBox<String> argType_comboBox;
	public SpringLayout sl_contentPane;
	public SpringLayout sl_panel;
	public SpringLayout sl_panel_1;
	public SpringLayout sl_panel_2;
	public SpringLayout sl_panel_3;
	public JCheckBox ck1 ;
	public JLabel lblArgName1;
	public JTextField arg_name_textField1;
	public JLabel lblArgType1;
	public JComboBox<String> arg_type_cb1;
	public JCheckBox ck2;
	public JLabel lblArgName2;
	public JTextField arg_name_textField2;
	public JLabel lblArgType2;
	public JComboBox<String> arg_type_cb2;
	public JCheckBox ck3;
	public JLabel lblArgName3;
	public JTextField arg_name_textField3;
	public JLabel lblArgType3;
	public JComboBox<String> arg_type_cb3;
	public JCheckBox ck4;
	public JLabel lblArgName4;
	public JTextField arg_name_textField4;
	public JLabel lblArgType4;
	public JComboBox<String> arg_type_cb4;
	public JCheckBox ck5;
	public JLabel lblArgName5;
	public JTextField arg_name_textField5;
	public JLabel lblArgType5;
	public JComboBox<String> arg_type_cb5;
	public JButton btnAdd_preconds;
	public JScrollPane effSupp_scrollPane;
	public JTextArea effSupp_textArea;
	public JLabel lblSupposedeffects;
	public JLabel lblAutomaticeffects;
	public JScrollPane effAuto_scrollPane;
	public JTextArea effAuto_textArea;
	public JButton btnAdd_effects;
	public JButton btnCancel;
	public JButton btnUpdt;
	
	public Vector<Vector<String>> vec_parIn; // per memorizzare gli argomenti in ingresso al task e i relativi tipi
	public Vector<Vector<String>> vec_dtls; // per memorizzare i dattagli del task, nello specifico le precondizioni, i supp-effects e gli auto -effects
	public String old_name; // per memorizzare il vecchio nome del task 
	
	public EditTask(String task_name){
		super("Update Task '" + task_name + "'");
		initComponent(task_name);
		initHandler();
	}

	public void initHandler() {
		_handler = new H_EditTask(this); 
		
	}

	private void initComponent(String n) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(500, 700);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		panel = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel, 5, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel, 15, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel, 64, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel, -5, SpringLayout.EAST, contentPane);
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel);
		sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		lblName = new JLabel("Name: ");
		panel.add(lblName);
		
		name_textField = new JTextField(n); // METTE IL NOME DEL TASK
		sl_panel.putConstraint(SpringLayout.NORTH, lblName, 3, SpringLayout.NORTH, name_textField);
		sl_panel.putConstraint(SpringLayout.EAST, lblName, -6, SpringLayout.WEST, name_textField);
		sl_panel.putConstraint(SpringLayout.WEST, name_textField, 99, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, name_textField, -119, SpringLayout.EAST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, name_textField, -10, SpringLayout.SOUTH, panel);
		lblName.setLabelFor(name_textField);
		panel.add(name_textField);
		name_textField.setColumns(10);
		
		panel_1 = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel_1, 6, SpringLayout.SOUTH, panel);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel_1, 0, SpringLayout.WEST, panel);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel_1, -358, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel_1, -5, SpringLayout.EAST, contentPane);
		panel_1.setBorder(new TitledBorder(null, "Arguments:", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		contentPane.add(panel_1);
		sl_panel_1 = new SpringLayout();
		panel_1.setLayout(sl_panel_1);
		
		ck1 = new JCheckBox();
		panel_1.add(ck1);
		
		lblArgName1 = new JLabel("Name: ");
		sl_panel_1.putConstraint(SpringLayout.EAST, ck1, -6, SpringLayout.WEST, lblArgName1);
		sl_panel_1.putConstraint(SpringLayout.NORTH, lblArgName1, 10, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.WEST, lblArgName1, 37, SpringLayout.WEST, panel_1);
		panel_1.add(lblArgName1);
		
		arg_name_textField1 = new JTextField();
		sl_panel_1.putConstraint(SpringLayout.SOUTH, ck1, 0, SpringLayout.SOUTH, arg_name_textField1);
		sl_panel_1.putConstraint(SpringLayout.NORTH, arg_name_textField1, -3, SpringLayout.NORTH, lblArgName1);
		sl_panel_1.putConstraint(SpringLayout.WEST, arg_name_textField1, 6, SpringLayout.EAST, lblArgName1);
		sl_panel_1.putConstraint(SpringLayout.EAST, arg_name_textField1, -193, SpringLayout.EAST, panel_1);
		lblArgName1.setLabelFor(arg_name_textField1);
		panel_1.add(arg_name_textField1);
		arg_name_textField1.setColumns(10);
		
		lblArgType1 = new JLabel("Type: ");
		sl_panel_1.putConstraint(SpringLayout.NORTH, lblArgType1, 0, SpringLayout.NORTH, lblArgName1);
		sl_panel_1.putConstraint(SpringLayout.WEST, lblArgType1, 6, SpringLayout.EAST, arg_name_textField1);
		panel_1.add(lblArgType1);
		
		arg_type_cb1 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfTasks());
		sl_panel_1.putConstraint(SpringLayout.NORTH, arg_type_cb1, -3, SpringLayout.NORTH, lblArgName1);
		sl_panel_1.putConstraint(SpringLayout.WEST, arg_type_cb1, 6, SpringLayout.EAST, lblArgType1);
		sl_panel_1.putConstraint(SpringLayout.EAST, arg_type_cb1, -28, SpringLayout.EAST, panel_1);
		lblArgType1.setLabelFor(arg_type_cb1);
		panel_1.add(arg_type_cb1);
		
		ck2 = new JCheckBox("");
		sl_panel_1.putConstraint(SpringLayout.NORTH, ck2, 11, SpringLayout.SOUTH, ck1);
		sl_panel_1.putConstraint(SpringLayout.WEST, ck2, 0, SpringLayout.WEST, ck1);
		panel_1.add(ck2);
		
		lblArgName2 = new JLabel("Name: ");
		sl_panel_1.putConstraint(SpringLayout.WEST, lblArgName2, 0, SpringLayout.WEST, lblArgName1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, lblArgName2, 0, SpringLayout.SOUTH, ck2);
		panel_1.add(lblArgName2);
		
		arg_name_textField2 = new JTextField();
		sl_panel_1.putConstraint(SpringLayout.WEST, arg_name_textField2, 0, SpringLayout.WEST, arg_name_textField1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, arg_name_textField2, 0, SpringLayout.SOUTH, ck2);
		sl_panel_1.putConstraint(SpringLayout.EAST, arg_name_textField2, 0, SpringLayout.EAST, arg_name_textField1);
		lblArgName2.setLabelFor(arg_name_textField2);
		panel_1.add(arg_name_textField2);
		arg_name_textField2.setColumns(10);
		
		lblArgType2 = new JLabel("Type: ");
		sl_panel_1.putConstraint(SpringLayout.SOUTH, lblArgType2, 0, SpringLayout.SOUTH, ck2);
		sl_panel_1.putConstraint(SpringLayout.EAST, lblArgType2, 0, SpringLayout.EAST, lblArgType1);
		panel_1.add(lblArgType2);
		
		arg_type_cb2 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfTasks());
		sl_panel_1.putConstraint(SpringLayout.WEST, arg_type_cb2, 0, SpringLayout.WEST, arg_type_cb1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, arg_type_cb2, 0, SpringLayout.SOUTH, ck2);
		sl_panel_1.putConstraint(SpringLayout.EAST, arg_type_cb2, 0, SpringLayout.EAST, arg_type_cb1);
		lblArgType2.setLabelFor(arg_type_cb2);
		panel_1.add(arg_type_cb2);
		
		ck3 = new JCheckBox("");
		sl_panel_1.putConstraint(SpringLayout.NORTH, ck3, 16, SpringLayout.SOUTH, ck2);
		sl_panel_1.putConstraint(SpringLayout.EAST, ck3, 0, SpringLayout.EAST, ck1);
		panel_1.add(ck3);
		
		lblArgName3 = new JLabel("Name: ");
		sl_panel_1.putConstraint(SpringLayout.WEST, lblArgName3, 0, SpringLayout.WEST, lblArgName1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, lblArgName3, 0, SpringLayout.SOUTH, ck3);
		panel_1.add(lblArgName3);
		
		arg_name_textField3 = new JTextField();
		sl_panel_1.putConstraint(SpringLayout.WEST, arg_name_textField3, 0, SpringLayout.WEST, arg_name_textField1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, arg_name_textField3, 0, SpringLayout.SOUTH, ck3);
		sl_panel_1.putConstraint(SpringLayout.EAST, arg_name_textField3, 0, SpringLayout.EAST, arg_name_textField1);
		lblArgName3.setLabelFor(arg_name_textField3);
		panel_1.add(arg_name_textField3);
		arg_name_textField3.setColumns(10);
		
		lblArgType3 = new JLabel("Type: ");
		sl_panel_1.putConstraint(SpringLayout.WEST, lblArgType3, 0, SpringLayout.WEST, lblArgType1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, lblArgType3, 0, SpringLayout.SOUTH, ck3);
		panel_1.add(lblArgType3);
		
		arg_type_cb3 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfTasks());
		sl_panel_1.putConstraint(SpringLayout.WEST, arg_type_cb3, 0, SpringLayout.WEST, arg_type_cb1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, arg_type_cb3, 0, SpringLayout.SOUTH, ck3);
		sl_panel_1.putConstraint(SpringLayout.EAST, arg_type_cb3, 0, SpringLayout.EAST, arg_type_cb1);
		lblArgType3.setLabelFor(arg_type_cb3);
		panel_1.add(arg_type_cb3);
		
		ck4 = new JCheckBox("");
		sl_panel_1.putConstraint(SpringLayout.EAST, ck4, 0, SpringLayout.EAST, ck1);
		panel_1.add(ck4);
		
		lblArgName4 = new JLabel("Name: ");
		sl_panel_1.putConstraint(SpringLayout.SOUTH, ck4, 0, SpringLayout.SOUTH, lblArgName4);
		sl_panel_1.putConstraint(SpringLayout.WEST, lblArgName4, 0, SpringLayout.WEST, lblArgName1);
		panel_1.add(lblArgName4);
		
		arg_name_textField4 = new JTextField();
		sl_panel_1.putConstraint(SpringLayout.NORTH, arg_name_textField4, 24, SpringLayout.SOUTH, arg_name_textField3);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, lblArgName4, 0, SpringLayout.SOUTH, arg_name_textField4);
		sl_panel_1.putConstraint(SpringLayout.WEST, arg_name_textField4, 0, SpringLayout.WEST, arg_name_textField1);
		sl_panel_1.putConstraint(SpringLayout.EAST, arg_name_textField4, 0, SpringLayout.EAST, arg_name_textField1);
		lblArgName4.setLabelFor(arg_name_textField4);
		panel_1.add(arg_name_textField4);
		arg_name_textField4.setColumns(10);
		
		lblArgType4 = new JLabel("Type: ");
		sl_panel_1.putConstraint(SpringLayout.NORTH, lblArgType4, 30, SpringLayout.SOUTH, lblArgType3);
		sl_panel_1.putConstraint(SpringLayout.WEST, lblArgType4, 0, SpringLayout.WEST, lblArgType1);
		panel_1.add(lblArgType4);
		
		arg_type_cb4 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfTasks());
		sl_panel_1.putConstraint(SpringLayout.NORTH, arg_type_cb4, 24, SpringLayout.SOUTH, arg_type_cb3);
		sl_panel_1.putConstraint(SpringLayout.WEST, arg_type_cb4, 0, SpringLayout.WEST, arg_type_cb1);
		sl_panel_1.putConstraint(SpringLayout.EAST, arg_type_cb4, 0, SpringLayout.EAST, arg_type_cb1);
		lblArgType4.setLabelFor(arg_type_cb4);
		panel_1.add(arg_type_cb4);
		
		ck5 = new JCheckBox("");
		sl_panel_1.putConstraint(SpringLayout.WEST, ck5, 0, SpringLayout.WEST, ck1);
		panel_1.add(ck5);
		
		lblArgName5 = new JLabel("Name: ");
		sl_panel_1.putConstraint(SpringLayout.SOUTH, ck5, 0, SpringLayout.SOUTH, lblArgName5);
		sl_panel_1.putConstraint(SpringLayout.EAST, lblArgName5, 0, SpringLayout.EAST, lblArgName1);
		panel_1.add(lblArgName5);
		
		arg_name_textField5 = new JTextField();
		lblArgName5.setLabelFor(arg_name_textField5);
		sl_panel_1.putConstraint(SpringLayout.NORTH, arg_name_textField5, 15, SpringLayout.SOUTH, arg_name_textField4);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, lblArgName5, 0, SpringLayout.SOUTH, arg_name_textField5);
		sl_panel_1.putConstraint(SpringLayout.WEST, arg_name_textField5, 0, SpringLayout.WEST, arg_name_textField1);
		sl_panel_1.putConstraint(SpringLayout.EAST, arg_name_textField5, 0, SpringLayout.EAST, arg_name_textField1);
		panel_1.add(arg_name_textField5);
		arg_name_textField5.setColumns(10);
		
		lblArgType5 = new JLabel("Type: ");
		sl_panel_1.putConstraint(SpringLayout.NORTH, lblArgType5, 21, SpringLayout.SOUTH, lblArgType4);
		sl_panel_1.putConstraint(SpringLayout.WEST, lblArgType5, 0, SpringLayout.WEST, lblArgType1);
		panel_1.add(lblArgType5);
		
		arg_type_cb5 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfTasks());
		sl_panel_1.putConstraint(SpringLayout.NORTH, arg_type_cb5, 15, SpringLayout.SOUTH, arg_type_cb4);
		sl_panel_1.putConstraint(SpringLayout.WEST, arg_type_cb5, 0, SpringLayout.WEST, arg_type_cb1);
		sl_panel_1.putConstraint(SpringLayout.EAST, arg_type_cb5, 0, SpringLayout.EAST, arg_type_cb1);
		lblArgType5.setLabelFor(arg_type_cb5);
		panel_1.add(arg_type_cb5);
		
		panel_2 = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel_2, 6, SpringLayout.SOUTH, panel_1);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel_2, 0, SpringLayout.WEST, panel);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel_2, -237, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel_2, -5, SpringLayout.EAST, contentPane);
		panel_2.setBorder(new TitledBorder(null, "Preconditions: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		sl_panel_2 = new SpringLayout();
		panel_2.setLayout(sl_panel_2);
		contentPane.add(panel_2);
		
		panel_3 = new JPanel();  
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel_3, 6, SpringLayout.SOUTH, panel_2);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel_3, 5, SpringLayout.WEST, panel);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel_3, 0, SpringLayout.EAST, contentPane);	
		panel_3.setBorder(new TitledBorder(null, "Effects: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		sl_panel_3 = new SpringLayout();
		panel_3.setLayout(sl_panel_3);
		contentPane.add(panel_3);
		
		prec_scrollPane = new JScrollPane();
		sl_panel_2.putConstraint(SpringLayout.NORTH, prec_scrollPane, 10, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.WEST, prec_scrollPane, 10, SpringLayout.WEST, panel_2);
		sl_panel_2.putConstraint(SpringLayout.SOUTH, prec_scrollPane, 73, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.EAST, prec_scrollPane, 337, SpringLayout.WEST, panel_2);
		prec_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		prec_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel_2.add(prec_scrollPane);
		
		prec_textArea = new JTextArea();
		prec_scrollPane.setViewportView(prec_textArea);
		
		btnAdd_preconds = new JButton("ADD >>");
		sl_panel_2.putConstraint(SpringLayout.NORTH, btnAdd_preconds, 27, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.EAST, btnAdd_preconds, -10, SpringLayout.EAST, panel_2);
		panel_2.add(btnAdd_preconds);
		
		lblSupposedeffects = new JLabel("Supposed-effects: ");
		sl_panel_3.putConstraint(SpringLayout.WEST, lblSupposedeffects, 10, SpringLayout.WEST, panel_3);
		panel_3.add(lblSupposedeffects);
		
		effSupp_scrollPane = new JScrollPane();
		effSupp_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		effSupp_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		sl_panel_3.putConstraint(SpringLayout.SOUTH, lblSupposedeffects, -6, SpringLayout.NORTH, effSupp_scrollPane);
		sl_panel_3.putConstraint(SpringLayout.NORTH, effSupp_scrollPane, 22, SpringLayout.NORTH, panel_3);
		sl_panel_3.putConstraint(SpringLayout.WEST, effSupp_scrollPane, 10, SpringLayout.WEST, panel_3);
		sl_panel_3.putConstraint(SpringLayout.SOUTH, effSupp_scrollPane, 76, SpringLayout.NORTH, panel_3);
		sl_panel_3.putConstraint(SpringLayout.EAST, effSupp_scrollPane, -102, SpringLayout.EAST, panel_3);
		lblSupposedeffects.setLabelFor(effSupp_scrollPane);
		panel_3.add(effSupp_scrollPane);
		
		effSupp_textArea = new JTextArea();
		effSupp_scrollPane.setViewportView(effSupp_textArea);
		
		lblAutomaticeffects = new JLabel("Automatic-effects: ");
		sl_panel_3.putConstraint(SpringLayout.WEST, lblAutomaticeffects, 0, SpringLayout.WEST, lblSupposedeffects);
		panel_3.add(lblAutomaticeffects);
		
		effAuto_scrollPane = new JScrollPane();
		sl_panel_3.putConstraint(SpringLayout.NORTH, effAuto_scrollPane, 109, SpringLayout.NORTH, panel_3);
		sl_panel_3.putConstraint(SpringLayout.SOUTH, effAuto_scrollPane, -16, SpringLayout.SOUTH, panel_3);
		sl_panel_3.putConstraint(SpringLayout.SOUTH, lblAutomaticeffects, -6, SpringLayout.NORTH, effAuto_scrollPane);
		sl_panel_3.putConstraint(SpringLayout.WEST, effAuto_scrollPane, 0, SpringLayout.WEST, lblSupposedeffects);
		sl_panel_3.putConstraint(SpringLayout.EAST, effAuto_scrollPane, 0, SpringLayout.EAST, effSupp_scrollPane);
		effAuto_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		effAuto_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		lblAutomaticeffects.setLabelFor(effAuto_scrollPane);
		panel_3.add(effAuto_scrollPane);
		
		effAuto_textArea = new JTextArea();
		effAuto_scrollPane.setViewportView(effAuto_textArea);
		
		btnAdd_effects = new JButton("ADD >>");
		sl_panel_3.putConstraint(SpringLayout.NORTH, btnAdd_effects, 68, SpringLayout.NORTH, panel_3);
		sl_panel_3.putConstraint(SpringLayout.EAST, btnAdd_effects, -10, SpringLayout.EAST, panel_3);
		panel_3.add(btnAdd_effects);
		
		btnUpdt = new JButton("Update");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel_3, -6, SpringLayout.NORTH, btnUpdt);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnUpdt, 99, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnUpdt, 0, SpringLayout.SOUTH, contentPane);
		contentPane.add(btnUpdt);
		
		btnCancel = new JButton("Cancel");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCancel, 0, SpringLayout.NORTH, btnUpdt);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnCancel, -98, SpringLayout.EAST, contentPane);
		contentPane.add(btnCancel);
		
		// parte della precompilazione dei campi
		old_name = n;
		
		int n_ArgTask;
		Vector<String> objects_1; 
		Vector<String> objects_2;
		Vector<String> objects_3;
		Vector<String> objects_4;
		Vector<String> objects_5;
		// vettori di appoggio per il riempimento delle combobox contenenti i tipi degli argomenti dei task
		
		vec_parIn = XMLParser.getInputParametersOfTask(n,false);
		
		n_ArgTask = vec_parIn.size();
		
		if(n_ArgTask == 1){
			ck1.setSelected(true);
			arg_name_textField1.setText(vec_parIn.get(0).firstElement());
			arg_type_cb1.removeAllItems(); 
			objects_1 = XMLParser.getRelevantTypesForArgumentsOfTasks();
			for(int i=0;i<objects_1.size();i++){
				arg_type_cb1.addItem(objects_1.get(i));
			}
			arg_type_cb1.setSelectedItem(vec_parIn.get(0).lastElement());
			
		}
		else if(n_ArgTask == 2){
			ck1.setSelected(true);
			arg_name_textField1.setText(vec_parIn.get(0).firstElement());
			arg_type_cb1.removeAllItems(); 
			objects_1 = XMLParser.getRelevantTypesForArgumentsOfTasks();
			for(int i=0;i<objects_1.size();i++){
				arg_type_cb1.addItem(objects_1.get(i));
			}
			arg_type_cb1.setSelectedItem(vec_parIn.get(0).lastElement());
			ck2.setSelected(true);
			arg_name_textField2.setText(vec_parIn.get(1).firstElement());
			arg_type_cb2.removeAllItems(); 
			objects_2 = XMLParser.getRelevantTypesForArgumentsOfTasks();
			for(int i=0;i<objects_2.size();i++){
				arg_type_cb2.addItem(objects_2.get(i));
			}
			arg_type_cb2.setSelectedItem(vec_parIn.get(1).lastElement());
			
		}
		else if(n_ArgTask == 3){
			ck1.setSelected(true);
			arg_name_textField1.setText(vec_parIn.get(0).firstElement());
			arg_type_cb1.removeAllItems(); 
			objects_1 = XMLParser.getRelevantTypesForArgumentsOfTasks();
			for(int i=0;i<objects_1.size();i++){
				arg_type_cb1.addItem(objects_1.get(i));
			}
			arg_type_cb1.setSelectedItem(vec_parIn.get(0).lastElement());
			ck2.setSelected(true);
			arg_name_textField2.setText(vec_parIn.get(1).firstElement());
			arg_type_cb2.removeAllItems(); 
			objects_2 = XMLParser.getRelevantTypesForArgumentsOfTasks();
			for(int i=0;i<objects_2.size();i++){
				arg_type_cb2.addItem(objects_2.get(i));
			}
			arg_type_cb2.setSelectedItem(vec_parIn.get(1).lastElement());
			ck3.setSelected(true);
			arg_name_textField3.setText(vec_parIn.get(2).firstElement());
			arg_type_cb3.removeAllItems(); 
			objects_3 = XMLParser.getRelevantTypesForArgumentsOfTasks();
			for(int i=0;i<objects_3.size();i++){
				arg_type_cb3.addItem(objects_3.get(i));
			}
			arg_type_cb3.setSelectedItem(vec_parIn.get(2).lastElement());
			
		}
		else if(n_ArgTask == 4){
			ck1.setSelected(true);
			arg_name_textField1.setText(vec_parIn.get(0).firstElement());
			arg_type_cb1.removeAllItems(); 
			objects_1 = XMLParser.getRelevantTypesForArgumentsOfTasks();
			for(int i=0;i<objects_1.size();i++){
				arg_type_cb1.addItem(objects_1.get(i));
			}
			arg_type_cb1.setSelectedItem(vec_parIn.get(0).lastElement());
			ck2.setSelected(true);
			arg_name_textField2.setText(vec_parIn.get(1).firstElement());
			arg_type_cb2.removeAllItems(); 
			objects_2 = XMLParser.getRelevantTypesForArgumentsOfTasks();
			for(int i=0;i<objects_2.size();i++){
				arg_type_cb2.addItem(objects_2.get(i));
			}
			arg_type_cb2.setSelectedItem(vec_parIn.get(1).lastElement());
			ck3.setSelected(true);
			arg_name_textField3.setText(vec_parIn.get(2).firstElement());
			arg_type_cb3.removeAllItems(); 
			objects_3 = XMLParser.getRelevantTypesForArgumentsOfTasks();
			for(int i=0;i<objects_3.size();i++){
				arg_type_cb3.addItem(objects_3.get(i));
			}
			arg_type_cb3.setSelectedItem(vec_parIn.get(2).lastElement());
			ck4.setSelected(true);
			arg_type_cb4.removeAllItems(); 
			arg_name_textField4.setText(vec_parIn.get(3).firstElement());
			objects_4 = XMLParser.getRelevantTypesForArgumentsOfTasks();
			for(int i=0;i<objects_4.size();i++){
				arg_type_cb4.addItem(objects_4.get(i));
			}
			arg_type_cb4.setSelectedItem(vec_parIn.get(3).lastElement());
			
		}
		else if(n_ArgTask == 5){
			ck1.setSelected(true);
			arg_name_textField1.setText(vec_parIn.get(0).firstElement());
			arg_type_cb1.removeAllItems(); 
			objects_1 = XMLParser.getRelevantTypesForArgumentsOfTasks();
			for(int i=0;i<objects_1.size();i++){
				arg_type_cb1.addItem(objects_1.get(i));
			}
			arg_type_cb1.setSelectedItem(vec_parIn.get(0).lastElement());
			ck2.setSelected(true);
			arg_name_textField2.setText(vec_parIn.get(1).firstElement());
			arg_type_cb2.removeAllItems(); 
			objects_2 = XMLParser.getRelevantTypesForArgumentsOfTasks();
			for(int i=0;i<objects_2.size();i++){
				arg_type_cb2.addItem(objects_2.get(i));
			}
			arg_type_cb2.setSelectedItem(vec_parIn.get(1).lastElement());
			ck3.setSelected(true);
			arg_name_textField3.setText(vec_parIn.get(2).firstElement());
			arg_type_cb3.removeAllItems(); 
			objects_3 = XMLParser.getRelevantTypesForArgumentsOfTasks();
			for(int i=0;i<objects_3.size();i++){
				arg_type_cb3.addItem(objects_3.get(i));
			}
			arg_type_cb3.setSelectedItem(vec_parIn.get(2).lastElement());
			ck4.setSelected(true);
			arg_name_textField4.setText(vec_parIn.get(3).firstElement());
			arg_type_cb4.removeAllItems(); 
			objects_4 = XMLParser.getRelevantTypesForArgumentsOfTasks();
			for(int i=0;i<objects_4.size();i++){
				arg_type_cb4.addItem(objects_4.get(i));
			}
			arg_type_cb4.setSelectedItem(vec_parIn.get(3).lastElement());
			ck5.setSelected(true);
			arg_name_textField5.setText(vec_parIn.get(4).firstElement());
			arg_type_cb5.removeAllItems(); 
			objects_5 = XMLParser.getRelevantTypesForArgumentsOfTasks();
			for(int i=0;i<objects_5.size();i++){
				arg_type_cb5.addItem(objects_5.get(i));
			}
			arg_type_cb5.setSelectedItem(vec_parIn.get(4).lastElement());
			
		}
		
		vec_dtls = XMLParser.getTaskDetails(n); // ci saranno le precondizioni, i supp-effects e gli auto-effects
		// PRECONDIZIONI
		String preconditions_area = "";
		String app_prec = null;
		Vector<String> prcds = vec_dtls.firstElement(); // precondizioni del task
		if(prcds.size()==1){ // ho una sola precondizione
			app_prec = prcds.firstElement();
			if(app_prec.contains("&gt;")){
				app_prec = app_prec.replace("&gt;", ">");
			}
			if(app_prec.contains("&lt;")){
				app_prec = app_prec.replace("&lt;", "<");
			}
			preconditions_area = app_prec;
			prec_textArea.setText(preconditions_area);
		}
		else{ // ho pi� precondizioni
			for(int j=0;j<prcds.size();j++){
				app_prec = prcds.get(j);
				if(app_prec.contains("&gt;")){
					app_prec = app_prec.replace("&gt;", ">");
				}
				if(app_prec.contains("&lt;")){
					app_prec = app_prec.replace("&lt;", "<");
				}
				if(j==prcds.size()-1)
					preconditions_area = preconditions_area + app_prec;
				else
					preconditions_area = preconditions_area + app_prec + " AND\n";
				
				prec_textArea.setText(preconditions_area); 
			}
		}
		
		// SUPPOSED-EFFECTS
		String supposed_area = "";
		String app_supposed = null;
		Vector<String> seffcts = vec_dtls.elementAt(1); // supposed-effects del task
		if(seffcts.size()==1){ // ho un solo supposed-effect
			app_supposed = seffcts.firstElement();
			if(app_supposed.contains("&gt;")){
				app_supposed = app_supposed.replace("&gt;", ">");
			}
			if(app_supposed.contains("&lt;")){
				app_supposed = app_supposed.replace("&lt;", "<");
			}
			supposed_area = app_supposed;
			effSupp_textArea.setText(supposed_area);
			
		}
		else{ // ho pi� supposed-effects
			for(int j=0;j<seffcts.size();j++){
				app_supposed = seffcts.get(j);
				if(app_supposed.contains("&gt;")){
					app_supposed = app_supposed.replace("&gt;", ">");
				}
				if(app_supposed.contains("&lt;")){
					app_supposed = app_supposed.replace("&lt;", "<");
				}
				if(j==seffcts.size()-1)
					supposed_area = supposed_area + app_supposed;
				else
					supposed_area = supposed_area + app_supposed + " AND\n";
				
				effSupp_textArea.setText(supposed_area); 
			}
		}
		
		// AUTOMATIC-EFFECTS 
		String automatic_area = "";
		String app_automatic = null;
		Vector<String> autoeff = vec_dtls.lastElement(); // supposed-effects del task
		if(autoeff.size()==1){ // ho un solo automatic-effect
			app_automatic = autoeff.firstElement();
			if(app_automatic.contains("&gt;")){
				app_automatic = app_automatic.replace("&gt;", ">");
			}
			if(app_automatic.contains("&lt;")){
				app_automatic = app_automatic.replace("&lt;", "<");
			}
			automatic_area = app_automatic;
			effAuto_textArea.setText(automatic_area);
			
		}
		else{ // ho pi� supposed-effects
			for(int j=0;j<autoeff.size();j++){
				app_automatic = autoeff.get(j);
				if(app_automatic.contains("&gt;")){
					app_automatic = app_automatic.replace("&gt;", ">");
				}
				if(app_automatic.contains("&lt;")){
					app_automatic = app_automatic.replace("&lt;", "<");
				}
				if(j==autoeff.size()-1)
					automatic_area = automatic_area + app_automatic;
				else
					automatic_area = automatic_area + app_automatic + " AND\n";
				
				effAuto_textArea.setText(automatic_area); 
			}
		}		
		
		
		
		
		
		
	
	} // CHIUDE INITCOMPONENT()
	
	/* --------------------------- METODI AUSILIARI PER LEGGERE LE VARIE COMPONENTI --------------------------------------- */
	
	/** Metodo ausiliario per leggere il nome del task */
	public String NameTask(){
		//String n;
		return  name_textField.getText();
	}
	
	/** Metodo ausiliario per leggere gli argomenti del task. Li restituisce sotto forma di
	 * vettore di vettore. Ogni vettore interno � della forma: [nome_argomento, tipo_argomento] */
	public Vector<Vector<String>> ArgsTask(){
		Vector<Vector<String>> args = new Vector<Vector<String>>();
		Vector<String> arg1 = new Vector<String>();
		Vector<String> arg2 = new Vector<String>();
		Vector<String> arg3 = new Vector<String>();
		Vector<String> arg4 = new Vector<String>();
		Vector<String> arg5 = new Vector<String>(); // appoggio, per creare i sottovettori
		if(ck1.isSelected()){
			arg1.add(arg_name_textField1.getText()); // prima componente: nome
			arg1.add(arg_type_cb1.getSelectedItem().toString()); // seconda componente: tipo
			args.add(arg1);
		}
		if(ck2.isSelected()){
			arg2.add(arg_name_textField2.getText());
			arg2.add(arg_type_cb2.getSelectedItem().toString());
			args.add(arg2);
		}
		if(ck3.isSelected()){
			arg3.add(arg_name_textField3.getText());
			arg3.add(arg_type_cb3.getSelectedItem().toString());
			args.add(arg3);
		}
		if(ck4.isSelected()){
			arg4.add(arg_name_textField4.getText());
			arg4.add(arg_type_cb4.getSelectedItem().toString());
			args.add(arg4);
		}
		if(ck5.isSelected()){
			arg5.add(arg_name_textField5.getText());
			arg5.add(arg_type_cb5.getSelectedItem().toString());
			args.add(arg5);
		}
		return args;
	}
	
	/**Metodo ausiliario per leggere le precondizioni del task. Le restituisce sotto forma di
	 * vettore di stringhe: ogni stringa � una precondizione. */
	public Vector<String> PreconditionsTask(){
		
		Vector<String> preconditions = new Vector<String>();
		
		String precs_string = prec_textArea.getText();
		if(precs_string.isEmpty()){
			return preconditions;
					
		}
		else{ // IL CAMPO NON E' VUOTO
			if(precs_string.contains("AND")){
				// ALLORA C'E' PIU' DI UNA PRECONDIZIONE
				String[] tokens = precs_string.split(" AND\n");
				/* Ogni elemento dell'array tokens[] � una precondizione */
				for(int i=0;i<tokens.length;i++){
					//System.out.println("TOKENS:"+ i +":" + tokens[i]); // --ok
					preconditions.add(tokens[i]);
				}
			}
			else{ 
				// C'E' UNA SOLA PRECONDIZIONE
				preconditions.add(precs_string);
			}
		}
		return preconditions;
		
		
	}
	
	/** Metodo ausiliario per leggere i supposed-effects del task. Li restituisce sotto forma di
	 * vettore di stringhe: ogni stringa � un supposed effect. */
	public  Vector<String> SupposedEffectsTask(){
		Vector<String> s_effects = new Vector<String>();
		
		
		String effs_string = effSupp_textArea.getText();
		if(effs_string.contains("AND")){
			// ALLORA C'E' PIU' DI UN EFFETTO (supposed)
			String[] tokens = effs_string.split(" AND\n");
			/* Ogni elemento dell'array tokens[] � un supposed-effect */
			for(int j=0;j<tokens.length;j++){		
				s_effects.add(tokens[j]);
			}
		}
		else{
			// C'E' UN SOLO EFFETTO
			s_effects.add(effs_string);
		}
		
		return s_effects;
		
	}
	
	/** Metodo ausiliario per leggere gli automatic-effects del task. Li restituisce sotto forma di
	 * vettore di stringhe: ogni stringa � un automatic effect. */
	public  Vector<String> AutomaticEffectsTask(){
		Vector<String> a_effects = new Vector<String>();
		
		
		String effa_string = effAuto_textArea.getText();
		if(effa_string.contains("AND")){
			// ALLORA C'E' PIU' DI UN EFFETTO (automatic)
			String[] tokens = effa_string.split(" AND\n");
			/* Ogni elemento dell'array tokens[] � un automatic-effect */
			for(int j=0;j<tokens.length;j++){		
				a_effects.add(tokens[j]);
			}
		}
		else{
			// C'E' UN SOLO EFFETTO
			a_effects.add(effa_string);
		}
		
		return a_effects;
		
	}
	
	

} // CHIUDE LA CLASSE
