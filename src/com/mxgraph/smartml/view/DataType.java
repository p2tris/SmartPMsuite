package com.mxgraph.smartml.view;

import java.awt.Color;  
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

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
	public JTextField integertype_textField;
	public JPanel panel; 
	public JPanel panel_1; 
	public JLabel lblBooleanType;
	public JLabel lblIntegerType;
	public JLabel lblPredefinedDataType;
	public JLabel lblUserDefinedData;
	public JScrollPane usertype_scrollPane;
	public JTextArea usertype_textArea;
	public JButton btnCancel;
	public JButton btnUpdate;
	
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
		setSize(450, 450);
		setLocationRelativeTo(null);
		setResizable(false);  // LA FINESTRA NON E' RIDIMENSIONABILE
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout());
		
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(440,110));
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
		lblIntegerType.setPreferredSize(new Dimension(250,30));
		panel.add(lblIntegerType);
		
		integertype_textField = new JTextField();
		lblIntegerType.setLabelFor(integertype_textField);
		integertype_textField.setPreferredSize(new Dimension(50,30));
		panel.add(integertype_textField);
		integertype_textField.setColumns(10);
		
		usertype_textArea = new JTextArea(9,30);


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
		
		
		usertype_scrollPane = new JScrollPane();
		usertype_scrollPane.setViewportView(usertype_textArea);
		usertype_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		usertype_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel_1.add(usertype_scrollPane);	
		panel_1.add(pluginButton);
		panel_1.add(selectPluginComboBox);
		
		btnUpdate = new JButton("Update");
		contentPane.add(btnUpdate);
		
		btnCancel = new JButton("Cancel");
		contentPane.add(btnCancel);
		
		
		
	}
	
	/** Metodo ausiliario per mostrare i valori "minimo" e "massimo" settati per
	 * il tipo predefinito Integer_type */
	public void mostraBoundsInt(Vector<String> v){
		String res = "";
		res ="<" + v.get(0) + ". ." + v.get(1) + ">";
		
		integertype_textField.setText(res);
		
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
		
		String res = "";
		String app2 = "";
		String app3 = "";
		
		if(v.isEmpty()){
			res = "<empty>\n\n";
			
			res = res + "To define a new data type,\nplease use the following syntax:\n\n";
			res = res + "DataTypeName_type = <obj1,obj2,...,objn>";
			
		}
		for(int i=0;i<v.size();i++){
			res = res + v.get(i).get(0) + " = <";
			for(int j=1;j<v.get(i).size();j++){
				if(j==v.get(i).size()-1)
					app2 = app2+v.get(i).get(j);
				else
					app2 = app2+v.get(i).get(j)+",";
				
			}
			app3=">\n";
			res=res+app2+app3;
			app2="";
		}
		usertype_textArea.setText(res);
			
		
	}
	
	/** Metodo ausiliario per leggere ci� che � scritto nella textarea dell'Integer_type*/
	public Vector<String> leggiIntegerBounds(){

		Vector<String> b = new Vector<String>();
		
		String usr = integertype_textField.getText();
		String[] tokens = usr.split("\\s*>");
		// L'array tokens ha due elementi:
		// l'elemento tokens[0] � "{0. .50"
		// l'elemento tokens[1] � un elemento vuoto
		String[] tokens_1 = tokens[0].split("\\s*. .");
		// L'array tokens_1 ha due elementi:
		// l'elemento tokens_1[0] � "<0"
		// l'elemento tokens_1[1] � "50"
		String[] tokens_2 = tokens_1[0].split("\\s*<");
		// L'array tokens_2 ha due elementi:
		// l'elemento tokens_2[0] � vuoto
		// l'elemento tokens_2[1] � "0" 
		
		b.add(tokens_2[1]);
		b.add(tokens_1[1]);
		return b;
		
	}
	
	
	/** Metodo ausiliario per leggere ci� che � scritto nella textArea dei data_types definiti
	 * dall'utente*/
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
	
	public JTextArea getUserTextArea() {
		return usertype_textArea;		
	}
	
	public JButton getPluginButton() {
		return pluginButton;		
	}
	
	public JComboBox getPluginsComboBox() {
		return selectPluginComboBox;		
	}
	
}
