package com.mxgraph.smartml.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.AddProvider;
import com.mxgraph.smartml.view.AddService;
import com.mxgraph.smartml.view.EditProvider;
import com.mxgraph.smartml.view.EditService;
import com.mxgraph.smartml.view.ServicePerspective;

public class H_Service_Perspective {
	
	public ServicePerspective _view = null;
	
	
	public H_Service_Perspective (ServicePerspective i_view){
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
		
		_view.getAddServiceButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	new AddService(_view);
            }
        });
		
		_view.getEditServiceButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	int selected_index = _view.getServicesList().getSelectedIndex();
            	
            	if (selected_index == -1) { //no selection
            		JOptionPane.showMessageDialog(null, "Please select a service to edit!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            		  } 
            	else {           
                    
            	   	String service_string = (String) _view.getServicesListModel().getElementAt(selected_index);

            	   	new EditService(_view,service_string);
            	

            	 }
            }
        });
		
		 _view.getDeleteServiceButton().addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent ae)
	            {
	            
	            	int selected_index = _view.getServicesList().getSelectedIndex();
	            	
	            	if (selected_index == -1) { //no selection
	            		JOptionPane.showMessageDialog(null, "Please select a service to delete!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
	            	} 
	            	else { 
	                    
	            	   	String selected_service = (String) _view.getServicesListModel().getElementAt(selected_index);

	            	    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the service '" + selected_service + "'?", "ATTENTION!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/question_icon.png"));
						   
						   if(reply==0) {						   
							   XMLParser.removeService(selected_service); 
							   XMLParser.removeServiceFROMProviders(selected_service);
							   XMLParser.removeAssociationsBetweenCapabilitiesAndAService(selected_service);
							   _view.loadExistingServices();
							   _view.loadExistingProviders();						   
						   }

	            	 }
	            	
	            }
	        });
		 
		_view.getAddProviderButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	new AddProvider(_view);
            }
        });

		 _view.getEditProviderButton().addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent ae)
	            {
	            	
	            	int selected_index = _view.getProvidersList().getSelectedIndex();
	            	
	            	if (selected_index == -1) { //no selection
	            		JOptionPane.showMessageDialog(null, "Please select a provider to edit!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
	            				     } 
	            	else {           //add after the selected item
	                    
	            	   	String complete_provider_string = (String) _view.getProvidersListModel().getElementAt(selected_index);

	            	   	new EditProvider(_view,complete_provider_string);
	            	

	            	 }
	               
	            }
	        });
		 
		 _view.getDeleteProviderButton().addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent ae)
	            {
	            	
	            	int selected_index = _view.getProvidersList().getSelectedIndex();
	            	
	            	if (selected_index == -1) { //no selection
	            		JOptionPane.showMessageDialog(null, "Please select a provider to delete!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
	            	} 
	            	else {           //add after the selected item
	                    
	            	   	String complete_provider_string = (String) _view.getProvidersListModel().getElementAt(selected_index);

	            	   	String[] split = complete_provider_string.split(" = ");
	            	   	String selected_provider = split[0];
	            	   	
	            	    int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the provider '" + selected_provider + "'?", "ATTENTION!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/question_icon.png"));
						   
						   if(reply==0) {						   
							   XMLParser.removeProvider(selected_provider); 
							   _view.loadExistingProviders();						   
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
