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
import com.mxgraph.smartml.view.AddTaskRequiresCapability;
import com.mxgraph.smartml.view.EditProvider;
import com.mxgraph.smartml.view.ServicePerspective;

public class H_AddTaskRequiresCapability {
	
	public AddTaskRequiresCapability _view = null;
	
	public H_AddTaskRequiresCapability (AddTaskRequiresCapability i_view){
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
		
		_view.getTasksList().addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent e) {
		    	  
		    	  if(e.getValueIsAdjusting()) { // -- Used to avoid that the ListSelectionListener is invoked twice.
		    	  
		    	  String task_selected = (String) _view.getTasksList().getSelectedValue();
		    	  
		    	  _view.getTaskLabel().setText("Selected Task: " + task_selected);
		    	  _view.getCapabilitiesLabel().setText("Required Capabilities: ");
		
		    	  Vector capabilities_required_by_selected_task_vector = XMLParser.getCapabilitiesRequiredByTask(task_selected);
		    	  
		    	  if(capabilities_required_by_selected_task_vector.isEmpty())
		    		  _view.getCapabilitiesLabel().setText(_view.getCapabilitiesLabel().getText() + "--none--");
		    	  
		    	  for(int i=0;i<capabilities_required_by_selected_task_vector.size();i++)  {
		    		  
		    		  if(i==capabilities_required_by_selected_task_vector.size()-1)
		    			  _view.getCapabilitiesLabel().setText(_view.getCapabilitiesLabel().getText() + capabilities_required_by_selected_task_vector.elementAt(i));
		    		  else
		    			  _view.getCapabilitiesLabel().setText(_view.getCapabilitiesLabel().getText() + capabilities_required_by_selected_task_vector.elementAt(i) + ", ");
		    	  }

		    	  _view.loadOtherTaskCapabilities(capabilities_required_by_selected_task_vector);
		    	  
		    	  }
	            }
	        });
		
		
		_view.getOkButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	
            	if(_view.getTasksList().getSelectedIndex() == -1) { //no service selected
            		JOptionPane.showMessageDialog(null, "Please select a task!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	} 
            	else if(_view.getCapabilitiesList().getSelectedIndex() == -1) { //no capability selected
            		JOptionPane.showMessageDialog(null, "Please select a capability to be associated to a task!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	} 
            	else {
            		XMLParser.addAssociationBetweenATaskAndACapability((String) _view.getTasksList().getSelectedValue(), (String) _view.getCapabilitiesList().getSelectedValue());
            		
            		_view.getCapabilitiesPerspective().loadExistingRequires();
            		
            		_view.dispose();
            	}
           
            	/*_view.getServicePerspective().loadExistingProviders();*/
				
            		
            	}
            	
            
        });
		
	}

}
