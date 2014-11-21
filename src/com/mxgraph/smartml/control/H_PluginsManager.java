package com.mxgraph.smartml.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.AddPlugin;
import com.mxgraph.smartml.view.AddProvider;
import com.mxgraph.smartml.view.AddService;
import com.mxgraph.smartml.view.EditProvider;
import com.mxgraph.smartml.view.EditService;
import com.mxgraph.smartml.view.PluginsManager;
import com.mxgraph.smartml.view.ServicePerspective;

public class H_PluginsManager {
	
	public PluginsManager _view = null;
	
	
	public H_PluginsManager (PluginsManager i_view){
		_view = i_view;
		installListeners();
	}


	private void installListeners() {
		
	
		_view.getAddPluginButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	new AddPlugin(_view);
            }
        });
		
		 _view.getDeletePluginButton().addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent ae)
	            {
	            
	            	int selected_index = _view.getPluginList().getSelectedIndex();
	            	
	            	if (selected_index == -1) { //no selection
	            		JOptionPane.showMessageDialog(null, "Please select a plugin to delete!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
	            	} 
	            	else { 
	                    
	            	   	String selected_plugin = (String) _view.getPluginsListModel().getElementAt(selected_index);

	            	    int reply = JOptionPane.showConfirmDialog(null, "Do you want to delete the plugin '" + selected_plugin + "'?", "ATTENTION!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/question_icon.png"));
						   
						   if(reply==0) {						   
							   XMLParser.removePlugin(selected_plugin); 
							   _view.loadExistingPlugins();
						   }

	            	 }
	            	
	            }
	        });
		 
		 _view.getPluginList().addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent e) {
		    	  
		    	  if(e.getValueIsAdjusting()) { // -- Used to avoid that the ListSelectionListener is invoked twice.
		    	  
		    	  String plugin_selected = (String) _view.getPluginList().getSelectedValue();
		    	  
		    	  Vector plugin_details = XMLParser.getPluginDetails(plugin_selected);
		    	  
		    	  
		    	  _view.getPluginList().setToolTipText("<html>NAME: " + plugin_selected + "<br/>URL: " + plugin_details.elementAt(1) + "<br/>DESCRIPTION: " + plugin_details.elementAt(2) + "</html>");
		    	  
		    	  }
	            }
	        });
		 
		_view.getOkButton().addActionListener(new ActionListener()
	        {
	            public void actionPerformed(ActionEvent ae)
	            {
	            	_view.dispose();
	            	
	            }
	        });
		
	}

}
