package com.mxgraph.smartml.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.AddCapability;
import com.mxgraph.smartml.view.AddProvider;
import com.mxgraph.smartml.view.AddService;
import com.mxgraph.smartml.view.AddServiceProvidesCapability;
import com.mxgraph.smartml.view.AddTaskRequiresCapability;
import com.mxgraph.smartml.view.CapabilitiesPerspective;
import com.mxgraph.smartml.view.EditProvider;
import com.mxgraph.smartml.view.EditService;
import com.mxgraph.smartml.view.ServicePerspective;

public class H_CapabilitiesPerspective {
	
	public CapabilitiesPerspective _view = null;
	
	
	public H_CapabilitiesPerspective (CapabilitiesPerspective i_view){
		_view = i_view;
		installListeners();
	}


	private void installListeners() {
		
		_view.addWindowListener(new WindowAdapter() {
					
			public void windowClosing(WindowEvent e)
			  {
				XMLParser.mf.updateInformationView();
			  }
			});
		
		
		 _view.getAddCapabilityButton().addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent ae)
	            {
	            
	            	new AddCapability(_view);
	            	
	            }
	        });
		
		 _view.getDeleteCapabilityButton().addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent ae)
	            {
	            
	            	int selected_index = _view.getCapabilitiesList().getSelectedIndex();
	            	
	            	if (selected_index == -1) { //no selection
	            		JOptionPane.showMessageDialog(null, "Please select a capability to delete!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
	            	} 
	            	else { 
	                    
	            	   	String selected_capability = (String) _view.getCapabilitiesListModel().getElementAt(selected_index);

	            	    int reply = JOptionPane.showConfirmDialog(null, "Do you want to delete the capability '" + selected_capability + "'?", "ATTENTION!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/question_icon.png"));
						   
						   if(reply==0) {						   
							   
							   XMLParser.removeCapability(selected_capability); 
							   XMLParser.removeAssociationsBetweenServicesAndACapability(selected_capability); 
							   XMLParser.removeAssociationsBetweenTasksAndSingleCapability(selected_capability);
						   
							   _view.loadExistingCapabilities();
							   _view.loadExistingProvides();
							   _view.loadExistingRequires();
							   
						   }

	            	 }
	            	
	            }
	        });
		 
		_view.getAddProvidesButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	new  AddServiceProvidesCapability(_view);
            }
        });

		 _view.getDeleteProvidesButton().addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent ae)
	            {
	            	
	            	int selected_index = _view.getProvidesList().getSelectedIndex();
	            	
	            	if (selected_index == -1) { //no selection
	            		
	            		JOptionPane.showMessageDialog(null, "Please select an item to remove!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
	            	
	            	} 
	            	else {
	                    
	            	   	String complete_provides_string = (String) _view.getProvidesListModel().getElementAt(selected_index);

	            	   	String[] split = complete_provides_string.split(" -->provides--> ");
	            	   	String selected_service = split[0];
	            	   	String selected_capability = split[1];
	            	   	
	            	    int reply = JOptionPane.showConfirmDialog(null, "Do you want to delete the association between '" + selected_service + "' and '" + selected_capability + "'?", "ATTENTION!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/question_icon.png"));
						   
						   if(reply==0) {						   
							   XMLParser.removeAssociationBetweenAServiceAndACapability(selected_service,selected_capability); 							   
							   _view.loadExistingProvides();						   
						   }

	            	 }
	               
	            }
	        });
		 
		 _view.getAddRequiresButton().addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent ae)
	            {
		           	new AddTaskRequiresCapability(_view);
	            	
	            }
	        });
		 
		 _view.getDeleteRequiresButton().addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent ae)
	            {
	            	
	            	int selected_index = _view.getRequiresList().getSelectedIndex();
	            	
	            	if (selected_index == -1) { //no selection
	            		
	            		JOptionPane.showMessageDialog(null, "Please select an item to remove!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
	            	
	            	} 
	            	else {
	                    
	            	   	String complete_requires_string = (String) _view.getRequiresListModel().getElementAt(selected_index);

	            	   	String[] split = complete_requires_string.split(" -->requires--> ");
	            	   	String selected_task = split[0];
	            	   	String selected_capability = split[1];
	            	   	
	            	    int reply = JOptionPane.showConfirmDialog(null, "Do you want to delete the association between '" + selected_task + "' and '" + selected_capability + "'?", "ATTENTION!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/question_icon.png"));
						   
						   if(reply==0) {						   
							   XMLParser.removeAssociationBetweenATaskAndACapability(selected_task,selected_capability); 							   
							   _view.loadExistingRequires();						   
						   }

	            	 }
	               
	            }
	        });
		 
		 _view.getOkButton().addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent ae)
	            {
	            	XMLParser.mf.updateInformationView();
	            	_view.dispose();
	            	
	            }
	        });
		
	}

}
