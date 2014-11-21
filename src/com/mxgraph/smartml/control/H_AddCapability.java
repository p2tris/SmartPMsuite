package com.mxgraph.smartml.control;

import java.awt.PageAttributes.OriginType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.AddCapability;
import com.mxgraph.smartml.view.AddProvider;
import com.mxgraph.smartml.view.AddService;
import com.mxgraph.smartml.view.EditProvider;
import com.mxgraph.smartml.view.ServicePerspective;

public class H_AddCapability {
	
	public AddCapability _view = null;
	
	public H_AddCapability (AddCapability i_view){
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
		
		_view.getOkButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	
     	
            	if(_view.getCapabilityNameTextField().getText().equalsIgnoreCase("") || 
            			_view.getCapabilityNameTextField().getText().equalsIgnoreCase(" ") ||	_view.getCapabilityNameTextField().getText().contains(" ")) {
            		
            			JOptionPane.showMessageDialog(null, "The name of the capability can not be empty and can not contain blank spaces!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            		
            	}
            	else if (XMLParser.getCapabilities(true).contains(_view.getCapabilityNameTextField().getText().toLowerCase())) {
            		
            		JOptionPane.showMessageDialog(null, "The capability '" + _view.getCapabilityNameTextField().getText().toLowerCase() + "' already exists. Please choose a different name for the capability!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	}
            	
            	else {
            	
            	XMLParser.addCapability(_view.getCapabilityNameTextField().getText());
            	_view.getCapabilitiesPerspective().loadExistingCapabilities();
  				_view.dispose();
            		
            	}
            	
            }
        });
		
	}

}
