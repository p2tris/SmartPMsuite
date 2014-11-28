package com.mxgraph.smartml.control;

import java.awt.PageAttributes.OriginType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.AddDataType;
import com.mxgraph.smartml.view.AddProvider;
import com.mxgraph.smartml.view.EditProvider;
import com.mxgraph.smartml.view.ServicePerspective;

public class H_AddDataType {
	
	public AddDataType _view = null;
	
	public H_AddDataType (AddDataType i_view){
		_view = i_view;
		installListeners();
	}


	private void installListeners() {
		
		_view.getRightButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	
            	String data_object_name =  _view.getDataObjectTextField().getText();
           	
            	if(data_object_name.equalsIgnoreCase("") || 
            			data_object_name.equalsIgnoreCase(" ") || data_object_name.contains(" ")) {            		
            			JOptionPane.showMessageDialog(null, "A data object can not be empty and can not contain blank spaces!", "ATTENTION!", JOptionPane.ERROR_MESSAGE);
            	}
            	else {
            		
            		boolean obj_already_exists = false;
            		
            		for(int i=0; i<_view.getDataTypeListModel().size();i++) {
            			String elem_i = (String) _view.getDataTypeListModel().elementAt(i);
            			if(elem_i.equalsIgnoreCase(data_object_name)) {
            				obj_already_exists = true;
            			    break;
            			}	
            		}
                    
            		if(obj_already_exists) {
               			JOptionPane.showMessageDialog(null, "The data object '" + data_object_name + "' is already included in the data type's domain!", "ATTENTION!", JOptionPane.ERROR_MESSAGE);
            		}
            		else if (data_object_name.equalsIgnoreCase("true") || data_object_name.equalsIgnoreCase("false")) {
            			JOptionPane.showMessageDialog(null, "The data object '" + data_object_name + "' already exists as an object of the predefined type 'Boolean_type'!", "ATTENTION!", JOptionPane.ERROR_MESSAGE);
            		}
            		else if(isInteger(data_object_name)) {
        				JOptionPane.showMessageDialog(null, "Integer values are not allowed to be used as data objects!", "ATTENTION!", JOptionPane.ERROR_MESSAGE); 
            		}            			
            		else {
            			            			
               	   		_view.getDataTypeListModel().addElement(data_object_name);
               	   		_view.getDataObjectTextField().setText("");
            		}
            	 }

            }
        });
		
		_view.getLeftButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	
            	int selected_index = _view.getDataTypeList().getSelectedIndex();
            	
            	if (selected_index == -1) { //no selection
            		JOptionPane.showMessageDialog(null, "Please select a data object to remove!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	} 
            	else {
            	   	_view.getDataTypeListModel().removeElementAt(selected_index);
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
            	String data_type_name = _view.getDataTypeNameTextField().getText();
            	
            	if(data_type_name.equalsIgnoreCase("") || data_type_name.equalsIgnoreCase(" ") || data_type_name.contains(" ")) {
            			JOptionPane.showMessageDialog(null, "The name of the data type can not be empty and can not contain blank spaces!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	}
            	else if (data_type_name.equalsIgnoreCase("Integer_type") || data_type_name.equalsIgnoreCase("Boolean_type") || isContainedIgnoreCase(_view.getDataTypeNameTextField().getText(),XMLParser.getUserDataTypeNames())) {
            			JOptionPane.showMessageDialog(null, "The data type '" + _view.getDataTypeNameTextField().getText() + "' already exists. Please choose a different name for the data type!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            	}
            	else if (isInteger(data_type_name)) {
        				JOptionPane.showMessageDialog(null, "The name of a data type can not be a number. Please choose a different name!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));            		
            	}
            	else if(data_type_name.length() < 5 || (data_type_name.length() >= 5 && !data_type_name.substring(data_type_name.length()-5, data_type_name.length()).equalsIgnoreCase("_type"))) {
        				JOptionPane.showMessageDialog(null, "The name of a data type must contain at the end the substring '_type' !", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));            		
            	}
            	else if(data_type_name.equalsIgnoreCase("_type")) {
        				JOptionPane.showMessageDialog(null, "Please select a valid name for the data type!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));            		
            	}
            	
            	else {
            		
            		Vector newDataType_vector = new Vector();
            		
            		String newDataType = data_type_name + " = <";
            		newDataType_vector.addElement(data_type_name);
            	
            		for(int i=0;i<_view.getDataTypeListModel().size();i++) {            			
            			
            			newDataType = newDataType + _view.getDataTypeListModel().getElementAt(i);    	
            			
            			if(i<_view.getDataTypeListModel().size()-1)
            				newDataType = newDataType + ",";
            			
            			newDataType_vector.addElement(_view.getDataTypeListModel().getElementAt(i));   
            			
                	}
            		
            		newDataType = newDataType + ">";
            		XMLParser.addUserDataType(newDataType_vector,null);
            		_view.getDataTypeView().getDataTypesListModel().addElement(newDataType);
            		_view.dispose();
            	}
            	
            }
        });
			
	}
	
	private static boolean isInteger(String s) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),10) < 0) return false;
	    }
	    return true;
	}
	
	private static boolean isContainedIgnoreCase(String s, Vector v) {
	    if(v.isEmpty()) return false;
	    
	    for(int i=0;i<v.size();i++) {
	    	String obj_i = (String) v.elementAt(i);
	    	if(obj_i.equalsIgnoreCase(s))
	    		return true;
	    }
	    return false;
	}

}
