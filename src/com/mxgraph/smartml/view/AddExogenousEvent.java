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

import com.mxgraph.smartml.control.H_AddExogenousEvent;
import com.mxgraph.smartml.model.XMLParser;


public class AddExogenousEvent extends JFrame 
{
	
	private static final long serialVersionUID = 1L;
	
	public H_AddExogenousEvent _handler = null;
	
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
	public JScrollPane eff_scrollPane;
	public JTextArea eff_textArea;
	public JButton btnAddEffects;
	public JButton btnCrt;
	public JButton btnCancel;
	
	public AddExogenousEvent() {
		super("Add a new Exogenous Event");
		initComponent();
		initHandler();
		
	}

	private void initHandler() {
		_handler = new H_AddExogenousEvent(this); 
		
	}

	private void initComponent() {
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
		
		name_textField = new JTextField();
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
		
		argtype_cb1 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfExogenousEvents());
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
		
		argtype_cb2 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfExogenousEvents());
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
		
		argtype_cb3 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfExogenousEvents());
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
		
		argtype_cb4 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfExogenousEvents());
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
		
		argtype_cb5 = new JComboBox<String>(XMLParser.getRelevantTypesForArgumentsOfExogenousEvents());
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
		panel_2.setBorder(new TitledBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null), "Effects: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_2);
		sl_panel_2 = new SpringLayout();
		panel_2.setLayout(sl_panel_2);

		eff_scrollPane = new JScrollPane();
		sl_panel_2.putConstraint(SpringLayout.NORTH, eff_scrollPane, 10, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.WEST, eff_scrollPane, 10, SpringLayout.WEST, panel_2);
		sl_panel_2.putConstraint(SpringLayout.SOUTH, eff_scrollPane, 97, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.EAST, eff_scrollPane, 337, SpringLayout.WEST, panel_2);
		eff_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		eff_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel_2.add(eff_scrollPane);
		
		eff_textArea = new JTextArea();
		eff_scrollPane.setViewportView(eff_textArea);
		
		btnAddEffects = new JButton("ADD >>");
		sl_panel_2.putConstraint(SpringLayout.NORTH, btnAddEffects, 42, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.EAST, btnAddEffects, -10, SpringLayout.EAST, panel_2);
		panel_2.add(btnAddEffects);
		
		btnCrt = new JButton("Add");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel_2, -44, SpringLayout.NORTH, btnCrt);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnCrt, 92, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnCrt, -10, SpringLayout.SOUTH, contentPane);
		contentPane.add(btnCrt);
		
		btnCancel = new JButton("Cancel");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCancel, 0, SpringLayout.NORTH, btnCrt);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnCancel, -69, SpringLayout.EAST, contentPane);
		contentPane.add(btnCancel);
		
	}
	
	/* --------------------------- METODI AUSILIARI PER LEGGERE LE VARIE COMPONENTI --------------------------------------- */
	
	/** Metodo ausiliario per leggere il nome dell'evento esogeno */
	public String NameExEvent(){
		return  name_textField.getText();
	}
	
	/** Metodo ausiliario per leggere gli argomenti dell'evento esogeno. Li restituisce sotto forma di
	 * vettore di vettore. Ogni vettore interno � della forma: [nome_argomento, tipo_argomento] */
	public Vector<Vector<String>> getArgsExEvent(){
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
	
	/**Metodo ausiliario per leggere gli effetti dell'evento esogeno. Li restituisce sotto forma di
	 * vettore di stringhe: ogni stringa � un effetto. */
	public  Vector<String> EffectsExEvent(){
		Vector<String> exEv_effects = new Vector<String>();
		
		String effs_string = eff_textArea.getText();
		if(effs_string.contains("AND")){
			// ALLORA C'E' PIU' DI UN EFFETTO 
			String[] tokens = effs_string.split(" AND\n");
			/* Ogni elemento dell'array tokens[] � un effect */
			for(int j=0;j<tokens.length;j++){		
				exEv_effects.add(tokens[j]);
			}
		}
		else{
			// C'E' UN SOLO EFFETTO
			exEv_effects.add(effs_string);
		}
		
		return exEv_effects;
		
	}

} // CHIUDE LA CLASSE
