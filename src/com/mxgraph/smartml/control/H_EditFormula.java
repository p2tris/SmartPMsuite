package com.mxgraph.smartml.control;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.EditFormula;

public class H_EditFormula {
	
	public EditFormula _view = null;
	public String effect = "";
	
	public H_EditFormula(EditFormula i_view){
		_view = i_view;
		installListeners();
	}

	private void installListeners() {
		
		_view.btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("CANCEL")){
					
					_view.dispose();
				}
				
			}
		});
		
		_view.btnUpd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("Update")){
					String nameold = _view.old_name; //// nome (vecchio) della formula
					
					// CANCELLO LA VECCHIA FORMULA
					XMLParser.deleteFormulaFromXSD(nameold);
					XMLParser.deleteFormula(nameold);
					
					String namenew = _view.NameFormula();    // (nuovo) nome della Formula
					
					Vector<String> other_elementsName = XMLParser.getAllSchemaNames(true);
					other_elementsName.addAll(XMLParser.getAllRepositoryNames(true));
					
					if(!other_elementsName.contains(namenew.toLowerCase())){
						
						Vector<Vector<String>> argmnts = _view.ArgsFormula(); // argomenti della formula (nome-tipo)
						
						String all_content = _view.getContentFormula(); // contenuto della formula (è una semplice stringa)
						
						// (RI-)CREO LA NUOVA FORMULA
						XMLParser.createFormula(namenew,argmnts,_view.getContentFormula());
						XMLParser.addFormulaToXSD(namenew); 
						
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
					
				} // chiude if... "CREATE FORMULA"
				
			}
		});
		
	}

}