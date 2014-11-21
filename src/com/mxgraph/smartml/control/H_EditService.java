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
import com.mxgraph.smartml.view.EditService;
import com.mxgraph.smartml.view.ServicePerspective;

public class H_EditService {
	
	public EditService _view = null;
	
	public H_EditService (EditService i_view){
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
            	else if (_view.getServiceNameTextField().getText().equals(_view.getOriginalServiceName())) {
            		
            		JOptionPane.showMessageDialog(null, "No change has been done to the service "+ _view.getOriginalServiceName(), "INFORMATION", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            		_view.dispose();
            	}
            	
            	else if (XMLParser.getServices(true).contains(_view.getServiceNameTextField().getText().toLowerCase())) {
            		
            		JOptionPane.showMessageDialog(null, "The service '" + _view.getServiceNameTextField().getText().toLowerCase() + "' already exists. Please choose a different name for the service!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	}
            	
            	else {
            	
            	int reply = JOptionPane.showConfirmDialog(null, "Do you want to change the service '" + _view.getOriginalServiceName() + "' with\n '" + _view.getServiceNameTextField().getText() + "'?", "ATTENTION!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/question_icon.png"));
					   
				if(reply==0) {	
            		
            	XMLParser.removeService(_view.getOriginalServiceName());
            	XMLParser.addService(_view.getServiceNameTextField().getText());
            	XMLParser.substituteAnOldServiceWithANewServiceFromAnyProvider(_view.getOriginalServiceName(),_view.getServiceNameTextField().getText());
            	
            	_view.getServicePerspective().loadExistingServices();
            	_view.getServicePerspective().loadExistingProviders();	
  				_view.dispose();
            		
            	}
            	}
            }
        });
		
	}

}
