package com.mxgraph.smartml.control;

import java.awt.PageAttributes.OriginType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.AddProvider;
import com.mxgraph.smartml.view.AddService;
import com.mxgraph.smartml.view.EditProvider;
import com.mxgraph.smartml.view.ServicePerspective;

public class H_AddService {
	
	public AddService _view = null;
	
	public H_AddService (AddService i_view){
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
            	
     	
            	if(_view.getServiceNameTextField().getText().equalsIgnoreCase("") || 
            			_view.getServiceNameTextField().getText().equalsIgnoreCase(" ") ||	_view.getServiceNameTextField().getText().contains(" ")) {
            		
            			JOptionPane.showMessageDialog(null, "The name of the service can not be empty and can not contain blank spaces!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            		
            	}
            	else if (XMLParser.getServices(true).contains(_view.getServiceNameTextField().getText().toLowerCase())) {
            		
            		JOptionPane.showMessageDialog(null, "The service '" + _view.getServiceNameTextField().getText().toLowerCase() + "' already exists. Please choose a different name for the service!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	}
            	
            	else {
            	
            	XMLParser.addService(_view.getServiceNameTextField().getText());
            	_view.getServicePerspective().loadExistingServices();
  				_view.dispose();
            		
            	}
            	
            }
        });
		
	}

}
