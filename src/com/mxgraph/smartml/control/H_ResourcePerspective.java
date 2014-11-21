package com.mxgraph.smartml.control;

import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.MainFrame;
import com.mxgraph.smartml.view.ResourcePerspective;

public class H_ResourcePerspective {

	private ResourcePerspective _view = null;
	
	
	public H_ResourcePerspective (ResourcePerspective i_view){
		_view = i_view;
		installListeners();
	}

	private void installListeners() {
		
		_view.btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getActionCommand().equals("UPDATE")){
					
					Vector<String> v_capabilities= _view.leggiCapabilitiesArea();
					XMLParser.setCapabilities(v_capabilities);
					
					// CONTROLLI CHE DEVO FARE PRIMA DI MODIFICARE IL DOCUMENTO XML
					Vector<String> vector_participants = _view.leggiParticipantsArea();
					Vector<Vector<String>> vector_roles = _view.leggiRolesArea();
					Vector<String> roles = new Vector<String>();
					for(int i=0;i<vector_roles.size();i++){
						for(int index=1;index<vector_roles.get(i).size();index++){
							roles.add(vector_roles.get(i).get(index));
							
						}
					}
					
					//roles è un vettore composto dai singoli partecipanti che si trovano nella textarea dei ruoli
					if(vector_participants.containsAll(roles)){
						
					//	XMLParser.setRoles(vector_roles); 
					}
					else{
						JOptionPane.showMessageDialog(_view, "Ogni partecipante in ROLES deve essere presente anche in PARTICIPANTS!!", "ATTENTION!!", JOptionPane.ERROR_MESSAGE);
					}
					
					XMLParser.mf.updateInformationView();
					_view.dispose();
			
				}
			}

		});
		
		_view.btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getActionCommand().equals("CANCEL")){
					
					XMLParser.mf.updateInformationView();
					_view.dispose(); 
					
				}
			}
		});
		
	}
	

}
