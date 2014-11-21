package com.mxgraph.smartml.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.EditAtomicTerm;

public class H_EditAtomicTerm {
	
	public EditAtomicTerm _view = null;
	
	public H_EditAtomicTerm(EditAtomicTerm i_view){
		
		_view = i_view;
		installListeners();
	}

	private void installListeners() {
		
		
		_view.rdbtnDynamic.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(e.getActionCommand().equals("Dynamic")){
					_view.panel_2.setVisible(true);
				
				}
				
			}
		});
		
		if(_view.rdbtnDynamic.isSelected()){
			_view.panel_2.setVisible(true);
		}
		
		
		_view.rdbtnStatic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("Static")){
					_view.panel_2.setVisible(false);
					
				}
				
			}
		});
		
		_view.btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("CANCEL")){
					
					_view.dispose();
				}
				
			}
		});
		
		_view.btnUpdt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("UPDATE")){
					String nameold = _view.old_name; // nome vecchio dell'atomic term
					// CANCELLO IL VECCHIO ATOMIC TERM
					XMLParser.deleteTermFromTheoryElement(nameold);
					XMLParser.deleteAtomicTerm(nameold);
					
					String namenew = _view.NameAtomicTerm(); // (nuovo) nome dell'atomic term
					
					
					Vector<String> other_elementsName = XMLParser.getAllSchemaNames(true);
					other_elementsName.addAll(XMLParser.getAllRepositoryNames(true));
					
					if(!other_elementsName.contains(namenew.toLowerCase())){
						String type_at = _view.ModeAtTerm();
						
						Vector<String> v1 = new Vector<String>(); // sarà un vettore del tipo: [NAME_AT,MODE_AT(static/dynamic)]
						v1.add(namenew);
						v1.add(type_at);
						
						Vector<Vector<String>> v2 = _view.ArgsAtTerm(); 
						/* sarà un vettore con tante componenti quante sono le ckbx cliccate all'interno di "Arguments".
						 * Ogni sottovettore sarà del tipo: [NAME,TYPE] */
						
						Vector<String> v3 = new Vector<String>(); // sarà un vettore del tipo: [VALUE_RET,RELEVANT(true/false)] se v1[1]="dynamic" altrimenti del tipo [VALUE] ??
						v3.add(_view.ReturnedValue());
						if(type_at.equalsIgnoreCase("dynamic")){
							v3.add(_view.RelevantValue());
						}
						
						XMLParser.addAtomicTerm(v1,v2,v3);
						XMLParser.addAtomicTermToTheoryElement(v1.firstElement());
						
						XMLParser.mf.updateInformationView();
						_view.dispose();
						
					}
					else{
						/* Esiste un qualche xml elements di tipo: 
						 * "participant","role","capability","predefined_data_type",
						 * "data_type","task","predefined_term","dynamic_term" e "static_term" 
						 * avente già un tale nome. */
						JOptionPane.showMessageDialog(_view, "Already exist such an element in the XML document!", "ERROR!!", JOptionPane.ERROR_MESSAGE);;
						
					}
					
				}
				
			}
		});
		
		
	}

}

