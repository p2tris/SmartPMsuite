package com.mxgraph.smartml.view;

import java.util.Vector;  

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JDialog;
import javax.swing.SpringLayout;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;

import com.mxgraph.smartml.control.H_ResourcePerspective;



public class ResourcePerspective extends JDialog 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public H_ResourcePerspective _handler = null;  
	
	public JPanel contentPane;
	public JPanel panel;
	public JPanel panel_1;
	public JLabel lblCapabilities;
	public JButton btnEditCapability;
	public JScrollPane cap_scrollPane;
	public JTextArea cap_textArea;
	public JLabel lblParticipants;
	public JButton btnEditParticipants;
	public JScrollPane part_scrollPane;
	public JTextArea part_textArea;
	public JPanel panel_2;
	public JScrollPane exRoles_scrollPane;
	public JTextArea roles_textArea;
	public JLabel lblRoles;
	public JButton btnUpdate;
	public JButton btnCancel;
	
	
	public ResourcePerspective() {
		
		super();
		initComponent();
		initHandler();
	
	}

	private void initHandler() {
		_handler = new H_ResourcePerspective(this);
	}
	
	
	private void initComponent() {
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);  
	
		
		panel = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel, -386, SpringLayout.SOUTH, contentPane);
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		sl_contentPane.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel, 414, SpringLayout.WEST, contentPane);
		contentPane.add(panel);
		
		panel_1 = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel_1, 37, SpringLayout.SOUTH, panel);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel_1, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel_1, -243, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel_1, -10, SpringLayout.EAST, contentPane);
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel_2, 20, SpringLayout.SOUTH, panel_1);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel_2, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel_2, 153, SpringLayout.SOUTH, panel_1);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel_2, 414, SpringLayout.WEST, contentPane);
		contentPane.add(panel_2);
		SpringLayout sl_panel_2 = new SpringLayout();
		panel_2.setLayout(sl_panel_2);
		
		lblCapabilities = new JLabel("Capabilities: ");
		sl_panel.putConstraint(SpringLayout.NORTH, lblCapabilities, 22, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, lblCapabilities, 22, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, lblCapabilities, 42, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, lblCapabilities, 87, SpringLayout.WEST, panel);
		panel.add(lblCapabilities);
		
		cap_scrollPane = new JScrollPane();
		sl_panel.putConstraint(SpringLayout.NORTH, cap_scrollPane, 10, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, cap_scrollPane, -45, SpringLayout.SOUTH, panel);
		lblCapabilities.setLabelFor(cap_scrollPane);
		sl_panel.putConstraint(SpringLayout.WEST, cap_scrollPane, 6, SpringLayout.EAST, lblCapabilities);
		sl_panel.putConstraint(SpringLayout.EAST, cap_scrollPane, 226, SpringLayout.EAST, lblCapabilities);
		cap_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		cap_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel.add(cap_scrollPane);
		
		cap_textArea = new JTextArea();
		cap_scrollPane.setViewportView(cap_textArea);
		contentPane.add(panel_1);
		SpringLayout sl_panel_1 = new SpringLayout();
		panel_1.setLayout(sl_panel_1);
		
		lblParticipants = new JLabel("Services: ");
		sl_panel_1.putConstraint(SpringLayout.NORTH, lblParticipants, 22, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.WEST, lblParticipants, 22, SpringLayout.WEST, panel_1);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, lblParticipants, 42, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.EAST, lblParticipants, -302, SpringLayout.EAST, panel_1);
		panel_1.add(lblParticipants);
		
		part_scrollPane = new JScrollPane();
		lblParticipants.setLabelFor(part_scrollPane);
		sl_panel_1.putConstraint(SpringLayout.SOUTH, part_scrollPane, 58, SpringLayout.NORTH, panel_1);
		part_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		part_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		sl_panel_1.putConstraint(SpringLayout.NORTH, part_scrollPane, 10, SpringLayout.NORTH, panel_1);
		sl_panel_1.putConstraint(SpringLayout.WEST, part_scrollPane, 3, SpringLayout.EAST, lblParticipants);
		sl_panel_1.putConstraint(SpringLayout.EAST, part_scrollPane, 225, SpringLayout.EAST, lblParticipants);
		panel_1.add(part_scrollPane);
		
		part_textArea = new JTextArea();
		part_scrollPane.setViewportView(part_textArea);
		
		lblRoles = new JLabel("Providers: ");
		sl_panel_2.putConstraint(SpringLayout.NORTH, lblRoles, 40, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.WEST, lblRoles, 35, SpringLayout.WEST, panel_2);
		panel_2.add(lblRoles);
		
		exRoles_scrollPane = new JScrollPane();
		lblRoles.setLabelFor(exRoles_scrollPane);
		exRoles_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		exRoles_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sl_panel_2.putConstraint(SpringLayout.NORTH, exRoles_scrollPane, 10, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.WEST, exRoles_scrollPane, 26, SpringLayout.EAST, lblRoles);
		sl_panel_2.putConstraint(SpringLayout.SOUTH, exRoles_scrollPane, 110, SpringLayout.NORTH, panel_2);
		sl_panel_2.putConstraint(SpringLayout.EAST, exRoles_scrollPane, 297, SpringLayout.EAST, lblRoles);
		panel_2.add(exRoles_scrollPane);
		
		roles_textArea = new JTextArea();
		exRoles_scrollPane.setViewportView(roles_textArea);
		
		btnUpdate = new JButton("UPDATE");
		sl_contentPane.putConstraint(SpringLayout.WEST, btnUpdate, 76, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnUpdate, -25, SpringLayout.SOUTH, contentPane);
		contentPane.add(btnUpdate);
		
		btnCancel = new JButton("CANCEL");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCancel, 0, SpringLayout.NORTH, btnUpdate);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnCancel, -67, SpringLayout.EAST, contentPane);
		contentPane.add(btnCancel);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(450,550);
		setTitle("RESOURCE PERSPECTIVE");
		setLocationRelativeTo(null);
		setResizable(true);
		//this.setModal(true);
		
	}
	
	// metodo ausiliario per mostrare i participants nella text_area dei participant
	public void mostraParticipants(Vector<String> v){
		String res ="";
		if(v.isEmpty()){
			res="<empty>";
		} 
		for(int i=0; i<v.size();i++){
			if(i==v.size()-1)
				res =res + v.get(i);
			else
				res =res + v.get(i) + ";";
		}
		part_textArea.setText(res);	
	}
	
	// metodo ausiliario per mostrare le capabilities nella text_area opportuna
	public void mostraCapabilities(Vector<String> v){
		String cap_res = "";
		if(v.isEmpty())
			cap_res = "<empty>";
		for(int i=0; i<v.size();i++){
		if(i==v.size()-1)	
			cap_res =cap_res + v.get(i);
		else
			cap_res = cap_res + v.get(i)+";";
		}
		cap_textArea.setText(cap_res);
	}
	
	// metodo ausiliario per mostrare i ruoli nella text_area opportuna
	public void mostraRoles (Vector<Vector<String>> res){ 
		
		String app1 ="";
		String app2 ="";
		String app3 ="";
		if(res.isEmpty())
			app1 = "<empty>"; 
		for(int i=0; i<res.size();i++){
			app1=app1+res.get(i).get(0)+"= <";
			for(int in=1;in<res.get(i).size();in++){
				if(in==res.get(i).size()-1)
					app2=app2+res.get(i).get(in);
				else
					app2=app2+res.get(i).get(in)+",";
					//System.out.println("STAMPA****** "+res.get(i).size());
			}
			app3=">\n";
			app1=app1+app2+app3;
			app2="";	
		}
		
		roles_textArea.setText(app1);
		
	}
	
	/** Metodo ausiliario per leggere ciò che è scritto nella textArea dei ruoli*/
	public Vector<Vector<String>> leggiRolesArea(){  
		Vector<Vector<String>> v_textArea = new Vector<Vector<String>>();
		Vector<String> role = null;
		
		String s_textArea = roles_textArea.getText();
		if(s_textArea.equals("<empty>")){
			System.out.println(v_textArea);
			
		}
		else{ // IL CAMPO NON E' VUOTO
			String[] tokens = s_textArea.split("\\s*\n");
			//Ogni elemento dell'array "tokens" è una riga di tipo "role_type= <r1,r2,r3,...,rn>" 		
			for(int ind=0;ind<tokens.length;ind++){
				
				String[] tokens_2 = tokens[ind].split("\\s*= <");
				//L'array "tokens_2" ha due elementi:
				//l'elemento tokens_2[0] è il nome del ruolo
				//l'elemento tokens_2[1] è una riga del tipo "r1,r2,r3,...,rn>" 
				role = new Vector<String>(); // creo un vector di stringhe
				role.add(tokens_2[0]);
				
				//System.out.println("ECCO_PROVA "+tokens_2[0]);				
				String[] tokens_3 = tokens_2[1].split("\\s*>");
				//L'array "tokens_3" ha un elemento: 
				//l'elemento tokens_3[0] è una riga del tipo "r1,r2,r3,...,rn" 

				String[] tokens_4 = tokens_3[0].split("\\s*,");
				//Ogni elemento dell'array "tokens_4" è un singolo ruolo di tipo "role_type".
					
				for(int x=0;x<tokens_4.length;x++){
					//System.out.println("ECCO2 "+tokens_4[x]);
					
					role.add(tokens_4[x]);
						
				} 
				// role è, ogni volta, un vettore del tipo "[role_type, r1, r2, ..., rn]"
				//System.out.println("PROVA VETTORE: "+ role.toString());
				v_textArea.add(role);
				
		
			}
			
		}
		//System.out.println("PROVA VETTORE_2: "+ v_textArea.toString());
		return v_textArea;
			
	}
	
	public Vector<String> leggiCapabilitiesArea(){   
		Vector<String> c_textArea = new Vector<String>();
		
		String usr = cap_textArea.getText();
		if(usr.equals("<empty>")){
			System.out.println(usr);
			
		}
		else{  // IL CAMPO NON E' VUOTO
			String[] tokens = usr.split("\\s*;");
			//Ogni elemento dell'array "tokens" è una singola capability 
			for(int x=0;x<tokens.length;x++){
				
				c_textArea.add(tokens[x]);
				
			}
			
		}
		return c_textArea;
		
	}
	
	public Vector<String> leggiParticipantsArea(){
		Vector<String> p_textArea = new Vector<String>();
		
		String usr = part_textArea.getText();
		
		if(usr.equals("<empty>")){
			System.out.println(usr);
		}
		else{ // IL CAMPO NON E' VUOTO
			String[] tokens = usr.split("\\s*;");
			//Ogni elemento dell'array "tokens" è un singolo participant
			for(int k=0;k<tokens.length;k++){
				
				p_textArea.add(tokens[k]);
				
			}
			
		}
		return p_textArea;
	
	}
	
	
	
	
}
