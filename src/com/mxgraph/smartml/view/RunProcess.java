package com.mxgraph.smartml.view;

import java.awt.Dimension;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import com.mxgraph.smartml.model.IndiGologWorkitem;
import com.mxgraph.smartml.model.XMLParser;

import Devices.ChannelsManager;
import Devices.DefaultPMProtocol;
import Devices.Environment;
import Devices.PMServer;
import Devices.Pda2;
import Devices.Workitem;
import Devices.World;

public class RunProcess {
	
	public RunProcess() {
		try {			
			//createIndiGologFile();
			invokeSmartPM("apms.pl");
		
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	
private void createIndiGologFile() {
	
	StringBuffer sb = new StringBuffer();
	
	sb.append("%%%%%%%%%%%%%%%%%%%% \n");
	sb.append("% FILE: aPMS/aPMS.pl \n");
	sb.append("%%%%%%%%%%%%%%%%%%%% \n\n");
	
	sb.append(":- dynamic controller/1. \n\n");
	
	sb.append("cache(_):-fail. \n\n");
	
	sb.append("causes_true(_,_,_) :- false.\n");
	sb.append("causes_false(_,_,_) :- false.\n\n");	
	
	sb.append("actionNum(X,X).\n\n");	

	sb.append("/* MACROS */ \n");
	sb.append("square(X,Y) :- Y is X * X.\n\n");
	
	sb.append("member(ELEM,[HEAD|_]) :- ELEM=HEAD.\n");
	sb.append("member(ELEM,[_|TAIL]) :- member(ELEM,TAIL).\n\n");

	sb.append("listEqual(L1,L2) :- subset(L1,L2),subset(L2,L1).\n\n");

	sb.append("/* RESOURCES PERSPECTIVE */ \n\n");
	 
	
    // ***************** SERVICES AND PROVIDERS (IF EXIST) *****************************
	
	Vector providers_vector = XMLParser.getProviders(true);
	Vector services_vector = XMLParser.getServices(true);
	
	if(providers_vector.size() > 0) {
		
		StringBuffer providerINservice = new StringBuffer();
		
		for(int j=0;j<providers_vector.size();j++) {
			
			Vector prov_vector = (Vector) providers_vector.elementAt(j);
			
			for(int i=0;i<prov_vector.size();i++) {
			
			if(i==0) {
				sb.append(prov_vector.firstElement()+"(X) :- domain(X,[");	
				providerINservice.append("service(S) :- " + prov_vector.firstElement() + "(S).\n");
			}
			else if(i==prov_vector.size()-1)
				sb.append(prov_vector.elementAt(i) + "]).\n");
			else sb.append(prov_vector.elementAt(i) + ",");
		}
		}
		sb.append("\n" + providerINservice + "\n");
	}
	else {
		
		sb.append("service(S) :- domain(S,[");
		
			for(int i=0;i<services_vector.size();i++) {
			
			if(i==services_vector.size()-1)
				sb.append(services_vector.elementAt(i) + "]).\n\n");
			else sb.append(services_vector.elementAt(i) + ",");
			}
	}
	
    // ***************** CAPABILITIES *****************************
	
	Vector capabilities_vector = XMLParser.getCapabilities(true);
	
	if(capabilities_vector.size() > 0) {
		
		sb.append("capability(B) :- domain(B,[");
		
		for(int i=0;i<capabilities_vector.size();i++) {
		
		if(i==capabilities_vector.size()-1)
			sb.append(capabilities_vector.elementAt(i) + "]).\n\n");
		else sb.append(capabilities_vector.elementAt(i) + ",");
		}
		
	}
	
    // ***************** List of PROVIDES predicates *****************************
	
		sb.append("\n/* List of PROVIDES predicates */ \n\n");
	
		Vector services_capabilities_associations_vector = XMLParser.getAssociationsBetweenServicesAndCapabilities(true);
	
		for(int j=0;j<services_capabilities_associations_vector.size();j++) {
		
		Vector single_association_vector = (Vector) services_capabilities_associations_vector.elementAt(j);
		
		sb.append("provides(");			
		sb.append(single_association_vector.firstElement()+",");
		sb.append(single_association_vector.lastElement());
		sb.append(").\n");
	}
	
    // ***************** List of PRE-DEFINED DATA TYPES **************************************
	
		sb.append("\n/* Pre-defined DATA TYPES */\n");
		sb.append("\n");	
		sb.append("boolean_type(Q) :- domain(Q,[true,false]).\n\n");
		
		sb.append("integer_type(N) :- domain(N,[");
		
	Vector int_bounds_vector = XMLParser.getIntegerType();
	
	int lower_bound = new Integer(((String) int_bounds_vector.firstElement()));
	int upper_bound = new Integer(((String) int_bounds_vector.lastElement()));
		
		for(int k=lower_bound;k<=upper_bound;k++) {
			
			if(k==upper_bound)
				sb.append(k + "]).\n\n");
			else sb.append(k + ",");
			
		}
		
	// ***************** List of USER-DEFINED DATA TYPES **************************************
		
		sb.append("\n/* User-defined DATA TYPES */\n");
		sb.append("\n");
	
		Vector user_data_types_vector = XMLParser.getUserDataTypes(true);
		
		for(int j=0;j<user_data_types_vector.size();j++) {
			
			Vector single_data_type_vector = (Vector) user_data_types_vector.elementAt(j);
			
			for(int i=0;i<single_data_type_vector.size();i++) {
				
				if(i==0) 
					sb.append(single_data_type_vector.firstElement()+"(X) :- domain(X,[");	
				else if(i==single_data_type_vector.size()-1)
					sb.append(single_data_type_vector.elementAt(i) + "]).\n");
				else 
					sb.append(single_data_type_vector.elementAt(i) + ",");
				
			}
		}
		
	// ***************** List of TASK NAMES **************************************
		
		sb.append("\n/* TASK PERSPECTIVE */\n");
		sb.append("\n");
		
		Vector task_names_vector = XMLParser.getTaskNames(true);
		
		if(task_names_vector.size() > 0) {
			
			sb.append("task(T) :- domain(T,[");
			
			for(int i=0;i<task_names_vector.size();i++) {
			
			if(i==task_names_vector.size()-1)
				sb.append(task_names_vector.elementAt(i) + "]).\n\n");
			else sb.append(task_names_vector.elementAt(i) + ",");
			}
			
		}
		
	// ***************** List of TASK IDENTIFIERS*****************************************
		
		sb.append("\n/* TASK IDENTIFIERS */\n");
		sb.append("\n");
		sb.append("task_identifiers([");
		
		int lower_bound_id = 1;
		int upper_bound_id = 100;
			
			for(int k=lower_bound_id;k<=upper_bound_id;k++) {
				
				if(k==upper_bound_id)
					sb.append("id_" + k + ",id_adapt]).\n\n");
				else sb.append("id_" + k + ",");
				
			}
		sb.append("id(D) :- domain(D,task_identifiers). \n");
		
    // ***************** List of REQUIRES predicates *****************************
	
		sb.append("\n/* List of REQUIRES predicates */ \n\n");
		
		Vector tasks_capabilities_associations_vector = XMLParser.getAssociationsBetweenTasksAndCapabilities(true);
	
		for(int j=0;j<tasks_capabilities_associations_vector.size();j++) {
		
		Vector single_association_vector = (Vector) tasks_capabilities_associations_vector.elementAt(j);
		
		sb.append("requires(");			
		sb.append(single_association_vector.firstElement()+",");
		sb.append(single_association_vector.lastElement());
		sb.append(").\n");
	}
		
	// ***************** List of WORKITEMS *****************************
		
		sb.append("\n/* WORKITEMS */\n\n");
		
		Vector indigolog_workitems_vector = new Vector();
		
		Vector formulae_vector = XMLParser.getFormulae();
		
		//We start also to populate the buffer containing preconditions
		StringBuffer precondition_buffer = new StringBuffer();
		
		if(task_names_vector.size() > 0) {
			
			Vector workitem_atomic_terms_for_supp_eff = new Vector();
			
			for(int i=0;i<task_names_vector.size();i++) {
				
				workitem_atomic_terms_for_supp_eff = new Vector();
			
				String workitem = "workitem(";
				String workitem_types;
				
				String task_name = (String) task_names_vector.elementAt(i);
				
				workitem = workitem + task_name + ",ID,[";
				workitem_types = "id(ID)";
				
				Vector task_inp_param_vector = XMLParser.getInputParametersOfTask(task_name,true);
				
				for(int ind=0;ind<task_inp_param_vector.size();ind++) {	
					
					
					Vector single_task_parameter_vector = (Vector) task_inp_param_vector.elementAt(ind);
					
					if(ind == task_inp_param_vector.size() - 1) {
						workitem = workitem + ((String)single_task_parameter_vector.firstElement()).toUpperCase() + "],";
						workitem_types = workitem_types + "," + single_task_parameter_vector.lastElement() + "(" + ((String)single_task_parameter_vector.firstElement()).toUpperCase() + ")";	
										
					}
					else {
						workitem = workitem + ((String)single_task_parameter_vector.firstElement()).toUpperCase() + ",";
						workitem_types = workitem_types + "," + single_task_parameter_vector.lastElement() + "(" + ((String)single_task_parameter_vector.firstElement()).toUpperCase() + ")";
						
					}
					
				}
				
				Vector task_supposed_effects_vector = XMLParser.getTaskSupposedEffects(task_name,true);
				
				workitem = workitem + "[";

				//***********************************************
				String supp_eff_for_SSA = "[";
				String workitem_types_for_SSA = workitem_types;
				//***********************************************
				
				if(task_supposed_effects_vector.size()>0) {
				
				for(int index=0;index<task_supposed_effects_vector.size();index++) {
				
					//***********************************************
					Vector workitem_single_at_for_supp_eff = new Vector();
					String supposed_return_value = null;
					//***********************************************
					
					String single_supp_effect = (String) task_supposed_effects_vector.elementAt(index);
					String at_term_return_type = new String();
					String[] split = single_supp_effect.split(" = ");

					if(split[1].equalsIgnoreCase("true") || split[1].equalsIgnoreCase("false")) {
					
						at_term_return_type = "boolean_type";
						workitem = workitem + split[1];
						
						//***********************************************
						supposed_return_value = split[1];
						supp_eff_for_SSA = supp_eff_for_SSA + "OUTPUT"+index;
						workitem_types_for_SSA = workitem_types_for_SSA + ",boolean_type(" + "OUTPUT"+index + ")";
						//***********************************************
						
						if(!workitem_types.contains("boolean_type(" + split[1] + ")")) {
							workitem_types = workitem_types + ",boolean_type(" + split[1] + ")";
							//***********************************************
							workitem_types_for_SSA = workitem_types_for_SSA + ",boolean_type(" + split[1] + ")";
							//***********************************************
						}
						if(index<task_supposed_effects_vector.size()-1)  {
							workitem = workitem +",";							
							//***********************************************
							supp_eff_for_SSA = supp_eff_for_SSA + ",";
							//***********************************************
						}
					}
					else {
						
						String split2[] = split[0].split("\\[");
						
						at_term_return_type = XMLParser.getAtomicTermReturnType(split2[0],true);
						
						if(!workitem_types.contains(at_term_return_type +"(" + split[1].toUpperCase() + ")")) {						
							workitem_types = workitem_types + "," + at_term_return_type + "(" + split[1].toLowerCase() + ")";
							//***********************************************
							supposed_return_value = split[1].toLowerCase();
							workitem_types_for_SSA = workitem_types_for_SSA + "," + at_term_return_type + "(" + split[1].toLowerCase() + ")";
							//***********************************************
							workitem = workitem + split[1];
						}
						else {
							//***********************************************
							supposed_return_value = split[1].toUpperCase();
							//***********************************************
							workitem = workitem + split[1].toUpperCase();
						}
						
						//***********************************************
						supp_eff_for_SSA = supp_eff_for_SSA + "OUTPUT"+index;						
						workitem_types_for_SSA = workitem_types_for_SSA + "," + at_term_return_type + "(" + "OUTPUT"+index + ")";
						//***********************************************
					
						if(index<task_supposed_effects_vector.size()-1) {
							workitem = workitem +",";
							//***********************************************
							supp_eff_for_SSA = supp_eff_for_SSA + ",";
							//***********************************************
						}
					}
					
					//*************** Auxiliary method for computing SSAs ***************
					String[] split_1 = split[0].split("\\[");
					String[] split_2 = split_1[1].split("\\]");
					
					String left_atomic_term_name = split_1[0];
					/*
					System.out.println(left_atomic_term_name);
					System.out.println(split_2[0]);
					System.out.println(at_term_return_type);
					System.out.println(supposed_return_value);
					 */
					
					String[] split_commas = split_2[0].split("\\,");
					
					if(split_commas.length>1) {
						String left_at_term = left_atomic_term_name + "(";
						String left_at_term_exp = left_atomic_term_name + "_exp(";
						
						for(int h=0;h<split_commas.length;h++) { 
							 String param = split_commas[h];
							 String param_Upper_Case = param.toUpperCase();

							 if(workitem_types_for_SSA.contains("(" + param_Upper_Case + ")")  || param_Upper_Case.equalsIgnoreCase("SRVC")) {
								left_at_term = left_at_term + param_Upper_Case;
								left_at_term_exp = left_at_term_exp + param_Upper_Case;
								} 
      						 else {
      							left_at_term = left_at_term + param;
      							left_at_term_exp = left_at_term_exp + param;
								}
							 
							 if(h<split_commas.length-1) {
								 left_at_term = left_at_term + ",";
								 left_at_term_exp = left_at_term_exp + ",";
							 }
						}
						workitem_single_at_for_supp_eff.addElement(left_at_term + ")");
						workitem_single_at_for_supp_eff.addElement("OUTPUT"+index);
						workitem_single_at_for_supp_eff.addElement(left_at_term_exp + ")");
						workitem_single_at_for_supp_eff.addElement(supposed_return_value);
					}
					else {
						String param = split_2[0];
						String param_Upper_Case = param.toUpperCase();
						if(workitem_types_for_SSA.contains("(" + param_Upper_Case + ")") || param_Upper_Case.equalsIgnoreCase("SRVC")) {
							workitem_single_at_for_supp_eff.addElement(left_atomic_term_name + "(" + param_Upper_Case + ")");
							workitem_single_at_for_supp_eff.addElement("OUTPUT"+index);
							workitem_single_at_for_supp_eff.addElement(left_atomic_term_name + "_exp(" + param_Upper_Case + ")");
							workitem_single_at_for_supp_eff.addElement(supposed_return_value);
						
						} 
						else {
							
							workitem_single_at_for_supp_eff.addElement(left_atomic_term_name + "(" + param.toLowerCase() + ")");
							workitem_single_at_for_supp_eff.addElement("OUTPUT"+index);
							workitem_single_at_for_supp_eff.addElement(left_atomic_term_name + "_exp(" + param.toLowerCase() + ")");
							workitem_single_at_for_supp_eff.addElement(supposed_return_value);
						}
						
					}
					
					workitem_atomic_terms_for_supp_eff.addElement(workitem_single_at_for_supp_eff);
					//***********************************************
					
				}
				}
				workitem = workitem + "])";
				//***********************************************
				supp_eff_for_SSA = supp_eff_for_SSA + "]";
				//***********************************************
				
				sb.append("listelem(" + workitem + ") :- " + workitem_types + ".\n");
	
				/*
				System.out.println(workitem);
				System.out.println(workitem_types);
				System.out.println(supp_eff_for_SSA);
				System.out.println(workitem_types_for_SSA);
				*/
				IndiGologWorkitem indiwork = new IndiGologWorkitem(task_name, workitem, workitem_types, supp_eff_for_SSA, workitem_types_for_SSA, workitem_atomic_terms_for_supp_eff);
				indigolog_workitems_vector.add(indiwork);

				precondition_buffer.append(createIndigologPrecondition(task_name,workitem,workitem_types,formulae_vector,task_inp_param_vector)+"\n");

			}

		}
		
		sb.append("\n");	
		sb.append("worklist([]).\n");	
		sb.append("worklist([ELEM | TAIL]) :- worklist(TAIL),listelem(ELEM).\n\n");	 
		
		sb.append("rec_worklist([]).\n");	
		sb.append("rec_worklist([ELEM | TAIL]) :- rec_worklist(TAIL),rec_listelem(ELEM).\n");
		sb.append("rec_listelem(recoveryTask(TASK,SRVC,_INPUTS)) :- task(TASK),service(SRVC).\n\n");		
		 
	// ***************** List of TASK PRECONDITIONS *****************************
		
		sb.append("\n/* TASK PRECONDITIONS */\n\n");

		sb.append("prim_action(assign(SRVC,LWRK)) :- worklist(LWRK),service(SRVC).\n\n");
		
		sb.append(precondition_buffer);
		
		
	// ***************** TASK LIFE CYCLE *****************************
		
		sb.append("\n/* TASK LIFE CYCLE */\n\n");
		
		sb.append("rel_fluent(assigned(SRVC,_LWRK)) :- service(SRVC).\n");
		sb.append("causes_val(assign(SRVC,LWRK),assigned(SRVC,LWRK),true,true).\n");		 
		sb.append("causes_val(release(SRVC,LWRK),assigned(SRVC,LWRK),false,true).\n\n");		 
		
		sb.append("rel_fluent(reserved(SRVC,ID,TASK)) :- task(TASK), service(SRVC), id(ID).\n"); 
		sb.append("causes_val(readyToStart(SRVC,ID,TASK),reserved(SRVC,ID,TASK),true,true).\n"); 
		sb.append("causes_val(finishedTask(SRVC,ID,TASK,_),reserved(SRVC,ID,TASK),false,true).\n\n"); 
		
		sb.append("prim_action(start(SRVC,ID,TASK,INPUT,EXOUTPUT)) :- listelem(workitem(TASK,ID,INPUT,EXOUTPUT)), service(SRVC).\n"); 
		sb.append("poss(start(SRVC,ID,TASK,_INPUT,_EXOUTPUT),reserved(SRVC,ID,TASK)) :- service(SRVC), id(ID), task(TASK).\n\n"); 
		 
		sb.append("prim_action(ackCompl(SRVC,ID,TASK)) :- task(TASK), service(SRVC), id(ID).\n");  
		sb.append("poss(ackCompl(SRVC,ID,TASK),neg(reserved(SRVC,ID,TASK))).\n\n"); 
		 
		sb.append("prim_action(release(SRVC,LWRK)) :- worklist(LWRK),service(SRVC).\n");  
		sb.append("poss(release(_SRVC,_LWRK), true).\n\n");  
		 
		sb.append("rel_fluent(free(SRVC)) :- service(SRVC).\n");  
		sb.append("causes_val(release(SRVC,_LWRK),free(SRVC),true,true).\n");
		sb.append("causes_val(assign(SRVC,_LWRK),free(SRVC),false,true).\n\n");		 
		 
		sb.append("rel_fluent(built_in_adaptation).\n"); 
		
		// ***************** USER DEFINED STATIC FLUENTS *****************************
		
		sb.append("\n/* USER-DEFINED STATIC FLUENTS */\n\n");
		
		Vector static_terms_vector = XMLParser.getAllStaticTermsName();
		
		for(int indice=0;indice<static_terms_vector.size();indice++) {
			
			sb.append("fun_fluent(");
			
			String single_static_term = (String) static_terms_vector.elementAt(indice);
			StringBuffer static_terms_types = new StringBuffer();
			
			Vector static_term_parameters_vector = XMLParser.getAtomicTermParametersNameAndType(single_static_term);
			
			
			if(static_term_parameters_vector.size()>0)  {
				
				sb.append(single_static_term.toLowerCase() + "(");
				
				for(int ind=0;ind<static_term_parameters_vector.size();ind++) {
					
					Vector single_static_term_parameter_vector = (Vector) static_term_parameters_vector.elementAt(ind);
					
					if(ind==static_term_parameters_vector.size()-1)  {
						sb.append(((String)single_static_term_parameter_vector.firstElement()).toUpperCase() +")) :- ");
						sb.append(static_terms_types + ((String)single_static_term_parameter_vector.lastElement()).toLowerCase() + "(" + ((String)single_static_term_parameter_vector.firstElement()).toUpperCase() + ").\n");
					
					}
					else {
						sb.append(((String)single_static_term_parameter_vector.firstElement()).toUpperCase() +",");
						static_terms_types.append(((String)single_static_term_parameter_vector.lastElement()).toLowerCase() + "(" + ((String)single_static_term_parameter_vector.firstElement()).toUpperCase() + "),");
						
					}
					
				}
				
				
			}
			else  { //the static term has no parameters.
				sb.append(single_static_term.toLowerCase() + ").\n");
			}
			
		}
		
		// ***************** USER DEFINED DYNAMIC FLUENTS ****************************
		
		sb.append("\n/* USER-DEFINED DYNAMIC FLUENTS */\n\n");
		
		sb.append(createSSAs(indigolog_workitems_vector));
		
		// ***************** COMPLEX FORMULAE ****************************************
		
		// ***************** INITIAL STATE *******************************************
		
		// ***************** EXOGENOUS ACTIONS ***************************************
		
		// ***************** EXOGENOUS EVENTS ****************************************
		
		// ***************** ADAPTATION COMMANDS (fixed) *****************************
		
		sb.append("\n/* ADAPTATION COMMANDS */\n\n");
		
		sb.append("prim_action(initPMS).\n");
		sb.append("poss(initPMS, true).\n\n");

		sb.append("prim_action(endPMS).\n");
		sb.append("poss(endPMS, true).\n\n");
		
		sb.append("prim_action(finish).\n");
		sb.append("poss(finish,true).\n");
		sb.append("rel_fluent(finished).\n");
		sb.append("causes_val(finish,finished,true,true).\n\n");		 
		 
		sb.append("rel_fluent(exogenous).\n");		 
		sb.append("initially(exogenous,false).\n");				 
		sb.append("causes_val(release(_,_),exogenous,true,neg(adapting)).\n\n");	
		 
		sb.append("/*******************************/\n");			 
		sb.append("prim_action(resetPreExog).\n");		
		sb.append("poss(resetPreExog,true).\n");	
		sb.append("causes_val(resetPreExog,pre_exogenous,false,true).\n");			
		sb.append("rel_fluent(pre_exogenous).\n");		
		sb.append("initially(pre_exogenous,false).\n"); 		
		sb.append("/*******************************/\n\n");	
		 
		sb.append("prim_action(resetExog).\n");		 
		sb.append("poss(resetExog,true).\n");			 
		sb.append("causes_val(resetExog,exogenous,false,true).\n\n");			 

		sb.append("prim_action(adaptFinish).\n");
		sb.append("poss(adaptFinish,true).\n\n");		 
		 
		sb.append("prim_action(adaptStart).\n");
		sb.append("poss(adaptStart,true).\n\n");			 

		sb.append("prim_action(adaptFound).\n");
		sb.append("poss(adaptFound,true).\n\n");			 
		 
		sb.append("prim_action(adaptFound).\n");
		sb.append("poss(adaptFound,true).\n\n");
		 
		sb.append("prim_action(adaptNotFound).\n");
		sb.append("poss(adaptNotFound,true).\n\n");		 

		sb.append("\n/*COMMANDS FOR INVOKING THE PLANNER*/\n\n");
		
		sb.append("prim_action(invokePlanner).\n");
		sb.append("poss(invokePlanner,true).\n\n");		 
		
		sb.append("rel_fluent(adaptationPlanReady).\n");
		sb.append("initially(adaptationPlanReady,true).\n");		
		sb.append("causes_val(invokePlanner,adaptationPlanReady,false,true).\n");			
		sb.append("causes_val(planReady(_P),adaptationPlanReady,true,true).\n\n");			
		
		sb.append("fun_fluent(recoveryPlan).\n");
		sb.append("initially(recoveryPlan,[]).\n");		
		sb.append("causes_val(planReady(P),recoveryPlan,P,true).\n\n");			
		
		sb.append("/*******************************/\n");
		sb.append("/* Action needed for managing signal events */\n");
		sb.append("prim_action(stop).\n");
		sb.append("poss(stop,false).\n");		
		sb.append("/*******************************/\n\n");		

		sb.append("rel_fluent(adapting).\n");
		sb.append("causes_val(adaptStart,adapting,true,true).\n"); 
		sb.append("causes_val(adaptFound,adapting,false,true).\n\n");		 
		
		// ***************** DEFINITION OF "RELEVANT" PREDICATE *****************************		

		// ***************** MONITOR AND ADAPTATION FEATURES ********************************
		
		sb.append("\n/* MONITOR AND ADAPTATION FEATURES */\n\n");
		
		sb.append("proc(monitor,[?(writeln('Monitor')),ndet([?(neg(relevant)),?(writeln('NonRelevant'))],");
		sb.append("[?(relevant),?(writeln('Relevant')),");
		sb.append("[adaptStart,createPlanningProblem,?(report_message(user, 'About to adapt...')),");				 
		sb.append("if(built_in_adaptation,pconc([adaptingProgram, adaptFinish],while(adapting, [?(writeln('waiting')),wait])),");				 
		sb.append("[invokePlanner,manageRecoveryProcess(recoveryPlan),adaptFound,adaptFinish])");				 
		sb.append("]]),");					    
		sb.append("resetExog]).\n\n");					  
				  
		sb.append("proc(adaptingProgram,searchn([?(true),adapt,[adaptFound,?(report_message(user, 'Adaptation program found!'))]],");	
		sb.append("[assumptions([[assign(N,[workitem(T,D,I,E)]),readyToStart(N,D,T)],[start(N,D,T,I,E),finishedTask(N,D,T,E)]])])).\n\n");		         
				      
		sb.append("proc(adapt,plans(0,2)).\n\n");		      

		sb.append("proc(plans(M,N),[if(M=N,[adaptNotFound],[?(M<(N+1)),ndet([actionSequence(M),?(neg(relevant))],[?(SUCCM is M+1),plans(SUCCM,N)])])]).\n\n");

		sb.append("proc(actionSequence(N),ndet([?(N=0)],[?(N>0),");	
		sb.append("pi([n,t,i,e],[ ?(and(service(n),and(free(n),capable(n,[workitem(t,id_adapt,i,e)])))),"); 
		sb.append("assign(n,[workitem(t,id_adapt,i,e)]),");	
		sb.append("start(n,id_adapt,t,i,e),");	
		sb.append("ackCompl(n,id_adapt,t),");	
		sb.append("release(n,[workitem(t,id_adapt,i,e)]),");	
		sb.append("?(write(' ITERATION = ')),?(write(N)),?(write(' ')),?(write(' TASK = ')),?(write(t)), ?(write(' INPUT = ')), ?(write(i)),");	
		sb.append("?(write(' SERVICE = ')), ?(write(n)), ?(write(' EXOUTPUT = ')), ?(writeln(e))]),");
		sb.append("?(PRECN is N-1), actionSequence(PRECN)])).\n\n");
 
		sb.append("proc(capable(N,X),or(X=[],and(X=[A|TAIL],and(listelem(A),and(A=workitem(T,_D,_I,_E),and(findall(B,requires(T,B),D),and(findall(B,provides(N,B),C),and(subset(D,C),capable(N,TAIL))))))))).\n\n");	

		sb.append("proc(possible(N,X),and(poss(assign(N,X),A),A)).\n\n");
 
		sb.append("proc(manageExecution(X),[");
		sb.append("atomic([pi(n,[?(and(possible(n,X),and(free(n),capable(n,X)))), assign(n,X)])]),"); 
		sb.append("pi(n,[?(assigned(n,X)=true),executionHelp(n,X)]),");
		sb.append("[atomic([pi(n,[?(assigned(n,X)=true),[release(n,X),printALL]])])]");
		sb.append("]).\n\n");		
			
		sb.append("proc(executionHelp(_N,[]),[]).\n");		
		sb.append("proc(executionHelp(N,[workitem(T,D,I,E)|TAIL]),[start(N,D,T,I,E), if(forced(D,T),abortTask(N,D,T,exOut(D,T)),ackCompl(N,D,T)), executionHelp(N,TAIL)]).\n\n");		

		sb.append("proc(manageRecoveryProcess([]),[]).\n");	 
		sb.append("proc(manageRecoveryProcess(N),[?(N=[recoveryTask(TASKNAME,SERVICE,INPUTS)|TAIL]),");	
		sb.append("manageRecoveryTask(TASKNAME,SERVICE,INPUTS),manageRecoveryProcess(TAIL)]).\n\n");	 

		sb.append("proc(manageRecoveryTask(TASKNAME,SERVICE,INPUTS),");
		sb.append("[pi(o,[?(and(A=workitem(TASKNAME,_D,INPUTS,o),listelem(A))),manageExecution(SERVICE,[workitem(TASKNAME,id_adapt,INPUTS,o)])])]).\n\n");				

		sb.append("proc(manageExecution(S,X),[assign(S,X),pi(n,[?(assigned(n,X)=true),executionHelp(n,X)]),atomic([pi(n,[?(assigned(n,X)=true),[release(n,X),printALL]])])]).\n\n"); 

		sb.append("proc(main, mainControl(N)) :- controller(N), !.\n");	 
		sb.append("proc(main, mainControl(5)).\n\n");	

		sb.append("proc(mainControl(5), prioritized_interrupts([");
		sb.append("interrupt(and(neg(finished),neg(adaptationPlanReady)), [?(writeln('>>>>>>>>>>>> Waiting for a recovery plan...')), wait]),"); 
		sb.append("interrupt(and(neg(finished),exogenous), monitor),");
		sb.append("interrupt(and(neg(finished),pre_exogenous), applySideEffects),");
		sb.append("interrupt(true, [process,finish]),");
		sb.append("interrupt(neg(finished), wait)])).\n\n");

		
	
	scriviFile("/home/and182/indigolog/Examples/aPMS/apms.pl", sb);
}

private StringBuffer createSSAs(Vector indigolog_workitems_vector) {

	StringBuffer Successor_State_Axioms_buffer = new StringBuffer();
	
	Vector dynamic_term_names_vector = XMLParser.getAllDynamicTermsName();
	
	for(int hindex=0;hindex<dynamic_term_names_vector.size();hindex++) {
		
		StringBuffer SSA_buffer = new StringBuffer();
		StringBuffer SSA_exp_buffer = new StringBuffer();
		
		String single_dynamic_term_name = (String) dynamic_term_names_vector.elementAt(hindex);
		StringBuffer dynamic_terms_types = new StringBuffer();
		
		SSA_buffer.append("\n/* FLUENT " + single_dynamic_term_name + " */\n");
	
		SSA_buffer.append("fun_fluent(");
		
		boolean isRelevant = new Boolean((String)((Vector)XMLParser.getIfAtomicTermIsRelevant(single_dynamic_term_name)).lastElement());
		
		if(isRelevant) SSA_exp_buffer.append("fun_fluent(");
		
		Vector dynamic_term_parameters_vector = XMLParser.getAtomicTermParametersNameAndType(single_dynamic_term_name);

		
		if(dynamic_term_parameters_vector.size()>0)  {
			
			SSA_buffer.append(single_dynamic_term_name.toLowerCase() + "(");
			if(isRelevant) SSA_exp_buffer.append(single_dynamic_term_name.toLowerCase() + "_exp(");
			
			for(int ind=0;ind<dynamic_term_parameters_vector.size();ind++) {
				
				Vector single_dynamic_term_parameter_vector = (Vector) dynamic_term_parameters_vector.elementAt(ind);
				
				if(ind==dynamic_term_parameters_vector.size()-1)  {
					SSA_buffer.append(((String)single_dynamic_term_parameter_vector.firstElement()).toUpperCase() +")) :- ");
					SSA_buffer.append(dynamic_terms_types + ((String)single_dynamic_term_parameter_vector.lastElement()).toLowerCase() + "(" + ((String)single_dynamic_term_parameter_vector.firstElement()).toUpperCase() + ").\n\n");
				
					if(isRelevant) {
						SSA_exp_buffer.append(((String)single_dynamic_term_parameter_vector.firstElement()).toUpperCase() +")) :- ");
						SSA_exp_buffer.append(dynamic_terms_types + ((String)single_dynamic_term_parameter_vector.lastElement()).toLowerCase() + "(" + ((String)single_dynamic_term_parameter_vector.firstElement()).toUpperCase() + ").\n\n");
					}

				}
				else {
					SSA_buffer.append(((String)single_dynamic_term_parameter_vector.firstElement()).toUpperCase() +",");
					dynamic_terms_types.append(((String)single_dynamic_term_parameter_vector.lastElement()).toLowerCase() + "(" + ((String)single_dynamic_term_parameter_vector.firstElement()).toUpperCase() + "),");
					
					if(isRelevant) {
					SSA_exp_buffer.append(((String)single_dynamic_term_parameter_vector.firstElement()).toUpperCase() +",");
					}
				}
				
			}

		}
		else  { //the dynamic term has no parameters.
			SSA_buffer.append(single_dynamic_term_name.toLowerCase() + ").\n\n");
			
			if(isRelevant) {
				SSA_exp_buffer.append(single_dynamic_term_name.toLowerCase() + "_exp).\n\n");				
			}
		}
		
		Successor_State_Axioms_buffer.append(SSA_buffer);
		Successor_State_Axioms_buffer.append(SSA_exp_buffer);

	}
	
	StringBuffer SSA_buffer_causes_val = new StringBuffer();
	StringBuffer SSA_exp_buffer_causes_val = new StringBuffer();
	
	for(int z=0;z<indigolog_workitems_vector.size();z++) {
		IndiGologWorkitem single_workitem = (IndiGologWorkitem) indigolog_workitems_vector.elementAt(z);
		
		Vector SSA_outcome_vector = single_workitem.getWorkitemAtomicTermsForSSA();
		
		for(int uind=0;uind<SSA_outcome_vector.size();uind++) {
		
		Vector single_SSA_vector = (Vector) SSA_outcome_vector.elementAt(uind);
			
		SSA_buffer_causes_val.append("causes_val(finishedTask(SRVC,ID,");
		SSA_buffer_causes_val.append(single_workitem.getTask_name() + ",");
		SSA_buffer_causes_val.append(single_workitem.getSuppEffForSSA() + "),");
		SSA_buffer_causes_val.append(single_SSA_vector.elementAt(0) + ",");
		SSA_buffer_causes_val.append(single_SSA_vector.elementAt(1) + ",");
		SSA_buffer_causes_val.append("assigned(SRVC,[" + single_workitem.getWorkitem() + "])) :- " + single_workitem.getWorkitemTypesForSSA() + ".\n\n");
		
		/*
		System.out.println("ECCOLO " + single_workitem.getTask_name());
		System.out.println(single_workitem.getWorkitem());
		System.out.println(single_workitem.getWorkitemTypesForSSA());
		System.out.println(single_workitem.getSuppEffForSSA());
		System.out.println(single_workitem.getWorkitemAtomicTermsForSSA());
		*/
		
		SSA_exp_buffer_causes_val.append("causes_val(finishedTask(SRVC,ID,");
		SSA_exp_buffer_causes_val.append(single_workitem.getTask_name() + ",");
		SSA_exp_buffer_causes_val.append(single_workitem.getSuppEffForSSA() + "),");
		SSA_exp_buffer_causes_val.append(single_SSA_vector.elementAt(2) + ",");
		SSA_exp_buffer_causes_val.append(single_SSA_vector.elementAt(3) + ",");
		SSA_exp_buffer_causes_val.append("and(neg(adapting),assigned(SRVC,[" + single_workitem.getWorkitem() + "]))) :- " + single_workitem.getWorkitemTypesForSSA() + ".\n\n");
		
		}
		
		Vector automatic_effects_vector = XMLParser.getTaskAutomaticEffects(single_workitem.getTask_name(),true);
		//Manage SSAs of tasks automatic effects
		
				
		
		for(int aind=0;aind<automatic_effects_vector.size();aind++) {
			
			SSA_buffer_causes_val.append("\n/* AUTOMATIC EFFECT OF " + single_workitem.getTask_name() + " */\n\n");
			
			SSA_buffer_causes_val.append("causes_val(finishedTask(SRVC,ID," + single_workitem.getTask_name() + "," + single_workitem.getSuppEffForSSA() + "),");
			
			String single_automatic_effect = (String) automatic_effects_vector.elementAt(aind);
			
			String split[] = null;
			
			if(single_automatic_effect.contains("+="))  {
				split = single_automatic_effect.split(" \\+= ");
			}
			else if(single_automatic_effect.contains("-="))	 {
				split = single_automatic_effect.split(" -= ");
				}	
			else if(single_automatic_effect.contains("="))		 {
				split = single_automatic_effect.split(" = ");
			}
			
				String[] split_1 = split[0].split("\\[");
				String[] split_2 = split_1[1].split("\\]");
					
				String left_atomic_term_name = split_1[0];
				String complete_left_atomic_term = new String();
				
				/*
				System.out.println(left_atomic_term_name);
				System.out.println(split_2[0]);
				
				System.out.println(at_term_return_type);
				System.out.println(supposed_return_value);
				 */
				if(split_2.length>0) {
				String[] split_commas = split_2[0].split("\\,");
					
				if(split_commas.length>1) {
					String left_at_term = left_atomic_term_name + "(";
						
						for(int h=0;h<split_commas.length;h++) { 
							 String param = split_commas[h];
							 String param_Upper_Case = param.toUpperCase();
	
							 if(single_workitem.getWorkitemTypesForSSA().contains("(" + param_Upper_Case + ")")  || param_Upper_Case.equalsIgnoreCase("SRVC")) {
								left_at_term = left_at_term + param_Upper_Case;
							} 
	  						 else {
	  							left_at_term = left_at_term + param;
	  							
								}
							 
							 if(h<split_commas.length-1) {
								 left_at_term = left_at_term + ",";
							 }
						}
						
						complete_left_atomic_term = left_atomic_term_name + "(" + left_at_term + ")";
						
						SSA_buffer_causes_val.append(left_atomic_term_name + "(" + left_at_term + "),L,");
					}	
					else {
						String param = split_2[0];
						String param_Upper_Case = param.toUpperCase();
						if(single_workitem.getWorkitemTypesForSSA().contains("(" + param_Upper_Case + ")") || param_Upper_Case.equalsIgnoreCase("SRVC")) {
							SSA_buffer_causes_val.append(left_atomic_term_name + "(" + param_Upper_Case + "),L,");
							
							complete_left_atomic_term = left_atomic_term_name + "(" + param_Upper_Case + ")";
						} 
						else {
							
							SSA_buffer_causes_val.append(left_atomic_term_name + "(" + param + "),L,");
							
							complete_left_atomic_term = left_atomic_term_name + "(" + param + ")";
						}
						
						
					}
				}
				else{
					
					SSA_buffer_causes_val.append(left_atomic_term_name + "(),L,");
					
					complete_left_atomic_term = left_atomic_term_name + "()";
				}
				
				SSA_buffer_causes_val.append("and(assigned(SRVC,[" + single_workitem.getWorkitem() + "]),");
				
				if(single_automatic_effect.contains("+="))
					SSA_buffer_causes_val.append("L is " + complete_left_atomic_term + " + ");
				else if(single_automatic_effect.contains("-="))
					SSA_buffer_causes_val.append("L is " + complete_left_atomic_term + " - ");
				else if(single_automatic_effect.contains("="))
					SSA_buffer_causes_val.append("L is ");
				
				String repl_1 = split[1].replace("[","(");
				String repl_2 = repl_1.replace("]",")");
				SSA_buffer_causes_val.append(repl_2);
				
				SSA_buffer_causes_val.append(")) :- " + single_workitem.getWorkitemTypesForSSA() + ".\n\n");
				
			}
			

		
		
		System.out.println("ECCOLO " + single_workitem.getTask_name());
		System.out.println(automatic_effects_vector);
		
	}
	
	Successor_State_Axioms_buffer.append(SSA_buffer_causes_val);
	Successor_State_Axioms_buffer.append(SSA_exp_buffer_causes_val);

	return Successor_State_Axioms_buffer;
}


private StringBuffer createIndigologPrecondition(String task_name, String workitem, String workitem_types, Vector formulae_vector, Vector task_parameters_vector) {

	Vector task_preconditions_vector = XMLParser.getTaskPreconditions(task_name);
	
	StringBuffer precondition_buffer = new StringBuffer();
	
	precondition_buffer.append("poss(assign(SRVC,[" + workitem + "]),");	
	
	if(task_preconditions_vector.size()>0) {
		
		//System.out.println("TASK PRECONDITIONS: " + task_preconditions_vector); 
		
		StringBuffer end_prec_buffer = new StringBuffer();

		//******************************* 1 - Do this cycle for each task precondition
		//******************************* 
		for(int kin=0;kin<task_preconditions_vector.size();kin++) {
		
			String single_precondition = (String) task_preconditions_vector.elementAt(kin);
			String new_single_precondition = new String();
			
			if(single_precondition.contains("==")) { //The atomic term could be an Integer_Type, a Boolean_Type or a Functional_Type
							
				String[] splitter = single_precondition.split("\\[");
				
				if (formulae_vector.contains(splitter[0])) { //case of a Complex Formula
					splitter = single_precondition.split(" == ");
					String repl_2 = splitter[0].replace("[", "(");
					String repl_3 = repl_2.replace("]", ")");
					if(repl_3.contains("()")) { //NO ARGUMENTS *******************************************
							   repl_3 = repl_3.replace("()", "");
							   String repl_3_lower_case = repl_3.toLowerCase();
							   new_single_precondition =  repl_3_lower_case;
					}
					else  { //ONE OR MORE ARGUMENTS *******************************************
						
						String repl_3_lower_case = repl_3.toLowerCase();
						
						for(int pi=0;pi<task_parameters_vector.size();pi++){
							
							Vector single_parameter_vector = (Vector) task_parameters_vector.elementAt(pi);
							
							if(repl_3_lower_case.contains("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")"))
								repl_3_lower_case = repl_3_lower_case.replace("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")", "(" + ((String)single_parameter_vector.firstElement()).toUpperCase() + ")");
							if(repl_3_lower_case.contains("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ","))
								repl_3_lower_case = repl_3_lower_case.replace("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ",", "(" + ((String)single_parameter_vector.firstElement()).toUpperCase() + ",");
							if(repl_3_lower_case.contains("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")"))
								repl_3_lower_case = repl_3_lower_case.replace("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")", "," + ((String)single_parameter_vector.firstElement()).toUpperCase() + ")");
							if(repl_3_lower_case.contains("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ","))
								repl_3_lower_case = repl_3_lower_case.replace("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ",", "," + ((String)single_parameter_vector.firstElement()).toUpperCase() + ",");
							if(repl_3_lower_case.contains("=" + ((String)single_parameter_vector.firstElement()).toLowerCase()))
								repl_3_lower_case = repl_3_lower_case.replace("=" + ((String)single_parameter_vector.firstElement()).toLowerCase(), "=" + ((String)single_parameter_vector.firstElement()).toUpperCase());
							if(repl_3_lower_case.contains("SRVC".toLowerCase()))
								repl_3_lower_case = repl_3_lower_case.replace("SRVC".toLowerCase(), "SRVC");	
						}
						new_single_precondition = repl_3_lower_case;
						
					}
				}
				else { //case of a Functional Term, a Boolean Term or an Integer Term*******************************************
					
					String[] right_split = single_precondition.split(" == ");
					
					String repl_1 = single_precondition.replace(" == ", "=");
					String repl_2 = repl_1.replace("[", "(");
					String repl_3 = repl_2.replace("]", ")");

					if(repl_3.contains("()")) { //NO ARGUMENTS *******************************************
						repl_3 = repl_3.replace("()", "");
						String repl_3_lower_case = repl_3.toLowerCase();	
						
						for(int pi=0;pi<task_parameters_vector.size();pi++){
							
							Vector single_parameter_vector = (Vector) task_parameters_vector.elementAt(pi);
						
							if(repl_3_lower_case.contains("=" + ((String)single_parameter_vector.firstElement()).toLowerCase()))
								repl_3_lower_case = repl_3_lower_case.replace("=" + ((String)single_parameter_vector.firstElement()).toLowerCase(), "=" + ((String)single_parameter_vector.firstElement()).toUpperCase());
							}
							if(repl_3_lower_case.contains("SRVC".toLowerCase()))
								repl_3_lower_case = repl_3_lower_case.replace("SRVC".toLowerCase(), "SRVC");
						
						new_single_precondition = repl_3_lower_case;
					}
					else  { //ONE OR MORE ARGUMENTS *******************************************
					
						String repl_3_lower_case = repl_3.toLowerCase();
						
						for(int pi=0;pi<task_parameters_vector.size();pi++){
							
							Vector single_parameter_vector = (Vector) task_parameters_vector.elementAt(pi);
							
							if(repl_3_lower_case.contains("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")"))
								repl_3_lower_case = repl_3_lower_case.replace("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")", "(" + ((String)single_parameter_vector.firstElement()).toUpperCase() + ")");
							if(repl_3_lower_case.contains("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ","))
								repl_3_lower_case = repl_3_lower_case.replace("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ",", "(" + ((String)single_parameter_vector.firstElement()).toUpperCase() + ",");
							if(repl_3_lower_case.contains("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")"))
								repl_3_lower_case = repl_3_lower_case.replace("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")", "," + ((String)single_parameter_vector.firstElement()).toUpperCase() + ")");
							if(repl_3_lower_case.contains("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ","))
								repl_3_lower_case = repl_3_lower_case.replace("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ",", "," + ((String)single_parameter_vector.firstElement()).toUpperCase() + ",");
							if(repl_3_lower_case.contains("=" + ((String)single_parameter_vector.firstElement()).toLowerCase()))
								repl_3_lower_case = repl_3_lower_case.replace("=" + ((String)single_parameter_vector.firstElement()).toLowerCase(), "=" + ((String)single_parameter_vector.firstElement()).toUpperCase());
							if(repl_3_lower_case.contains("SRVC".toLowerCase()))
								repl_3_lower_case = repl_3_lower_case.replace("SRVC".toLowerCase(), "SRVC");	
						}
						new_single_precondition = repl_3_lower_case;
						
					}
				}
			} 
			else if(single_precondition.contains(" >= ")) { //valid only for an Integer Term*******************************************
				String repl_1 = single_precondition.replace(" >= ", ">=");
				String repl_2 = repl_1.replace("[", "(");
				String repl_3 = repl_2.replace("]", ")");
				
				if(repl_3.contains("()")) { //NO ARGUMENTS *******************************************
						   repl_3 = repl_3.replace("()", "");
						   String repl_3_lower_case = repl_3.toLowerCase();	
							
							for(int pi=0;pi<task_parameters_vector.size();pi++){
								
								Vector single_parameter_vector = (Vector) task_parameters_vector.elementAt(pi);
							
								if(repl_3_lower_case.contains(">=" + ((String)single_parameter_vector.firstElement()).toLowerCase()))
									repl_3_lower_case = repl_3_lower_case.replace(">=" + ((String)single_parameter_vector.firstElement()).toLowerCase(), ">=" + ((String)single_parameter_vector.firstElement()).toUpperCase());
								}
								if(repl_3_lower_case.contains("SRVC".toLowerCase()))
									repl_3_lower_case = repl_3_lower_case.replace("SRVC".toLowerCase(), "SRVC");
							
							new_single_precondition = repl_3_lower_case;

				}		   
				else  { //ONE OR MORE ARGUMENTS *******************************************
					
					String repl_3_lower_case = repl_3.toLowerCase();
					
					for(int pi=0;pi<task_parameters_vector.size();pi++){
						
						Vector single_parameter_vector = (Vector) task_parameters_vector.elementAt(pi);
						
						if(repl_3_lower_case.contains("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")"))
							repl_3_lower_case = repl_3_lower_case.replace("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")", "(" + ((String)single_parameter_vector.firstElement()).toUpperCase() + ")");
						if(repl_3_lower_case.contains("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ","))
							repl_3_lower_case = repl_3_lower_case.replace("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ",", "(" + ((String)single_parameter_vector.firstElement()).toUpperCase() + ",");
						if(repl_3_lower_case.contains("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")"))
							repl_3_lower_case = repl_3_lower_case.replace("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")", "," + ((String)single_parameter_vector.firstElement()).toUpperCase() + ")");
						if(repl_3_lower_case.contains("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ","))
							repl_3_lower_case = repl_3_lower_case.replace("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ",", "," + ((String)single_parameter_vector.firstElement()).toUpperCase() + ",");
						if(repl_3_lower_case.contains(">=" + ((String)single_parameter_vector.firstElement()).toLowerCase()))
							repl_3_lower_case = repl_3_lower_case.replace(">=" + ((String)single_parameter_vector.firstElement()).toLowerCase(), ">=" + ((String)single_parameter_vector.firstElement()).toUpperCase());
	
					}
					if(repl_3_lower_case.contains("SRVC".toLowerCase()))
						repl_3_lower_case = repl_3_lower_case.replace("SRVC".toLowerCase(), "SRVC");
					
					new_single_precondition = repl_3_lower_case;
					
				}
			}
			
			else if(single_precondition.contains(" <= ")) { //valid only for an Integer Term*******************************************
				String repl_1 = single_precondition.replace(" <= ", "<=");
				String repl_2 = repl_1.replace("[", "(");
				String repl_3 = repl_2.replace("]", ")");
				
				if(repl_3.contains("()")) { //NO ARGUMENTS *******************************************
						   repl_3 = repl_3.replace("()", "");
						   String repl_3_lower_case = repl_3.toLowerCase();	
							
							for(int pi=0;pi<task_parameters_vector.size();pi++){
								
								Vector single_parameter_vector = (Vector) task_parameters_vector.elementAt(pi);
							
								if(repl_3_lower_case.contains("<=" + ((String)single_parameter_vector.firstElement()).toLowerCase()))
									repl_3_lower_case = repl_3_lower_case.replace("<=" + ((String)single_parameter_vector.firstElement()).toLowerCase(), "<=" + ((String)single_parameter_vector.firstElement()).toUpperCase());
								}
								if(repl_3_lower_case.contains("SRVC".toLowerCase()))
									repl_3_lower_case = repl_3_lower_case.replace("SRVC".toLowerCase(), "SRVC");
							
							new_single_precondition = repl_3_lower_case;

				}		   
				else  { //ONE OR MORE ARGUMENTS *******************************************
					
					String repl_3_lower_case = repl_3.toLowerCase();
					
					for(int pi=0;pi<task_parameters_vector.size();pi++){
						
						Vector single_parameter_vector = (Vector) task_parameters_vector.elementAt(pi);
						
						if(repl_3_lower_case.contains("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")"))
							repl_3_lower_case = repl_3_lower_case.replace("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")", "(" + ((String)single_parameter_vector.firstElement()).toUpperCase() + ")");
						if(repl_3_lower_case.contains("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ","))
							repl_3_lower_case = repl_3_lower_case.replace("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ",", "(" + ((String)single_parameter_vector.firstElement()).toUpperCase() + ",");
						if(repl_3_lower_case.contains("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")"))
							repl_3_lower_case = repl_3_lower_case.replace("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")", "," + ((String)single_parameter_vector.firstElement()).toUpperCase() + ")");
						if(repl_3_lower_case.contains("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ","))
							repl_3_lower_case = repl_3_lower_case.replace("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ",", "," + ((String)single_parameter_vector.firstElement()).toUpperCase() + ",");
						if(repl_3_lower_case.contains("<=" + ((String)single_parameter_vector.firstElement()).toLowerCase()))
							repl_3_lower_case = repl_3_lower_case.replace("<=" + ((String)single_parameter_vector.firstElement()).toLowerCase(), "<=" + ((String)single_parameter_vector.firstElement()).toUpperCase());
	
					}
					if(repl_3_lower_case.contains("SRVC".toLowerCase()))
						repl_3_lower_case = repl_3_lower_case.replace("SRVC".toLowerCase(), "SRVC");
					
					new_single_precondition = repl_3_lower_case;
					
				}
			}
			

			else if(single_precondition.contains(" < ")) { //valid only for an Integer Term*******************************************
				String repl_1 = single_precondition.replace(" < ", "<");
				String repl_2 = repl_1.replace("[", "(");
				String repl_3 = repl_2.replace("]", ")");
				
				if(repl_3.contains("()")) { //NO ARGUMENTS *******************************************
						   repl_3 = repl_3.replace("()", "");
						   String repl_3_lower_case = repl_3.toLowerCase();	
							
							for(int pi=0;pi<task_parameters_vector.size();pi++){
								
								Vector single_parameter_vector = (Vector) task_parameters_vector.elementAt(pi);
							
								if(repl_3_lower_case.contains("<" + ((String)single_parameter_vector.firstElement()).toLowerCase()))
									repl_3_lower_case = repl_3_lower_case.replace("<" + ((String)single_parameter_vector.firstElement()).toLowerCase(), "<" + ((String)single_parameter_vector.firstElement()).toUpperCase());
								}
								if(repl_3_lower_case.contains("SRVC".toLowerCase()))
									repl_3_lower_case = repl_3_lower_case.replace("SRVC".toLowerCase(), "SRVC");
							
							new_single_precondition = repl_3_lower_case;

				}		   
				else  { //ONE OR MORE ARGUMENTS *******************************************
					
					String repl_3_lower_case = repl_3.toLowerCase();
					
					for(int pi=0;pi<task_parameters_vector.size();pi++){
						
						Vector single_parameter_vector = (Vector) task_parameters_vector.elementAt(pi);
						
						if(repl_3_lower_case.contains("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")"))
							repl_3_lower_case = repl_3_lower_case.replace("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")", "(" + ((String)single_parameter_vector.firstElement()).toUpperCase() + ")");
						if(repl_3_lower_case.contains("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ","))
							repl_3_lower_case = repl_3_lower_case.replace("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ",", "(" + ((String)single_parameter_vector.firstElement()).toUpperCase() + ",");
						if(repl_3_lower_case.contains("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")"))
							repl_3_lower_case = repl_3_lower_case.replace("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")", "," + ((String)single_parameter_vector.firstElement()).toUpperCase() + ")");
						if(repl_3_lower_case.contains("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ","))
							repl_3_lower_case = repl_3_lower_case.replace("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ",", "," + ((String)single_parameter_vector.firstElement()).toUpperCase() + ",");
						if(repl_3_lower_case.contains("<" + ((String)single_parameter_vector.firstElement()).toLowerCase()))
							repl_3_lower_case = repl_3_lower_case.replace("<" + ((String)single_parameter_vector.firstElement()).toLowerCase(), "<" + ((String)single_parameter_vector.firstElement()).toUpperCase());
	
					}
					if(repl_3_lower_case.contains("SRVC".toLowerCase()))
						repl_3_lower_case = repl_3_lower_case.replace("SRVC".toLowerCase(), "SRVC");
					
					new_single_precondition = repl_3_lower_case;
					
				}
			}
			else if(single_precondition.contains(" > ")) { //valid only for an Integer Term*******************************************
				String repl_1 = single_precondition.replace(" > ", ">");
				String repl_2 = repl_1.replace("[", "(");
				String repl_3 = repl_2.replace("]", ")");
				
				if(repl_3.contains("()")) { //NO ARGUMENTS *******************************************
						   repl_3 = repl_3.replace("()", "");
						   String repl_3_lower_case = repl_3.toLowerCase();	
							
							for(int pi=0;pi<task_parameters_vector.size();pi++){
								
								Vector single_parameter_vector = (Vector) task_parameters_vector.elementAt(pi);
							
								if(repl_3_lower_case.contains(">" + ((String)single_parameter_vector.firstElement()).toLowerCase()))
									repl_3_lower_case = repl_3_lower_case.replace(">" + ((String)single_parameter_vector.firstElement()).toLowerCase(), ">" + ((String)single_parameter_vector.firstElement()).toUpperCase());
								}
								if(repl_3_lower_case.contains("SRVC".toLowerCase()))
									repl_3_lower_case = repl_3_lower_case.replace("SRVC".toLowerCase(), "SRVC");
							
							new_single_precondition = repl_3_lower_case;

				}		   
				else  { //ONE OR MORE ARGUMENTS *******************************************
					
					String repl_3_lower_case = repl_3.toLowerCase();
					
					for(int pi=0;pi<task_parameters_vector.size();pi++){
						
						Vector single_parameter_vector = (Vector) task_parameters_vector.elementAt(pi);
						
						if(repl_3_lower_case.contains("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")"))
							repl_3_lower_case = repl_3_lower_case.replace("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")", "(" + ((String)single_parameter_vector.firstElement()).toUpperCase() + ")");
						if(repl_3_lower_case.contains("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ","))
							repl_3_lower_case = repl_3_lower_case.replace("(" + ((String)single_parameter_vector.firstElement()).toLowerCase() + ",", "(" + ((String)single_parameter_vector.firstElement()).toUpperCase() + ",");
						if(repl_3_lower_case.contains("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")"))
							repl_3_lower_case = repl_3_lower_case.replace("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ")", "," + ((String)single_parameter_vector.firstElement()).toUpperCase() + ")");
						if(repl_3_lower_case.contains("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ","))
							repl_3_lower_case = repl_3_lower_case.replace("," + ((String)single_parameter_vector.firstElement()).toLowerCase() + ",", "," + ((String)single_parameter_vector.firstElement()).toUpperCase() + ",");
						if(repl_3_lower_case.contains(">" + ((String)single_parameter_vector.firstElement()).toLowerCase()))
							repl_3_lower_case = repl_3_lower_case.replace(">" + ((String)single_parameter_vector.firstElement()).toLowerCase(), ">" + ((String)single_parameter_vector.firstElement()).toUpperCase());
	
					}
					if(repl_3_lower_case.contains("SRVC".toLowerCase()))
						repl_3_lower_case = repl_3_lower_case.replace("SRVC".toLowerCase(), "SRVC");
					
					new_single_precondition = repl_3_lower_case;
					
				}
			}
			
			
			//INSERTION OF THE 'AND' conjunctions
			if(task_preconditions_vector.size()==1) {
				precondition_buffer.append(new_single_precondition);
			}
			else if(task_preconditions_vector.size()>1) {
				if(kin==task_preconditions_vector.size()-1) {
					precondition_buffer.append(new_single_precondition);
					precondition_buffer.append(end_prec_buffer);
				}
				else {
					precondition_buffer.append("and(" + new_single_precondition + ",");
					end_prec_buffer.append(")");
				}
				
			}

		}
	}
	else //NO Preconditions for the specific task
		precondition_buffer.append("true");
	
	precondition_buffer.append(") :- service(SRVC),");
	precondition_buffer.append(workitem_types + ".\n\n");
	
	return precondition_buffer;
	
}


public static boolean invokeSmartPM(String name_of_the_file) throws IOException {
		
		//A new ChannelsManager object is created
		ChannelsManager manager = new ChannelsManager();
	    
		//The protocol for exchanging actions is initialized
		new DefaultPMProtocol(manager);
	    
		//The server waits for new actions at the port 5555, with the ChannelManager object just created
		PMServer server = new PMServer(5555, manager);
	    server.start();
	 
		World world = new World(4,4,new Dimension(500,500));
	    //World world = null;
	    
		//new Pda2("act1",0,80,world);
		//new Pda2("act2",0,460,world); 
		//new Pda2("act3",340,80,world);
		//new Pda2("act4",340,460,world); 
		//new Pda2("rb1",640,80,world);
		//new Pda2("rb2",640,460,world); 
		
		new Environment("Environment",740,820,null); 
	    
		//Invocation of the IndiGolog engine
		try
        {

		Process pr = Runtime.getRuntime().exec("gnome-terminal -e ./indi_script");
		
	    pr.waitFor();

        }
        catch(Exception e)
	    {e.printStackTrace();
	    }
	
        return true;
		
	}

private static void scriviFile(String nomeFile, StringBuffer buffer) {
	 
	  File file = null;
    FileWriter fw = null;
	   
	   try {
		file = new File(nomeFile);
		fw = new FileWriter(file);
		fw.write(buffer.toString());
		fw.close();
		
	   //fw.flush();
	   //fw.close();
	   }
	   catch(IOException e) {
	   e.printStackTrace();
	   }
	   }

/*
public static void main(String[] args)
{
	try {
		invokeSmartPM("apms.pl");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
*/
}
