package com.mxgraph.smartml.view;

import java.awt.Color;  
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.xml.transform.OutputKeys;

import com.mxgraph.smartml.control.H_DataType;
import com.mxgraph.smartml.model.XMLParser;



public class DataType extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public H_DataType _handler = null;  
	
	public JPanel contentPane;
	public JTextField booltype_textField;
	//public JTextField integertype_textField;
	public JTextField min_integertype_textField;
	public JTextField max_integertype_textField;
	public JLabel min_Label = new JLabel("Min: ");
	public JLabel max_Label = new JLabel("Max: ");
	
	
	public JPanel panel; 
	public JPanel panel_1; 
	public JLabel lblBooleanType;
	public JLabel lblIntegerType;
	public JLabel lblPredefinedDataType;
	
	public JLabel lblUserDefinedData;
		
	//public JScrollPane usertype_scrollPane;
	//public JTextArea usertype_textArea;
	
	private JList dataTypesList;
	private DefaultListModel dataTypesListModel;
	private JScrollPane dataTypesScrollPane;
	private JPanel dataTypesButtonPanel;
	private JButton addDataTypeButton;
	private JButton deleteDataTypeButton;

	
	
	
	public JLabel blank_label_1 = new JLabel();
	public JLabel blank_label_2 = new JLabel();
	public JButton updateIntegerTypeButton;	
	public JButton btnOK;
	
	public JButton pluginButton;
	public JComboBox selectPluginComboBox;
	
	public DataType(){
		
		super("Data Perspective");
		initComponent();
		initHandler();
		
	}

	public void initHandler() {
		_handler = new H_DataType(this); 
		
	}

	private void initComponent() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(450, 480);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout());
		
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(440,150));
		panel.setLayout(new FlowLayout());		
		panel.setBorder(new TitledBorder(null, "Predefined Data Types", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel);

		panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(440,250));
		panel_1.setLayout(new FlowLayout());
		panel_1.setBorder(new TitledBorder(null, "User-defined Data Types", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_1);
		
		lblBooleanType = new JLabel("Boolean type: ");
		lblBooleanType.setPreferredSize(new Dimension(250,30));
		panel.add(lblBooleanType);
		
		booltype_textField = new JTextField();
		lblBooleanType.setLabelFor(booltype_textField);
		booltype_textField.setPreferredSize(new Dimension(40,30));
		booltype_textField.setEditable(false);
		booltype_textField.setBackground(Color.WHITE);
		panel.add(booltype_textField);
		booltype_textField.setColumns(10);
		
		lblIntegerType = new JLabel("Integer type: ");
		lblIntegerType.setPreferredSize(new Dimension(225,30));
		panel.add(lblIntegerType);
		
		
		min_integertype_textField = new JTextField();
		max_integertype_textField = new JTextField();
		min_integertype_textField.setPreferredSize(new Dimension(40,30));
		max_integertype_textField.setPreferredSize(new Dimension(40,30));
		
		panel.add(min_Label);		
		panel.add(min_integertype_textField);
		panel.add(max_Label);		
		panel.add(max_integertype_textField);
		
		/*
		//integertype_textField = new JTextField();
		lblIntegerType.setLabelFor(integertype_textField);
		integertype_textField.setPreferredSize(new Dimension(50,30));
		panel.add(integertype_textField);
		integertype_textField.setColumns(10);
		*/
		blank_label_1.setPreferredSize(new Dimension(440,5));
		updateIntegerTypeButton = new JButton("Update");
		panel.add(blank_label_1);
		panel.add(updateIntegerTypeButton);
		
		
		//usertype_textArea = new JTextArea(9,30);

		//infoArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		pluginButton = new JButton("Import from:");
		selectPluginComboBox = new JComboBox();
		selectPluginComboBox.addItem("No plugin selected");
		selectPluginComboBox.setPreferredSize(new Dimension(200, 30));
		
		Vector all_plugins_vector = XMLParser.getPlugins();
		
		for(int uy=0;uy<all_plugins_vector.size();uy++) {
			Vector single_plugin_vector = (Vector) all_plugins_vector.elementAt(uy);
			selectPluginComboBox.addItem(single_plugin_vector.firstElement());
		}
		
		
		dataTypesListModel = new DefaultListModel();
		//loadExistingServices();

		dataTypesList = new JList(dataTypesListModel);
		dataTypesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dataTypesList.setSelectedIndex(-1);
		//providersList.setVisibleRowCount(8);		
		
		dataTypesScrollPane = new JScrollPane(dataTypesList);
		dataTypesScrollPane.setPreferredSize(new Dimension(320,150));
		dataTypesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		dataTypesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		dataTypesButtonPanel = new JPanel();
		dataTypesButtonPanel.setPreferredSize(new Dimension(90,120));
		
		addDataTypeButton = new JButton("Add");
		addDataTypeButton.setPreferredSize(new Dimension(70,30));
		deleteDataTypeButton = new JButton("Delete");
		deleteDataTypeButton.setPreferredSize(new Dimension(70,30));
		
		dataTypesButtonPanel.add(addDataTypeButton);
		dataTypesButtonPanel.add(deleteDataTypeButton);
		
		blank_label_2.setPreferredSize(new Dimension(440,5));
		
		/*
		usertype_scrollPane = new JScrollPane();
		usertype_scrollPane.setViewportView(usertype_textArea);
		usertype_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		usertype_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel_1.add(usertype_scrollPane);	
		panel_1.add(pluginButton);
		panel_1.add(selectPluginComboBox);
		*/
		
		panel_1.add(dataTypesScrollPane);	
		panel_1.add(dataTypesButtonPanel);	
		panel_1.add(blank_label_2);
		panel_1.add(pluginButton);
		panel_1.add(selectPluginComboBox);
		
		btnOK = new JButton("OK");
		contentPane.add(btnOK);
		
		
	}
	
	/** Metodo ausiliario per mostrare i valori "minimo" e "massimo" settati per
	 * il tipo predefinito Integer_type */
	public void mostraBoundsInt(Vector<String> v){
		
		min_integertype_textField.setText(v.get(0));
		max_integertype_textField.setText(v.get(1));
		
	}
	
	/** Metodo ausiliario per mostrare i valori del tipo predefinito Boolean_type 
	 * nell'apposito jtextfield*/
	public void mostraBooleanValues(){
		String res = "";
		res = "{" + "true" + "," + "false" + "}"  ;
		
		booltype_textField.setText(res);
		
	}
	
	/** Metodo ausiliario che mostra i vari data_types (definiti dall'utente)
	 * nell'apposita jtextarea */
	public void mostraDataTypes(Vector<Vector<String>> v){
		
		dataTypesListModel.removeAllElements();
		
		String res = "";
		String app2 = "";
		String app3 = "";
		
		for(int i=0;i<v.size();i++){
				res = res + v.get(i).get(0) + " = <";
				for(int j=1;j<v.get(i).size();j++){
					if(j==v.get(i).size()-1)
						app2 = app2+v.get(i).get(j);
					else
						app2 = app2+v.get(i).get(j)+",";
					
				}
				app3=">";
				res=res+app2+app3;
				
				dataTypesListModel.addElement(res);
				
				app2="";app3="";res="";
		}
		
	}
	
	
	/** Metodo ausiliario per leggere ci� che � scritto nella textArea dei data_types definiti
	 * dall'utente*/
	/*
	public Vector<Vector<String>> leggiDataTypesArea(){
		Vector<Vector<String>> d_textArea = new Vector<Vector<String>>();
		Vector<String> data_type = null;
		
		String dt_textArea = usertype_textArea.getText();
		if(dt_textArea.contains("<empty>")){
			System.out.println(dt_textArea);
		}
		else{ // IL CAMPO NON E' VUOTO
			String[] tokens = dt_textArea.split("\\s*\n");
			//Ogni elemento dell'array "tokens" � una riga di tipo "data_type= <d1,d2,d3,...,dn>"
			for(int ind=0;ind<tokens.length;ind++){
				String[] tokens_2 = tokens[ind].split("\\s*= <");
				//L'array "tokens_2" ha due elementi:
				//l'elemento tokens_2[0] � il nome del data_type
				//l'elemento tokens_2[1] � una riga del tipo "d1,d2,d3,...,dn>" 
				data_type = new Vector<String>(); // creo un vector di stringhe
				data_type.add(tokens_2[0]);
				
				//System.out.println("ECCO_PROVA "+tokens_2[0]);				
				String[] tokens_3 = tokens_2[1].split("\\s*>");
				//L'array "tokens_3" ha un elemento: 
				//l'elemento tokens_3[0] � una riga del tipo "d1,d2,d3,...,dn" 
				
				String[] tokens_4 = tokens_3[0].split("\\s*,");
				//Ogni elemento dell'array "tokens_4" � un singolo ruolo di tipo "data_type".
				
				for(int x=0;x<tokens_4.length;x++){
					//System.out.println("ECCO2 "+tokens_4[x]);
					
					data_type.add(tokens_4[x]);
						
				} 
				// role �, ogni volta, un vettore del tipo "[role_type, r1, r2, ..., rn]"
				//System.out.println("PROVA VETTORE: "+ role.toString());
				d_textArea.add(data_type);
			}
		}
		return d_textArea;
	}
	*/
	
	public JButton getUpdateIntegerTypeButton() {
		return updateIntegerTypeButton;
	}

	public JButton getOKButton() {
		return btnOK;
	}

	public JTextField getMin_integertype_textField() {
		return min_integertype_textField;
	}

	public JTextField getMax_integertype_textField() {
		return max_integertype_textField;
	}	

	public JButton getAddDataTypeButton() {
		return addDataTypeButton;
	}

	public JButton getDeleteDataTypeButton() {
		return deleteDataTypeButton;
	}

	public JButton getPluginButton() {
		return pluginButton;		
	}
	
	public JComboBox getPluginsComboBox() {
		return selectPluginComboBox;		
	}

	public JList getDataTypesList() {
		return dataTypesList;
	}

	public DefaultListModel getDataTypesListModel() {
		return dataTypesListModel;
	}

	
}
