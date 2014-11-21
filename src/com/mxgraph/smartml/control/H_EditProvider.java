package com.mxgraph.smartml.control;

import java.awt.PageAttributes.OriginType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.EditProvider;
import com.mxgraph.smartml.view.ServicePerspective;

public class H_EditProvider {
	
	public EditProvider _view = null;
	
	public H_EditProvider (EditProvider i_view){
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
            	
            	boolean areServicesChanged = false;
            	
            	for(int i=0;i<_view.getProviderServicesListModel().size();i++) {
            		newProviderServicesListVector.addElement(_view.getProviderServicesListModel().getElementAt(i));
            		
            		if(!_view.getOriginalProviderServicesVector().contains(_view.getProviderServicesListModel().getElementAt(i)))
            			areServicesChanged = true;
            	   	}
            	
            		if(_view.getOriginalProviderServicesVector().size() != _view.getProviderServicesListModel().size())
            			areServicesChanged = true;
            	
            	//FIRST CASE : NO CHANGE HAS BEEN MADE TO THE PROVIDER
            	if(_view.getOriginalProvider().equalsIgnoreCase(_view.getProviderNameTextField().getText()) && areServicesChanged == false)
            	{
            		JOptionPane.showMessageDialog(null, "No change has been done to the provider "+ _view.getOriginalProvider(), "INFORMATION", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            		_view.dispose();
            	}
            	//SECOND CASE : THE NAME OF THE PROVIDER IS ALREADY EXISTING
            	else if(XMLParser.getProvidersNames(true).contains(_view.getProviderNameTextField().getText().toLowerCase()) && !_view.getOriginalProvider().equalsIgnoreCase(_view.getProviderNameTextField().getText()))
            	{
            		JOptionPane.showMessageDialog(null, "The provider '" + _view.getProviderNameTextField().getText().toLowerCase() + "' already exists. Please choose a different name for the provider!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	}            		
            	//THIRD CASE : THE NAME OF THE PROVIDER AND/OR ITS INTERNAL SERVICES ARE CHANGED.   
            	else  {
            		
            		String newProvider = _view.getProviderNameTextField().getText() + " = <";
            		
            		for(int j=0;j<newProviderServicesListVector.size();j++) {            			
            			if(j==newProviderServicesListVector.size()-1) newProvider = newProvider + newProviderServicesListVector.elementAt(j);
            			else newProvider = newProvider + newProviderServicesListVector.elementAt(j)+",";
            		}
            		
            		newProvider = newProvider + ">";
            		
            		int reply = JOptionPane.showConfirmDialog(null, "Do you want to change the original provider\n" + _view.getProviderWithAssociatedServices()+ " with\n" +  newProvider + "?", "ATTENTION!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/question_icon.png"));
					   
					   if(reply==0) {						   
						   XMLParser.setProvider(_view.getOriginalProvider(), _view.getProviderNameTextField().getText(), newProviderServicesListVector);
						   _view.getServicePerspective().loadExistingProviders();
						   _view.dispose();
					   }
            		
            	}

            }
        });
		
	}

}
