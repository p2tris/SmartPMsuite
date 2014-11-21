package com.mxgraph.smartml.control;

import java.awt.PageAttributes.OriginType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.AddProvider;
import com.mxgraph.smartml.view.EditProvider;
import com.mxgraph.smartml.view.ServicePerspective;

public class H_AddProvider {
	
	public AddProvider _view = null;
	
	public H_AddProvider (AddProvider i_view){
		_view = i_view;
		installListeners();
	}


	private void installListeners() {
		
		_view.getRightButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	
            	int selected_index = _view.getProviderServicesList().getSelectedIndex();
            	
            	if (selected_index == -1) { //no selection
            		JOptionPane.showMessageDialog(null, "Please select a provider's service to remove!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            				     } 
            	else {           //add after the selected item
                    
            	   	String provider_service = (String) _view.getProviderServicesListModel().getElementAt(selected_index);
            	   	
            	   	_view.getProviderServicesListModel().removeElementAt(selected_index);

            	   	_view.getOtherServicesListModel().addElement(provider_service);
           	

            	 }
               
            	

            }
        });
		
		_view.getLeftButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	
            	int selected_index = _view.getOtherServicesList().getSelectedIndex();
            	
            	if (selected_index == -1) { //no selection
            		JOptionPane.showMessageDialog(null, "Please select a service to be associated to the provider!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            				     } 
            	else {           //add after the selected item
                    
            	   	String provider_service = (String) _view.getOtherServicesListModel().getElementAt(selected_index);
            	   	
            	   	_view.getOtherServicesListModel().removeElementAt(selected_index);

            	   	_view.getProviderServicesListModel().addElement(provider_service);
           	

            	 }
               
            	

            }
        });
		
		_view.getCancelButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	
            	_view.dispose();

            }
        });
		
		_view.getOkButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	
            	Vector newProviderServicesListVector = new Vector();
            	
            	if(_view.getProviderNameTextField().getText().equalsIgnoreCase("") || 
            			_view.getProviderNameTextField().getText().equalsIgnoreCase(" ") ||	_view.getProviderNameTextField().getText().contains(" ")) {
            		
            			JOptionPane.showMessageDialog(null, "The name of the provider can not be empty and can not contain blank spaces!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            		
            	}
            	else if (XMLParser.getProvidersNames(true).contains(_view.getProviderNameTextField().getText().toLowerCase())) {
            		
            		JOptionPane.showMessageDialog(null, "The provider '" + _view.getProviderNameTextField().getText().toLowerCase() + "' is already existing. Please choose a different name for the provider!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	}
            	
            	else {
            	
            	String newProvider = _view.getProviderNameTextField().getText() + " = <";
            	
 	
            	for(int i=0;i<_view.getProviderServicesListModel().size();i++) {
            		newProviderServicesListVector.addElement(_view.getProviderServicesListModel().getElementAt(i));
            	}
            	for(int j=0;j<newProviderServicesListVector.size();j++) {            			
            			if(j==newProviderServicesListVector.size()-1) newProvider = newProvider + newProviderServicesListVector.elementAt(j);
            			else newProvider = newProvider + newProviderServicesListVector.elementAt(j)+",";
            		}
            	newProvider = newProvider + ">";
            		
            	XMLParser.addProvider(_view.getProviderNameTextField().getText(), newProviderServicesListVector);
            	
            	_view.getServicePerspective().loadExistingProviders();
				_view.dispose();
            		
            	}
            	
            }
        });
		
	}

}
