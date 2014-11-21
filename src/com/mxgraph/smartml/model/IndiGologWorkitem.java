package com.mxgraph.smartml.model;

import java.util.Vector;

public class IndiGologWorkitem {
	
	private String task_name;
	private String workitem;
	private String workitem_type;
	private String supp_eff_for_SSA;
	private String workitem_types_for_SSA;
	private Vector workitem_atomic_terms_for_supp_eff;

	/**
	 * An IndiGologWorkitem object aims at recording:
	 * (i) - the name of the task involved in the workitem.
	 * (ii) - 
	 * (iii) - 
	**/
	
	public IndiGologWorkitem(String task_name) {		
		this.task_name = task_name;
		this.workitem = workitem;
		this.workitem_type = workitem_type;
	}
	
	public IndiGologWorkitem(String task_name, String workitem, String workitem_type, String supp_eff_for_SSA, String workitem_types_for_SSA, Vector workitem_atomic_terms_for_supp_eff) {		
		this.task_name = task_name;
		this.workitem = workitem;
		this.workitem_type = workitem_type;
		this.supp_eff_for_SSA = supp_eff_for_SSA;
		this.workitem_types_for_SSA = workitem_types_for_SSA;
		this.workitem_atomic_terms_for_supp_eff = workitem_atomic_terms_for_supp_eff;
	}

	public String getTask_name() {
		return task_name;
	}

	public String getWorkitem() {
		return workitem;
	}

	public String getWorkitem_type() {
		return workitem_type;
	}
	
	public String getSuppEffForSSA() {
		return supp_eff_for_SSA;
	}
	
	public String getWorkitemTypesForSSA() {
		return workitem_types_for_SSA;
	}
	
	public Vector getWorkitemAtomicTermsForSSA() {
		return workitem_atomic_terms_for_supp_eff;
	}
	
}
