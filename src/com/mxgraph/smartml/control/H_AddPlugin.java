package com.mxgraph.smartml.control;

import java.awt.PageAttributes.OriginType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.AddPlugin;
import com.mxgraph.smartml.view.AddProvider;
import com.mxgraph.smartml.view.AddService;
import com.mxgraph.smartml.view.EditProvider;
import com.mxgraph.smartml.view.ServicePerspective;

public class H_AddPlugin {
	
	public AddPlugin _view = null;
	
	public H_AddPlugin (AddPlugin i_view){
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
            	
     	
            	if(_view.getPluginNameTextField().getText().equalsIgnoreCase("") || 
            			_view.getPluginNameTextField().getText().equalsIgnoreCase(" ") ||	_view.getPluginNameTextField().getText().contains(" ")) {
            		
            			JOptionPane.showMessageDialog(null, "The name of the plugin can not be empty and can not contain blank spaces!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            		
            	}
            	else if(_view.getPluginURLNameTextField().getText().equalsIgnoreCase("") || 
            			_view.getPluginURLNameTextField().getText().equalsIgnoreCase(" ") || _view.getPluginURLNameTextField().getText().contains(" ")) {
            		
            			JOptionPane.showMessageDialog(null, "The URL of the plugin can not be empty and can not contain blank spaces!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            		
            	}
            	/*
            	else if (XMLParser.getServices(true).contains(_view.getPluginNameTextField().getText().toLowerCase())) {
            		
            		JOptionPane.showMessageDialog(null, "The service '" + _view.getPluginNameTextField().getText().toLowerCase() + "' already exists. Please choose a different name for the service!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	}
            	*/
            	else {
            	
            	XMLParser.addPlugin(_view.getPluginNameTextField().getText(),_view.getPluginURLNameTextField().getText(),_view.getPluginDescriptionArea().getText());
            	_view.getPluginManager().loadExistingPlugins();
  				_view.dispose();
            		
            	}
            	
            }
        });
		
	}

}
