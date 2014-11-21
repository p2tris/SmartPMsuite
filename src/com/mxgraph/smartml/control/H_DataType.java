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
import com.mxgraph.smartml.view.DataType;
import com.mxgraph.smartml.view.PluginsManager;

public class H_DataType {
	
	public DataType _view = null;
	
	
	public H_DataType (DataType i_view){
		
		_view = i_view;
		installListeners();
	}


	public void installListeners() {
		_view.btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("UPDATE")){
					Vector<String> boundsInt = _view.leggiIntegerBounds();
					// Primo elemento: limite inferiore; Secondo elemento: limite superiore
					if((Integer.parseInt(boundsInt.get(0)) >= 0 ) && (Integer.parseInt(boundsInt.get(1)) >= Integer.parseInt(boundsInt.get(0)))){
						XMLParser.setIntegerBounds(boundsInt); // PROVA
					}
					else{
						JOptionPane.showMessageDialog(_view, "The bounds for Integer_type are not valids", "ERROR!", JOptionPane.ERROR_MESSAGE);;
					}
					
					Vector<Vector<String>> user_dtype_vector = _view.leggiDataTypesArea();
					XMLParser.setUserDataTypes(user_dtype_vector);
					
					XMLParser.mf.updateInformationView();
					_view.dispose();
				}
				
			}
		});
		
		_view.btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equalsIgnoreCase("CANCEL")){
					
					XMLParser.mf.updateInformationView();
					_view.dispose();
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
                    		 
                    		 String new_data_type = XMLParser.readPluginFile("properties/" + plugin_name + ".xml");
                    		 
         					if(_view.getUserTextArea().getText().contains("<empty>"))
         						_view.getUserTextArea().setText(new_data_type);
         					else	
         						_view.getUserTextArea().setText(_view.getUserTextArea().getText() + "\n" + new_data_type);
                    		 
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

}
