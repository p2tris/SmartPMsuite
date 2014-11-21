package com.mxgraph.smartml.control;

import java.awt.PageAttributes.OriginType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.AddProvider;
import com.mxgraph.smartml.view.AddServiceProvidesCapability;
import com.mxgraph.smartml.view.EditProvider;
import com.mxgraph.smartml.view.ServicePerspective;

public class H_AddServiceProvidesCapability {
	
	public AddServiceProvidesCapability _view = null;
	
	public H_AddServiceProvidesCapability (AddServiceProvidesCapability i_view){
		_view = i_view;
		installListeners();
	}


	private void installListeners() {
		
	
		_view.getCancelButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	
            	_view.dispose();

            }
        });
		
		_view.getServicesList().addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent e) {
		    	  
		    	  if(e.getValueIsAdjusting()) { // -- Used to avoid that the ListSelectionListener is invoked twice.
		    	  
		    	  String service_selected = (String) _view.getServicesList().getSelectedValue();
		    	  
		    	  _view.getServiceLabel().setText("Selected Service: " + service_selected);
		    	  _view.getCapabilitiesLabel().setText("Provided Capabilities: ");
		
		    	  Vector capabilities_provided_by_selected_service_vector = XMLParser.getCapabilitiesProvidedByService(service_selected);

		    	  if(capabilities_provided_by_selected_service_vector.isEmpty())
		    		  _view.getCapabilitiesLabel().setText(_view.getCapabilitiesLabel().getText() + "--none--");
		    	  
		    	  for(int i=0;i<capabilities_provided_by_selected_service_vector.size();i++)  {
		    		  
		    		  if(i==capabilities_provided_by_selected_service_vector.size()-1)
		    			  _view.getCapabilitiesLabel().setText(_view.getCapabilitiesLabel().getText() + capabilities_provided_by_selected_service_vector.elementAt(i));
		    		  else
		    			  _view.getCapabilitiesLabel().setText(_view.getCapabilitiesLabel().getText() + capabilities_provided_by_selected_service_vector.elementAt(i) + ", ");
		    	  }
		    	  
		    	  _view.loadOtherServiceCapabilities(capabilities_provided_by_selected_service_vector);
		    	  
		    	  }
	            }
	        });
		
		
		_view.getOkButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	
            	if(_view.getServicesList().getSelectedIndex() == -1) { //no service selected
            		JOptionPane.showMessageDialog(null, "Please select a service!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	} 
            	else if(_view.getCapabilitiesList().getSelectedIndex() == -1) { //no capability selected
            		JOptionPane.showMessageDialog(null, "Please select a capability to be associated to a service!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	} 
            	else {
            		XMLParser.addAssociationBetweenAServiceAndACapability((String) _view.getServicesList().getSelectedValue(), (String) _view.getCapabilitiesList().getSelectedValue());
            		
            		_view.getCapabilitiesPerspective().loadExistingProvides();
            		
            		_view.dispose();
            	}
            		
            	}
                    
        });
		
	}

}
