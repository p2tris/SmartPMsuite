package com.mxgraph.smartml.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.AddEffectsEditTask;
import com.mxgraph.smartml.view.AddPreconditionEditTask;
import com.mxgraph.smartml.view.EditTask;

public class H_EditTask {
	
	public EditTask _view = null;
	public String precondition = ""; 
	public String effect = "";
	public static Vector<Vector<String>> attual_arg; 
	
	public H_EditTask (EditTask i_view){
		_view = i_view;
		installListeners();
		
	}
	
	private void installListeners() {
		attual_arg = new Vector<Vector<String>>();
		
		_view.btnAdd_preconds.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("ADD >>")){
					
					// VETTORE AUSILIARIO CON GLI ARGOMENTI ATTUALI 
					// DEFINITI PER IL TASK (INSERITI DALL'UTENTE)
					// --> GLI ARGOMENTI ATTUALI DEVONO POTER ESSERE USATI DINAMICAMENTE NEL WIZARD 
					attual_arg = _view.ArgsTask();
					
					AddPreconditionEditTask apET = new AddPreconditionEditTask();
					apET.setVisible(true);
		 
					if(apET.isFinished()){
						
						precondition = _view.prec_textArea.getText();
						if(precondition.isEmpty()){
							precondition = apET.precond_string;
							_view.prec_textArea.setText(precondition);
						}
						else{ // precondition NON è vuota!--> c'è già una precondizione
							precondition = precondition + " " + "AND\n" + apET.precond_string;
							_view.prec_textArea.setText(precondition);
						}
						

					}
				}
				
			}
		});
		
		_view.btnAdd_effects.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("ADD >>")){
					
					// VETTORE AUSILIARIO CON GLI ARGOMENTI ATTUALI 
					// DEFINITI PER IL TASK (INSERITI DALL'UTENTE)
					// --> GLI ARGOMENTI ATTUALI DEVONO POTER ESSERE USATI DINAMICAMENTE NEL WIZARD  
					attual_arg = _view.ArgsTask();
					
					AddEffectsEditTask aeET = new AddEffectsEditTask();
					aeET.setVisible(true);
					
					if(aeET.isFinished()){
						if(aeET.eff_mode.equalsIgnoreCase("supposed")){
							effect = _view.effSupp_textArea.getText();
							if(effect.isEmpty()){
								effect = aeET.effect_string;
								_view.effSupp_textArea.setText(effect);
							}
							else{ // effect NON è vuota! c'è già un effetto
								effect = effect + " " + "AND\n" + aeET.effect_string;
								_view.effSupp_textArea.setText(effect);
							}
						}
						else if(aeET.eff_mode.equalsIgnoreCase("automatic")){
							effect = _view.effAuto_textArea.getText();
							if(effect.isEmpty()){
								effect = aeET.effect_string;
								_view.effAuto_textArea.setText(effect);
							}
							else{ // effect NON è vuota! c'è già un effetto
								effect = effect + " " + "AND\n" + aeET.effect_string;
								_view.effAuto_textArea.setText(effect);
							}
							
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
		
		_view.btnUpdt.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("Update")){
					String nameold = _view.old_name;
					// CANCELLO IL VECCHIO TASK
					XMLParser.deleteTaskFromXSD(nameold);
					XMLParser.deleteTask(nameold);
					
					String namenew = _view.NameTask();    // (nuovo) nome del task
					
					Vector<String> other_elementsName = XMLParser.getAllSchemaNames(true);
					other_elementsName.addAll(XMLParser.getAllRepositoryNames(true));
					
					if(!other_elementsName.contains(namenew.toLowerCase())){
						
						Vector<Vector<String>> argmnts = _view.ArgsTask(); // argomenti del task (nome-tipo)
					
						Vector<String> all_preconditions = _view.PreconditionsTask(); // precondizioni del task
						
						Vector<String> all_supposed_effects = _view.SupposedEffectsTask(); // supposed-effects del task
						
						Vector<String> all_automatic_effects = _view.AutomaticEffectsTask(); // automatic-effects del task
						
						// (RI-)CREO IL NUOVO TASK
						XMLParser.createTask(namenew, argmnts, all_preconditions, all_supposed_effects, all_automatic_effects);
						XMLParser.addTaskToXSD(namenew);
						
						XMLParser.mf.updateInformationView();
						
						_view.dispose();
					}
					else{
						/* Esiste già sul file "schema.xsd" o sul file "repository.xml"
						 * un qualche elemento avente tale nome (il controllo nel file xsd è effettuato
						 * solo a livello dei tipi, non a livello dei data_objects). Quindi si può creare
						 * un task con il nome di un data_object, come per es. act1 */
						
						JOptionPane.showMessageDialog(_view, "Already exist such an element!", "ERROR!!", JOptionPane.ERROR_MESSAGE);;
						
					}
					
				} // chiude if... "update-task"
				
			}
		});
		
	}

}
