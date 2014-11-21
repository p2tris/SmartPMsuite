package com.mxgraph.smartml.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.AddEffectsToExogenousEvent;
import com.mxgraph.smartml.view.AddExogenousEvent;

public class H_AddExogenousEvent {
	
	public AddExogenousEvent _view = null;
	public String effect = "";
	public static Vector<Vector<String>> attual_argum;
	
	public H_AddExogenousEvent(AddExogenousEvent i_view){
		_view = i_view;
		installListeners();
	}

	private void installListeners() {
		attual_argum = new Vector<Vector<String>>();
		
		_view.btnAddEffects.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("ADD >>")){
					// VETTORE AUSILIARIO CON GLI ARGOMENTI ATTUALI 
					// DEFINITI PER L'EVENTO ESOGENO (INSERITI DALL'UTENTE)
					// --> GLI ARGOMENTI ATTUALI DEVONO POTER ESSERE USATI DINAMICAMENTE NEL WIZARD 
					attual_argum = _view.getArgsExEvent();
					
					AddEffectsToExogenousEvent effExEv = new AddEffectsToExogenousEvent();
					effExEv.setVisible(true);
					
					if(effExEv.isFinished()){
						effect = _view.eff_textArea.getText();
						if(effect.isEmpty()){
							effect = effExEv.effect_string;
							_view.eff_textArea.setText(effect);
						}
						else{ // effect NON è vuota! c'è già un effetto
							effect = effect + " " + "AND\n" + effExEv.effect_string;
							_view.eff_textArea.setText(effect);
						}
					}
				}
			}
		});
		
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
				if(e.getActionCommand().equalsIgnoreCase("Add")){
					String name = _view.NameExEvent();    // nome dell'evento esogeno
					
					Vector<String> other_elementsName = XMLParser.getAllSchemaNames(true);
					other_elementsName.addAll(XMLParser.getAllRepositoryNames(true));
					
					if(!other_elementsName.contains(name.toLowerCase())){
						Vector<Vector<String>> argmnts = _view.getArgsExEvent(); // argomenti dell'evento esogeno (nome-tipo)
						
						Vector<String> all_effects = _view.EffectsExEvent(); // effetti dell'evento esogeno
						
						XMLParser.createExogenousEvent(name, argmnts, all_effects);
						XMLParser.addExogenousEventToXSD(name);
						
						XMLParser.mf.updateInformationView();
						
						_view.dispose();
						
					}
					else{
						/* Esiste già sul file "schema.xsd" o sul file "repository.xml"
						 * un qualche elemento avente tale nome (il controllo nel file xsd è effettuato
						 * solo a livello dei tipi, non a livello dei data_objects). Quindi si può creare
						 * un evento con il nome di un data_object, come per esempio act1 */
						
						JOptionPane.showMessageDialog(_view, "Already exist such an element in the XML document!", "ERROR!!", JOptionPane.ERROR_MESSAGE);
						
					}
					
				} 
				
			}
		});
		
	}

}
