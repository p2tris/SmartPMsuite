package com.mxgraph.smartml.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.AddAtomicTerm;

public class H_AddAtomicTerm {
	
	public AddAtomicTerm _view = null;
	
	public H_AddAtomicTerm(AddAtomicTerm i_view){
		
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
		
		_view.btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("Add")){
					String name_at = _view.name_textField.getText();
					String type_at = null;
					
					Vector<String> other_elementsName = XMLParser.getAllSchemaNames(true);
					other_elementsName.addAll(XMLParser.getAllRepositoryNames(true));
					
					Vector<String> v1 = new Vector<String>(); // sarà un vettore del tipo: [NAME_AT,TYPE_AT(static/dynamic)] 
					Vector<Vector<String>> v2 = new Vector<Vector<String>>(); 
					/* sarà un vettore con tante componenti quante sono le ckbx cliccate all'interno di "Arguments".
					 * Ogni sottovettore sarà del tipo: [NAME,TYPE] */
					Vector<String> v3 = new Vector<String>(); // sarà un vettore del tipo: [VALUE,RELEVANT(true/false)] se v1[1]="dynamic" altrimenti del tipo [VALUE] ??
					
					if(!other_elementsName.contains(name_at.toLowerCase())){
						// leggo tutte le componenti che mi servono e mi costruisco i vettori che mi servono
						v1.add(name_at);
						if(_view.rdbtnDynamic.isSelected()){
							type_at = _view.rdbtnDynamic.getText();
						}
						else if(_view.rdbtnStatic.isSelected()){ 
							type_at = _view.rdbtnStatic.getText();
						}
						v1.add(type_at); // v1 OK!
						
						v3.add((String)_view.value_combo.getSelectedItem().toString());
						if(v1.get(1).equalsIgnoreCase("Dynamic")){
							String rel=_view.rel_combo.getSelectedItem().toString();
							v3.add(rel);
						}
						// v3 OK!
						
						if(_view.ck1.isSelected()){
							Vector<String> c1 = new Vector<String>();
							c1.add(_view.name_textField1.getText());
							c1.add((String)_view.type_cb1.getSelectedItem());
							v2.add(c1);
						}
						if(_view.ck2.isSelected()){
							Vector<String> c2 = new Vector<String>();
							c2.add(_view.name_textField2.getText());
							c2.add((String)_view.type_cb2.getSelectedItem());
							v2.add(c2);
						}
						if(_view.ck3.isSelected()){
							Vector<String> c3 = new Vector<String>();
							c3.add(_view.name_textField3.getText());
							c3.add((String)_view.type_cb3.getSelectedItem());
							v2.add(c3);
						}
						if(_view.ck4.isSelected()){
							Vector<String> c4 = new Vector<String>();
							c4.add(_view.name_textField4.getText());
							c4.add((String)_view.type_cb4.getSelectedItem());
							v2.add(c4);
						}
						if(_view.ck5.isSelected()){
							Vector<String> c5 = new Vector<String>();
							c5.add(_view.name_textField5.getText());
							c5.add((String)_view.type_cb5.getSelectedItem());
							v2.add(c5);
						}
						// OK v2
						
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
