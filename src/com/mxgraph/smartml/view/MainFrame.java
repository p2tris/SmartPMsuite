package com.mxgraph.smartml.view;


import java.awt.*;    
import java.util.Vector;

import javax.swing.*;

import com.mxgraph.smartml.control.H_MainFrame;
import com.mxgraph.smartml.model.Constants;
import com.mxgraph.smartml.model.XMLParser;


public class MainFrame extends JDialog
{
	
	private static final long serialVersionUID = 1L;
	
	public H_MainFrame _handler;
	
	public Container content;
	public JMenuBar menubar;
	public JMenu file;
	public JMenu edit;
	public JScrollPane scrollPane;
	public JTextArea recap_all;
	
	
	public MainFrame(){
		super();
		initComponent();
		initHandler();
		XMLParser.mf = this;
		
	}
	
	public void initHandler() {
		_handler = new H_MainFrame(this);
		
	}

	public void initComponent(){
		
		content = getContentPane();
		content.setLayout(new BorderLayout());
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
		
		this.setTitle("Domain Theory Editor");
		
		menubar=new JMenuBar();
		this.setJMenuBar(menubar);
		
		file = new JMenu("File");
		menubar.add(file);
		
		edit = new JMenu("Edit");
		menubar.add(edit);		
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		content.add(scrollPane);
		
		updateInformationView();		
		
		scrollPane.setViewportView(recap_all);
		
		// JMenu non ha bisogno del listener 
		Menu edm = new Menu(file,edit,XMLParser.getTaskNames(false),XMLParser.getExogenousEventsName(),XMLParser.getFormulae(),XMLParser.getAllAtomicTermsName(false)); //     
		edm.setVisible(true);

		this.setLocationRelativeTo(null);	
		this.setSize(400,600);
		this.setResizable(true);
		this.setModal(false);
		
	}

	public void updateInformationView() {
		
		recap_all = new JTextArea();
		recap_all.setEditable(false);
		
		Vector vector_participants = XMLParser.getServices(false);
		
		String res ="SERVICES: \n";
		if(vector_participants.isEmpty()){
			res="<NO SERVICES>\n\n";
		} 
		for(int i=0; i<vector_participants.size();i++){
			if(i==0)
				res = res+"<";
			
			if(i==vector_participants.size()-1)
				res = res + vector_participants.get(i)+">\n\n";
			else{
				res = res + vector_participants.get(i) + ",";
		}
		}
		recap_all.append(res);	
			
		Vector vector_capabilities = (XMLParser.getCapabilities(false)); 
		
		String cap_res = "CAPABILITIES: \n";
		if(vector_capabilities.isEmpty())
			cap_res = "<NO CAPABILITIES>\n\n";
		for(int i=0; i<vector_capabilities.size();i++){
		if(i==0)	
				cap_res = cap_res+"<";
			
			if(i==vector_capabilities.size()-1)	
			cap_res =cap_res + vector_capabilities.get(i)+">\n\n";
		else{		
			cap_res = cap_res + vector_capabilities.get(i)+",";
		}
		}
		recap_all.append(cap_res);	
		
		Vector <Vector<String>> vector_roles = XMLParser.getProviders(false);
		
		String app1 ="PROVIDERS:\n";
		String app2 ="";
		String app3 ="";
		if(vector_roles.isEmpty())
			app1 = "<NO PROVIDERS>\n"; 
		for(int i=0; i<vector_roles.size();i++){
			app1=app1+vector_roles.get(i).get(0)+" = <";
			for(int in=1;in<vector_roles.get(i).size();in++){
				if(in==vector_roles.get(i).size()-1)
					app2=app2+vector_roles.get(i).get(in);
				else
					app2=app2+vector_roles.get(i).get(in)+",";
			}
			app3=">\n";
			app1=app1+app2+app3;
			app2="";	
		}
		
		recap_all.append(app1);	
		
		Vector integer_vector = XMLParser.getIntegerType();
		
		String int_res = "\nDATA TYPES:\n";
		int_res = int_res+"Integer_type = <" + integer_vector.get(0) + ".." + integer_vector.get(1) + ">\n";
		
		recap_all.append(int_res);

		String bool_res = "";
		bool_res = bool_res+ "Boolean_type = <true,false>\n\n";
		
		recap_all.append(bool_res);
		
		Vector <Vector<String>> user_type_vector = XMLParser.getUserDataTypes(false);
		res = "";
		app2 = "";
		app3 = "";
		
		res = "USER DEFINED DATA TYPES:\n";
		if(user_type_vector.isEmpty()){
			res = "<NO USER DEFINED TYPES>\n\n";
		}
		for(int i=0;i<user_type_vector.size();i++){
			res = res + user_type_vector.get(i).get(0) + " = <";
			for(int j=1;j<user_type_vector.get(i).size();j++){
				if(j==user_type_vector.get(i).size()-1)
					app2 = app2+user_type_vector.get(i).get(j);
				else
					app2 = app2+user_type_vector.get(i).get(j)+",";
				
			}
			
			if(i==user_type_vector.size()-1) {
				app3=">\n\n";	
			}
			else {
				app3=">\n";
			}
			
			res=res+app2+app3;
			app2="";
		}
		
		recap_all.append(res);
		
		recap_all.append(getAtomicTermsforInformationView());
		
		recap_all.append(getTasksforInformationView());
		
		recap_all.append(getExogenousEventsforInformationView());
		
		recap_all.append(getFormulaeforInformationView());
		
		//XMLParser.getInputArgumentsOfTerm("at");
		recap_all.setCaretPosition(0);
		scrollPane.setViewportView(recap_all);
		
	}
	 
	private String getAtomicTermsforInformationView(){
		
		Vector<String> nat = XMLParser.getAllDynamicTermsName();
		

		String res = "ATOMIC TERMS (DYNAMIC):\n";
		if(nat.isEmpty())
			res = "<NO DYNAMIC ATOMIC TERMS>\n\n";
		
		Vector inputParametersVector = new Vector();
		
		for(int index=0;index<nat.size();index++){			
			inputParametersVector = XMLParser.getAtomicTermParametersNameAndType(nat.get(index));
						
			res = res + nat.get(index)+"[";
			
			for(int in=0;in<inputParametersVector.size();in++){
				Vector singleInputParameterVector = (Vector) inputParametersVector.elementAt(in);
				res = res + singleInputParameterVector.get(0)+":"+singleInputParameterVector.get(1);
				if(in<inputParametersVector.size()-1)
					res = res + ",";
				
			}
			res = res + "] = (";
			res = res + XMLParser.getAtomicTermReturnType(nat.get(index),false);
			res = res + ")\n";
			
			if(index==nat.size()-1)
				res = res + "\n";
		}
		
		nat = XMLParser.getAllStaticTermsName();
		
		
		if(nat.isEmpty())
			res = res + "<NO STATIC ATOMIC TERMS>\n\n";
		else
			res = res + "ATOMIC TERMS (STATIC):\n";
		
		for(int index=0;index<nat.size();index++){
			
			
			inputParametersVector = XMLParser.getAtomicTermParametersNameAndType(nat.get(index));
			res = res + nat.get(index)+"[";

			for(int in=0;in<inputParametersVector.size();in++){
				Vector singleInputParameterVector = (Vector) inputParametersVector.elementAt(in);
				res = res + singleInputParameterVector.get(0)+":"+singleInputParameterVector.get(1);
				if(in<inputParametersVector.size()-1)
					res = res + ",";
				
			}
			
			res = res + "] = (";
			res = res + XMLParser.getAtomicTermReturnType(nat.get(index),false);
			res = res + ")\n";
			
			if(index==nat.size()-1)
				res = res + "\n";
		}
		
		return res;
	}
	
	private String getTasksforInformationView(){
		
		String res = "TASKS :\n";
		
		Vector tasks_vector = XMLParser.getTaskNames(false);
		
		if(tasks_vector.size()==0)
			res = "<NO TASKS>\n\n";
		else {
			
			for(int i=0;i<tasks_vector.size();i++) {
				
				String task_name = (String) tasks_vector.elementAt(i);
				
				res = res + task_name + "[";
				
				
				Vector<Vector<String>> task_params_vector = XMLParser.getInputParametersOfTask(task_name,false);
							
				for(int j=0;j<task_params_vector.size();j++) {
					
					Vector argument_vector = task_params_vector.elementAt(j);
					
					if(j==task_params_vector.size()-1)
						res = res + argument_vector.elementAt(0) + ":" + argument_vector.elementAt(1);
					else
						res = res + argument_vector.elementAt(0) + ":" + argument_vector.elementAt(1)+",";
				
				}
				
				res = res + "]\n--Preconditions : {";
				
				Vector vec_preconditions = XMLParser.getTaskPreconditions(task_name);

				String app_pre = null;
				
				if(vec_preconditions.size() == 1){ // ho una sola precondizione
					app_pre = (String) vec_preconditions.firstElement();
					if(app_pre.contains("&gt;")){
						app_pre = app_pre.replace("&gt;", ">");
					}
					if(app_pre.contains("&lt;")){
						app_pre = app_pre.replace("&lt;", "<");
					}
					res = res + app_pre;
				}
				else{ // ho pi� di una precondizione
					for(int j=0;j<vec_preconditions.size();j++){
						app_pre = (String) vec_preconditions.get(j);
						if(app_pre.contains("&gt;")){
							app_pre = app_pre.replace("&gt;", ">");
						}
						if(app_pre.contains("&lt;")){
							app_pre = app_pre.replace("&lt;", "<");
						}
						if(j==vec_preconditions.size()-1)
							res = res + app_pre;
						else
							res = res + app_pre + " AND ";		
					}
				}
				res = res + "}\n";
				
				res = res + "--Supposed Effects : {";
				
				Vector vec_supp_effects = XMLParser.getTaskSupposedEffects(task_name,false);

				String app_supp_eff = null;
				
				if(vec_supp_effects.size() == 1){ // ho una sola precondizione
					app_supp_eff = (String) vec_supp_effects.firstElement();
					if(app_supp_eff.contains("&gt;")){
						app_supp_eff = app_supp_eff.replace("&gt;", ">");
					}
					if(app_supp_eff.contains("&lt;")){
						app_supp_eff = app_supp_eff.replace("&lt;", "<");
					}
					res = res + app_supp_eff;
				}
				else{ // ho pi� di una precondizione
					for(int j=0;j<vec_supp_effects.size();j++){
						app_supp_eff = (String) vec_supp_effects.get(j);
						if(app_supp_eff.contains("&gt;")){
							app_supp_eff = app_supp_eff.replace("&gt;", ">");
						}
						if(app_supp_eff.contains("&lt;")){
							app_supp_eff = app_supp_eff.replace("&lt;", "<");
						}
						if(j==vec_supp_effects.size()-1)
							res = res + app_supp_eff;
						else
							res = res + app_supp_eff + " AND ";		
					}
				}
				res = res + "}\n";
				
				res = res + "--Automatic Effects : {";
				
				Vector vec_auto_effects = XMLParser.getTaskAutomaticEffects(task_name,false);

				String app_auto_eff = null;
				
				if(vec_auto_effects.size() == 1){ // ho una sola precondizione
					app_auto_eff = (String) vec_auto_effects.firstElement();
					if(app_auto_eff.contains("&gt;")){
						app_auto_eff = app_auto_eff.replace("&gt;", ">");
					}
					if(app_auto_eff.contains("&lt;")){
						app_auto_eff = app_auto_eff.replace("&lt;", "<");
					}
					res = res + app_auto_eff;
				}
				else{ // ho pi� di una precondizione
					for(int j=0;j<vec_auto_effects.size();j++){
						app_auto_eff = (String) vec_auto_effects.get(j);
						if(app_auto_eff.contains("&gt;")){
							app_auto_eff = app_auto_eff.replace("&gt;", ">");
						}
						if(app_auto_eff.contains("&lt;")){
							app_auto_eff = app_auto_eff.replace("&lt;", "<");
						}
						if(j==vec_auto_effects.size()-1)
							res = res + app_auto_eff;
						else
							res = res + app_auto_eff + " AND ";		
					}
				}
				res = res + "}\n";
								
				if(i==tasks_vector.size()-1)
					res = res + "\n";
			}
			
		}
		
		return res;
	}
	
	private String getExogenousEventsforInformationView(){
		
		Vector ex_events_vector = XMLParser.getExogenousEventsName();
		
		String res = "EXOGENOUS EVENTS :\n";
		
		if(ex_events_vector.size()==0)
			res = "<NO EXOGENOUS EVENTS>\n\n";
		else {
			
			for(int i=0;i<ex_events_vector.size();i++) {
				
				String ex_event_name = (String) ex_events_vector.elementAt(i);
				
				res = res + ex_event_name + "[";
				
				Vector<Vector<String>> ex_event_params_vector = XMLParser.getInputParametersOfExogenousEvent(ex_event_name);
							
				for(int j=0;j<ex_event_params_vector.size();j++) {
					
					Vector argument_vector = ex_event_params_vector.elementAt(j);
					
					if(j==ex_event_params_vector.size()-1)
						res = res + argument_vector.elementAt(0) + ":" + argument_vector.elementAt(1);
					else
						res = res + argument_vector.elementAt(0) + ":" + argument_vector.elementAt(1)+",";
				
				}
				
				res = res + "]\n--Effects : {";
				
				Vector vec_effects = XMLParser.getExogenousEventEffects(ex_event_name);

				String effects_area = "";
				String app_eff = null;
				
				if(vec_effects.size() == 1){ // ho un solo effetto
					app_eff = (String) vec_effects.firstElement();
					if(app_eff.contains("&gt;")){
						app_eff = app_eff.replace("&gt;", ">");
					}
					if(app_eff.contains("&lt;")){
						app_eff = app_eff.replace("&lt;", "<");
					}
					res = res + app_eff;
				}
				else{ // ho pi� di un effetto
					for(int j=0;j<vec_effects.size();j++){
						app_eff = (String) vec_effects.get(j);
						if(app_eff.contains("&gt;")){
							app_eff = app_eff.replace("&gt;", ">");
						}
						if(app_eff.contains("&lt;")){
							app_eff = app_eff.replace("&lt;", "<");
						}
						if(j==vec_effects.size()-1)
							res = res + app_eff;
						else
							res = res + app_eff + " AND";
						
					}
				}
				
				res = res + "}\n";
				
				if(i==ex_events_vector.size()-1)
					res = res + "\n";
			}
		}
				
		return res;
				
	}
	
	private String getFormulaeforInformationView(){
		
		Vector formulae_vector = XMLParser.getFormulae();
		
		String res = "COMPLEX FORMULAE :\n";
		
		if(formulae_vector.size()==0)
			res = "<NO COMPLEX FORMULAE>\n\n";
		else {
			
			for(int i=0;i<formulae_vector.size();i++) {
				
				String formula_name = (String) formulae_vector.elementAt(i);
				
				res = res + formula_name + "[";
				
				Vector<Vector<String>> formula_params_vector = XMLParser.getInputParametersOfFormula(formula_name);
							
				for(int j=0;j<formula_params_vector.size();j++) {
					
					Vector argument_vector = formula_params_vector.elementAt(j);
					
					if(j==formula_params_vector.size()-1)
						res = res + argument_vector.elementAt(0) + ":" + argument_vector.elementAt(1);
					else
						res = res + argument_vector.elementAt(0) + ":" + argument_vector.elementAt(1)+",";
				
				}
				
				res = res + "] = {";
				
				String formula_content = XMLParser.getFormulaContent(formula_name);
				
				res = res + formula_content + "}\n";
				
				if(i==formulae_vector.size()-1)
					res = res + "\n";
			}
			
			
			
		}
				
		return res;
				
	}
	
}
