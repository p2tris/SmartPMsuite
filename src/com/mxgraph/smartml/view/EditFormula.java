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

import com.mxgraph.smartml.control.H_EditFormula;
import com.mxgraph.smartml.model.XMLParser;


public class EditFormula extends JFrame 
{

	private static final long serialVersionUID = 1L;
	
	public H_EditFormula _handler = null; 
	
	public JPanel contentPane;
	public SpringLayout sl_contentPane;
	public SpringLayout sl_panel_2;
	public JPanel panel;
	public JLabel lblName; 
	public JTextField name_textField;
	public SpringLayout sl_panel;
	public JPanel panel_1;
	public SpringLayout sl_panel_1;
	public JCheckBox ck1;
	public JTextField argname_textField1;
	public JLabel lblArgName1;
	public JLabel lblArgType1;
	public JComboBox<String> argtype_cb1;
	public JCheckBox ck2;
	public JLabel lblArgName2;
	public JTextField argname_textField2;
	public JLabel lblArgType2;
	public JComboBox<String> argtype_cb2;
	public JCheckBox ck3;
	public JLabel lblArgName3;
	public JTextField argname_textField3;
	public JLabel lblArgType3;
	public JComboBox<String> argtype_cb3;
	public JCheckBox ck4;
	public JLabel lblArgName4;
	public JTextField argname_textField4;
	public JLabel lblArgType4;
	public JComboBox<String> argtype_cb4;
	public JCheckBox ck5;
	public JLabel lblArgName5;
	public JTextField argname_textField5;
	public JLabel lblArgType5;
	public JComboBox<String> argtype_cb5;
	public JPanel panel_2 ;
	public JScrollPane cont_scrollPane; 
	public JTextArea cont_textArea;
	public JButton btnUpd;
	public JButton btnCancel;
	
	public Vector<Vector<String>> vec_parIn; // per memorizzare gli argomenti in ingresso alla formula e i relativi tipi
	public String content_string; // per memorizzare il contenuto della formula (� una semplice stringa di testo)
	public String old_name; // per memorizzare il vecchio nome della formula
	
	public EditFormula(String formula_name) {
		super("Update the formula '" + formula_name +"'");
		initComponent(formula_name);
		initHandler();
		
	}

	private void initHandler() {
		_handler = new H_EditFormula(this); 
		
	}

	private void initComponent(String n) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(500, 600);
		setResizable(false); // LA FINESTRA NON E' RIDIMENSIONABILE
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
		
		name_textField = new JTextField(n); // CARICA IL NOME DELLA FORMULA
		sl_panel.putConstraint(SpringLayout.NORTH, lblName, 3, SpringLayout.NORTH, name_textField);
		sl_panel.putConstraint(SpringLayout.EAST, lblName, -6, SpringLayout.WEST, name_textField);
		sl_panel.putConstraint(SpringLayout.WEST, name_textField, 99, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, name_textField, -119, SpringLayout.EAST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, name_textField, -10, SpringLayout.SOUTH, panel);
		lblName.setLabelFor(name_textField);
		panel.add(name_textField);
		name_textField.setColumns(10);
		
		panel_1 = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel_1, 17, SpringLayout.SOUTH, panel);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel_1, 0, SpringLayout.WEST, panel);
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
		
		argname_textField1 = new JTextField();
		sl_panel_1.putConstraint(SpringLayout.SOUTH, ck1, 0, SpringLayout.SOUTH, argname_textField1);
		sl_panel_1.putConstraint(SpringLayout.NORTH, argname_textField1, -3, SpringLayout.NORTH, lblArgName1);
		sl_panel_1.putConstraint(SpringLayout.WEST, argname_textField1, 6, SpringLayout.EAST, lblArgName1);
		sl_panel_1.putConstraint(SpringLayout.EAST, argname_textField1, -193, SpringLayout.EAST, panel_1);
		lblArgName1.setLabelFor(argname_textField1);
		panel_1.add(argname_textField1);
		argname_textField1.setColumns(10);
		
		lblArgType1 = new JLabel("Type: ");
		sl_panel_1.putConstraint(SpringLayout.NORTH, lblArgType1, 0, SpringLayout.NORTH, lblArgName1);
		sl_panel_1.putConstraint(SpringLayout.WEST, lblArgType1, 6, SpringLayout.EAST, argname_textField1);
		panel_1.add(lblArgType1);
		
		argtype_cb1 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfFormulae());
		sl_panel_1.putConstraint(SpringLayout.NORTH, argtype_cb1, -3, SpringLayout.NORTH, lblArgName1);
		sl_panel_1.putConstraint(SpringLayout.WEST, argtype_cb1, 6, SpringLayout.EAST, lblArgType1);
		sl_panel_1.putConstraint(SpringLayout.EAST, argtype_cb1, -28, SpringLayout.EAST, panel_1);
		lblArgType1.setLabelFor(argtype_cb1);
		panel_1.add(argtype_cb1);
		
		ck2 = new JCheckBox("");
		sl_panel_1.putConstraint(SpringLayout.NORTH, ck2, 11, SpringLayout.SOUTH, ck1);
		sl_panel_1.putConstraint(SpringLayout.WEST, ck2, 0, SpringLayout.WEST, ck1);
		panel_1.add(ck2);
		
		lblArgName2 = new JLabel("Name: ");
		sl_panel_1.putConstraint(SpringLayout.WEST, lblArgName2, 0, SpringLayout.WEST, lblArgName1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, lblArgName2, 0, SpringLayout.SOUTH, ck2);
		panel_1.add(lblArgName2);
		
		argname_textField2 = new JTextField();
		sl_panel_1.putConstraint(SpringLayout.WEST, argname_textField2, 0, SpringLayout.WEST, argname_textField1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, argname_textField2, 0, SpringLayout.SOUTH, ck2);
		sl_panel_1.putConstraint(SpringLayout.EAST, argname_textField2, 0, SpringLayout.EAST, argname_textField1);
		lblArgName2.setLabelFor(argname_textField2);
		panel_1.add(argname_textField2);
		argname_textField2.setColumns(10);
		
		lblArgType2 = new JLabel("Type: ");
		sl_panel_1.putConstraint(SpringLayout.SOUTH, lblArgType2, 0, SpringLayout.SOUTH, ck2);
		sl_panel_1.putConstraint(SpringLayout.EAST, lblArgType2, 0, SpringLayout.EAST, lblArgType1);
		panel_1.add(lblArgType2);
		
		argtype_cb2 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfFormulae());
		sl_panel_1.putConstraint(SpringLayout.WEST, argtype_cb2, 0, SpringLayout.WEST, argtype_cb1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, argtype_cb2, 0, SpringLayout.SOUTH, ck2);
		sl_panel_1.putConstraint(SpringLayout.EAST, argtype_cb2, 0, SpringLayout.EAST, argtype_cb1);
		lblArgType2.setLabelFor(argtype_cb2);
		panel_1.add(argtype_cb2);
		
		ck3 = new JCheckBox("");
		sl_panel_1.putConstraint(SpringLayout.NORTH, ck3, 16, SpringLayout.SOUTH, ck2);
		sl_panel_1.putConstraint(SpringLayout.EAST, ck3, 0, SpringLayout.EAST, ck1);
		panel_1.add(ck3);
		
		lblArgName3 = new JLabel("Name: ");
		sl_panel_1.putConstraint(SpringLayout.WEST, lblArgName3, 0, SpringLayout.WEST, lblArgName1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, lblArgName3, 0, SpringLayout.SOUTH, ck3);
		panel_1.add(lblArgName3);

		argname_textField3 = new JTextField();
		sl_panel_1.putConstraint(SpringLayout.WEST, argname_textField3, 0, SpringLayout.WEST, argname_textField1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, argname_textField3, 0, SpringLayout.SOUTH, ck3);
		sl_panel_1.putConstraint(SpringLayout.EAST, argname_textField3, 0, SpringLayout.EAST, argname_textField1);
		lblArgName3.setLabelFor(argname_textField3);
		panel_1.add(argname_textField3);
		argname_textField3.setColumns(10);

		lblArgType3 = new JLabel("Type: ");
		sl_panel_1.putConstraint(SpringLayout.WEST, lblArgType3, 0, SpringLayout.WEST, lblArgType1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, lblArgType3, 0, SpringLayout.SOUTH, ck3);
		panel_1.add(lblArgType3);
		
		argtype_cb3 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfFormulae());
		sl_panel_1.putConstraint(SpringLayout.WEST, argtype_cb3, 0, SpringLayout.WEST, argtype_cb1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, argtype_cb3, 0, SpringLayout.SOUTH, ck3);
		sl_panel_1.putConstraint(SpringLayout.EAST, argtype_cb3, 0, SpringLayout.EAST, argtype_cb1);
		lblArgType3.setLabelFor(argtype_cb3);
		panel_1.add(argtype_cb3);
		
		ck4 = new JCheckBox("");
		sl_panel_1.putConstraint(SpringLayout.EAST, ck4, 0, SpringLayout.EAST, ck1);
		panel_1.add(ck4);
		
		lblArgName4 = new JLabel("Name: ");
		sl_panel_1.putConstraint(SpringLayout.SOUTH, ck4, 0, SpringLayout.SOUTH, lblArgName4);
		sl_panel_1.putConstraint(SpringLayout.WEST, lblArgName4, 0, SpringLayout.WEST, lblArgName1);
		panel_1.add(lblArgName4);
		
		argname_textField4 = new JTextField();
		sl_panel_1.putConstraint(SpringLayout.NORTH, argname_textField4, 24, SpringLayout.SOUTH, argname_textField3);
		lblArgName4.setLabelFor(argname_textField4);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, lblArgName4, 0, SpringLayout.SOUTH, argname_textField4);
		sl_panel_1.putConstraint(SpringLayout.WEST, argname_textField4, 0, SpringLayout.WEST, argname_textField1);
		sl_panel_1.putConstraint(SpringLayout.EAST, argname_textField4, 0, SpringLayout.EAST, argname_textField1);
		panel_1.add(argname_textField4);
		argname_textField4.setColumns(10);
		
		lblArgType4 = new JLabel("Type: ");
		sl_panel_1.putConstraint(SpringLayout.NORTH, lblArgType4, 30, SpringLayout.SOUTH, lblArgType3);
		sl_panel_1.putConstraint(SpringLayout.WEST, lblArgType4, 0, SpringLayout.WEST, lblArgType1);
		panel_1.add(lblArgType4);
		
		argtype_cb4 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfFormulae());
		sl_panel_1.putConstraint(SpringLayout.NORTH, argtype_cb4, 24, SpringLayout.SOUTH, argtype_cb3);
		lblArgType4.setLabelFor(argtype_cb4);
		sl_panel_1.putConstraint(SpringLayout.WEST, argtype_cb4, 0, SpringLayout.WEST, argtype_cb1);
		sl_panel_1.putConstraint(SpringLayout.EAST, argtype_cb4, 0, SpringLayout.EAST, argtype_cb1);
		panel_1.add(argtype_cb4);
		
		ck5 = new JCheckBox("");
		sl_panel_1.putConstraint(SpringLayout.WEST, ck5, 0, SpringLayout.WEST, ck1);
		//panel_1.add(ck5);
		
		lblArgName5 = new JLabel("Name: ");
		sl_panel_1.putConstraint(SpringLayout.SOUTH, ck5, 0, SpringLayout.SOUTH, lblArgName5);
		sl_panel_1.putConstraint(SpringLayout.EAST, lblArgName5, 0, SpringLayout.EAST, lblArgName1);
		//panel_1.add(lblArgName5);
		
		argname_textField5 = new JTextField();
		lblArgName5.setLabelFor(argname_textField5);
		sl_panel_1.putConstraint(SpringLayout.NORTH, argname_textField5, 15, SpringLayout.SOUTH, argname_textField4);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, lblArgName5, 0, SpringLayout.SOUTH, argname_textField5);
		sl_panel_1.putConstraint(SpringLayout.WEST, argname_textField5, 0, SpringLayout.WEST, argname_textField1);
		sl_panel_1.putConstraint(SpringLayout.EAST, argname_textField5, 0, SpringLayout.EAST, argname_textField1);
		//panel_1.add(argname_textField5);
		argname_textField5.setColumns(10);
		
		lblArgType5 = new JLabel("Type: ");
		sl_panel_1.putConstraint(SpringLayout.NORTH, lblArgType5, 21, SpringLayout.SOUTH, lblArgType4);
		sl_panel_1.putConstraint(SpringLayout.WEST, lblArgType5, 0, SpringLayout.WEST, lblArgType1);
		//panel_1.add(lblArgType5);
		
		argtype_cb5 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfFormulae());
		lblArgType5.setLabelFor(argtype_cb5);
		sl_panel_1.putConstraint(SpringLayout.NORTH, argtype_cb5, 15, SpringLayout.SOUTH, argtype_cb4);
		sl_panel_1.putConstraint(SpringLayout.WEST, argtype_cb5, 0, SpringLayout.WEST, argtype_cb1);
		sl_panel_1.putConstraint(SpringLayout.EAST, argtype_cb5, 0, SpringLayout.EAST, argtype_cb1);
		//panel_1.add(argtype_cb5);
		
		panel_2 = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel_1, -26, SpringLayout.NORTH, panel_2);
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel_2, 324, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel_2, 0, SpringLayout.WEST, panel);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel_2, -5, SpringLayout.EAST, contentPane);
		panel_2.setBorder(new TitledBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null), "Content: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_2);
		sl_panel_2 = new SpringLayout();
		panel_2.setLayout(sl_panel_2);

		cont_scrollPane = new JScrollPane(); 
		sl_panel_2.putConstraint(SpringLayout.NORTH, cont_scrollPane, 10, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.WEST, cont_scrollPane, 10, SpringLayout.WEST, panel_2);
		sl_panel_2.putConstraint(SpringLayout.SOUTH, cont_scrollPane, 97, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.EAST, cont_scrollPane, 420, SpringLayout.WEST, panel_2);
		cont_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		cont_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel_2.add(cont_scrollPane);
		
		cont_textArea = new JTextArea();  
		cont_scrollPane.setViewportView(cont_textArea);
		
		btnUpd = new JButton("Update");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel_2, -44, SpringLayout.NORTH, btnUpd);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnUpd, 92, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnUpd, -10, SpringLayout.SOUTH, contentPane);
		contentPane.add(btnUpd);
		
		btnCancel = new JButton("Cancel");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCancel, 0, SpringLayout.NORTH, btnUpd);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnCancel, -69, SpringLayout.EAST, contentPane);
		contentPane.add(btnCancel);

		old_name = n;
		
		int n_ArgFormula;
		Vector<String> objects_1; 
		Vector<String> objects_2;
		Vector<String> objects_3;
		Vector<String> objects_4;
		Vector<String> objects_5;
		// vettori di appoggio per il riempimento delle combobox contenenti i tipi degli argomenti della formula
		
		
		vec_parIn = XMLParser.getInputParametersOfFormula(n);
		
		n_ArgFormula = vec_parIn.size();
		
		if(n_ArgFormula == 0){
			// niente
		}
		if(n_ArgFormula == 1){
			ck1.setSelected(true);
			argname_textField1.setText(vec_parIn.get(0).firstElement());
			argtype_cb1.removeAllItems(); 
			objects_1 = XMLParser.getRelevantTypesForArgumentsOfFormulae();
			for(int i=0;i<objects_1.size();i++){
				argtype_cb1.addItem(objects_1.get(i));
			}
			argtype_cb1.setSelectedItem(vec_parIn.get(0).lastElement());
			
		}
		else if(n_ArgFormula == 2){
			ck1.setSelected(true);
			argname_textField1.setText(vec_parIn.get(0).firstElement());
			argtype_cb1.removeAllItems(); 
			objects_1 = XMLParser.getRelevantTypesForArgumentsOfFormulae();
			for(int i=0;i<objects_1.size();i++){
				argtype_cb1.addItem(objects_1.get(i));
			}
			argtype_cb1.setSelectedItem(vec_parIn.get(0).lastElement());
			ck2.setSelected(true);
			argname_textField2.setText(vec_parIn.get(1).firstElement());
			argtype_cb2.removeAllItems(); 
			objects_2 = XMLParser.getRelevantTypesForArgumentsOfFormulae();
			for(int i=0;i<objects_2.size();i++){
				argtype_cb2.addItem(objects_2.get(i));
			}
			argtype_cb2.setSelectedItem(vec_parIn.get(1).lastElement());
			
		}
		else if(n_ArgFormula == 3){
			ck1.setSelected(true);
			argname_textField1.setText(vec_parIn.get(0).firstElement());
			argtype_cb1.removeAllItems(); 
			objects_1 = XMLParser.getRelevantTypesForArgumentsOfFormulae();
			for(int i=0;i<objects_1.size();i++){
				argtype_cb1.addItem(objects_1.get(i));
			}
			argtype_cb1.setSelectedItem(vec_parIn.get(0).lastElement());
			ck2.setSelected(true);
			argname_textField2.setText(vec_parIn.get(1).firstElement());
			argtype_cb2.removeAllItems(); 
			objects_2 = XMLParser.getRelevantTypesForArgumentsOfFormulae();
			for(int i=0;i<objects_2.size();i++){
				argtype_cb2.addItem(objects_2.get(i));
			}
			argtype_cb2.setSelectedItem(vec_parIn.get(1).lastElement());
			ck3.setSelected(true);
			argname_textField3.setText(vec_parIn.get(2).firstElement());
			argtype_cb3.removeAllItems(); 
			objects_3 = XMLParser.getRelevantTypesForArgumentsOfFormulae();
			for(int i=0;i<objects_3.size();i++){
				argtype_cb3.addItem(objects_3.get(i));
			}
			argtype_cb3.setSelectedItem(vec_parIn.get(2).lastElement());
			
		}
		else if(n_ArgFormula == 4){
			ck1.setSelected(true);
			argname_textField1.setText(vec_parIn.get(0).firstElement());
			argtype_cb1.removeAllItems(); 
			objects_1 = XMLParser.getRelevantTypesForArgumentsOfFormulae();
			for(int i=0;i<objects_1.size();i++){
				argtype_cb1.addItem(objects_1.get(i));
			}
			argtype_cb1.setSelectedItem(vec_parIn.get(0).lastElement());
			ck2.setSelected(true);
			argname_textField2.setText(vec_parIn.get(1).firstElement());
			argtype_cb2.removeAllItems(); 
			objects_2 = XMLParser.getRelevantTypesForArgumentsOfFormulae();
			for(int i=0;i<objects_2.size();i++){
				argtype_cb2.addItem(objects_2.get(i));
			}
			argtype_cb2.setSelectedItem(vec_parIn.get(1).lastElement());
			ck3.setSelected(true);
			argname_textField3.setText(vec_parIn.get(2).firstElement());
			argtype_cb3.removeAllItems(); 
			objects_3 = XMLParser.getRelevantTypesForArgumentsOfFormulae();
			for(int i=0;i<objects_3.size();i++){
				argtype_cb3.addItem(objects_3.get(i));
			}
			argtype_cb3.setSelectedItem(vec_parIn.get(2).lastElement());
			ck4.setSelected(true);
			argname_textField4.setText(vec_parIn.get(3).firstElement());
			argtype_cb4.removeAllItems(); 
			objects_4 = XMLParser.getRelevantTypesForArgumentsOfFormulae();
			for(int i=0;i<objects_4.size();i++){
				argtype_cb4.addItem(objects_4.get(i));
			}
			argtype_cb4.setSelectedItem(vec_parIn.get(3).lastElement());
			
		}
		else if(n_ArgFormula == 5){
			ck1.setSelected(true);
			argname_textField1.setText(vec_parIn.get(0).firstElement());
			argtype_cb1.removeAllItems(); 
			objects_1 = XMLParser.getRelevantTypesForArgumentsOfFormulae();
			for(int i=0;i<objects_1.size();i++){
				argtype_cb1.addItem(objects_1.get(i));
			}
			argtype_cb1.setSelectedItem(vec_parIn.get(0).lastElement());
			ck2.setSelected(true);
			argname_textField2.setText(vec_parIn.get(1).firstElement());
			argtype_cb2.removeAllItems(); 
			objects_2 = XMLParser.getRelevantTypesForArgumentsOfFormulae();
			for(int i=0;i<objects_2.size();i++){
				argtype_cb2.addItem(objects_2.get(i));
			}
			argtype_cb2.setSelectedItem(vec_parIn.get(1).lastElement());
			ck3.setSelected(true);
			argname_textField3.setText(vec_parIn.get(2).firstElement());
			argtype_cb3.removeAllItems(); 
			objects_3 = XMLParser.getRelevantTypesForArgumentsOfFormulae();
			for(int i=0;i<objects_3.size();i++){
				argtype_cb3.addItem(objects_3.get(i));
			}
			argtype_cb3.setSelectedItem(vec_parIn.get(2).lastElement());
			ck4.setSelected(true);
			argname_textField4.setText(vec_parIn.get(3).firstElement());
			argtype_cb4.removeAllItems(); 
			objects_4 = XMLParser.getRelevantTypesForArgumentsOfFormulae();
			for(int i=0;i<objects_4.size();i++){
				argtype_cb4.addItem(objects_4.get(i));
			}
			argtype_cb4.setSelectedItem(vec_parIn.get(3).lastElement());
			ck5.setSelected(true);
			argname_textField5.setText(vec_parIn.get(4).firstElement());
			argtype_cb5.removeAllItems(); 
			objects_5 = XMLParser.getRelevantTypesForArgumentsOfFormulae();
			for(int i=0;i<objects_5.size();i++){
				argtype_cb5.addItem(objects_5.get(i));
			}
			argtype_cb5.setSelectedItem(vec_parIn.get(4).lastElement());
			
		}
		
		content_string = XMLParser.getFormulaContent(n);

		// "CONTENT"
		String app_cont = null;
		app_cont = content_string;
		if(app_cont.contains("&gt;")){
			app_cont = app_cont.replace("&gt;", ">");
		}
		if(app_cont.contains("&lt;")){
			app_cont = app_cont.replace("&lt;", "<");
		}
		cont_textArea.setText(app_cont);  
	}
	
	
	/* --------------------------- METODI AUSILIARI PER LEGGERE LE VARIE COMPONENTI --------------------------------------- */
	
	/** Metodo ausiliario per leggere il nome della formula */
	public String NameFormula(){
		return  name_textField.getText();
	}
	
	/** Metodo ausiliario per leggere gli argomenti della formula. Li restituisce sotto forma di
	 * vettore di vettore. Ogni vettore interno � della forma: [nome_argomento, tipo_argomento] */
	public Vector<Vector<String>> ArgsFormula(){
		Vector<Vector<String>> args = new Vector<Vector<String>>();
		Vector<String> arg1 = new Vector<String>();
		Vector<String> arg2 = new Vector<String>();
		Vector<String> arg3 = new Vector<String>();
		Vector<String> arg4 = new Vector<String>();
		Vector<String> arg5 = new Vector<String>(); // appoggio, per creare i sottovettori
		if(ck1.isSelected()){
			arg1.add(argname_textField1.getText()); // prima componente: nome
			arg1.add(argtype_cb1.getSelectedItem().toString()); // seconda componente: tipo
			args.add(arg1);
		}
		if(ck2.isSelected()){
			arg2.add(argname_textField2.getText());
			arg2.add(argtype_cb2.getSelectedItem().toString());
			args.add(arg2);
		}
		if(ck3.isSelected()){
			arg3.add(argname_textField3.getText());
			arg3.add(argtype_cb3.getSelectedItem().toString());
			args.add(arg3);
		}
		if(ck4.isSelected()){
			arg4.add(argname_textField4.getText());
			arg4.add(argtype_cb4.getSelectedItem().toString());
			args.add(arg4);
		}
		if(ck5.isSelected()){
			arg5.add(argname_textField5.getText());
			arg5.add(argtype_cb5.getSelectedItem().toString());
			args.add(arg5);
		}
		return args;
	}
	
	/** Metodo ausiliario per leggere il contenuto della formula. Lo restituisce sotto forma di
	 * stringa */
	public  String getContentFormula(){  
		
		String frm_content = ""; // Stringa finale
		
		frm_content = cont_textArea.getText();
		
		return frm_content; 
		
	}
	

} // CHIUDE LA CLASSE

