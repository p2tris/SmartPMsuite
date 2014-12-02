package com.mxgraph.smartml.control;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.mxgraph.smartml.model.XMLParser;
import com.mxgraph.smartml.view.AddDataType;
import com.mxgraph.smartml.view.DataType;
import com.mxgraph.smartml.view.EditService;
import com.mxgraph.smartml.view.PluginsManager;

public class H_DataType {
	
	public DataType _view = null;
	
	
	public H_DataType (DataType i_view){
		
		_view = i_view;
		installListeners();
	}


	public void installListeners() {
		_view.getOKButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("OK")){
					XMLParser.mf.updateInformationView();
					_view.dispose();
				}
				
			}
		});
			
		//Updating the bounds of the Integer_type
		_view.getUpdateIntegerTypeButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
		
            	String min_int_string = _view.getMin_integertype_textField().getText();
            	String max_int_string = _view.getMax_integertype_textField().getText();
            	
            	if(min_int_string.equalsIgnoreCase("") || 
            			min_int_string.equalsIgnoreCase(" ") ||	min_int_string.contains(" ")) {            		
            			JOptionPane.showMessageDialog(null, "The lower bound of an Integer_type can not be empty and can not contain blank spaces!", "ATTENTION!", JOptionPane.ERROR_MESSAGE);
            		
            	}
            	else if(max_int_string.equalsIgnoreCase("") || 
            			max_int_string.equalsIgnoreCase(" ") ||	max_int_string.contains(" ")) {            		
            			JOptionPane.showMessageDialog(null, "The upper bound of an Integer_type can not be empty and can not contain blank spaces!", "ATTENTION!", JOptionPane.ERROR_MESSAGE);
            		
            	}
            	else {
            		
	            	int min_int;
	            	int max_int;
	            	
	            	try {
	            		
	            		min_int = Integer.parseInt(min_int_string);
	            		max_int = Integer.parseInt(max_int_string);
	            		
	            		if(min_int >= 0 && max_int >= min_int) {
	            			Vector boundsInt = new Vector();
		            		boundsInt.addElement(min_int_string);
		            		boundsInt.addElement(max_int_string);
		            		XMLParser.setIntegerBounds(boundsInt);
							XMLParser.mf.updateInformationView();
		            		JOptionPane.showMessageDialog(_view, "The bounds of the Integer_type have been correctly updated!", "Info!", JOptionPane.INFORMATION_MESSAGE);
	    				}
	            		else {
	            			JOptionPane.showMessageDialog(_view, "The lower bounds of the Integer_type can not be greater than the upper bound!", "ERROR!", JOptionPane.ERROR_MESSAGE);
	            		}
	            		            		
	            	}
	            	catch(Exception e) {
	            		JOptionPane.showMessageDialog(_view, "The bounds of the Integer_type are not valid integers!", "ERROR!", JOptionPane.ERROR_MESSAGE);
	            	}
            	}
			}
		});		
		
		_view.getAddDataTypeButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
               	new AddDataType(_view);    	
            }
        });
		
		_view.getDeleteDataTypeButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
               	
            	int selected_index = _view.getDataTypesList().getSelectedIndex();
            	
            	if (selected_index == -1) { //no selection
            		JOptionPane.showMessageDialog(null, "Please select a data type to remove!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
            		  } 
            	else {           
                    
            	   	String data_type_string = (String) _view.getDataTypesListModel().getElementAt(selected_index);
            	   	
            	   	String[] split = data_type_string.split(" = <");
            	   	data_type_string = split[0];
            	   	
            	   	XMLParser.removeUserDataType(data_type_string);
            	   	_view.getDataTypesListModel().remove(selected_index);
            	
            	 }
            	
            }
        });
		
		_view.getPluginButton().addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
            	
              if(_view.getPluginsComboBox().getSelectedIndex() == 0) { //no selection
            	JOptionPane.showMessageDialog(null, "Please select a valid plugin!", "ATTENTION!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/info_icon.png"));
      		  }
              else { 
            	final JFrame content = new JFrame();
            	content.setLayout(new FlowLayout());
        		
            	final String plugin_name = (String) _view.getPluginsComboBox().getSelectedItem();
            	
            	content.setTitle("Import data from the " + plugin_name);
        		
            	Vector plugin_details_vector = XMLParser.getPluginDetails(plugin_name);
            	
            	String plugin_URL = (String) plugin_details_vector.elementAt(1);
            	
        		JPanel northPanel = new JPanel();
        		northPanel.setPreferredSize(new Dimension(330,120));
        		northPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        		northPanel.setLayout(new FlowLayout());
        		
        		JLabel pluginNameLabel = new JLabel("Plugin URL :");
        		pluginNameLabel.setPreferredSize(new Dimension(100,40));
        		final JTextField pluginNameTextField = new JTextField();
        		pluginNameTextField.setEditable(false);
        		pluginNameTextField.setPreferredSize(new Dimension(180,25));
        		pluginNameTextField.setText(plugin_URL);
        		pluginNameTextField.setCaretPosition(0);
        		
        		JLabel XMLNameLabel = new JLabel("URL of XML :");
        		XMLNameLabel.setPreferredSize(new Dimension(100,40));
        		final JTextField XMLNameTextField = new JTextField();
        		XMLNameTextField.setPreferredSize(new Dimension(180,25));
        		
        		northPanel.add(pluginNameLabel);
        		northPanel.add(pluginNameTextField);
           		northPanel.add(XMLNameLabel);
        		northPanel.add(XMLNameTextField);
        		//northPanel.add(blankLabel_1);

        		JButton okButton = new JButton("Invoke");
        		
        		okButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent ae)
                    {
                    	try {
							openWebpage(new URL(pluginNameTextField.getText()));
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                    
                });
        		        		
        		JButton parseButton = new JButton("Parse");
        		
        		parseButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent ae)
                    {

                    	 try {
							
                    		 String string_of_generated_xml = get(XMLNameTextField.getText());
                    		 StringBuffer buffer = new StringBuffer(string_of_generated_xml);
                    		 
                    		 XMLParser.scriviFile("properties/" + plugin_name + ".xml", buffer);
                    		 
                    		 String name_of_the_new_data_type = XMLParser.readTheNameofTheDataTypeFromThePluginFile("properties/" + plugin_name + ".xml");
                    		 String new_data_type = XMLParser.parseDataTypeFromPluginFileAsAString("properties/" + plugin_name + ".xml");
                    		 Vector new_data_type_vector = XMLParser.parseDataTypeFromPluginFileAsAVector("properties/" + plugin_name + ".xml");

                    		 if(isContainedIgnoreCase(name_of_the_new_data_type,XMLParser.getUserDataTypeNames())) {
                    			 
         	            	   int reply = JOptionPane.showConfirmDialog(null, "A data type named '" + name_of_the_new_data_type +"' already exists.\nDo you want to substitute it?", "ATTENTION!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon("images/question_icon.png"));
     						   
     						   if(reply==0) {						       							   
     							  XMLParser.removeUserDataType(name_of_the_new_data_type); 
     							  
     							  for(int indx=0;indx<_view.getDataTypesListModel().size();indx++) {
     								  String s = (String) _view.getDataTypesListModel().elementAt(indx);
     								  String[] split = s.split(" = <");
     								  if(split[0].equalsIgnoreCase(name_of_the_new_data_type)){
     									 _view.getDataTypesListModel().removeElementAt(indx);
     									 break;
     								  }
     							  }

     							  XMLParser.addUserDataType(new_data_type_vector,XMLNameTextField.getText());
     							 _view.getDataTypesListModel().addElement(new_data_type);
     						   }                    			 
                    		 }                    		 
                    		   else{
                    		      _view.getDataTypesListModel().addElement(new_data_type);
                    		      XMLParser.addUserDataType(new_data_type_vector,XMLNameTextField.getText());
                    		   }

                    		 
							content.dispose();
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    	
                    	
                    }
                });
        		
        		JButton cancelButton = new JButton("Cancel");
        		
        		cancelButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent ae)
                    {
                    	content.dispose();
                    }
                });
        		
        		okButton.setPreferredSize(new Dimension(70,25));
        		parseButton.setPreferredSize(new Dimension(70,25));
        		cancelButton.setPreferredSize(new Dimension(70,25));
        		
        		JPanel southPanel = new JPanel();
        		southPanel.setPreferredSize(new Dimension(340,90));
        		southPanel.setLayout(new FlowLayout());
        		southPanel.add(okButton);
        		southPanel.add(parseButton);
        		southPanel.add(cancelButton);	
        			
        		content.add(northPanel);
        		content.add(southPanel);		
        		
        		content.setSize(350,210);
        		content.setResizable(false);
        		content.setVisible(true);
        		
            	}
            }
        });
		
	}
	
	public static void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	public static void openWebpage(URL url) {
	    try {
	        openWebpage(url.toURI());
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
	}
	
	public static String get(String url) throws Exception {
		   StringBuilder sb = new StringBuilder();
		   for(Scanner sc = new Scanner(new URL(url).openStream()); sc.hasNext(); )
		      sb.append(sc.nextLine()).append('\n');
		   return sb.toString();
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
