package com.mxgraph.smartml.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.AddFormula;

public class H_AddFormula {
	
	public AddFormula _view = null;
	public String effect = "";
	
	
	public H_AddFormula(AddFormula i_view){
		_view = i_view;
		installListeners();
	}

	private void installListeners() {
		
		
		_view.btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("Cancel")){
					
					_view.dispose();
				}
				
			}
		});
		
		_view.btnCrt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("Create")){
					String formula_name = _view.getNameFormula();
					
					Vector<String> other_elementsName = XMLParser.getAllSchemaNames(true);
					other_elementsName.addAll(XMLParser.getAllRepositoryNames(true));
					
					if(!other_elementsName.contains(formula_name.toLowerCase())){
						
						Vector<Vector<String>> formula_arguments_vector = _view.getArgsFormula(); // argomenti della formula (nome-tipo)
						
						String formula_content_text = _view.getContentFormula(); // contenuto della formula (è una semplice stringa)
						
						XMLParser.createFormula(formula_name,formula_arguments_vector,formula_content_text);
						XMLParser.addFormulaToXSD(formula_name); 
						
						XMLParser.mf.updateInformationView();
						
						_view.dispose();
					
					}
					else{
						/* Esiste già sul file "schema.xsd" o sul file "repository.xml"
						 * un qualche elemento avente tale nome (il controllo nel file xsd è effettuato
						 * solo a livello dei tipi, non a livello dei data_objects). Quindi si può creare
						 * una formula con il nome di un data_object, come per esempio act1 */
						
						JOptionPane.showMessageDialog(_view, "Already exist such an element in the XML document!", "ERROR!!", JOptionPane.ERROR_MESSAGE);
						
					}
				} 
			}
		});
		
	}

}